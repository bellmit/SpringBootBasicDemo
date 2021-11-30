package com.laison.erp.config.auth;

import com.laison.erp.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


/**
 * app环境下替换providerSignInUtils，避免由于没有session导致读不到社交用户信息的问题
 * 
 * @author zhailiang
 *
 */
@Component
public class AppSingUpUtils {

	public static String CACHE_NAME = "socail_user";


	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Autowired
	private UsersConnectionRepository usersConnectionRepository;

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;

	
	@Autowired
	public UserDetailsService userDetailsService;
	/**
	 * 缓存社交网站用户信息到redis
	 * 
	 * @param key
	 * @param connectionData
	 * @throws Exception
	 */
	public void saveConnectionData(String key,ConnectionData connectionData) throws Exception {
		
		redisTemplate.opsForValue().set(key, connectionData, 10, TimeUnit.MINUTES);
		
	}

	/**
	 * 将缓存的社交网站用户信息与系统注册用户信息绑定
	 * @param bindKey
	 * @param userId
	 * @throws Exception 
	 */
	public SocialAuthenticationToken doPostSignUp(String bindKey, String userId) throws Exception {
	
		
		if(!redisTemplate.hasKey(bindKey)){
			throw new Exception("无法找到缓存的用户社交账号信息");
		}
		ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(bindKey);
		Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId())
				.createConnection(connectionData);
		
		
		//QQConnectionFactory
//		if(sysUserDao.findIfUsernameBinded(userId, connection.getKey().getProviderId()))
//			throw new Exception(ContentConstant.USERNAME_AREADY_BINDED);
//		
//		if(sysUserDao.findIfBinded(connection.getKey().getProviderUserId()))
//			throw new Exception(ContentConstant.SOCIAL_USER_AREADY_BINDED);
		
		usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);
		UserDetails user = userDetailsService.loadUserByUsername(userId);
		
		redisTemplate.delete(bindKey);
		SocialAuthenticationToken authentication= new SocialAuthenticationToken(connection, user, new HashMap<String, String>(), user.getAuthorities());
		
		return authentication;
	}

	/**
	 * 获取redis key
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String getKey(WebRequest request) throws Exception {
		
		String deviceId = request.getParameter("bindKey");
		if (StringUtils.isBlank(deviceId)) {
			throw new Exception("设备id参数不能为空");
		}
		return  deviceId;
	}

}