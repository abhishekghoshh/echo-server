package com.tw.ag.model;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
public class EchoResponse {
	private String url;
	private String httpMethod;
	private String protocol;
	private String remoteHost;
	private Map<String, String> requestHeaders;
	private Map<String, String> requestParams;
	private Map<String, Object> requestBody;
}
