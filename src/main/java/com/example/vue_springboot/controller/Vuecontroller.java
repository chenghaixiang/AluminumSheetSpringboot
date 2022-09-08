package com.example.vue_springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.vue_springboot.bean.Jiaofei;
import com.example.vue_springboot.bean.Lpian;
import com.example.vue_springboot.bean.User;
import com.example.vue_springboot.bean.lp_count;
import com.example.vue_springboot.mapper.Vuemapper;
import com.example.vue_springboot.service.Vueservice;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class Vuecontroller {

    @Autowired
    private Vueservice vueservice;
    @Autowired
    private Vuemapper vuemapper;


    @Autowired
    private HttpServletResponse response;

    @GetMapping
    public List<Lpian> getAll(){
        List<Lpian> all= vueservice.findAll();
        return all;
    }

    //新增和更新，@RetquesBody前端传入的json变成java对象
    @PostMapping
    public Integer save(@RequestBody Lpian lpian){
        return vueservice.save(lpian);

    }

    //@PathVariable("xxx")
    //通过 @PathVariable 可以将URL中占位符参数{xxx}绑定到处理器类的方法形参中@PathVariable(“xxx“)
    //@RequestMapping(value=”user/{id}/{name}”)
    @DeleteMapping("/{u_id}")
    public Integer delete(@PathVariable Integer u_id){
        return vueservice.deletByid(u_id);
    }

    //@RequestParam接收前端的值
//    @RequestParam：将请求参数绑定到你控制器的方法参数上（是springmvc中接收普通参数的注解）
//    语法：@RequestParam(value=”参数名”,required=”true/false”,defaultValue=””)
//    value：参数名
//    required：是否包含该参数，默认为true，表示该请求路径中必须包含该参数，如果不包含就报错。
//    defaultValue：默认参数值，如果设置了该值，required=true将失效，自动为false,如果没有传该参数，就使用默认值
    @GetMapping("/page")
    public Map<String,Object> findPage(@RequestParam Integer pagNum,@RequestParam Integer pagSize,@RequestParam String class_name){
        pagNum = (pagNum-1)*pagSize;

        class_name="%"+class_name+"%";
        Integer total=vueservice.selectTotal(class_name);
        List<Lpian> data=vueservice.selectPage(pagNum,pagSize,class_name);
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }


    @GetMapping("/page_flaw")
    public Map<String,Object> findPage_flaw(@RequestParam Integer pagNum,@RequestParam Integer pagSize,@RequestParam String only_file){
        pagNum = (pagNum-1)*pagSize;
//        only_file="%"+only_file+"%";
        Integer total=vueservice.select_flaw(only_file);
        List<Lpian> data=vueservice.selectPage_flaw(pagNum,pagSize,only_file);
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    //导出接口
    @GetMapping("/export")
    public void export()throws Exception{

        List<Lpian> list =vueservice.findAll();
        //通过工具创建writer写出到磁盘路径
        //内存操作，写出游览器

        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义标题
//        writer.addHeaderAlias("u_id","id");
//        writer.addHeaderAlias("class_name","缺陷名");
//        writer.addHeaderAlias("score","分数");
//        writer.addHeaderAlias("xmin","xmin");
//        writer.addHeaderAlias("ymin","ymin");
//        writer.addHeaderAlias("xmax","xmax");
//        writer.addHeaderAlias("ymax","ymax");
//        writer.addHeaderAlias("len","len");

        //写出到客户端下载（写出到Servlet）
        //设置游览器格式
        writer.write(list,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=test.xlsx");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();

    }

    //导入文件
    @RequestMapping("/import")
    public boolean imp(MultipartFile file)throws Exception{
        InputStream inputStream=file.getInputStream();
        ExcelReader reader=ExcelUtil.getReader(inputStream);
        List<Lpian>list=reader.readAll(Lpian.class);
        System.out.println(list);
        System.out.println(list);
        //批量插入数据
//        vueservice.saveBatch(list);
        return true;
    }

    @GetMapping("/ehcart_lp")
    public Map<String,Object> ehcharts(){
        List<lp_count> data=vueservice.select_count();
        int total=4;
        Map<String,Object> res=new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        System.out.println(data);
        return res;
    }

    @GetMapping("/ehcart_day")
    public Map<String,Object> ehcharts_day(){
        List<lp_count> data=vueservice.select_day();
        int total=data.size();
        Map<String,Object> res=new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        System.out.println(data);
        return res;
    }

    //用户登录
    @GetMapping("/login")
    public boolean finduser(@RequestParam String username,@RequestParam String password){
        for(int x = 0; x < username.length(); x++){
            char ch = username.charAt(x);
            //判断该字符到底是属于那种类型的
            if ((ch >= '0' && ch <= '9')||(ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z')) {
            }else {
                return false;
            }
        }

        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)) {
            return false;
        }else {
            User pd=vueservice.finduser(username,password);
            if (pd==null){
                return false;
            }
            if (pd.getUsername().equals(username) && pd.getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @GetMapping("/Regis")
    public boolean userInsert(@RequestParam String username,@RequestParam String password,@RequestParam String phone){
        for(int x = 0; x < username.length(); x++){
            char ch = username.charAt(x);
            //判断该字符到底是属于那种类型的
            if ((ch >= '0' && ch <= '9')||(ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z')) {
            }else {
                return false;
            }
        }
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)||StrUtil.isBlank(phone)) {
            return false;
        }
        for(int x = 0; x < phone.length(); x++){
            char ch = phone.charAt(x);
            //判断该字符到底是属于那种类型的
            if ((ch >= '0' && ch <= '9')) {
            }else {
                return false;
            }
        }
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        return vueservice.userInsert(user);
    }


}
