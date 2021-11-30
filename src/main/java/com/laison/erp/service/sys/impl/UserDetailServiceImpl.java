package com.laison.erp.service.sys.impl;
import com.laison.erp.model.common.LoginAppUser;
import com.laison.erp.service.sys.SysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;





/**
 * 根据用户名获取用户<br>
 * <p>
 * 密码校验请看下面两个类
 *
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
 */

@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService,SocialUserDetailsService  {

	 @Autowired
	private SysUserService sysUserService;
    
  

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 为了支持多类型登录，这里username后面拼装上登录类型,如username|type
    	LoginAppUser loginAppUser = sysUserService.findByUsername(username);
    	
        if (loginAppUser == null) {
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        } else if (loginAppUser.getDelFlag()) {
            throw new DisabledException("用户已作废");
        }else if (!loginAppUser.getStatus()) {
            throw new DisabledException("用户已作废");
        }else  {
        	 if(CollectionUtils.isEmpty(loginAppUser.getPermissions())) {
        		 throw new DisabledException("用户已作废");
             }
        }
        //String objectToJsonWhitI18N = JsonUtils.objectToJson(loginAppUser);
        //System.out.println(objectToJsonWhitI18N);
        return loginAppUser;
    }



	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		// 为了支持多类型登录，这里username后面拼装上登录类型,如username|type
		LoginAppUser loginAppUser = sysUserService.findByUsername(userId);
       
        if (loginAppUser == null) {
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        } else if (loginAppUser.getDelFlag()) {
            throw new DisabledException("用户已作废");
        }else if (!loginAppUser.getStatus()) {
            throw new DisabledException("用户已作废");
        }else  {
       	 if(CollectionUtils.isEmpty(loginAppUser.getPermissions())) {
    		 throw new DisabledException("用户已作废");
         }
    }
        return loginAppUser;
	}



}
