package com.ld.intercity.business.user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ld.intercity.business.user.mapper.UserMapper;
import com.ld.intercity.business.user.model.UserModel;
import com.ld.intercity.business.user.service.UserService;
import com.ld.intercity.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Explain
 * @Author Dong-Liu
 * @Date 2019/4/20 19:59
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public ResponseResult<UserModel> save(UserModel model) throws Exception {
        mapper.save(model);
        return new ResponseResult<>(true, "成功");
    }

    @Override
    public ResponseResult<UserModel> deleteById(String uuid) throws Exception {
        mapper.deleteById(uuid);
        return new ResponseResult<>(true, "成功");
    }

    @Override
    public ResponseResult<UserModel> updateById(UserModel model) throws Exception {
        mapper.updateById(model);
        return new ResponseResult<>(true, "成功");
    }

    @Override
    public ResponseResult<UserModel> getById(String uuid) throws Exception {
        UserModel one = mapper.getById(uuid);
        if (one != null) {
            return new ResponseResult<>(true, "成功", one);
        } else {
            return new ResponseResult<>(false, "未查询到记录");
        }
    }

    @Override
    public ResponseResult<PageInfo<UserModel>> findAll(int pageNow, int pageSize, UserModel model) throws Exception {
//        PageHelper.startPage(pageNow, pageSize, "wtsj");
        PageHelper.startPage(pageNow, pageSize);
        Page<UserModel> page = mapper.findAll(model);
        if (page.size() > 0) {
            PageInfo<UserModel> pageInfo = new PageInfo<>(page);
            return new ResponseResult<>(true, "成功", pageInfo);
        } else {
            return new ResponseResult<>(false, "未查询到记录");
        }
    }

    @Override
    public UserModel getByWeChatId(String weChatId) throws Exception{
        return mapper.getByWeChatId(weChatId);
    }
}
