package com.telecom.cos.ui.base;

import android.app.Activity;
import android.os.Bundle;

public class TeleComBaseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		_onCreate(savedInstanceState);
	}
	
	protected boolean _onCreate(Bundle savedInstanceState){
		return true;
	}
	
}
