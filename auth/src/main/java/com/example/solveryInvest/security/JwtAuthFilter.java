package com.example.solveryInvest.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;
    private static final String AUTH = "Authorization";
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(
            //we can intercept every request and get data from it
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            //contains list of other filters that we need to execute
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTH);
        final String jwt;
        final String email;
        if (Objects.isNull(authHeader) || !authHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(BEARER.length());
        email = jwtService.getEmail(jwt);
        if (Objects.nonNull(email) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        jwt,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
