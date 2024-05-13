package com.example.moneysystem.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

// better to use interceptor SOLID
// interceptor to add GUID to MDC response and request if not present
public class Slf4jMDCInterceptor implements HandlerInterceptor {

    private final String header;

    public Slf4jMDCInterceptor(String header) {
        this.header = header;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(header);
        if(StringUtils.isEmpty(token)) {
            token = UUID.randomUUID().toString().toUpperCase();
        }
        response.setHeader(header, token);
        MDC.put(header, token);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        MDC.remove(header);
    }

}
