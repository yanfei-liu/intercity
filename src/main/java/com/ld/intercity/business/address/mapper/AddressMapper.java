package com.ld.intercity.business.address.mapper;

import com.ld.intercity.business.address.model.AddressModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressMapper {
    /**
     * 存
     * @param addressModel 实体类
     * @return int
     */
    @Insert("inset into address_table values (#{a.uuid},#{a.createBy},#{a.createTime},#{a.province},#{a.city},#{a.county}" +
            ",#{a.address},#{a.defaul},#{a.delFlag})")
    int save(@Param("a") AddressModel addressModel);

    /**
     * 删——delflag更改为1
     * @param uuid 记录的主键
     * @return int
     */
    @Update("update from address_table set del_flag = 1 where uuid = #{u}")
    int del(@Param("u") String uuid);

    /**
     * 改
     * @param addressModel  实体类
     * @return int
     */
    @Update("update from address_table set province = #{a.province},city = #{a.city},county = #{a.county},address = #{a.address}" +
            ",defaul = #{a.defaul},del_flag = #{a.delFlag}")
    int update(@Param("a") AddressModel addressModel);

    /**
     * 根据记录主键查找整条记录
     * @param uuid  记录主键
     * @return AddressModel
     */
    @Select("select uuid,create_by as createBy,create_time as createTime, province as province,city as city,county as county,address as address" +
            ",defaul as defaul,del_flag as delFlag from address_table where uuid = #{u}")
    AddressModel getByUuid(@Param("u") String uuid);

    /**
     * 根据记录创建人主键查找全部对应的未删除记录
     * @param uuid 创建人主键
     * @return list
     */
    @Select("select uuid,create_by as createBy,create_time as createTime, province as province,city as city,county as county,address as address" +
            ",defaul as defaul,del_flag as delFlag from address_table where create_by = #{u}")
    List<AddressModel> findByCreateBy(@Param("u") String uuid);
}
