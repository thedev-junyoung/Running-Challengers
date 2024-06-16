package com.example.runners.filters;

import com.example.runners.dto.user.RunnerUserDetails;
import com.example.runners.utils.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private final AuthenticationManager authenticationManager;
    private final JWT jwt;

    public LoginFilter(AuthenticationManager authenticationManager, JWT jwt){
        this.authenticationManager = authenticationManager;
        this.jwt = jwt;
    }

    // 로그인 시 호출.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("attemptAuthentication() in LoginFilter"+obtainEmail(request));
        logger.info("ㅇㅈ배ㅗㅕ옵ㅈ오ㅜㅈ바유ㅑㅕ");
        // 요청으로 부터 이메일, 패스워드 추출
        String email = obtainEmail(request);
        String password = obtainPassword(request);

        System.out.println(email + " login!");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
        System.out.println("authToken:"+authToken);
        return authenticationManager.authenticate(authToken); // 인증시도
    }

    private String obtainEmail(HttpServletRequest request) {
        return request.getParameter("email");
    }

    // attemptAuthentication 인증 성공하면 호출
    // 1. 인증된 사용자 정보(RunnerUserDetails)를 가져와 JWT 토큰 생성
    // 2. 생성된 JWT 토큰을 response 헤더에 추가.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        System.out.println("successfulAuthentication() in LoginFilter");

        // create JWT token with user info
        RunnerUserDetails customUserDetails = (RunnerUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername(); //geUsername 메서드는 오버라이드메서드
        System.out.println("username:"+username);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();


        String token = jwt.createJwt(username, role, 60*60*1000L);
        System.out.println("JWT token 생성 :"+token);
        response.addHeader("Authorization", "Bearer " + token);
    }

    // 1. 인증이 실패하면 호출됩니다.
    // 2. 응답 상태를 401(Unauthorized)로 설정합니다.
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("unsuccessfulAuthentication() in LoginFilter");
        response.setStatus(401);
    }


}
