package com.telecom.cos.task;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public abstract class TelecomTaskProgress implements ProgressMethodImpl,TelecomWidget{
	private static final String TAG = "TelecomTaskProgress";
	public static final int MAX = 100;

	private ProgressBar mProgress = null;
	
	public TelecomTaskProgress(Context context){
		mProgress = new ProgressBar(context);
	}
	
	public boolean isAvailable() {
		if (null == mProgress) {
			Log.e(TAG, "R.id.progress_bar is missing");
			return false;
		}
		return true;
	}

	public void start(CharSequence text) {
		mProgress.setProgress(20);
	}

	public void cancel(CharSequence text) {
		// TODO Auto-generated method stub
		
	}

	public void success(CharSequence text) {
		mProgress.setProgress(MAX);
		resetProgressBar();
	}

	public void failed(CharSequence text) {
		resetProgressBar();
		showMessage(text);
	}

	public void update(Object arg0) {
		if (arg0 instanceof Integer) {
			mProgress.setProgress((Integer) arg0);
		} else if (arg0 instanceof CharSequence) {
			showMessage((String) arg0);
		}
	}
	
	public Context getContext(){
		if(mProgress != null){
			return mProgress.getContext();
		}
		return null;
	}

	public void setIndeterminate(boolean indeterminate) {
		mProgress.setIndeterminate(indeterminate);
	}
	
	private void resetProgressBar() {
		if (mProgress.isIndeterminate()) {
			// TODO: 第二次不会出现
			mProgress.setIndeterminate(false);
		}
		mProgress.setProgress(0);
	}

	private void showMessage(CharSequence text) {
		Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
	}
}
