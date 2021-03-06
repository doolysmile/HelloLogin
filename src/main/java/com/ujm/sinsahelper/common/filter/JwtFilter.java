package com.ujm.sinsahelper.common.filter;

import com.ujm.sinsahelper.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    public static final String AUTHORIZATION = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //KCH : check1
//        String fullToken = request.getHeader(AUTHORIZATION);

        String fullToken = request.getHeader(AUTHORIZATION);

        System.out.println("\nfullToken = " + fullToken);
        if (StringUtils.hasText(fullToken)) {

            String token = fullToken;

            System.out.println("\ntoken = " + token);

            String userEmail = jwtUtil.parseJwtToken(token);

            System.out.println("userEmail = " + userEmail + ":::");

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, null);

            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authenticate);

        }

        filterChain.doFilter(request, response);
    }
}
