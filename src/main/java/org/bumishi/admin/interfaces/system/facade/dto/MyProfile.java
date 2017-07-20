package org.bumishi.admin.interfaces.system.facade.dto;

/**
 * 用户个人信息
 *
 * @author qiang.xie
 * @date 2016/11/4
 */
public class MyProfile {

    private String username;

    private String email;

    public MyProfile(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
