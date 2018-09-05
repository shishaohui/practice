package com.ssh.service.practice.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.util.ResourceUtils;

public class test {

	public static void main(String[] args) throws IOException, DocumentException {
		test();
	}

	private static void test() throws IOException, DocumentException {
		PdfReader reader = new PdfReader("temp/surgery.pdf"); // 模版文件目录
		OutputStream out = new FileOutputStream("D:\\surgery.pdf");
		PdfStamper ps = new PdfStamper(reader, out); // 生成的输出流
//		BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		AcroFields s = ps.getAcroFields();
		Float f = Float.valueOf(18);
		File fontFile = ResourceUtils.getFile("classpath:font/simhei.ttf");

		BaseFont bf = BaseFont.createFont(fontFile.getPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		s.addSubstitutionFont(bf);
//设置文本域表单的字体
// 对于模板要显中文的，在此处设置字体比在pdf模板中设置表单字体的好处：
//1.模板文件的大小不变；2.字体格式满足中文要求
		s.setFieldProperty("doctorAdvice", "textsize", f, null);
		s.setFieldProperty("test", "textsize", f, null);
		s.setFieldProperty("test", "textfont", bf, null);
//编辑文本域表单的内容
		s.setField("fill_3", "姚 秀 才");
		s.setField("fill_5", "cf");
		s.setField("fill_2", "cn-990000");
		s.setField("fill_4", "模版文件目录");
		s.setField("fill_6", "模版文件目录");
		s.setField("test", "一些疾病，如淋巴组\n织肿瘤，软组织肿瘤,\n内分泌肿瘤和一些需要计数分裂相和充分取材方能作出诊断的疾病，这些疾病若做冰");
		s.setField("doctorAdvice", "一些疾病，如淋巴组织肿瘤，软组织肿瘤,内分泌肿瘤和一些需要计数分裂相和充分取材方能作出诊断的疾病，这些疾病若做冰冻就是临床医生在给病理医生下套，使病理医生犯错误！ "
			+ "最后需要指出：术中冰冻不是最终诊断，仅是给外科医生的参考诊断，外科医生必须结合一切可能的其它指标综合考虑进行下一步。");
		ps.setFormFlattening(true);
		ps.close();
		reader.close();
	}
}
