package com.laison.erp.model.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc
 * @Author sdp
 * @Date 2020/8/6 19:26
 */
@Data
public class UserRoleView implements Serializable {
    /**
     * 编号
     */
    private String id;
    /**
     * 登录的用户名
     */
    private String username;
    /**
     * 花名昵称
     */
    private String realname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 性别
     */
    private Integer sex;

    /**
     * 邮箱email
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 工号
     */
    private String workNo;
    /**
     * 电话
     */
    private String telephoto;
    /**
     * 电话
     */
    private String language;

    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;
}
