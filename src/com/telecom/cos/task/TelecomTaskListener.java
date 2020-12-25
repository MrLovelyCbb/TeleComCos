package com.telecom.cos.task;

public interface TelecomTaskListener {

	void onPreExecute(TelecomTask task);

	void onPostExecute(TelecomTask task, TelecomResult result);

	void onProgressUpdate(TelecomTask task, Object param);

	void onCancelled(TelecomTask task);
}
