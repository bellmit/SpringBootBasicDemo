package com.laison.erp.common.utils;
import java.util.Properties;

public class PropertiesUtils {  
      
    private static Properties properties=new Properties();  
      
    static{  
        try {  
            //注意属性配置文件所在的路径  
            properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream("db.properties"));  
        } catch (Exception e) {  
            //System.out.println(e.getMessage());  
        }  
    }  
      
    //读取属性配置文件中的某个属性对应的值  
    public static String readProperty(String property){  
        return (String) properties.get(property);  
    }  
          
}  