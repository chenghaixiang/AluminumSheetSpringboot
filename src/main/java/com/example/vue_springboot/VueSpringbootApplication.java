package com.example.vue_springboot;

import com.example.vue_springboot.bean.Chesiclass;
import com.example.vue_springboot.mapper.Vuemapper;
import com.example.vue_springboot.service.Vueservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class VueSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueSpringbootApplication.class, args);
    }

}
