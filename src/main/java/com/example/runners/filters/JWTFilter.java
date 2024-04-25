package com.example.runners.filters;

import com.example.runners.dto.RunnerUserDetails;
import com.example.runners.entity.User;
import com.example.runners.utils.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWT jwt;

    public JWTFilter(JWT jwt) {
        System.out.println("JWTFilter() in JWTFilter");
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("doFilterInternal() in JWTFilter");
        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");
        System.out.println("authorization:"+authorization);
        //Authorization 헤더 검증 Berer zxxxx
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token is null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        String token = authorization.split(" ")[1];
        System.out.println("token:"+token);
        //토큰 소멸 시간 검증
        if (jwt.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        System.out.println("소멸시간 패스");

        String username = jwt.getUsername(token);
        String role = jwt.getRole(token);
        System.out.println("username:"+username);
        System.out.println("role:"+role);
        User user = new User();
        user.setUsername(username);
        user.setPassword("temppassword");
        user.setRole(role);

        RunnerUserDetails customUserDetails = new RunnerUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        System.out.println("SecurityContextHolder.getContext().getAuthentication()"+SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request, response);
    }
}
