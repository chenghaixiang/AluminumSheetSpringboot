package com.example.vue_springboot.bean;

import java.util.Date;

public class Lpian {
    private String u_id;
    private String class_name;
    private String score;
    private String xmin;
    private String xmax;
    private String ymin;
    private String ymax;
    private Integer len;
    private Date times;
    private String only_file;

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getXmin() {
        return xmin;
    }

    public void setXmin(String xmin) {
        this.xmin = xmin;
    }

    public String getXmax() {
        return xmax;
    }

    public void setXmax(String xmax) {
        this.xmax = xmax;
    }

    public String getYmin() {
        return ymin;
    }

    public void setYmin(String ymin) {
        this.ymin = ymin;
    }

    public String getYmax() {
        return ymax;
    }

    public void setYmax(String ymax) {
        this.ymax = ymax;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public Date getTimes() {
        return times;
    }

    public void setTimes(Date times) {
        this.times = times;
    }

    public String getOnly_file() {
        return only_file;
    }

    public void setOnly_file(String only_file) {
        this.only_file = only_file;
    }

    @Override
    public String toString() {
        return "Lpian{" +
                "u_id='" + u_id + '\'' +
                ", class_name='" + class_name + '\'' +
                ", score='" + score + '\'' +
                ", xmin='" + xmin + '\'' +
                ", xmax='" + xmax + '\'' +
                ", ymin='" + ymin + '\'' +
                ", ymax='" + ymax + '\'' +
                ", len=" + len +
                ", times=" + times +
                ", only_file='" + only_file + '\'' +
                '}';
    }
}
