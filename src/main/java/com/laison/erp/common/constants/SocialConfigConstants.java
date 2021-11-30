package com.laison.erp.common.constants;


import com.laison.erp.common.utils.YmlUtils;

public class SocialConfigConstants {
	public static  String DEFAULT_SOCIAL_WEIXIN_PROVIDER_ID= YmlUtils.getvalue("config.default.social.weixin.provider_id");
	public 	static String DEFAULT_SOCIAL_WEIXIN_APP_ID = YmlUtils.getvalue("config.default.social.weixin.app_id");
	public  static String DEFAULT_SOCIAL_WEIXIN_APP_SECRET = YmlUtils.getvalue("config.default.social.weixin.app_secret");
	
	/**
	 * 微信获取授权码的url
	 */
	public static final String DEFAULT_SOCIAL_WEIXIN_URL_AUTHORIZE = YmlUtils.getvalue("config.default.social.weixin.url_authorize");
	/**
	 * 微信获取accessToken的url
	 */
	public static final String DEFAULT_SOCIAL_WEIXIN_URL_ACCESS_TOKEN =  YmlUtils.getvalue("config.default.social.weixin.url_accesstoken");
	public  static String DEFAULT_SOCIAL_QQ_PROVIDER_ID=YmlUtils.getvalue("config.default.social.qq.provider_id");
	public 	static String DEFAULT_SOCIAL_QQ_APP_ID = YmlUtils.getvalue("config.default.social.qq.app_id");
	public  static String DEFAULT_SOCIAL_QQ_APP_SECRET = YmlUtils.getvalue("config.default.social.qq.app_secret");
	/**
	 * 微信获取授权码的url
	 */
	public static final String DEFAULT_SOCIAL_QQ_URL_AUTHORIZE = YmlUtils.getvalue("config.default.social.qq.url_authorize");
	/**
	 * 微信获取accessToken的url
	 */
	public static final String DEFAULT_SOCIAL_QQ_URL_ACCESS_TOKEN =  YmlUtils.getvalue("config.default.social.qq.url_accesstoken");
	public static String SOCIAL_LOGIN="/auth";
	public final static String SOCIAL_SIGNUP="/social/user";
	public final static String SOCIAL_BIND="/social/bind";

}
