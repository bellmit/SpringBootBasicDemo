package com.laison.erp.model.sys;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.SpringContextUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lihua
 * @date 2021-02-01 17:05:46
 * 从 sys_dept 表 自动生成的entity.
 */
@Table(name = "sys_dept")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
@Log4j2
public class SysDept implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @Id
    @KeySql(genId = UUIdGenId.class)
    @Null(groups = {AddGroup.class}, message = ContentConstant.ID_NOT_NULL)
    @NotNull(groups = {UpdateGroup.class, FindGroup.class}, message = ContentConstant.ID_NULL)
    @ApiModelProperty(value = "部门id  ")
    private String id;

    /**
     * 直接父id
     */
    @ApiModelProperty(value = "直接父id  ")
    private String parentId;

    /**
     * 路径
     */
    @ApiModelProperty(value = "路径  ")
    private String paths;

    private String managerId;//管理员的id
    @Transient
    private String managerName;//管理员的realname
    /**
     * 部门名字
     */
    @ApiModelProperty(value = "部门名字  ")
    private String name;

    /**
     * 层级
     */
    @ApiModelProperty(value = "层级  ")
    private Integer level;

    /**
     * 部门在父部门的排序
     */
    @ApiModelProperty(value = "部门在父部门的排序  ")
    private Integer sort;

    /**
     * 区域id
     */
    @ApiModelProperty(value = "区域id  ")
    private String regionCode = "0";

    @ApiModelProperty(value = "更新时间 ")
    private Date updateTime;

    public static Integer COMPANY = 1;
    public static Integer SUB_COMPANY = 2;
    public static Integer STATION = 8;
    public static Integer AGENCY = 16;

    /**
     * 类型  1-公司 2-子公司  营业厅=3 维修部=4
     */
    @ApiModelProperty(value = "类型  1-公司 2-子公司 4-第三方 营业厅=8 代理=16 维修=32 其他=1048576 ")
    private Integer type;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态  ")
    private Integer state;
    private Integer compID;

    /**
     * 删除状态(0-正常,1-已删除)
     */
    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)  ")
    private Boolean delFlag;


    private Boolean hasChild;

    /**
     * 部门所在地址
     */
    @ApiModelProperty(value = "部门所在地址  ")
    private String address;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注  ")
    private String remarks;

    @ApiModelProperty(value = "部门配置参数  ")
    @Transient
    private Config config;
    @Transient
    private List<SysDept> children;

    @Transient
    private SysUser deptManager;

    public List<SysDept> getChildren() {
        if (this.hasChild != null && this.hasChild && this.children == null) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
        try {
            SysUser sysUser = SpringContextUtils.getBean(SysUserService.class).selectBriefByPrimaryKey(managerId);
            if (sysUser != null) {
                this.managerName = sysUser.getRealname();
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void main(String[] args) {
        ArrayList a ;
        LinkedList d;
    }

}