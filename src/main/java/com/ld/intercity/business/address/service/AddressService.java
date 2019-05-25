package com.ld.intercity.business.address.service;

import com.ld.intercity.business.address.model.AddressModel;

import java.util.List;

public interface AddressService {
    /**
     * 存
     * @param addressModel 实体类
     * @return int
     */
    int save(AddressModel addressModel);

    /**
     * 删——delflag更改为1
     * @param uuid 记录的主键
     * @return int
     */
    int del(String uuid);

    /**
     * 改
     * @param addressModel  实体类
     * @return int
     */
    int update(AddressModel addressModel);

    /**
     * 根据记录主键查找整条记录
     * @param uuid  记录主键
     * @return AddressModel
     */
    AddressModel getByUuid(String uuid);

    /**
     * 根据记录创建人主键查找全部对应的未删除记录
     * @param uuid 创建人主键
     * @return list
     */
    List<AddressModel> findByCreateBy(String uuid);
}
