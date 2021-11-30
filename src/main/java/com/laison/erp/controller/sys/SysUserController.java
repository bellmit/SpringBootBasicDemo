package com.laison.erp.controller.sys;


import com.github.pagehelper.PageInfo;
import com.laison.erp.common.Result;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.ParamUtils;
import com.laison.erp.common.validatgroup.AddGroup;
import com.laison.erp.common.validatgroup.UpdateGroup;
import com.laison.erp.model.sys.SysUser;
import com.laison.erp.service.sys.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihua 496583747@qq.com
 * @date 2021-02-01 16:49:38
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping("/findById/{id}")
    @ApiOperation(value = "根据id查找", notes = "/sysUser/findById/1")
    public SysUser findById(@PathVariable String id) throws Exception {
        SysUser sysUser = sysUserService.selectByPrimaryKey(id);
        sysUser.setPassword(null);
        sysUser.getSysDept().setChildren(null);
        sysUser.setSubDeptIds(null);
        sysUser.setSysDeptIds(null);
        return sysUser;
    }

    @GetMapping("/findUserNameById/{id}")
    @ApiOperation(value = "根据id查找", notes = "/sysUser/findById/1")
    public String findUserNameById(@PathVariable String id) throws Exception {
        SysUser sysUser = sysUserService.selectByPrimaryKey(id);
        if (sysUser != null) {
            return sysUser.getUsername();
        }
        return null;
    }

    @PostMapping("/findAll")
    @ApiOperation(value = "根据条件查找所有", notes = "条件 {'属性':'value'}")
    public List<SysUser> findAll(@RequestBody HashMap<String, Object> condition) throws Exception {
        List<SysUser> list = sysUserService.selectAllByCondition(condition);
        return list;
    }


    @PostMapping("/findPage/{page}/{size}")
    @ApiOperation(value = "根据条件分页查找", notes = "条件 {'属性':'value'}")
    public PageInfo<SysUser> findPage(@RequestBody HashMap<String, Object> condition, @PathVariable int page, @PathVariable int size) throws Exception {
        PageInfo<SysUser> pageInfo = sysUserService.selectPageByCondition(condition, page, size);
        return pageInfo;
    }

    @PostMapping("/findMaintainers")
    @ApiOperation(value = "查找维修员列表", notes = "查找维修员列表")
    public List<SysUser> findMaintainers() throws Exception {
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("type", SysUser.MAINTAINER_TYPE);
        List<SysUser> list = sysUserService.selectAllByCondition(condition);
        return list;
    }
    /**
     * 新增用户
     *
     * @param sysUser
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加sysUser", notes = "添加sysUser")
    @PreAuthorize("hasAuthority('sysUser:add')")
    public Result add(@RequestBody @Validated(value = {AddGroup.class}) SysUser sysUser) throws Exception {
        sysUserService.save(sysUser);
        Result ok = Result.ok(ContentConstant.ADD_SUCCESS);
        ok.put("record", sysUser);
        return ok;
    }

    /**
     * 判断用户名是否存在
     */
    @GetMapping("/usernameExist/{username}")
    @ApiOperation(value = "判断用户名已经存在", notes = "")
    public Result usernameExist(@PathVariable String username) throws Exception {
        boolean exist = sysUserService.usernameExist(username);
        Result ok = Result.ok();
        ok.put("exist", exist);
        return ok;
    }

    /**
     * 判断用户基本信息是否存在
     */
    @PostMapping("/userBaseInfoExist")
    @ApiOperation(value = "判断用户基本信息是否存在", notes = "只能查询一个 如电话，用户名，手机，邮箱等")
    public Result userBaseInfoExist(@RequestBody Map<String, Object> condition) throws Exception {
        return sysUserService.userBaseInfoExist(condition);
    }


    @PostMapping("/update")
    @ApiOperation(value = "更新sysUser", notes = "更新sysUser")
    @PreAuthorize("hasAuthority('sysUser:update')")
    public Result update(@RequestBody @Validated(value = {UpdateGroup.class}) SysUser sysUser) throws Exception {
        sysUserService.updateByPrimaryKey(sysUser);
        Result ok = Result.ok(ContentConstant.ADD_SUCCESS);
        ok.put("record", sysUser);
        return ok;
    }
    
	@PostMapping("/changeStatus")
	@ApiOperation(value = "更新基本信息", notes = "")
	@PreAuthorize("hasAuthority('sysUser:update')")
	public Result changeStatus(@RequestBody @Validated(value = {UpdateGroup.class}) SysUser sysUser) throws Exception {
		SysUser oldSysUser = sysUserService.selectByPrimaryKey(sysUser.getId());
		SysUser update = new SysUser().setId(sysUser.getId()).setStatus(sysUser.getStatus());
		sysUserService.updateByPrimaryKey(update);
		Result ok = Result.ok(ContentConstant.UPDATE_SUCCESS);
		return ok;
	}

    @DeleteMapping("/deleteById/{id}")
    @ApiOperation(value = "根据主键删除sysUser", notes = "根据主键删除sysUser ")
    @PreAuthorize("hasAuthority('sysUser:delete')")
    public Result deleteById(@PathVariable String id) throws Exception {
        SysUser delete = new SysUser().setId(id).setDelFlag(true);
        sysUserService.updateByPrimaryKey(delete);
        Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
        return ok;
    }

    @DeleteMapping("/realDeleteById/{id}")
    @ApiOperation(value = "根据主键删除sysUser", notes = "根据主键删除sysUser ")
    @PreAuthorize("hasAuthority('sysUser:delete')")
    public Result realDeleteById(@PathVariable String id) throws Exception {
        int count = sysUserService.deleteByPrimaryKey(id);
        Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
        ok.put("count", count);
        return ok;
    }

    @PostMapping("/deleteByIds")
    @ApiOperation(value = "根据主键删除sysUser", notes = "根据主键删除sysUser [111,222,333]")
    @PreAuthorize("hasAuthority('sysUser:delete')")
    public Result deleteByIds(@RequestBody List<String> ids) throws Exception {
        int count = sysUserService.deleteByPrimaryKeys(ids);
        Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
        ok.put("count", count);
        return ok;
    }

    @PostMapping("/resetPassword/{id}/{password}")
    @ApiOperation(value = "重置密码", notes = "id被更新用户的id  需要权限sysUser:resetPassword ")
//    @PreAuthorize("hasAuthority('sysUser:resetPassword')")
    public Result resetPassword(@PathVariable String id, @PathVariable String password) throws Exception {
        sysUserService.resetPassword(id, password);
        Result ok = Result.ok(ContentConstant.OPERATE_SUCCESS);
        return ok;
    }

    @PutMapping("/changePassword/{oldpass}/{newpass}")
    @ApiOperation(value = "更改当前登录用户的密码", notes = " ")
    public @ResponseBody
    Result changePassword(@PathVariable String oldpass, @PathVariable String newpass) throws Exception {
        sysUserService.changePassword(oldpass, newpass);
        return Result.ok(ContentConstant.OPERATE_SUCCESS);
    }

    @PostMapping("/vendor/accountRecharge")
    @ApiOperation(value = "Vendor账户充值", notes = "先获取Vendor列表，选择一个，执行此接口")
    @PreAuthorize("hasAuthority('sysUser:accountRecharge')")
    public Result accountRecharge(@RequestBody Map<String, Object> param) throws Exception {
        Boolean rechargeResult = sysUserService.accountRecharge(param);
        Result ok = Result.ok(ContentConstant.OPERATE_SUCCESS);
        return ok;
    }

    @GetMapping("/listUsersInSameDept")
    @ApiOperation(value = "获取当前登录用户总部门下所有的用户信息", notes = "获取当前登录用户总部门下所有的用户信息")
    public Result listUsersInSameDept(HttpServletRequest request) throws Exception {
        return sysUserService.listUsersInSameDept(request);
    }

    @GetMapping("/queryUserByDepId")
    @ApiOperation(value = "获取当前登录用户对应部门下的用户信息", notes = "获取当前登录用户对应部门下的用户信息")
    public Result queryUserByDepId(String id) throws Exception {
        return sysUserService.queryUserByDepId(id);
    }

    @PostMapping("/changeUserPwd")
    @ApiOperation(value = "更改当前登录用户的密码", notes = " ")
    public Result changeUserPwd(@RequestBody Map<String, Object> condition) throws Exception {
        String oldpass = ParamUtils.getStringWE(condition, "oldpass");
        String newpass = ParamUtils.getStringWE(condition, "newpass");
        sysUserService.changePassword(oldpass, newpass);
        return Result.ok(ContentConstant.OPERATE_SUCCESS);
    }

    @PostMapping("/changeUserAvatar")
    @ApiOperation(value = "更改当前登录用户的头像", notes = " ")
    public Result changeUserAvatar(@Param("filePath") String filePath) throws Exception {
        sysUserService.changeUserAvatar(filePath);
        return Result.ok(ContentConstant.OPERATE_SUCCESS);
    }

}
