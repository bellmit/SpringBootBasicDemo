package com.laison.erp.config.auth;

import com.laison.erp.common.constants.PermitAllUrl;
import com.laison.erp.config.ExceptionHandlerAdvice;
import com.laison.erp.config.auth.social.LaisonSpringSocialConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.social.connect.ConnectionFactoryLocator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;


/**
 * spring security配置
 * 
 * @author 李华
 * 
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; 
	
	
	
	@Autowired
	private LaisonSpringSocialConfigurer springSocialConfigurer;
	@Autowired
	 private  DataSource dataSource;
	@Autowired
	ConnectionFactoryLocator connectionFactoryLocator;
	/**
	 * 	全局用户信息<br>
	 * 	方法上的注解@Autowired的意思是，方法的参数的值是从spring容器中获取的<br>
	 * 	即参数AuthenticationManagerBuilder是spring中的一个Bean
	 *
	 * @param auth 认证管理
	 * @throws Exception 用户认证异常信息
	 */
	@Autowired
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		 auth.authenticationProvider(new LoginAuthenticationProvider(userDetailsService));
		 
		//OAuth2Authentication
//		JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//		 SocialAuthenticationProvider  s=new SocialAuthenticationProvider(jdbcUsersConnectionRepository, userDetailsService);
//		 auth.authenticationProvider(s);
	}
	
	

	/**
	 * 认证管理
	 * 
	 * @return 认证管理对象
	 * @throws Exception
	 *             认证异常信息
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * http安全配置
	 * 
	 * @param http
	 *            http安全对象
	 * @throws Exception
	 *             http安全异常信息
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//SocialAuthenticationFilter
		//SecurityContextPersistenceFilter
		//OAuth2AuthenticationProcessingFilter
		//TokenEndpoint 
		//这个filter的作用是 当 chain.doFilter(request, response);发生异常时用自己的逻辑去处理异常，而不是抛出异常让框架去处理
		
		//org.springframework.web.filter.DelegatingFilterProxy
		Filter filter=new Filter() {
			
			@Override
			public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
					throws IOException, ServletException {
				
				HttpServletRequest request = (HttpServletRequest) req;
				HttpServletResponse response = (HttpServletResponse) res;
				try {
					chain.doFilter(request, response);
				}catch (Exception ex){
					ExceptionHandlerAdvice.handerError(request, response, ex);//自己处理提出
				}
				
			}
		};
		http.apply(springSocialConfigurer)
					.and()
			.addFilterAfter(filter, ExceptionTranslationFilter.class)
			.exceptionHandling()
					.accessDeniedHandler((request, response, e) -> ExceptionHandlerAdvice.handerError(request, response, e))
					.authenticationEntryPoint((request, response, e) -> ExceptionHandlerAdvice.handerError(request, response, e))
					.and()
			.authorizeRequests()
					.antMatchers(PermitAllUrl.permitAllUrl()).permitAll() // 放开权限的url
					.anyRequest().authenticated()
					.and()
			.httpBasic().and()
				.headers().frameOptions().disable().and()
			.csrf().disable();
	}
	
}