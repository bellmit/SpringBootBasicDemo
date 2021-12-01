package com.laison.erp.common.utils;

import com.laison.erp.config.exception.CustomerException;
import okhttp3.*;

import java.io.IOException;
import java.util.*;

/**
 * @author xiaobu
 * @version JDK1.8.0_171
 * @date on 2019/3/4 10:56
 * @description V1.0
 */
public class OkHttp3Util {
	
	

	public static String HOST = "http://192.168.0.38:9999";
	// MEDIA_TYPE <==> Content-Type
	private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
	// MEDIA_TYPE_TEXT
	// post请求不是application/x-www-form-urlencoded的，全部直接返回，不作处理，即不会解析表单数据来放到request
	// parameter map中。所以通过request.getParameter(name)是获取不到的。只能使用最原始的方式，读取输入流来获取。
	private static final MediaType MEDIA_TYPE_TEXT = MediaType
			.parse("application/x-www-form-urlencoded; charset=utf-8");

	private static String TOKEN = "9063d0c0-1149-40d4-9cb4-00e3eb61dd32";

	public static final int UNAUTHORIZED = 401; // 未登錄 ，或者認證失敗

	public static final int FORBIDDEN = 403;

	/**
	 * @param url getUrl
	 * @return java.lang.String
	 * @author xiaobu
	 * @date 2019/3/4 11:20
	 * @descprition
	 * @version 1.0
	 */
	public static String sendByGetUrl(String url) throws Exception {
		String result;
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(HOST + url).build();

		if (!url.equals("/oauth/token")) {

			request = new Request.Builder().url(HOST + url).addHeader("Authorization", "Bearer " + TOKEN).build();

		} else {
			request = new Request.Builder().url(HOST + url).build();
		}
		Response response = null;
		try {
			response = client.newCall(request).execute();
			assert response.body() != null;
			result = response.body().string();
			checkResult(response.code(), result);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param url , json
	 * @return java.lang.String
	 * @author xiaobu
	 * @date 2019/3/4 11:13
	 * @descprition
	 * @version 1.0 post+json方式
	 */
	public static String sendByPostJson(String url, String json) throws Exception {
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);

		Request request;
		if (!url.equals("/oauth/token")) {
			request = new Request.Builder().url(HOST + url).addHeader("Authorization", "Bearer " + TOKEN).post(body)
					.build();

		} else {
			request = new Request.Builder().url(HOST + url).post(body).build();
		}
		Response response = null;
		try {
			response = client.newCall(request).execute();
			assert response.body() != null;
			String result = response.body().string();
			
			checkResult(response.code(), result);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author xiaobu
	 * @date 2019/3/4 15:58
	 * @param url , params]
	 * @return java.lang.String
	 * @descprition post方式请求
	 * @version 1.0
	 */
	public static String sendByPostMap(String url, Map<String, String> params) throws Exception {
		String result;
		OkHttpClient client = new OkHttpClient();
		StringBuilder content = new StringBuilder();
		Set<Map.Entry<String, String>> entrys = params.entrySet();
		Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			content.append(entry.getKey()).append("=").append(entry.getValue());
			if (iterator.hasNext()) {
				content.append("&");
			}
		}

		RequestBody requestBody = RequestBody.create(MEDIA_TYPE_TEXT, content.toString());
		Request request = new Request.Builder().url(HOST + url).post(requestBody).build();
		if (!url.equals("/oauth/token")) {
			request = new Request.Builder().url(HOST + url).addHeader("Authorization", "Bearer " + TOKEN)
					.post(requestBody).build();

		} else {
			request = new Request.Builder().url(HOST + url).post(requestBody).build();
		}
		Response response = null;

		response = client.newCall(request).execute();

		assert response.body() != null;
		result = response.body().string();
		checkResult(response.code(), result);
		System.out.println("result = " + result);
		return result;

	}

	private static void checkCode(Response response) {
		System.out.println(response.code());

	}

	private static void checkResult(Integer httpstatus, String result) throws Exception {
		if(result.startsWith("{")) {
			HashMap resultmap = JsonUtils.jsonToPojo(result, HashMap.class);
			if (httpstatus.equals(UNAUTHORIZED)) {

				Object description = resultmap.get("error_description");
				if (description == null) {
					description = resultmap.get("error");
				}
				throw CustomerException.create(UNAUTHORIZED, description == null ? "你要改代碼" : description.toString());
			}

			Object error = resultmap.get("errors");
			if (error != null) {
				throw CustomerException.create(error.toString());
			}
		}
		
	}

	
	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param clazz  想转换成什么类
	 * @param clazz2 如果clazz是数组  那么数组里面的详细类  如果不是数组clazz传null
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T>  T sendByGetUrl(String url, Class<T> clazz, Class<?> clazz2) throws Exception {
		String reStr = sendByGetUrl(url);
		if (clazz.isArray()) {
			 List<?> list = JsonUtils.jsonToList(reStr, clazz2);
			return (T)list;		
		}
		T pojo = JsonUtils.jsonToPojo(reStr, clazz);
		return pojo;
	}

	/**
	 * 	数组（List）类型不要掉用
	 * @param <T>
	 * @param url
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T>  T sendByGetUrl(String url, Class<T> clazz) throws Exception {
		// TODO Auto-generated method stub
		return sendByGetUrl(url, clazz,null);
	}
	
	public static <T>  T sendByPostMap(String url, Map<String, String> params, Class<T> clazz, Class<?> clazz2) throws Exception {
		String reStr = sendByPostMap(url, params);
		if (clazz.isArray()) {
			 List<?> list = JsonUtils.jsonToList(reStr, clazz2);
			return (T)list;
			
		}
		T pojo = JsonUtils.jsonToPojo(reStr, clazz);
		return pojo;
	}

	/**
	 * 	数组（List）类型不要掉用
	 * @param <T>
	 * @param url
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T>  T sendByPostMap(String url, Map<String, String> params, Class<T> clazz) throws Exception {
		return sendByPostMap(url,  params, clazz,null);
	}
	
	public static <T>  T sendByPostJson(String url, String json, Class<T> clazz, Class<?> clazz2) throws Exception{
		String reStr = sendByPostJson(url, json);
		if (clazz.isArray()) {
			 List<?> list = JsonUtils.jsonToList(reStr, clazz2);
			return (T)list;	
		}
		T pojo = JsonUtils.jsonToPojo(reStr, clazz);
		return pojo;
	}
	public static <T>  T sendByPostJson(String url, String json, Class<T> clazz) throws Exception{
		return sendByPostJson(url, json, clazz);
	}
}
