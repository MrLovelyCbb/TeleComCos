package com.telecom.cos.util;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class StringUtil {

	public static String unescapeUnicode(String str){
        StringBuffer b=new StringBuffer();
        Matcher m = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(str);
        while(m.find())
            b.append((char)Integer.parseInt(m.group(1),16));
        return b.toString();
    }
	/**
	 * 检查是否为空
	 * @param checkStr
	 * @return
	 * true 不为空
	 * false 为空
	 */
	public static boolean isValidStrings(String validStr){
		return !TextUtils.isEmpty(validStr);
	}
	/**
	 * 仅判断是否为空
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public static boolean isValidCredentials(String username, String password) {
		return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
	}
	
	public static boolean isEquals(String one,String two){
		if(one.compareToIgnoreCase(two)==0)
			return true;
		return false;
	}
	
	public static void ToastShow(Context context,String showtext){
		Toast.makeText(context, showtext, Toast.LENGTH_SHORT).show();
	}
	
	public static void ToastShow(Context context,int resid){
		Toast.makeText(context, resid, Toast.LENGTH_SHORT).show();
	}
	
	public static boolean isMobileNO(String mobiles){
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4，\\D])|(18[0，5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		System.out.println(m.matches()+"---");
		return m.matches();
		}
	
	public static String GetPHPDateFormat(String phpdate){
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String dateTime = sdf.format(Long.parseLong(phpdate+"000"));
		return dateTime;
	}
	
	public static String GetPHPDateFormatLong(String phpdate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTime = sdf.format(Long.parseLong(phpdate+"000"));
		return dateTime;
	}
}
