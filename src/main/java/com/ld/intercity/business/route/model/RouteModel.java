package com.ld.intercity.business.route.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
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
    //路线地址：省1
    private String provinceOne;
    //路线地址：市1
    private String cityOne;
    //路线地址：区/县1
    private String countyOne;
    //路线地址：省2
    private String provinceTwo;
    //路线地址：市2
    private String cityTwo;
    //路线地址：区/县2
    private String countyTwo;
    //路线距离
    private String distance;
    //距离单位
    private String company;
    //单位距离单价
    private BigDecimal price;
    //总价格
    private BigDecimal money;
}
