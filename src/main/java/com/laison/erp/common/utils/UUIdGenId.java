package com.laison.erp.common.utils;

import cn.hutool.core.util.IdUtil;
import tk.mybatis.mapper.genid.GenId;

public class UUIdGenId implements GenId<String> {
    @Override
    public String genId(String s, String s1) {
        return IdUtil.fastSimpleUUID();
    }
}