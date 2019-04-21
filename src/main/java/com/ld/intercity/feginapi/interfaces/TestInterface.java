package com.ld.intercity.feginapi.interfaces;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * @author: LD
 * @date:
 * @description:
 */
public interface TestInterface {

    //    @Headers({"Content-Type: application/json","Accept: application/json"})
//    @RequestLine("POST api/query/QUERY.asp")
//    Object getOwner(@Param("time") String time,
//                    @Param("sign") String sign,
//                    @Param("stuno") String stuno,
//                    @Param("stuname") String stuname);

    @RequestLine("POST api/query/QUERY.asp")
    Object getOwner(String time,
                    String sign,
                    String stuno,
                    String stuname);

    @RequestLine("POST test.asp")
    Object test();

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @RequestLine("POST /login/login")
    Object tst(@Param("account") String account,
               @Param("password") String password);

}
