package com.example.moneysystem.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

// NOT USED
// filter to add GUID to MDC response and request if not present
public class Slf4jMDCFilter extends OncePerRequestFilter {
    private final String header;

    public Slf4jMDCFilter(String header) {
        this.header = header;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader(header);
            if(StringUtils.isEmpty(token)) {
                token = UUID.randomUUID().toString().toUpperCase();
            }
            MDC.put(header, token);
            response.setHeader(header, token);
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(header);
        }
    }
}
