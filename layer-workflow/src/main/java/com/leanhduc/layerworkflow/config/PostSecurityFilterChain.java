package com.leanhduc.layerworkflow.config;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PostSecurityFilterChain implements Filter {
    Logger logger = LoggerFactory.getLogger(PostSecurityFilterChain.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("FilterAfterSecurityFilterChain running");
        chain.doFilter(request, response);
    }
}
