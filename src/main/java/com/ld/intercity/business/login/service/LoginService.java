package com.ld.intercity.business.login.service;


import com.ld.intercity.business.login.entity.Login;

public interface LoginService {
    Login getByOpenId(String openId);

    int save(Login login);
}
