package com.laison.erp.service.common.impl;

import java.util.HashMap;
import java.util.List;

import com.laison.erp.common.constants.ConfigConstant;
import com.laison.erp.common.utils.DateUtils;
import com.laison.erp.common.utils.LoginUserUtil;
import com.laison.erp.common.utils.SpringContextUtils;
import com.laison.erp.dao.common.OperateLogDao;
import com.laison.erp.model.common.OperateLog;
import com.laison.erp.service.common.OperateLogService;
import com.laison.erp.service.sys.SysDeptService;
import com.laison.erp.service.sys.SysUserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;





/**
* @author lihua 496583747@qq.com
* @date  2021-07-12 09:52:59
*/
@Service
@Log4j2
public class OperateLogServiceImpl implements OperateLogService {
	
	
    @Autowired
	private OperateLogDao operateLogDao;

    @Autowired
	private SysUserService sysUserService;

    @Autowired
	private SysDeptService sysDeptService;

	
	
	@Override
	public int countByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return  operateLogDao.selectCountByExample(example);
	}

	@Override
	@CacheEvict(value=CACHE_NAME,allEntries=true)// 清空  缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return  operateLogDao.deleteByExample(example);
	}

	@Override
	@CacheEvict(value =CACHE_NAME, key = "#id") // 清空 缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByPrimaryKey(String id) throws Exception {
		
		return  operateLogDao.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int save(OperateLog record) throws Exception {
		String res = record.getResult();
		if(res.length()>2500) {
            res = res.substring(0,2500);
            record.setResult(res);
        }
		String param = record.getParam();
		if(param.length()>2500) {
			param = param.substring(0,2500);
            record.setParam(param);
        }
		return operateLogDao.insertSelective(record);
	}

	@Override
	public List<OperateLog> selectAllByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		if(ConfigConstant.ISOLATE_ENABLE) {
			LoginUserUtil.addIsolate(example);
		}
		String orderByClause = (String) condition.get("orderByClause");
		if(orderByClause!=null) {
			example.setOrderByClause(orderByClause);
		}
		return operateLogDao.selectByExample(example);
	}

	@Override
	public PageInfo<OperateLog> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
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
		List<OperateLog> list = operateLogDao.selectByExample(example);
		setPartInfo(list);
		PageInfo<OperateLog> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public OperateLog selectFirstByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		String orderByClause = (String) condition.get("orderByClause");
		if(orderByClause!=null) {
			example.setOrderByClause(orderByClause);
		}
		PageHelper.startPage(1, 1, true);
		List<OperateLog> list = operateLogDao.selectByExample(example);
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}else {
			return list.get(0);
		}
		
	}

	@Override
	@Cacheable(value =CACHE_NAME, key = "#id", sync=true) 
	public OperateLog selectByPrimaryKey(String id) throws Exception {
		return operateLogDao.selectByPrimaryKey(id);
	}

	@Override
	@CacheEvict(value=CACHE_NAME,allEntries=true)// 清空  缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateByCondition(OperateLog record, HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return operateLogDao.updateByExample(record, example);
	}

	@Override
	@CacheEvict(value =CACHE_NAME, key = "#record.id") // 清除 缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateByPrimaryKey(OperateLog record) throws Exception {
		return operateLogDao.updateByPrimaryKeySelective(record);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByPrimaryKeys(List<String> ids) throws Exception {
		Example example =new Example(OperateLog.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("id", ids);
		Cache cache = SpringContextUtils.getCache(CACHE_NAME);
		((List<String>) ids).forEach((id) -> cache.evict(id));
		return operateLogDao.deleteByExample(example);
	}

	//遍历数据
	private void setPartInfo(List<OperateLog> list){
		if (CollectionUtils.isNotEmpty(list)){
			for (OperateLog operateLog : list){
				setUserNameAndDeptName(operateLog);
			}
		}
	}

	//向日志中添加用户信息以及部门信息
	public void setUserNameAndDeptName(OperateLog operateLog){
		if (operateLog != null){
			try {
				operateLog.setSysUser(sysUserService.selectByPrimaryKey(operateLog.getUserId()));
				operateLog.setSysDept(sysDeptService.selectByPrimaryKey(operateLog.getDeptId()));
			} catch (Exception e) {
				log.info(e);
			}
		}
	}

	private Example createExample(HashMap<String, Object> condition) {
		Example example = new Example(OperateLog.class);
		Example.Criteria criteria = example.createCriteria();
		if (condition != null) {
			if (condition.get("id") != null && !"".equals(condition.get("id"))) {
				criteria.andEqualTo("id", condition.get("id"));
			}
//			if (condition.get("delFlag") != null && !"".equals(condition.get("delFlag"))) {
//				criteria.andEqualTo("delFlag", condition.get("delFlag"));
//			}
			if (condition.get("uri") != null ) {
				criteria.andLike("uri", "%"+condition.get("uri")+"%");
			}
			if (condition.get("startTime") != null ) {
				criteria.andGreaterThanOrEqualTo("time", DateUtils.parse(condition.get("startTime")+""));
			}
			if (condition.get("endTime") != null ) {
				criteria.andLessThanOrEqualTo("time", DateUtils.parse(condition.get("endTime")+""));
			}
			if (condition.get("userId") != null && !"".equals(condition.get("userId"))) {
				criteria.andEqualTo("userId", condition.get("userId"));
			}
		}
		return example;
	}

	
	

}
