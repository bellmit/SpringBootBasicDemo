package com.laison.erp.config.filter;

import com.laison.erp.common.utils.HttpUtils;
import org.springframework.util.FastByteArrayOutputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**重写HttpServletRequestWrapper
 * 
 * @author 李华
 * @date 2016年12月7日 下午3:06:52
 *
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    private final FastByteArrayOutputStream content = new FastByteArrayOutputStream(1024);

    private BufferedReader reader;

    private ServletInputStream inputStream;

    public byte[] getBody() {
        return body;
    }

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = HttpUtils.getBodyString(request).getBytes(Charset.forName("UTF-8"));
    }


    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

      
        return new ServletInputStream() {
        	
            
			public boolean isFinished() {
                return true;
            }

            
           
			public boolean isReady() {
                return true;
            }

           
          
			public void setReadListener(ReadListener readListener) {
            }
            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
}