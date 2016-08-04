package net.jeeshop.services.manage.auth.impl;


import com.tuisitang.modules.shop.entity.Authentication;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.manage.auth.AuthService;
import net.jeeshop.services.manage.auth.dao.AuthDao;

public class AuthServiceImpl extends ServersManager<Authentication> implements AuthService{
	private AuthDao authDao;

	public void setAuthDao(AuthDao authDao) {
		this.authDao = authDao;
	}

}
