package com.telecom.cos.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public abstract class TelecomTaskCallBack {
	private static TelecomTaskCallBack _instance = null;
	/**
	 * 对话框模式
	 */
	public static final int DIALOG_MODE = 0x01;
	/**
	 * 刷新，更新模式
	 */
	public static final int REFRESH_MODE = 0x02;
	/**
	 * 滚动进度条模式
	 */
	public static final int PROGRESS_MODE = 0x03;

	public static TelecomTaskCallBack getInstance(int type, Context context) {
		switch (type) {
		case DIALOG_MODE:
			// 对话框初始化
			_instance = DialogFeedback.getInstance(); // 对话框模式
			break;
		case REFRESH_MODE:
			// 刷新，更新模式 初始化
			break;
		case PROGRESS_MODE:
			// 滚动进度条模式 初始化
			break;
		}
		_instance.setContext(context);
		return _instance;
	}

	protected Context _context;

	protected void setContext(Context context) {
		_context = context;
	}

	public Context getContent() {
		return _context;
	}

	// default do nothing
	public void start(String prompt) {
	};

	public void cancel() {
	};

	public void success(String prompt) {
	};

	public void success() {
		success("");
	};

	public void failed(String prompt) {
	};

	public void showProgress(int progress) {
	};
}

/**
 * 对话框
 */
class DialogFeedback extends TelecomTaskCallBack {
	private static DialogFeedback _instance = null;

	public static DialogFeedback getInstance() {
		if (_instance == null) {
			_instance = new DialogFeedback();
		}
		return _instance;
	}

	private ProgressDialog _dialog = null;

	@Override
	public void cancel() {
		if (_dialog != null) {
			_dialog.dismiss();
		}
	}

	@Override
	public void failed(String prompt) {
		if (_dialog != null) {
			_dialog.dismiss();
		}

		Toast toast = Toast.makeText(_context, prompt, Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public void start(String prompt) {
		_dialog = ProgressDialog.show(_context, "", prompt, true);
		_dialog.setCancelable(true);
	}

	@Override
	public void success(String prompt) {
		if (_dialog != null) {
			_dialog.dismiss();
		}
		Toast.makeText(_context, prompt, Toast.LENGTH_SHORT).show();
	}
	
	
}

class RefreshAnimationFeedback extends TelecomTaskCallBack {
	private static RefreshAnimationFeedback _instance = null;

	public static RefreshAnimationFeedback getInstance() {
		if (_instance == null) {
			_instance = new RefreshAnimationFeedback();
		}
		return _instance;
	}

	private Activity _activity;

	@Override
	protected void setContext(Context context) {
		super.setContext(context);
		_activity = (Activity) context;
	}

	@Override
	public void cancel() {
//		_activity.setRefreshAnimation(false);
		//取消之后关闭动画
	}

	@Override
	public void failed(String prompt) {
//		_activity.setRefreshAnimation(false);
		//失败之后关闭动画

		Toast toast = Toast.makeText(_context, prompt, Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public void start(String prompt) {
//		_activity.setRefreshAnimation(true);
		//开始前设置开启动画
	}

	@Override
	public void success(String prompt) {
//		_activity.setRefreshAnimation(false);
		//成功完成后关闭动画
	}

}

/**
 *
 */
class ProgressBarFeedback extends TelecomTaskCallBack {

	private static ProgressBarFeedback _instance = null;

	public static ProgressBarFeedback getInstance() {
		if (_instance == null) {
			_instance = new ProgressBarFeedback();
		}
		return _instance;
	}

	private Activity _activity;

	@Override
	protected void setContext(Context context) {
		super.setContext(context);
		_activity = (Activity) context;
	}

	@Override
	public void cancel() {
//		_activity.setGlobalProgress(0);
		//设置滚动进度条转动  进度
	}

	@Override
	public void failed(String prompt) {
		cancel();
		//当失败了，调用取消方法，关闭滚动进度条
		Toast toast = Toast.makeText(_context, prompt, Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public void start(String prompt) {
//		_activity.setGlobalProgress(10);
		//开始了，设置进度条进度为10
	}

	@Override
	public void success(String prompt) {
		Log.d("LDS", "ON SUCCESS");
//		_activity.setGlobalProgress(0);
		//当成功了，设置进度条，进度为0
	}

	@Override
	public void showProgress(int progress) {
//		_activity.setGlobalProgress(progress);
		//当还处在loading的状态时，进度条反应进度
	}
}
	// mProgress.setIndeterminate(true);
