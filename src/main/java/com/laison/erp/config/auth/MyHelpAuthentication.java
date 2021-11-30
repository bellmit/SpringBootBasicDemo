package com.laison.erp.config.auth;


import com.laison.erp.common.utils.SpringContextUtils;
import com.laison.erp.model.sys.SysRole;
import com.laison.erp.model.sys.SysUser;
import com.laison.erp.service.sys.SysUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


public class MyHelpAuthentication extends  OAuth2Authentication {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	//private OAuth2Authentication oa ;
	
	
	
	public MyHelpAuthentication(OAuth2Request storedRequest, Authentication userAuthentication) {
		super(storedRequest, userAuthentication);
		
	}

	
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> idHolder = super.getAuthorities();
		Iterator<GrantedAuthority> iterator = idHolder.iterator();
		GrantedAuthority authority = iterator.next();
		String uid = authority.getAuthority();
		Collection<GrantedAuthority> collection = new HashSet<>();
		SysUserService sysUserService = SpringContextUtils.getBean(SysUserService.class);
		Collection<String> permissions;
		try {
			SysUser sysUser = sysUserService.selectByPrimaryKey(uid);
			SysRole sysRole = sysUser.getSysRole();
			if(sysRole!=null) {
				collection.add(new SimpleGrantedAuthority(sysRole.getName()));
			}
			permissions = sysUser.getPermissions();
			if (!CollectionUtils.isEmpty(permissions)) {
				permissions.forEach(per -> {
					collection.add(new SimpleGrantedAuthority(per));
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Collection<GrantedAuthority> getAuthorities() ");
		return collection;
	}

	
	

}
