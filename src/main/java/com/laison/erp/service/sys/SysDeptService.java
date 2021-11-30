package com.laison.erp.service.sys;

import com.github.pagehelper.PageInfo;
import com.laison.erp.model.sys.Config;
import com.laison.erp.model.sys.Structure;
import com.laison.erp.model.sys.SysDepartTreeModel;
import com.laison.erp.model.sys.SysDept;
import com.laison.erp.service.BaseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author lihua
 * @ClassName: SysDeptService
 * @Description: 实现类 SysDeptServiceImpl
 * @date 2021-02-01 17:05:46
 */
public interface SysDeptService extends BaseService<SysDept, String> {

    /**
     * CACHE_NAME     SysDept
     */
    public final static String CACHE_NAME = "SysDept";

    public final static String STRUCTURE_CACHE_NAME = "Structure";

    /**
     * 查询符合条件的 SysDept的个数
     *
     * @return int count
     * @throws Exception
     */
    int countByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 删除符合条件的 SysDept
     *
     * @return 成功删除的个数
     * @throws Exception
     */
    int deleteByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键删除 SysDept
     *
     * @param  id
     * @return 成功删除的个数
     * @throws Exception
     */
    int deleteByPrimaryKey(String id) throws Exception;

    /**
     * 保存 SysDept
     *
     * @param  record
     * @return 成功保存的个数
     * @throws Exception
     */
    int save(SysDept record) throws Exception;

    /**
     * 查询所有符合条件的  SysDept
     *
     * @param  condition 条件
     * @return List<SysDept>
     * @throws Exception
     */
    List<SysDept> selectAllByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 分页查询所有符合条件的  SysDept
     *
     * @param  condition 条件 Integer pageNum 从1开始, Integer pageSize
     * @return PageInfo<SysDept>
     * @throws Exception
     */
    PageInfo<SysDept> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
            throws Exception;

    /**
     * 查询所有符合条件的  第一个SysDept
     *
     * @param condition 条件
     * @return SysDept  record
     * @throws Exception
     */
    SysDept selectFirstByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键查询 SysDept
     *
     * @param  id
     * @return SysDept  record
     * @throws Exception
     */
    SysDept selectByPrimaryKey(String id) throws Exception;

    /**
     * 将所有符合条件的SysDept 更新为SysDept  record
     *
     * @return SysDept  record
     * @throws Exception
     */
    int updateByCondition(SysDept record, HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键更新 SysDept
     *
     * @param  record
     * @return 更新成功的个数
     * @throws Exception
     */
    int updateByPrimaryKey(SysDept record) throws Exception;


    /**
     * 删除所有主键在List<String> ids的记录
     *
     * @return 更新成功的个数
     * @throws Exception
     */
    int deleteByPrimaryKeys(List<String> ids) throws Exception;

    ArrayList<String> findSubDeptIds(String deptId);

    void changeDeptConfig(Config config) throws Exception;

    Structure structure(String deptId) throws Exception;

    int countByRegion(String regionId);

    String queryDeptName(String deptId);

    List<SysDepartTreeModel> queryTreeList() throws Exception;

    SysDept queryTreeList2() throws Exception;

    /**
     * 只获取对应的下一级的子部门
     */
    List<SysDept> findNextSubDept(String deptId);

    List<String> findDeptIds(String deptId, Integer isolationLevel) throws Exception;

	boolean deptnameExist(String name) throws Exception;

    List<String> findDeptLeaderIds(List<String> deptIds);
}
