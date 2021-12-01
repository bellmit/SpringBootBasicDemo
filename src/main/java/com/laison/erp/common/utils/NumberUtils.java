package com.laison.erp.common.utils;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.NumberUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;

public class NumberUtils extends NumberUtil{
	

 
	/**
	 * 
	 *	@Description: 将byte转换成long  字节数组应该是正序的
	 *  @param bytes
	 *  @return（展示方法参数和返回值）
	 */
    public static long bytesToLong(byte[] bytes) {
    	ArrayUtils.reverse(bytes);
        return reversedBytesToLong(bytes);
    }
    
    /**
	 * 
	 *	@Description: 将逆序的byte转换成long  字节数组应该是逆序的
	 *  @param bytes
	 *  @return（展示方法参数和返回值）
	 */
    public static long reversedBytesToLong(byte[] bytes) {
    	long a=0;
    	for (int i = 0; i < bytes.length; i++) {
			a+=(bytes[i] & 0x000000ffl)<<(8*i);
		}
        return a;
    }

    /**
     * 
     *	@Description: 第一位是符号位的byte转换成long  字节数组应该是正序的
     *  @param bytes
     *  @return（展示方法参数和返回值）
     */
    public static long bytesToLongWithSignBit (byte[] bytes) {
    	ArrayUtils.reverse(bytes);
        return reversedBytesToLongWithSignBit(bytes);
    }
    /**
     * 适合jwx的表
     * 32位 byte数组 转 有符号long ByteBuffer.wrap(bytes).getInt()强转成long
     * @param bytes  一定要32位bytes
     * @return
     */
    public static long jwxBytesToLongWithSignBit (byte[] bytes) {
    	
    	return ByteBuffer.wrap(bytes).getInt();
    }
	
    /**
     * 
     *	@Description: 第一位是符号位的逆序byte转换成long  字节数组应该是逆序的
     *  @param bytes
     *  @return（展示方法参数和返回值）
     */
    public static long reversedBytesToLongWithSignBit (byte[] bytes) {
    	
    	long a=0;
    	long b=1;
    	for (int i = 0; i < bytes.length; i++) {
    		if(i==bytes.length-1) {//最后一位
    			if((bytes[i]&0x00000080l)==128l) {//符号位
    				b=-1;
    			}
    			a+=(bytes[i] & 0x0000007fl)<<(8*i);
    		}else {
    			a+=(bytes[i] & 0x000000ffl)<<(8*i);
    		}
			
		}
        return a*b;
    }
	/**
	 * 
	 *	@Description: 将byte转换成正整数
	 *  @param number
	 *  @return（展示方法参数和返回值）
	 */
	public static int byteToPositiveInt(byte number){
		return number & 0xFF;	
	}

	
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
		return bytes;	
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
}
