package com.leanhduc.layerworkflow.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class OncePerReqLogging extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(OncePerReqLogging.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info("OncePerRequest: Processing request running: {}", request.getRequestURI());

        filterChain.doFilter(request, response);

        logger.info("OncePerRequest: Processing response running: {}", response.getStatus());
    }
}
