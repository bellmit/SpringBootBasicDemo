package com.laison.erp.controller.sys;


import com.github.pagehelper.PageInfo;
import com.laison.erp.common.Result;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.CollectionUtils;
import com.laison.erp.common.validatgroup.AddGroup;
import com.laison.erp.common.validatgroup.UpdateGroup;
import com.laison.erp.model.sys.Config;
import com.laison.erp.model.sys.Structure;
import com.laison.erp.model.sys.SysDept;
import com.laison.erp.service.sys.SysDeptService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author lihua 496583747@qq.com
 * @date 2021-02-01 17:05:46
 */
@RestController
@RequestMapping("/sysDept")
public class SysDeptController {
    @Autowired
    SysDeptService sysDeptService;

    @GetMapping("/findById/{id}")
    @ApiOperation(value = "根据id查找", notes = "/sysDept/findById/1")
    public SysDept findById(@PathVariable String id) throws Exception {
        SysDept sysDept = sysDeptService.selectByPrimaryKey(id);
        return sysDept;
    }

    @PostMapping("/findAll")
    @ApiOperation(value = "根据条件查找所有", notes = "条件 {'属性':'value'}")
    public List<SysDept> findAll(@RequestBody HashMap<String, Object> condition) throws Exception {
        List<SysDept> list = sysDeptService.selectAllByCondition(condition);
        return list;
    }


    @PostMapping("/findPage/{page}/{size}")
    @ApiOperation(value = "根据条件分页查找", notes = "条件 {'属性':'value'}")
    public PageInfo<SysDept> findPage(@RequestBody HashMap<String, Object> condition, @PathVariable int page, @PathVariable int size) throws Exception {
        PageInfo<SysDept> pageInfo = sysDeptService.selectPageByCondition(condition, page, size);
        List<SysDept> list = pageInfo.getList();
        if(!CollectionUtils.isEmpty(list)){
            for(SysDept sysDept:list){
                SysDept allDept = sysDeptService.selectByPrimaryKey(sysDept.getId());
                sysDept.setConfig(allDept.getConfig());
            }
        }


        return pageInfo;
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加sysDept", notes = "添加sysDept")
    @PreAuthorize("hasAuthority('sysDept:add')")
    public Result add(@RequestBody @Validated(value = {AddGroup.class}) SysDept sysDept) throws Exception {
        sysDeptService.save(sysDept);
        Result ok = Result.ok(ContentConstant.ADD_SUCCESS);
        ok.put("record", sysDept);
        return ok;
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新sysDept", notes = "更新sysDept")
    @PreAuthorize("hasAuthority('sysDept:update')")
    public Result update(@RequestBody @Validated(value = {UpdateGroup.class}) SysDept sysDept) throws Exception {
        sysDeptService.updateByPrimaryKey(sysDept);
        Result ok = Result.ok(ContentConstant.UPDATE_SUCCESS);
        ok.put("record", sysDept);
        return ok;
    }
    
    @PostMapping("/deptnameExist")
    @ApiOperation(value = "判断部门名字是否存在已经存在", notes = "")
    public Result deptnameExist(@RequestBody SysDept sysDept) throws Exception {
    	String id = sysDept.getId();
    	String name = sysDept.getName();
    	boolean exist =false;
    	Boolean needCheckBoolean=false;
    	if(id==null) {//新增直接去查
    		needCheckBoolean=true;
    	}else {
    		SysDept oldDept = sysDeptService.selectByPrimaryKey(id);
    		if(!oldDept.getName().equals(name)) {
    			needCheckBoolean=true;
    		}
    	}
    	if(needCheckBoolean) {
    		exist =sysDeptService.deptnameExist(name);
    	}
    	
        Result ok = Result.ok();
        ok.put("exist", exist);
        return ok;
    }

    @PostMapping("/getChildListBatch")
    @ApiOperation(value = "添加sysMenu", notes = "添加sysMenu")
    public Result getChildListBatch(@RequestBody List<String> pids) throws Exception {
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("pids", pids);
        condition.put("orderByClause", "update_time desc");
        List<SysDept> list = sysDeptService.selectAllByCondition(condition);
        if(!CollectionUtils.isEmpty(list)){
            for(SysDept sysDept:list){
                SysDept allDept = sysDeptService.selectByPrimaryKey(sysDept.getId());
                sysDept.setConfig(allDept.getConfig());
            }
        }
        Result ok = Result.ok(ContentConstant.QUERY_SUCCESS);
        ok.put("list", list);
        return ok;
    }

    @DeleteMapping("/deleteById/{id}")
    @ApiOperation(value = "根据主键删除sysDept", notes = "根据主键删除sysDept ")
    @PreAuthorize("hasAuthority('sysDept:delete')")
    public Result deleteById(@PathVariable String id) throws Exception {
        int count = sysDeptService.deleteByPrimaryKey(id);
        Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
        ok.put("count", count);
        return ok;
    }

    @RequestMapping(value = "/changeDeptConfig", method = RequestMethod.POST)
    @ApiOperation(value = "更新SysDept配置", notes = "xxx ")
    @PreAuthorize("hasAuthority('sysDept:change')")
    public @ResponseBody
    Result changeDeptConfig(@RequestBody Config config) throws Exception {
        sysDeptService.changeDeptConfig(config);
        Result ok = Result.ok(ContentConstant.OPERATE_SUCCESS);
        return ok;
    }

    @PostMapping("/deleteByIds")
    @ApiOperation(value = "根据主键删除sysDept", notes = "根据主键删除sysDept [111,222,333]")
    @PreAuthorize("hasAuthority('sysDept:delete')")
    public Result deleteByIds(@RequestBody List<String> ids) throws Exception {
        int count = sysDeptService.deleteByPrimaryKeys(ids);
        Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
        ok.put("count", count);
        return ok;
    }

    @RequestMapping(value = "/structure/{deptId}", method = RequestMethod.POST)
    @ApiOperation(value = "更新SysDept配置", notes = "xxx ")
    public @ResponseBody
    Result structure(@PathVariable String deptId) throws Exception {
        Structure structure = sysDeptService.structure(deptId);
        Result ok = Result.ok(ContentConstant.OPERATE_SUCCESS);
        ok.put("structure", structure);
        return ok;
    }

    @GetMapping("/queryTreeList")
    @ApiOperation(value = "获取当前用户的部门树", notes = "获取当前用户的部门树 ")
    public Result queryTreeList() throws Exception {
        return Result.okData(sysDeptService.queryTreeList());
    }
//    @GetMapping("/queryTreeList")
//    @ApiOperation(value = "获取当前用户的部门树", notes = "获取当前用户的部门树 ")
//    public Result queryTreeList() throws Exception {
//        return Result.okData(sysDeptService.queryTreeList2());
//    }
}
