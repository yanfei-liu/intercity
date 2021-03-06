package com.ld.intercity.business.login.service;


import com.ld.intercity.business.login.entity.Login;

public interface LoginService {
    Login getByOpenId(String openId);

    Login getByAccount(String account);

    int save(Login login);
}
