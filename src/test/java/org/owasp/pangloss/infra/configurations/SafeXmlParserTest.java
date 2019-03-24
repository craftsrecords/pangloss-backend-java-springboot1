package org.owasp.pangloss.infra.configurations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithMockUser("poc-user")
@ActiveProfiles("mitigated")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SafeXmlParserTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("classpath:xxe.xml")
    private Resource xxeFile;

    @Test
    public void should_not_be_vulnerable_to_XXE_attack() throws Exception {
        mockMvc.perform(post("/api/items")
                .contentType(APPLICATION_XML)
                .content(Files.readAllBytes(xxeFile.getFile().toPath())))
                .andExpect(status().isForbidden());
    }

    @Test
    public void should_still_be_able_to_request_in_json() throws Exception {
        mockMvc.perform(post("/api/items")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content("{\"categoryId\": \"unknown\"}"))
                .andExpect(status().isBadRequest());
    }
}
