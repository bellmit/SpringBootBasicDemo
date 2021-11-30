package com.laison.erp.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laison.erp.common.constants.ConfigConstant;
import com.laison.erp.common.utils.LoginUserUtil;
import com.laison.erp.common.utils.SpringContextUtils;
import com.laison.erp.dao.sys.DeptConfigDao;
import com.laison.erp.model.sys.DeptConfig;
import com.laison.erp.service.sys.DeptConfigService;
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
* @date  2021-02-03 11:19:56
*/
@Service
public class DeptConfigServiceImpl implements DeptConfigService{
	
	
    @Autowired
	private DeptConfigDao deptConfigDao;

	
	
	@Override
	public int countByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return  deptConfigDao.selectCountByExample(example);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
	public int deleteByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return  deptConfigDao.deleteByExample(example);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value = CACHE_NAME, key = "#id") // 清空 缓存
	public int deleteByPrimaryKey(String id) throws Exception {
		
		return  deptConfigDao.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int save(DeptConfig record) throws Exception {
		
		return deptConfigDao.insertSelective(record);
	}

	@Override
	public List<DeptConfig> selectAllByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		if(ConfigConstant.ISOLATE_ENABLE) {
			LoginUserUtil.addIsolate(example);
		}
		return deptConfigDao.selectByExample(example);
	}

	@Override
	public PageInfo<DeptConfig> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
			throws Exception {
		Example example = createExample(condition);
		if(ConfigConstant.ISOLATE_ENABLE) {
			LoginUserUtil.addIsolate(example);
		}
		PageHelper.startPage(pageNum, pageSize, true);
		String orderByClause = (String) condition.get("orderByClause");
		if(orderByClause!=null) {
			example.setOrderByClause(orderByClause);
		}
		List<DeptConfig> list = deptConfigDao.selectByExample(example);
		PageInfo<DeptConfig> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public DeptConfig selectFirstByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		String orderByClause = (String) condition.get("orderByClause");
		if(orderByClause!=null) {
			example.setOrderByClause(orderByClause);
		}
		PageHelper.startPage(1, 1, true);
		List<DeptConfig> list = deptConfigDao.selectByExample(example);
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}else {
			return list.get(0);
		}
		
	}

	@Override
	@Cacheable(value = CACHE_NAME, key = "#id", sync = true)
	public DeptConfig selectByPrimaryKey(String id) throws Exception {
		return deptConfigDao.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value = CACHE_NAME, allEntries = true)// 清空  缓存
	public int updateByCondition(DeptConfig record, HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return deptConfigDao.updateByExample(record, example);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value = CACHE_NAME, key = "#record.id") // 清除 缓存
	public int updateByPrimaryKey(DeptConfig record) throws Exception {
		return deptConfigDao.updateByPrimaryKeySelective(record);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByPrimaryKeys(List<String> ids) throws Exception {
		Example example =new Example(DeptConfig.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("id", ids);
		Cache cache = SpringContextUtils.getCache(CACHE_NAME);
		ids.forEach(cache::evict);
		return deptConfigDao.deleteByExample(example);
	}

	private Example createExample(HashMap<String, Object> condition) {
		Example example = new Example(DeptConfig.class);
		Criteria criteria = example.createCriteria();
		if (condition != null) {
			if (condition.get("id") != null && !"".equals(condition.get("id"))) {
				criteria.andEqualTo("id", condition.get("id"));
			}
		}
		return example;
	}

	@Override
	public DeptConfig findByDeptId(String deptId) {
		
		DeptConfig q=new DeptConfig().setDeptId(deptId);
		return deptConfigDao.selectOne(q);
	}

	
	

}
