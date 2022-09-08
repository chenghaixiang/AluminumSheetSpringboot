package com.example.vue_springboot.controller;

import com.example.vue_springboot.VueSpringbootApplication;
import com.example.vue_springboot.bean.Jietu;
import com.example.vue_springboot.bean.Lpian;
import com.example.vue_springboot.mapper.Filemapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author 程海翔
 * @school 石家庄铁道大学
 */
@SpringBootTest(classes = VueSpringbootApplication.class)

class JietucontrollerTest {

    @Autowired
    private WebApplicationContext webct;

    private MockMvc mockMvc;
    private HttpHeaders httpHeaders;

    @BeforeEach
    public void init(){
        mockMvc=MockMvcBuilders.webAppContextSetup(webct).build();

    }
    @Test
    void jietu_upload() throws Exception {
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/file1/jietu_upload?filename=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void sendHttpRequest() {
    }

    @Test
    void addresult() {

    }
}