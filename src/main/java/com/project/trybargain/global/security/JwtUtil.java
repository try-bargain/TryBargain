package com.project.trybargain.global.security;

import com.project.trybargain.domain.user.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    /**토큰 해더 키값 **/
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**사용자 권한 값의 키 **/
    public static final String AUTHORIZATION_KEY = "auth";

    /**토큰 식별자 **/
    public static final String BEARER_PREFIX = "Bearer ";

    private final long TOKEN_TIME = 60 * 60 * 1000L * 24; //60분

    @Value("${jwt.secret.key}")
    private String secretKey;

    /**암호화 키**/
    private Key key;

    /**암호화 알고리즘**/
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }


    /**
     * 토큰생성
     * @param userId
     * @param role
     * @return
     */
    public String createAccessToKen(String userId, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(userId) // 사용자 식별아이디
                        .claim(AUTHORIZATION_KEY, role) //유저 권한정보
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) //만료시간
                        .setIssuedAt(date) //발급일
                        .signWith(key, signatureAlgorithm)  // 암호화 알고리즘
                        .compact();  //끝
    }

    /**
     * 쿠키에 jwt 저장
     * @param token
     * @param response
     */
    public void addJwtToCookie(String token, HttpServletResponse response) {
        try{
            token = URLEncoder.encode(token, "UTF-8").replaceAll("\\+", "%20");
            Cookie accessCookie = new Cookie(AUTHORIZATION_HEADER, token);
            accessCookie.setPath("/");
            response.addCookie(accessCookie);
        }catch (UnsupportedEncodingException e) {
            log.error(e.toString());
        }
    }

    /**
     * 토큰 자르기
     * @param tokenValue
     * @return
     */
    public String subStringToken(String tokenValue) {
        if(StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }

        throw new NullPointerException("유효하지 않은 토큰입니다.");
    }


    /**
     * 토큰 유효성 검증
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    /**
     * 토큰 사용자 정보 가져오기
     * @param token
     * @return
     */
    public Claims getUerInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * 쿠키에서 토큰값 가져오기
     * @param request
     * @return
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        log.error(e.toString());
                        return null;
                    }
                }
            }
        }

        return null;
    }







}
