package com.ssh.service.practice.controller;

import com.ssh.service.practice.common.HttpResult;
import com.ssh.service.practice.domain.Student;
import com.ssh.service.practice.service.TestService;
import com.ssh.service.practice.validation.TransParam;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import javax.transaction.SystemException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

@RestController
@RequestMapping(value = "/test", consumes = MediaType.TEXT_PLAIN_VALUE)
public class TestController {

	public static void main(String[] args) throws Exception{
		String responseBody = "<xml><appid><![CDATA[wx2d5abea4e879c387]]></appid><attach><![CDATA[99:1000000184]]></attach></xml>";
		DocumentBuilderFactory documentBuilderFactory =DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setExpandEntityReferences(false);

		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

		Reader reader = new StringReader(responseBody);
		InputSource inputSource = new InputSource(reader);
		Document document = documentBuilder.parse(inputSource);
		DOMSource source = new DOMSource(document);

		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		Map<String,String> result = (Map<String, String>) jaxb2Marshaller.unmarshal(source);
		System.out.println("result"+result.toString());
	}

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	TestService testService;

	//aop暴露代理对象 实现service内部调用方法 事物管理
	@PostMapping(value = "/test/aop")
	public HttpResult<Student> testAopContext(@Validated(TransParam.class) @RequestBody Student student) throws SystemException {
		testService.testAopContext(student);
		return HttpResult.OK(student);
	}

	//手动新建事务 实现service内部调用方法 事物管理
	@PostMapping(value = "/test/transaction")
	public HttpResult<Student> testTransaction(@Validated(TransParam.class) @RequestBody Student student) throws SystemException {
		testService.testTransaction(student);
		return HttpResult.OK(student);
	}
}
