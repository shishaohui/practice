package com.ssh.service.practice.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

/**
 * 基于itext5实现pdf模板填充
 */
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

	/**
	 * 展示TextField的一些属性设置
	 * @param pdfStamper
	 * @param reader
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void creatTextField(PdfStamper pdfStamper, PdfReader reader) throws IOException, DocumentException {
		PdfWriter writer = pdfStamper.getWriter();
		PdfFormField form = PdfFormField.createEmpty(writer);
		//普通文本框
		TextField  field = new TextField(writer, new Rectangle(200, 200, 400, 300), "text");
		field.setOptions(TextField.MULTILINE);
		//防止读取pdf文档时，就是有旋转角度的
		field.setRotation(reader.getPageRotation(1));
		//有些情况下，页数问题可以在这里设置，优先级最高
		field.getTextField().setPlaceInPage(1);
		form.addKid(field.getTextField());
/*		field.setOptions(TextField.VISIBLE);//文本域可见(相对于文本域是否高亮)
		field.setOptions(TextField.HIDDEN);//文本域不可见
		field.setOptions(TextField.VISIBLE_BUT_DOES_NOT_PRINT);//该字段可见，但不打印。
		field.setOptions(TextField.HIDDEN_BUT_PRINTABLE);//该字段不可见，但不打印。
		field.setOptions(TextField.HIDDEN_BUT_PRINTABLE);//该字段不可见，但不打印。
		field.setOptions(TextField.READ_ONLY);//只读
		field.setOptions(TextField.REQUIRED);//该字段在通过提交表单操作导出时必须具有值。
		field.setOptions(TextField.MULTILINE);//规定区域内可以换行显示
		field.setOptions(TextField.DO_NOT_SCROLL);//文本域不会有滚动条,对于单行字段为水平，对于多行字段为垂直,一旦区域满了，将不会再接受任何文字。
		field.setOptions(TextField.PASSWORD);//该字段用于输入安全密码，该密码不应该被可视地显示在屏幕上。
		field.setOptions(TextField.FILE_SELECTION);//个人理解好像是上传文件，不是很理解
		field.setOptions(TextField.DO_NOT_SPELL_CHECK);//无拼写检查
		field.setOptions(TextField.EDIT);//如果设置组合框包括一个可编辑的文本框以及一个下拉列表;如果清楚，它只包括一个下拉列表。这个标志只对组合字段有意义。
		field.setOptions(TextField.MULTISELECT);//不管列表是否可以有多个选择。仅适用于/ ch列表字段，而不适用于组合框。
		field.setOptions(TextField.COMB);//组合框标志。*/
		pdfStamper.addAnnotation(form,1);
	}
}
