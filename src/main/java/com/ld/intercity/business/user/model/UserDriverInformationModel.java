package com.ld.intercity.business.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel("司机详细信息表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDriverInformationModel implements Serializable {

    @ApiModelProperty(value = "主键", example = "由后台自动生成")
    private String uuid;

    @ApiModelProperty(value = "身份证图片正面", example = "上传后的图片名称")
    private String userIdPictureJust;

    @ApiModelProperty(value = "身份证图片背面", example = "上传后的图片名称")
    private String userIdPictureBack;

    @ApiModelProperty(value = "驾驶证正面", example = "上传后的图片名称")
    private String driverPictureJust;

    @ApiModelProperty(value = "驾驶证背面", example = "上传后的图片名称")
    private String driverPictureBack;

    @ApiModelProperty(value = "驾驶证有效期", example = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date driverEndDate;
}
