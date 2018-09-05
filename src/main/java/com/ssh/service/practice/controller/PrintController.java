/*
 * Copyright 2017 - 数多科技
 *
 * 北京数多信息科技有限公司 本公司保留所有下述内容的权利。
 * 本内容为保密信息，仅限本公司内部使用。
 * 非经本公司书面许可，任何人不得外泄或用于其他目的。
 */
package com.ssh.service.practice.controller;

import com.itextpdf.text.DocumentException;
import com.mongodb.gridfs.GridFSDBFile;
import com.ssh.service.practice.common.HttpResult;
import com.ssh.service.practice.domain.Template;
import com.ssh.service.practice.service.PDFService;
import com.ssh.service.practice.service.PdfGeneratorService;
import com.ssh.service.practice.service.TemplateService;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.*;

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
	@Autowired
	private PDFService pdfService;

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

	@PostMapping(value = "/pdf/online", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpResult<Void> downloadOnline(@RequestBody Map<String, String> params,@RequestParam boolean online,HttpServletResponse response)
		throws IOException, DocumentException {

		String templateId = params.get("templateId");
		Assert.isTrue(Objects.nonNull(templateId), "模板ID不能为空");
		Template template = templateService.get(Integer.valueOf(templateId));
		params.remove("templateId");
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(template.getBodyId()));
		GridFSDBFile gridFS = gridFsTemplate.findOne(query);

		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setHeader("Content-Disposition", "attachment; filename=" + gridFS.getFilename());
		if(online){
			response.setHeader("Content-Disposition", "inline; filename=" + gridFS.getFilename());
		}

		OutputStream out = response.getOutputStream();
		pdfService.produce(gridFS.getInputStream(), params, out);
		return new HttpResult<>(200,"ok");
	}
}
