package com.telecom.cos.httpclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

public class Response {
	private final byte[] buffer;
	private final HttpEntity entity;

	public Response(HttpResponse res) throws ResponseException {
		try {

			entity = res.getEntity();
			if (!entity.isRepeatable() || entity.getContentLength() < 0) {
				this.buffer = EntityUtils.toByteArray(entity);
			} else {
				this.buffer = null;
			}
		} catch (IllegalStateException e) {
			throw new ResponseException(e.getMessage(), e);
		} catch (IOException e) {
			throw new ResponseException(e.getMessage(), e);
		}
	}

	public InputStream asStream() throws ResponseException {
		try {
			if (this.buffer != null) {
				return new ByteArrayInputStream(this.buffer);
			} else {
				return entity.getContent();
			}
		} catch (IllegalStateException e) {
			throw new ResponseException(e.getMessage(), e);
		} catch (IOException e) {
			throw new ResponseException(e.getMessage(), e);
		}
	}

	public byte[] asByteArray() {
		return this.buffer;
	}

	/**
	 * 
	 * @return
	 */
	public String asString() {
		final InputStream inStream;
		try {
			inStream = new ByteArrayInputStream(this.buffer);

			if (entity.getContentLength() > Integer.MAX_VALUE) {
				throw new IllegalArgumentException(
						"HTTP entity too large to be buffered in memory");
			}

			int i = (int) entity.getContentLength();
			if (i < 0) {
				i = 4096;
			}

			Reader reader = new BufferedReader(new InputStreamReader(inStream,
					"UTF-8"));
			CharArrayBuffer buffer = new CharArrayBuffer(i);
			try {
				char[] tmp = new char[1024];
				int l;
				while ((l = reader.read(tmp)) != -1) {
					buffer.append(tmp, 0, l);
				}
			} finally {
				reader.close();
			}

			return buffer.toString();
		} catch (IOException e) {
			return "";
		}
	}

	public Object parseResponse(String responseBody) throws JSONException {
		return new JSONTokener(responseBody).nextValue();
	}

	// 将字符串转JSONObject或JSONArray
	public void handleasJson() throws JSONException {
		Object jsonResponse = parseResponse(asString());
		if (jsonResponse instanceof JSONObject) {
			setAsjsonObject((JSONObject) jsonResponse);
		} else if (jsonResponse instanceof JSONArray) {
			setAsjsonArray((JSONArray)jsonResponse);
		} else {
			throw new JSONException("Unexpected type "
					+ jsonResponse.getClass().getName());
		}
	}
	
	private JSONObject asjsonObject;

	private JSONArray asjsonArray;
	
	public JSONObject getAsjsonObject() {
		return asjsonObject;
	}

	public void setAsjsonObject(JSONObject asjsonObject) {
		this.asjsonObject = asjsonObject;
	}

	public JSONArray getAsjsonArray() {
		return asjsonArray;
	}

	public void setAsjsonArray(JSONArray asjsonArray) {
		this.asjsonArray = asjsonArray;
	}


	public JSONObject asJSONObject() {
		try {
			handleasJson();
			if (asjsonObject != null) {
				return getAsjsonObject();
			} else {
				return new JSONObject(asString());
			}
		} catch (JSONException e) {
			return null;
		}
	}

	public JSONArray asJSONArray() {
		try {
			handleasJson();
			if (asjsonArray != null) {
				return getAsjsonArray();
			} else {
				return new JSONArray(asString());

			}
		} catch (Exception jsone) {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public Document asDocument() {
		Document doc = null;
		try {
			doc = (Document) Jsoup.parse(asString());
		} catch (Exception e) {
			return null;
		}
		return doc;
	}

	/**
	 * 
	 * @param htmldata
	 * @return
	 */
	public Document asDocumentdata(String htmldata) {
		Document doc = null;
		try {
			doc = (Document) Jsoup.parse(htmldata);
		} catch (Exception e) {
			return null;
		}
		return doc;
	}

	public Document asDocument(String htmldata, String url) {
		Document doc = null;
		try {
			doc = (Document) Jsoup.parse(htmldata, url);
		} catch (Exception e) {
			return null;
		}
		return doc;
	}

	/**
	 * 返回jsoup document 数据
	 * 
	 * @param url
	 *            地址对比
	 * @return
	 */
	// @deprecated 过时
	public Document asDocument(String url) {
		Document doc = null;
		try {
			doc = (Document) Jsoup.parse(asString(), url);
		} catch (Exception e) {
			return null;
		}
		return doc;
	}

	/**
	 * 返回URL jsoup document数据
	 * 
	 * @param url
	 *            网页链接地址
	 * @return
	 */
	public Document asURLDocument(String url) {
		Document doc = null;
		try {
			doc = (Document) Jsoup.connect(url).timeout(60000).get();
		} catch (IOException e) {
			System.out.println("网络连接不正常");
			return null;
		}
		return doc;
	}

	private static Pattern escaped = Pattern.compile("&#([0-9]{3,5});");

	/**
	 * Unescape UTF-8 escaped characters to string.
	 * 
	 * @author pengjianq...@gmail.com
	 * 
	 * @param original
	 *            The string to be unescaped.
	 * @return The unescaped string
	 */
	public static String unescape(String original) {
		Matcher mm = escaped.matcher(original);
		StringBuffer unescaped = new StringBuffer();
		while (mm.find()) {
			mm.appendReplacement(unescaped, Character.toString((char) Integer
					.parseInt(mm.group(1), 10)));
		}
		mm.appendTail(unescaped);
		return unescaped.toString();
	}
}
