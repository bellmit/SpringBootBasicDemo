package com.laison.erp.dao.sys;

import com.laison.erp.common.utils.MybatisExtendedLanguageDriver;
import com.laison.erp.model.sys.SysRole;
import com.laison.erp.model.sys.SysUser;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author lihua  496583747@qq.com
 * @date 2021-02-01 17:04:47
 */
@org.apache.ibatis.annotations.Mapper
public interface SysRoleDao extends Mapper<SysRole> {

    @Select("select r.* from sys_user_role ru inner join sys_role r on r.id = ru.role_id where ru.user_id = #{userId}")
    List<SysRole> findRolesByUserId(String userId);

    @Select("select role_id from sys_user_role ru inner join sys_role r on r.id = ru.role_id where ru.user_id = #{userId}")
    List<String> findRoleIdsByUserId(String userId);

    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT * FROM sys_role WHERE dept_id in (#{ids})")
    List<SysRole> findRolesByDeptIds(@Param("ids") List<String> deptIds);

    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT * FROM sys_role WHERE  `name` LIKE CONCAT('%',#{name},'%') AND dept_id in (#{ids})")
    List<SysRole> findRolesByNameAndDeptIds(@Param("name") String name, @Param("ids") List<String> deptIds);

    @Select("SELECT user_id from sys_user_role where role_id = #{roleId}")
    List<String> selectUserIds(@Param("roleId") String roleId);

    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT * FROM sys_user WHERE id IN (SELECT user_id FROM sys_user_role WHERE role_id in (#{ids})) AND del_flag = 0")
    List<SysUser> selectUsersByRoleIds(@Param("ids") List<String> ids);

    @Select("SELECT * FROM sys_role WHERE  `name` LIKE CONCAT('%',#{roleName},'%') AND dept_id = #{deptId}")
    List<SysRole> selectByDeptIdAndRoleName(@Param("deptId") String deptId, @Param("roleName") String roleName);

    @Select("SELECT * from sys_role where dept_id = #{deptId}")
    List<SysRole> selectByDeptId(@Param("deptId") String deptId);

    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT * FROM sys_role WHERE id in (#{ids})")
    List<SysRole> selectRolesByIds(@Param("ids")List<String> ids);
}