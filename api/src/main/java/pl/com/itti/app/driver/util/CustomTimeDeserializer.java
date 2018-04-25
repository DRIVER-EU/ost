package pl.com.itti.app.driver.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ssxxx");

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        return ZonedDateTime.parse(p.getValueAsString(), FORMATTER);
    }
}
