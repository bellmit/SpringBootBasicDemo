package com.laison.erp.controller.sys;


import com.github.pagehelper.PageInfo;
import com.laison.erp.common.Result;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.LoginUserUtil;
import com.laison.erp.common.validatgroup.AddGroup;
import com.laison.erp.common.validatgroup.UpdateGroup;
import com.laison.erp.model.sys.RegionInfo;
import com.laison.erp.service.sys.RegionInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
* @author lihua 496583747@qq.com
* @date  2021-02-24 10:06:56
*/
@RestController
@RequestMapping("/regionInfo")
public class RegionInfoController {

	@Autowired
	RegionInfoService regionInfoService;

	@GetMapping("/findById/{id}")
	@ApiOperation(value = "根据id查找", notes = "/regionInfo/findById/1")
	public RegionInfo findById(@PathVariable String id) throws Exception {
		RegionInfo regionInfo = regionInfoService.selectByPrimaryKey(id);
		List<RegionInfo> childs = regionInfo.getChildren();
		Iterator<RegionInfo> iterator = childs.iterator();
		List<String> sysDeptIds = LoginUserUtil.getSysUser().getSysDeptIds();
		while(iterator.hasNext()){
			RegionInfo child = iterator.next();
			if(!sysDeptIds.contains(child.getDeptId())){
				iterator.remove();
			}

		}
		

		return regionInfo;
	}

	@PostMapping("/findAll")
	@ApiOperation(value = "根据条件查找所有", notes = "条件 {'属性':'value'}")
	public List<RegionInfo> findAll(@RequestBody HashMap<String, Object> condition) throws Exception {
		List<RegionInfo> list = regionInfoService.selectAllByCondition(condition);
		return list;
	}
	
	
	@PostMapping("/findPage/{page}/{size}")
	@ApiOperation(value = "根据条件分页查找", notes = "条件 {'属性':'value'}")
	public PageInfo<RegionInfo> findPage(@RequestBody HashMap<String, Object> condition,@PathVariable  int page, @PathVariable  int size) throws Exception {
		PageInfo<RegionInfo> pageInfo = regionInfoService.selectPageByCondition(condition, page, size);
		return pageInfo;
	}
	
	@PostMapping("/add")
	@ApiOperation(value = "添加regionInfo", notes = "添加regionInfo")
	@PreAuthorize("hasAuthority('regionInfo:add')")
	public Result add(@RequestBody @Validated(value = { AddGroup.class }) RegionInfo regionInfo) throws Exception {
		regionInfoService.save(regionInfo);
		Result ok = Result.ok(ContentConstant.ADD_SUCCESS);
		ok.put("record", regionInfo);
		return ok;
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "更新regionInfo", notes = "更新regionInfo")
	@PreAuthorize("hasAuthority('regionInfo:update')")
	public Result update(@RequestBody @Validated(value = { UpdateGroup.class }) RegionInfo regionInfo) throws Exception {
		regionInfoService.updateByPrimaryKey(regionInfo);
		Result ok = Result.ok(ContentConstant.UPDATE_SUCCESS);
		ok.put("record", regionInfo);
		return ok;
	}

	/**
	 * 删除id对应的区域
	 */
	@DeleteMapping("/deleteById/{id}")
	@ApiOperation(value = "根据主键删除regionInfo", notes = "根据主键删除regionInfo ")
	@PreAuthorize("hasAuthority('regionInfo:delete')")
	public Result deleteById(@PathVariable String id) throws Exception {
		int count = regionInfoService.deleteByPrimaryKey(id);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		ok.put("count", count);
		return ok;
	}
	
	@PostMapping("/deleteByIds")
	@ApiOperation(value = "根据主键删除regionInfo", notes = "根据主键删除regionInfo [111,222,333]")
	@PreAuthorize("hasAuthority('regionInfo:delete')")
	public Result deleteByIds(@RequestBody List<String> ids) throws Exception {
		int count = regionInfoService.deleteByPrimaryKeys(ids);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		ok.put("count", count);
		return ok;
	}
	
	@PostMapping("/getChildListBatch")
	@ApiOperation(value = "批量获取child", notes = "查找所有pid的child")
	public Result getChildListBatch(@RequestBody List<String> pids) throws Exception {
		HashMap<String, Object> condition=new HashMap<>();
		condition.put("pids", pids);
		List<RegionInfo> list = regionInfoService.selectAllByCondition(condition);
		Result ok = Result.ok(ContentConstant.QUERY_SUCCESS);
		ok.put("list", list);
		return ok;
	}
}
