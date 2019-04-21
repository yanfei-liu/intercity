package com.ld.intercity.sys.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    final static String secret = "ldtoken";

    //    生成token
    public static String creaToken(String account, String id, String orgid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return JWT.create().withHeader(map)
//                qian fa zhe
                .withClaim("iss", "ldtoken")
//                mian xiang de yong hu
                .withClaim("sub", account)
                .withClaim("subid", id)
                .withClaim("orgid", orgid)
//                jie shou gai jwt fang
                .withClaim("aud", "ldtoken")
                //签发时间
                .withIssuedAt(new Date(System.currentTimeMillis()))
//                过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 12)))
//                mi yao
                .sign(Algorithm.HMAC256(secret));
    }

    //    解密token
    public static Map<String, Claim> verifToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            return null;
        }
        return jwt.getClaims();
    }

    //    获取值
    public static Claim getApp(String token, String k) {
        Map<String, Claim> claimMap = verifToken(token);
        if (claimMap != null && claimMap.containsKey(k)) {
            return claimMap.get(k);
        } else {
            return null;
        }
    }

    public static String getAcc(HttpServletRequest request) {
        String lTokenD = request.getHeader("LTokenD");
        Map<String, Claim> map = JWTUtils.verifToken(lTokenD);
        if (map != null && map.containsKey("sub")) {
            return map.get("sub").asString();
        } else {
            return null;
        }
    }

    public static String getAccId(HttpServletRequest request) {
        String token = request.getHeader("LTokenD");
        if (token == null || token.trim().isEmpty())
            return null;
        Map<String, Claim> map = JWTUtils.verifToken(token);
        if (map == null || map.size() <= 0)
            return null;
        String uuid = map.get("subid").asString();
        if (uuid == null || uuid.isEmpty())
            return null;
        return uuid;
    }

    public static String getOrgid(HttpServletRequest request) {
        String token = request.getHeader("LTokenD");
        if (token == null || token.isEmpty())
            return null;
        Map<String, Claim> map = JWTUtils.verifToken(token);
        if (map == null || map.size() <= 0)
            return null;
        String uuid = map.get("orgid").asString();
        if (uuid == null || uuid.isEmpty())
            return null;
        return uuid;
    }
}
