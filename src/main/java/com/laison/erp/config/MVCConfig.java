package com.laison.erp.config;

import java.util.List;

import com.laison.erp.common.constants.LogUrl;
import com.laison.erp.config.auth.MyPasswordEncoder;
import com.laison.erp.config.interceptor.LogInterceptor;
import com.laison.erp.config.interceptor.MyContextInterceptor;
import com.laison.erp.config.interceptor.OperateLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@ComponentScan("com.**.controller") // 继承WebMvcConfigurer 接口，重写其方法可对Spring MVC进行配置
public class MVCConfig implements WebMvcConfigurer {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new MyPasswordEncoder();
	}
	
	

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		WebMvcConfigurer.super.extendMessageConverters(converters);
	}

	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor()).addPathPatterns(LogUrl.logAllUrl("/**"));
		registry.addInterceptor(new MyContextInterceptor()).addPathPatterns(LogUrl.logAllUrl("/**"));
		registry.addInterceptor(new OperateLogInterceptor()).addPathPatterns(LogUrl.logAllUrl("/**"));

        //.excludePathPatterns("/**find**","/**get**");
    }
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
		WebMvcConfigurer.super.addFormatters(registry);
	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/**").addResourceLocations("/activiti/**")
//				.addResourceLocations("/resources/**");
//	}
}
