package com.zls.bishe.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_info")
public class SysInfo {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    /**
     * 信息
     */
    private String message;

    /**
     * 类型 （0为系统通知，1为修改信息 默认为0）
     */
    private Integer type;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * CURRENT_TIMESTAMP
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取信息
     *
     * @return message - 信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置信息
     *
     * @param message 信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取类型 （0为系统通知，1为修改信息 默认为0）
     *
     * @return type - 类型 （0为系统通知，1为修改信息 默认为0）
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 （0为系统通知，1为修改信息 默认为0）
     *
     * @param type 类型 （0为系统通知，1为修改信息 默认为0）
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取CURRENT_TIMESTAMP
     *
     * @return update_time - CURRENT_TIMESTAMP
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置CURRENT_TIMESTAMP
     *
     * @param updateTime CURRENT_TIMESTAMP
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}