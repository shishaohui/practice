/*
 * Copyright 2017 - 数多科技
 *
 * 北京数多信息科技有限公司 本公司保留所有下述内容的权利。
 * 本内容为保密信息，仅限本公司内部使用。
 * 非经本公司书面许可，任何人不得外泄或用于其他目的。
 */
package com.ssh.service.practice.service;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

/**
 * 基于itext7填充pdf模板
 */
@Service
public class PdfGeneratorService {

	@Value("${tempPath}")
	private String tempPath;

	@PostConstruct
	private void init() {
		File file = new File(tempPath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public byte[] produce(InputStream template, Map<String, String> params) throws IOException {

		File fontFile = ResourceUtils.getFile("classpath:font/simhei.ttf");
		PdfFont font = PdfFontFactory.createFont(fontFile.getPath(), PdfEncodings.IDENTITY_H, false);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfReader(template), new PdfWriter(outputStream));

		PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
		form.setGenerateAppearance(true);
		for (Map.Entry<String, String> entry : params.entrySet()) {

			PdfFormField field = form.getField(entry.getKey());
			if (Objects.nonNull(field)) {
				//如果不设置左对齐 内容会在一行显示，过长的话不会自动换行
				field.setValue(entry.getValue(), font, 10f).setJustification(PdfFormField.ALIGN_LEFT);
//				field.setValue(entry.getValue(), font, 10f);
			}
		}

		form.flattenFields(); //设置表单域内容不可编译，否则生成的pdf文件 填充内容是可以修改的
		pdfDoc.close();
		return outputStream.toByteArray();
	}

	public File generateFile(InputStream template, Map<String, String> params) throws IOException {
		String filename = UUID.randomUUID().toString().concat(".pdf");
		File tempFile = new File(tempPath, filename);
		OutputStream outputStream = new FileOutputStream(tempFile);
		outputStream.write(this.produce(template, params));
		outputStream.close();

		return tempFile;
	}
}
