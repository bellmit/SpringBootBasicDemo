package com.laison.erp.model.sys;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.SpringContextUtils;
import com.laison.erp.common.utils.StringUtils;
import com.laison.erp.common.utils.UUIdGenId;
import com.laison.erp.common.validatgroup.AddGroup;
import com.laison.erp.common.validatgroup.FindGroup;
import com.laison.erp.common.validatgroup.UpdateGroup;
import com.laison.erp.service.sys.SysUserService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lihua
 * @date 2021-02-01 17:04:47
 * 从 sys_role 表 自动生成的entity.
 */
@Table(name = "sys_role")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
@Log4j2
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Id
    @KeySql(genId = UUIdGenId.class)
    @Null(groups = {AddGroup.class}, message = ContentConstant.ID_NOT_NULL)
    @NotNull(groups = {UpdateGroup.class, FindGroup.class}, message = ContentConstant.ID_NULL)
    @ApiModelProperty(value = "编号  ")
    private String id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称  ")
    @NotNull(groups = {AddGroup.class}, message = "角色名称 不能为空")
    private String name;

    /**
     * 如果有特殊角色硬编码用的这个字段，慢慢加
     */
    @ApiModelProperty(value = "如果有特殊角色硬编码用的这个字段，慢慢加  ")
    private Integer type;

    /**
     * 状态 0不启用 1启用
     */
    @ApiModelProperty(value = "状态 0不启用 1启用  ")
    private Integer state;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者  ")
    private String createBy;

    @Transient
    private String creator;

    public SysRole setCreateBy(String createBy) {
        this.createBy = createBy;
        try {
            if (StringUtils.isNotBlank(this.createBy)) {
                SysUser sysUser = SpringContextUtils.getBean(SysUserService.class).selectBriefByPrimaryKey(createBy);
                if (sysUser != null) {
                    this.creator = sysUser.getRealname();
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return this;
    }

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间  ")
    private Date createDate;

    @ApiModelProperty(value = "创建时间  ")
    private Date updateTime;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息  ")
    private String remarks;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id  ")
    private String deptId;
    /**
     * 部门名称
     */
    @Transient
    private String deptName;

    @ApiModelProperty(value = "角色的菜单  ")
    @Transient
    private List<String> menuIdList;


}