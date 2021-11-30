package com.laison.erp.common.utils;


import com.laison.erp.service.sys.SysMenuService;

import java.util.HashSet;
import java.util.List;



public class TranslateSet {
	
	public static HashSet<String> translateSet;
	
	static {
		translateSet=new HashSet<>();
		//获取菜单列表加入需要翻译的
		SysMenuService sysMenuService = SpringContextUtils.getBean(SysMenuService.class);
		List<String> menus = sysMenuService.findAllMenus();
		translateSet.addAll(menus);
		translateSet.add("不允许访问");
		translateSet.add("用户不存在");
		translateSet.add("行政区域");
		translateSet.add("组织架构");
		translateSet.add("苏伊士CPU卡表");
		translateSet.add("中国电信NB蓝牙表");
		translateSet.add("组织架构");
		translateSet.add("管理员");
		
	}

	public static Boolean contains(String content) {
		return translateSet.contains(content);
	}
}
