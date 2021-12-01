package com.laison.erp.config.invoice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceCodeConfig {

	
	@Bean
	public InvoiceCodeGenerator invoiceCodeGenerator() {
		return new DefaultInvoiceCodeGenerator();
	}
}
