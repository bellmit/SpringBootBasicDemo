package com.laison.erp.config.auth.social;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


/**
 * @author zhailiang
 *
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		
		return bean;
	}

}
