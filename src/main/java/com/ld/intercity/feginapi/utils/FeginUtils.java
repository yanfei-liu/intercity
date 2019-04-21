package com.ld.intercity.feginapi.utils;

import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class FeginUtils {
    public final static String URL = "http://192.168.1.12";

    public Feign.Builder object = Feign.builder()
            .encoder(new JacksonEncoder())
            .decoder(new JacksonDecoder())
            .options(new Request.Options(1000, 3500))
            .retryer(new Retryer.Default(5000, 5000, 3));
//            .target(Object.class, "http://192.168.1.12:8080");
}