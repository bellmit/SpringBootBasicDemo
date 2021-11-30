package com.laison.erp.model.sys;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.laison.erp.common.constants.ConfigConstant;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.MyIDGenId;
import com.laison.erp.common.utils.StringUtils;
import com.laison.erp.common.validatgroup.AddGroup;
import com.laison.erp.common.validatgroup.FindGroup;
import com.laison.erp.common.validatgroup.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author lihua
 * @date 2021-02-01 16:49:38
 * 从 sys_user 表 自动生成的entity.
 */
@Table(name = "sys_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Integer MAINTAINER_TYPE = 4;

    /**
     * 主键id
     */
    @Id
    @KeySql(genId = MyIDGenId.class)
    @Null(groups = {AddGroup.class}, message = ContentConstant.ID_NOT_NULL)
    @NotNull(groups = {UpdateGroup.class, FindGroup.class}, message = ContentConstant.ID_NULL)
    @ApiModelProperty(value = "主键id  ")
    private String id;

    /**
     * 登录账号
     */
    @ApiModelProperty(value = "登录账号  ")
    private String username;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名  ")
    private String realname;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码  ")
    private String password;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像  ")
    private String avatar;


    /**
     * 生日
     */
    @ApiModelProperty(value = "生日  ")
    private Date birthday;

    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    @ApiModelProperty(value = "性别(0-默认未知,1-男,2-女)  ")
    private Boolean sex;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件  ")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话  ")
    private String phone;


    /**
     * 状态(1-正常,0=禁用)
     */
    @ApiModelProperty(value = "状态(1-正常,0=禁用)  ")
    private Boolean status;

    /**
     * 删除状态(0-正常,1-已删除)
     */
    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)  ")
    private Boolean delFlag;

    /**
     * 同步工作流引擎(1-同步,0-不同步)
     */
    @ApiModelProperty(value = "同步工作流引擎(1-同步,0-不同步)  ")
    private Boolean activitiSync;

    /**
     * 工号，唯一键
     */
    @ApiModelProperty(value = "工号，唯一键  ")
    private String workNo;

    /**
     * 座机号
     */
    @ApiModelProperty(value = "座机号  ")
    private String telephone;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人  ")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间  ")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人  ")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间  ")
    private Date updateTime;
    private Date nextChangePassTime;

    /**
     * 是否是部门负责人
     */
    @ApiModelProperty(value = "是否是部门负责人  ")
    private Boolean manageFlag;

    private BigDecimal balance;

    private Integer type;

    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门  ")
    private String deptId;
    @Transient
    private String roleId;
    @Transient
    private com.laison.erp.model.sys.SysRole sysRole;
    @Transient
    private com.laison.erp.model.sys.SysDept sysDept;
    @Transient
    private com.laison.erp.model.sys.SysMenu sysMenu;
    @Transient
    private String roleName;
    @Transient
    private String deptName;

    @Transient
    private List<String> sysDeptIds; //包括自己部门
    @Transient
    private List<String> subDeptIds;//只有子部门

    private String language;

    private String quickEntry;//用户自定义快捷入口
    @Transient
    private Set<String> permissions;

    /**
     *	 获取2级部门id  如果本部门大于2级  则返回 本部门id
     */
    public String getParentDeptId() {
        if (sysDept == null) return null;
        Integer isolateLevel = ConfigConstant.ISOLATE_LEVEL;
        if (sysDept.getLevel() <= isolateLevel) {
            return sysDept.getId();
        }
        String paths = sysDept.getPaths() + sysDept.getId();

        String[] pathList = paths.split(",");
        if (ConfigConstant.ISOLATE_ENABLE) {
            //获取最外层的部门信息,即公司的父id公司名称
            return pathList[ConfigConstant.ISOLATE_LEVEL + 1];
        } else {
            return pathList[2];
        }
    }

    /**
     * 获取用户要显示的名字
     */
    public String getShowName() {
        if (StringUtils.isBlank(getRealname())) {
            return getUsername();
        }
        return getRealname();
    }


}