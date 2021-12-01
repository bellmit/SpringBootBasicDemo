package com.laison.erp.common.utils;

import cn.hutool.core.util.HexUtil;
import org.apache.commons.lang3.ArrayUtils;

public class NumberUtil extends cn.hutool.core.util.NumberUtil{
	
	
	
	/**
	 * 字符串长度是hexLen*2
	 * @param number
	 * @param hexLen
	 * @return
	 */
	public static String integerToHexString(Integer number ,Integer... hexLen) {
		byte[] bytes = integerToByteArray( number , hexLen); 
		return HexUtil.encodeHexStr(bytes)	;	
	}
	
	public static byte[] integerToByteArray(Integer number ,Integer... hexLen) {
	
		Integer bytesLen;
		if(ArrayUtils.isEmpty(hexLen)) {
			bytesLen=4;	
		}else {
			bytesLen=hexLen[0];
		}
		byte[] bytes = new byte[bytesLen];
		for (int i = 0; i < bytes.length; i++) {
			if(i<4) {
				bytes[bytes.length-i-1]=(byte)((number>>i*8) & 0xFF);
			}else{
				bytes[bytes.length-i-1]=0;
			}
		}
		return bytes	;	
	}

	/**
	 * Integer 转成逆序的 ByteArray
	 */
	public static byte[] integerToInvertedByteArray(Integer number ,Integer... hexLen) {
		byte[] integerToByteArray = integerToByteArray(number, hexLen);
		byte[] bytes = new byte[integerToByteArray.length];
		for (int i = 0; i < integerToByteArray.length; i++) {
			bytes[bytes.length-1-i]=integerToByteArray[i];
		}
		return bytes;
	}
	
	/**
	 * 字符串长度是hexLen*2
	 * @param number
	 * @param hexLen
	 * @return
	 */
	public static String longToHexString(Long number ,Integer... hexLen) {
		byte[] bytes = longToByteArray( number , hexLen); 
		return HexUtil.encodeHexStr(bytes)	;		
	}
	
	public static byte[] longToByteArray(Long number ,Integer... hexLen) {
		Integer bytesLen;
		if(ArrayUtils.isEmpty(hexLen)) {
			bytesLen=4;	
		}else {
			bytesLen=hexLen[0];
		}
		byte[] bytes = new byte[bytesLen];
		for (int i = 0; i < bytes.length; i++) {
			if(i<8) {
				bytes[bytes.length-i-1]=(byte)((number>>i*8) & 0xFF);
			}else{
				bytes[bytes.length-i-1]=0;
			}
		}
		return bytes	;	
	}
	
	/**
	 *  long 转成逆序的 ByteArray
	 */
	public static byte[] longToInvertedByteArray(Long number ,Integer... hexLen) {
		byte[] longToByteArray = longToByteArray(number, hexLen);
		byte[] bytes = new byte[longToByteArray.length];

		for (int i = 0; i < longToByteArray.length; i++) {
			bytes[bytes.length-1-i]=longToByteArray[i];
		}
		return bytes;
	}
	
	public static Integer getSixRandom()
	{
		return (int)(Math.random()*9+1)*100000;
	}
	
}
