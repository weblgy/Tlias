package com.itheima.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@WebFilter("/*")
public class Demofilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    log.info("Demofilter初始化...");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Demofilter执行了...");
        filterChain.doFilter(servletRequest,servletResponse);
    }
    @Override
    public void destroy() {
    log.info("Demofilter销毁...");
    }
}
