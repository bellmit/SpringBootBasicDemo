package com.laison.erp.service.sys;

import com.github.pagehelper.PageInfo;
import com.laison.erp.common.Result;
import com.laison.erp.model.sys.SysMenu;
import com.laison.erp.service.BaseService;

import java.util.HashMap;
import java.util.List;


/**
 * @author lihua
 * @ClassName: SysMenuService
 * @Description: 实现类 SysMenuServiceImpl
 * @date 2021-02-01 11:02:08
 */
public interface SysMenuService extends BaseService<SysMenu, String> {

    /**
     * CACHE_NAME     SysMenu
     */
    public final static String CACHE_NAME = "SysMenu";

    /**
     * 查询符合条件的 SysMenu的个数
     *
     * @return int count
     * @throws Exception
     */
    int countByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 删除符合条件的 SysMenu
     *
     * @return 成功删除的个数
     * @throws Exception
     */
    int deleteByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键删除 SysMenu
     *
     * @return 成功删除的个数
     * @throws Exception
     */
    int deleteByPrimaryKey(String id) throws Exception;

    /**
     * 保存 SysMenu
     *
     * @return 成功保存的个数
     * @throws Exception
     */
    int save(SysMenu record) throws Exception;

    /**
     * 查询所有符合条件的  SysMenu
     *
     * @return List<SysMenu>
     * @throws Exception
     */
    List<SysMenu> selectAllByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 分页查询所有符合条件的  SysMenu
     *
     * @return PageInfo<SysMenu>
     * @throws Exception
     */
    PageInfo<SysMenu> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
            throws Exception;

    /**
     * 查询所有符合条件的  第一个SysMenu
     *
     * @return SysMenu  record
     * @throws Exception
     */
    SysMenu selectFirstByCondition(HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键查询 SysMenu
     *
     * @param id
     * @return SysMenu  record
     * @throws Exception
     */
    SysMenu selectByPrimaryKey(String id) throws Exception;

    /**
     * 将所有符合条件的SysMenu 更新为SysMenu  record
     *
     * @return SysMenu  record
     * @throws Exception
     */
    int updateByCondition(SysMenu record, HashMap<String, Object> condition) throws Exception;

    /**
     * 根据主键更新 SysMenu
     *
     * @param record
     * @return 更新成功的个数
     * @throws Exception
     */
    int updateByPrimaryKey(SysMenu record) throws Exception;


    /**
     * 删除所有主键在List<String> ids的记录
     *
     * @return 更新成功的个数
     * @throws Exception
     */
    int deleteByPrimaryKeys(List<String> ids) throws Exception;

    List<String> findAllAuthoritys();

    SysMenu findUserMenus(String id, SysMenu sysMenu);

    List<String> findAllMenus();

    Result findMenuTree(String id) throws Exception;
}
