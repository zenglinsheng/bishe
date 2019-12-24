package com.zls.bishe.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Classes {
    /**
     * 班级id
     */
    @Id
    @Column(name = "class_id")
    private Integer classId;

    /**
     * 班级名
     */
    private String classname;

    /**
     * 人数
     */
    private Integer num;

    /**
     * 班主任
     */
    @Column(name = "headmaster_id")
    private String headmasterId;

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
     * 获取班级id
     *
     * @return class_id - 班级id
     */
    public Integer getClassId() {
        return classId;
    }

    /**
     * 设置班级id
     *
     * @param classId 班级id
     */
    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    /**
     * 获取班级名
     *
     * @return classname - 班级名
     */
    public String getClassname() {
        return classname;
    }

    /**
     * 设置班级名
     *
     * @param classname 班级名
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * 获取人数
     *
     * @return num - 人数
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置人数
     *
     * @param num 人数
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取班主任
     *
     * @return headmaster_id - 班主任
     */
    public String getHeadmasterId() {
        return headmasterId;
    }

    /**
     * 设置班主任
     *
     * @param headmasterId 班主任
     */
    public void setHeadmasterId(String headmasterId) {
        this.headmasterId = headmasterId;
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