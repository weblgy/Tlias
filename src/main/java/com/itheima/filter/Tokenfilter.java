package com.itheima.filter;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
//@WebFilter("/*")
public class Tokenfilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        log.info("拦截到请求：{}", requestURI);
        if (requestURI.contains("login")) {
            log.info("本次请求不需要处理");
            filterChain.doFilter(request, response);
            return;
        }


        String token = request.getHeader("token");
        if (token == null || token.isEmpty()){
            log.info("请求头为空");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            JwtUtils.parseJWT(token);
        } catch (Exception e) {
            log.info("解析令牌失败");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
