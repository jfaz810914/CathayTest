package com.springboot.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // 自動配置 MockMvc
public class PostTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testPostSuccess() throws Exception {
        Object request = Map.of(
                "startDate", "2024/01/02",
                "endDate", "2024/01/02",
                "currency", "usd"
        );

        MvcResult result = mockMvc.perform(post("/forex")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        System.out.println("Response content: " + contentAsString);
    }

    @Test
    void testPostFailed() throws Exception {
        Object request = Map.of(
                "startDate", "2024/01/02",
                "endDate", "2025/01/02",
                "currency", "usd"
        );

        MvcResult result = mockMvc.perform(post("/forex")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        System.out.println("Response content: " + contentAsString);
    }

}