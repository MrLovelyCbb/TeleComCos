package com.telecom.cos.httpclient;

/**
 * HTTP StatusCode is not 200
 */
public class HttpException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4985938210404203707L;
	private int statusCode = -1;

	public HttpException(String msg) {
		super(msg);
	}

	public HttpException(Exception cause) {
		super(cause);
	}

	public HttpException(String msg, int statusCode) {
		super(msg);
		this.statusCode = statusCode;
	}

	public HttpException(String msg, Exception cause) {
		super(msg, cause);
	}

	public HttpException(String msg, Exception cause, int statusCode) {
		super(msg, cause);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

}
