package com.ssh.service.practice.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import org.springframework.core.convert.ConversionService;


public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {


    private ConversionService converter;

    public LocalDateDeserializer(ConversionService converter) {
        super();
        this.converter=converter;
    }

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        if (p.hasToken(JsonToken.VALUE_STRING)) {
            String string = p.getText().trim();
            if (string.length() == 0) {
                return null;
            }
            try {
                return this.converter.convert(string, LocalDate.class);
            } catch (Exception ex) {
                new JsonParseException(p, String.format("将解析[%s]为LocalDate异常", string));
            }

        }
        if (p.isExpectedStartArrayToken()) {
            if (p.nextToken() == JsonToken.END_ARRAY) {
                return null;
            }
            int year = p.getIntValue();
            int month = p.nextIntValue(-1);
            int day = p.nextIntValue(-1);

            if (p.nextToken() != JsonToken.END_ARRAY) {
                throw ctxt.wrongTokenException(p, JsonToken.END_ARRAY, "Expected array to end.");
            }
            return LocalDate.of(year, month, day);
        }
        if (p.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
            return (LocalDate) p.getEmbeddedObject();
        }
        throw ctxt.wrongTokenException(p, JsonToken.VALUE_STRING, "Expected array or string.");
    }
}
