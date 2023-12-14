package cn.cofco.cpmp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cofco.cpmp.dao.UserDao;
import cn.cofco.cpmp.entity.User;
import cn.cofco.cpmp.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Resource
	private UserDao userDao;
	
//	@Override
//	public Result addUser(User user) {
//		Integer count = userDao.insert(user);
//
//		Result result = new Result();
//		result.setSuccess(true);
//		result.setCount(count);
//		return result;
//	}

}
