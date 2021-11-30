package com.laison.erp.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laison.erp.common.constants.ConfigConstant;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.*;
import com.laison.erp.dao.sys.SysDeptDao;
import com.laison.erp.model.sys.*;
import com.laison.erp.service.sys.DeptConfigService;
import com.laison.erp.service.sys.SysDeptService;
import com.laison.erp.service.sys.SysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author lihua 496583747@qq.com
 * @date 2021-02-01 17:05:46
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {


    @Autowired
    private SysDeptDao sysDeptDao;


    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private DeptConfigService deptConfigService;

    @Override
    public int countByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return sysDeptDao.selectCountByExample(example);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return sysDeptDao.deleteByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = CACHE_NAME, key = "#id") // 清空 缓存
    public int deleteByPrimaryKey(String id) throws Exception {
        Example example = new Example(SysDept.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", id);
        int count = sysDeptDao.selectCountByExample(example);
        count += sysDeptDao.countUserNumber(id);
//        count += sysDeptDao.countCustomerNumber(id);// TODO 改成真正的查询客户数量
        if (count > 0) {
            throw new Exception(ContentConstant.CAN_NOT_DELETE);
        }
        SysDept sysDept = sysDeptDao.selectByPrimaryKey(id);
        if (sysDept != null) {
            SysDept q = new SysDept().setParentId(sysDept.getParentId());
            count = sysDeptDao.selectCount(q);//我的父亲有几个孩子
            if (count == 1) {
                SysDept up = new SysDept().setId(sysDept.getParentId()).setHasChild(false);
                sysDeptDao.updateByPrimaryKeySelective(up);
            }
        }
        SpringContextUtils.getCache(CACHE_NAME).clear();
        SpringContextUtils.getCache(SysUserService.CACHE_NAME).clear();
        SpringContextUtils.getCache(SysUserService.BRIEF_CACHE_NAME).clear();

        SpringContextUtils.getCache(STRUCTURE_CACHE_NAME).clear();


        return sysDeptDao.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int save(SysDept record) throws Exception {
        String parentId = record.getParentId();
        SysDept psysDept = sysDeptDao.selectByPrimaryKey(parentId);


        if (psysDept == null) {
            throw new Exception(ContentConstant.P_OBJECT_NOT_EXIST);
        }
        DeptConfig pdeptConfig = deptConfigService.findByDeptId(parentId);
        String[] deptIds = psysDept.getPaths().split(",");
        int index = deptIds.length;
        while (pdeptConfig == null) {
            pdeptConfig = deptConfigService.findByDeptId(deptIds[--index]);
        }
        Config pconifg = JsonUtils.jsonToPojo(pdeptConfig.getConfigJson(), Config.class);
        record.setLevel(psysDept.getLevel() + 1);
        record.setPaths(psysDept.getPaths() + psysDept.getId() + ",");
        if (!psysDept.getHasChild()) {
            psysDept.setHasChild(true);
            sysDeptDao.updateByPrimaryKeySelective(psysDept);
        }
        Integer mylevel = record.getLevel();
        Integer compIDGenMode = pconifg.getCompIDGenMode();
        if (compIDGenMode.equals(2)) {
            if (mylevel > 2) {
                record.setCompID(psysDept.getCompID());
            } else {
                record.setCompID(SequenceUtil.getCompId());
            }
        }
        Cache cache = SpringContextUtils.getCache(CACHE_NAME);
        cache.clear();
        cache = SpringContextUtils.getCache(SysUserService.CACHE_NAME);
        SpringContextUtils.getCache(SysUserService.BRIEF_CACHE_NAME).clear();
        cache.clear();
        cache = cacheManager.getCache(STRUCTURE_CACHE_NAME);
        cache.clear();
        record.setUpdateTime(DateUtils.utcDate());
        SysUser sysUser = LoginUserUtil.getSysUser();
        record.setManagerId(sysUser.getId());//默认创建者就是管理员
        return sysDeptDao.insertSelective(record);
    }

    @Override
    public List<SysDept> selectAllByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        if (ConfigConstant.ISOLATE_ENABLE) {
            try {
                SysUser sysUser = LoginUserUtil.getSysUser();
                if (sysUser != null) {
                    Criteria criteria = example.createCriteria();
                    criteria.andIn("id", sysUser.getSysDeptIds());
                    example.and(criteria);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        String orderByClause = (String) condition.get("orderByClause");
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        return sysDeptDao.selectByExample(example);
    }

    @Override
    public PageInfo<SysDept> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
            throws Exception {
        Example example = createExample(condition);
        if (ConfigConstant.ISOLATE_ENABLE) {
            try {
                SysUser sysUser = LoginUserUtil.getSysUser();
                if (sysUser != null) {
                    Criteria criteria = example.createCriteria();
                    criteria.andIn("id", sysUser.getSysDeptIds());
                    example.and(criteria);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        PageHelper.startPage(pageNum, pageSize, true);
        String orderByClause = (String) condition.get("orderByClause");
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        List<SysDept> list = sysDeptDao.selectByExample(example);

        //2021年10月11日19:49:12 陆家全 这边需要将部门的配置查询出来 设置

        PageInfo<SysDept> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public SysDept selectFirstByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        String orderByClause = (String) condition.get("orderByClause");
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        PageHelper.startPage(1, 1, true);
        List<SysDept> list = sysDeptDao.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }

    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#id", sync = true)
    public SysDept selectByPrimaryKey(String id) throws Exception {
        SysDept sysDept = sysDeptDao.selectByPrimaryKey(id);
        if (sysDept == null) {
            throw new Exception(ContentConstant.DEPT_NOT_EXIST);
        }
        String paths = sysDept.getPaths();
        DeptConfig deptConfig = deptConfigService.findByDeptId(id);
        String[] deptIds = paths.split(",");
        int index = deptIds.length;
        while (deptConfig == null) {
            deptConfig = deptConfigService.findByDeptId(deptIds[--index]);
        }
        Config conifg = JsonUtils.jsonToPojo(deptConfig.getConfigJson(), Config.class);
        conifg.setDeptId(id);
        sysDept.setConfig(conifg);
        loadSubDepts(sysDept);
        return sysDept;
    }

    private void loadSubDepts(SysDept sysDept) throws Exception {
        String id = sysDept.getId();
        // 开始设置子部门
        if (sysDept.getHasChild()) {
            List<SysDept> subDepts = sysDeptDao.loadSubDepts(id);
            if (!CollectionUtils.isEmpty(subDepts)) {
                for (SysDept tsysDept : subDepts) {
                    loadSubDepts(tsysDept);
                }
            }
            sysDept.setChildren(subDepts);
        }


    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByCondition(SysDept record, HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return sysDeptDao.updateByExample(record, example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = CACHE_NAME, allEntries = true) // 清除 缓存
    public int updateByPrimaryKey(SysDept record) throws Exception {
        record.setParentId(null).setLevel(null).setPaths(null).setType(null).setHasChild(null);//不能修改类型
        record.setUpdateTime(DateUtils.utcDate());
        SpringContextUtils.getCache(CACHE_NAME).clear();
        SpringContextUtils.getCache(SysUserService.CACHE_NAME).clear();
        SpringContextUtils.getCache(SysUserService.BRIEF_CACHE_NAME).clear();
        return sysDeptDao.updateByPrimaryKeySelective(record);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKeys(List<String> ids) throws Exception {
        Example example = new Example(SysDept.class);
        Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        Cache cache = SpringContextUtils.getCache(CACHE_NAME);
        ids.forEach(cache::evict);
        return sysDeptDao.deleteByExample(example);
    }

    private Example createExample(HashMap<String, Object> condition) {
        Example example = new Example(SysDept.class);

        Criteria criteria = example.createCriteria();
        if (condition != null) {
            if (condition.get("id") != null && !"".equals(condition.get("id"))) {
                criteria.andEqualTo("id", condition.get("id"));
            }
            if (condition.get("level") != null && !"".equals(condition.get("level"))) {
                criteria.andEqualTo("level", condition.get("level"));
            }
            if (condition.get("pids") != null) {
                criteria.andIn("parentId", (List) condition.get("pids"));
            }
            if (condition.get("deptName") != null) {
                criteria.andLike("name", "%" + condition.get("deptName") + "%");
            }
            if (condition.get("forOrder") != null) {
                criteria.andCondition("level <3 or type=32 or type=8 ");
            }
        }

        SysUser sysUser = LoginUserUtil.getSysUser();
        if (sysUser != null) {
            criteria.andGreaterThanOrEqualTo("level", sysUser.getSysDept().getLevel());
        }
        return example;
    }

    @Override
    public ArrayList<String> findSubDeptIds(String pdeptid) {
        ArrayList<String> subIds = sysDeptDao.findSubDeptIds(pdeptid);
        subIds.add(pdeptid);
        return subIds;
    }

    @Override
    public void changeDeptConfig(Config config) throws Exception {
        String deptId = config.getDeptId();
        SysDept sysDept = selectByPrimaryKey(deptId);
        DeptConfig deptConfig = deptConfigService.findByDeptId(deptId);
        if (deptConfig == null) {
            deptConfig = new DeptConfig();
            deptConfig.setDeptId(deptId);
            deptConfig.setConfigJson(JsonUtils.objectToJson(config));
            deptConfig.setName(sysDept.getName() + "_config");
            deptConfigService.save(deptConfig);

        } else {
            deptConfig.setConfigJson(JsonUtils.objectToJson(config));
            deptConfigService.updateByPrimaryKey(deptConfig);
        }
        SpringContextUtils.getCache(CACHE_NAME).clear();
        SpringContextUtils.getCache(SysUserService.CACHE_NAME).clear();
        SpringContextUtils.getCache(SysUserService.BRIEF_CACHE_NAME).clear();
    }

    @Override
    @Cacheable(value = STRUCTURE_CACHE_NAME, key = "#deptId", sync = true)
    public Structure structure(String deptId) throws Exception {
        Structure structure = sysDeptDao.selectStructureByPrimaryKey(deptId);
        List<Member> members = sysDeptDao.selectStructureMember(deptId);
        structure.setMembers(members);
        loadSubStructureAndMember(structure);
        return structure;
    }

    @Override
    public int countByRegion(String regionId) {
        return sysDeptDao.countByRegion(regionId);
    }

    @Override
    public String queryDeptName(String deptId) {
        return sysDeptDao.queryDeptName(deptId);
    }

    /**
     * 查询部门树
     */
    @Override
    public List<SysDepartTreeModel> queryTreeList() throws Exception {
        //获取用户所在的部门
        String parentId = LoginUserUtil.getSysUser().getParentDeptId();
        if (StringUtils.isBlank(parentId)) return null;
        SysDept parentDept = sysDeptDao.selectBrief(parentId);
        if (parentDept == null) return null;
        //获取其全部的子部门
        setDeptTree(parentDept);
        List<SysDept> tree = new ArrayList<>();
        tree.add(parentDept);
        return AntTreeUIUtils.wrapTreeDataToTreeList(tree);
    }

    @Override
    public SysDept queryTreeList2() throws Exception {
        return sysDeptDao.getDepartmentsByParentId(LoginUserUtil.getSysUser().getParentDeptId());
    }

    @Override
    public List<SysDept> findNextSubDept(String deptId) {
        return sysDeptDao.selectDeptsByParentId(deptId);
    }

    private void setDeptTree(SysDept parentDept) {
        if (parentDept.getHasChild()) {
            List<SysDept> children = sysDeptDao.selectBriefDeptsByParentId(parentDept.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                for (SysDept sysDept : children) {
                    setDeptTree(sysDept);
                }
            }
            parentDept.setChildren(children);
        }
    }

    private void loadSubStructureAndMember(Structure structure) throws Exception {

        String id = structure.getId();
        // 开始设置子组织
        List<Structure> subStructures = sysDeptDao.selectSubStructuresByParentId(id);
        if (!CollectionUtils.isEmpty(subStructures)) {
            for (Structure subStructure : subStructures) {
                String deptid = subStructure.getId();
                List<Member> members = sysDeptDao.selectStructureMember(deptid);
                subStructure.setMembers(members);
                loadSubStructureAndMember(subStructure);
            }
        }
        structure.setSubStructures(subStructures);
    }

    @Override
    public List<String> findDeptIds(String deptId, Integer deptlevel) throws Exception {
        List<String> subDeptIds;
        SysDept dept = selectByPrimaryKey(deptId);
        Integer level = dept.getLevel();
        String[] split = dept.getPaths().split(",");
        if (level == null || deptlevel == null || deptlevel < 1 || level.equals(0)) {//获取本部门及下面的
            subDeptIds = findSubDeptIds(deptId);
        } else if (deptlevel < split.length - 1) {//获取deptlevel级部门及以下
            subDeptIds = findSubDeptIds(split[deptlevel + 1]);
        } else {//获取本部门及下面的
            subDeptIds = findSubDeptIds(deptId);
        }
        subDeptIds.add(dept.getId());//加上自己部门
        return subDeptIds;
    }

    @Override
    public boolean deptnameExist(String name) throws Exception {
        SysDept sysDept = LoginUserUtil.getSysUser().getSysDept();//我的部门
        Integer level = sysDept.getLevel();
        String parentid = sysDept.getId();

        if (level > 1) {
            parentid = sysDept.getPaths().split(",")[2];
        }
        return sysDeptDao.deptnameExist(name, parentid);
    }

    /**
     * 获取部门下的领导Id
     */
    @Override
    public List<String> findDeptLeaderIds(List<String> deptIds) {
        if (CollectionUtils.isEmpty(deptIds)) return null;
        return sysDeptDao.findDeptLeaderIds(deptIds);
    }

}
