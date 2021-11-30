package com.laison.erp.model.sys;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Structure  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;//部门id
	private String parentId;//部门id
	private String name;//部门名字
	private Integer sort;//部门在父部门的排序
	private String remarks;//备注
	private Integer type;//类型
	private Integer state;//状态
	private Integer level;//层级
	private String address;//部门所在地址	
	private List<Member> members;//成员
	private List<Structure> subStructures;//子组织

}