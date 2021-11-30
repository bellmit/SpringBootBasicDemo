package com.laison.erp.config.auth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laison.erp.common.utils.I18NResourceBundleUtils;

import java.io.IOException;
import java.util.Map;

public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }
   
    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        

        gen.writeStartObject();
        
        gen.writeNumberField("code", value.getHttpErrorCode());
        gen.writeStringField("errors", I18NResourceBundleUtils.getLocalizedText(value.getMessage()));
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, I18NResourceBundleUtils.getLocalizedText(add));
            }
        }
        gen.writeEndObject();
    }
}