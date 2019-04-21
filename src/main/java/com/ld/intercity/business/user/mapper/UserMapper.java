package com.ld.intercity.business.user.mapper;

import com.github.pagehelper.Page;
import com.ld.intercity.business.user.mapper.sql.UserMapperSql;
import com.ld.intercity.business.user.model.UserModel;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;

/**
 * @Explain 用户主题
 * @Author Dong-Liu
 * @Date 2019/4/20 19:12
 **/
@Mapper
public interface UserMapper {

    String TABLE_NAME = " user_table ";

    @Insert({
            "insert into " + TABLE_NAME + " (uuid,we_chat_id,identity) values " +
                    " (#{model.uuid},#{model.weChatId},#{model.identity})"
    })
    void save(@Param("model") UserModel model) throws SQLException;

    @Delete({
            "delete " + TABLE_NAME + " where uuid = #{uuid}"
    })
    void deleteById(@Param("uuid") String uuid) throws SQLException;

    @UpdateProvider(type = UserMapperSql.class, method = "updateById")
    void updateById(@Param("model") UserModel model) throws SQLException;

    @Select({
            "select * from " + TABLE_NAME + " where uuid = #{uuid}"
    })
    UserModel getById(@Param("uuid") String uuid) throws SQLException;

    @SelectProvider(type = UserMapperSql.class, method = "findAll")
    Page<UserModel> findAll(@Param("model") UserModel model) throws SQLException;
}
