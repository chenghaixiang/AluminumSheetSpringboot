package com.example.vue_springboot.bean;

public class Jiaofei {
    private String type;
    private Integer num1;
    private String u_id;
    private String num;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {

        this.num = num;
    }

    public Integer getNum1() {
        return num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    @Override
    public String toString() {
        return "Jiaofei{" +
                "type='" + type + '\'' +
                ", num1=" + num1 +
                ", u_id='" + u_id + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
