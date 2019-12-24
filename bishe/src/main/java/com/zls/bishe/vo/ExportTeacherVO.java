package com.zls.bishe.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class ExportTeacherVO {

    @Excel(name = "工号", orderNum = "0", width=30)
    private String jobNumber;

    @Excel(name = "姓名", orderNum = "1", width=30)
    private String username;

    @Excel(name = "性别", orderNum = "2", width=30)
    private String genderStr;

    @Excel(name = "年龄", orderNum = "3", width=30)
    private Integer age;

    @Excel(name = "出生年月", orderNum = "4", width=30)
    private String birthdayStr;

    @Excel(name = "邮箱", orderNum = "5", width=30)
    private String email;

    @Excel(name = "现聘职称", orderNum = "6", width=30)
    private Integer currentTitle;

    @Excel(name = "行政职务", orderNum = "7", width=30)
    private String administrativePosition;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(Integer currentTitle) {
        this.currentTitle = currentTitle;
    }

    public String getAdministrativePosition() {
        return administrativePosition;
    }

    public void setAdministrativePosition(String administrativePosition) {
        this.administrativePosition = administrativePosition;
    }

    public String getGenderStr() {
        return genderStr;
    }

    public void setGenderStr(String genderStr) {
        this.genderStr = genderStr;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }
}
