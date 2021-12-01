/**
 * 
 */
package com.laison.erp.config.auth.social.qq.config;

import com.laison.erp.common.constants.SocialConfigConstants;
import com.laison.erp.config.auth.social.qq.connect.QQConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;


/**
 * @author zhailiang
 *
 */
@Configuration
public class QQAutoConfig extends SocialConfigurerAdapter {

	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
			Environment environment) {
		configurer.addConnectionFactory(createConnectionFactory());
	} 
	
	protected ConnectionFactory<?> createConnectionFactory() {
		
		return new QQConnectionFactory(SocialConfigConstants.DEFAULT_SOCIAL_QQ_PROVIDER_ID, SocialConfigConstants.DEFAULT_SOCIAL_QQ_APP_ID,SocialConfigConstants.DEFAULT_SOCIAL_QQ_APP_SECRET);
	}
	

	@Override
    public UserIdSource getUserIdSource() {
       
        return new AuthenticationNameUserIdSource();
    }
}
