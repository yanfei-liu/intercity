package com.ld.intercity.business.login.service.serviceImpl;

import com.ld.intercity.business.login.entity.Login;
import com.ld.intercity.business.login.mapper.LoginMapper;
import com.ld.intercity.business.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Override
    public Login getByOpenId(String openId) {
        return loginMapper.getByOpenId(openId);
    }

    @Override
    public Login getByAccount(String account) {
        return loginMapper.getByAccount(account);
    }

    @Override
    public int save(Login login) {
        return loginMapper.save(login);
    }
}
