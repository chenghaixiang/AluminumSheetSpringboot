package com.example.vue_springboot.service;

import com.example.vue_springboot.bean.Jiaofei;
import com.example.vue_springboot.bean.Lpian;
import com.example.vue_springboot.bean.User;
import com.example.vue_springboot.bean.lp_count;
import com.example.vue_springboot.mapper.Vuemapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Vueservice {
    @Autowired(required = false)
    private Vuemapper vuemapper;

    public int save(Lpian lpian){
        if(lpian.getU_id() == null){
            return vuemapper.insert(lpian);
        }else{
            return vuemapper.updata(lpian);
        }
    }

    //模型检测json返回数据添加
    public int save1(Lpian lpian){
        return vuemapper.insert(lpian);
    }

    public Integer deletByid(Integer u_id) {
        return vuemapper.deletByid(u_id);
    }

    public List<Lpian> findAll(){

        return vuemapper.findAll();
    }

    public List<Lpian> selectPage(Integer pagNum, Integer pagSize,String class_name) {
        return vuemapper.selectPage(pagNum,pagSize,class_name);
    }


    public Integer selectTotal(String class_name) {
        return vuemapper.selectTotal(class_name);

    }

    //查询某一图片的缺陷数据
    public List<Lpian> selectPage_flaw(Integer pagNum, Integer pagSize,String only_file) {
        return vuemapper.selectPage_flaw(pagNum,pagSize,only_file);
    }


    public Integer select_flaw(String only_file) {
        return vuemapper.select_flaw(only_file);

    }


    public List<Jiaofei> jiaofeiAll(){
        return vuemapper.jiaofeiAll();
    }

    public List<lp_count> select_count(){
        return vuemapper.select_count();
    }
    public List<lp_count> select_day(){
        return vuemapper.select_day();
    }

    //用户登录
    public User finduser(String username, String password){
        return vuemapper.finduser(username,password);
    }

    public boolean userInsert(User user){
        return vuemapper.userInsert(user);
    }

}
