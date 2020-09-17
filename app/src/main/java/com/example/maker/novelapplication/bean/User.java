package com.example.maker.novelapplication.bean;

import android.graphics.Bitmap;

public class User {
    private String name;
    private String age;
    private String sex;
    private String id;
    private String phone;
    private String headimg;
    private String mail;
    private String password;
    private String mark;
    public User(){

    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public User(String name, String age, String sex, String id, String phone, String headimg, String mail, String password, String mark) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.id = id;
        this.phone = phone;
        this.headimg = headimg;
        this.mail = mail;
        this.password = password;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
