package com.laison.erp.common.utils;


import java.util.List;
import com.laison.erp.common.constants.ConfigConstant;
import com.laison.erp.config.interceptor.MyContextHolder;
import com.laison.erp.model.sys.SysUser;
import com.laison.erp.service.sys.SysUserService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

public class LoginUserUtil {

	/**
	 * 获取登陆的 LoginAppUser
	 *
	 * @return
	 */
//	@SuppressWarnings("rawtypes")
//	public static LoginAppUser getLoginAppUser() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication instanceof OAuth2Authentication) {
//			OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
//			authentication = oAuth2Auth.getUserAuthentication();
//			if (authentication instanceof UsernamePasswordAuthenticationToken) {
//				UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
//				Object principal = authentication.getPrincipal();
//				if (principal instanceof LoginAppUser) {
//					return (LoginAppUser) principal;
//				}
//				Map map = (Map) authenticationToken.getDetails();
//				map = (Map) map.get("principal");
//				return JsonUtils.jsonToPojo(JsonUtils.objectToJson(map), LoginAppUser.class);
//			}else if(authentication instanceof SocialAuthenticationToken){
//				Object principal = authentication.getPrincipal();
//				if (principal instanceof LoginAppUser) {
//					return (LoginAppUser) principal;
//				}
//			}
//		}
//		return null;
//	}
	
//	public static SysUser getSysUser(Boolean... isThrowExcption) throws Exception{
//		
//		Boolean throwExcption=isThrowExcption==null||isThrowExcption.length==0||isThrowExcption[0];
//		LoginAppUser loginAppUser = getLoginAppUser();
//		if(loginAppUser ==null ) {
//			if(throwExcption) {
//				throw new Exception(ContentConstant.PLEASE_LOGIN);
//			}
//			return null;
//			
//		}
////		SysUserService sysUserService = SpringContextUtils.getBean(SysUserService.class);
////		try {
////			SysUser sysUser = sysUserService.selectByPrimaryKey(loginAppUser.getId());
////			sysUser.setLanguage(getLanguage(sysUser.getUsername()));
////			return sysUser;
////		} catch (Exception e) {
////			e.printStackTrace();
////			if(throwExcption) {
////				throw new Exception(ContentConstant.PLEASE_LOGIN);
////			}
////			return null;
////		}
//		
//	}
	
	/**
	 * 更新权限
	 * @throws Exception 
	 */
//	public static void updateAuthorities() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		//List<GrantedAuthority> updatedAuthorities =
//		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//		List<GrantedAuthority> updatedAuthorities = new ArrayList<GrantedAuthority>(authorities);
//		
//		if ( auth instanceof OAuth2Authentication) {
//			Object principal = auth.getPrincipal();
//			if (principal instanceof LoginAppUser) {
//				LoginAppUser	loginAppUser=(LoginAppUser) principal;
//				updatedAuthorities.clear();
//				Set<String> permissions = new HashSet<String>();
//				
//				if(loginAppUser.getId().equals("1")) {
//					
//					
//					SysMenuService sysMenuService = SpringContextUtils.getBean(SysMenuService.class);
//					List<String> authoritys = sysMenuService.findAllAuthoritys();
//					permissions.addAll(authoritys);
//					
//					
//				}else {
//					SysUserDao sysUserDao = SpringContextUtils.getBean(SysUserDao.class);
//					List<String> authoritys =sysUserDao.findUserAuthoritys(loginAppUser.getId());
//					permissions.addAll(authoritys);
//				}	
//				
//				Collection<GrantedAuthority> collection = new HashSet<>();
//				if (!CollectionUtils.isEmpty(permissions)) {
//					permissions.forEach(per -> {
//						collection.add(new SimpleGrantedAuthority(per));
//					});
//				}
//				updatedAuthorities.addAll(collection);
//			}
//		}
//		//AuthenticationManager authenticationManager = SpringContextUtils.getBean(AuthenticationManager.class);
//		
//		
//		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), updatedAuthorities);
//		
//		String clientId ="system";
//		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
//		
//		ClientDetails clientDetails = SpringContextUtils.getBean(RedisClientDetailsService.class).loadClientByClientId(clientId);
//
//		TokenRequest tokenRequest = new TokenRequest(new HashMap<String, String>(), clientId, clientDetails.getScope(),
//				"password");
//
//		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
//		
//		OAuth2Authentication oAuth2Authentication = new MyHelpAuthentication(details.getTokenValue(),oAuth2Request, newAuth);
//		//OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, newAuth);
//
//		SpringContextUtils.getBean(AuthorizationServerTokenServices.class).createAccessToken(oAuth2Authentication);
//		
//
//	}

	public static String getLanguage()  {
		SysUser sysUser = getSysUser();
		if(sysUser!=null) {
			return sysUser.getLanguage();
		}
		return ConfigConstant.I18N;
	}

	public static String getTimeZone()  {
		SysUser sysUser = getSysUser();
		if(sysUser!=null) {
			return sysUser.getSysDept().getConfig().getTimeZone();
		}
		return null;
	}
	public static void changeLanguage(String language) {
		if(I18NResourceBundleUtils.contains(language)) {
			SysUser sysUser = getSysUser();
			SysUser updateUser = new SysUser();
			updateUser.setId(sysUser.getId());
			updateUser.setLanguage(language);
			SysUserService sysUserService = SpringContextUtils.getBean(SysUserService.class);
			try {
				sysUserService.updateByPrimaryKey(updateUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
			sysUser.setLanguage(language);
		}
		
	}
	public static void updateQuickEntry(String quickEntry) {
		//接收的字符串为修改后的值，应当被写入数据库
		//获取用户id
			try {
				SysUser sysUser = getSysUser();
				SysUser updateUser = new SysUser();
				updateUser.setId(sysUser.getId());
				updateUser.setQuickEntry(quickEntry);

				SysUserService sysUserService = SpringContextUtils.getBean(SysUserService.class);
				sysUserService.updateByPrimaryKey(updateUser);

//				sysUser.setQuickEntry(quickEntry);


//				SysUser sysUserUpdate = new SysUser().setId(sysUser.getId()).setPassword(sysUser.getPassword());
//				sysUserDao.updateByPrimaryKeySelective(sysUserUpdate);
				SpringContextUtils.getCache(SysUserService.CACHE_NAME).evict(sysUser.getId());
				SpringContextUtils.getCache(SysUserService.BRIEF_CACHE_NAME).evict(sysUser.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}

//		if(I18NResourceBundleUtils.contains(language)) {
//			SysUser sysUser = getSysUser();
//			SysUser updateUser = new SysUser();
//			updateUser.setId(sysUser.getId());
//			updateUser.setLanguage(language);
//			SysUserService sysUserService = SpringContextUtils.getBean(SysUserService.class);
//			try {
//				sysUserService.updateByPrimaryKey(updateUser);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			sysUser.setLanguage(language);
//		}

	}


	public static void addIsolate(Example example) {
		try {
			SysUser sysUser = getSysUser();
			if(sysUser!=null) {
				List<Criteria> oredCriteria = example.getOredCriteria();
				Criteria criteria =null;
				if(!CollectionUtils.isEmpty(oredCriteria)) {
					criteria=oredCriteria.get(0);
				}
				if(criteria==null) {
					criteria = example.createCriteria();
					criteria.andIn("deptId", sysUser.getSysDeptIds());
					example.and(criteria);	
					return ;
				}
				criteria.andIn("deptId", sysUser.getSysDeptIds());
				return ;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	public static SysUser getSysUser() {
		return MyContextHolder.getContext().getSysUser();
	}

	

	
	
//	public static void addIsolate(Example example) throws Exception {
//		SysUser sysUser = getSysUser(false);
//		if(sysUser==null) {
//			return ;
//		}
//		Criteria criteria = example.createCriteria();
//		criteria.andIn("deptId", sysUser.getSysDeptIds());
//		example.and(criteria);	
//	}
	
}
