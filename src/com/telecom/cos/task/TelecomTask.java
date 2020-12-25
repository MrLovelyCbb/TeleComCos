package com.telecom.cos.task;

import java.util.Observable;
import java.util.Observer;

import android.os.AsyncTask;

public abstract class TelecomTask extends
		AsyncTask<TelecomParams, Object, TelecomResult> implements Observer {

	private TelecomTaskListener mListener = null;

	private ProgressMethodImpl mProgress = null;

	private boolean isCancelable = true;

	abstract protected TelecomResult _doInBackground(TelecomParams... params);

	public void setListener(TelecomTaskListener taskListener) {
		mListener = taskListener;
	}

	public TelecomTaskListener getListener() {
		return mListener;
	}

	public void doPublishProgress(Object... values) {
		super.publishProgress(values);
	}

	@Override
	protected TelecomResult doInBackground(TelecomParams... params) {
		TelecomResult result = _doInBackground(params);
		if (mProgress != null) {
			mProgress.update(99);
		}
		return result;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();

		if (mListener != null) {
			mListener.onCancelled(this);
		}

	}

	@Override
	protected void onPostExecute(TelecomResult result) {
		super.onPostExecute(result);
		if (mListener != null) {
			mListener.onPostExecute(this, result);
		}

		if (mProgress != null) {
			mProgress.success("");
		}

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (mListener != null) {
			mListener.onPreExecute(this);
		}

		if (mProgress != null) {
			mProgress.start("");
		}

	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
		if (mListener != null) {
			if (values != null && values.length > 0) {
				mListener.onProgressUpdate(this, values);
			}
		}
		if (mProgress != null) {
			mProgress.update(values[0]);
		}
	}

	public void update(Observable observable, Object data) {
		if (TaskManager.CANCEL_ALL == (Integer) data && isCancelable) {
			if (getStatus() == TelecomTask.Status.RUNNING) {
				cancel(true);
			}
		}

	}

	public void setCancelable(boolean flag) {
		isCancelable = flag;
	}

	public void setProgress(ProgressMethodImpl progressimpl) {
		mProgress = progressimpl;
	}
}
