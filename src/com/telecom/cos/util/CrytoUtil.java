package com.telecom.cos.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
/**
 * MD5,SHA1等加密类
 * @author MrLovelyCbb
 *
 * @since 修改解密MD5 http://happymen001.iteye.com/blog/576388
 */
public class CrytoUtil {
	private static final String default_charset = "UTF-8";
	private static final String MD5_Keyword = "MD5";
	public static final String TeleCom_Login = "telecom_log";
	public static final String Telecom_register = "telecom_reg";

	// private static final String SHA_1_Keyword = "SHA-1";
	
	/**
	 * 注册-效验码
	 */
	public static String CryptTeleComRegister(String phonenumber,String timenow){
//		Calendar calendar = Calendar.getInstance();
//		String timenow = String.valueOf(calendar.getTimeInMillis());
		String cryptStr = Telecom_register + timenow + phonenumber;
		return cryptMD5ToHex(cryptStr);
	}
	
	/**
	 * 登录-效验码
	 */
	public static String CryptTeleComLogin(String phonenumber,String timenow){
//		Calendar calendar = Calendar.getInstance();
//		String timenow = String.valueOf(calendar.getTimeInMillis());
		String cryptStr = TeleCom_Login + timenow + phonenumber;
		return cryptMD5ToHex(cryptStr);
	}
	
	
	
	/**
	 * 简单MD5加密
	 * @param str  加密内容(默认字符集UTF-8)
	 * @return
	 */
	public static String cryptMD5ToHex(String str) {
		return cryptMD5ToHEX(str, default_charset);
	}
	/**
	 * 简单MD5加密
	 * @param str   加密内容
	 * @param charset  加密字符集
	 * @return
	 */
	public static String cryptMD5ToHEX(String str, String charset) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return hex(md.digest(str.getBytes(charset)));
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 标准MD5加密
	 * @param str   加密内容(默认字符集UTF-8)
	 * @return
	 */
	public static String StandardMD5ToHex(String str) {
		return StandardMD5ToHex(str, default_charset);
	}
	/**
	 * 标准MD5加密
	 * @param str   加密内容
	 * @param charset 加密编码集
	 * @return
	 */
	public static String StandardMD5ToHex(String str, String charset) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return bytes2Hex(md.digest(str.getBytes(charset)));
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 高级MD5加密
	 * @param str  加密内容
	 * @return
	 */
	public static String AdvancedMD5ToString(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] b = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	/**
	 * 通用加密算法
	 * @param strSrc    加密内容(默认为MD5)
	 * @return
	 */
	public static String Encrypt(String strSrc) {
		return Encrypt(strSrc, MD5_Keyword);
	}
	/**
	 * 通用加密方法
	 * @param strSrc    加密内容
	 * @param encName   加密关键字
	 * @return
	 */
	public static String Encrypt(String strSrc, String encName) {
		if (encName == null || encName.equals("")) {
			encName = "MD5";
		}
		try {
			MessageDigest md = MessageDigest.getInstance(encName);
			return hex(md.digest(strSrc.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * byte数组转Ascii
	 * @param bySourceByte  Byte[]数组
	 * @return
	 */
	public static String binToAscii(byte[] bySourceByte) {
		String result = new String();
		int len = bySourceByte.length;
		for (int i = 0; i < len; i++) {
			byte tb = bySourceByte[i];

			char tmp = (char) (tb >>> 4 & 0xF);
			char high;
			if (tmp >= '\n')
				high = (char) ('a' + tmp - 10);
			else
				high = (char) ('0' + tmp);
			result = result + high;
			tmp = (char) (tb & 0xF);
			char low;
			if (tmp >= '\n')
				low = (char) ('a' + tmp - 10);
			else {
				low = (char) ('0' + tmp);
			}
			result = result + low;
		}
		return result;
	}
	
	public static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(Integer.toHexString(array[i] & 0xFF | 0x100).substring(1,
					3));
		}

		return sb.toString();
	}

	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;

		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
}
