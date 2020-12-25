package com.telecom.cos;

import com.telecom.cos.preferences.IntentPref;
import com.telecom.cos.task.TelecomParams;
import com.telecom.cos.task.TelecomResult;
import com.telecom.cos.task.TelecomTask;
import com.telecom.cos.task.TelecomTaskAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class TelecomSplashActivity extends Activity{
	
	private long m_dwSplashTime=3000;
    private boolean m_bPaused=false;
    private boolean m_bSplashActive=true;
	private SplashLoadingTask loadingtask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.telecom_splash);
		
		TelecomLoading();
	}
	
	public void onFaileGetForums(){
		Toast.makeText(TelecomSplashActivity.this, "程序加载出现异常...", Toast.LENGTH_SHORT).show();
	}
	
	public void onSuccessGetForums(){
		
		Thread splashTimer = new Thread(){
			public void run(){
				try{
					long ms=0;
					while(m_bSplashActive && ms<m_dwSplashTime){
						sleep(100);
						if(!m_bPaused)
							ms+=100;
					}
				startActivity(new Intent(IntentPref.Login));
				}catch(Exception e){
					onFaileGetForums();
					android.os.Process.killProcess(android.os.Process.myPid());
				}finally{
					finish();
				}
			}
		};
		splashTimer.start();
		
	}
	
	public void TelecomLoading(){
		if(loadingtask != null && loadingtask.getStatus() == TelecomTask.Status.RUNNING){
			return;
		}else{
			loadingtask = new SplashLoadingTask();
			loadingtask.setListener(new TelecomTaskAdapter() {

				@Override
				public void onPostExecute(TelecomTask task, TelecomResult result) {
					if(result == TelecomResult.OK){
						onSuccessGetForums();
					}else if(result == TelecomResult.FAILED){
						onFaileGetForums();
					}
				}
				
			});
			loadingtask.execute();
		}
	}
	
	
	public class SplashLoadingTask extends TelecomTask{

		@Override
		protected TelecomResult _doInBackground(TelecomParams... params) {
			try{
				//可放置登录
				//可放置检测其网络
			}catch (Exception e) {
				return TelecomResult.FAILED;
			}
			return TelecomResult.OK;
		}
		
	}
}
