package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IApiService;
import cn.cofco.cpmp.utils.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 杨宏毅 on 2017/5/30.
 */
@Service
public class ApiServiceImpl implements IApiService {
    @Value("${umsApiUrl}")
    String umsApiUrl;
    String appkey = "0cb11943b1e14163a0948acb15245e33";

    @Override
    public String getAccessToken(String username, String password, String userType) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("userType", userType);
        params.put("appkey", appkey);
        String res = new JSONObject().put("success", false).put("msg", "错误").toString();
        try {
            res = HttpUtil.sendHttpPost(umsApiUrl + "/accessToken", params);
        } catch (Exception e) {
            LoggerManager.getSplrMngLog().error("错误-> ", e);
        }
        return res;
    }

    @Override
    public String getProfile(String access_token, String userType) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", access_token);
        params.put("userType", userType);
        params.put("appkey", appkey);
        String res = new JSONObject().put("success", false).put("msg", "错误").toString();
        try {
            res = HttpUtil.sendHttpPost(umsApiUrl + "/profile", params);
        } catch (Exception e) {
            LoggerManager.getSplrMngLog().error("错误-> ", e);
        }
        return res;
    }

    @Override
    /**
     * 获取当前登录的账号信息
     *
     * @param access_token
     * @return
     */
    public CurrentUserInfo flushCurrentUserInfo(String access_token) {
        try {
            return flushCurrentUserInfoInner(access_token);
        } catch (Exception e) {
            LoggerManager.getSplrMngLog().info("error->", e);
        }
        return null;
    }

    private CurrentUserInfo flushCurrentUserInfoInner(String access_token) throws Exception {
        LoggerManager.getSplrMngLog().info("umsApiUrl->" + umsApiUrl);
        LoggerManager.getSplrMngLog().info("appkey->" + appkey);
        CurrentUserInfo info = ContextTools.appContext.getBean(CurrentUserInfo.class);
        LoggerManager.getSplrMngLog().info("into->" + info);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", access_token);
        params.put("appkey", appkey);
        String res = "";
        try {
            res = HttpUtil.sendHttpPost(umsApiUrl + "/profile", params);
            LoggerManager.getSplrMngLog().info("res->" + res);
            JSONObject respData = new JSONObject(res);
            if (respData.has("error")) {
                info.clear();
            }
            info.setUserid(respData.getInt("uid"));
            info.setUsername(respData.getString("username"));
            info.setRealname(respData.getString("realname"));
            //
            res = HttpUtil.sendHttpPost(umsApiUrl + "/userinfo", params);
            LoggerManager.getSplrMngLog().info("res->" + res);
            respData = new JSONObject(res);
            JSONArray roles = respData.getJSONArray("roles");
            for (int i = 0; i < roles.length(); i++) {
                info.getRoles().add(new CurrentUserInfoRole(roles.getJSONObject(i)));
            }
            //设置角色
            JSONArray resources = respData.getJSONArray("resources");
            for (int i = 0; i < resources.length(); i++) {
                info.getResources().add(new CurrentUserInfoPageResource(resources.getJSONObject(i)));
            }
            //设置资源
            if (respData.has("factory")) {
                info.setFactory(new CurrentUserInfoFactory(respData.getJSONObject("factory")));
            }
            if (respData.has("company")) {
                info.setCompany(new CurrentUserInfoFactory(respData.getJSONObject("company")));
            }
            if (respData.has("manageCompanies")) {
                JSONArray manageCompanies = respData.getJSONArray("manageCompanies");
                for (int i = 0; i < manageCompanies.length(); i++) {
                    info.getManageCompanies().add(new CurrentUserInfoFactory(manageCompanies.getJSONObject(i)));
                }
            }
            if (respData.has("parentDept")) {
                info.setParentDept(new CurrentUserInfoFactory(respData.getJSONObject("parentDept")));
            }
        } catch (Exception e) {
            LoggerManager.getSplrMngLog().error("错误-> ", e);
            info.clear();
        }
        return info;
    }

    @Override
    public String region_all() {
        String res = null;
        try {
            res = HttpUtil.sendHttpGet(umsApiUrl + "/region_all");
        } catch (Exception e) {
            LoggerManager.getSplrMngLog().error("错误-> ", e);
        }
        if (res == null) {
            res = new JSONObject().put("msg", "错误").toString();
        }
        return res;
    }

    @Override
    public String pageresources_bycode(String rolecode) {
        Map<String, String> params = new HashMap<>();
        params.put("roleCode", rolecode);

        String res = null;
        try {
            res = HttpUtil.sendHttpPost(umsApiUrl + "/pageresources/bycode", params);
        } catch (Exception e) {
            LoggerManager.getSplrMngLog().error("错误-> ", e);
        }
        if (res == null) {
            res = new JSONObject().put("msg", "错误").toString();
        }
        return res;
    }

    @Override
    public String allCompany(String deptName) {

        Map<String, String> params = new HashMap<>();
        if (null != deptName && !"{}".equals(deptName)) {
            params.put("dept_name", deptName);
        }

        String res = null;
        try {
            res = HttpUtil.sendHttpPost(umsApiUrl + "/search/company-all", params);
        } catch (Exception e) {
            LoggerManager.getSysLog().error("查询公司列表错误：[{}]", e);
        }
        if (null == res) {
            res = new JSONObject().put("msg", "查询公司列表错误!").toString();
        }

        return res;
    }
}
