package com.ld.intercity.business.order.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@ApiModel("取消订单记录表")
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCancelModel {
    private String uuid;
    private String orderSn;
    private String cancelUserId;
    private LocalDateTime cancelTime;
    private String delFlag;
}
