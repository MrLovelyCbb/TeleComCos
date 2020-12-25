package com.telecom.cos.http;

import com.telecom.cos.httpclient.HttpClient;
public class TelecomSupport {
	protected HttpClient http;
	
	protected final boolean USE_SSL;
	TelecomSupport(){
		USE_SSL = Configuration.useSSL();
		http = new HttpClient();
	}
	
	public HttpClient getHttp() {
		return http;
	}
}
