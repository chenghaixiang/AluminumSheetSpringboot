package com.example.vue_springboot.controller;

import com.example.vue_springboot.VueSpringbootApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 程海翔
 * @school 石家庄铁道大学
 */
@SpringBootTest(classes = VueSpringbootApplication.class)
class VuecontrollerTest {

    @Autowired
    private WebApplicationContext webct;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webct).build();

    }


    @Test
    void getAll() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void findPage() throws Exception {
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/user/page?pagNum=1&pagSize=5&class_name="))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(contentAsString);
    }

    @Test
    void findPage_flaw() {
    }

    @Test
    void export() {
    }

    @Test
    void imp() {
    }

    @Test
    void ehcharts() throws Exception {
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/user/ehcart_lp"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(contentAsString);
    }

    @Test
    void ehcharts_day() {
    }
}