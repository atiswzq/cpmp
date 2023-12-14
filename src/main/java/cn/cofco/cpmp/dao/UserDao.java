package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.User;


public interface UserDao {
	
	/**
	 * 插入用户
	 * @param user
	 * @return
	 */
	Integer insert(User user);
}
