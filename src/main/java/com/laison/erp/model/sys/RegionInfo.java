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
 * @date  2021-02-24 10:06:56
 * 从 region_info 表 自动生成的entity.
 */
@Table(name="region_info")
@JsonInclude(JsonInclude.Include.NON_NULL) 
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class RegionInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     *   区域代码  就是主键
     */
	@Id
	@KeySql(genId = UUIdGenId.class)
	@Null(groups = {AddGroup.class }, message = ContentConstant.ID_NOT_NULL)
	@NotNull(groups = {UpdateGroup.class,FindGroup.class }, message = ContentConstant.ID_NULL)
    @ApiModelProperty(value="区域代码  就是主键  ")
	private String id;
	
	
	/**
     *   父亲区域代码
     */
	/** @NotNull(groups = {AddGroup.class }, message = "父亲区域代码 不能为空") */ 
    @ApiModelProperty(value="父亲区域代码  ")
	private String parentId;
	
	
	/**
     *   区域id组成的路径
     */
	/** @NotNull(groups = {AddGroup.class }, message = "区域id组成的路径 不能为空") */ 
    @ApiModelProperty(value="区域id组成的路径  ")
	private String paths;
	
	
	/**
     *   区域名字
     */
	/** @NotNull(groups = {AddGroup.class }, message = "区域名字 不能为空") */ 
    @ApiModelProperty(value="区域名字  ")
	private String regionName;
	
	
	/**
     *   区域类型 1省 2市3镇 以此类推
     */
	/** @NotNull(groups = {AddGroup.class }, message = "区域类型 1省 2市3镇 以此类推 不能为空") */ 
    @ApiModelProperty(value="区域类型 1省 2市3镇 以此类推  ")
	private Integer level;
	
	
	/**
     *   部门id
     */
	/** @NotNull(groups = {AddGroup.class }, message = "部门id 不能为空") */ 
    @ApiModelProperty(value="部门id  ")
	private String deptId;
	
	
	/**
     *   
     */
	/** @NotNull(groups = {AddGroup.class }, message = " 不能为空") */ 
    @ApiModelProperty(value="  ")
	private Boolean hasChild;
	
    private Date createTime;
    
    private Date updateTime;
	@Transient
    private List<RegionInfo> children;
	
    /** children 发给前端的时候不能为null 要发一个空数组 */ 
    public List<RegionInfo> getChildren() {
    	if(this.hasChild !=null && this.hasChild &&this.children ==null) {
    		this.children=new ArrayList<>();
    	}
		return this.children;
	};
	
	


	
}