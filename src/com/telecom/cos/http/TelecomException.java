package com.telecom.cos.http;

public class TelecomException extends Exception{
	
	/**
	 * 电信Android异常处理类
	 */
	private static final long serialVersionUID = -7731349270557949075L;
	private int statusCode = -1;  // 默认返回错误code -1

	public TelecomException(String msg){
		super(msg);
	}
	
	public TelecomException(Exception exce){
		super(exce);
	}
	
	public TelecomException(String msg, int statusCode){
		super(msg);
		this.statusCode = statusCode;
	}
	
	public TelecomException(String msg, Exception exce){
		super(msg,exce);
	}
	
	public TelecomException(String msg, Exception cause, int statusCode){
		super(msg, cause);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode(){
		return this.statusCode;
	}
}
