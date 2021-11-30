package com.laison.erp.model.sys;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.UUIdGenId;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lihua
 * @date  2021-02-01 11:02:08
 * 从 sys_menu 表 自动生成的entity.
 */
@Table(name="sys_menu")
@JsonInclude(JsonInclude.Include.NON_NULL) 
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class SysMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     *   主键id
     */
	@Id
	@KeySql(genId = UUIdGenId.class)
	@Null(groups = {AddGroup.class }, message = ContentConstant.ID_NOT_NULL)
	@NotNull(groups = {UpdateGroup.class,FindGroup.class }, message = ContentConstant.ID_NULL)
    @ApiModelProperty(value="主键id  ")
	private String id;
	
	/**
     *   父id
     */
    @ApiModelProperty(value="父id  ")
    @NotNull(groups = {AddGroup.class}, message = "父id不能为空")
	private String parentId; 
	
	/**
     *   菜单标题
     */
    @ApiModelProperty(value="菜单标题")
    @NotNull(groups = {AddGroup.class }, message = "菜单标题不能为空")
	private String title;
	
	/**
     *   路径
     */
    @ApiModelProperty(value="组件路径  ")
    @NotNull(groups = {AddGroup.class }, message = "组件路径不能为空")
	private String url;
	
	/**
     *   组件
     */
    @ApiModelProperty(value="组件  ")
    @NotNull(groups = {AddGroup.class }, message = "组件不能为空")
	private String component;
	
	/**
     *   组件名字
     */
    @ApiModelProperty(value="组件名字")
	private String componentName;
	
	/**
     *   一级菜单跳转地址
     */
    @ApiModelProperty(value="一级菜单跳转地址  ")
	private String redirect;
	
    public static final Integer DIR_TYPE=0;
    public static final Integer URL_TYPE=1;
    public static final Integer BUTTON_TYPE=2;
	/**
     *   菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
     */
    @ApiModelProperty(value="菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)  ")
    @NotNull(groups = {AddGroup.class }, message = "菜单类型不能为空")
	private Integer menuType;
	
	/**
     *   菜单权限编码
     */
    @ApiModelProperty(value="菜单权限编码  ")
	private String perms;
	
	/**
     *   权限策略1显示2禁用
     */
    @ApiModelProperty(value="权限策略1显示2禁用  ")
	private String permsType;
	
	/**
     *   菜单排序
     */
    @ApiModelProperty(value="菜单排序  ")
	private Integer sortNo;
	
	/**
     *   菜单图标
     */
    @ApiModelProperty(value="菜单图标  ")
	private String icon;
	
	/**
     *   是否隐藏路由: 0否,1是
     */
    @ApiModelProperty(value="是否隐藏路由: 0否,1是  ")
	private Integer hidden;
	
	/**
     *   描述
     */
    @ApiModelProperty(value="描述  ")
	private String description;
	
	/**
     *   创建人
     */
    @ApiModelProperty(value="创建人  ")
	private String createBy;
	
	/**
     *   创建时间
     */
    @ApiModelProperty(value="创建时间  ")
	private Date createTime;
	
	/**
     *   更新人
     */
    @ApiModelProperty(value="更新人  ")
	private String updateBy;
	
	/**
     *   更新时间
     */
    @ApiModelProperty(value="更新时间  ")
	private Date updateTime;
	
	
	
	/**
     *   按钮权限状态(0无效1有效)
     */
    @ApiModelProperty(value="按钮权限状态(0无效1有效)  ")
	private String status;
	
	/**
     *   外链菜单打开方式 0/内部打开 1/外部打开
     */
    @ApiModelProperty(value="外链菜单打开方式 0/内部打开 1/外部打开  ")
	private Boolean internalOrExternal;
	
	/**
     *   层级
     */
    @ApiModelProperty(value="层级  ")
	private Integer level;
	
	/**
     *   路劲
     */
    @ApiModelProperty(value="路劲  ")
	private String paths;
	
    @Transient
    private List<SysMenu> children;


    public List<SysMenu> getChildren() {
    	if(this.hasChild !=null && this.hasChild &&this.children ==null) {
    		this.children=new ArrayList<>();
    	}
		return this.children;
	}


	private Boolean hasChild;
}