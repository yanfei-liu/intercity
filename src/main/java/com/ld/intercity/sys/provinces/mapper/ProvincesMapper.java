package com.ld.intercity.sys.provinces.mapper;

import com.ld.intercity.sys.provinces.model.ProvincesModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProvincesMapper {
    /**
     * 获取省
     * @return list
     */
    @Select("select * from sys_provinces where depth = 1")
    List<ProvincesModel> findProvince();

    /**
     * 获取市
     * @return list
     */
    @Select("select * from sys_provinces where parentId = #{p} and depth = 2")
    List<ProvincesModel> findCity(@Param("p") String parentId);

    /**
     * 获取区/县
     * @return list
     */
    @Select("select * from sys_provinces where parentId = #{p} and depth = 3")
    List<ProvincesModel> findCounty(@Param("p") String parentId);

    /**
     * 根据区/县Id查找
     * @return ProvincesModel
     */
    @Select("select * from sys_provinces where id = #{i}")
    ProvincesModel getByCountyId(@Param("i") String id);
}
