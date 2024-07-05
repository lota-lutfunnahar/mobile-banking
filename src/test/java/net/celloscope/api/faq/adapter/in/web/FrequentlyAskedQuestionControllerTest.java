package net.celloscope.api.faq.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class FrequentlyAskedQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;


    String getUrl = "/v1/banks/35/faq?offset=0&limit=1";

    @Test
    void ShouldReturnValidFAQListForValidRequest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(getUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userMessage").value("FAQ list retrieved successfully"));
    }
}