package com.elsprage.words.config.security;

import com.elsprage.words.service.JwtService;
import com.elsprage.words.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isHeaderRequestValid(authenticationHeader)) {
            final String jwt = TokenUtils.getTokenFromHeader(authenticationHeader);
            if (jwtService.isTokenValid(jwt)) {
                final String userName = jwtService.extractUsername(jwt);
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    addUserToAuthContext(userName, request);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isHeaderRequestValid(final String authenticationHeader) {
        return authenticationHeader != null && authenticationHeader.startsWith("Bearer ");
    }

    private void addUserToAuthContext(final String userName, final HttpServletRequest request) {
        UserDetails userDetails = new User(userName, "", List.of());
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
