package com.laison.erp.model.sys;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.MyIDGenId;
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
 * @date  2021-03-23 18:23:58
 * 从 sequence_record 表 自动生成的entity.
 */
@Table(name="sequence_record")
@JsonInclude(JsonInclude.Include.NON_NULL) 
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class SequenceRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     *   主键
     */
	@Id
	@KeySql(genId = MyIDGenId.class)
	@Null(groups = {AddGroup.class }, message = ContentConstant.ID_NOT_NULL)
	@NotNull(groups = {UpdateGroup.class,FindGroup.class }, message = ContentConstant.ID_NULL)
    @ApiModelProperty(value="主键  ")
	private String id;
	
	
	/**
     *   前缀
     */
	/** @NotNull(groups = {AddGroup.class }, message = "前缀 不能为空") */ 
    @ApiModelProperty(value="前缀  ")
	private String sequencePrefix;
	
	
	/**
     *   8位 current no 
     */
	/** @NotNull(groups = {AddGroup.class }, message = "8位 current no  不能为空") */ 
    @ApiModelProperty(value="8位 current no   ")
	private Integer sequenceNo;
	
    public void addCurrentNo() {
		++this.sequenceNo;
		
	}


	
}