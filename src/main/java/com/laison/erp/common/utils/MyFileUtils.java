package com.laison.erp.common.utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.laison.erp.common.constants.ConfigConstant;
import org.apache.commons.io.FileUtils;


import lombok.extern.log4j.Log4j2;  


@Log4j2
public class MyFileUtils {
	public static final String basePath= ConfigConstant.FILE_PATH;
	public static final Set<PosixFilePermission> perms;
	static {
		  perms = new HashSet<PosixFilePermission>();
		   perms.add(PosixFilePermission.OWNER_READ); //设置所有者的读取权限
		   perms.add(PosixFilePermission.OWNER_WRITE); //设置所有者的写权限
		   perms.add(PosixFilePermission.OWNER_EXECUTE); //设置所有者的执行权限
		   perms.add(PosixFilePermission.GROUP_READ); //设置组的读取权限
		   perms.add(PosixFilePermission.GROUP_EXECUTE); //设置组的读取权限
		   perms.add(PosixFilePermission.OTHERS_READ); //设置其他的读取权限
		   perms.add(PosixFilePermission.OTHERS_EXECUTE); //设置其他的读取权限
	}
	//public static final String basePath= "/data/html/file";
	public static String uploadFile(InputStream inputStream,String name) {
		Random random = new Random();
		int path1 =random.nextInt(1000)+1000;
		int path2 =random.nextInt(1000)+1000;
		//String path2 = IdGen.get4UUID();
		StringBuilder sb = new StringBuilder();
		sb.append(basePath).append("/").append(path1).append("/").append(path2).append("/").append(name);
		String dir =sb.toString();
		
		File destination =new File(dir);
		try {	
			 
			
			FileUtils.copyInputStreamToFile(inputStream, destination);	
			File fisrtDir = destination.getParentFile();
			File secendDir = fisrtDir.getParentFile();
			Path path = Paths.get(destination.getAbsolutePath()); 
			String os = System.getProperty("os.name");  
			if(os.toLowerCase().startsWith("win")){  
				
			}else {
				Files.setPosixFilePermissions(path, perms);
				Files.setPosixFilePermissions(Paths.get(fisrtDir.getAbsolutePath()), perms);
				Files.setPosixFilePermissions(Paths.get(secendDir.getAbsolutePath()), perms);
			}
			
			
		} catch (IOException e) {
			log.error(e);
		}
		sb.delete( 0, sb.length() );
		sb.append("/").append(path1).append("/").append(path2).append("/").append(name);
		return sb.toString();
		
	}
	public static String uploadFileTemp(File file) {
		
		String path1 = UUIDutil.uuid();
		String path2 = UUIDutil.uuid();
		StringBuilder sb = new StringBuilder();
		sb.append(basePath).append("/").append(path1).append("/").append(path2).append("/");
		String dir =sb.toString();
		
		File destDir =new File(dir);
		try {
			FileUtils.copyFileToDirectory(file, destDir);
		} catch (IOException e) {
			log.error(e);
		}
		return "";
		
	}
	public static File getFile(String url) {
		return FileUtils.getFile(basePath+url);
	}
}
