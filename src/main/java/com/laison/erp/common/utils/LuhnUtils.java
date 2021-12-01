package com.laison.erp.common.utils;
/**
 * 
* @ClassName: LuhnUtils 
* @Description: LuhnUtils.isLegal(123123) 用luhn算法验证一个数字是否合法
* 
* @author lihua
* @date 2017年10月25日 下午2:29:43 
*
 */
public class LuhnUtils {  
    public static boolean isLegal(String number) {  
    
    	try {
			Long.parseLong(number);
		} catch (NumberFormatException e) {//不是纯数字直接返回false
			return false; 
		}
        
        int sum = 0;  
        
        int length = number.length();  
        int[] wei = new int[length];  
        for (int i = 0; i < number.length(); i++) {  
            wei[i] = Integer.parseInt(number.substring(length - i - 1, length- i));// 从最末一位开始提取，每一位上的数值  
        }  
       
    
        for (int i = 0; i < length; i++) {
			if(i%2!=0){//偶数位
				if(wei[i]*2>=10) { //乘2大于等于10
					wei[i]=wei[i]*2-9;
				}else {
					wei[i]=wei[i]*2;
				}
			}
			sum += wei[i];
		}
        
		return sum % 10 == 0;  
    }  

    public static Integer getCheckCode(String numberStr) { 	        	
        
        int sum = 0;  
       
        int length = numberStr.length();  
        int[] wei = new int[length];  
        for (int i = 0; i < numberStr.length(); i++) {  
            wei[i] = Integer.parseInt(numberStr.substring(length - i - 1, length- i));// 从最末一位开始提取，每一位上的数值  
        }  
       
    
        for (int i = 0; i < length; i++) {
			if(i%2==0){//偶数位
				if(wei[i]*2>=10) { //乘2大于等于10
					wei[i]=wei[i]*2-9;
				}else {
					wei[i]=wei[i]*2;
				}
			}
			sum += wei[i];
			
		}
		return (10-(sum % 10))%10;  
    }

	public static void main(String[] args) {
		String me1 = "2021101500001";
		String me2 = "2021101500100";
		System.out.println(isLegal(me1));
		System.out.println(isLegal(me2));
	}
}  