package com.laison.erp.common.utils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * JsonUtils
 */
public class JsonUtils {


	//private static final ObjectMapper MAPPER = new I18NObjectMapper();
	private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
    	MAPPER.setSerializationInclusion(Include.NON_NULL); 
    	MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    	MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); 
    }
    /**
     *	 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJsonWhitI18N(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
   public static String objectToJson(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
	/**
	 * 将json结果集转化为对象
	 *
	 * @param jsonData json数据
	 * @param beanType 对象中的object类型
	 * @return
	 */
	public static <T> T jsonToPojo(Object jsonData, Class<T> beanType) {
		try {
			T t = MAPPER.readValue(objectToJson(jsonData), beanType);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    
    
    public static class I18NObjectMapper extends ObjectMapper{

	   	private static final long serialVersionUID = 1L;
	
	   	public I18NObjectMapper(){  
	          super();  
	          this.setSerializationInclusion(Include.NON_NULL); 
	          SimpleModule module = new SimpleModule();
	          module.addSerializer(String.class, new I18NStringSerializer());//翻译字符串
	          //module.addSerializer(Long.class, ToStringSerializer.instance);
	          //module.addSerializer(Long.TYPE, ToStringSerializer.instance);//解决long精度丢失问题
	          this.registerModule(module);
	       }  
	   }
    
    public static class I18NStringSerializer extends   JsonSerializer<String>{

		@Override
		public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			    gen.writeString(value);
		}
    	
    }
}
