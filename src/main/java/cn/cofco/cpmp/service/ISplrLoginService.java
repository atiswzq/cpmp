package cn.cofco.cpmp.service;

import cn.cofco.cpmp.utils.CurrentSplrUserInfo;

public interface ISplrLoginService {

	CurrentSplrUserInfo currentUserInfo(String access_token);
}
