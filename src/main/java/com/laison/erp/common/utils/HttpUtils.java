package com.laison.erp.common.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



/**
 * 
 * @desc: 获取ip  获取request里面的东西
 * @author: 李华
 * @createTime: 2018年8月27日 
 * @version: v1.0
 */

public class HttpUtils {
	
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	/**
	 * 获取当前请求对象
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		try{
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			return requestAttributes==null?null:requestAttributes.getRequest();
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 获取IP地址
	 * 
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
    	String ip = null;
        try {
        	
        	
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
				if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
					//根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						logger.error("getIpAddress exception:", e);
					}
					ip = inet.getHostAddress();
				}
            }
        } catch (Exception e) {
        	logger.error("IPUtils ERROR ", e);
        }
        return ip;
	}
	public static byte[] getStringFromStream(HttpServletRequest req) {  
        ServletInputStream is;  
        try {  
        	
            is = req.getInputStream();  
            int nRead = 1;  
            int nTotalRead = 0;  
            byte[] bytes = new byte[10240];  
            while (nRead > 0) {  
                nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);  
                if (nRead > 0)  
                    nTotalRead = nTotalRead + nRead;  
            }  
            return bytes;  
        } catch (IOException e) {  
            e.printStackTrace();
            return null;  
        }  
    } 
	 public static String getBodyString(ServletRequest request) {
	        StringBuilder sb = new StringBuilder();
	        InputStream inputStream = null;
	        BufferedReader reader = null;
	        try {
	            inputStream = request.getInputStream();
	            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	            String line = "";
	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	            }
	        } catch (IOException e) {
	        	e.printStackTrace();
	        } finally {
	            if (inputStream != null) {
	                try {
	                    inputStream.close();
	                } catch (IOException e) {
	                	e.printStackTrace();
	                }
	            }
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                	e.printStackTrace();
	                }
	            }
	        }
	        return sb.toString();
	 }
}
