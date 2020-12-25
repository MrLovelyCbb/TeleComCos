package com.telecom.cos.ui.base;

import com.telecom.cos.http.TelecomApi;

import android.app.Application;

public class TeleComBaseApplication extends Application{
	
	public static TelecomApi mApi;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mApi = new TelecomApi();
	}

}
