package com.laison.erp.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lihua
 * @ClassName: R
 * @Description: 返回的数据
 * @date 2017年11月6日 上午9:43:15
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /*
     * private Object data;
     *
     * public Object getData() { return data; }
     *
     * public void setData(Object data) { this.data = data; }
     */
    public static final int NEED_BIND = 466;

    /**
     * 执行中
     */
    private static final int WS_RUNNING = 0;

    /**
     * 失败
     */
    private static final int WS_ERROR = -1;

    /**
     * 执行完成
     */
    private static final int WS_END = 1;

    public Result() {
        put("errcode", 0);
        put("timestamp", System.currentTimeMillis());
    }

    public static Result error() {
        Result r = new Result();
        r.put("errcode", 500);
        r.put("errmsg", "请求失败");
        return r;
    }

    public static Result error(String errmsg) {
        return error(500, errmsg);
    }

    public static Result error(int errcode, String errmsg) {
        Result r = new Result();
        r.put("errcode", errcode);
        r.put("errmsg", errmsg);
        return r;
    }

    public static Result ok(String errmsg) {
        Result r = new Result();
        r.put("errcode", 0);
        r.put("errmsg", errmsg);

        return r;
    }

    public static Result okData(Object data) {
        Result r = new Result();
        r.put("errcode", 0);
        r.put("errmsg", "ok");
        r.put("result", data);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        r.put("errcode", 0);
        return r;
    }

    public static Result ok() {
        Result r = new Result();
        r.put("errcode", 0);
        r.put("errmsg", "ok");
        return r;
    }

    public static Result BAD_REQUEST(String message) {
        Result r = new Result();
        r.put("errcode", HttpStatus.BAD_REQUEST.value());
        r.put("errmsg", message);
        return r;
    }

    public static Result WS_RUNNING() {
        Result r = new Result();
        r.put("errcode", 0);
        r.put("operateState", WS_RUNNING);
        return r;
    }

    public static Result WS_END() {
        Result r = new Result();
        r.put("errcode", 0);
        r.put("operateState", WS_END);
        return r;
    }

    public static Result WS_ERROR() {
        Result r = new Result();
        r.put("errcode", 0);
        r.put("operateState", WS_ERROR);
        return r;
    }

    public static Map<String, Object> INTERNAL_SERVER_ERROR(String message) {
        Result r = new Result();
        r.put("errcode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        r.put("errmsg", message);
        return r;
    }
}
