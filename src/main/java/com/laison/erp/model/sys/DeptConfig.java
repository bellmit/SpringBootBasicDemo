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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * @author lihua
 * @date  2021-02-03 11:19:56
 * 从 dept_config 表 自动生成的entity.
 */
@Table(name="dept_config")
@JsonInclude(JsonInclude.Include.NON_NULL) 
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class DeptConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     *   
     */
	@Id
	@KeySql(genId = UUIdGenId.class)
	@Null(groups = {AddGroup.class }, message = ContentConstant.ID_NOT_NULL)
	@NotNull(groups = {UpdateGroup.class,FindGroup.class }, message = ContentConstant.ID_NULL)
    @ApiModelProperty(value="  ")
	private String id;
	
	/**
     *   
     */
    @ApiModelProperty(value="  ")
	private String name;
	
	/**
     *   
     */
    @ApiModelProperty(value="  ")
	private String configJson;
	
	/**
     *   
     */
    @ApiModelProperty(value="  ")
	private String deptId;
    
    
	


	
}