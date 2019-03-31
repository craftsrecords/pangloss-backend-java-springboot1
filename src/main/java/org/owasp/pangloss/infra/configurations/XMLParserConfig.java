package org.owasp.pangloss.infra.configurations;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.stream.XMLInputFactory;

@Configuration
public class XMLParserConfig {
    @Bean
    public XmlMapper xmlMapper() {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        return new XmlMapper(factory);
    }
}
