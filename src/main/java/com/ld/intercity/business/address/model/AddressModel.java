package com.ld.intercity.business.address.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Explain
 * @Author Chen_xie_cang
 * @Date 2019/5/25 19：44
 **/
@ApiModel("地址管理接口")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressModel {
    //主键
    @ApiModelProperty(value = "主键",example = "后天自动生成",required = true)
    private String uuid;
    //创建人
    private String createBy;
    //创建时间
    private LocalDateTime createTime;
    //地址-省
    private String province;
    //事
    private String city;
    //区/县
    private String county;
    //街道门牌号
    private String address;
    //默认常用地址
    private int defaul;
    //删除标记
    private int delFlag;
}
