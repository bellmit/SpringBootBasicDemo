package com.laison.erp.config.auth;

import com.laison.erp.common.constants.SocialConfigConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class LaisonSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;
    
    @Autowired
    LaisonAuthenticationSuccessHandler laisonAuthenticationSuccessHandler;

    /**
     * 
     * @param filterProcessesUrl  登录前缀
     */
    public LaisonSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        filter.setSignupUrl(SocialConfigConstants.SOCIAL_SIGNUP);
        //处理json返回
        filter.setAuthenticationSuccessHandler(laisonAuthenticationSuccessHandler);
        return (T) filter;
    }
}
