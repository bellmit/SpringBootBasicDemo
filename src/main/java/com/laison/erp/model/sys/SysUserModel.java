package com.laison.erp.model.sys;

import com.laison.erp.common.utils.CollectionUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Desc
 * @Author sdp
 * @Date 2021/4/1 19:00
 */
@Data
public class SysUserModel {
    private Date birthday;
    private String relTenantIds;
    private Object sex_dictText;
    private int activitiSync;
    private String userIdentity;
    private String status_dictText;
    private int delFlag;
    private String workNo;
    private String post;
    private Date updateBy;
    private String orgCode;
    private String id;
    private String email;
    private String post_dictText;
    private Object clientId;
    private Integer sex;
    private Object departIds_dictText;
    private String telephone;
    private Date updateTime;
    private String departIds;
    private String avatar;
    private String realname;
    private String createBy;
    private String phone;
    private Date createTime;
    private String orgCodeTxt;
    private String username;
    private Integer status;

    public SysUserModel(SysUser sysUser) {
        if (sysUser != null) {
            this.birthday = sysUser.getBirthday();
            this.relTenantIds = "";
            this.sex_dictText = "";
            this.activitiSync = sysUser.getActivitiSync() == null ? 0 : (sysUser.getActivitiSync() ? 0 : 1);
            this.userIdentity = "";
            this.status_dictText = "";
            this.delFlag = sysUser.getDelFlag() == null ? 0 : (sysUser.getDelFlag() ? 1 : 0);
            this.workNo = sysUser.getWorkNo();
            this.post = "";
            this.updateBy = sysUser.getBirthday();
            this.orgCode = "";
            this.id = sysUser.getId();
            this.email = sysUser.getEmail();
            this.post_dictText = "";
            this.clientId = sysUser.getBirthday();
            this.sex = sysUser.getSex() == null ? 0 : (sysUser.getSex() ? 1 : 2);
            this.departIds_dictText = sysUser.getBirthday();
            this.telephone = sysUser.getTelephone();
            this.updateTime = sysUser.getBirthday();
            this.departIds = buildDeprtIds(sysUser.getSysDeptIds());
            this.avatar = sysUser.getAvatar();
            this.realname = sysUser.getRealname();
            this.createBy = sysUser.getCreateBy();
            this.phone = sysUser.getPhone();
            this.createTime = sysUser.getCreateTime();
            this.orgCodeTxt = sysUser.getSysDept() == null ? "" : sysUser.getSysDept().getName();
            this.username = sysUser.getUsername();
            this.status = sysUser.getStatus() == null ? 0 : (sysUser.getStatus() ? 0 : 1);
        }
    }

    private String buildDeprtIds(List<String> sysDeptIds) {
        if (CollectionUtils.isEmpty(sysDeptIds)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String dept : sysDeptIds) {
            sb.append(dept).append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }
}
