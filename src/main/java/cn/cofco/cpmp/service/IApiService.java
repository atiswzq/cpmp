package cn.cofco.cpmp.service;

import cn.cofco.cpmp.utils.CurrentUserInfo;

/**
 * Created by jige0727 on 2017/5/30.
 */
public interface IApiService {
    String getAccessToken(String username, String password, String userType);

    String getProfile(String access_token, String userType);

    CurrentUserInfo flushCurrentUserInfo(String access_token);

    String region_all();

    String pageresources_bycode(String rolecode);

    String allCompany(String deptName);
}
