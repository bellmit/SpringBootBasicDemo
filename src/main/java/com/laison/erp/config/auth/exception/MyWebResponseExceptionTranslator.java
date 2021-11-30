package com.laison.erp.config.auth.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

public class MyWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		
	
		
		String message="前端自己根据code输出";
		int httpErrorCode=406;//405密码错误  406 用户名错误 407 禁止登陆  
		if(e instanceof BadClientCredentialsException) {
			httpErrorCode=405;
		}else if (e instanceof InternalAuthenticationServiceException) {
			Throwable cause = e.getCause();
			if(cause !=null && cause instanceof DisabledException) {
				httpErrorCode=407; //禁止登陆
			}else {
				httpErrorCode=406;//用户名错误
			}
		}else if(e instanceof InvalidGrantException){//密码错误
			httpErrorCode=405;
		}else {
			message=e.getMessage();
			httpErrorCode=406;
		}
		
			
		
		CustomOauthException customOauthException = new CustomOauthException(message);
		customOauthException.setHttpErrorCode(httpErrorCode);
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body(customOauthException);
		

		
	}

}
