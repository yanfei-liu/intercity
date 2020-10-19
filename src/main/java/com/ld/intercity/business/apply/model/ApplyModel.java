package com.ld.intercity.business.apply.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Explain
 * @Author Chen_xie_cang
 * @Date 2019/5/19 17:01
 **/
@ApiModel("申请升级司机表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyModel {
    @ApiModelProperty(value = "主键", example = "由后台自动生成", required = true)
    private String uuid;
    @ApiModelProperty(value = "申请人ID", example = "申请人ID", required = true)
    private String passengerId;
    @ApiModelProperty(value = "身份证号码",example = "18位身份证号码",required = true)
    private String idCardId;
    @ApiModelProperty(value = "申请人姓名",example = "某某某",required = true)
    private String driverName;
    @ApiModelProperty(value = "申请人电话",example = "11位电话号码",required = true)
    private String driverPhone;
    @ApiModelProperty(value = "身份证正面照",example = "图片路径",required = true)
    private String idCarFacePhoto;
    @ApiModelProperty(value = "身份证反面照",example = "图片路径",required = true)
    private String idCarBackPhoto;
    @ApiModelProperty(value = "手持身份证正面照",example = "图片路径",required = true)
    private String holdIdCarFacePhoto;
    @ApiModelProperty(value = "驾驶证正本照",example = "图片路径",required = true)
    private String driverLicenseOriginal;
    @ApiModelProperty(value = "驾驶证副本照",example = "图片路径",required = true)
    private String driverLicenseCopy;
    @ApiModelProperty(value = "车辆型号",example = "宝骏530",required = true)
    private String vehicleModel;
    @ApiModelProperty(value = "行驶证正本照",example = "图片路径",required = true)
    private String drivingLicenseOriginal;
    @ApiModelProperty(value = "行驶证副本照",example = "图片路径",required = true)
    private String drivingLicenseCopy;
    @ApiModelProperty(value = "车辆尾部照片",example = "图片路径",required = true)
    private String vehicleRearPhoto;
    @ApiModelProperty(value = "车辆正侧面45°照片",example = "图片路径",required = true)
    private String carFortyFivePhoto;
    @ApiModelProperty(value = "申请时间", example = "yyyy-MM-dd HH:mm:ss", required = true)
    private LocalDateTime createTime;
    @ApiModelProperty(value = "申请进度", example = "0 未进行操作   1：申请通过  2:申请未通过  3：申请已作废", required = true)
    private int progress;
}
