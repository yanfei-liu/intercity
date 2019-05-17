package com.ld.intercity.sys.provinces.service.serviceImpl;

import com.ld.intercity.sys.provinces.mapper.ProvincesMapper;
import com.ld.intercity.sys.provinces.model.ProvincesModel;
import com.ld.intercity.sys.provinces.service.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProvincesServiceImpl implements ProvincesService {
    @Autowired
    private ProvincesMapper mapper;

    @Override
    public List<ProvincesModel> findProvince() {
        return mapper.findProvince();
    }

    @Override
    public List<ProvincesModel> findCity(String parentId) {
        return mapper.findCity(parentId);
    }

    @Override
    public List<ProvincesModel> findCounty(String parentId) {
        return mapper.findCounty(parentId);
    }

    @Override
    public ProvincesModel getByCountyId(String id) {
        return mapper.getByCountyId(id);
    }
}
