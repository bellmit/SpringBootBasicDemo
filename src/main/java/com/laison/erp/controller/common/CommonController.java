package com.laison.erp.controller.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laison.erp.common.Result;
import com.laison.erp.common.constants.ContentConstant;
import com.laison.erp.common.constants.SocialConfigConstants;
import com.laison.erp.common.utils.LoginUserUtil;
import com.laison.erp.model.sys.SocialUserInfo;
import com.laison.erp.model.sys.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.annotations.ApiOperation;

@SuppressWarnings("unused")
@Controller
public class CommonController {
    public static final String VALIDATE_CODE = "validate" ;

    public static final String TOKEN = "okjfdlsf";

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

//	@Autowired
//	private AppSingUpUtils appSingUpUtils;


    @Autowired
    private ClientDetailsService clientDetailsService;

//	@Autowired
//	private AuthorizationServerTokenServices authorizationServerTokenServices;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private UsersConnectionRepository usersConnectionRepository;


    @Autowired
    private ConsumerTokenServices tokenServices;

    @PostMapping("/changeLanguage")
    @ApiOperation(value = "更改用户语言", notes = " {'language':'en_US'}")
    public @ResponseBody
    Result changeLanguage(@RequestBody Map<String, String> param) throws Exception {
        LoginUserUtil.changeLanguage(param.get("language"));
        return Result.ok(ContentConstant.OPERATE_SUCCESS);
    }

    @PostMapping("/updateQuickEntry")
    @ApiOperation(value = "更改用户首页快捷入口")
    public @ResponseBody
    Result updateQuickEntry(@RequestBody Map<String, String> param) throws Exception {
        LoginUserUtil.updateQuickEntry(param.get("quickEntry"));
        return Result.ok(ContentConstant.OPERATE_SUCCESS);
    }

    @DeleteMapping("/logout/{token}")
    public Result deleteById(@PathVariable String token) throws Exception {
        boolean flag = tokenServices.revokeToken(token);
        Result ok = Result.ok();
        ok.put("logoutResult", flag);
        return ok;
    }


    /**
     * 需要注册时跳到这里
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(SocialConfigConstants.SOCIAL_SIGNUP)
    public @ResponseBody
    Result getSocialUserInfo(HttpServletRequest request) throws Exception {
        return null;
//		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
//		String uuid = UUIDutil.get16UUID();
//		appSingUpUtils.saveConnectionData(uuid, connection.createData());
//		SocialUserInfo socialUserInfo = buildSocialUserInfo(connection);
//		Result error = Result.error(Result.NEED_BIND, "need bind");
//		error.put("needBind", true);
//		error.put("bindKey", uuid);
//		error.put("socialUserInfo", socialUserInfo);
//		return error;
    }

    @PostMapping(SocialConfigConstants.SOCIAL_BIND)
    public @ResponseBody
    OAuth2AccessToken bind(@RequestBody HashMap<String, Object> parma, HttpServletRequest request)
            throws Exception {
//		String userId = (String) parma.get("username");
//		String password = (String) parma.get("password");
//		String bindKey = (String) parma.get("bindKey");
//		//password = new String(new BASE64Decoder().decodeBuffer(password));
//		//password=passwordEncoder.encode(password);
//		SysUserDao sysUserDao = SpringContextUtils.getBean(SysUserDao.class);
//		
//		SysUser record=new SysUser();
//		record.setUsername(userId);
//		record = sysUserDao.selectOne(record);
//		if(record==null)
//			throw new Exception(ContentConstant.LOGIN_NAME_ERROR);
//		
//		if(!passwordEncoder.matches(password, record.getPassword())) 
//			throw new Exception(ContentConstant.LOGIN_PASSWORD_ERROR);
//		
//		
//		SocialAuthenticationToken authentication = appSingUpUtils.doPostSignUp(bindKey, userId);
//
//		
//		
//		String clientId = request.getParameter("client_id");
//		// String clientSecret = request.getParameter("client_secret");
//		if (StringUtils.isBlank(clientId)) {
//			clientId = "system";
//		}
//		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
//
//		TokenRequest tokenRequest = new TokenRequest(new HashMap<String, String>(), clientId, clientDetails.getScope(),"social");
//
//		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
//
//		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
//
//		OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        return null;//token
    }

    protected SocialUserInfo buildSocialUserInfo(Connection<?> connection) {
        SocialUserInfo userInfo = new SocialUserInfo();
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());
        return userInfo;
    }

//	@PostMapping("/changeLanguage")
//	@ApiOperation(value = "更改用户语言", notes = " {'language':'en_US'}")
//	public @ResponseBody Result changeLanguage(HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		LoginUserUtil.changeLanguage(param.get("language"));
//		return Result.ok(ContentConstant.OPERATE_SUCCESS);
//		return Result.ok(JsonUtils.objectToJson(request.getParameterMap()));
//	}


    @GetMapping("/getLoginUser")
    public @ResponseBody
    Result getLoginUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysUser sysUser = LoginUserUtil.getSysUser().setPassword(null);
        return Result.okData(sysUser);
    }

    @GetMapping("/getBizMenu")
    public @ResponseBody
    Result getBizMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return Result.okData(LoginUserUtil.getSysUser().setPassword(null));
    }

}
