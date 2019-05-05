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
    @ApiModelProperty(value = "出发地", example = "由后台自动生成,生成时间的long类型数值", required = true)
    private String placeOfDeparture;
    //目的地
    @ApiModelProperty(value = "目的地", example = "由后台自动生成,生成时间的long类型数值", required = true)
    private String destination;
    //出发时间
    @ApiModelProperty(value = "出发时间", example = "由后台自动生成,生成时间的long类型数值", required = true)
    private Date departureTime;
    //乘坐人数
    @ApiModelProperty(value = "乘坐人数", example = "由后台自动生成,生成时间的long类型数值", required = true)
    private String presionNumber;
    //订单金额
    @ApiModelProperty(value = "订单金额", example = "由后台自动生成,生成时间的long类型数值", required = true)
    private String orderAmount;
    //下单时间
    @ApiModelProperty(value = "下单时间", example = "由后台自动生成,生成时间的long类型数值", required = true)
    private Date orderTime;
    //订单状态
    @ApiModelProperty(value = "订单状态", example = "由后台自动生成,生成时间的long类型数值", required = true)
    private String orderType;
    //结算时间
    @ApiModelProperty(value = "结算时间", example = "由后台自动生成,生成时间的long类型数值", required = true)
    private Date settingTime;
    //结算人
    @ApiModelProperty(value = "结算人", example = "由后台自动生成,生成时间的long类型数值", required = true)
    private String settingPresion;

}
