package com.laison.erp.config.invoice;


import com.laison.erp.common.utils.IdUtil;
import com.laison.erp.common.utils.SequenceUtil;

public class DefaultInvoiceCodeGenerator implements InvoiceCodeGenerator {
	@Override
	public String generatInvoiceCode() {
		try {
			return SequenceUtil.generateInvoice()+"";
		} catch (Exception e) {
			return IdUtil.nextId()+"";
		}
		
	}
}
