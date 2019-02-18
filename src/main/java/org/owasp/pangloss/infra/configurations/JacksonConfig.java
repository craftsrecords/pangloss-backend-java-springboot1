package org.owasp.pangloss.infra.configurations;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.owasp.pangloss.infra.serializer.BigDecimalSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigDecimal;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
                jackson2ObjectMapperBuilder.modulesToInstall(customSerializerModule());
            }
        };
    }

    private SimpleModule customSerializerModule() {
        SimpleModule serializerModule = new SimpleModule();
        serializerModule.addSerializer(BigDecimal.class, new BigDecimalSerializer());
        return serializerModule;
    }
}
