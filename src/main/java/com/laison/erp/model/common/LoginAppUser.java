package com.laison.erp.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laison.erp.model.sys.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * spring security当前登录对象
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class LoginAppUser extends SysUser implements SocialUserDetails {

	private static final long serialVersionUID = 1753977564987556640L;
	private String language;
	
	
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new HashSet<>();
		collection.add(new SimpleGrantedAuthority(getId()));
		return collection;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !getDelFlag();
	}

	@Override
	public String getUserId() {
		
		return getUsername();
	}
}
