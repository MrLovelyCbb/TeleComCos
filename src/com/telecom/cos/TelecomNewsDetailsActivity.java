
package com.telecom.cos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.telecom.cos.entity.Telecom_News_Comment;
import com.telecom.cos.httpclient.HttpException;
import com.telecom.cos.ui.base.TeleComBaseActivity;
import com.telecom.cos.ui.base.TeleComBaseApplication;
import com.telecom.cos.util.StringUtil;

import android.content.Intent;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.TextView;



public class TelecomNewsDetailsActivity extends TeleComBaseActivity{
	TextView news_title;
	TextView news_time;
	TextView news_source;
	WebView news_webview;
	
	String title,time,source,newsid,newslink;
	String htmldata="";
	String commentid;
	@Override
	protected boolean _onCreate(Bundle savedInstanceState) {
		getIntentdata();
		setContentView(R.layout.telecom_news_details);
		InitCompontents();
		return super._onCreate(savedInstanceState);
	}
	
	public void getIntentdata(){
		Intent intent = getIntent();
		title = intent.getStringExtra("news_titles");
//		newsid = intent.getStringExtra("news_id");
		time = intent.getStringExtra("news_time");
		newslink= intent.getStringExtra("news_link");
		commentid = intent.getStringExtra("news_comment");
	}
	
	public void InitCompontents(){
		news_title = (TextView) findViewById(R.id.newsdetails_title);
		news_time = (TextView) findViewById(R.id.newsdetails_time);
		news_source = (TextView) findViewById(R.id.newsdetails_source);
		news_webview = (WebView) findViewById(R.id.newsdetails_webview);
		
		news_title.setText(title);
		news_time.setText(StringUtil.GetPHPDateFormatLong(time));
		String data_source = getResources().getString(R.string.lbl_newsdetails_comefrom);
		data_source = String.format(data_source, "网络");
		news_source.setText(data_source);
		gethtmldata();
		WebSettings settings = news_webview.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		settings.setJavaScriptEnabled(true);
		settings.setLoadWithOverviewMode(true);
//		news_webview.loadDataWithBaseURL("http://icos.sgamer.com/ds/", htmldata, "text/html", "utf-8", null);
//		news_webview.loadUrl("http://www.baidu.com");
//		news_webview.setWebViewClient(new WebViewClient());
	}
	
	
	public void gethtmldata(){
		try {
			TeleComBaseApplication.mApi.Api_GetNewsDetailsList(newslink);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getcomments();
		submitcomments();
	}
	
	//获取新闻详细信息
//	Document doc = Jsoup.parse(response);
//	Elements articleElements = doc.select("div.article");
//	Elements pageElements = doc.select("p.page span.num");
//	String docstr = articleElements.html();
//	if(!pageElements.isEmpty()){
//		String pages = pageElements.last().text();
//		System.out.println("docstr"+docstr+"总页数"+pages);
//	}
//	news_webview.loadDataWithBaseURL("http://icos.sgamer.com/ds/", docstr, "text/html", "utf-8", null);
//	news_webview.setWebViewClient(new WebViewClient());
	
	//提交新闻详细信息评论
//	String status = response.getString("status");
//	if(status.equals("1")){
//		System.out.println("OK Post Comment News ");
//	}
	
	//获取新闻详细信息评论信息
	
//	JSONArray jsonArray = response.getJSONArray("commentlist");
//	
//	for(int i=0;i<jsonArray.length();i++){
//		Telecom_News_Comment news_comment = new Telecom_News_Comment();
//		news_comment.setUsername(jsonArray.getJSONObject(i).getString("username"));
//		news_comment.setComment_content(jsonArray.getJSONObject(i).getString("body"));
//		news_comment.setDatetime(jsonArray.getJSONObject(i).getString("addtime"));
//		news_comment.setComment_nickname(jsonArray.getJSONObject(i).getString("nickname"));
//		System.out.println("Username"+news_comment.getUsername());
//		System.out.println("Body"+news_comment.getComment_content());
//		System.out.println("time"+ news_comment.getDatetime());
//		System.out.println("nickname"+news_comment.getComment_nickname());
//		String nickname = news_comment.getComment_nickname();
//		
//		if(nickname.equals("")||nickname==null){
//			String username = news_comment.getUsername();
//			//return username;
//		}
//		
	
	
	
	public void submitcomments(){
		try {
			TeleComBaseApplication.mApi.Api_GetNewsSubmitComments(commentid, "What can B short", "18971458926");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void getcomments(){
		try {
			TeleComBaseApplication.mApi.Api_GetNewsComments(commentid);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class WebClientLoL extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	
	
}