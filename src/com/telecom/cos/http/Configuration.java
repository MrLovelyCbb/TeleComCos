package com.telecom.cos.http;

import java.security.AccessControlException;
import java.util.Properties;

public class Configuration {
	private static Properties defaultProperty;
	
	private static boolean DALVIK;
	static {
		init();
	}
	
	static void init(){
		defaultProperty = new Properties();
		
		defaultProperty.setProperty("sgamer.http.useSSL", "false");
		defaultProperty.setProperty("sgamer.http.proxyHost.fallback","http.proxyHost");
		defaultProperty.setProperty("sgamer.http.proxyPort.fallback","http.proxyPort");
		defaultProperty.setProperty("sgamer.http.connectionTimeout","20000");
		defaultProperty.setProperty("sgamer.http.readTimeout", "120000");
		defaultProperty.setProperty("sgamer.http.retryCount", "3");
		defaultProperty.setProperty("sgamer.http.retryIntervalSecs", "10");
		defaultProperty.setProperty("sgamer.async.numThreads", "1");
		defaultProperty.setProperty("sgamer.clientVersion", "2.0");
		defaultProperty.setProperty("sgamer.http.hostname", "icos.sgamer.com");
		
		//"bbs.sgamer.com/" 
		try{
			Class.forName("dalvik.system.VMRuntime");
			defaultProperty.setProperty("sgamer.dalvik", "true");
		}catch(Exception e){
			defaultProperty.setProperty("sgamer.dalvik", "false");
		}
		DALVIK = getBoolean("sgamer.dalvik");
//		String t4jProps = "sgamer.properties";
		
//		boolean loaded = loadProperties(defaultProperty, "."
//				+ File.separatorChar + t4jProps)
//				|| loadProperties(
//						defaultProperty,
//						Configuration.class.getResourceAsStream("/WEB-INF/"
//								+ t4jProps))
//				|| loadProperties(defaultProperty,
//						Configuration.class.getResourceAsStream("/" + t4jProps));
	}
	
	
//	private static boolean loadProperties(Properties props, String path) {
//		try {
//			File file = new File(path);
//			if (file.exists() && file.isFile()) {
//				props.load(new FileInputStream(file));
//				return true;
//			}
//		} catch (Exception ignore) {
//		}
//		return false;
//	}
//
//	private static boolean loadProperties(Properties props, InputStream is) {
//		try {
//			props.load(is);
//			return true;
//		} catch (Exception ignore) {
//		}
//		return false;
//	}
	public static String GetHostName(){
		return getProperty("sgamer.http.hostname");
	}
	
	public static boolean isDalvik() {
		return DALVIK;
	}
	
	public static boolean useSSL() {
		return getBoolean("sgamer.http.useSSL");
	}
	
	public static String getScheme() {
		return useSSL() ? "https://" : "http://";
	}
	
	public static String getCilentVersion() {
		return getProperty("sgamer.clientVersion");
	}
	
	public static String getCilentVersion(String clientVersion) {
		return getProperty("sgamer.clientVersion", clientVersion);
	}

	public static String getSource() {
		return getProperty("sgamer.source");
	}

	public static String getSource(String source) {
		return getProperty("sgamer.source", source);
	}

	public static String getProxyHost() {
		return getProperty("sgamer.http.proxyHost");
	}

	public static String getProxyHost(String proxyHost) {
		return getProperty("sgamer.http.proxyHost", proxyHost);
	}

	public static String getProxyUser() {
		return getProperty("sgamer.http.proxyUser");
	}

	public static String getProxyUser(String user) {
		return getProperty("sgamer.http.proxyUser", user);
	}

	public static String getClientURL() {
		return getProperty("sgamer.clientURL");
	}

	public static String getClientURL(String clientURL) {
		return getProperty("sgamer.clientURL", clientURL);
	}

	public static String getProxyPassword() {
		return getProperty("sgamer.http.proxyPassword");
	}

	public static String getProxyPassword(String password) {
		return getProperty("sgamer.http.proxyPassword", password);
	}

	public static int getProxyPort() {
		return getIntProperty("sgamer.http.proxyPort");
	}

	public static int getProxyPort(int port) {
		return getIntProperty("sgamer.http.proxyPort", port);
	}

	public static int getConnectionTimeout() {
		return getIntProperty("sgamer.http.connectionTimeout");
	}

	public static int getConnectionTimeout(int connectionTimeout) {
		return getIntProperty("sgamer.http.connectionTimeout",
				connectionTimeout);
	}

	public static int getReadTimeout() {
		return getIntProperty("sgamer.http.readTimeout");
	}

	public static int getReadTimeout(int readTimeout) {
		return getIntProperty("sgamer.http.readTimeout", readTimeout);
	}

	public static int getRetryCount() {
		return getIntProperty("sgamer.http.retryCount");
	}

	public static int getRetryCount(int retryCount) {
		return getIntProperty("sgamer.http.retryCount", retryCount);
	}

	public static int getRetryIntervalSecs() {
		return getIntProperty("sgamer.http.retryIntervalSecs");
	}

	public static int getRetryIntervalSecs(int retryIntervalSecs) {
		return getIntProperty("sgamer.http.retryIntervalSecs",
				retryIntervalSecs);
	}

	public static String getUser() {
		return getProperty("sgamer.user");
	}

	public static String getUser(String userId) {
		return getProperty("sgamer.user", userId);
	}

	public static String getPassword() {
		return getProperty("sgamer.password");
	}

	public static String getPassword(String password) {
		return getProperty("sgamer.password", password);
	}

	public static String getUserAgent() {
		return getProperty("sgamer.http.userAgent");
	}

	public static String getUserAgent(String userAgent) {
		return getProperty("sgamer.http.userAgent", userAgent);
	}

	public static String getOAuthConsumerKey() {
		return getProperty("sgamer.oauth.consumerKey");
	}

	public static String getOAuthConsumerKey(String consumerKey) {
		return getProperty("sgamer.oauth.consumerKey", consumerKey);
	}

	public static String getOAuthConsumerSecret() {
		return getProperty("sgamer.oauth.consumerSecret");
	}

	public static String getOAuthConsumerSecret(String consumerSecret) {
		return getProperty("sgamer.oauth.consumerSecret", consumerSecret);
	}

	public static boolean getBoolean(String name) {
		String value = getProperty(name);
		return Boolean.valueOf(value);
	}

	public static int getIntProperty(String name) {
		String value = getProperty(name);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

	public static int getIntProperty(String name, int fallbackValue) {
		String value = getProperty(name, String.valueOf(fallbackValue));
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

	public static long getLongProperty(String name) {
		String value = getProperty(name);
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

	public static String getProperty(String name) {
		return getProperty(name, null);
	}

	public static String getProperty(String name, String fallbackValue) {
		String value;
		try {
			value = System.getProperty(name, fallbackValue);
			if (null == value) {
				value = defaultProperty.getProperty(name);
			}
			if (null == value) {
				String fallback = defaultProperty.getProperty(name
						+ ".fallback");
				if (null != fallback) {
					value = System.getProperty(fallback);
				}
			}
		} catch (AccessControlException ace) {
			// Unsigned applet cannot access System properties
			value = fallbackValue;
		}
		return replace(value);
	}

	private static String replace(String value) {
		if (null == value) {
			return value;
		}
		String newValue = value;
		int openBrace = 0;
		if (-1 != (openBrace = value.indexOf("{", openBrace))) {
			int closeBrace = value.indexOf("}", openBrace);
			if (closeBrace > (openBrace + 1)) {
				String name = value.substring(openBrace + 1, closeBrace);
				if (name.length() > 0) {
					newValue = value.substring(0, openBrace)
							+ getProperty(name)
							+ value.substring(closeBrace + 1);

				}
			}
		}
		if (newValue.equals(value)) {
			return value;
		} else {
			return replace(newValue);
		}
	}

	public static int getNumberOfAsyncThreads() {
		return getIntProperty("sgamer.async.numThreads");
	}

	public static boolean getDebug() {
		return getBoolean("sgamer.debug");

	}
}
