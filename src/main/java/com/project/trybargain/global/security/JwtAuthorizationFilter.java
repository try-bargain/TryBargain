package com.project.trybargain.global.security;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/api/auth/login") ||
                request.getRequestURI().equals("/api/auth/**") ||
                request.getRequestURI().equals("/")) {
            log.debug("Pass Authorization : " + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtUtil.getJwtFromRequest(request);

        if(StringUtils.hasText(accessToken)) {

            accessToken = jwtUtil.subStringToken(accessToken);

            if(!jwtUtil.validateToken(accessToken)) {
                log.error("토큰정보가 일치하지 않습니다.");
                return;
            }

            Claims info = jwtUtil.getUerInfoFromToken(accessToken);

            try{
                setAuthentication(info.getSubject());
            }catch (Exception e) {
                log.error(e.toString());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
    }
}
