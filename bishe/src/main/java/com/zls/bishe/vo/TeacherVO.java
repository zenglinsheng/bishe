package com.zls.bishe.vo;

import com.zls.bishe.common.annotation.Equal;
import com.zls.bishe.common.annotation.FullLike;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class TeacherVO {

    private String userId;

    @FullLike
    @ApiModelProperty("工号")
    private String jobNumber;

    private String userImg;

    @ApiModelProperty("姓名")
    private String username;

    private Integer age;

    private Date birthday;

    private String telephone;

    private String email;

    private Integer deleteStatus;

    @Equal
    @ApiModelProperty("性别 0为男，1为女")
    private Integer gender;

    @Equal
    @ApiModelProperty("性别 0为普通教师，1为领导")
    private Integer isLeader;

    @Equal
    @ApiModelProperty("性别 0为讲师，1为助教，2为副教授，3为教授")
    private Integer currentTitle;

    @ApiModelProperty("行政职务")
    private String administrativePosition;

    private String birthdayStr;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }
}
