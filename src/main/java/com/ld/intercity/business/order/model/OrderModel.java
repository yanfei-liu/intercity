package com.ld.intercity.business.order.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @ApiModelProperty(value = "订单编号", example = "由后台自动生成,生成时间的long类型数值", required = true)
    private String orderSn;
    //出发地
    @ApiModelProperty(value = "出发地", example = "省-市-区/县", required = true)
    private String placeOfDeparture;
    //目的地
    @ApiModelProperty(value = "目的地", example = "省-市-区/县", required = true)
    private String destination;
    //出发时间
    @ApiModelProperty(value = "出发时间", example = "dateTime", required = true)
    private Date departureTime;
    //乘坐人数
    @ApiModelProperty(value = "乘坐人数", example = "数值", required = true)
    private String presionNumber;
    //订单金额
    @ApiModelProperty(value = "订单金额", example = "数值", required = true)
    private String orderAmount;
    //下单时间
    @ApiModelProperty(value = "下单时间", example = "dateTime", required = true)
    private Date orderTime;
    //订单状态
    @ApiModelProperty(value = "订单状态", example = "0-已下单未接单 1-未开始已接单  2-已开始未结算 3-已结束已结算   4-已取消", required = true)
    private String orderType;
    //结算时间
    @ApiModelProperty(value = "结算时间", example = "dateTime", required = true)
    private Date settingTime;
    //结算人
    @ApiModelProperty(value = "结算人", example = "结算用户主键", required = true)
    private String settingPresion;

}
