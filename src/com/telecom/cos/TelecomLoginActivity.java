package com.telecom.cos;

import com.telecom.cos.entity.Status;
import com.telecom.cos.httpclient.HttpException;
import com.telecom.cos.preferences.IntentPref;
import com.telecom.cos.preferences.Preferences;
import com.telecom.cos.task.TelecomParams;
import com.telecom.cos.task.TelecomResult;
import com.telecom.cos.task.TelecomTask;
import com.telecom.cos.task.TelecomTaskAdapter;
import com.telecom.cos.task.TelecomTaskCallBack;
import com.telecom.cos.ui.base.TeleComBaseApplication;
import com.telecom.cos.util.StringUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class TelecomLoginActivity extends Activity{
	
	EditText edit_account;
	EditText edit_password;
	ImageButton btn_opation;
	Button btn_login;
	Button btn_register;
	CheckBox chb_remember_pwd;
	CheckBox chb_auto_login;
	String phonenumber,password;
	boolean savepwd,autologon;
	private SharedPreferences mPreferences;
	
	LoginTask logintask;
	Status loginstatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telecom_login);
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		InitCompontents();
	}
	
	
	
	public void InitCompontents(){
		edit_account = (EditText) this.findViewById(R.id.login_edit_account);
		edit_password = (EditText)this.findViewById(R.id.login_edit_pwd);
		btn_opation = (ImageButton) this.findViewById(R.id.login_btn_opation);
		btn_login = (Button) this.findViewById(R.id.login_btn_login);
		btn_register = (Button) this.findViewById(R.id.login_btn_register);
		chb_remember_pwd = (CheckBox) this.findViewById(R.id.login_cb_savepwd);
		chb_auto_login = (CheckBox) this.findViewById(R.id.login_auto_login);
		
		phonenumber = mPreferences.getString(Preferences.UserName, "");
		password = mPreferences.getString(Preferences.PassWord, "");
		savepwd = mPreferences.getBoolean(Preferences.SavePwd, true);
		autologon = mPreferences.getBoolean(Preferences.AutoLogon, true);
		
		
		edit_account.setText(phonenumber);
		edit_password.setText(password);
		
		btn_login.setOnClickListener(TelecomLogin_OnClickListener);
		btn_register.setOnClickListener(Comeinregister_OnClickListener);
	}
	
	private View.OnClickListener TelecomLogin_OnClickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			phonenumber = edit_account.getText().toString();
			password = edit_password.getText().toString();
			savepwd = chb_remember_pwd.isChecked();
			autologon = chb_auto_login.isChecked();
			
			if (!StringUtil.isMobileNO(phonenumber)) {
				StringUtil.ToastShow(TelecomLoginActivity.this,R.string.register_status_vlidatephone_isphonenumber);
			}
			
			if(StringUtil.isValidCredentials(phonenumber, password)){
				telecomlogin();
			}else{
				StringUtil.ToastShow(TelecomLoginActivity.this, getString(R.string.login_status_fail_null));
			}

		}
	};
	
	private View.OnClickListener  Comeinregister_OnClickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			startActivity(new Intent(IntentPref.Register));
		}
	};
	
	public void onLoginbegin(){
		TelecomTaskCallBack.getInstance(TelecomTaskCallBack.DIALOG_MODE, this).start(
				getString(R.string.login_status_logging_in));
	}
	
	public void onFaileLogin(String prompt){
		TelecomTaskCallBack.getInstance(TelecomTaskCallBack.DIALOG_MODE,this).failed(prompt);
	}
	
	public void onSuccessLogon(String prompt){
		TelecomTaskCallBack.getInstance(TelecomTaskCallBack.DIALOG_MODE,this).success(prompt);
		Rememberpwd(phonenumber,password,savepwd,autologon);
		startActivity(new Intent(IntentPref.Main_Menu));
	}
	
	public void telecomlogin(){
		if(logintask != null && logintask.getStatus() == TelecomTask.Status.RUNNING){
			return;
		}else{
			logintask = new LoginTask();
			logintask.setListener(new TelecomTaskAdapter() {

				@Override
				public void onPreExecute(TelecomTask task) {
					onLoginbegin();
				}

				@Override
				public void onPostExecute(TelecomTask task, TelecomResult result) {
					if(result == TelecomResult.OK){
						onSuccessLogon(loginstatus.getInfo());
					}else{
						onFaileLogin(loginstatus.getInfo());
					}
				}
				
			});
			logintask.execute();
		}
	}
	
//登录请求结果	
//	String status = response.getString("status");
//	String info = response.getString("info");
//	String data = response.getString("data");
//
//	System.out.println("登录"+status);
//	System.out.println("信息"+info);
//	System.out.println("数据"+data);
//	
//	if(status.equals("1")){
//		onSuccessLogon(info);
//	}else{
//		onFaileLogin(info);
//	}
	
	
	private class LoginTask extends TelecomTask{

	@Override
	protected TelecomResult _doInBackground(TelecomParams... params) {
		try {
			loginstatus = TeleComBaseApplication.mApi.Api_Login(phonenumber, password);
			if(loginstatus.getStatus().equals("1")){
				return TelecomResult.OK;
			}else{
				return TelecomResult.FAILED;
			}
		} catch (HttpException e) {
			onFaileLogin("请求错误...");
			return TelecomResult.FAILED;
		}
	}
		
	}
	
	
	/**
	 * 通过SharedPreferences 共享XML设置
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	public void Rememberpwd(String username, String password,boolean savepwd,boolean autologon) {
		
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putString(Preferences.UserName, username);//帐号
		if(savepwd == true){
			editor.putString(Preferences.PassWord, password);//密码
		}
		editor.putBoolean(Preferences.SavePwd, savepwd); //保存密码
		editor.putBoolean(Preferences.AutoLogon, autologon);//自动登录
		editor.commit();
	}
}
