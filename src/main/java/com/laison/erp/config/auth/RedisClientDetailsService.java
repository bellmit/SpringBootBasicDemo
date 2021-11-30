package com.laison.erp.config.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;

/**
 * 将oauth_client_details表数据缓存到redis，毕竟该表改动非常小，而且数据很少，这里做个缓存优化<br>
 * 如果有通过界面修改client的需求的话，不要用JdbcClientDetailsService了，请用该类，否则redis里有缓存<br>
 * 如果手动修改了该表的数据，请注意清除redis缓存，是hash结构，key是client_details
 *
 * @author 李华
 */
@Slf4j
@Service
public class RedisClientDetailsService extends JdbcClientDetailsService {

    @Autowired
    private CacheManager cacheManager;

    public RedisClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    private static final String CACHE_CLIENT_KEY = "client_details";

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails clientDetails = null;

        // 先从redis获取
        Cache cache = cacheManager.getCache(CACHE_CLIENT_KEY);
        ValueWrapper valueWrapper = cache.get(clientId);
        
        if (valueWrapper != null) {
        	clientDetails = (ClientDetails) valueWrapper.get();
        } else {
        	   clientDetails = super.loadClientByClientId(clientId);
              if (clientDetails != null) {// 写入redis缓存
            	  cache.put(clientId, clientDetails);
                  log.info("缓存clientId:{},{}", clientId, clientDetails);
              }
        }

        return clientDetails;
    }

    /**
     * 缓存client并返回client
     *
     * @param clientId
     */
    private ClientDetails cacheAndGetClient(String clientId) {
        // 从数据库读取
        ClientDetails clientDetails = super.loadClientByClientId(clientId);
        if (clientDetails != null) {// 写入redis缓存
        	Cache cache = cacheManager.getCache(CACHE_CLIENT_KEY);
        	cache.put(clientId, clientDetails);
            log.info("缓存clientId:{},{}", clientId, clientDetails);
        }

        return clientDetails;
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        super.updateClientDetails(clientDetails);
        cacheAndGetClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        super.updateClientSecret(clientId, secret);
        cacheAndGetClient(clientId);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        super.removeClientDetails(clientId);
        removeRedisCache(clientId);
    }

    /**
     * 删除redis缓存
     *
     * @param clientId
     */
    private void removeRedisCache(String clientId) {
    	Cache cache = cacheManager.getCache(CACHE_CLIENT_KEY);
    	cache.evict(clientId);
        
    }

    /**
               *       将oauth_client_details全表刷入redis
     */
    public void loadAllClientToCache() {
    	Cache cache = cacheManager.getCache(CACHE_CLIENT_KEY);
        
        //log.info("将oauth_client_details全表刷入redis");
        List<ClientDetails> list = super.listClientDetails();
        if (CollectionUtils.isEmpty(list)) {
            log.error("oauth_client_details表数据为空，请检查");
            return;
        }
        list.parallelStream().forEach(client -> {
        	cache.put(client.getClientId(), client);
        });
    }
}
