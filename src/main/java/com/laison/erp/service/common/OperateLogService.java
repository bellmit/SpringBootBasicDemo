package com.laison.erp.service.common;
import java.util.HashMap;
import java.util.List;
import com.github.pagehelper.PageInfo;
import com.laison.erp.model.common.OperateLog;
import com.laison.erp.service.BaseService;


/**
 * 
 * @ClassName: OperateLogService 
 * @Description: 实现类 OperateLogServiceImpl
 * @author lihua
 * @date  2021-07-12 09:52:59
 */
public interface OperateLogService extends BaseService<OperateLog,String> {
	
    /**
	 *  CACHE_NAME     OperateLog
	 */
	public final static String CACHE_NAME = "OperateLog";
	
	/**
	 * 查询符合条件的 OperateLog的个数
	 * @param  condition 条件
	 * @return int count
	 * @throws Exception
	 */
	int countByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 删除符合条件的 OperateLog
	 * @param  condition 条件
	 * @return 成功删除的个数
	 * @throws Exception
	 */
	int deleteByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键删除 OperateLog
	 * @param  id
	 * @return 成功删除的个数
	 * @throws Exception
	 */
	int deleteByPrimaryKey(String id) throws Exception;

	/**
	 * 保存 OperateLog
	 * @param  record
	 * @return  成功保存的个数
	 * @throws Exception
	 */
	int save(OperateLog record) throws Exception;

	/**
	 * 查询所有符合条件的  OperateLog
	 * @param  condition 条件
	 * @return  List<OperateLog>
	 * @throws Exception
	 */
	List<OperateLog> selectAllByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 分页查询所有符合条件的  OperateLog
	 * @param  condition 条件 Integer pageNum 从1开始, Integer pageSize
	 * @return  PageInfo<OperateLog>
	 * @throws Exception
	 */
	PageInfo<OperateLog> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
			throws Exception;

	/**
	 * 查询所有符合条件的  第一个OperateLog
	 * @param  condition 条件
	 * @return  OperateLog  record
	 * @throws Exception
	 */
	OperateLog selectFirstByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键查询 OperateLog
	 * @param  id
	 * @return  OperateLog  record
	 * @throws Exception
	 */
	OperateLog selectByPrimaryKey(String id) throws Exception;

	/**
	 * 将所有符合条件的OperateLog 更新为OperateLog  record
	 * @return  OperateLog  record
	 * @throws Exception
	 */
	int updateByCondition(OperateLog record, HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键更新 OperateLog
	 * @param  record
	 * @return  更新成功的个数
	 * @throws Exception
	 */
	int updateByPrimaryKey(OperateLog record) throws Exception;

	
	/**
	 * 删除所有主键在List<String> ids的记录
	 * @param  ids
	 * @return  更新成功的个数
	 * @throws Exception
	 */
	int deleteByPrimaryKeys(List<String> ids) throws Exception;

}
