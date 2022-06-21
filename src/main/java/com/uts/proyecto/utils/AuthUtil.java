/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author otalo
 */
public class AuthUtil {

    public static final String SECRET_KEY = "jose123";

    public static String generateJwtWithUsername(String username) {

        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, AuthUtil.SECRET_KEY).compact();
    }

    public static String getUsernameFromJwt(String token) {

        return Jwts.parser().setSigningKey(AuthUtil.SECRET_KEY)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public static String generateSha512(String data) {
        return DigestUtils.sha512Hex(data);
    }

    public static String generateMd5(String data) {
        return DigestUtils.md5Hex(data);
    }
}
