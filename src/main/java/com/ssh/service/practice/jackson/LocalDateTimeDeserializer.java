package com.ssh.service.practice.jackson;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;


public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    private ConversionService converter;

    public LocalDateTimeDeserializer(ConversionService converter) {
        super();
        this.converter=converter;
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (p.hasTokenId(JsonTokenId.ID_STRING)) {
            String string = p.getText().trim();
            if (string.length() == 0) {
                return null;
            }

            try {
                return this.converter.convert(string, LocalDateTime.class);
            } catch (Exception ex) {
                new JsonParseException(p, String.format("将解析[%s]为LocalDateTime异常", string));
            }
        }
        if (p.isExpectedStartArrayToken()) {
            if (p.nextToken() == JsonToken.END_ARRAY) {
                return null;
            }
            int year = p.getIntValue();
            int month = p.nextIntValue(-1);
            int day = p.nextIntValue(-1);
            int hour = p.nextIntValue(-1);
            int minute = p.nextIntValue(-1);

            if (p.nextToken() != JsonToken.END_ARRAY) {
                int second = p.getIntValue();

                if (p.nextToken() != JsonToken.END_ARRAY) {
                    int partialSecond = p.getIntValue();
                    if(partialSecond < 1_000 &&
                            !ctxt.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS))
                        partialSecond *= 1_000_000; // value is milliseconds, convert it to nanoseconds

                    if(p.nextToken() != JsonToken.END_ARRAY) {
                        throw ctxt.wrongTokenException(p, JsonToken.END_ARRAY, "Expected array to end.");
                    }
                    return LocalDateTime.of(year, month, day, hour, minute, second, partialSecond);
                }
                return LocalDateTime.of(year, month, day, hour, minute, second);
            }
            return LocalDateTime.of(year, month, day, hour, minute);
        }
        if (p.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
            return (LocalDateTime) p.getEmbeddedObject();
        }
        throw ctxt.wrongTokenException(p, JsonToken.VALUE_STRING, "Expected array or string.");


    }
}
