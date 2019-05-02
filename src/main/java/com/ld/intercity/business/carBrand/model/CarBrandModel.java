package com.ld.intercity.business.carBrand.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 */
@ApiModel("车辆品牌表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarBrandModel implements Serializable {
    @ApiModelProperty(value = "主键", example = "由后台自动生成", required = true)
    private String uuid;

    @ApiModelProperty(value = "品牌名称", example = "福特等", required = true)
    private String name;
}
