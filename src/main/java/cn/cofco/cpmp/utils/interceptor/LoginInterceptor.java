package cn.cofco.cpmp.utils.interceptor;

import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IApiService;
import cn.cofco.cpmp.utils.ContextTools;
import cn.cofco.cpmp.utils.CurrentUserInfo;
import cn.cofco.cpmp.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cas操作步骤 获取登录状态
 * 只获取状态 不处理
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private IApiService apiService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        LoggerManager.getSplrMngLog().info("uri -> " + uri);
        String login_type = request.getHeader("login_type");
        //如果是供应商登录 则不通过ums
        if (login_type == null || !login_type.equals("ums")) {
            return true;
        }
        if (uri.endsWith("accessToken") || uri.endsWith("profile")) {
            //不需要判断登录
            return true;
        } else {
            String access_token = request.getHeader("access_token");
            if (StringUtils.isEmpty(access_token)) {
                response.sendError(201, "token access field!");
                return false;
            }

            LoggerManager.getBusiLog().info("access_token -> " + access_token);
            apiService.flushCurrentUserInfo(access_token);
            //使用这个工具类获取当前的用户信息
            CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
            if (userinfo.getUserid() == null) {
                response.sendError(201, "token access field!");
                return false;
            }
            LoggerManager.getBusiLog().info("userinfo -> " + new JSONObject(userinfo));
        }
        return true;
    }
}
