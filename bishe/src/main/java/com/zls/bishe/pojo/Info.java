package com.zls.bishe.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Info {
    @Id
    @Column(name = "info_id")
    private String infoId;

    @Column(name = "user_id")
    private String userId;

    /**
     * 类型 （0为学生，1为老师，3为领导）
     */
    private Integer type;

    /**
     * 是否通过 (0为未审核，1为通过，2为作废 默认为0)
     */
    @Column(name = "is_adopt")
    private Integer isAdopt;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 修改的信息
     */
    private String information;

    /**
     * @return info_id
     */
    public String getInfoId() {
        return infoId;
    }

    /**
     * @param infoId
     */
    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    /**
     * 获取类型 （0为学生，1为老师，3为领导）
     *
     * @return type - 类型 （0为学生，1为老师，3为领导）
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 （0为学生，1为老师，3为领导）
     *
     * @param type 类型 （0为学生，1为老师，3为领导）
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取是否通过
     *
     * @return is_adopt - 是否通过
     */
    public Integer getIsAdopt() {
        return isAdopt;
    }

    /**
     * 设置是否通过
     *
     * @param isAdopt 是否通过
     */
    public void setIsAdopt(Integer isAdopt) {
        this.isAdopt = isAdopt;
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
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取修改的信息
     *
     * @return information - 修改的信息
     */
    public String getInformation() {
        return information;
    }

    /**
     * 设置修改的信息
     *
     * @param information 修改的信息
     */
    public void setInformation(String information) {
        this.information = information;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}