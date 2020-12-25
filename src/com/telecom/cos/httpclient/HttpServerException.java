package com.telecom.cos.httpclient;

/**
 * HTTP StatusCode is not 200 INTERNAL_SERVER_ERROR: 500 BAD_GATEWAY: 502
 * SERVICE_UNAVAILABLE: 503
 */
public class HttpServerException extends HttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3672475355411420740L;

	public HttpServerException(Exception cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public HttpServerException(String msg, Exception cause, int statusCode) {
		super(msg, cause, statusCode);
		// TODO Auto-generated constructor stub
	}

	public HttpServerException(String msg, Exception cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

	public HttpServerException(String msg, int statusCode) {
		super(msg, statusCode);
		// TODO Auto-generated constructor stub
	}

	public HttpServerException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
