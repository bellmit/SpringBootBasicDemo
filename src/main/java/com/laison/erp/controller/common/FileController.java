package com.laison.erp.controller.common;


import com.laison.erp.common.Result;
import com.laison.erp.common.utils.MyFileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
@RequestMapping("/file")
public class FileController {
	
	@PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file")  MultipartFile file) throws IOException {
        if (file.isEmpty() || file == null) {
        	Result error = Result.error("上传失败，请选择文件");
            return error;
        }
    	String name = file.getOriginalFilename();	
		InputStream inputStream = file.getInputStream();
		String path = MyFileUtils.uploadFile(inputStream, name);
		Result ok = Result.ok();
		ok.put("fileLocation", path);
        return ok;
    }

	
	@RequestMapping("/download")
	private void downloadFile(HttpServletResponse response,@RequestParam("path") String path) throws Exception{	
		if(!path.startsWith("/")) {
			throw new Exception("路径错误");
		}
		
		File file =MyFileUtils.getFile(path);
		if (!file.exists()) {
			throw new Exception("文件不存在");
		}else {
			if(file.isDirectory()) {
				throw new Exception("文件不存在");
			}
			String[] split = path.split("/");
			String fileName=split[split.length-1];
			response.setContentType("application/force-download");// 设置强制下载不打开            
			response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("Download the song successfully!");
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
								e.printStackTrace();
					}
				}
			}
		}
	}

}
