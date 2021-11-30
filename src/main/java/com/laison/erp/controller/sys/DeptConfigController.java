package com.laison.erp.controller.sys;


import com.github.pagehelper.PageInfo;
import com.laison.erp.common.Result;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.validatgroup.AddGroup;
import com.laison.erp.common.validatgroup.UpdateGroup;
import com.laison.erp.model.sys.DeptConfig;
import com.laison.erp.service.sys.DeptConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
* @author lihua 496583747@qq.com
* @date  2021-02-03 11:19:56
*/
@RestController
@RequestMapping("/deptConfig")
public class DeptConfigController {

	@Autowired
	DeptConfigService deptConfigService;

	@GetMapping("/findById/{id}")
	@ApiOperation(value = "根据id查找", notes = "/deptConfig/findById/1")
	public DeptConfig findById(@PathVariable String id) throws Exception {
		DeptConfig deptConfig = deptConfigService.selectByPrimaryKey(id);
		return deptConfig;
	}

	@PostMapping("/findAll")
	@ApiOperation(value = "根据条件查找所有", notes = "条件 {'属性':'value'}")
	public List<DeptConfig> findAll(@RequestBody HashMap<String, Object> condition) throws Exception {
		List<DeptConfig> list = deptConfigService.selectAllByCondition(condition);
		return list;
	}
	
	
	@PostMapping("/findPage/{page}/{size}")
	@ApiOperation(value = "根据条件分页查找", notes = "条件 {'属性':'value'}")
	public PageInfo<DeptConfig> findPage(@RequestBody HashMap<String, Object> condition,@PathVariable  int page, @PathVariable  int size) throws Exception {
		PageInfo<DeptConfig> pageInfo = deptConfigService.selectPageByCondition(condition, page, size);
		return pageInfo;
	}
	
	@PostMapping("/add")
	@ApiOperation(value = "添加deptConfig", notes = "添加deptConfig")
	@PreAuthorize("hasAuthority('deptConfig:add')")
	public Result add(@RequestBody @Validated(value = { AddGroup.class }) DeptConfig deptConfig) throws Exception {
		deptConfigService.save(deptConfig);
		Result ok = Result.ok(ContentConstant.ADD_SUCCESS);
		ok.put("record", deptConfig);
		return ok;
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "更新deptConfig", notes = "更新deptConfig")
	@PreAuthorize("hasAuthority('deptConfig:update')")
	public Result update(@RequestBody @Validated(value = { UpdateGroup.class }) DeptConfig deptConfig) throws Exception {
		deptConfigService.updateByPrimaryKey(deptConfig);
		Result ok = Result.ok(ContentConstant.ADD_SUCCESS);
		ok.put("record", deptConfig);
		return ok;
	}
	
	@DeleteMapping("/deleteById/{id}")
	@ApiOperation(value = "根据主键删除deptConfig", notes = "根据主键删除deptConfig ")
	@PreAuthorize("hasAuthority('deptConfig:delete')")
	public Result deleteById(@PathVariable String id) throws Exception {
		int count = deptConfigService.deleteByPrimaryKey(id);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		ok.put("count", count);
		return ok;
	}
	
	@PostMapping("/deleteByIds")
	@ApiOperation(value = "根据主键删除deptConfig", notes = "根据主键删除deptConfig [111,222,333]")
	@PreAuthorize("hasAuthority('deptConfig:delete')")
	public Result deleteByIds(@RequestBody List<String> ids) throws Exception {
		int count = deptConfigService.deleteByPrimaryKeys(ids);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		ok.put("count", count);
		return ok;
	}
	
}
