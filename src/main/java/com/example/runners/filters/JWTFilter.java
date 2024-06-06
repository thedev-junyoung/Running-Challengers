package com.example.runners.filters;

import com.example.runners.dto.user.RunnerUserDetails;
import com.example.runners.type.Role;
import com.example.runners.entity.User;
import com.example.runners.utils.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


// JWT 토큰을 검증하고, 토큰이 유효한 경우 사용자를 인증하는 필터
public class JWTFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    private final JWT jwt;

    public JWTFilter(JWT jwt) {
        this.jwt = jwt;
    }

    // 요청 헤더에서 JWT 토큰을 추출하고 검증 - 모든요청
    // 토큰이 유효하면 사용자 정보를 설정하고, SecurityContextHolder에 Authentication 객체를 설정
    // 필터 체인의 다음 필터를 호출
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");
        logger.info("authorization:"+authorization);
        //Authorization 헤더 검증 Berer zxxxx
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token is null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        String token = authorization.split(" ")[1];
        //토큰 소멸 시간 검증
        if (jwt.isExpired(token)) {

            logger.info("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        String username = jwt.getUsername(token);
        String role = jwt.getRole(token);
        logger.info("username:"+username);
        logger.info("role:"+role);
        User user = new User();
        user.setEmail(username);
        user.setPassword("temppassword");
        user.setRole(Role.valueOf(role));

        RunnerUserDetails customUserDetails = new RunnerUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        logger.info("SecurityContextHolder.getContext().getAuthentication()"+SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request, response);
    }
}
