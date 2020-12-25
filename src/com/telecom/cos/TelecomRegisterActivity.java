package com.telecom.cos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.telecom.cos.entity.Status;
import com.telecom.cos.entity.Telecom_News_Entity;
import com.telecom.cos.httpclient.HttpException;
import com.telecom.cos.httpclient.Response;
import com.telecom.cos.preferences.IntentPref;
import com.telecom.cos.task.TelecomParams;
import com.telecom.cos.task.TelecomResult;
import com.telecom.cos.task.TelecomTask;
import com.telecom.cos.task.TelecomTaskAdapter;
import com.telecom.cos.task.TelecomTaskCallBack;
import com.telecom.cos.ui.base.TeleComBaseApplication;
import com.telecom.cos.util.StringUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;

public class TelecomRegisterActivity extends Activity {
	EditText register_account;
	EditText register_nickname;
	EditText register_password;
	EditText confirm_password;
	EditText validate_number;
	Button btn_register;
	Button btn_validatenumber;

	String phonenumber;
	String nickname;
	String password;
	String password1;
	String validatenumber;

	private static final String validatemethod = "validate";
	private static final String registermethod = "register";
	
	TeleComRegister teleComRegistertask;
	TeleComValidate teleComValidatetask;
	
	Status registerStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telecom_register);

		InitCompontent();
	}

	public void InitCompontent() {
		register_account = (EditText) findViewById(R.id.register_edit_account);
		register_nickname = (EditText)findViewById(R.id.register_edit_nickname);
		register_password = (EditText) findViewById(R.id.register_edit_pwd);
		confirm_password = (EditText) findViewById(R.id.confirm_edit_pwd);
		validate_number = (EditText) findViewById(R.id.validate_edit_number);
		btn_register = (Button) findViewById(R.id.register_submit);
		btn_validatenumber = (Button) findViewById(R.id.register_validatenumber);

		btn_register.setOnClickListener(registerSubmit_OnClickListener);
		btn_validatenumber.setOnClickListener(validatesms_OnClickListener);
	}

	private View.OnClickListener validatesms_OnClickListener = new View.OnClickListener() {

		public void onClick(View v) {
			System.out.println("计时器90秒");
			// MyCountTimer mc=new MyCountTimer(90000,1000);
			// mc.start();
			if (ValidateRegInfo(validatemethod)) {
				btn_validatenumber.setEnabled(false);
				MyCountTimer mc = new MyCountTimer(90000, 1000);
				mc.start();
			}
		}
	};

	class MyCountTimer extends CountDownTimer {
		public MyCountTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			btn_validatenumber.setEnabled(true);
			btn_validatenumber.setText(getString(R.string.get_validate_number));
		}

		@Override
		public void onTick(long millisUntilFinished) {

			btn_validatenumber.setText("等待90秒(" + millisUntilFinished / 1000
					+ ")...");
			// StringUtil.ToastShow(TelecomRegisterActivity.this,
			// millisUntilFinished / 1000 + "");//toast有显示时间延迟
		}
	}

	private View.OnClickListener registerSubmit_OnClickListener = new View.OnClickListener() {

		public void onClick(View v) {
			ValidateRegInfo(registermethod);
		}
	};

	public boolean ValidateRegInfo(String method) {
		phonenumber = register_account.getText().toString();
		nickname = register_nickname.getText().toString();
		password = register_password.getText().toString();
		password1 = confirm_password.getText().toString();
		validatenumber = validate_number.getText().toString();

		System.out.println(StringUtil.isEquals(password, password1));

		if (ValidateRegisterInfo(method)) {
			if (method.equals(registermethod)) {
				RegisterMethod(registermethod);
				return true;
			}
			if (method.equals(validatemethod)) {
				RegisterMethod(validatemethod);
				return true;
			}
		}
		return false;

	}

	public boolean ValidateRegisterInfo(String method) {

		if (!StringUtil.isMobileNO(phonenumber)) {
			StringUtil.ToastShow(this,
					R.string.register_status_vlidatephone_isphonenumber);
			return false;
		}

		if (method.equals(validatemethod)) {
			if (TextUtils.isEmpty(phonenumber)) {
				return false;
			} else {
				return true;
			}
		}

		if (StringUtil.isValidCredentials(phonenumber, password)) {
		} else {
			StringUtil.ToastShow(TelecomRegisterActivity.this,
					getString(R.string.login_status_fail_null));
			return false;
		}

		if (!StringUtil.isEquals(password, password1)) {
			StringUtil.ToastShow(TelecomRegisterActivity.this,
					getString(R.string.register_status_doublepassword_error));
			return false;
		}
		if (method.equals(registermethod)) {
			if (TextUtils.isEmpty(validatenumber)) {
				StringUtil
						.ToastShow(
								TelecomRegisterActivity.this,
								getString(R.string.register_status_validatenumber_isnull));
				return false;
			}
		}
		return true;
	}

	private void onValidateCodebegin(String method) {
		if (method.equals(registermethod)) {
			TelecomTaskCallBack.getInstance(TelecomTaskCallBack.DIALOG_MODE,
					this).start(getString(R.string.register_status_loading_in));
		}
		if (method.equals(validatemethod)) {
			TelecomTaskCallBack.getInstance(TelecomTaskCallBack.DIALOG_MODE,
					this).start(getString(R.string.get_status_validate_number));
		}
	}

	private void onSuccessRegister(String prompt, String method) {
		TelecomTaskCallBack.getInstance(TelecomTaskCallBack.DIALOG_MODE, this)
				.success(prompt);
		if (method.equals(registermethod)) {
			startActivity(new Intent(IntentPref.Login));
		}
		
	}

	private void onFaileRegister(String prompt, String method) {
		TelecomTaskCallBack.getInstance(TelecomTaskCallBack.DIALOG_MODE, this)
				.failed(prompt);
	}
	
	 class TeleComRegister extends TelecomTask{

			@Override
			protected TelecomResult _doInBackground(TelecomParams... params) {
				try {
					registerStatus = TeleComBaseApplication.mApi.Api_Register(phonenumber,nickname, password,
							validatenumber);
					if(registerStatus.getStatus().equals("1")){
						return TelecomResult.OK;
					}else{
						return TelecomResult.FAILED;
					}
				} catch (HttpException e) {
					return TelecomResult.FAILED;
				}

			}
	    	
	    }
	 
	 class TeleComValidate extends TelecomTask{

		@Override
		protected TelecomResult _doInBackground(TelecomParams... params) {
			try {
				registerStatus = TeleComBaseApplication.mApi.Api_VlidateNumber(phonenumber);
				String code = registerStatus.getJsonObject().getJSONObject("data").getString("code");;
				System.out.println("验证码"+code);
				if(registerStatus.getStatus().equals("1")){
					return TelecomResult.OK;
				}else{
					return TelecomResult.FAILED;
				}
			} catch (HttpException e) {
				return TelecomResult.FAILED;
			} catch (JSONException e) {
				return TelecomResult.FAILED;
			}
		}
		 
	 }

	//获取验证码
////	 String vcode = response.getJSONObject("data")
////	 .getString("vcode");
//	String code = response.getJSONObject("data").getString(
//			"code");
//	String status = response.getString("status");
//	String info = response.getString("info");
//
////	System.out.println("vcode 验证码" + code);
//	System.out.println("status 状态信息" + status);
//	System.out.println("info 状态描述" + info);
//	if (status.equals("1")) {
//		System.out.println("VCODE" + code);
//		onSuccessRegister(info, method);
//	} else {
//		onFaileRegister(info, method);
//	}
	
	//注册提交
//	String status = response.getString("status");
//	String info = response.getString("info");
//	String data = response.getString("data");
//
//	if (status.equals("1")) {
//		onSuccessRegister(info, method);
//	} else {
//		onFaileRegister(info, method);
//	}
	
	
	
	
	public void RegisterMethod(final String method) {
		onValidateCodebegin(method);
		if (method.equals(registermethod)) {
			if(teleComRegistertask != null && teleComRegistertask.getStatus() == TelecomTask.Status.RUNNING){
				return;
			}else{
				teleComRegistertask = new TeleComRegister();
				teleComRegistertask.setListener(new TelecomTaskAdapter() {

					@Override
					public void onPreExecute(TelecomTask task) {
						// TODO Auto-generated method stub
						super.onPreExecute(task);
					}

					@Override
					public void onPostExecute(TelecomTask task,
							TelecomResult result) {
						if(result == TelecomResult.OK){
							onSuccessRegister(registerStatus.getInfo(), registermethod);
						}else{
							onFaileRegister(registerStatus.getInfo(), registermethod);
						}
					}
					
				});
				teleComRegistertask.execute();
			}
		}
		if (method.equals(validatemethod)) {
			if(teleComValidatetask != null && teleComValidatetask.getStatus() == TelecomTask.Status.RUNNING){
				return;
			}else{
				teleComValidatetask = new TeleComValidate();
				teleComValidatetask.setListener(new TelecomTaskAdapter() {

					@Override
					public void onPostExecute(TelecomTask task,
							TelecomResult result) {
						if(result == TelecomResult.OK){
							onSuccessRegister(registerStatus.getInfo(), validatemethod);
						}else{
							onFaileRegister(registerStatus.getInfo(), validatemethod);
						}
					}
					
				});
				teleComValidatetask.execute();
			}
		}
	}
	
	 
}


