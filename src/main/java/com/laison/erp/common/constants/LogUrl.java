package com.laison.erp.common.constants;
import java.util.Collections;
import java.util.HashSet;

/**
 * 需要放开权限的url
 *
 * @author lihua
 */
public final class LogUrl {

    /**
     * 监控中心和swagger需要访问的url
     */
    private static  String[] ENDPOINTS = {"/oauth/token"};
    private static  HashSet<String> URLS;

    /**
     	* 需要放开权限的url
     *
     * @param urls 自定义的url
     * @return 自定义的url和监控中心需要访问的url集合
     */
    public static String[] logAllUrl(String... urls) {
        if (urls == null || urls.length == 0) {
            return ENDPOINTS;
        }
        URLS = new HashSet<>();
        Collections.addAll(URLS, ENDPOINTS);
        Collections.addAll(URLS, urls);
        ENDPOINTS=  URLS.toArray(new String[URLS.size()]);
        return ENDPOINTS;
    }

    public static HashSet<String> getLogUrl() {
        return URLS;
    }
}