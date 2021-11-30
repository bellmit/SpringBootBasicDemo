package com.laison.erp.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laison.erp.common.AppR;
import com.laison.erp.common.Result;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.JsonUtils;
import com.laison.erp.config.exception.AppCustomException;
import com.laison.erp.config.exception.CustomerException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> badRequestException(IllegalArgumentException exception) {
		
		return Result.BAD_REQUEST(exception.getMessage());
	}
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> notValidRequestException(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		List<ObjectError> errors = bindingResult.getAllErrors();
		String defaultMessage=null;
		if(errors!=null&&errors.size()>0) {
			defaultMessage = errors.get(0).getDefaultMessage();
		}else {
			defaultMessage= ContentConstant.UNKNOWN_ERROR;
		}
		return Result.BAD_REQUEST(defaultMessage);
	}
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.OK)
    public Map<String, Object> error(Exception e){
		
        return Result.INTERNAL_SERVER_ERROR(e.getMessage());
    }
	
	@ExceptionHandler(value = NullPointerException.class)
	@ResponseStatus(HttpStatus.OK)
    public Map<String, Object> nullPointerError(NullPointerException e){
		e.printStackTrace();
        return Result.INTERNAL_SERVER_ERROR(e.getStackTrace().toString());
    }
	@ExceptionHandler(value = DuplicateKeyException.class)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> duplicateKeyError(DuplicateKeyException e){
		return Result.BAD_REQUEST(e.getCause().getMessage());
	}
	
	/**
     * @param exception
     * @return
     * @throws Exception
     * @// TODO: 2018/4/25 处理token 过期异常
     */
    @ExceptionHandler(value = AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object ExpiredJwtExceptionHandler(AuthenticationCredentialsNotFoundException exception) throws Exception {
    	exception.printStackTrace();
    	 return Result.INTERNAL_SERVER_ERROR(exception.getStackTrace().toString());
    }

	@ExceptionHandler(value = CustomerException.class)
	@ResponseStatus(HttpStatus.OK)
	public Object CustomerExceptionHandler(CustomerException exception) throws Exception {
		return Result.error(exception.getErrorCode(),exception.getMessage());
	}
	
	@ExceptionHandler(value = AppCustomException.class)
	@ResponseStatus(HttpStatus.OK)
	public Object AppCustomExceptionHandler(AppCustomException exception) throws Exception {
		AppR r = AppR.error(exception.getCode(),exception.getMessage());
		return r;
	}

    public static void handerError(HttpServletRequest request , HttpServletResponse response, Exception e) throws IOException {
    	String message = e.getMessage();
    	int code=500;
    	if(e instanceof InsufficientAuthenticationException) {
    		message="{token.error}";
    		code=CustomerException.NEED_LOGIN;
    	}else if(e instanceof CustomerException) {
    		CustomerException re=(CustomerException) e;
    		message=re.getMessage();
    		code=re.getErrorCode();
    	}
		Result r = Result.error(code,message);
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(JsonUtils.objectToJson(r));
	}
}
