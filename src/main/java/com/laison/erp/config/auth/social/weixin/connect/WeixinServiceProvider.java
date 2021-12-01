package com.laison.erp.config.auth.social.weixin.connect;

import com.laison.erp.common.constants.SocialConfigConstants;
import com.laison.erp.config.auth.social.weixin.api.WeixinImpl;
import com.laison.erp.config.auth.social.weixin.api.Weixin;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;


/**
 * 
 * 微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 * 
 * @author zhailiang
 *
 */
public class WeixinServiceProvider extends AbstractOAuth2ServiceProvider<Weixin> {
	


	/**
	 * @param appId
	 * @param appSecret
	 */
	public WeixinServiceProvider(String appId, String appSecret) {
		super(new WeixinOAuth2Template(appId, appSecret, SocialConfigConstants.DEFAULT_SOCIAL_WEIXIN_URL_AUTHORIZE,SocialConfigConstants.DEFAULT_SOCIAL_WEIXIN_URL_ACCESS_TOKEN));
	}


	/* (non-Javadoc)
	 * @see org.springframework.social.oauth2.AbstractOAuth2ServiceProvider#getApi(java.lang.String)
	 */
	@Override
	public Weixin getApi(String accessToken) {
		return new WeixinImpl(accessToken);
	}

}
