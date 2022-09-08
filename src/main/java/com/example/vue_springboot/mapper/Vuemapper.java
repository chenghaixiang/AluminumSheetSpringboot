package com.example.vue_springboot.mapper;

import com.example.vue_springboot.bean.Jiaofei;
import com.example.vue_springboot.bean.Lpian;
import com.example.vue_springboot.bean.User;
import com.example.vue_springboot.bean.lp_count;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Vuemapper {

    List<Lpian> findAll();

    int insert(Lpian lpian);

    int updata(Lpian lpian);

    Integer deletByid(@Param("u_id") Integer u_id);

    List<Lpian> selectPage(Integer pagNum,Integer pagSize,String class_name);
    @Select("select count(*) from lpianjianche where class_name like #{class_name} or u_id like #{class_name}")
    Integer selectTotal(String class_name);

    //查询某一图片的缺陷数据
    @Select("select count(*) from lpianjianche where only_file like #{only_file}")
    Integer select_flaw(String only_file);

    List<Lpian> selectPage_flaw(Integer pagNum,Integer pagSize,String only_file);

    //查询数据库数据完成图表展示
//    @Select("select class_name,count(*) as num from lpianjianche group by class_name")
    @Select("select class_name,count(*) as num from lpianjianche where class_name='ca_shang' or class_name='zang_wu' or class_name='zhen_kong' or class_name='zhe_zhou' group by class_name")
    List<lp_count> select_count();
    @Select("select SUBSTRING_INDEX(times,' ',1) as class_name,count(*) as num from lpianjianche group by SUBSTRING_INDEX(times,' ',1)")
    List<lp_count> select_day();


    List<Jiaofei> jiaofeiAll();

    //用户登录
    User finduser(@Param("username") String username, @Param("password") String passwrod);
    Boolean userInsert(User user);

}
