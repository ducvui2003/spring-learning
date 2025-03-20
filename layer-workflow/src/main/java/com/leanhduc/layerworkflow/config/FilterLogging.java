package com.leanhduc.layerworkflow.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FilterLogging implements Filter {
    Logger logger = LoggerFactory.getLogger(FilterLogging.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("Filter: Incoming request - {}", req.getRequestURI());

        chain.doFilter(request, response); // Continue request

        HttpServletResponse resp = (HttpServletResponse) response;
        logger.info("Filter: Outgoing response - Status: " + resp.getStatus());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
