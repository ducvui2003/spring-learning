package com.leanhduc.springsecurityjwt.filter;

import com.leanhduc.springsecurityjwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    Logger logger = Logger.getLogger(JwtAuthFilter.class.getName());
    private final JwtUtil accessTokenJwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(@Qualifier("accessTokenUtil") JwtUtil accessTokenJwtUtil, UserDetailsService userDetailsService) {
        this.accessTokenJwtUtil = accessTokenJwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("JwtAuthFilter doFilterInternal");
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.info("JwtAuthFilter: no bearer token");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = this.accessTokenJwtUtil.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (this.accessTokenJwtUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set authentication details from the request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // @PreAuthorize will use this value to check permission and role
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else
                logger.warning("JwtAuthFilter: token nor valid");
        }

        filterChain.doFilter(request, response);
    }
}
