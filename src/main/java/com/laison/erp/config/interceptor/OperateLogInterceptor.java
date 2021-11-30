package com.laison.erp.config.interceptor;

import com.laison.erp.common.utils.*;
import com.laison.erp.config.filter.BodyReaderHttpServletRequestWrapper;
import com.laison.erp.model.common.OperateLog;
import com.laison.erp.model.sys.SysUser;
import com.laison.erp.service.common.OperateLogService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Log4j2
public class OperateLogInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<OperateLog> st = new NamedThreadLocal<OperateLog>("ThreadLocal OperateLogStartTime");

    private static String[] UN_LOG_URL ={"/**/*Page*/**","/**/findAll","/**/findById/0","/websocket/**","/getLoginUser"};
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //路径匹配器 匹配连接通配符
        String requestURI = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String s : UN_LOG_URL) {
            if (antPathMatcher.match(s,requestURI))
                return true;
        }

        if (request instanceof BodyReaderHttpServletRequestWrapper){
            BodyReaderHttpServletRequestWrapper req=(BodyReaderHttpServletRequestWrapper) request;
            OperateLog operateLog = new OperateLog();
            operateLog.setTime(DateUtils.utcDate());
            byte[] body = req.getBody();
            String param = new String(body,"UTF-8");
            if (StringUtils.isBlank(param)) {
                Map<?, ?> parameter = request.getParameterMap();
                param= JsonUtils.objectToJson(parameter);
            }
            if(param.length()>2000) {
                param.substring(0, 2000);
            }
            operateLog.setParam(param);

            st.set(operateLog); // 线程绑定变量（该数据只有当前请求的线程可见）
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (response instanceof ContentCachingResponseWrapper) {
            SysUser sysUser = LoginUserUtil.getSysUser();
            OperateLog operateLog = st.get();// 得到线程绑定的局部变量（开始时间）
            if(operateLog!=null){
                long beginTime = st.get().getTime().getTime();// 得到线程绑定的局部变量（开始时间）
                long endTime = System.currentTimeMillis(); // 2、结束时间
                operateLog.setConsumeTime(endTime - beginTime);
                String requestURI = request.getRequestURI();
                String ipAddr = HttpUtils.getIpAddr(request);

                operateLog.setIp(ipAddr);
                operateLog.setUri(requestURI);
                if(sysUser!=null) {
                	operateLog.setUserId(sysUser.getId());
                	operateLog.setDeptId(sysUser.getDeptId());
                }else {
                	operateLog.setUserId("");
                	operateLog.setDeptId("");
                }
                ContentCachingResponseWrapper reps = (ContentCachingResponseWrapper) response;
                byte[] s = reps.getContentAsByteArray();
                reps.copyBodyToResponse();
                String res = new String(s,"UTF-8");
                operateLog.setResult(res);
                SpringContextUtils.getBean(OperateLogService.class).save(operateLog);
            }

        }
        st.remove();
    }
}
