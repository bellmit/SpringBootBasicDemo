package com.laison.erp.config.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
   


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int httpErrorCode=402;

	public CustomOauthException(String msg) {
        super(msg);
    }
    
    @Override
	public int getHttpErrorCode() {
		return this.httpErrorCode;
	}

	public void setHttpErrorCode(int httpErrorCode) {
		this.httpErrorCode = httpErrorCode;
	}
    
    
    
}