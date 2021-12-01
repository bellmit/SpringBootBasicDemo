package com.laison.erp.config.auth.social.weixin.config;


import com.laison.erp.common.constants.SocialConfigConstants;
import com.laison.erp.config.auth.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;


/**
 * 微信登录配置
 * 
 * @author zhailiang
 *
 */
//@Configuration
public class WeixinAutoConfiguration extends SocialConfigurerAdapter {

	

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
			Environment environment) {
		configurer.addConnectionFactory(createConnectionFactory());
	}


	private ConnectionFactory<?> createConnectionFactory() {
		return new WeixinConnectionFactory(SocialConfigConstants.DEFAULT_SOCIAL_WEIXIN_PROVIDER_ID, SocialConfigConstants.DEFAULT_SOCIAL_WEIXIN_APP_ID,SocialConfigConstants.DEFAULT_SOCIAL_WEIXIN_APP_SECRET);
	}
	
	@Override
    public UserIdSource getUserIdSource() {
       
        return new AuthenticationNameUserIdSource();
    }
	
}
