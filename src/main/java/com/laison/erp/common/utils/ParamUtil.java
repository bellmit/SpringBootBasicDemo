package com.laison.erp.common.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ParamUtil {


    public static Long getLongWE(Map<String, Object> param, String keyname) throws Exception {


        return getLongWE(param, keyname, keyname + "can not be null");

    }

    public static <T> T getValueWE(Map<String, Object> param, String keyname, Class<T> beanType) throws Exception {
        Object object = param.get(keyname);
        if (object == null) {
            throw new Exception(keyname + "can not be null");
        }

        return (T) object;

    }

    public static Long getLongWE(Map<String, Object> param, String key, String errorMsg) throws Exception {
        Object object = param.get(key);
        if (object == null)
            throw new Exception(errorMsg);

        return Long.parseLong(object.toString());

    }

    public static BigDecimal getBigDecimalWE(Map<String, Object> param, String keyname) throws Exception {

        return getBigDecimalWE(param, keyname, keyname + "can not be null");
    }


    public static BigDecimal getBigDecimalWE(Map<String, Object> param, String key, String errorMsg) throws Exception {
        Object object = param.get(key);
        if (object == null)
            throw new Exception(errorMsg);

        return new BigDecimal(object.toString());

    }

    public static BigDecimal getBigDecimal(Map<String, Object> param, String key) {
        Object object = param.get(key);
        if (object == null)
            return null;
        return new BigDecimal(object.toString());
    }

    public static String getStringWE(Map<String, Object> param, String key) throws Exception {
        return getValueWE(param, key, String.class, "{miss.key}" + "-" + key);
    }

    public static String getStringWE(Map<String, Object> param, String key, String errorMsg) throws Exception {
        return getValueWE(param, key, String.class, errorMsg);
    }

    public static <T> T getValueWE(Map<String, Object> param, String key, Class<T> clzzz, String errorMsg) throws Exception {

        T object = (T) param.get(key);
        if (object == null)
            throw new Exception(errorMsg);
        return object;
    }


    public static Integer getIntWE(Map<String, Object> condition, String key, String errorMsg) throws Exception {
        Object object = condition.get(key);
        if (object == null)
            throw new Exception(errorMsg);

        return Integer.parseInt(object.toString());
    }

    public static Integer getIntWE(Map<String, Object> condition, String key) throws Exception {
        return getIntWE(condition, key, "{miss.parameter}");

    }

    public static String getString(Map<String, Object> condition, String key) {
        if (condition == null || condition.size() < 1) return null;
        if (condition.containsKey(key)) return String.valueOf(condition.get(key));
        return null;
    }


    public static Boolean getBoolean(Map<String, Object> condition, String key) {
        if (condition == null || condition.size() < 1) return null;
        if (condition.containsKey(key)) return (Boolean) condition.get(key);
        return null;
    }

    public static Boolean getBoolean(Map<String, Object> condition, String key, boolean defaultVal) {
        if (condition == null || condition.size() < 1) return defaultVal;
        if (condition.containsKey(key)) return (Boolean) condition.get(key);
        return defaultVal;
    }

    public static Integer getInt(Map<String, Object> condition, String key) {
        if (condition == null || condition.size() < 1 || !condition.containsKey(key)) return null;
        String val = String.valueOf(condition.get(key));
        if (val.matches("\\d+")) {
            return Integer.parseInt(val);
        } else if (val.matches("\\d+||\\d*\\.\\d+||\\d*\\.?\\d+?e[+-]\\d*\\.?\\d+?||e[+-]\\d*\\.?\\d+?")) {
            double res = Double.parseDouble(val);
            return (int) res;
        }
        return null;
    }

    public static Integer StrToInt(String val) {
        if (StringUtils.isBlank(val)) {
            return null;
        }
        if (val.matches("\\d+")) {
            return Integer.parseInt(val);
        }
        return null;
    }

    public static Object getValue(Map<String, Object> pageInfo, String key) {
        if (pageInfo == null || pageInfo.size() < 1 || !pageInfo.containsKey(key)) return null;
        return pageInfo.get(key);
    }

    public static void setTimeRange(HashMap<String, Object> params) throws Exception {
        String startTime = ParamUtil.getStringWE(params, "startTime", "startTime 不能为空 ");//图表是日数据还是月数据
        String endTime = ParamUtil.getStringWE(params, "endTime", "endTime 不能为空 ");//图表是日数据还是月数据
        params.put("startTime", DateUtils.parse(startTime));
        params.put("endTime", DateUtils.parse(endTime));


    }
}
