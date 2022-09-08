package com.example.vue_springboot.bean;


import java.util.Date;

public class File_1 {
    private Integer id;
    private String filename;
    private String type;
    private Long size;
    private String url;
    private Boolean is_delete;
    private Date times;
    private String only_file;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(Boolean is_delete) {
        this.is_delete = is_delete;
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
        return "File_1{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", url='" + url + '\'' +
                ", is_delete=" + is_delete +
                ", times=" + times +
                ", only_file='" + only_file + '\'' +
                '}';
    }
}
