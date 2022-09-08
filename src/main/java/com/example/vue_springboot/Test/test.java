package com.example.vue_springboot.Test;


import cn.hutool.crypto.SecureUtil;
import com.example.vue_springboot.mapper.Vuemapper;
import org.apache.tomcat.util.descriptor.web.MessageDestinationRef;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.vue_springboot.controller.FileController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class test {
    private FileController fileController;
    @Resource
    private Vuemapper vuemapper;


    @Test
    public void asd() throws ParseException {
        Date data=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdf.parse("2025-5-28");
        String a=formatter.format(data);
        String b=formatter.format(d1);
        int c=a.compareTo(b);
        if(c>0){
            System.out.println("a>b");
        }else{
            System.out.println("a<b");
        }
    }


}
