package com.ace.entities;


import java.time.LocalDateTime;

/**
 * @Classname: UsersDto
 * @Date: 21/3/2024 10:04 pm
 * @Author: garlam
 * @Description:
 */


public class UsersDto {

    //users_description
    public static final String ADMINISTRATOR = "administrator";
    // public static final String Editor = "Editor"; // part of read update insert
    public static final String DISABLE = "disable";
    public static final String USER = "user"; // read update
    public static final String VIEWER = "viewer"; // read only

    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";



    private Long userId;
    private String password;


    //personal info
    private String userAccount;
    private String username;
    private String description;
    private String email;
    private String mobile;
    private String gender;
    private LocalDateTime dateOfBirth;
    private long age = 0L;
    private LocalDateTime loginDateTime;
    private String status = "ACTIVE"; // ACTIVE || INACTIVE //决定模组功能是否开启
    private String region;
    private String domain;
    private String ip;
    private String hostName;
    private String remark;
    private LocalDateTime expireDate;
    private boolean enabled = true; // user account


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public LocalDateTime getLoginDateTime() {
        return loginDateTime;
    }

    public void setLoginDateTime(LocalDateTime loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

