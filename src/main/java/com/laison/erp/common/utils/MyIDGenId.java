package com.laison.erp.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import tk.mybatis.mapper.genid.GenId;

public class MyIDGenId implements GenId<String> {
	private static Snowflake snowflake = IdUtil.createSnowflake(0l,0l);
    @Override
    public String genId(String s, String s1) {
        return snowflake.nextId()+"";
    }
    
    
    public static String genID() {
    	return snowflake.nextId()+"";
    }
}