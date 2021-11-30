package com.laison.erp.config.auth;

import com.laison.erp.common.constants.SocialConfigConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;

import javax.sql.DataSource;

/**
 * 社交配置类
 *
 * @author jiangpingping
 * @date 2019-02-05 17:23
 */
@Configuration
@EnableSocial
public class SocialSecurityConfig extends SocialConfigurerAdapter {

    private final DataSource dataSource;
    
  

    @Autowired
    public SocialSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        // 创建一个JDBC连接仓库，需要dataSource、connectionFactory加载器，对存到数据库中的加密策略，这里选择不做加密，信息原样存入数据库
        // 这里创建的JdbcUsersConnectionRepository可以设置UserConnection表的前缀
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
    }

    @Bean
    public LaisonSpringSocialConfigurer springSocialConfigurer() {
    	LaisonSpringSocialConfigurer laisonSpringSocialConfigurer = new LaisonSpringSocialConfigurer(SocialConfigConstants.SOCIAL_LOGIN);
    	
        return laisonSpringSocialConfigurer;
    }
    @Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator,
				getUsersConnectionRepository(connectionFactoryLocator)) {
		};
	}
   
}
