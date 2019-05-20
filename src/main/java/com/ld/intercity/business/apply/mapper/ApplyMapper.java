package com.ld.intercity.business.apply.mapper;

import com.ld.intercity.business.apply.model.ApplyModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ApplyMapper {
    @Insert("insert into apply_table values (#{a.uuid},#{a.passengerId},#{a.applyType},@{a.applicationMaterials},#{a.createTime},#{a.progress})")
    int save(@Param("a") ApplyModel applyModel);
    @Delete("delete from apply_table where uuid = #{uuid}")
    int del(@Param("uuid") String uuid);
    @Delete("delete from apply_table where passenger_id = #{p}")
    int delByPassengerId(@Param("p") String passengerId);
    @Update("update apply_table set passenger_id = #{a.passengerId},apply_type=#{a.applyType},application_materials=@{a.applicationMaterials},create_time = #{a.createTime},progress = #{a.progress} where uuid = #{a.uuid}")
    int update(@Param("a") ApplyModel applyModel);
    @Select("select uuid as uuid,passenger_id as passengerId,apply_type as applyType,application_materials as applicationMaterials,create_time as createTime,progress as progress from apply_table where passenger_id = #{p} and type = #{t}")
    List<ApplyModel> findByPassengerId(@Param("p") String passengerId,@Param("t") String type);
    @Select("select uuid as uuid,passenger_id as passengerId,apply_type as applyType,application_materials as applicationMaterials,create_time as createTime,progress as progress from apply_table where type = #{t}")
    List<ApplyModel> findAllByType(@Param("t") String type);
}
