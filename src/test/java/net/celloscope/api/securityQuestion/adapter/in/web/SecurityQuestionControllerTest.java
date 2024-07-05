package net.celloscope.api.securityQuestion.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.celloscope.api.securityQuestion.application.port.in.ValidateSecurityQuestionCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SecurityQuestionControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    String getUrl = "/v1/security-questions/{ibUserOid}";
    String postUrl = "/v1/security-questions/verify";
    String ibUserOid = "a66e86a3-3c72-4460-903e-32978c0f613c";


    @Test
    void ShouldReturnSecurtyQuestionListWhenGetSecurityQuestionIsCalled() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(getUrl, ibUserOid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userMessage").value("Security questions list successfully retrieved"));
    }



    @Test
    void ShouldVerifySecurityQuestionWhenVerifySecurityQuestionIsCalled() throws Exception {
        mockMvc.perform(post(postUrl)
                        .content(objectMapper.writeValueAsString(mockBuildRequestBody()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    ValidateSecurityQuestionCommand mockBuildRequestBody() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/test/java/net/celloscope/api/securityQuestion/common/securityQuestionRequestBody.json"), ValidateSecurityQuestionCommand.class);
    }

}