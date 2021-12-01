package com.laison.erp.config.auth;

import com.laison.erp.config.exception.CustomerException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyResourceOwnerPasswordTokenGranter extends ResourceOwnerPasswordTokenGranter {

    private final AuthenticationManager myauthenticationManager;

    public MyResourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager,
                                               AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                               OAuth2RequestFactory requestFactory) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory);
        this.myauthenticationManager = authenticationManager;
    }

    /**
     * 验证用户信息，关联 @link UserDetailServiceImpl -- loadUserByUsername
     *
     * @param client
     * @param tokenRequest
     * @return
     */
    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String username = parameters.get("username");
        String password = parameters.get("password");
        // Protect from downstream leaks of password
        parameters.remove("password");

        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = myauthenticationManager.authenticate(userAuth);
        } catch (Exception ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw CustomerException.create(ase.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw CustomerException.create("Could not authenticate user: " + username);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new MyHelpAuthentication(storedOAuth2Request, userAuth);
    }

}
