package com.laison.erp.dao.sys;

import com.laison.erp.model.sys.SysRoleMenu;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author lihua  496583747@qq.com
 * @date  2021-02-01 17:09:42
 */
@org.apache.ibatis.annotations.Mapper
public interface SysRoleMenuDao extends Mapper<SysRoleMenu>{

	@Select("SELECT menu_id from sys_role_menu where role_id =#{id}")
	List<String> selectMenuIdsByRoleId(String id);
	
}