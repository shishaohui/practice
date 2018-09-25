/*
 * Copyright 2017 - 数多科技
 *
 * 北京数多信息科技有限公司 本公司保留所有下述内容的权利。
 * 本内容为保密信息，仅限本公司内部使用。
 * 非经本公司书面许可，任何人不得外泄或用于其他目的。
 */
package com.ssh.service.practice.service;

import com.ssh.service.practice.domain.Template;
import com.ssh.service.practice.repository.TemplateRepository;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

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
@Service
@Validated
public class TemplateService {

	@Autowired
	TemplateRepository repository;
	@Autowired
	GridFsTemplate fsTemplate;

	public Template get(Integer id){
		return repository.getOne(id);
	}

	public Template add(Template data) {
		Assert.isTrue(Objects.nonNull(data),"带保存对象不能为空!");
		return repository.saveAndFlush(data);
	}

	public void delete(Integer id) {
		Template template = repository.getOne(id);
		this.deleteMongo(template);
		repository.delete(id);
	}
	private void deleteMongo(Template data) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(data.getBodyId()));
		fsTemplate.delete(query);
	}
}
