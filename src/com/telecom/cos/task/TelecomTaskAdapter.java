package com.telecom.cos.task;

public abstract class TelecomTaskAdapter implements TelecomTaskListener{

	public void onPreExecute(TelecomTask task) {
	};

	public void onPostExecute(TelecomTask task, TelecomResult result) {
	}

	public void onProgressUpdate(TelecomTask task, Object param) {
	}

	public void onCancelled(TelecomTask task) {
	}
	
}
