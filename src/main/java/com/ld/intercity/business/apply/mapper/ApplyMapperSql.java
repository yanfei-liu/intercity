package com.ld.intercity.business.apply.mapper;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ApplyMapperSql {

    public String findAllByParamBack(@Param("val") String val, @Param("val1") String val1, @Param("val2") String val2){
        return new SQL(){{
            SELECT("uuid as uuid,passenger_id as passengerId,id_card_id as idCardId," +
                    "driver_name as driverName,driver_phone as driverPhone,create_time as createTime," +
                    "progress as progress");
            FROM("apply_table");
            String sql = "1=1 ";
            if (StringUtils.isNotBlank(val)) {
                sql += "and id_card_id=#{val}";
            }
            if (StringUtils.isNotBlank(val1)){
                sql += "and driver_phone = #{val1}";
            }
            if (StringUtils.isNotBlank(val2)){
                sql += "and progress = #{val2} ";
            }
            WHERE(sql);
        }}.toString();
    }
}
