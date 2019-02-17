package org.owasp.pangloss.infra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.pangloss.domain.item.Items;
import org.owasp.pangloss.infra.configurations.WebConfig;
import org.owasp.pangloss.infra.controller.ItemController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(
        value = ItemController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Items.class))
@Import(WebConfig.class)
@WithMockUser("poc-user")
@ActiveProfiles("insecure")
public class XmlXXETest {

    @Autowired
    private MockMvc mockMvc;

    @Value("classpath:xxe.xml")
    private Resource xxeFile;


    @Test
    public void should_throw_BadRequest_when_trying_to_list_items_for_an_unknown_category() throws Exception {
        mockMvc.perform(post("/items")
                .contentType(APPLICATION_XML)
                .content(Files.readAllBytes(xxeFile.getFile().toPath())))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_still_be_able_to_request_in_json() throws Exception {
        mockMvc.perform(post("/items")
                .contentType(APPLICATION_JSON)
                .content("{\"categoryId\": \"unknown\"}"))
                .andExpect(status().isBadRequest());
    }
}
