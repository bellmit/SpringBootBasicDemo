package com.laison.erp.common.constants;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 需要放开权限的url
 *
 * @author lihua
 */
public final class PermitAllUrl {

    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {SocialConfigConstants.SOCIAL_BIND,SocialConfigConstants.SOCIAL_SIGNUP,SocialConfigConstants.SOCIAL_LOGIN+"/*","/wechat/*","/hello.do","/file/*","/weixin","/login/qq","/","/index.html","/actuator/health", "/actuator/env", "/actuator/metrics/**", "/actuator/trace", "/actuator/dump",
            "/lapisCtwingDataChanged","/activiti/**","/static/**", "/actuator/jolokia", "/sysMenu/**","/sysMenu/findById/**", "/actuator/info", "/actuator/logfile", "/actuator/refresh", "/actuator/flyway", "/actuator/liquibase",
            "/actuator/heapdump", "/actuator/loggers", "/actuator/auditevents", "/actuator/env/PID", "/actuator/jolokia/**",
            "/v2/api-docs/**","/systemInfo/findAll", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**","/login","/vcode","/cardUtil/**","/sys/logout/**","/websocket/**","/app/**","/mobile/**","/file/**","/app/checkAppUpdate"};
//, "/oauth/token"
    /**
     	* 需要放开权限的url
     *
     * @param urls 自定义的url
     * @return 自定义的url和监控中心需要访问的url集合
     */
    public static String[] permitAllUrl(String... urls) {
        if (urls == null || urls.length == 0) {
            return ENDPOINTS;
        }
        Set<String> set = new HashSet<>();
        Collections.addAll(set, ENDPOINTS);
        Collections.addAll(set, urls);
        return set.toArray(new String[set.size()]);
    }

}