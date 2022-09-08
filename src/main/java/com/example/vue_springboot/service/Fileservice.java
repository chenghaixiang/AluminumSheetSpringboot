package com.example.vue_springboot.service;

import com.example.vue_springboot.bean.File_1;
import com.example.vue_springboot.bean.Lpian;
import com.example.vue_springboot.mapper.Filemapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Fileservice {

    @Autowired
    private Filemapper filemapper;

    public List<File_1> selectPage_file(Integer pagNum, Integer pagSize, String filename) {
        return filemapper.selectPage_file(pagNum,pagSize,filename);
    }


    public Integer selectTotal_file(String filename) {
        return filemapper.selectTotal_file(filename);
    }

    public List<File_1> select_now(Integer pagNum, Integer pagSize, String filename,String nowday) {
        return filemapper.select_now(pagNum,pagSize,filename,nowday);
    }

    public Integer selectTotal_now(String filename,String nowday) {
        return filemapper.selectTotal_now(filename,nowday);
    }


    //截图
}
