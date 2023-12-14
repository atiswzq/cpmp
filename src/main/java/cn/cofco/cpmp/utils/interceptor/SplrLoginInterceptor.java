package cn.cofco.cpmp.utils.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrLoginService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.CurrentSplrUserInfo;
import cn.cofco.cpmp.utils.SplrContextTools;

/**
 * 供应商账户拦截器
 */
public class SplrLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private ISplrLoginService splrLoginService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        LoggerManager.getSplrMngLog().info("uri -> " + uri);
        String login_type = httpServletRequest.getHeader("login_type");
        
        if (login_type == null) {
        	return true;
        }
        
        // 验证login_type参数内容
        if (login_type != null && login_type.length() == 0) {
        	httpServletResponse.sendError(201, "login_type must set!");
        	httpServletResponse.addHeader("title", "lost param");
        	return false;
        }
        
        // 如果不是供应商账户，则通过
        if (login_type == null || !login_type.equalsIgnoreCase("splr")) {
        	return true;
        }
        
        if (uri.endsWith("/splrSlfMng/rgst") || uri.endsWith("/splrSlfMng/login")) {
        	// 不用判断注册或者是登录
            return true;
        } else {
        	String access_token = httpServletRequest.getHeader("access_token");
        	LoggerManager.getSplrMngLog().info("access_token -> " + access_token);
        	splrLoginService.currentUserInfo(access_token);
        	//使用这个工具类获取当前的用户信息
        	CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
        	if (currentSplrUserInfo.getAcntId() == null) {
        		
        		// token 验证失败
        		OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.LOGIN_ERR,
        				RtnEnum.LOGIN_ERR.getDesc());
        		httpServletResponse.setCharacterEncoding("UTF-8");  
        		httpServletResponse.setContentType("application/json; charset=utf-8");
        		PrintWriter out = null;  
        	    try {  
        	        out = httpServletResponse.getWriter();  
        	        out.append(outputDto.toJson().toString());  
        	    } catch (IOException e) {  
        	        e.printStackTrace();  
        	    } finally {  
        	        if (out != null) {  
        	            out.close();  
        	        }  
        	    }
        		return false;
        	}
            LoggerManager.getSplrMngLog().info("splrAcnt -> " + new JSONObject(currentSplrUserInfo));
        }
        return true;
    }
}
