package cn.cofco.cpmp.utils;

import org.json.JSONObject;

/**
 * Created by jige0727 on 2017/5/30.
 * 角色信息
 */
public class CurrentUserInfoRole {
    private String role_name; //角色名称
    private String role_code; //角色编号

    public CurrentUserInfoRole(JSONObject data) {
        role_name = "" + data.get("role_name");
        role_code = "" + data.get("role_code");
    }

    public CurrentUserInfoRole(String role_name, String role_code) {
        this.role_name = role_name;
        this.role_code = role_code;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_code() {
        return role_code;
    }

    public void setRole_code(String role_code) {
        this.role_code = role_code;
    }
}
