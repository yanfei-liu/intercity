package com.ld.intercity.utils.aop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author: LD
 * @date:
 * @description:
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogModel implements Serializable {
    private String uuid;
    /*请求人id*/
    private String requestAccId;
    /*请求时间*/
    private String requestTime;
    /*请求方IP*/
    private String requestIP;
    /*请求URL*/
    private String requestURL;
    /*请求method*/
    private String requestMethod;
    /*请求参数*/
    private String requestParm;
    /*class_method*/
    private String classMethod;
    /*返回结果*/
    private String responeStr;
}
