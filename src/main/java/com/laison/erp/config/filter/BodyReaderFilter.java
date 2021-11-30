package com.laison.erp.config.filter;

import com.laison.erp.common.constants.LogUrl;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;

@Component
public class BodyReaderFilter implements Filter {
	private static PathMatcher pm =new AntPathMatcher() ;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String header = request.getHeader("Content-Type");
		HashSet<String> logUrl = LogUrl.getLogUrl();
		String requestURI = request.getRequestURI();
		boolean needWapper=false;
		for (String url : logUrl) {
			if(pm.match(url, requestURI)) {
				needWapper=true;
				break;
			}
		}
		if (needWapper)
			if (header != null && (header.contains("multipart/form-data") || header.contains("application/json")))
				if (!header.contains("boundary"))
					req = new BodyReaderHttpServletRequestWrapper(request);

		chain.doFilter(req, res);
	}
}