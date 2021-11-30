package com.laison.erp.service.sys;

import com.github.pagehelper.PageInfo;
import com.laison.erp.model.sys.DeptConfig;
import com.laison.erp.service.BaseService;

import java.util.HashMap;
import java.util.List;


/**
 * 
 * @ClassName: DeptConfigService 
 * @Description: 实现类 DeptConfigServiceImpl
 * @author lihua
 * @date  2021-02-03 11:19:56
 */
public interface DeptConfigService extends BaseService<DeptConfig,String>{
	
    /**
	 *  CACHE_NAME     DeptConfig
	 */
	public final static String CACHE_NAME = "DeptConfig";
	
	/**
	 * 查询符合条件的 DeptConfig的个数
	 * @param  condition 条件
	 * @return int count
	 * @throws Exception
	 */
	int countByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 删除符合条件的 DeptConfig
	 * @param  condition 条件
	 * @return 成功删除的个数
	 * @throws Exception
	 */
	int deleteByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键删除 DeptConfig
	 * @return 成功删除的个数
	 * @throws Exception
	 */
	int deleteByPrimaryKey(String id) throws Exception;

	/**
	 * 保存 DeptConfig
	 * @return  成功保存的个数
	 * @throws Exception
	 */
	int save(DeptConfig record) throws Exception;

	/**
	 * 查询所有符合条件的  DeptConfig
	 * @return  List<DeptConfig>
	 * @throws Exception
	 */
	List<DeptConfig> selectAllByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 分页查询所有符合条件的  DeptConfig
	 * @return  PageInfo<DeptConfig>
	 * @throws Exception
	 */
	PageInfo<DeptConfig> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
			throws Exception;

	/**
	 * 查询所有符合条件的  第一个DeptConfig
	 * @return  DeptConfig  record
	 * @throws Exception
	 */
	DeptConfig selectFirstByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键查询 DeptConfig
	 * @return  DeptConfig  record
	 * @throws Exception
	 */
	DeptConfig selectByPrimaryKey(String id) throws Exception;

	/**
	 * 将所有符合条件的DeptConfig 更新为DeptConfig  record
	 * @return  DeptConfig  record
	 * @throws Exception
	 */
	int updateByCondition(DeptConfig record, HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键更新 DeptConfig
	 * @return  更新成功的个数
	 * @throws Exception
	 */
	int updateByPrimaryKey(DeptConfig record) throws Exception;

	
	/**
	 * 删除所有主键在List<String> ids的记录
	 * @return  更新成功的个数
	 * @throws Exception
	 */
	int deleteByPrimaryKeys(List<String> ids) throws Exception;

	DeptConfig findByDeptId(String deptId);

}
