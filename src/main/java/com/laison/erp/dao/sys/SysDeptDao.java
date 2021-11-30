package com.laison.erp.dao.sys;

import com.laison.erp.common.utils.MybatisExtendedLanguageDriver;
import com.laison.erp.model.sys.Member;
import com.laison.erp.model.sys.Structure;
import com.laison.erp.model.sys.SysDept;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihua  496583747@qq.com
 * @date 2021-02-01 17:05:46
 */
@org.apache.ibatis.annotations.Mapper
public interface SysDeptDao extends Mapper<SysDept> {
    @Select("select id from sys_dept where paths like '%,${_parameter},%'")
    ArrayList<String> findSubDeptIds(String pdeptid);

    @Select("select id from sys_dept where parent_id =#{_parameter} or id =#{_parameter}")
    ArrayList<String> findSonDeptIds(String pdeptid);

    @Select("SELECT count(1) from sys_user  where dept_id =#{deptId}")
    int countUserNumber(@Param("deptId") String deptId);

    // TODO 改成真正的查询客户数量
    @Select("SELECT count(1) from sys_user  where dept_id =#{deptId}")
    int countCustomerNumber(@Param("deptId") String deptId);

    @Select("select * from sys_dept where parent_id = #{id}")
    List<SysDept> loadSubDepts(String id);

    @Select("select id,parent_id,name,sort,remarks,type,state,level,address from sys_dept t where t.id = #{deptId}")
    Structure selectStructureByPrimaryKey(@Param("deptId") String deptId);


    @Select("select sys_user.username ,sys_user.realname ,sys_role.`name`  rolename\r\n" +
            "from sys_user  \r\n" +
            "LEFT JOIN sys_user_role on sys_user.id=sys_user_role.user_id\r\n" +
            "LEFT JOIN sys_role on sys_role.id=sys_user_role.role_id\r\n" +
            "where sys_user.dept_id =  #{deptId}")
    List<Member> selectStructureMember(@Param("deptId") String deptId);

    @Select("select id,parent_id,name,sort,remarks,type,state,level,address from sys_dept t where t.parent_id = #{parentId}")
    List<Structure> selectSubStructuresByParentId(@Param("parentId") String parentId);

    @Select("SELECT COUNT(1) FROM sys_dept WHERE region_code = #{regionId}")
    int countByRegion(@Param("regionId") String regionId);

    @Select("SELECT `name` AS deptName FROM sys_dept WHERE id = #{deptId}")
    String queryDeptName(@Param("deptId") String deptId);

    @Select("SELECT * \n" +
            "FROM sys_dept\n" +
            "WHERE parent_id = #{parentId}")
    List<SysDept> selectDeptsByParentId(@Param("parentId") String parentId);


    SysDept getDepartmentsByParentId(@Param("pid") String pid);

    /**
     * 查询该部门下是否存在该名称的部门
     *
     * @param deptname
     * @param deptId
     * @return
     */
    @Select("SELECT count(1)>0 from sys_dept where (paths like '%,${deptId},%' or id ='0') and name =#{deptname}")
    boolean deptnameExist(@Param("deptname") String deptname, @Param("deptId") String deptId);

    /**
     * 获取简要部门信息
     */
    @Select("SELECT id,name,parent_id,address,sort,remarks,state,del_flag,has_child,update_time FROM sys_dept WHERE id = #{id} LIMIT 1")
    SysDept selectBrief(@Param("id") String id);

    @Select("SELECT id,name,parent_id,address,sort,remarks,state,del_flag,has_child,update_time FROM sys_dept WHERE parent_id = #{parentId}")
    List<SysDept> selectBriefDeptsByParentId(@Param("parentId") String parentId);

    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT manager_id FROM sys_dept WHERE id IN (#{ids}) AND del_flag = 0")
    List<String> findDeptLeaderIds(@Param("ids")List<String> ids);
}