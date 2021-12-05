package com.galvanize.prodman.rest;

import com.galvanize.prodman.service.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {ProductController.class,ProductService.class})
@ComponentScan({"com.galvanize.prodman"})
@EnableJpaRepositories("com.galvanize.prodman.repository")
@WebMvcTest
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProduct() {
        MvcResult result = null;
        try {
            result = mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String response = result.getResponse().getContentAsString();

            System.out.println(response);

            assertNotNull(response);
            //assertEquals(HomeController.RESPONSE, response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
