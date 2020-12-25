package com.telecom.cos.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.telecom.cos.httpclient.HttpException;
import com.telecom.cos.httpclient.Response;

/**
 * 电信主页新闻 实体类
 * 
 * @author MrLovelyCbb
 * 
 */
public class Telecom_News_Entity {
	private Status status;
	private static JSONObject jsonObject;
	private List<Telecom_News_Entity> mynewslist ;

	public List<Telecom_News_Entity> getMynewslist() {
		return mynewslist;
	}

	Telecom_News_Entity(){
		
	}
	
	public Telecom_News_Entity(Response res, Status status)
			throws HttpException {
		this.status = status;
		
		jsonObject = res.asJSONObject();
		try {
			JSONObject newsjson = jsonObject.getJSONObject("data");
			JSONArray jsonArray = newsjson.getJSONArray("list");
			mynewslist = new ArrayList<Telecom_News_Entity>();
			for (int i = 0; i < jsonArray.length(); i++) {
				Telecom_News_Entity newsEntity = new Telecom_News_Entity();
				newsEntity.setNews_Title(jsonArray.getJSONObject(i).getString("title"));
				newsEntity.setNews_Links(jsonArray.getJSONObject(i).getString("_link"));
				newsEntity.setNews_Date(jsonArray.getJSONObject(i).getString("updatetime"));
				newsEntity.setNews_Comment(jsonArray.getJSONObject(i).getString("commentrootid"));
				newsEntity.setStatus(status);
				System.out.println("title"+jsonArray.getJSONObject(i).getString("title"));
				System.out.println("links"+jsonArray.getJSONObject(i).getString("_link"));
				
				mynewslist.add(newsEntity);
			}
		} catch (JSONException jsone) {
			throw new HttpException(jsone);
		}
	}
	
	
	 
	
//	public List<Telecom_News_Entity> constructNews(Response res,Status status)throws HttpException{
//		jsonObject = res.asJSONObject();
//		try {
//			JSONObject newsjson = jsonObject.getJSONObject("data");
//			JSONArray jsonArray = newsjson.getJSONArray("list");
//			List<Telecom_News_Entity> mylist = new ArrayList<Telecom_News_Entity>(jsonArray.length());
//			for (int i = 0; i < jsonArray.length(); i++) {
//				Telecom_News_Entity newsEntity = new Telecom_News_Entity();
//				newsEntity.setNews_Title(jsonArray.getJSONObject(i).getString("title"));
//				newsEntity.setNews_Links(jsonArray.getJSONObject(i).getString("_link"));
//				newsEntity.setNews_Date(jsonArray.getJSONObject(i).getString("updatetime"));
//				newsEntity.setNews_Comment(jsonArray.getJSONObject(i).getString("commentrootid"));
//				newsEntity.setStatus(status);
//				System.out.println("title"+jsonArray.getJSONObject(i).getString("title"));
//				System.out.println("links"+jsonArray.getJSONObject(i).getString("_link"));
//				
//				mylist.add(newsEntity);
//			}
//			return mylist;
//		} catch (JSONException jsone) {
//			throw new HttpException(jsone);
//		}
//	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	public Status getStatus() {
		return status;
	}

	/**
	 * 新闻标题
	 */
	private String News_Title;
	/**
	 * 新闻日期
	 */
	private String News_Date;
	/**
	 * 新闻链接(source link)
	 */
	private String News_Links;
	/**
	 * 评论id
	 */
	private String News_Comment;

	public String getNews_Comment() {
		return News_Comment;
	}

	public void setNews_Comment(String news_Comment) {
		News_Comment = news_Comment;
	}

	public String getNews_Title() {
		return News_Title;
	}

	public void setNews_Title(String news_Title) {
		News_Title = news_Title;
	}

	public String getNews_Date() {
		return News_Date;
	}

	public void setNews_Date(String news_Date) {
		News_Date = news_Date;
	}

	public String getNews_Links() {
		return News_Links;
	}

	public void setNews_Links(String news_Links) {
		News_Links = news_Links;
	}
}
