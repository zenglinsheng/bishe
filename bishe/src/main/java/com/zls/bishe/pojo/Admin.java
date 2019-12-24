package com.zls.bishe.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Admin {
    /**
     * 管理员id
     */
    @Id
    @Column(name = "admin_id")
    private String adminId;

    /**
     * 账号
     */
    @Column(name = "account_number")
    private String accountNumber;

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
     * 管理员名
     */
    private String username;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 电子邮箱
     */
    private String email;

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
     * 获取管理员id
     *
     * @return admin_id - 管理员id
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * 设置管理员id
     *
     * @param adminId 管理员id
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    /**
     * 获取账号
     *
     * @return account_number - 账号
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 设置账号
     *
     * @param accountNumber 账号
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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
     * 获取管理员名
     *
     * @return username - 管理员名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置管理员名
     *
     * @param username 管理员名
     */
    public void setUsername(String username) {
        this.username = username;
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
}