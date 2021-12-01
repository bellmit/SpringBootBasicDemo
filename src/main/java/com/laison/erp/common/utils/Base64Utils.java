package com.laison.erp.common.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Utils {
	/**
     * 编码字符串
     * @param str
     * @return
	 * @throws UnsupportedEncodingException 
     */
    public static String encode(String str) throws UnsupportedEncodingException{
        if(StringUtils.isEmpty(str)) 
        	return "";
        return Base64.encodeBase64String(str.getBytes()); 
    }

    /**
     * 解码字符串
     * @param str
     * @return
     */
    public static String decode(String str){
        if(StringUtils.isEmpty(str)) return "";
        return new String(Base64.decodeBase64(str));
    }
}
