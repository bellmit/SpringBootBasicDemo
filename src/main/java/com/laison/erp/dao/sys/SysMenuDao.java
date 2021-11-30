package com.laison.erp.dao.sys;

import com.laison.erp.model.sys.SysMenu;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author lihua  496583747@qq.com
 * @date  2021-02-01 11:02:08
 */
@org.apache.ibatis.annotations.Mapper
public interface SysMenuDao extends Mapper<SysMenu>{
	@Select("select * from sys_menu where parent_id = #{id} order by sort_no desc")
	List<SysMenu> selectChildMenusByParentId(String id);
	@Select("select DISTINCT perms from sys_menu where  perms is not null and perms !=''")
	List<String> findAllAuthoritys();
	@Select("select DISTINCT title from sys_menu ")
	List<String> findAllMenus();
	@Select("SELECT DISTINCT menu_id from sys_role_menu  where role_id = (SELECT role_id from sys_user_role where user_id = #{uid} limit 0,1)")
	Set<String> findAllUserMenuIds(String uid);
}