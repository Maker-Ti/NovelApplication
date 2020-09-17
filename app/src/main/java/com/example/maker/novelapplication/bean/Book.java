package com.example.maker.novelapplication.bean;

public class Book {
    private String id;
    private String creatTime;
    private String name;
    private String chapter;
    private String creaotrname;
    private String detail;
    private String img;

    public Book(String id, String creatTime, String name, String chapter, String creaotrname, String detail, String img) {
        this.id = id;
        this.creatTime = creatTime;
        this.name = name;
        this.chapter = chapter;
        this.creaotrname = creaotrname;
        this.detail = detail;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getCreaotrname() {
        return creaotrname;
    }

    public void setCreaotrname(String creaotrname) {
        this.creaotrname = creaotrname;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
