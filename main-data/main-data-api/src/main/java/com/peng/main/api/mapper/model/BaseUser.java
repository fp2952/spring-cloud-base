package com.peng.main.api.mapper.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "base_user")
public class BaseUser implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PHONE")
    private String phone;

    /**
     * male 男、female 女
     */
    @Column(name = "GENDER")
    private String gender;

    @Column(name = "AGE")
    private Integer age;

    /**
     * 0 禁用、1 启用
     */
    @Column(name = "ACTIVE")
    private Integer active;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    /**
     * @return ID
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
     * @return USER_NAME
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return PASSWORD
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return PHONE
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取male 男、female 女
     *
     * @return GENDER - male 男、female 女
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置male 男、female 女
     *
     * @param gender male 男、female 女
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return AGE
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取0 禁用、1 启用
     *
     * @return ACTIVE - 0 禁用、1 启用
     */
    public Integer getActive() {
        return active;
    }

    /**
     * 设置0 禁用、1 启用
     *
     * @param active 0 禁用、1 启用
     */
    public void setActive(Integer active) {
        this.active = active;
    }

    /**
     * @return CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return UPDATE_DATE
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}