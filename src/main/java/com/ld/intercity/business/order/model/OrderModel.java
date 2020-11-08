package com.ld.intercity.business.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 订单基础类
 *
 */
@ApiModel("订单基本信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderModel {
    @ApiModelProperty(value = "主键",example = "后台自动生成",required = true)
    private String uuid;
    @ApiModelProperty(value = "订单编号", example = "由后台自动生成,生成时间的long类型数值", required = true)
    private String orderSn;
    @ApiModelProperty(value = "创建人", example = "创建人Uuid", required = true)
    private String createPresion;
    @ApiModelProperty(value = "创建人称谓", example = "陈先生", required = true)
    private String createName;
    @ApiModelProperty(value = "创建人电话")
    private String phone;
    @ApiModelProperty(value = "上车地点坐标", example = "坐标", required = true)
    private  String startCoordinate;
    @ApiModelProperty(value = "上车地点", example = "地点", required = true)
    private String startAddress;
    @ApiModelProperty(value = "下车地点坐标", example = "坐标", required = true)
    private String endCoordinate;
    @ApiModelProperty(value = "下车地点", example = "地点", required = true)
    private String endAddress;
    @ApiModelProperty(value = "出发省",required = true)
    private String startProvince;
    @ApiModelProperty(value = "出发市",required = true)
    private String startCity;
    @ApiModelProperty(value = "出发区县",required = true)
    private String startCounty;
    @ApiModelProperty(value = "目的省",required = true)
    private String endProvince;
    @ApiModelProperty(value = "目的市",required = true)
    private String endCity;
    @ApiModelProperty(value = "目的区县",required = true)
    private String endCounty;
    @ApiModelProperty(value = "路线表id",required = true)
    private String routeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "出发时间", example = "dateTime", required = true)
    private Date departureTime;
    @ApiModelProperty(value = "乘坐人数", example = "数值", required = true)
    private String presionNumber;
    @ApiModelProperty(value = "是否包车",example = "0:不包车，1:包车")
    private String isCharterCar;
    @ApiModelProperty(value = "包车车型",example = "1:五座，2:七座")
    private String charterCarType;
    @ApiModelProperty(value = "订单金额", example = "数值", required = true)
    private String orderAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下单时间", example = "dateTime", required = true)
    private Date orderTime;
    @ApiModelProperty(value = "接单人", example = "dateTime", required = true)
    private String jieDanPresion;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "接单时间", example = "dateTime", required = true)
    private Date jieDanTime;
    @ApiModelProperty(value = "订单状态", example = "0-已下单未接单 1-已接单未开始  2-已开始未结算  3-已结束未结算 4-已结束已结算  5-已取消", required = true)
    private String orderType;
    @ApiModelProperty(value = "下车地点坐标",example = "坐标")
    private String outCarCoordinate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下车时间",example = "2020-9-26 20:08:10")
    private Date outCarTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结算时间", example = "dateTime", required = true)
    private Date settingTime;
    @ApiModelProperty(value = "结算人", example = "结算用户主键", required = true)
    private String settingPresion;
    @ApiModelProperty(value = "是否删除",example = "0正常，1已删除",required = true)
    private String delFlag;
}
