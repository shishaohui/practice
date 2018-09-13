package com.ssh.service.practice.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列化器 将对象转换为二进制串的过程
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public LocalDateTimeSerializer() {
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

//        gen.writeStartObject();
        gen.writeString(value.toString());
//        gen.writeEndObject();
    }
}
