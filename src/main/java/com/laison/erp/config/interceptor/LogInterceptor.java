package com.laison.erp.config.interceptor;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laison.erp.common.utils.HttpUtils;
import com.laison.erp.common.utils.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogInterceptor implements HandlerInterceptor {


	private static String logFormat = "IP:%s URI:%s 耗时:%s 参数:%s 最大内存:%sm  已分配内存:%sm  已分配内存中的剩余空间:%sm  最大可用内存:%sm ";

	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");

	//在请求处理之前进行调用（Controller方法调用之前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime); // 线程绑定变量（该数据只有当前请求的线程可见）

		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
		long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		long endTime = System.currentTimeMillis(); // 2、结束时间


		String requestURI = request.getRequestURI();
		String ipAddr = HttpUtils.getIpAddr(request);
		long spendTime=endTime-beginTime;

		String param = IOUtils.toString(request.getInputStream());
		//从request获取参数会导致 后续分方法无法在获取到参数，因为原来的request只能获取一次参数
		//要写个fiter包装request 增强参数获取
		if (StringUtils.isBlank(param)) {
			Map<?, ?> parameter = request.getParameterMap();
			param= JsonUtils.objectToJson(parameter);
		}

		log.info(String.format(logFormat, ipAddr, requestURI, spendTime, param
				, Runtime.getRuntime().maxMemory() / 1024 / 1024
				,  Runtime.getRuntime().totalMemory() / 1024 / 1024
				, Runtime.getRuntime().freeMemory() / 1024 / 1024
				, (Runtime.getRuntime().maxMemory()
						- Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024));

	}
}