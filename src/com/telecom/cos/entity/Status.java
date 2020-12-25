package com.telecom.cos.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.telecom.cos.httpclient.HttpException;
import com.telecom.cos.httpclient.Response;

public class Status implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5585733705320131831L;
	private String status;
	private String info;
	private String data;
	private JSONObject jsonObject;

	
	public Status(Response res) throws HttpException{
		init(res);
	}
	
	public void init(Response res) throws HttpException{
		jsonObject = res.asJSONObject();
		try {
			status = jsonObject.getString("status");
			data = jsonObject.getString("data");
			info = jsonObject.getString("info");
		} catch (JSONException e) {
			throw new HttpException(e.getMessage() + ":" + jsonObject.toString(), e);
		}
	}
	
	 public JSONObject getJsonObject() {
			return jsonObject;
		}

	 
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
