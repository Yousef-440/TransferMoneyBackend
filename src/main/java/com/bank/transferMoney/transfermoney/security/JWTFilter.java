package com.bank.transferMoney.transfermoney.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JwtGenerator jwtGenerator;
    private final CustomUserDetailsService userDetails;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");

            if(StringUtils.hasText(header) && header.startsWith("Bearer ")){
                String token = header.substring(7);
                String username =jwtGenerator.extractUsername(token);

                if(username != null){
                    UserDetails userDetails1 =userDetails.loadUserByUsername(username);

                    if(jwtGenerator.validateToken(token, userDetails1)){
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails1,
                                        null,
                                        userDetails1.getAuthorities()
                                );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);                    }

                }
            }
        }catch (Exception ex){
            log.error("JWT Filter Error");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\n\t \"error\": \"Invalid or expired token\"\n}");
        }
        filterChain.doFilter(request, response);
    }
}
