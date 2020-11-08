package com.ld.intercity.business.route.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 路线基础类
 *
 */
@ApiModel("路线基本信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteModel {
    @ApiModelProperty(value = "主键",example = "uuid",required = true)
    private String uuid;
    @ApiModelProperty(value = "出发省",example = "113000",required = true)
    private String provinceOne;
    @ApiModelProperty(value = "出发市",example = "1130001",required = true)
    private String cityOne;
    @ApiModelProperty(value = "出发区县",example = "113000101",required = true)
    private String countyOne;
    @ApiModelProperty(value = "出发区域")
    private String regionOne;
    @ApiModelProperty(value = "目的地省",example = "113000",required = true)
    private String provinceTwo;
    @ApiModelProperty(value = "目的地市",example = "1130001",required = true)
    private String cityTwo;
    @ApiModelProperty(value = "目的地区县",example = "113000101",required = true)
        private String countyTwo;
    @ApiModelProperty(value = "目的地区域")
    private String regionTwo;
    @ApiModelProperty(value = "路线距离",example = "123",required = true)
    private String distance;
    @ApiModelProperty(value =  "距离单位")
    private String company;
    @ApiModelProperty(value = "一个人的价格")
    private BigDecimal oneUserPrice;
    @ApiModelProperty(value = "五座车包车价")
    private BigDecimal fiveSeatsPrice;
    @ApiModelProperty(value = "七座车包车价")
    private BigDecimal sevenSeatsPrice;
    @ApiModelProperty(value = "是否删除")
    private BigDecimal delFlag;
}
