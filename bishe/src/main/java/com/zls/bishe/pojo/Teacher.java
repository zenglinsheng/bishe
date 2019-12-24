package com.zls.bishe.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Teacher {
    /**
     * 用户id
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 工号
     */
    @Column(name = "job_number")
    private String jobNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    @Column(name = "user_img")
    private String userImg;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别（0为男，1为女，默认为0）
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 是否为领导（0为普通教师，1为领导，默认为0）
     */
    @Column(name = "is_leader")
    private Integer isLeader;

    /**
     * 籍贯
     */
    @Column(name = "native_place")
    private String nativePlace;

    /**
     * 民族
     */
    private String nation;

    /**
     * 婚姻状况（0为未婚，1为已婚，2为未知，默认为0）
     */
    @Column(name = "marital_status")
    private Integer maritalStatus;

    /**
     * 政治面貌（0为群众，1为中共党员）
     */
    @Column(name = "political_appearance")
    private Integer politicalAppearance;

    /**
     * 最高职称（0为讲师，1为助教，2为副教授，3为教授，默认为0）
     */
    @Column(name = "top_title")
    private Integer topTitle;

    /**
     * 现聘职称（0为讲师，1为助教，2为副教授，3为教授，默认为0）
     */
    @Column(name = "current_title")
    private Integer currentTitle;

    /**
     * 参加工作时间
     */
    @Column(name = "working_time")
    private Date workingTime;

    /**
     * 在职状况（0为在职，1为修业）
     */
    @Column(name = "job_status")
    private Integer jobStatus;

    /**
     * 行政职务
     */
    @Column(name = "administrative_position")
    private String administrativePosition;

    /**
     * 身份证号
     */
    @Column(name = "id_number")
    private String idNumber;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 第一学历（0为学士，1为硕士，3为博士，默认为0）
     */
    @Column(name = "first_degree")
    private Integer firstDegree;

    /**
     * 最高学历（0为学士，1为硕士，3为博士，默认为0）
     */
    @Column(name = "highest_degree")
    private Integer highestDegree;

    /**
     * 户口所在地
     */
    @Column(name = "registered_residence")
    private String registeredResidence;

    /**
     * 编制情况（0为在编，1为非在编，默认为0）
     */
    private Integer compilation;

    /**
     * 审核状态（0为审核中，1为已审核 默认为1）
     */
    @Column(name = "review_status")
    private Integer reviewStatus;

    /**
     * 邮编
     */
    @Column(name = "zip_code")
    private String zipCode;

    /**
     * 进校时间
     */
    @Column(name = "entrance_time")
    private Date entranceTime;

    /**
     * 家庭住址
     */
    @Column(name = "family_address")
    private String familyAddress;

    /**
     * 住宅电话
     */
    @Column(name = "residential_tel")
    private String residentialTel;

    /**
     * 离职时间
     */
    @Column(name = "departure_time")
    private Date departureTime;

    /**
     * 是否删除（0为未删，1为已删，默认为0）
     */
    @Column(name = "delete_status")
    private Integer deleteStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取工号
     *
     * @return job_number - 工号
     */
    public String getJobNumber() {
        return jobNumber;
    }

    /**
     * 设置工号
     *
     * @param jobNumber 工号
     */
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取头像
     *
     * @return user_img - 头像
     */
    public String getUserImg() {
        return userImg;
    }

    /**
     * 设置头像
     *
     * @param userImg 头像
     */
    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取性别（0为男，1为女，默认为0）
     *
     * @return gender - 性别（0为男，1为女，默认为0）
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置性别（0为男，1为女，默认为0）
     *
     * @param gender 性别（0为男，1为女，默认为0）
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取出生日期
     *
     * @return birthday - 出生日期
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置出生日期
     *
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取是否为领导（0为普通教师，1为领导，默认为0）
     *
     * @return is_leader - 是否为领导（0为普通教师，1为领导，默认为0）
     */
    public Integer getIsLeader() {
        return isLeader;
    }

    /**
     * 设置是否为领导（0为普通教师，1为领导，默认为0）
     *
     * @param isLeader 是否为领导（0为普通教师，1为领导，默认为0）
     */
    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }

    /**
     * 获取籍贯
     *
     * @return native_place - 籍贯
     */
    public String getNativePlace() {
        return nativePlace;
    }

    /**
     * 设置籍贯
     *
     * @param nativePlace 籍贯
     */
    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    /**
     * 获取民族
     *
     * @return nation - 民族
     */
    public String getNation() {
        return nation;
    }

    /**
     * 设置民族
     *
     * @param nation 民族
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * 获取婚姻状况（0为未婚，1为已婚，2为未知，默认为0）
     *
     * @return marital_status - 婚姻状况（0为未婚，1为已婚，2为未知，默认为0）
     */
    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * 设置婚姻状况（0为未婚，1为已婚，2为未知，默认为0）
     *
     * @param maritalStatus 婚姻状况（0为未婚，1为已婚，2为未知，默认为0）
     */
    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * 获取政治面貌（0为群众，1为中共党员）
     *
     * @return political_appearance - 政治面貌（0为群众，1为中共党员）
     */
    public Integer getPoliticalAppearance() {
        return politicalAppearance;
    }

    /**
     * 设置政治面貌（0为群众，1为中共党员）
     *
     * @param politicalAppearance 政治面貌（0为群众，1为中共党员）
     */
    public void setPoliticalAppearance(Integer politicalAppearance) {
        this.politicalAppearance = politicalAppearance;
    }

    /**
     * 获取最高职称（0为讲师，1为助教，2为副教授，3为教授，默认为0）
     *
     * @return top_title - 最高职称（0为讲师，1为助教，2为副教授，3为教授，默认为0）
     */
    public Integer getTopTitle() {
        return topTitle;
    }

    /**
     * 设置最高职称（0为讲师，1为助教，2为副教授，3为教授，默认为0）
     *
     * @param topTitle 最高职称（0为讲师，1为助教，2为副教授，3为教授，默认为0）
     */
    public void setTopTitle(Integer topTitle) {
        this.topTitle = topTitle;
    }

    /**
     * 获取现聘职称（0为讲师，1为助教，2为副教授，3为教授，默认为0）
     *
     * @return current_title - 现聘职称（0为讲师，1为助教，2为副教授，3为教授，默认为0）
     */
    public Integer getCurrentTitle() {
        return currentTitle;
    }

    /**
     * 设置现聘职称（0为讲师，1为助教，2为副教授，3为教授，默认为0）
     *
     * @param currentTitle 现聘职称（0为讲师，1为助教，2为副教授，3为教授，默认为0）
     */
    public void setCurrentTitle(Integer currentTitle) {
        this.currentTitle = currentTitle;
    }

    /**
     * 获取参加工作时间
     *
     * @return working_time - 参加工作时间
     */
    public Date getWorkingTime() {
        return workingTime;
    }

    /**
     * 设置参加工作时间
     *
     * @param workingTime 参加工作时间
     */
    public void setWorkingTime(Date workingTime) {
        this.workingTime = workingTime;
    }

    /**
     * 获取在职状况（0为在职，1为修业）
     *
     * @return job_status - 在职状况（0为在职，1为修业）
     */
    public Integer getJobStatus() {
        return jobStatus;
    }

    /**
     * 设置在职状况（0为在职，1为修业）
     *
     * @param jobStatus 在职状况（0为在职，1为修业）
     */
    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * 获取行政职务
     *
     * @return administrative_position - 行政职务
     */
    public String getAdministrativePosition() {
        return administrativePosition;
    }

    /**
     * 设置行政职务
     *
     * @param administrativePosition 行政职务
     */
    public void setAdministrativePosition(String administrativePosition) {
        this.administrativePosition = administrativePosition;
    }

    /**
     * 获取身份证号
     *
     * @return id_number - 身份证号
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * 设置身份证号
     *
     * @param idNumber 身份证号
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * 获取手机号码
     *
     * @return telephone - 手机号码
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置手机号码
     *
     * @param telephone 手机号码
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取第一学历（0为学士，1为硕士，3为博士，默认为0）
     *
     * @return first_degree - 第一学历（0为学士，1为硕士，3为博士，默认为0）
     */
    public Integer getFirstDegree() {
        return firstDegree;
    }

    /**
     * 设置第一学历（0为学士，1为硕士，3为博士，默认为0）
     *
     * @param firstDegree 第一学历（0为学士，1为硕士，3为博士，默认为0）
     */
    public void setFirstDegree(Integer firstDegree) {
        this.firstDegree = firstDegree;
    }

    /**
     * 获取最高学历（0为学士，1为硕士，3为博士，默认为0）
     *
     * @return highest_degree - 最高学历（0为学士，1为硕士，3为博士，默认为0）
     */
    public Integer getHighestDegree() {
        return highestDegree;
    }

    /**
     * 设置最高学历（0为学士，1为硕士，3为博士，默认为0）
     *
     * @param highestDegree 最高学历（0为学士，1为硕士，3为博士，默认为0）
     */
    public void setHighestDegree(Integer highestDegree) {
        this.highestDegree = highestDegree;
    }

    /**
     * 获取户口所在地
     *
     * @return registered_residence - 户口所在地
     */
    public String getRegisteredResidence() {
        return registeredResidence;
    }

    /**
     * 设置户口所在地
     *
     * @param registeredResidence 户口所在地
     */
    public void setRegisteredResidence(String registeredResidence) {
        this.registeredResidence = registeredResidence;
    }

    /**
     * 获取编制情况（0为在编，1为非在编，默认为0）
     *
     * @return compilation - 编制情况（0为在编，1为非在编，默认为0）
     */
    public Integer getCompilation() {
        return compilation;
    }

    /**
     * 设置编制情况（0为在编，1为非在编，默认为0）
     *
     * @param compilation 编制情况（0为在编，1为非在编，默认为0）
     */
    public void setCompilation(Integer compilation) {
        this.compilation = compilation;
    }

    /**
     * 获取邮编
     *
     * @return zip_code - 邮编
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮编
     *
     * @param zipCode 邮编
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 获取进校时间
     *
     * @return entrance_time - 进校时间
     */
    public Date getEntranceTime() {
        return entranceTime;
    }

    /**
     * 设置进校时间
     *
     * @param entranceTime 进校时间
     */
    public void setEntranceTime(Date entranceTime) {
        this.entranceTime = entranceTime;
    }

    /**
     * 获取家庭住址
     *
     * @return family_address - 家庭住址
     */
    public String getFamilyAddress() {
        return familyAddress;
    }

    /**
     * 设置家庭住址
     *
     * @param familyAddress 家庭住址
     */
    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    /**
     * 获取住宅电话
     *
     * @return residential_tel - 住宅电话
     */
    public String getResidentialTel() {
        return residentialTel;
    }

    /**
     * 设置住宅电话
     *
     * @param residentialTel 住宅电话
     */
    public void setResidentialTel(String residentialTel) {
        this.residentialTel = residentialTel;
    }

    /**
     * 获取离职时间
     *
     * @return departure_time - 离职时间
     */
    public Date getDepartureTime() {
        return departureTime;
    }

    /**
     * 设置离职时间
     *
     * @param departureTime 离职时间
     */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * 获取是否删除（0为未删，1为已删，默认为0）
     *
     * @return delete_status - 是否删除（0为未删，1为已删，默认为0）
     */
    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置是否删除（0为未删，1为已删，默认为0）
     *
     * @param deleteStatus 是否删除（0为未删，1为已删，默认为0）
     */
    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}