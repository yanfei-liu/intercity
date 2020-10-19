package com.ld.intercity.business.route.Mapper;

import com.ld.intercity.business.route.model.RouteModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RouteMapper {
    @Insert("insert into route_table values (uuid = #{r.uuid},province_one = #{r.provinceOne},city_one = #{r.cityOne},county_one = #{r.countyOne},region_one = #{r.regionOne}," +
            "province_two = #{r.provinceTwo},city_two = #{r.cityTwo},county_two = #{r.countyTwo},region_two = #{r.regionTwo},distance = #{r.distance}," +
            "company = #{r.company},price = #{r.price},money = #{r.money})")
    int save (@Param("r") RouteModel routeModel);
    @Update("update from route_table set del_flag = 1 where uuid = #{u}")
    int del(@Param("u") String uuid);
    @Update("update from route_table set province_one = #{r.provinceOne},city_one = #{r.cityOne},county_one = #{r.countyOne},region_one = #{r.regionOne}," +
            "province_two = #{r.provinceTwo},city_two = #{r.cityTwo},county_two = #{r.countyTwo},region_two = #{r.regionTwo},distance = #{r.distance}," +
            "company = #{r.company},price = #{r.price},money = #{r.money} where uuid = #{r.uuid}")
    int update(@Param("r") RouteModel routeModel);

    /**
     * 根据起始地点和目标地点查询路线
     * @param provinceOne 起始地省
     * @param cityOne 目标地市
     * @param countyOne 目标地区县
     * @param provinceTwo 目标地省
     * @param cityTwo 目标地市
     * @param countyTwo 目标地区县
     * @return  RouteModel
     */
    @Select("select uuid as uuid,province_one as provinceOne,city_one as cityOne,county_one as countyOne,region_one as regionOne," +
            "province_two as provinceTwo,city_two as cityTwo,county_two as countyTwo,region_two as regionTwo,distance as distance," +
            "company as company,price as price,money as money from route_table where province_one = #{provincOne} and city_one = " +
            "#{cityOne} and county_one = #{countyOne} and province_two = #{provinceTwo} and city_two = #{cityTwo} and county_two = #{countyTwo}")
    RouteModel getByRegionOneAndRegionTwo(
            @Param("provinceOne") String provinceOne,@Param("cityOne") String cityOne,@Param("countyOne") String countyOne,
            @Param("provinceTwo") String provinceTwo,@Param("cityTwo") String cityTwo,@Param("countyTwo") String countyTwo);

    /**
     * 查询所有delFlag删除标记为0的记录
     * @return
     */
    @Select("select uuid as uuid,province_one as provinceOne,city_one as cityOne,county_one as countyOne,region_one as regionOne," +
            "province_two as provinceTwo,city_two as cityTwo,county_two as countyTwo,region_two as regionTwo,distance as distance," +
            "company as company,price as price,money as money from route_table where del_flag = 0")
    List<RouteModel> findAll();
}
