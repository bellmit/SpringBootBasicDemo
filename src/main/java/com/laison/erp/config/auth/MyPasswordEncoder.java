package com.laison.erp.config.auth;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.regex.Pattern;

public class MyPasswordEncoder extends BCryptPasswordEncoder {
	private static BASE64Decoder decoder = new BASE64Decoder();
	private Pattern BCRYPT_PATTERN = Pattern
			.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
	
	@Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (encodedPassword == null || encodedPassword.length() == 0) {
			
			return false;
		}
		String base64 = rawPassword.toString();
		
		try {
			String pwd ;
			if(base64.equals("pos")||base64.equals("system")) {
				pwd=base64;
			}else {
				pwd=new String(decoder.decodeBuffer(base64));
			}
			return BCrypt.checkpw(pwd, encodedPassword);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
        
    }

}
