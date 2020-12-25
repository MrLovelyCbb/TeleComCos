package com.telecom.cos.ui.base;

import com.telecom.cos.R;
import com.telecom.cos.preferences.IntentPref;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost;

public class TeleComMainTabActivity extends TabActivity implements OnCheckedChangeListener{

	private TabHost mTabHost;
	private Intent mIndex;  
    private Intent mChannel;  
    private Intent mSearch;  
    private Intent mMyAinme;  
    private Intent mTeleComMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		InitIntentActivity();
		InitComponents();
	}
	/**
	 * 初始化Intent
	 */
	public void InitIntentActivity(){
		mIndex = new Intent(IntentPref.Index);
		mChannel = new Intent(IntentPref.Channel);
		mSearch = new Intent(IntentPref.Search);
		mMyAinme = new Intent(IntentPref.MyAnime);
		mTeleComMore = new Intent(IntentPref.Telecom_More);
	}
	/**
	 * 初始化组件
	 */
	public void InitComponents(){
		((RadioButton) findViewById(R.id.radio_button_index)).setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_button_channel)).setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_button_search)).setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_button_mycomic)).setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_button_more)).setOnCheckedChangeListener(this);
		
		setupIntent();
	}
	
	public void setupIntent(){
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		
		localTabHost.addTab(buildTabSpec("Index", R.string.Index_text_index, 
                R.drawable.telecom_menu_index, this.mIndex)); 
 
        localTabHost.addTab(buildTabSpec("Channel", R.string.Index_text_channel, 
                R.drawable.telecom_menu_channel, this.mChannel)); 
 
        localTabHost.addTab(buildTabSpec("Search", 
                R.string.Index_text_search, R.drawable.telecom_menu_search, 
                this.mSearch)); 
 
        localTabHost.addTab(buildTabSpec("MyAnime", R.string.Index_text_mycomic, 
                R.drawable.telecom_menu_mycosplay, this.mMyAinme)); 
 
        localTabHost.addTab(buildTabSpec("MORE_TAB", R.string.Index_text_more, 
                R.drawable.telecom_menu_more, this.mTeleComMore)); 
	}
	
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon, 
            final Intent content) { 
        return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel), 
                getResources().getDrawable(resIcon)).setContent(content); 
    } 

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			switch (buttonView.getId()) {
			case R.id.radio_button_index:
				this.mTabHost.setCurrentTabByTag("Index");
				break;
				
			case R.id.radio_button_channel:
				this.mTabHost.setCurrentTabByTag("Channel");
				break;
				
			case R.id.radio_button_search:
				this.mTabHost.setCurrentTabByTag("Search");
				break;
				
			case R.id.radio_button_mycomic:
				this.mTabHost.setCurrentTabByTag("MyAnime");
				break;
				
			case R.id.radio_button_more:
				this.mTabHost.setCurrentTabByTag("MORE_TAB");
				break;

			default:
				break;
			}
		}
	}

}
