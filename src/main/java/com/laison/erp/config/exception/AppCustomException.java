package com.laison.erp.config.exception;


import com.laison.erp.common.utils.I18NResourceBundleUtils;

/**
 * 
 * @author lihua
 *
 */
public class AppCustomException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//默认的messageKey  
    public static final String DEFAULT_MESSAGE_KEY = "unkonwException";  

	private String message;
	private int code = 500;
	public AppCustomException(String message){
		super(message);
		this.message = message;
	}
	public AppCustomException(int code,String message){
		super(message);
		this.code=code;
		this.message = message;
	}
	
	public String getMessage() {
		return I18NResourceBundleUtils.getLocalizedText(message);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public AppCustomException(String message, int code) {
		super();
		this.message = message;
		this.code = code;
	}
	
	@Override  
    public String getLocalizedMessage() {  
		String text = I18NResourceBundleUtils.getLocalizedText(message);  	  
		return text;
		
    }  
}
