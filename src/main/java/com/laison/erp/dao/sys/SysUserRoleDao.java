package com.laison.erp.dao.sys;

import com.laison.erp.common.utils.MybatisExtendedLanguageDriver;
import com.laison.erp.model.sys.SysUser;
import com.laison.erp.model.sys.SysUserRole;
import com.laison.erp.model.sys.UserRoleView;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author lihua  496583747@qq.com
 * @date 2021-02-01 17:10:10
 */
@org.apache.ibatis.annotations.Mapper
public interface SysUserRoleDao extends Mapper<SysUserRole> {
    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT * FROM sys_user WHERE dept_id in (#{ids}) AND del_flag = 0")
    List<SysUser> selectByDepts(@Param("ids") List<String> deptIds);

    @Select("SELECT * FROM sys_user AS su \n" +
            "LEFT JOIN \n" +
            "(SELECT ur.user_id,ur.role_id,r.`name` AS role_name  FROM sys_user_role AS ur LEFT JOIN sys_role r ON ur.role_id = r.id ) AS sur \n" +
            "ON su.id = sur.user_id\n" +
            "WHERE su.id = #{userId} ")
    UserRoleView findUserRoleById(String assignee);
}