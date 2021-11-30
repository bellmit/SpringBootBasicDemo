package com.laison.erp.service.sys;

import com.github.pagehelper.PageInfo;
import com.laison.erp.model.sys.RegionInfo;
import com.laison.erp.service.BaseService;

import java.util.HashMap;
import java.util.List;


/**
 * @author lihua
 * @ClassName: RegionInfoService
 * @Description: 实现类 RegionInfoServiceImpl
 * @date 2021-03-09 14:21:04
 */
public interface RegionInfoService extends BaseService<RegionInfo, String> {

    /**
     * CACHE_NAME     RegionInfo
     */
    public final static String CACHE_NAME = "RegionInfo";

    /**
     * 查询符合条件的 RegionInfo的个数
     *
     * @param condition 条件
     * @return int count
     * @throws Exception
     */
    int countByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 删除符合条件的 RegionInfo
     *
     * @param condition 条件
     * @return 成功删除的个数
     * @throws Exception
     */
    int deleteByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键删除 RegionInfo
     *
     * @param  id
     * @return 成功删除的个数
     * @throws Exception
     */
    int deleteByPrimaryKey(String id) throws Exception;

    /**
     * 保存 RegionInfo
     *
     * @param  record
     * @return 成功保存的个数
     * @throws Exception
     */
    int save(RegionInfo record) throws Exception;

    /**
     * 查询所有符合条件的  RegionInfo
     *
     * @param  condition 条件
     * @return List<RegionInfo>
     * @throws Exception
     */
    List<RegionInfo> selectAllByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 分页查询所有符合条件的  RegionInfo
     *
     * @param  condition 条件 Integer pageNum 从1开始, Integer pageSize
     * @return PageInfo<RegionInfo>
     * @throws Exception
     */
    PageInfo<RegionInfo> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
            throws Exception;

    /**
     * 查询所有符合条件的  第一个RegionInfo
     *
     * @param  condition 条件
     * @return RegionInfo  record
     * @throws Exception
     */
    RegionInfo selectFirstByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键查询 RegionInfo
     *
     * @param  id
     * @return RegionInfo  record
     * @throws Exception
     */
    RegionInfo selectByPrimaryKey(String id) throws Exception;

    /**
     * 将所有符合条件的RegionInfo 更新为RegionInfo  record
     *
     * @return RegionInfo  record
     * @throws Exception
     */
    int updateByCondition(RegionInfo record, HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键更新 RegionInfo
     *
     * @param  record
     * @return 更新成功的个数
     * @throws Exception
     */
    int updateByPrimaryKey(RegionInfo record) throws Exception;


    /**
     * 删除所有主键在List<String> ids的记录
     *
     * @param  ids
     * @return 更新成功的个数
     * @throws Exception
     */
    int deleteByPrimaryKeys(List<String> ids) throws Exception;

    List<String> findRegions(List<String> regionIds) throws Exception;
}
