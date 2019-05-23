package com.ld.intercity.business.apply.service;

import com.ld.intercity.business.apply.model.ApplyModel;

import java.util.List;

public interface ApplyService {
    int save(ApplyModel applyModel);
    int del(String uuid);
    int delByPassengerId(String passengerId);
    int update(ApplyModel applyModel);
    List<ApplyModel> findByPassengerId(String passengerId,String type);
    List<ApplyModel> findAllByType(String type);
}
