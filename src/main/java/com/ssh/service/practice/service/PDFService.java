package com.ssh.service.practice.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class PDFService {
	public OutputStream produce(InputStream template, Map<String, String> params,OutputStream outputStream) throws IOException, DocumentException {

		PdfStamper ps = new PdfStamper(new PdfReader(template), outputStream);

		AcroFields acroFields = ps.getAcroFields();
		File fontFile = ResourceUtils.getFile("classpath:font/simhei.ttf");
		BaseFont bf = BaseFont.createFont(fontFile.getPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		acroFields.addSubstitutionFont(bf);
		Float f = 9f;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			//设置字体类型和大小
/*			acroFields.setFieldProperty(entry.getKey(),"textsize", f, null);
			acroFields.setFieldProperty(entry.getKey(),"textfont", bf, null);*/
			acroFields.setField(entry.getKey(),entry.getValue());
		}

		ps.setFormFlattening(true);
		ps.close();
		return outputStream;
	}
}
