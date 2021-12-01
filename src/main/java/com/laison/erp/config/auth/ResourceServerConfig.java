package com.laison.erp.config.auth;

import com.laison.erp.common.constants.PermitAllUrl;
import com.laison.erp.config.ExceptionHandlerAdvice;
import com.laison.erp.config.auth.social.LaisonSpringSocialConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务配置
 * 
 * @author lihua
 *
 */
@EnableResourceServer
@EnableWebSecurity
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private LaisonSpringSocialConfigurer springSocialConfigurer;
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().exceptionHandling()
				.authenticationEntryPoint((request, response, authException) -> ExceptionHandlerAdvice
						.handerError(request, response, authException))
				.accessDeniedHandler((request, response, accessDeniedException) -> ExceptionHandlerAdvice
						.handerError(request, response, accessDeniedException))
				.and().apply(springSocialConfigurer).and()
				.headers().frameOptions().disable().and()
				.authorizeRequests().antMatchers(PermitAllUrl.permitAllUrl()).permitAll() // 放开权限的url ,
				.anyRequest().authenticated().and().httpBasic();

	}

	

	/**
	 * 	判断来源请求是否包含oauth2授权信息<br>
	 * 	url参数中含有access_token,或者header里有Authorization
	 */
	/*
	 * private static class OAuth2RequestedMatcher implements RequestMatcher {
	 * 
	 * @Override public boolean matches(HttpServletRequest request) { String method
	 * = request.getMethod(); System.out.println("OPTIONSMatcher："+method);
	 * if(method.equals("OPTIONS")) {
	 * System.out.println(" OAuth2RequestedMatcher return true"); return true; } //
	 * 请求参数中包含access_token参数 if
	 * (request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) != null) { return true;
	 * }
	 * 
	 * // 头部的Authorization值以Bearer开头 String auth =
	 * request.getHeader("Authorization"); if (auth != null) { return
	 * auth.startsWith(OAuth2AccessToken.BEARER_TYPE); }
	 * 
	 * return false; } }
	 */

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		
		resources
		
				.authenticationEntryPoint((request, response, authException) -> ExceptionHandlerAdvice.handerError(request, response, authException))
				.accessDeniedHandler((request, response, accessDeniedException) -> ExceptionHandlerAdvice
						.handerError(request, response, accessDeniedException));
	}

	
}