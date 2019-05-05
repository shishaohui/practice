package com.ssh.service.practice;

import com.ssh.service.practice.repository.JpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackageClasses = Application.class)
@EnableJpaRepositories(basePackageClasses = JpaRepository.class)
// @EnableAspectJAutoProxy表示使用cglib进行代理对象的生成 表示通过aop框架暴露该代理对象，aopContext能够访问.
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
