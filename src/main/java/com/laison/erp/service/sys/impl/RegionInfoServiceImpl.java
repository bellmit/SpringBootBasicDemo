package com.laison.erp.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laison.erp.common.constants.ConfigConstant;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.DateUtils;
import com.laison.erp.common.utils.LoginUserUtil;
import com.laison.erp.common.utils.SpringContextUtils;
import com.laison.erp.config.exception.CustomerException;
import com.laison.erp.dao.sys.RegionInfoDao;
import com.laison.erp.model.sys.RegionInfo;
import com.laison.erp.service.sys.RegionInfoService;
import com.laison.erp.service.sys.SysDeptService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.HashMap;
import java.util.List;


/**
 * @author lihua 496583747@qq.com
 * @date 2021-02-24 10:06:56
 */
@Service
public class RegionInfoServiceImpl implements RegionInfoService {


    @Autowired
    private RegionInfoDao regionInfoDao;


    @Override
    public int countByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return regionInfoDao.selectCountByExample(example);
    }

    /**
     * 删除id对应的区域
     */
    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        List<RegionInfo> regionInfos = regionInfoDao.selectByExample(example);
        if (CollectionUtils.isNotEmpty(regionInfos)) {
            RegionInfo regionInfo = regionInfos.get(0);
            return deleteByPrimaryKey(regionInfo.getId());
        } else {
            return 0;
        }
    }

    /**
     * 删除id对应的区域
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(String id) throws Exception {
        //查询是否有对应的区域信息
        RegionInfo regionInfo = selectByPrimaryKey(id);
        if (regionInfo == null) return 0;
        //判断部门是否关联该区域，关联则不能删除
        int count = SpringContextUtils.getBean(SysDeptService.class).countByRegion(id);
        if (count > 0) {
            //throw CustomerException.create("{region.relation.dept}");
            throw CustomerException.create(ContentConstant.CAN_NOT_DELETE);
        }
        //判断客户是否关联该区域，关联则不能删除
//        count = SpringContextUtils.getBean(CustomerInfoService.class).countByRegion(id);
//        if (count > 0) {
//            //throw CustomerException.create("{region.relation.customer}");
//            throw CustomerException.create(ContentConstant.CAN_NOT_DELETE);
//        }
//        //判断表计是否关联该区域，关联则不能删除
//        count = SpringContextUtils.getBean(MeterInfoService.class).countByRegion(id);
//        if (count > 0) {
//            //throw CustomerException.create("{region.relation.meter}");
//            throw CustomerException.create(ContentConstant.CAN_NOT_DELETE);
//        }
        String parentId = regionInfo.getParentId();
        //查询该区域是否有children,有则不能直接删除
        if (regionInfo.getHasChild()) {
            //throw CustomerException.create("{region.has.child}");
            throw CustomerException.create(ContentConstant.CAN_NOT_DELETE);
        }
        //删除该区域
        count = regionInfoDao.deleteByPrimaryKey(id);
        //从数据库查询其父所在区域是否还有children
        RegionInfo parentRegionInfo = selectByPrimaryKey(parentId);
        if (parentRegionInfo != null) {
            int parentCount = regionInfoDao.selectReleaseParentCount(parentId);
            if (parentCount < 1) {
                //更新父id的无子区域信息
                parentRegionInfo.setHasChild(false);
                updateByPrimaryKey(parentRegionInfo);
            }
        }
        //清除区域所在的缓存
        SpringContextUtils.getCache(CACHE_NAME).clear();
        //清除部门缓存
        SpringContextUtils.getCache(SysDeptService.CACHE_NAME).clear();
//        //清除客户缓存
//        SpringContextUtils.getCache(CustomerInfoService.CACHE_NAME).clear();
//        //清除表计缓存
//        SpringContextUtils.getCache(MeterInfoService.CACHE_NAME).clear();
        return count;
    }

    /**
     * 批量删除id对应的区域
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKeys(List<String> ids) throws Exception {
        Example example = new Example(RegionInfo.class);
        Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        //清除缓存
        Cache cache = SpringContextUtils.getCache(CACHE_NAME);
        ids.forEach(cache::evict);
        //不允许批量删除，因为会有各种数据表相关联
        List<RegionInfo> regionInfos = regionInfoDao.selectByExample(example);
        int count = 0;
        if (CollectionUtils.isNotEmpty(regionInfos)) {
            for (RegionInfo region : regionInfos) {
                count += deleteByPrimaryKey(region.getId());
            }
        }
        return count;
    }

    @Override
    public List<String> findRegions(List<String> regions) throws Exception {
        return regionInfoDao.findRegions(regions);
    }

    /**
     * 新增区域
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int save(RegionInfo record) throws Exception {
        String parentId = record.getParentId();
        RegionInfo pRegionInfo = regionInfoDao.selectByPrimaryKey(parentId);
        if (pRegionInfo == null) {
            throw new Exception(ContentConstant.P_OBJECT_NOT_EXIST);
        }
        record.setDeptId(LoginUserUtil.getSysUser().getDeptId());
        record.setLevel(pRegionInfo.getLevel() + 1);
        record.setPaths(pRegionInfo.getPaths() + pRegionInfo.getId() + ",");

        if (!pRegionInfo.getHasChild()) {
            pRegionInfo.setHasChild(true);
            regionInfoDao.updateByPrimaryKeySelective(pRegionInfo);
        }
        record.setCreateTime(DateUtils.utcDate()).setUpdateTime(DateUtils.utcDate());
        Cache cache = SpringContextUtils.getCache(CACHE_NAME);
        cache.clear();
        return regionInfoDao.insertSelective(record);

    }

    @Override
    public List<RegionInfo> selectAllByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        if (ConfigConstant.ISOLATE_ENABLE) {
            LoginUserUtil.addIsolate(example);
        }
        return regionInfoDao.selectByExample(example);
    }

    @Override
    public PageInfo<RegionInfo> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
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
        List<RegionInfo> list = regionInfoDao.selectByExample(example);
        PageInfo<RegionInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public RegionInfo selectFirstByCondition(HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        String orderByClause = (String) condition.get("orderByClause");
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        PageHelper.startPage(1, 1, true);
        List<RegionInfo> list = regionInfoDao.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }

    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#id", sync = true)
    public RegionInfo selectByPrimaryKey(String id) throws Exception {
        RegionInfo regionInfo = regionInfoDao.selectByPrimaryKey(id);
        if (regionInfo == null) return null;
        loadSubRegions(regionInfo);
        return regionInfo;
    }

    private void loadSubRegions(RegionInfo regionInfo) {
        String id = regionInfo.getId();
        // 开始设置子部门
        RegionInfo record = new RegionInfo();
        record.setParentId(id);
        List<RegionInfo> subRegions = regionInfoDao.select(record);
        if (!CollectionUtils.isEmpty(subRegions)) {
            for (RegionInfo tregionInfo : subRegions) {
                loadSubRegions(tregionInfo);
            }
        }
        regionInfo.setChildren(subRegions);

    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByCondition(RegionInfo record, HashMap<String, Object> condition) throws Exception {
        Example example = createExample(condition);
        return regionInfoDao.updateByExample(record, example);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, key = "#record.id") // 清除 缓存
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKey(RegionInfo record) throws Exception {
        Cache cache = SpringContextUtils.getCache(CACHE_NAME);
        cache.clear();
        return regionInfoDao.updateByPrimaryKeySelective(record);
    }


    private Example createExample(HashMap<String, Object> condition) {
        Example example = new Example(RegionInfo.class);
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
            if (condition.get("regionName") != null) {
                criteria.andLike("regionName", "%" + condition.get("regionName") + "%");
            }
        }
        return example;
    }


}
