package com.ld.intercity.business.apply.service.serviceImpl;

import com.ld.intercity.business.apply.mapper.ApplyMapper;
import com.ld.intercity.business.apply.model.ApplyModel;
import com.ld.intercity.business.apply.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public int save(ApplyModel applyModel) {
        return applyMapper.save(applyModel);
    }

    @Override
    public int del(String uuid) {
        return applyMapper.del(uuid);
    }

    @Override
    public int delByPassengerId(String passengerId) {
        return applyMapper.delByPassengerId(passengerId);
    }

    @Override
    public int update(ApplyModel applyModel) {
        return applyMapper.update(applyModel);
    }

    @Override
    public List<ApplyModel> findByPassengerId(String passengerId,String type) {
        return applyMapper.findByPassengerId(passengerId, type);
    }

    @Override
    public List<ApplyModel> findAllByType(String type) {
        return applyMapper.findAllByType(type);
    }
}
