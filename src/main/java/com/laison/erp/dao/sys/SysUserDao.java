package com.laison.erp.dao.sys;

import com.laison.erp.common.utils.MybatisExtendedLanguageDriver;
import com.laison.erp.model.sys.SysUser;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author lihua  496583747@qq.com
 * @date 2021-02-01 16:49:38
 */
@org.apache.ibatis.annotations.Mapper
public interface SysUserDao extends Mapper<SysUser> {

    @Select("SELECT perms from sys_menu\n" +
            "LEFT JOIN sys_role_menu on sys_menu.id = sys_role_menu.menu_id\n" +
            "LEFT JOIN sys_role on sys_role_menu.role_id = sys_role.id\n" +
            "LEFT JOIN sys_user_role on sys_role.id = sys_user_role.role_id\n" +
            "LEFT JOIN sys_user on sys_user_role.user_id = sys_user.id\n" +
            "where sys_user.id = #{id} and  perms is not null and perms !='' ")
    List<String> findUserAuthoritys(String id);

    @Select("SELECT perms from sys_menu\n" +
            "where  perms is not null and perms !=''")
    List<String> findAllAuthoritys();

    @Select("SELECT COUNT(*) from sys_user WHERE username = #{username}")
    int selectUserCountByUsername(@Param("username") String username);

    @Select("SELECT username FROM sys_user WHERE id = #{creatorId}")
    String queryUserName(@Param("creatorId") String creatorId);

    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT * FROM sys_user WHERE id IN (#{ids}) AND del_flag = 0")
    List<SysUser> selectByUserIds(@Param("ids") List<String> ids);
}