package com.ld.intercity.sys.provinces.service;

import com.ld.intercity.sys.provinces.model.ProvincesModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProvincesService {
    /**
     * 获取省
     * @return list
     */
    List<ProvincesModel> findProvince();

    /**
     * 获取市
     * @return list
     */
    List<ProvincesModel> findCity(String parentId);

    /**
     * 获取区/县
     * @return list
     */
    List<ProvincesModel> findCounty(String parentId);

    /**
     * 根据区/县Id查找
     * @return ProvincesModel
     */
    ProvincesModel getByCountyId(String id);
}
