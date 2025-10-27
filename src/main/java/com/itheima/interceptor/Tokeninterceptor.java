package com.itheima.interceptor;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class Tokeninterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if (token == null || token.isEmpty()){
            log.info("请求头为空");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return  false;
        }

        try {
            JwtUtils.parseJWT(token);
        } catch (Exception e) {
            log.info("解析令牌失败");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return  false;
        }
        return true;

    }
}
