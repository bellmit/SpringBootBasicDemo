package com.laison.erp.config.exception;

public class CustomerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer errorCode;
	public static final Integer NEED_LOGIN=401;
	
	public static CustomerException create(String errorMsg) {
		return new CustomerException(500,errorMsg);
	}
	
	
	public static CustomerException create(Integer errorCode,String errorMsg) {
		return new CustomerException(errorCode,errorMsg);
	}
	public CustomerException(Integer errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}


	public Integer getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
}
