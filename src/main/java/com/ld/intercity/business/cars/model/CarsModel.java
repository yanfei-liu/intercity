package com.ld.intercity.business.cars.model;

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
@ApiModel("车辆基本信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarsModel implements Serializable {

    @ApiModelProperty(value = "主键", example = "由后台自动生成", required = true)
    private String uuid;

    @ApiModelProperty(value = "用户id", example = "关联的用户id", required = true)
    private String carUserId;

    @ApiModelProperty(value = "车牌号前缀", example = "选择框;冀，晋，京;关联地区(region)", required = true)
    private String carRelgionId;

    @ApiModelProperty(value = "车牌号后缀", example = "A123MU,注意绿牌长度", required = true)
    private String licensePlateEnd;

    @ApiModelProperty(value = "车辆颜色", example = "红色，珍珠白，黑色", required = true)
    private String carColour;

    @ApiModelProperty(value = "车辆品牌", example = "福特，凯迪拉克，奥迪；选择框，关联车辆品牌；", required = true)
    private String carBrandId;

    @ApiModelProperty(value = "车辆型号", example = "xt4，福克斯，A6；选择框；关联车辆型号；", required = true)
    private String carTypeId;

    @ApiModelProperty(value = "车辆准乘数量", example = "5，7；选择框，将具体值写入选项；", required = true)
    private String carSeatSize;

    @ApiModelProperty(value = "车辆类型", example = "suv,huv,mpv；选择框，将具体值写入选项；", required = true)
    private String carClassificationId;

    @ApiModelProperty(value = "车辆gps编号", example = "车辆gps编号，保留", required = true)
    private String carGPS;

    @ApiModelProperty(value = "车辆摄像头编号", example = "车辆摄像头编号，保留", required = true)
    private String carCamera;
}
