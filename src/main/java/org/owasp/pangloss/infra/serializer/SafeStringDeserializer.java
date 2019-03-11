package org.owasp.pangloss.infra.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.owasp.encoder.Encode;

import java.io.IOException;

//@JsonComponent //reactivate when dealing with XSS + change package potentially
public class SafeStringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String unsafe = jsonParser.getCodec().readValue(jsonParser, String.class);
        return Encode.forHtml(unsafe);
    }
}
