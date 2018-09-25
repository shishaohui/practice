/*
 * Copyright 2017 - 数多科技
 *
 * 北京数多信息科技有限公司 本公司保留所有下述内容的权利。
 * 本内容为保密信息，仅限本公司内部使用。
 * 非经本公司书面许可，任何人不得外泄或用于其他目的。
 */
package com.ssh.service.practice.controller;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.ssh.service.practice.common.HttpResult;
import com.ssh.service.practice.domain.Template;
import com.ssh.service.practice.service.TemplateService;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner.Mode;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangmingming<zhangmingming01 @ we.com>
 * @version v1.0
 * @datetime 2018/6/8
 * @since JDK1.8
 */
@RestController
@RequestMapping(value = "/internal/v1.0/template")
@Qualifier("internal")
public class TemplateController {
	@Autowired
	private TemplateService service;
	@Autowired
	private GridFsTemplate gridFsTemplate;


	/**
	 * 添加新模板
	 */
	@RequestMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpResult<Template> addTemplate(@RequestParam(name = "file") MultipartFile file, @ModelAttribute Template template,Mode mode) throws IOException {
		InputStream inputStream = file.getInputStream();
		GridFSFile gridFS = gridFsTemplate.store(inputStream, file.getOriginalFilename());
		template.setBodyId(gridFS.getId().toString());
		this.service.add(template);
		return HttpResult.OK(template);
	}

	@GetMapping(value = "/download")
	public ResponseEntity<byte[]> download(@RequestParam Integer id) throws IOException {
		Template template = service.get(id);
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(template.getBodyId()));
		GridFSDBFile gridFS = gridFsTemplate.findOne(query);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", "template.pdf");
		headers.setContentType(MediaType.APPLICATION_PDF);
		return new ResponseEntity<>(StreamUtils.copyToByteArray(gridFS.getInputStream()), headers, HttpStatus.CREATED);
	}

	@GetMapping(value = "/delete")
	public HttpResult<String> delete(@RequestParam Integer id) {
		service.delete(id);
		return HttpResult.OK("success");
	}
}
