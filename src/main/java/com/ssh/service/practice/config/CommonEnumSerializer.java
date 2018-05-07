package com.ssh.service.practice.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ssh.service.practice.common.commonenums.CommonEnum;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列化器 将对象转换为二进制串的过程
 */
public class CommonEnumSerializer extends JsonSerializer<CommonEnum> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public CommonEnumSerializer() {
    }

    @Override
    public void serialize(CommonEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        gen.writeStartObject();
        gen.writeNumberField("id",value.getId());
        gen.writeNumberField("value",value.getValue());
        gen.writeStringField("code",value.getCode());
        gen.writeStringField("name",value.getName());
        gen.writeEndObject();
    }
}
