package com.laison.erp.config.auth;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 开启session共享
 * 
 * @author lihua
 *
 */
@EnableRedisHttpSession
public class SessionConfig {

}