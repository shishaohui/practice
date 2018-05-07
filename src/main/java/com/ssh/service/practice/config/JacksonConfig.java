package com.ssh.service.practice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ssh.service.practice.common.commonenums.CommonEnum;
import com.ssh.service.practice.util.ClassUtil;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.convert.ConversionService;

@Configuration
@DependsOn
public class JacksonConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public JacksonConfig(@Qualifier("mvcConversionService") ConversionService conversionService,
                         List<ObjectMapper>  objectMappers) {
        //配置日期时间反序列化器，优先顺序是哪个呢？有多个会怎么办
        logger.info("执行json配置");
        SimpleModule module = new SimpleModule();

        module.addSerializer(CommonEnum.class, new CommonEnumSerializer());
        Set<Class<?>> all = ClassUtil.getClasses("com.ssh");
        Set<Class<? extends CommonEnum>> enums = ClassUtil.getByInterface(CommonEnum.class, all);
        enums.forEach(o->{
            module.addDeserializer(o, new CommonEnumDeserializer(o));
        });

        if (objectMappers.size()==0) {
            logger.warn("ObjectMapper未配置");
            return;
        }
        for (int i = 0; i <objectMappers.size() ; i++) {
            logger.trace("注入的ObjectMapper为{}",objectMappers.get(i));
            objectMappers.get(i).registerModule(module);

            logger.trace("注入完成");
        }
    }
}
