package cn.cofco.cpmp.utils;

import org.json.JSONObject;

/**
 * Created by 杨宏毅 on 2017/7/8.
 * Updated by 徐家园 on 2017-10-15 for 新增id字段
 * 工厂信息
 */
public class CurrentUserInfoFactory {
    private Integer id; // 当前节点ID
    private String dept_name;
    private String dept_code;
    private Integer parent_dept_id; //上级的id 根节点则为 null
    private String parent_dept_name; //上级的名称
    private String struct = ""; //结构 每个节点的id拼一起 -隔开
    private String mobilephone;
    private String email;
    private String tel;
    private String area;// 大区
    private String province;// 省份
    private String city;// 城市
    private String town;// 区县

    public CurrentUserInfoFactory(JSONObject data) {
        id = data.has("id") ? data.getInt("id") : null;
        dept_name = data.has("dept_name") ? "" + data.get("dept_name") : null;
        dept_code = data.has("dept_code") ? "" + data.get("dept_code") : null;
        parent_dept_id = data.has("parent_dept_id") ? data.getInt("parent_dept_id") : null;
        parent_dept_name = data.has("parent_dept_name") ? "" + data.get("parent_dept_name") : null;
        struct = data.has("struct") ? "" + data.get("struct") : null;
        mobilephone = data.has("mobilephone") ? "" + data.get("mobilephone") : null;
        email = data.has("email") ? "" + data.get("email") : null;
        tel = data.has("tel") ? "" + data.get("tel") : null;
        area = data.has("area") ? "" + data.get("area") : null;
        province = data.has("province") ? "" + data.get("province") : null;
        city = data.has("city") ? "" + data.get("city") : null;
        town = data.has("town") ? "" + data.get("town") : null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public Integer getParent_dept_id() {
        return parent_dept_id;
    }

    public void setParent_dept_id(Integer parent_dept_id) {
        this.parent_dept_id = parent_dept_id;
    }

    public String getParent_dept_name() {
        return parent_dept_name;
    }

    public void setParent_dept_name(String parent_dept_name) {
        this.parent_dept_name = parent_dept_name;
    }

    public String getStruct() {
        return struct;
    }

    public void setStruct(String struct) {
        this.struct = struct;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "CurrentUserInfoFactory{" +
                "id=" + id +
                ", dept_name='" + dept_name + '\'' +
                ", dept_code='" + dept_code + '\'' +
                ", parent_dept_id=" + parent_dept_id +
                ", parent_dept_name='" + parent_dept_name + '\'' +
                ", struct='" + struct + '\'' +
                ", mobilephone='" + mobilephone + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", area='" + area + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", town='" + town + '\'' +
                '}';
    }
}
