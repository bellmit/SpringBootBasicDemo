package com.laison.erp.common;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
* @ClassName: APPR 
* @Description: 返回的数据
* @author lihua
* @date 2017年11月6日 上午9:43:15 
*
 */
 
@JsonInclude(JsonInclude.Include.NON_NULL)  
public class AppR extends HashMap<Object,Object> {
	private static final long serialVersionUID = 1L;
	

	public AppR() {
		put("errorcode", 0);
		put("gzipcompress", 0);
	}
	
	public static AppR error() {
		AppR r = new AppR();
		r.put("errorcode", 4);
		r.put("gzipcompress", 0);
		return  r;
	}
	
	public static AppR error(String msg) {
		return error(500, msg);
	}
	public static AppR error(int code) {
		AppR r = new AppR();
		r.put("errorcode", code);
		
		return r;
	}
	
	public static AppR error(int code, String errors) {
		AppR r = new AppR();
		r.put("errorcode", code);
		r.put("errors", errors);
		return r;
	}

	public static AppR ok(String msg) {
		AppR r = new AppR();
		r.put("msg", msg);	
		
		return r;
	}
	
	public static AppR ok(Map<String, Object> map) {
		AppR r = new AppR();
		r.putAll(map);
		return r;
	}
	
	public static AppR ok() {
		return new AppR();
	}

	

	
	
}
