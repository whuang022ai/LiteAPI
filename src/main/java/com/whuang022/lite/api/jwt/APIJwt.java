
package com.whuang022.lite.api.jwt;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * APIJwt
 * 專門處理JWT簽發驗證
 *
 * @author whuang022
 */
public class APIJwt {
    private static final String SECRET = "******your pass word***************";
    private static final String EXP = "exp";
    private static final String PAYLOAD = "payload";

    /**
     * sign token
     *
     * @param object  傳入的加密對象 - 放入PAYLOAD
     * @param maxTime 過期事件,單位毫秒
     * @param <T>
     * @return String 返回加密後的JWT
     */
    public static <T> String sign(T object, long maxTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        String jsonString = JSON.toJSONString(object);
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        long exp = System.currentTimeMillis() + maxTime;
        String token = null;
        try {
            token = JWT.create()
                    .withHeader(map)//header
                    .withClaim(PAYLOAD, jsonString)
                    .withClaim(EXP, new DateTime(exp).toDate())//超時時間
                    .sign(Algorithm.HMAC256(SECRET));//密鑰
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * unsign token
     *
     * @param token jwt類型的token
     * @return String 返回解密後的JSON - 如果token過期返回空對象
     */
    public static String unsign(String token) {
        DecodedJWT decode = JWT.decode(token);
        Map<String, Claim> claims = decode.getClaims();
        if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
            long tokenTime = claims.get(EXP).asDate().getTime();
            long nowTime = new Date().getTime();
            // 判斷token是否超時
            if (tokenTime > nowTime) {
                String json = claims.get(PAYLOAD).asString();
                return json;
            }
        }
        return null;
    }
}