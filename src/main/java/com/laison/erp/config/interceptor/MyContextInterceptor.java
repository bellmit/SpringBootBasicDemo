package com.laison.erp.config.interceptor;


import com.laison.erp.common.utils.JsonUtils;
import com.laison.erp.common.utils.SpringContextUtils;
import com.laison.erp.model.common.LoginAppUser;
import com.laison.erp.model.sys.SysUser;
import com.laison.erp.service.sys.SysUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class MyContextInterceptor implements HandlerInterceptor {
	
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
	    	try {
	    		LoginAppUser loginAppUser = null ;
	    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    		if (authentication instanceof OAuth2Authentication) {
	    			OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
	    			authentication = oAuth2Auth.getUserAuthentication();
	    			if (authentication instanceof UsernamePasswordAuthenticationToken) {
	    				UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
	    				Object principal = authentication.getPrincipal();
	    				if (principal instanceof LoginAppUser) {
	    					loginAppUser= (LoginAppUser) principal;
	    				}else {
	    					Map map = (Map) authenticationToken.getDetails();
		    				map = (Map) map.get("principal");
		    				loginAppUser= JsonUtils.jsonToPojo(JsonUtils.objectToJson(map), LoginAppUser.class);
	    				}
	    				
	    			}else if(authentication instanceof SocialAuthenticationToken){
	    				Object principal = authentication.getPrincipal();
	    				if (principal instanceof LoginAppUser) {
	    					loginAppUser=  (LoginAppUser) principal;
	    				}
	    			}
	    		}
	    		Mycontext context = MyContextHolder.getContext();
	    		if(loginAppUser!=null) {
	    			SysUserService sysUserService = SpringContextUtils.getBean(SysUserService.class);
	    			SysUser sysUser = sysUserService.selectByPrimaryKey(loginAppUser.getId());
	    			context.setSysUser(sysUser);
	    		}
			} catch (Exception e) {
				e.printStackTrace();
			} 
	    	
	    	return true;   
	    }

	 
	 	@Override
	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
	    	
		 MyContextHolder.clearContext();
	    }
}
