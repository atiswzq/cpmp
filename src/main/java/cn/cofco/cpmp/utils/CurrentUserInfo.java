package cn.cofco.cpmp.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jige0727 on 2017/5/30.
 * 当前的账号信息
 */
@Component
@Scope("request")
public class CurrentUserInfo {
    Integer userid; //用户id
    String username; //账号
    String realname; //姓名
    String orgId = "04"; //组织
    String reseIndex ; //角色代号
    List<CurrentUserInfoRole> roles = new ArrayList<>(); //角色列表
    List<CurrentUserInfoPageResource> resources = new ArrayList<>(); //页面资源列表
    CurrentUserInfoFactory factory = null;
    CurrentUserInfoFactory parentDept = null;
    CurrentUserInfoFactory company = null;
    List<CurrentUserInfoFactory> manageCompanies = new ArrayList<>();

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public List<CurrentUserInfoRole> getRoles() {
        return roles;
    }

    public List<CurrentUserInfoPageResource> getResources() {
        return resources;
    }

    public CurrentUserInfoFactory getFactory() {
        return factory;
    }

    public void setFactory(CurrentUserInfoFactory factory) {
        this.factory = factory;
    }

    public CurrentUserInfoFactory getParentDept() {
        return parentDept;
    }

    public void setParentDept(CurrentUserInfoFactory parentDept) {
        this.parentDept = parentDept;
    }

    public CurrentUserInfoFactory getCompany() {
        return company;
    }

    public void setCompany(CurrentUserInfoFactory company) {
        this.company = company;
    }

    public List<CurrentUserInfoFactory> getManageCompanies() {
        return manageCompanies;
    }

    public void setManageCompanies(List<CurrentUserInfoFactory> manageCompanies) {
        this.manageCompanies = manageCompanies;
    }

    public String getReseIndex() {
        return reseIndex;
    }

    public void setReseIndex(String reseIndex) {
        this.reseIndex = reseIndex;
    }

    /**
     * 如果请求错误则清空数据
     */
    public void clear() {
        userid = -1;
        username = "";
        realname = "";
        roles.clear();
        resources.clear();
        factory = null;
    }
}
