package com.tw.ag.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Component
public class EchoIntercepter implements HandlerInterceptor {
    Logger log = LoggerFactory.getLogger(EchoIntercepter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Map<String, String> context = new HashMap<>();
        String uuid = UUID.randomUUID().toString();
        context.put("uuid", uuid);
        context.put("method", request.getMethod());
        context.put("uri", request.getRequestURI());
        context.put("startTime", String.valueOf(System.currentTimeMillis()));
        ThreadContext.putAll(context);
        MDC.setContextMap(context);
        log.debug("transaction started for uuid {}", uuid);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        Map<String, String> context = MDC.getCopyOfContextMap();
        long responseTime = System.currentTimeMillis() - Long.valueOf(context.get("startTime"));
        String uuid = context.get("uuid");
        log.debug("transaction completed for uuid {}", uuid);
        log.info("ResponseCode={}|ResponseTime={}ms", response.getStatus(), responseTime);
        ThreadContext.clearMap();
        MDC.clear();
    }

}
