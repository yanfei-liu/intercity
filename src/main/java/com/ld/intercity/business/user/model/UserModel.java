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
@ApiModel("账号表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel implements Serializable {

    @ApiModelProperty(value = "主键", example = "由后台自动生成")
    private String uuid;

    @ApiModelProperty(value = "微信标识", example = "由微信获取")
    private String weChatId;

    @ApiModelProperty(value = "身份类型", example = "系统标记")
    private String identity;
}
