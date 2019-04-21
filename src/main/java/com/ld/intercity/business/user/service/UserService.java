package com.ld.intercity.business.user.service;

import com.github.pagehelper.PageInfo;
import com.ld.intercity.business.user.model.UserModel;
import com.ld.intercity.utils.ResponseResult;

/**
 * @Explain
 * @Author Dong-Liu
 * @Date 2019/4/20 19:51
 **/
public interface UserService {
    /**
     * @return com.ld.intercity.utils.ResponseResult<com.ld.intercity.business.user.model.UserModel>
     * @Author Dong-Liu
     * @Date 19:57 2019/4/20
     * @Param [model]
     **/
    ResponseResult<UserModel> save(UserModel model) throws Exception;

    /**
     * @return com.ld.intercity.utils.ResponseResult<com.ld.intercity.business.user.model.UserModel>
     * @Author Dong-Liu
     * @Date 19:58 2019/4/20
     * @Param [uuid]
     **/
    ResponseResult<UserModel> deleteById(String uuid) throws Exception;

    /**
     * @return com.ld.intercity.utils.ResponseResult<com.ld.intercity.business.user.model.UserModel>
     * @Author Dong-Liu
     * @Date 19:58 2019/4/20
     * @Param [model]
     **/
    ResponseResult<UserModel> updateById(UserModel model) throws Exception;

    /**
     * @return com.ld.intercity.utils.ResponseResult<com.ld.intercity.business.user.model.UserModel>
     * @Author Dong-Liu
     * @Date 19:58 2019/4/20
     * @Param [uuid]
     **/
    ResponseResult<UserModel> getById(String uuid) throws Exception;

    /**
     * @return com.ld.intercity.utils.ResponseResult<com.github.pagehelper.PageInfo < com.ld.intercity.business.user.model.UserModel>>
     * @Author Dong-Liu
     * @Date 20:03 2019/4/20
     * @Param [pageNow, pageSize, model]
     **/
    ResponseResult<PageInfo<UserModel>> findAll(int pageNow, int pageSize, UserModel model) throws Exception;
}
