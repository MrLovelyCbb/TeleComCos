package com.telecom.cos.adapter;

import java.util.List;

import com.telecom.cos.R;
import com.telecom.cos.entity.Telecom_News_Entity;
import com.telecom.cos.util.StringUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Telecom_News_Adapter extends BaseAdapter{

	private List<Telecom_News_Entity> m_list;
	private Context m_context;
	protected LayoutInflater mInflater;
	
	public Telecom_News_Adapter(Context context,List<Telecom_News_Entity> mylist){
		this.m_context = context;
		this.m_list = mylist;
		mInflater = LayoutInflater.from(m_context);
	}
	
	public int getCount() {
		if(m_list!=null)
			return m_list.size();
		else
			return 0;
	}
	
	public Object getItem(int position) {
		if(m_list!=null)
			return m_list.get(position);
		else
			return null;
	}
	
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View My_View = null;
		if(convertView==null){
			My_View = mInflater.inflate(R.layout.telecom_news_items, null);
			NewsHolder newsHolder = new NewsHolder();
			newsHolder.news_title = (TextView) My_View.findViewById(R.id.telecom_link_title);
			newsHolder.news_date = (TextView) My_View.findViewById(R.id.telecom_date_txt);
			My_View.setTag(newsHolder);
		}else{
			My_View = convertView;
		}
		NewsHolder raHolder = (NewsHolder) My_View.getTag();
		Telecom_News_Entity newsEntity = m_list.get(position);
		if(m_list != null){
			raHolder.news_title.setText(newsEntity.getNews_Title());
			raHolder.news_date.setText(StringUtil.GetPHPDateFormat(newsEntity.getNews_Date()));
		}

		return My_View;
	}

	public class NewsHolder {
		TextView news_title;
		TextView news_date;
	}
}
