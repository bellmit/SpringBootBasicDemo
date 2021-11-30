package com.laison.erp.controller.sys;


import com.github.pagehelper.PageInfo;
import com.laison.erp.common.Result;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.validatgroup.AddGroup;
import com.laison.erp.common.validatgroup.UpdateGroup;
import com.laison.erp.model.sys.SysMenu;
import com.laison.erp.service.sys.SysMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
* @author lihua 496583747@qq.com
* @date  2021-02-01 11:02:08
*/
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {

	@Autowired
	SysMenuService sysMenuService;

	@GetMapping("/findById/{id}")
	@ApiOperation(value = "根据id查找", notes = "/sysMenu/findById/1")
	public SysMenu findById(@PathVariable String id) throws Exception {
		SysMenu sysMenu = sysMenuService.selectByPrimaryKey(id);
		return sysMenu;
	}

	@PostMapping("/findAll")
	@ApiOperation(value = "根据条件查找所有", notes = "条件 {'属性':'value'}")
	public List<SysMenu> findAll(@RequestBody HashMap<String, Object> condition) throws Exception {
		List<SysMenu> list = sysMenuService.selectAllByCondition(condition);
		return list;
	}
	
	
	@PostMapping("/findPage/{page}/{size}")
	@ApiOperation(value = "根据条件分页查找", notes = "条件 {'属性':'value'}")
	public PageInfo<SysMenu> findPage(@RequestBody HashMap<String, Object> condition,@PathVariable  int page, @PathVariable  int size) throws Exception {
		PageInfo<SysMenu> pageInfo = sysMenuService.selectPageByCondition(condition, page, size);
		return pageInfo;
	}
	
	@PostMapping("/add")
	@ApiOperation(value = "添加sysMenu", notes = "添加sysMenu")
	//@PreAuthorize("hasAuthority('sysMenu:add')")
	public Result add(@RequestBody @Validated(value = { AddGroup.class }) SysMenu sysMenu) throws Exception {
		sysMenuService.save(sysMenu);
		Result ok = Result.ok(ContentConstant.ADD_SUCCESS);
		ok.put("record", sysMenu);
		return ok;
	}
	
	@PostMapping("/getChildListBatch")
	@ApiOperation(value = "添加sysMenu", notes = "添加sysMenu")
	public Result getChildListBatch(@RequestBody List<String> pids) throws Exception {
		HashMap<String, Object> condition=new HashMap<>();
		condition.put("pids", pids);
		condition.put("orderByClause", "sort_no desc");
		List<SysMenu> list = sysMenuService.selectAllByCondition(condition);
		Result ok = Result.ok(ContentConstant.QUERY_SUCCESS);
		ok.put("list", list);
		return ok;
	}
	@PostMapping("/update")
	@ApiOperation(value = "更新sysMenu", notes = "更新sysMenu")
	//@PreAuthorize("hasAuthority('sysMenu:update')")
	public Result update(@RequestBody @Validated(value = { UpdateGroup.class }) SysMenu sysMenu) throws Exception {
		sysMenuService.updateByPrimaryKey(sysMenu);
		Result ok = Result.ok(ContentConstant.UPDATE_SUCCESS);
		ok.put("record", sysMenu);
		return ok;
	}
	
	@DeleteMapping("/deleteById/{id}")
	@ApiOperation(value = "根据主键删除sysMenu", notes = "根据主键删除sysMenu ")
	//@PreAuthorize("hasAuthority('sysMenu:delete')")
	public Result deleteById(@PathVariable String id) throws Exception {
		int count = sysMenuService.deleteByPrimaryKey(id);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		ok.put("count", count);
		return ok;
	}
	
	@PostMapping("/deleteByIds")
	@ApiOperation(value = "根据主键删除sysMenu", notes = "根据主键删除sysMenu [111,222,333]")
	//@PreAuthorize("hasAuthority('sysMenu:delete')")
	public Result deleteByIds(@RequestBody List<String> ids) throws Exception {
		int count = sysMenuService.deleteByPrimaryKeys(ids);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		ok.put("count", count);
		return ok;
	}

	@GetMapping("/findMenuTree/{id}")
	@ApiOperation(value = "获取菜单树",notes = "查询菜单树")
	public Result findMenuTree(@PathVariable String id) throws Exception {
		return sysMenuService.findMenuTree(id);
	}
	
}
