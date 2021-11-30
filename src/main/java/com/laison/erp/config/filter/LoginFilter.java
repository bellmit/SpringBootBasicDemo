package com.laison.erp.config.filter;

import com.laison.erp.common.Result;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.utils.JsonUtils;
import com.laison.erp.controller.common.CommonController;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

//现在没什么用了
//@WebFilter(filterName="httpServletRequestReplacedFilter",urlPatterns={"/oauth/token","/login"})// filter的执行顺序是按名字排序的a>b>c
public class LoginFilter implements Filter {
    @Override
    public void destroy() {
        System.out.println("--------------过滤器销毁------------");
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
    	
    	
    	String code =request.getParameter(CommonController.VALIDATE_CODE);
    	
    	HttpSession session = ((HttpServletRequest)request).getSession(true);
    	String ocode = (String) session.getAttribute(CommonController.VALIDATE_CODE);
    	if(ocode==null || code ==null || !StringUtils.equalsIgnoreCase(ocode, code)) {
    		
    		response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(JsonUtils.objectToJsonWhitI18N( Result.BAD_REQUEST(ContentConstant.VCODE_ERROR)));
			return;
    	}
    	//request.gets
    	//request.getSession()
    	HashMap<String, Object> params = new HashMap<>();
    
    	params.put("grant_type", new String[] {"password"});
		params.put("client_id", new String[] {"system"});
		params.put("client_secret", new String[] {"system"});
		params.put("scope", new String[] {"app"});
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper((HttpServletRequest)request,params);
        chain.doFilter(wrapRequest, response);
        
    }
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    	
    	
        System.out.println("--------------过滤器初始化------------");
    }
 
}
