package com.ld.intercity.business.apply.model;

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
 * @Date 2019/5/19 17:01
 **/
@ApiModel("申请升级司机表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyModel {
    //主键
    @ApiModelProperty(value = "主键", example = "由后台自动生成", required = true)
    private String uuid;
    //申请人Id
    @ApiModelProperty(value = "申请人ID", example = "申请人ID", required = true)
    private String passengerId;
    //申请资料类型(0：身份证ID 1：姓名 2：联系电话 3：身份证照片正面照 4：身份证照片反面照 5：手持身份证正面照 6：驾驶证正本照 7：驾驶证副本照
    // 8：车辆型号已经车身颜色 9：车辆生产日期 10：行驶证正本照片  11：行驶证副本照片 12：车辆尾部照片 13：车辆正面45°角度照片)
    @ApiModelProperty(value = "申请资料类型", example = "0：身份证ID 1：姓名 2：联系电话 3：身份证照片正面照 4：身份证照片反面照 5：手持身份证正面照 6：驾驶证正本照 7：驾驶证副本照\n" +
            "    // 8：车辆型号已经车身颜色 9：车辆生产日期 10：行驶证正本照片  11：行驶证副本照片 12：车辆尾部照片 13：车辆正面45°角度照片", required = true)
    private String applyType;
    //申请资料
    @ApiModelProperty(value = "申请资料", example = "申请资料", required = true)
    private String ApplicationMaterials;
    //申请时间
    @ApiModelProperty(value = "申请时间", example = "yyyy-MM-dd HH:mm:ss", required = true)
    private LocalDateTime createTime;
    //申请进度（0 未进行操作   1：申请通过  2:申请未通过  3：申请已作废）
    @ApiModelProperty(value = "申请进度", example = "0 未进行操作   1：申请通过  2:申请未通过  3：申请已作废", required = true)
    private int progress;
}
