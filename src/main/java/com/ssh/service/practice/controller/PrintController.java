/*
 * Copyright 2017 - 数多科技
 *
 * 北京数多信息科技有限公司 本公司保留所有下述内容的权利。
 * 本内容为保密信息，仅限本公司内部使用。
 * 非经本公司书面许可，任何人不得外泄或用于其他目的。
 */
package com.ssh.service.practice.controller;

import com.mongodb.gridfs.GridFSDBFile;
import com.ssh.service.practice.domain.Template;
import com.ssh.service.practice.service.PdfGeneratorService;
import com.ssh.service.practice.service.TemplateService;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 打印接口 </p>
 *
 * @author zhangmingming<zhangmingming01 @ we.com>
 * @version v1.0
 * @datetime 2018/6/8
 * @since JDK1.8
 */
@RestController
@RequestMapping(value = "/internal/v1.0/print", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class PrintController {

	@Autowired
	private TemplateService templateService;
	@Autowired
	private GridFsTemplate gridFsTemplate;
	@Autowired
	private PdfGeneratorService pdfGeneratorService;

	/**
	 * 打印pdf模板文件
	 * @param params
	 * @return pdf文件
	 * @throws IOException
	 */
	@PostMapping(value = "/pdf/file", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<byte[]> printPdfFile(@RequestBody Map<String, String> params) throws IOException {
		String templateId = params.get("templateId");
		Assert.isTrue(Objects.nonNull(templateId), "模板ID不能为空");
		Template template = templateService.get(Integer.valueOf(templateId));
		params.remove("templateId");
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(template.getBodyId()));
		GridFSDBFile gridFS = gridFsTemplate.findOne(query);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", "template.pdf");
		headers.setContentType(MediaType.APPLICATION_PDF);
		byte[] bytes = pdfGeneratorService.produce(gridFS.getInputStream(), params);
		return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
	}
}
