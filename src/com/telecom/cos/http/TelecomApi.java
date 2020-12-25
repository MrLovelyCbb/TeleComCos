package com.telecom.cos.http;

import java.util.Calendar;
import java.util.List;

import com.telecom.cos.entity.Status;
import com.telecom.cos.entity.Telecom_News_Entity;
import com.telecom.cos.httpclient.HttpException;
import com.telecom.cos.httpclient.RequestParams;
import com.telecom.cos.httpclient.Response;
import com.telecom.cos.util.CrytoUtil;
import com.telecom.cos.util.StringUtil;

public class TelecomApi extends TelecomSupport {
	private static final String baseHost = Configuration.GetHostName() + "/" +"ds/";
	private String baseURL = Configuration.getScheme() +baseHost;
	private Calendar calendar = Calendar.getInstance();
	public TelecomApi() {
		super();
	}

	/**
	 * 业务逻辑
	 */



	/**
	 * 是否登录
	 * 
	 * @return
	 */
	public boolean isLoggedIn() {
		// HttpClient的userName&password是由TwitterApplication#onCreate
		// 从SharedPreferences中取出的，他们为空则表示尚未登录，因为他们只在验证
		// 账户成功后才会被储存，且注销时被清空。

		return StringUtil.isValidCredentials("", "");
	}
	
	/**
	 * URL进行加工 
	 */
	public String get(String url){
		if(url.indexOf("ajax") == -1){
			url += "&ajax="+ "1";
		}
		return url;
	}
	

	/**
	 * 注册
	 * @throws HttpException 
	 */
	public Status Api_Register(String phonenumber ,String nickname,String password,String xcode) throws HttpException {
		String timenow = String.valueOf(calendar.getTimeInMillis());
		String sign = CrytoUtil.CryptTeleComRegister(phonenumber, timenow);
		
		String url = baseURL+"member/index.php?m=Accountcheck&a=index&sign="+sign+"&time="+timenow+"&username="+phonenumber;
		System.out.println("注册接口URL"+url);
		
		RequestParams params = new RequestParams();
		
		System.out.println("nickname"+nickname);
		
		params.put("username",phonenumber);
		params.put("nickname", nickname);
		params.put("userpwd", password);
		params.put("reuserpwd",password);
		params.put("code", xcode);
		Response res = http.post(get(url), params);
		return new Status(res);
	}

	
	/**
	 * 短信验证码 
	 * http://icos.sgamer.com/index.php?
	 * m=Api&a=sendmessage&sign=62896ac226632e1364723adf97ab8d80
	 * &time=1337910200&mobile=15268808420&ajax=1
	 * @throws HttpException 
	 */
	public Status Api_VlidateNumber(String phonenumber) throws HttpException {
		String timenow = String.valueOf(calendar.getTimeInMillis());
		String sign = CrytoUtil.CryptTeleComRegister(phonenumber, timenow);
		String url = baseURL+"index.php?m=Api&a=sendmessage&sign="+sign+"&time="+timenow+"&username="+phonenumber;
		System.out.println("验证码URL"+url); 
		Response res = http.get(get(url));
		return new Status(res);
	}

	/**
	 * 登录
	 * @throws HttpException 
	 */
	public Status Api_Login(String phonenumber,String password) throws HttpException {
		String timenow = String.valueOf(calendar.getTimeInMillis());
		String sign = CrytoUtil.CryptTeleComLogin(phonenumber, timenow);
		String url = baseURL+"member/index.php?m=Accountcheck&a=login&sign="+sign+"&time="+timenow+"&username="+phonenumber+"&ajax=1";
		System.out.println("登录URL"+url);
		
		RequestParams params = new RequestParams();
		params.put("username", phonenumber);
		params.put("userpwd", password);
		
		Response res = http.post(url, params);
//		System.out.println(res.asString());
		return new Status(res);
	}
	
	/**
	 * 获取新闻列表
	 * @param type =3  视频    no cid
	 * @param type =2  图片     cid=1   漫画cid = 2
	 * @param type =1  新闻   no cid
	 * http://icos.sgamer.com/ds/index.php?ajax=1&m=Api&a=getlist&type=1&cid=1&limit=10
	 * @throws HttpException 
	 */
	public Telecom_News_Entity Api_GetNewsList(int type,int cid,int limit) throws HttpException{
		String url = baseURL+"index.php?m=Api&a=getlist&type="+type+"&cid="+cid+"&limit"+limit;
		System.out.println("新闻列表URL"+url);
		Response res = http.get(get(url));
		Status status = new Status(res);
//		System.out.println(res.asString());
//		System.out.println(status.getInfo());
		return new Telecom_News_Entity(res, status);
	}
	
	
	/**
	 * 获取新闻详细信息
	 * @throws HttpException 
	 */
	public void Api_GetNewsDetailsList(String news_html) throws HttpException{
		String url = Configuration.getScheme()+Configuration.GetHostName()+news_html;
		System.out.println("新闻详细URL"+url);
		http.get(url);
	}
	
	/**
	 * 获取新闻评论
	 * @throws HttpException 
	 */
	public void Api_GetNewsComments(String rootid) throws HttpException{
		String pages = "999999";
		String url = baseURL+"index.php?m=Comment&a=loadcomments&rootid"+rootid+"&p="+pages;
		System.out.println("新闻评论URL"+url);
		http.get(url);
	}
	
	/**
	 * 提交评论
	 * @throws HttpException 
	 */
	public void Api_GetNewsSubmitComments(String rootid,String sendbody,String phonenumber) throws HttpException{
		String timenow = String.valueOf(calendar.getTimeInMillis());
		String sign = CrytoUtil.CryptTeleComLogin(phonenumber, timenow);
		String url = baseURL+"index.php?m=Comment&a=javacreatecomment&rootid="+rootid;
		System.out.println("新闻评论提交URL"+url);
		RequestParams params = new RequestParams();
		params.put("username", phonenumber);
		params.put("body", sendbody);
		params.put("time", timenow);
		params.put("sign", sign);
		
		http.post(get(url), params);
	}
	
	/**
	 * 获取主页
	 * cos
	 * 漫画
	 * 优秀作品
	 * http://icos.sgamer.com/ds/index.php?ajax=1&m=Api&a=getallist
	 * @throws HttpException 
	 */
	public void Api_GetIndexImages() throws HttpException{
		String url = baseURL+"index.php&m=Api&a=getallist";
		System.out.println("获取主页图片数据URL"+url);
		
		http.get(get(url));
	}

}
