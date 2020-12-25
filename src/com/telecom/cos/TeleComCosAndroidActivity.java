package com.telecom.cos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.telecom.cos.adapter.Telecom_News_Adapter;
import com.telecom.cos.entity.Telecom_News_Entity;
import com.telecom.cos.httpclient.HttpException;
import com.telecom.cos.httpclient.Response;
import com.telecom.cos.preferences.IntentPref;
import com.telecom.cos.task.TelecomParams;
import com.telecom.cos.task.TelecomResult;
import com.telecom.cos.task.TelecomTask;
import com.telecom.cos.task.TelecomTaskAdapter;
import com.telecom.cos.task.TelecomTaskCallBack;
import com.telecom.cos.ui.base.TeleComBaseActivity;
import com.telecom.cos.ui.base.TeleComBaseApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TeleComCosAndroidActivity extends TeleComBaseActivity {
	ListView index_news_listview;
	Telecom_News_Adapter telecom_News_Adapter;
	Telecom_News_Entity newsEntity;
	public List<Telecom_News_Entity> mynewslist = new ArrayList<Telecom_News_Entity>();
	NewsIndexListask newsIndexListask;
	
	@Override
	protected boolean _onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.index);
		InitComponents();
		return super._onCreate(savedInstanceState);
	}
    
    public void InitComponents(){
    	index_news_listview = (ListView) findViewById(R.id.telecom_news_listview);
    	GetJsonNewsList();
		index_news_listview.setOnItemClickListener(newsItems_OnClickListener);
    }
    
    public void onFaileInitData(String prompt){
    	TelecomTaskCallBack.getInstance(TelecomTaskCallBack.DIALOG_MODE,this).failed(prompt);
    }
    
    public void GetJsonNewsList(){
    	
    	if(newsIndexListask != null && newsIndexListask.getStatus() == TelecomTask.Status.RUNNING){
			return;
		}else{
			newsIndexListask = new NewsIndexListask();
			newsIndexListask.setListener(new TelecomTaskAdapter() {

				@Override
				public void onPostExecute(TelecomTask task, TelecomResult result) {
					if(result == TelecomResult.OK){
						mynewslist = ((NewsIndexListask)task).getMylist();
						System.out.println("list size !!!!"+mynewslist.size());
						telecom_News_Adapter = new Telecom_News_Adapter(TeleComCosAndroidActivity.this,mynewslist);
						index_news_listview.setAdapter(telecom_News_Adapter);
						telecom_News_Adapter.notifyDataSetChanged();
					}else{
						onFaileInitData(newsEntity.getStatus().getInfo());	
					}
				}
				
			});
			newsIndexListask.execute();
		}
    	
    	
		
	}
    
    public void GetJsonImagesList(){
    	try {
			TeleComBaseApplication.mApi.Api_GetIndexImages();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

    class NewsIndexListask extends TelecomTask{
    	private List<Telecom_News_Entity> mylist;
    	
		public List<Telecom_News_Entity> getMylist() {
			return mylist;
		}

		@Override
		protected TelecomResult _doInBackground(TelecomParams... params) {
			try {
				Telecom_News_Entity newsEntity = TeleComBaseApplication.mApi.Api_GetNewsList(1, 1, 10);
				mylist = newsEntity.getMynewslist();
				System.out.println(newsEntity.getStatus().getInfo());
				if(newsEntity.getStatus().getStatus().equals("1")){
					return TelecomResult.OK;
				}else{
					return TelecomResult.FAILED;
				}
				
			} catch (HttpException e) {
				return TelecomResult.FAILED;
			}
		}
    	
    }
    
    private class CosIndexListtask extends TelecomTask{

		@Override
		protected TelecomResult _doInBackground(TelecomParams... params) {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }
    
    //获取新闻列表结果集
//    String status = response.getString("status");
//	String info = response.getString("info");
//	JSONObject jsonObject = response.getJSONObject("data");
//	JSONArray jsonArray = jsonObject.getJSONArray("list");
//	for(int i=0;i<jsonArray.length();i++){
//		Telecom_News_Entity newsEntity = new Telecom_News_Entity();
//		newsEntity.setNews_Title(jsonArray.getJSONObject(i).getString("title"));
//		newsEntity.setNews_Links(jsonArray.getJSONObject(i).getString("_link"));
//		newsEntity.setNews_Date(jsonArray.getJSONObject(i).getString("updatetime"));
//		newsEntity.setNews_Comment(jsonArray.getJSONObject(i).getString("commentrootid"));
//		System.out.println("title"+jsonArray.getJSONObject(i).getString("title"));
//		System.out.println("links"+jsonArray.getJSONObject(i).getString("_link"));
//		
//		mylist.add(newsEntity);
//		
//	}
//	telecom_News_Adapter.notifyDataSetChanged();
    
    
    //获取首页Cos照片结果集
//    JSONObject jsonObject = response.getJSONObject("data").getJSONObject("list");
//	JSONArray vedioArray = jsonObject.getJSONArray("video");
//	JSONArray photoArray = jsonObject.getJSONArray("photo");
//	JSONArray cosArray = jsonObject.getJSONArray("cos");
//	
//	for(int i=0;i<vedioArray.length();i++){
//		
//	}
//	
//	for(int j=0;j<vedioArray.length();j++){
//		
//	}
//	
//	for(int k=0;k<photoArray.length();k++){
//		
//	}
    
    
    private AdapterView.OnItemClickListener newsItems_OnClickListener = new AdapterView.OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Telecom_News_Entity news_Entity = (Telecom_News_Entity) telecom_News_Adapter.getItem(arg2);
			
			Intent intent=new Intent(IntentPref.NewsDetails);
			intent.putExtra("news_titles", news_Entity.getNews_Title());
			intent.putExtra("news_time", news_Entity.getNews_Date());
			intent.putExtra("news_link", news_Entity.getNews_Links());
			intent.putExtra("news_comment", news_Entity.getNews_Comment());
			startActivity(intent);
		}
	};
}