package com.tw.ag.controller;

import com.tw.ag.model.EchoResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EchoController {
	Logger logger = LoggerFactory.getLogger(EchoController.class);

	public EchoController(@Autowired RequestMappingHandlerMapping requestMappingHandlerMapping) throws NoSuchMethodException {
		requestMappingHandlerMapping.registerMapping(
				RequestMappingInfo.paths("/**")
						.methods(RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE)
						.consumes(MediaType.ALL_VALUE)
						.produces(MediaType.APPLICATION_JSON_VALUE)
						.build(),
				this,
				EchoController.class.getMethod("echo", HttpServletRequest.class, Map.class, Map.class, Map.class));

	}

	public EchoResponse echo(HttpServletRequest httpServletRequest, @RequestHeader(required = false) Map<String, String> requestHeaders,
							 @RequestParam(required = false) Map<String, String> requestParams,
							 @RequestBody(required = false) Map<String, Object> requestBody) {
		logger.info("requested url {}", httpServletRequest.getRequestURI());
		logger.info("requested method {}", httpServletRequest.getMethod());
		logger.info("requested protocol {}", httpServletRequest.getProtocol());
		logger.info("calling host {}", httpServletRequest.getRemoteHost());
		logger.info("requestHeaders {}", requestHeaders);
		logger.info("requestParams {}", requestParams);
		logger.info("requestBody {}", requestBody);

		Map<String, Object> cookiesInMap = new HashMap<>();
		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies != null) for (Cookie cookie : cookies) cookiesInMap.put(cookie.getName(), cookie.getValue());

		return EchoResponse.builder()
				.url(httpServletRequest.getRequestURI())
				.httpMethod(httpServletRequest.getMethod())
				.protocol(httpServletRequest.getProtocol())
				.remoteHost(httpServletRequest.getRemoteHost())
				.requestHeaders(requestHeaders)
				.requestParams(requestParams)
				.requestBody(requestBody)
				.cookies(cookiesInMap)
				.build();
	}
}
