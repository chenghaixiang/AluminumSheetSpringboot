package com.example.vue_springboot.bean;

public class lp_count {
    private String class_name;
    private Integer num;

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "lp_count{" +
                "class_name='" + class_name + '\'' +
                ", num=" + num +
                '}';
    }
}
