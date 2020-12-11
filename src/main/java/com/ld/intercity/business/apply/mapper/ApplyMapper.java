package com.ld.intercity.business.apply.mapper;

import com.ld.intercity.business.apply.model.ApplyModel;
import com.ld.intercity.business.order.dao.sql.OrderMapperSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ApplyMapper {
    @Insert("insert into apply_table values (" +
            "#{a.uuid},#{a.passengerId},#{a.idCardId},#{a.driverName},#{a.driverPhone},#{a.idCarFacePhoto},#{a.idCarBackPhoto}" +
            ",#{a.holdIdCarFacePhoto},#{a.driverLicenseOriginal},#{a.driverLicenseCopy},#{a.vehicleModel},#{a.drivingLicenseOriginal}" +
            ",#{a.drivingLicenseCopy},#{a.vehicleRearPhoto},#{a.carFortyFivePhoto},#{a.createTime},#{a.progress})")
    int save(@Param("a") ApplyModel applyModel);
    @Delete("delete from apply_table where uuid = #{uuid}")
    int del(@Param("uuid") String uuid);
    @Delete("delete from apply_table where passenger_id = #{p}")
    int delByPassengerId(@Param("p") String passengerId);
    @Update("update apply_table set progress = #{a.progress} where uuid = #{a.uuid}")
    int update(@Param("a") ApplyModel applyModel);
    @Select("select uuid as uuid,passenger_id as passengerId,id_card_id as idCardId,driver_name as driverName," +
            "driver_phone as driverPhone,id_car_face_photo as idCarFacePhoto,id_car_back_photo as idCarBackPhoto," +
            "hold_id_car_face_photo as holdIdCarFacePhoto,driver_license_original as driverLicenseOriginal," +
            "vehicle_model as vehicleModel,driving_license_original as drivingLicenseOriginal,driver_license_copy as driverLicenseCopy," +
            "vehicle_rear_photo as vehicleRearPhoto,car_forty_five_photo as carFortyFivePhoto,create_time as createTime,progress as progress " +
            "from apply_table where passenger_id = #{p}")
    List<ApplyModel> findByPassengerId(@Param("p") String passengerId);
    @Select("select uuid as uuid,passenger_id as passengerId,id_card_id as idCardId,driver_name as driverName," +
            "driver_phone as driverPhone,id_car_face_photo as idCarFacePhoto,id_car_back_photo as idCarBackPhoto," +
            "hold_id_car_face_photo as holdIdCarFacePhoto,driver_license_original as driverLicenseOriginal," +
            "vehicle_model as vehicleModel,driving_license_original as drivingLicenseOriginal,driver_license_copy as driverLicenseCopy," +
            "vehicle_rear_photo as vehicleRearPhoto,car_forty_five_photo as carFortyFivePhoto,create_time as createTime,progress as progress " +
            "from apply_table where progress = #{t}")
    List<ApplyModel> findAllByType(@Param("t") String type);

    @SelectProvider(type = ApplyMapperSql.class,method = "findAllByParamBack")
    List<ApplyModel> findAllByParamBack(@Param("val") String val,@Param("val1") String val1,@Param("val2") String val2);

    @Select("select uuid as uuid,passenger_id as passengerId,id_card_id as idCardId,driver_name as driverName," +
            "driver_phone as driverPhone,id_car_face_photo as idCarFacePhoto,id_car_back_photo as idCarBackPhoto," +
            "hold_id_car_face_photo as holdIdCarFacePhoto,driver_license_original as driverLicenseOriginal," +
            "vehicle_model as vehicleModel,driving_license_original as drivingLicenseOriginal,driver_license_copy as driverLicenseCopy," +
            "vehicle_rear_photo as vehicleRearPhoto,car_forty_five_photo as carFortyFivePhoto,create_time as createTime,progress as progress " +
            " from apply_table where uuid = #{uuid}")
    ApplyModel getByUuid(String uuid);
}
