package com.ld.intercity.business.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Explain
 * @Author Dong-Liu
 * @Date 2019/4/20 19:01
 **/
@ApiModel("用户表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel implements Serializable {

    @ApiModelProperty(value = "主键", example = "由后台自动生成", required = true)
    private String uuid;

    @ApiModelProperty(value = "微信标识", example = "由微信获取", required = true)
    private String weChatId;

    @ApiModelProperty(value = "身份类型", example = "系统标记 1:客户 2:司机,新关注用户默认1", required = true)
    private String identity;

    @ApiModelProperty(value = "手机号", example = "手机号码,当identity为2时必填")
    private String phone;

    @ApiModelProperty(value = "身份证号", example = "身份证号,当identity为2时必填")
    private String userId;
}
