package com.laison.erp.model.common;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.MyIDGenId;
import com.laison.erp.common.validatgroup.AddGroup;
import com.laison.erp.common.validatgroup.FindGroup;
import com.laison.erp.common.validatgroup.UpdateGroup;
import com.laison.erp.model.sys.SysDept;
import com.laison.erp.model.sys.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import tk.mybatis.mapper.annotation.KeySql;
/**
 * @author lihua
 * @date  2021-07-12 09:52:59
 * 从 operate_log 表 自动生成的entity.
 */
@Table(name="operate_log")
@JsonInclude(JsonInclude.Include.NON_NULL) 
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class OperateLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     *   id
     */
	@Id
	@KeySql(genId = MyIDGenId.class)
	@Null(groups = {AddGroup.class }, message = ContentConstant.ID_NOT_NULL)
	@NotNull(groups = {UpdateGroup.class, FindGroup.class }, message = ContentConstant.ID_NULL)
    @ApiModelProperty(value="id  ")
	private String id;
	
	
	/**
     *   用户id
     */
	/** @NotNull(groups = {AddGroup.class }, message = "用户id 不能为空") */ 
    @ApiModelProperty(value="用户id  ")
	private String userId;
	
	
	/**
     *   部门id
     */
	/** @NotNull(groups = {AddGroup.class }, message = "部门id 不能为空") */ 
    @ApiModelProperty(value="部门id  ")
	private String deptId;
	
	
	/**
     *   客户操作时设备ip
     */
	/** @NotNull(groups = {AddGroup.class }, message = "客户操作时设备ip 不能为空") */ 
    @ApiModelProperty(value="客户操作时设备ip  ")
	private String ip;
	
	
	/**
     *   操作时间
     */
	/** @NotNull(groups = {AddGroup.class }, message = "操作时间 不能为空") */ 
    @ApiModelProperty(value="操作时间  ")
	private Date time;
	
	
	/**
     *   访问的接口
     */
	/** @NotNull(groups = {AddGroup.class }, message = "访问的接口 不能为空") */ 
    @ApiModelProperty(value="访问的接口  ")
	private String uri;
	
	
	/**
     *   参数信息
     */
	/** @NotNull(groups = {AddGroup.class }, message = "参数信息 不能为空") */ 
    @ApiModelProperty(value="参数信息  ")
	private String param;
	
	
	/**
     *   返回信息
     */
	/** @NotNull(groups = {AddGroup.class }, message = "返回信息 不能为空") */ 
    @ApiModelProperty(value="返回信息  ")
	private String result;
	
	
	/**
     *   消耗时间
     */
	/** @NotNull(groups = {AddGroup.class }, message = "消耗时间 不能为空") */ 
    @ApiModelProperty(value="消耗时间  ")
	private Long consumeTime;

	@Transient
	private SysDept sysDept;

	@Transient
	private SysUser sysUser;


	
}