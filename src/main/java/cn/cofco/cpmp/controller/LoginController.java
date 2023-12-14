package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.service.IApiService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 杨宏毅 on 2017/5/30.
 * 接入登录
 */
@Controller
public class LoginController {
    @Resource
    private IApiService apiService;

    /**
     * 获取token 然后以后的请求要把token放在header里面
     *
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/accessToken", produces = "application/json;charset=UTF-8")
    public String accessToken(@RequestBody String data, HttpServletRequest request) {
        JSONObject dataJ = new JSONObject(data);
        return apiService.getAccessToken(
                dataJ.getString("yonhum"),
                dataJ.getString("mim"),
                dataJ.getString("userType")
        );
    }

    @ResponseBody
    @RequestMapping(value = "profile", produces = "application/json;charset=UTF-8")
    public String profile(@RequestBody String data, HttpServletResponse response) {
        JSONObject dataJ = new JSONObject(data);
        return apiService.getProfile(
                dataJ.getString("access_token"),
                dataJ.getString("userType"));
    }

    @ResponseBody
    @RequestMapping(value = "login-test", produces = "application/json;charset=UTF-8")
    public String login_test() {
        return new JSONObject().put("res", "ok").toString();
    }
}
