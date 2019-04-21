package com.ld.intercity.business.user.mapper.sql;

import com.ld.intercity.business.user.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Explain 用户主题信息 sql文件
 * @Author Dong-Liu
 * @Date 2019/4/20 19:25
 **/
public class UserMapperSql {

    public String updateById(@Param("model") UserModel model) {
        return new SQL() {
            {
                UPDATE("user_table");
                if (!model.getIdentity().isBlank()) {
                    SET("identity=#{model.identity}");
                }
                WHERE("uuid = #{model.uuid}");
            }
        }.toString();
    }

    public String findAll(@Param("model") UserModel model) {
        return new SQL() {
            {
                SELECT("*");
                FROM("user_table");
                if (!model.getIdentity().isBlank()) {
                    WHERE("identity=#{model.identity}");
                }
            }
        }.toString();
    }
}
