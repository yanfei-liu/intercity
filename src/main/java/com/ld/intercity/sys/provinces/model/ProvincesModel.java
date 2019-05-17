package com.ld.intercity.sys.provinces.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 省市区县基础类
 *
 */
@ApiModel("省市区县基础类")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProvincesModel {
    private int id;
    private String cityName;
    private int parentId;
    private String shortName;
    private int depth;
    private String cityCode;
    private String zipCode;
    private String mergerName;
    private String longitude;
    private String latitude;
    private String pinyin;
    private int isUse;
}
