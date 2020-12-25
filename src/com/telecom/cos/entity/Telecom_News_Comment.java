package com.telecom.cos.entity;

public class Telecom_News_Comment {
private String username;
private String datetime;
private String comment_content;
private String comment_nickname;

public String getComment_nickname() {
	return comment_nickname;
}
public void setComment_nickname(String comment_nickname) {
	this.comment_nickname = comment_nickname;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getDatetime() {
	return datetime;
}
public void setDatetime(String datetime) {
	this.datetime = datetime;
}
public String getComment_content() {
	return comment_content;
}
public void setComment_content(String comment_content) {
	this.comment_content = comment_content;
}
}
