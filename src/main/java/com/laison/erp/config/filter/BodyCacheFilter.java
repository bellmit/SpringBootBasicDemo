package com.laison.erp.config.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BodyCacheFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        requestWrapper.getParameterMap(); //将参数写到缓存
        ContentCachingResponseWrapper responseWrapper=new ContentCachingResponseWrapper(response);
        //reps.copyBodyToResponse();//最后注意需要请reponsewrapper的内容写入到原始response, 最后一步执行，因为执行完后ContentCachingResponseWrapper的FastByteArrayOutputStream会被重置
        chain.doFilter(requestWrapper, responseWrapper);
        responseWrapper.copyBodyToResponse();  //读完后重置
    }
}
