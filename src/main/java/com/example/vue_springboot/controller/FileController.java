package com.example.vue_springboot.controller;

import cn.hutool.core.io.FileUtil;

import cn.hutool.core.net.multipart.UploadFile;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONException;
import com.example.vue_springboot.bean.File_1;
import com.example.vue_springboot.bean.Jiaofei;
import com.example.vue_springboot.bean.Jietu;
import com.example.vue_springboot.bean.Lpian;
import com.example.vue_springboot.mapper.Filemapper;
import com.example.vue_springboot.service.Fileservice;
import com.example.vue_springboot.service.Vueservice;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//文件上传接口
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private Filemapper filemapper;
    @Autowired
    private Fileservice fileservice;
    @Autowired
    private Vueservice vueservice;

    @Value("${files.upload.path}")
    private String filepath;
//    private String filepath1="/home/lxy/jiekou/output1/";

    private String filepath1="";//改成python检测后图片存放路径

    //上传
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws Exception {
        String originalFilename=file.getOriginalFilename();
        String type= FileUtil.extName(originalFilename);
        long size=file.getSize();
        //存入磁盘
        //判断文件路径是否存在，不存在创建
        File uploadpath=new File(filepath);
        if(!uploadpath.exists()){
            uploadpath.mkdirs();
        }
        //图片的唯一标识符
        String only_file= java.util.UUID.randomUUID().toString();
        //定义文件的标识码
        String uuid=IdUtil.fastSimpleUUID();
        String fileUuid=uuid+ StrUtil.DOT+type;
        File uploadpath_chengou = new File(filepath+fileUuid);
        //获取文件的md5（磁盘的唯一标识）
//        String md5= SecureUtil.md5(uploadpath_chengou);;
        //获取的文件存储到磁盘目录
        file.transferTo(uploadpath_chengou);
        //文件下载路径，先写好，为文件下载准备
        String url="http://localhost:9090/file/moxin/"+fileUuid;
        //上传时模型检测
        String place="?image="+filepath+fileUuid;
        String a=sendHttpRequest("http://localhost:80/detect",place);
        System.out.println(a);
        Date times=new Date();
        //获取返回的json添加进数据库
        addresult(a,originalFilename,only_file);
        //存储数据库http://localhost:9090/file/ce18148e17494a138986a26fc6c0dc70.jpg
        File_1 saveFile=new File_1();
        saveFile.setFilename(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setTimes(times);
        saveFile.setOnly_file(only_file);
        filemapper.fileinsert(saveFile);

        return url;
    }

    //下载
    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {

        //根据文件的唯一标识符获取文件
        File uploadpath_chengou = new File(filepath+fileUuid);
        //设置输出流的格式
        ServletOutputStream os=response.getOutputStream();
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUuid,"utf-8"));
        response.setContentType("application/octet-stream");

        //读取文件的字节流
        os.write(FileUtil.readBytes(uploadpath_chengou));
        os.flush();
        os.close();
    }
    //下载检测图片
    @GetMapping("/moxin/{fileUuid}")
    public void download1(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {

        //根据文件的唯一标识符获取文件
        File uploadpath_chengou = new File(filepath1+fileUuid);
        //设置输出流的格式
        ServletOutputStream os=response.getOutputStream();
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUuid,"utf-8"));
        response.setContentType("application/octet-stream");

        //读取文件的字节流
        os.write(FileUtil.readBytes(uploadpath_chengou));
        os.flush();
        os.close();
    }

    //查询数据库
    @GetMapping("/page")
    public Map<String,Object> findPage_2(@RequestParam Integer pagNum, @RequestParam Integer pagSize,@RequestParam String filename){
        pagNum = (pagNum-1)*pagSize;
        filename="%"+filename+"%";
        Integer total=fileservice.selectTotal_file(filename);
        List<File_1> data=fileservice.selectPage_file(pagNum,pagSize,filename);
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;

    }
    //查询当天的上传文件
    @GetMapping("/pagenow")
    public Map<String,Object> findPage_3(@RequestParam Integer pagNum, @RequestParam Integer pagSize,@RequestParam String filename){
        pagNum = (pagNum-1)*pagSize;
        filename="%"+filename+"%";
        Date nowday=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String nowday1=formatter.format(nowday);
        Integer total=fileservice.selectTotal_now(filename,nowday1);
        List<File_1> data=fileservice.select_now(pagNum,pagSize,filename,nowday1);
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;

    }

    //上传图片，图片检测
    //去请求第三方的接口http://127.0.0.1:8000/detect
    public static String sendHttpRequest(String httpURL, String placeId) throws Exception {
        //placeid是请求参数，httpURL是请求路径，拼接它
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
        // 获得返回json的内容，utf-8获取内容的编码
        InputStreamReader in = new InputStreamReader(conn.getInputStream(),"utf-8");
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

    //模型检测json返回数据添加,str是json,filename文件名是存入数据库的标识，only_file是文件唯一标识符
    public void addresult(String str,String filename,String only_file){

        //将json中格式转义"\"变成空
        String value_review = str.replace("\\","");
        String value_red=value_review.replace("\"\"","");
        //待解析的JSON字符串
        String JSONString = value_red;
        //实体类对象
        Lpian lpian=new Lpian();
        String u_id=filename;
        //获取当前时间
        Date times=new Date();
        try {

            org.json.JSONObject JSON = new org.json.JSONObject(JSONString);      // 第一步，将string变为JSON  这里最外层是{  所以是new JSONObject
            /*
             * 属性中既有中括号包括，又嵌套了大括号，一层层获取即可   先getJSONArray，然后getJSONObject，到了属性，就是getXxx()了
             */
            JSONArray result= JSON.getJSONArray("result");
            //json中的有个key是len
            int len=JSON.getInt("len");
            if(len==0){
                lpian.setClass_name(null);
                lpian.setScore(null);
                lpian.setXmin(null);
                lpian.setYmin(null);
                lpian.setXmax(null);
                lpian.setYmax(null);
                lpian.setLen(null);
                lpian.setU_id(u_id);
                lpian.setTimes(times);
                lpian.setOnly_file(only_file);
                vueservice.save1(lpian);
            }else {
//            System.out.println("结果：");
                //将json中数据提取出来存入数据库,过程麻烦，因为要转类型，可能有更好的方法，但现在只会这个
                for (int i = 0; i < result.length(); i++) {
                    //获取json数据中的各个值
                    JSONObject honor = result.getJSONObject(i);
                    String class_name = (String) honor.getString("class_name");
                    Double score = honor.getDouble("score");
                    String score1 = String.valueOf(score);
                    Double xmin = honor.getDouble("xmin");
                    String xmin1 = String.valueOf(xmin);
                    Double ymin = honor.getDouble("ymin");
                    String ymin1 = String.valueOf(ymin);
                    Double xmax = honor.getDouble("xmax");
                    String xmax1 = String.valueOf(xmax);
                    Double ymax = honor.getDouble("ymax");
                    String ymax1 = String.valueOf(ymax);
                    //添加数据库
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
//                    System.out.println(class_name + " : " + score + ":" + xmin + ":" + ymin + ":" + xmax + ":" + ymax);
                }
            }
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
