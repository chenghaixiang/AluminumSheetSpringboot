package com.example.vue_springboot.controller;

import com.example.vue_springboot.bean.File_1;
import com.example.vue_springboot.bean.Jietu;
import com.example.vue_springboot.bean.Lpian;
import com.example.vue_springboot.mapper.Filemapper;
import com.example.vue_springboot.service.Vueservice;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@RestController
@RequestMapping("/file1")

public class Jietucontroller {
    @Resource
    private Filemapper filemapper;
    @Autowired
    private Vueservice vueservice;

    @RequestMapping("/jietu_upload")
    public String jietu_upload(@RequestParam String filename) throws Exception {
        String url="C:\\Users\\86182\\Downloads\\"+filename+".jpg";
        //存储数据库
        //定义一些变量
        Jietu saveFile=new Jietu();
        String filename1=filename+".jpg";
        saveFile.setFilename(filename1);
        String type="jpg";
        saveFile.setType(type);
        int size=147;
        //图片唯一标识符
        String only_file= java.util.UUID.randomUUID().toString();
        //上传时模型检测,去http://127.0.0.1:8000/detect接口
        String place="?image="+url;
        String a=sendHttpRequest("http://127.0.0.1:8000/detect",place);
//        System.out.println(a);
        //获取返回的json添加进数据库
        Date times=addresult(a,filename+".jpg",only_file);
        saveFile.setSize((long) size);
        saveFile.setUrl(url);
        saveFile.setTimes(times);
        saveFile.setOnly_file(only_file);
        filemapper.jietu_fileinsert(saveFile);
        //文件信息写入sys_file
        String url_1="http://localhost:9090/file/moxin/";
        File_1 jetufile=new File_1();
        jetufile.setUrl(url_1+filename+".jpg");
        jetufile.setFilename(filename1);
        jetufile.setType(type);
        jetufile.setSize(147L);
        jetufile.setTimes(times);
        jetufile.setOnly_file(only_file);
        filemapper.fileinsert(jetufile);
        return url;
    }
    //上传图片，图片检测
    //去请求第三方的接口http://127.0.0.1:8000/detect
    public static String sendHttpRequest(String httpURL, String placeId) throws Exception {
        String httpurl=httpURL+placeId;
        //建立URL连接对象
        URL url = new URL(httpurl);
        //创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求的方式(需要是大写的)
        conn.setRequestMethod("POST");
        //设置需要输出
        conn.setDoOutput(true);
//        conn.addRequestProperty("Cookie", "JSESSIONID=" + jesssionId);
        //发送请求到服务器
        conn.connect();
        conn.getInputStream();
        InputStreamReader in = new InputStreamReader(conn.getInputStream(),"utf-8"); // 获得读取的内容，utf-8获取内容的编码
        BufferedReader buffer = new BufferedReader(in);
        //StringBuilder来把接收到的数据拼接起来
        StringBuilder result = new StringBuilder();
        //逐行读取数据
        String line;//用来读取数据
        while ((line = buffer.readLine()) != null) {
            result.append(line);
//            System.out.print(line);
        }
        //关闭连接
        in.close();
        conn.disconnect();
        //返回json字符串
        return result.toString();
    }

    //模型检测json返回数据添加,str是json数据,filename文件名是存入数据库的标识
    public Date addresult(String str,String filename,String only_file){

        //将json中格式转义"\"变成空
        String value_review = str.replace("\\","");
        String value_red=value_review.replace("\"\"","");
        //待解析的JSON字符串
        String JSONString = value_red;

        Lpian lpian=new Lpian();
        String u_id=filename;
        Date times=new Date();
        try {

            org.json.JSONObject JSON = new org.json.JSONObject(JSONString);      // 第一步，将string变为JSON  这里最外层是{  所以是new JSONObject
            /*
             * 属性中既有中括号包括，又嵌套了大括号，一层层获取即可   先getJSONArray，然后getJSONObject，到了属性，就是getXxx()了
             */
            JSONArray result= JSON.getJSONArray("result");
            int len=JSON.getInt("len");
//            System.out.println("结果：");
            //将json中数据提取出来存入数据库,过程麻烦，因为要转类型，可能有更好的方法，但现在只会这个
            for(int i=0;i<result.length();i++)
            {
                JSONObject honor = result.getJSONObject(i);
                String class_name = (String) honor.getString("class_name");
                Double score =honor.getDouble("score");
                String score1 = String.valueOf(score);
                Double xmin=honor.getDouble("xmin");
                String xmin1 = String.valueOf(xmin);
                Double ymin=honor.getDouble("ymin");
                String ymin1 = String.valueOf(ymin);
                Double xmax=honor.getDouble("xmax");
                String xmax1 = String.valueOf(xmax);
                Double ymax=honor.getDouble("ymax");
                String ymax1 = String.valueOf(ymax);
                lpian.setClass_name(class_name);
                lpian.setScore(score1);
                lpian.setXmin(xmin1);
                lpian.setYmin(ymin1);
                lpian.setXmax(xmax1);
                lpian.setYmax(ymax1);
                lpian.setLen(len);
                lpian.setU_id(u_id);
                lpian.setTimes(times);
                lpian.setOnly_file(only_file);
                vueservice.save1(lpian);
                System.out.println(class_name+" : "+ score+":"+xmin+":"+ymin+":"+xmax+":"+ymax);
            }
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return times;
    }
}
