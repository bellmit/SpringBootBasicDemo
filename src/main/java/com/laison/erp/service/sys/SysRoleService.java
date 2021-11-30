package com.laison.erp.service.sys;

import com.github.pagehelper.PageInfo;
import com.laison.erp.common.Result;
import com.laison.erp.model.sys.SysRole;
import com.laison.erp.model.sys.SysUser;
import com.laison.erp.service.BaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lihua
 * @ClassName: SysRoleService
 * @Description: 实现类 SysRoleServiceImpl
 * @date 2021-02-01 17:04:47
 */
public interface SysRoleService extends BaseService<SysRole, String> {

    /**
     * CACHE_NAME     SysRole
     */
    public final static String CACHE_NAME = "SysRole";

    /**
     * 查询符合条件的 SysRole的个数
     *
     * @return int count
     * @throws Exception
     */
    int countByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 删除符合条件的 SysRole
     *
     * @param  condition 条件
     * @return 成功删除的个数
     * @throws Exception
     */
    int deleteByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键删除 SysRole
     *
     * @param  id
     * @return 成功删除的个数
     * @throws Exception
     */
    int deleteByPrimaryKey(String id) throws Exception;

    /**
     * 保存 SysRole
     *
     * @param  record
     * @return 成功保存的个数
     * @throws Exception
     */
    int save(SysRole record) throws Exception;

    /**
     * 查询所有符合条件的  SysRole
     *
     * @param  condition 条件
     * @return List<SysRole>
     * @throws Exception
     */
    List<SysRole> selectAllByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 分页查询所有符合条件的  SysRole
     *
     * @param  condition 条件 Integer pageNum 从1开始, Integer pageSize
     * @return PageInfo<SysRole>
     * @throws Exception
     */
    PageInfo<SysRole> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
            throws Exception;

    /**
     * 查询所有符合条件的  第一个SysRole
     *
     * @param  condition 条件
     * @return SysRole  record
     * @throws Exception
     */
    SysRole selectFirstByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键查询 SysRole
     *
     * @param  id
     * @return SysRole  record
     * @throws Exception
     */
    SysRole selectByPrimaryKey(String id) throws Exception;

    /**
     * 将所有符合条件的SysRole 更新为SysRole  record
     *
     * @param  record
     * @throws Exception
     */
    int updateByCondition(SysRole record, HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键更新 SysRole
     *
     * @param  record
     * @return 更新成功的个数
     * @throws Exception
     */
    int updateByPrimaryKey(SysRole record) throws Exception;


    /**
     * 删除所有主键在List<String> ids的记录
     *
     * @param  ids
     * @return 更新成功的个数
     * @throws Exception
     */
    int deleteByPrimaryKeys(List<String> ids) throws Exception;

    List<SysRole> findRolesByUserId(String id);

    Result selectRolesByDept(Map<String, Object> condition);
    Result selectRolesByIds(List<String> ids);

    List<String> findRoleIdsByUserId(String id);

    Result listDeptRole(Map<String, Object> pageInfo) throws Exception;

    /**
     * 根据角色id 返回使用这个角色的用户ids
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    List<String> selectUserIds(String roleId) throws Exception;

    List<SysUser> selectUsersByRoleIds(List<String> roleIds);

}
