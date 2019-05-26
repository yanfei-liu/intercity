package com.ld.intercity.business.order.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    //创建人
    @ApiModelProperty(value = "创建人", example = "创建人Uuid", required = true)
    private String createPresion;
    //创建人称谓
    @ApiModelProperty(value = "创建人称谓", example = "陈先生", required = true)
    private String createName;
    //上车地点
    @ApiModelProperty(value = "上车地点", example = "地址主键", required = true)
    private  String boardingLocation;
    //下车地点
    @ApiModelProperty(value = "下车地点", example = "地址主键", required = true)
    private String DropOffArea;
    //出发地
    @ApiModelProperty(value = "出发地点", example = "路线主键", required = true)
    private String placeOfDeparture;
    //目的地
    @ApiModelProperty(value = "目标地点", example = "路线主键", required = true)
    private String destination;
    //出发时间
    @ApiModelProperty(value = "出发时间", example = "dateTime", required = true)
    private LocalDateTime departureTime;
    //乘坐人数
    @ApiModelProperty(value = "乘坐人数", example = "数值", required = true)
    private String presionNumber;
    //订单金额
    @ApiModelProperty(value = "订单金额", example = "数值", required = true)
    private String orderAmount;
    //下单时间
    @ApiModelProperty(value = "下单时间", example = "dateTime", required = true)
    private LocalDateTime orderTime;
    //接单人
    @ApiModelProperty(value = "接单人", example = "dateTime", required = true)
    private String jieDanPresion;
    //接单时间
    @ApiModelProperty(value = "接单时间", example = "dateTime", required = true)
    private LocalDateTime jieDanTime;
    //订单状态
    @ApiModelProperty(value = "订单状态", example = "0-已下单未接单 1-未开始已接单  2-已开始未结算  3-已结束未结算 4-已结束已结算  5-已取消", required = true)
    private String orderType;
    //结算时间
    @ApiModelProperty(value = "结算时间", example = "dateTime", required = true)
    private LocalDateTime settingTime;
    //结算人
    @ApiModelProperty(value = "结算人", example = "结算用户主键", required = true)
    private String settingPresion;

}
