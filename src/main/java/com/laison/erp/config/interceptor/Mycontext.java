package com.laison.erp.config.interceptor;


import com.fasterxml.jackson.annotation.JsonInclude;

import com.laison.erp.model.sys.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@JsonInclude(JsonInclude.Include.NON_NULL) 
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class Mycontext {
	private SysUser sysUser;

}
