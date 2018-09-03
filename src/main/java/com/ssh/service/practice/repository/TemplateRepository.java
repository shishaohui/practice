package com.ssh.service.practice.repository;

import com.ssh.service.practice.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template,Integer> {

}
