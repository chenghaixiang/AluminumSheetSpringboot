package com.example.vue_springboot.mapper;

import com.example.vue_springboot.bean.File_1;
import com.example.vue_springboot.bean.Jietu;
import com.example.vue_springboot.bean.Lpian;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.io.File;
import java.util.Date;
import java.util.List;

@Mapper
public interface Filemapper {

    int fileinsert(File_1 saveFile);

    File_1 select(String md5);

    List<File_1> selectPage_file(Integer pagNum, Integer pagSize, String filename);

    @Select("select count(*) from sys_file where filename like #{filename}")
    Integer selectTotal_file(String filename);

    //查询当天的上传的文件
    @Select("select * from sys_file where SUBSTRING_INDEX(times,' ',1)=#{nowday} and filename like #{filename} limit #{pagNum},#{pagSize}")
    List<File_1> select_now(Integer pagNum, Integer pagSize, String filename,String nowday);
    @Select("select count(*) from sys_file where filename like #{filename} and SUBSTRING_INDEX(times,' ',1)=#{nowday}")
    Integer selectTotal_now(String filename,String nowday);
    //将模型检测出json添加进数据库

    //截图
    int jietu_fileinsert(Jietu saveFile);
}
