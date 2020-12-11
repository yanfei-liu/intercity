package com.ld.intercity.business.apply.service;

import com.ld.intercity.business.apply.model.ApplyModel;
import com.ld.intercity.utils.ResponseResult;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ApplyService {
    ResponseResult<String> save(ApplyModel applyModel);
    int del(String uuid);
    int delByPassengerId(String passengerId);
    int update(ApplyModel applyModel);
    List<ApplyModel> findByPassengerId(String passengerId);
    List<ApplyModel> findAllByType(String type);
    List<ApplyModel> findAllByParamBack(String val, String val1, String val2);
    ApplyModel getByUuid(String uuid) throws Exception;
}
