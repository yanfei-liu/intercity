package com.ld.intercity.business.apply.mapper;

import com.ld.intercity.business.apply.model.ApplyModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplyMapper {
    int save(ApplyModel applyModel);
    int del(String uuid);
    int delByPassengerId(String passengerId);
    int update(ApplyModel applyModel);
    List<ApplyModel> findByPassengerId(String passengerId, String type);
    List<ApplyModel> findAllByType(String type);
}
