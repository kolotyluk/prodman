package com.galvanize.prodman.rest;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {HomeController.class})
@WebMvcTest
public class HomeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getHome() {
        MvcResult result = null;
        try {
            result = mockMvc.perform(MockMvcRequestBuilders.get("/")
                            .contentType(MediaType.TEXT_PLAIN)
                            .accept(MediaType.TEXT_PLAIN))
                    .andExpect(status().isOk())
                    .andReturn();

            String response = result.getResponse().getContentAsString();
            assertNotNull(response);
            assertEquals(HomeController.RESPONSE, response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
