package cn.cofco.cpmp.utils;

import org.json.JSONObject;

/**
 * Created by jige0727 on 2017/5/30.
 * 页面资源权限
 */
public class CurrentUserInfoPageResource {
    private String rese_name = "";
    private String rese_uri = "";//唯一标示符 页面地址或者按钮地址
    private String rese_type = ""; //元素类型 1页面 2按钮
    private Integer sort_order = -1; //排序号
    private Integer parent_rese_id = -1; //上级的id 根节点则为 null
    private String parent_rese_name = ""; //上级的名称
    private String struct = ""; //结构 每个节点的id拼一起 -隔开

    public CurrentUserInfoPageResource(JSONObject data) {
        rese_name = data.has("rese_name") ? "" + data.get("rese_name") : null;
        rese_uri = data.has("rese_uri") ? "" + data.get("rese_uri") : null;
        rese_type = data.has("rese_type") ? "" + data.get("rese_type") : null;
        sort_order = data.has("sort_order") ? data.getInt("sort_order") : null;
        parent_rese_id = data.has("parent_rese_id") ? data.getInt("parent_rese_id") : null;
        parent_rese_name = data.has("parent_rese_name") ? "" + data.get("parent_rese_name") : null;
        struct = data.has("struct") ? "" + data.get("struct") : null;
    }

    public String getRese_name() {
        return rese_name;
    }

    public void setRese_name(String rese_name) {
        this.rese_name = rese_name;
    }

    public String getRese_uri() {
        return rese_uri;
    }

    public void setRese_uri(String rese_uri) {
        this.rese_uri = rese_uri;
    }

    public String getRese_type() {
        return rese_type;
    }

    public void setRese_type(String rese_type) {
        this.rese_type = rese_type;
    }

    public Integer getSort_order() {
        return sort_order;
    }

    public void setSort_order(Integer sort_order) {
        this.sort_order = sort_order;
    }

    public Integer getParent_rese_id() {
        return parent_rese_id;
    }

    public void setParent_rese_id(Integer parent_rese_id) {
        this.parent_rese_id = parent_rese_id;
    }

    public String getParent_rese_name() {
        return parent_rese_name;
    }

    public void setParent_rese_name(String parent_rese_name) {
        this.parent_rese_name = parent_rese_name;
    }

    public String getStruct() {
        return struct;
    }

    public void setStruct(String struct) {
        this.struct = struct;
    }
}
