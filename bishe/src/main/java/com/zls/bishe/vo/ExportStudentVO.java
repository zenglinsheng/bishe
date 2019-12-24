package com.zls.bishe.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class ExportStudentVO {

    @Excel(name = "学号", orderNum = "0", width=30)
    private String number;

    @Excel(name = "姓名", orderNum = "1", width=30)
    private String username;

    @Excel(name = "性别", orderNum = "2", width=30)
    private String genderStr;

    @Excel(name = "出生年月", orderNum = "3", width=30)
    private String birthdayStr;

    @Excel(name = "年龄", orderNum = "4", width=30)
    private Integer age;

    @Excel(name = "班级", orderNum = "5", width=30)
    private String className;

    @Excel(name = "电话", orderNum = "6", width=30)
    private String telephone;

    @Excel(name = "邮箱", orderNum = "7", width=30)
    private String email;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGenderStr() {
        return genderStr;
    }

    public void setGenderStr(String genderStr) {
        this.genderStr = genderStr;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }
}
