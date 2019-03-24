package org.owasp.pangloss.infra.configurations;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.xml.stream.XMLInputFactory;

import static javax.xml.stream.XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES;
import static javax.xml.stream.XMLInputFactory.SUPPORT_DTD;

@Configuration
public class XMLParserConfig {

    @Bean
    @Profile("insecure")
    public XmlMapper insecureXmlMapper() {
        return new XmlMapper(XMLInputFactory.newFactory());
    }

    @Bean
    @Profile("mitigated")
    public XmlMapper saferXmlMapper() {
        /*
         * This is a just an example on how to disable External Entities resolution no XML Parsers.
         * When using spring, we should rather use Jackson2ObjectMapperBuilder, instead of crafting manually
         * Object and XmlMappers. Spring is securing them by default for us.
         */
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        xmlInputFactory.setProperty(IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        xmlInputFactory.setProperty(SUPPORT_DTD, false);
        return new XmlMapper(xmlInputFactory);
    }
}
