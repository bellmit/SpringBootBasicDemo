package com.laison.erp.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laison.erp.common.Result;
import com.laison.erp.common.constants.ConfigConstant;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.*;
import com.laison.erp.config.exception.CustomerException;
import com.laison.erp.dao.sys.SysRoleDao;
import com.laison.erp.dao.sys.SysRoleMenuDao;
import com.laison.erp.dao.sys.SysUserRoleDao;
import com.laison.erp.model.sys.SysRole;
import com.laison.erp.model.sys.SysRoleMenu;
import com.laison.erp.model.sys.SysUser;
import com.laison.erp.model.sys.SysUserRole;
import com.laison.erp.service.sys.SysDeptService;
import com.laison.erp.service.sys.SysRoleService;
import com.laison.erp.service.sys.SysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihua 496583747@qq.com
 * @date 2021-02-01 17:04:47
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {


    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysDeptService sysDeptService;


    @Override
    public int countByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return sysRoleDao.selectCountByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
    public int deleteByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return sysRoleDao.deleteByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = CACHE_NAME, key = "#id") // 清空 缓存
    public int deleteByPrimaryKey(String id) throws Exception {
        SysUserRole record = new SysUserRole();
        record.setRoleId(id);
        int count = sysUserRoleDao.selectCount(record);
        if (count > 0) {
            throw new Exception(ContentConstant.CAN_NOT_DELETE);
        }
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setRoleId(id);
        sysRoleMenuDao.delete(sysRoleMenu);
        return sysRoleDao.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int save(SysRole record) throws Exception {
        SysUser sysUser = LoginUserUtil.getSysUser();
        record.setCreateBy(sysUser.getId()).setDeptId(sysUser.getDeptId())
                .setCreateDate(DateUtils.utcDate()).setUpdateTime(DateUtils.utcDate());
        List<String> menuIdList = record.getMenuIdList();
        if (menuIdList == null) {
            throw CustomerException.create(ContentConstant.MISS_PARAM);
        }
        int count = sysRoleDao.insertSelective(record);
        if (!CollectionUtils.isEmpty(menuIdList)) {
            for (String menuId : menuIdList) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(record.getId());
                sysRoleMenuDao.insertSelective(sysRoleMenu);
            }
        }
        return count;

    }

    @Override
    public List<SysRole> selectAllByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        if (ConfigConstant.ISOLATE_ENABLE) {
            LoginUserUtil.addIsolate(example);
        }
        return sysRoleDao.selectByExample(example);
    }

    @Override
    public PageInfo<SysRole> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
            throws Exception {
        Example example = createExample(condition);
        if (ConfigConstant.ISOLATE_ENABLE) {
            LoginUserUtil.addIsolate(example);
        }
        PageHelper.startPage(pageNum, pageSize, true);
        String orderByClause = (String) condition.get("orderByClause");
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        List<SysRole> list = sysRoleDao.selectByExample(example);
        for (SysRole sysRole : list) {
            List<String> menuIds = sysRoleMenuDao.selectMenuIdsByRoleId(sysRole.getId());
            sysRole.setMenuIdList(menuIds);
        }
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public SysRole selectFirstByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        String orderByClause = (String) condition.get("orderByClause");
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        PageHelper.startPage(1, 1, true);
        List<SysRole> list = sysRoleDao.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }

    }

    @Override
    public SysRole selectByPrimaryKey(String id) throws Exception {
        SysRole sysRole = sysRoleDao.selectByPrimaryKey(id);
        if (sysRole == null) {
            throw new Exception(ContentConstant.OBJECT_NOT_EXIST);
        }
        List<String> menuIds = sysRoleMenuDao.selectMenuIdsByRoleId(id);
        sysRole.setMenuIdList(menuIds);
        return sysRole;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByCondition(SysRole record, HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return sysRoleDao.updateByExample(record, example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKey(SysRole record) throws Exception {
        SysRole oldSysRole = sysRoleDao.selectByPrimaryKey(record.getId());
        if (oldSysRole == null) {
            throw new Exception(ContentConstant.OBJECT_NOT_EXIST);
        }

        List<String> menuIdList = record.getMenuIdList();

        if (!CollectionUtils.isEmpty(menuIdList)) {
            SysRoleMenu condition = new SysRoleMenu();
            condition.setRoleId(record.getId());
            sysRoleMenuDao.delete(condition);
            for (String menuId : menuIdList) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(record.getId());
                sysRoleMenuDao.insertSelective(sysRoleMenu);
            }
        }
        record.setDeptId(null);
        int count = sysRoleDao.updateByPrimaryKeySelective(record);
        Cache cache = SpringContextUtils.getCache(SysUserService.CACHE_NAME);
        cache.clear();
        SpringContextUtils.getCache(SysUserService.BRIEF_CACHE_NAME).clear();
        return count;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKeys(List<String> ids) throws Exception {
        Example example = new Example(SysRole.class);
        Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        Cache cache = SpringContextUtils.getCache(CACHE_NAME);
        ((List<String>) ids).forEach((id) -> cache.evict(id));
        return sysRoleDao.deleteByExample(example);
    }

    private Example createExample(HashMap<String, Object> condition) {
        Example example = new Example(SysRole.class);
        Criteria criteria = example.createCriteria();
        if (condition != null) {
            if (condition.get("id") != null && !"".equals(condition.get("id"))) {
                criteria.andEqualTo("id", condition.get("id"));
            }
            if (condition.get("type") != null && !"".equals(condition.get("type"))) {
                criteria.andEqualTo("type", condition.get("type"));
            }
            if (condition.get("state") != null && !"".equals(condition.get("state"))) {
                criteria.andEqualTo("state", condition.get("state"));
            }
            if (condition.get("name") != null) {
                criteria.andLike("name", "%" + condition.get("name") + "%");
            }
        }
        return example;
    }

    @Override
    public List<SysRole> findRolesByUserId(String id) {
        return sysRoleDao.findRolesByUserId(id);

    }

    /**
     * 按照条件查询部门下的角色信息
     */
    @Override
    public Result selectRolesByDept(Map<String, Object> condition) {
        Map<String, Object> pageInfo = (Map<String, Object>) ParamUtils.getValue(condition, "pageInfo");
        Integer page = 1;
        Integer size = 10;
        String deptId = ParamUtils.getString(condition, "deptId");
        String roleName = ParamUtils.getString(condition, "roleName");
        if (pageInfo != null) {
            page = ParamUtils.getInt(pageInfo, "current");
            size = ParamUtils.getInt(pageInfo, "pageSize");
        }
        PageHelper.startPage(page, size);
        List<SysRole> roles;
        if (StringUtils.isNotBlank(roleName)) {
            roles = sysRoleDao.selectByDeptIdAndRoleName(deptId, roleName);
        } else {
            roles = sysRoleDao.selectByDeptId(deptId);
        }
        if (CollectionUtils.isNotEmpty(roles)) {
            for (SysRole role : roles) {
                role.setCreateBy(role.getCreateBy());
                role.setDeptName(sysDeptService.queryDeptName(role.getDeptId()));
            }
        }
        return Result.okData(new PageInfo<>(roles));
    }

    @Override
    public Result selectRolesByIds(List<String> ids) {
        List<SysRole> roles = sysRoleDao.selectRolesByIds(ids);
        if (CollectionUtils.isNotEmpty(roles)) {
            for (SysRole role : roles) {
                role.setCreateBy(role.getCreateBy());
                role.setDeptName(sysDeptService.queryDeptName(role.getDeptId()));
            }
        }
        return Result.okData(roles);
    }

    @Override
    public List<String> findRoleIdsByUserId(String id) {
        return sysRoleDao.findRoleIdsByUserId(id);
    }

    /**
     * 查询部门下对应的所有角色信息
     */
    @Override
    public Result listDeptRole(Map<String, Object> pageInfo) throws Exception {
        SysUser sysUser = LoginUserUtil.getSysUser();
        if (sysUser == null) {
            return Result.error("{user.not.exist}");
        }
        //获取在该父部门下所有的子部门列表
        List<String> sysDeptIds = sysUser.getSysDeptIds();
        String name = "";
        Map<String, Object> condition = JsonUtils.jsonToPojo(ParamUtils.getValue(pageInfo, "condition"), Map.class);
        if (condition != null) {
            name = ParamUtils.getString(condition, "name");
        }
        List<SysRole> roles;
        PageHelper.startPage(ParamUtils.getInt(pageInfo, "current"), ParamUtils.getInt(pageInfo, "pageSize"), true);
        if (StringUtils.isBlank(name)) {
            roles = sysRoleDao.findRolesByDeptIds(sysDeptIds);
        } else {
            roles = sysRoleDao.findRolesByNameAndDeptIds(name, sysDeptIds);
        }
        //判断当前角色是否有他自己
        SysRole sysRole = sysUser.getSysRole();
        checkSelfRole(sysRole, roles);
        return Result.okData(new PageInfo<>(roles));
    }

    @Override
    public List<String> selectUserIds(String roleId) throws Exception {

        return sysRoleDao.selectUserIds(roleId);
    }

    /**
     * 获取角色id对应的用户信息
     */
    @Override
    public List<SysUser> selectUsersByRoleIds(List<String> roleIds) {
        return sysRoleDao.selectUsersByRoleIds(roleIds);
    }

    private void checkSelfRole(SysRole sysRole, List<SysRole> roles) {
        if (sysRole == null) return;
        if (CollectionUtils.isEmpty(roles)) {
            roles = new ArrayList<>();
            roles.add(sysRole);
        } else {
            boolean hasSelf = false;
            for (SysRole role : roles) {
                if (role.getId().equals(sysRole.getId())) {
                    hasSelf = true;
                    break;
                }
            }
            if (!hasSelf) {
                roles.add(sysRole);
            }
        }
    }

}
