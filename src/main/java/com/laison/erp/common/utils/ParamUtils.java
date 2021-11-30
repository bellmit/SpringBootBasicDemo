package com.laison.erp.common.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class ParamUtils {

	private static HashSet<String> dateTypes;
	static {
		// %Y-%m-%d 按日 默认, dateTyep=%Y-%u 按周, dateTyep=%Y-%m 按月 dateTyep=%Y
		dateTypes=new HashSet<>();
		dateTypes.add("%Y-%m-%d");
		dateTypes.add("%Y-%u");
		dateTypes.add("%Y-%m");
		dateTypes.add("%Y");
		
	}

	/**
	 * 可能返回null
	 * 
	 * @param params
	 * @return
	 * @throws ParseException
	 */
	public static Date getEndTime(Map<String, Object> params) throws ParseException {

		Object param = params.get("endTime");
		if (param == null) {
			return null;
		} else {
			if(param instanceof Date) {
				return (Date) param;
			}
			return DateUtils.parseDate((String) param);
		}

	}

	/**
	 * 	可能返回null
	 * 
	 * @param params
	 * @return
	 * @throws ParseException
	 */
	public static Date getStartTime(Map<String, Object> params) throws ParseException {

		Object param = params.get("startTime");
		if (param == null) {
			return null;
		} else {
			if(param instanceof Date) {
				return (Date) param;
			}
			return DateUtils.parseDate((String) param);
		}

	}

	public static void setTimeRange(Map<String, Object> params) throws ParseException {

		Object startTime = params.get("startTime");
		if (startTime == null) {
			params.put("startTime", DateUtils.getStartOfDay());
		} else {
			if(startTime instanceof Date) {
				params.put("startTime",  startTime);
			}else {
				params.put("startTime", DateUtils.parseDate((String) startTime));
			}
		}

		Object endTime = params.get("endTime");
		if (endTime == null) {
			params.put("endTime", DateUtils.date());
		} else {
			if(endTime instanceof Date) {
				params.put("endTime",  endTime);
			}else {
				params.put("endTime", DateUtils.parseDate((String) endTime));
			}
			
			
		}

	}

	public static void setTypes(Map<String, Object> params) throws ParseException {

		Object types = params.get("types");
		if (types != null) {
			if (types instanceof String) {
				String[] split = ((String) types).split(",");
				Integer[] typeArray = new Integer[split.length];
				for (int i = 0; i < split.length; i++) {
					typeArray[i] = Integer.parseInt(split[i]);
				}
				params.put("types", typeArray);
			}
		}

	}


	public static void setDateType(HashMap<String, Object> params) throws Exception{
		 Object object = params.get("dateType");
		 if(object != null && dateTypes.contains(object)) { 	
			 params.put("dateType", object);
			 return;
		 } 
		params.put("dateType","%Y-%m-%d");
	}
	/**
	 * @Description: 获取int 如果没有则返回默认值
	 */
	public static Integer getIntD(Map<String, ? extends Object> params, String paramName, Integer defaultp) {
		Object object = params.get(paramName);
		if (object == null) {
			return defaultp;
		} else {
			String param = object.toString();
			if (StringUtils.isNotBlank(param)) {
				try {
					return Integer.parseInt(param);
				} catch (NumberFormatException e) {
					return defaultp;
				}

			} else {
				return defaultp;
			}
		}
	}
//	/**
//	 * @Description: 获取int 如果没有则返回 null
//	 */
//	public static Integer getInt(Map<String, ? extends Object> params, String paramName) {
//		Object object = params.get(paramName);
//		if (object == null) {
//			return null;
//		} else {
//			String param = object.toString();
//			if (StringUtils.isNotBlank(param)) {
//				try {
//					return Integer.parseInt(param);
//				} catch (NumberFormatException e) {
//					return null;
//				}
//
//			} else {
//				return null;
//			}
//		}
//
//	}

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
		String startTime = getStringWE(params, "startTime", "startTime 不能为空 ");//图表是日数据还是月数据
		String endTime = getStringWE(params, "endTime", "endTime 不能为空 ");//图表是日数据还是月数据
		params.put("startTime", DateUtils.parse(startTime));
		params.put("endTime", DateUtils.parse(endTime));


	}
}
