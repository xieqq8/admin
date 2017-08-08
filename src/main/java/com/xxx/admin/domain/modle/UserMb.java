package com.xxx.admin.domain.modle;

import java.util.Date;
import java.util.UUID;

public class UserMb {
    /** 主键ID */
    private String id = UUID.randomUUID().toString();

    private String username;

    private String password;

    private String email;

    private String salt;

    private boolean disabled = false;

    private Date createtime;

    private Date lasttime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

//    public Short getDisabled() {
//        return disabled;
//    }
//
//    public void setDisabled(Short disabled) {
//        this.disabled = disabled;
//    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getCreateTime() {
        return createtime;
    }

    public void setCreateTime(Date createTime) {
        this.createtime = createTime;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public boolean isRoot() {
        return "root".equals(username);
    }
}