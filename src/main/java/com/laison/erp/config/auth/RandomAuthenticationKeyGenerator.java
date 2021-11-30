package com.laison.erp.config.auth;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;


import lombok.extern.log4j.Log4j2;

import java.util.UUID;

/**
 * 解决同一username每次登陆access_token都相同的问题,看下RedisTokenStore的方法getAccessToken便知<br>
 * 2018.08.04添加
 *
 * @author 李华
 * @see org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator
 * @see org.springframework.security.oauth2.provider.token.TokenStore
 */
@Log4j2
public class RandomAuthenticationKeyGenerator implements AuthenticationKeyGenerator {
	
	//private static HashMap<OAuth2Authentication, String> tokenHolder=new HashMap<OAuth2Authentication, String>();

    @Override
    public String extractKey(OAuth2Authentication authentication) {
		return UUID.randomUUID().toString();
    }
}