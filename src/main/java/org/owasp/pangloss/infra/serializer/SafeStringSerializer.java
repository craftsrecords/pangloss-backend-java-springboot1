package org.owasp.pangloss.infra.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.owasp.encoder.Encode;

import java.io.IOException;

//@JsonComponent //reactivate when dealing with XSS + change package potentially
public class SafeStringSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String string, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(Encode.forHtml(string));
    }
}
