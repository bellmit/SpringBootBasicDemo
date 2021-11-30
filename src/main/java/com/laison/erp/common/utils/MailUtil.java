package com.laison.erp.common.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


import cn.hutool.extra.mail.MailAccount;
import com.laison.erp.model.sys.Config;
import com.laison.erp.model.sys.SysUser;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MailUtil extends cn.hutool.extra.mail.MailUtil {
	
	private static MailAccount account;
	private static  ScheduledExecutorService ex=Executors.newScheduledThreadPool(2);//执行业务的线程池   用于dcu的同步通信

	static {
		account = new MailAccount();
		account.setHost("smtp.exmail.qq.com");
		account.setPort(25);
		account.setAuth(true);
		account.setFrom("software@laisontech.com");
		account.setUser("software@laisontech.com");
		account.setPass("Laison4u123"); //密码
	}
	

	public static void sendMail(String content,String title,String emailSet ,boolean ishtml ) {
		
	    MailAccount eMailAccount=account;
		SysUser sysUser = LoginUserUtil.getSysUser();
		if(sysUser!=null) {
			Config config = sysUser.getSysDept().getConfig();
			String mailConfigString = config.getMailConfigString();
			String[] mailconfig = mailConfigString.split("\\|");
			eMailAccount = new MailAccount();
			eMailAccount.setHost(mailconfig[0]);
			eMailAccount.setPort(Integer.parseInt(mailconfig[1]));
			eMailAccount.setAuth(true);
			eMailAccount.setFrom(mailconfig[2]);
			eMailAccount.setUser(mailconfig[3]);
			eMailAccount.setPass(mailconfig[4]); //密码
		}
		final  MailAccount feMailAccount =eMailAccount;
		ex.submit(() ->{
			cn.hutool.extra.mail.MailUtil.send(feMailAccount, emailSet,title,content, ishtml);
		});
		
	}
	
	public static void sendPassword(String account,String password,String emailSet  ) {
		String tempFormatString="<h1>%s<font color=\"blue\">%s</font></h1>\r\n"
				              + "<h1>%s<font color=\"blue\">%s</font></h1>";
		String title = I18NResourceBundleUtils.getLocalizedText("{email.account.title}");
		String acc =  I18NResourceBundleUtils.getLocalizedText("{email.account}")+":";//您的账号
		String pass =  I18NResourceBundleUtils.getLocalizedText("{email.pass}")+":";//您的账号
		String message = String.format(tempFormatString, acc,account, pass,password);
		log.info("发送邮件"+message);
		sendMail(message,title,emailSet,true);
	}
}
