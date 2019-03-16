package org.owasp.pangloss.infra.configurations;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Profile("!poc")
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private XmlMapper xmlMapper;

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //You should no longer use directly the constructor of an ObjectMapper.
        //Use the Jackson2ObjectMapperBuilder instead which provides default security configurations
        //BTW WebMvcConfigurationSupport should be used for non Spring Boot Applications
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new MappingJackson2XmlHttpMessageConverter(xmlMapper));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/images/**", "/css/**")
                .addResourceLocations("classpath:/static/images/",
                        "classpath:/static/css/");
    }
}
