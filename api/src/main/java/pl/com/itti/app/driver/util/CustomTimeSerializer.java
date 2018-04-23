package pl.com.itti.app.driver.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomTimeSerializer extends JsonSerializer<ZonedDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'hh:mm:ss xxx");

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        gen.writeString(value.format(FORMATTER));
    }
}
