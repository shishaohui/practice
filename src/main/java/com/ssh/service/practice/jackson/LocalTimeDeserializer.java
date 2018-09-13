package com.ssh.service.practice.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;


public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private ConversionService converter;

    public LocalTimeDeserializer(ConversionService converter) {
        super();
        this.converter=converter;
    }

    @Override
    public LocalTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        if (parser.hasToken(JsonToken.VALUE_STRING)) {
            String string = parser.getText().trim();
            if (string.length() == 0) {
                return null;
            }
            try {
                return this.converter.convert(string, LocalTime.class);
            } catch (Exception ex) {
                new JsonParseException(parser, String.format("将解析[%s]为LocalTime异常", string));
            }
        }
        if (parser.isExpectedStartArrayToken()) {
            if (parser.nextToken() == JsonToken.END_ARRAY) {
                return null;
            }
            int hour = parser.getIntValue();

            parser.nextToken();
            int minute = parser.getIntValue();

            if(parser.nextToken() != JsonToken.END_ARRAY)
            {
                int second = parser.getIntValue();

                if(parser.nextToken() != JsonToken.END_ARRAY)
                {
                    int partialSecond = parser.getIntValue();
                    if(partialSecond < 1_000 &&
                            !context.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS))
                        partialSecond *= 1_000_000; // value is milliseconds, convert it to nanoseconds

                    if(parser.nextToken() != JsonToken.END_ARRAY)
                        throw context.wrongTokenException(parser, JsonToken.END_ARRAY, "Expected array to end.");

                    return LocalTime.of(hour, minute, second, partialSecond);
                }

                return LocalTime.of(hour, minute, second);
            }
            return LocalTime.of(hour, minute);
        }
        if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
            return (LocalTime) parser.getEmbeddedObject();
        }
        throw context.wrongTokenException(parser, JsonToken.START_ARRAY, "Expected array or string.");
    }
}
