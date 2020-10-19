package com.ld.intercity.business.login.mapper;

import com.ld.intercity.business.login.entity.Login;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    @Select("select uuid,openId,account,password,sessionKey from login_table where openId = #{openId}")
    Login getByOpenId(@Param("openId") String openId);

    @Insert("insert into login_table (uuid,openId,account,password,sessionKey) values (#{l.uuid},#{l.openId},#{l.account},#{l.password},#{l.sessionKey})")
    int save(@Param("l") Login login);
}
