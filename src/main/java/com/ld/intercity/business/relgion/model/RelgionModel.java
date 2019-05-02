package com.ld.intercity.business.relgion.model;

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
@ApiModel("车牌地区")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelgionModel implements Serializable {
    @ApiModelProperty(value = "主键", example = "由后台自动生成", required = true)
    private String uuid;

    @ApiModelProperty(value = "地区缩写名称", example = "京，津等", required = true)
    private String name;
}
