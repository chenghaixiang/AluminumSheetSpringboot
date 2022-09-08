package com.example.vue_springboot.bean;

public class Chesiclass {

    private String name;
    private int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        if(num<=30&&num>0) {
            return num;
        }else {
            return -1;
        }
    }

    public void setNum(int num) {
        this.num = num;
    }


    @Override
    public String toString() {
        return "Chesiclass{" +
                "name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
