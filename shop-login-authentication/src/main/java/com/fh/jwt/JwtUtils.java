package com.fh.jwt;

import com.fh.utils.ResponseServer;
import com.fh.utils.ServerEnum;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiezhuang
 * @ClassName jwtUtils
 * @date 2020/6/28 18:23
 */
public class JwtUtils {

    /**
     * 生成token的方法
     * @param payLoad
     * @param securityKey
     * @return
     */
    public static Map<String,Object> createToken(Map<String,Object> payLoad,String securityKey){
        //头部信息
        Map<String,Object> headMap = new HashMap<>();
        headMap.put("alg","HS256");
        headMap.put("type","JWT");

        long time = System.currentTimeMillis();
        long expTime = time+3600000L;
        long refreshTime = time+2400000L;
        //生成jwt的token值
        String token = Jwts.builder()
                .setHeader(headMap)
                .setClaims(payLoad)
                .setExpiration(new Date(expTime))
                .signWith(SignatureAlgorithm.HS256,securityKey)
                .compact();
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("expTime",expTime);
        map.put("refreshTime",refreshTime);
        return  map;
    }

    /**
     * 解析token的方法
     * @param token
     * @param securityKey
     * @return
     */

    public static ResponseServer resolveToken(String token,String securityKey){
        try {
            Jwts.parser()
                    .setSigningKey(securityKey)
                    .parseClaimsJws(token)
                    .getBody();
            return ResponseServer.success();
        }catch (SignatureException e){
            return ResponseServer.error(ServerEnum.SECRET_ERROR);
        }catch (ExpiredJwtException e){
            return ResponseServer.error(ServerEnum.TOKEN_TIMEOUT);
        }catch (Exception e){
            return ResponseServer.error();
        }


    }

    /**
     * 获取token值的数据
     * @param token
     * @param securityKey
     * @return
     */
    public static Claims getUserInfo(String token,String securityKey){

        return Jwts.parser()
                .setSigningKey(securityKey)
                .parseClaimsJws(token)
                .getBody();
    }

}
