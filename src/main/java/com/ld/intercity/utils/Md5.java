package com.ld.intercity.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author LD
 */
public class Md5 {

    public static void main(String[] args) {
        String md5Password = DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8));
        //e10adc3949ba59abbe56e057f20f883e
        System.out.println(md5Password);
    }
}
