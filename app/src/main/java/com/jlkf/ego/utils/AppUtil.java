package com.jlkf.ego.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.jlkf.ego.R;
import com.jlkf.ego.application.ImageApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 工具类
 * @author dcg
 *
 */
public class AppUtil {
	private static SharedPreferencesUtil sharedPreferencesUtil;
	public static String SUCCESS_ISDOAMIN="OK";
	public static  String TIME_FORMAT="yyyy/MM/dd";

	/**
	 * 通过universal展示图片
	 *
	 * @param url:图片的url
	 * @param iv:Image的控件
	 * @param tag:标志（不同的值，给的加载中和加载失败的图片不一样）0:轮播图，1:普通图，2:头像
	 */
	public static void showImgByLoader(String url, ImageView iv, int tag) {
//		ImageApplication.initImageLoader(ImageApplication.getAppContext());
		// 将图片用universal展示出来
		if (tag == 0) {
			ImageApplication.imageLoader.displayImage(url, iv,
					ImageApplication.options, null);
		} else if(tag ==1){
			ImageApplication.imageLoader.displayImage(url, iv,
					ImageApplication.options1, null);
		}else if(tag ==2){
			ImageApplication.imageLoader.displayImage(url, iv,
					ImageApplication.options2, null);
		}else if(tag ==3){
			ImageApplication.imageLoader.displayImage(url, iv,
					ImageApplication.options3, null);
		}else if(tag ==4){
			ImageApplication.imageLoader.displayImage(url, iv,
					ImageApplication.options4, null);
		}
	}

	/**
	 * 获取刷新时间
	 */
	public static String onLoadTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
				Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	/**
	 * 解决判断某个字符串是否为json数据格式
	 * @param json
	 * @return
	 */
	public static boolean isGoodJson(String json) {
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			AppLog.Loge("mycrash","bad json: " + json);
			return false;
		}
	}
	
	//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）  
		// 身份证的一个正则表达式：\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])"
		public static boolean isIdNumMatcher (String idNum) {
			Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");  
	        //通过Pattern获得Matcher  
	        Matcher idNumMatcher = idNumPattern.matcher(idNum);  
			return idNumMatcher.matches();
		}
		
		//验证护照  	表达式  ^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$
		public static boolean isPassportMatcher (String Passport) {
			Pattern idPassportPattern = Pattern.compile("^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$");  
	        //通过Pattern获得Matcher
	        Matcher idPassportMatcher = idPassportPattern.matcher(Passport);  
			return idPassportMatcher.matches();
		}
		
		//港澳通行证验证  	表达式  ^[HMhm]{1}([0-9]{10}|[0-9]{8})$
		public static boolean isGAPassMatcher (String Passport) {
			Pattern idPassportPattern = Pattern.compile("^[HMhm]{1}([0-9]{10}|[0-9]{8})$");  
	        //通过Pattern获得Matcher
	        Matcher idPassportMatcher = idPassportPattern.matcher(Passport);  
			return idPassportMatcher.matches();
		}
		
		//验证邮政编码格式  	表达式  ^[1-9]\d{5}$
		public static boolean isZipCodeMatcher (String Passport) {
			Pattern zipCodePattern = Pattern.compile("^[1-9]\\d{5}$");  
	        //通过Pattern获得Matcher
	        Matcher idZipCodeMatcher = zipCodePattern.matcher(Passport);  
			return idZipCodeMatcher.matches();
		}
		
		//验证邮箱格式  	表达式  ^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$
		public static boolean isEmailMatcher (String Passport) {
			Pattern idPassportPattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");  
	        //通过Pattern获得Matcher
	        Matcher idEmailMatcher = idPassportPattern.matcher(Passport);  
			return idEmailMatcher.matches();
		}
		//验证域名前缀格式  	使用ascii比较
		public static boolean isNormalMatcher (String str) {
			boolean flag=false;
			for (int i = 0; i < str.length(); i++) {
				int code=(int)str.charAt(i);
				if(code>128 ||  code==45 || (code>=48 && code<=57) || (code>=65 && code<=90)  || (code>=97 && code<=122)){
					flag=true;
				}else{
					  AppLog.Loge("前缀出错的字符"+str.charAt(i));
					  return false;
				}
			}
			return flag;
		}
		
		//台湾通行证验证  	表达式  ^[0-9]{8}$  或 ^[0-9]{10}$
		public static boolean isTWPassMatcher (String Passport) {
			Pattern idPassportPatternA = Pattern.compile("^[0-9]{8}$");  
			Pattern idPassportPatternB = Pattern.compile("^[0-9]{10}$");  
	        //通过Pattern获得Matcher
	        Matcher idPassportMatcherA = idPassportPatternA.matcher(Passport);  
	        Matcher idPassportMatcherB = idPassportPatternB.matcher(Passport);  
			return idPassportMatcherA.matches() || idPassportMatcherB.matches();
		}
	
	/**
	 * 判定用户是否登录
	 * @return
	 */
	public static boolean IsLogin(Context context){
		sharedPreferencesUtil = new SharedPreferencesUtil(context, "user");
		String uId=(String) sharedPreferencesUtil.getSPValue("userId", " ");
		String token=(String) sharedPreferencesUtil.getSPValue("token", " ");
		if(uId.trim().length()!=0 && token.trim().length()!=0){ //id不为空，密码不为空
			return true;
		}
		return false;
	}
	/**
	 * 获取登录用户uid
	 * @param context
	 * @return
	 */
	public static String getUid(Context context){
		if(IsLogin(context)){
			sharedPreferencesUtil = new SharedPreferencesUtil(context, "user");
			String uId=(String) sharedPreferencesUtil.getSPValue("userId", "");
			return uId;
		}else{
//			Toast.makeText(context, "大哥先登录，好吗~~", Toast.LENGTH_SHORT).show();
			return "";
		}
	}
	/**
	 * 获取资源字符串
	 * @param context
	 * @return
	 */
	public static String getResString(Context context,int id){
		return context.getResources().getString(id);
	}
	/**
	 * 获取登录用户账号
	 * @param context
	 * @return
	 */
	public static String getMobile(Context context){
		if(IsLogin(context)){
			sharedPreferencesUtil = new SharedPreferencesUtil(context, "user");
			String mobile=(String) sharedPreferencesUtil.getSPValue("mobile", "");
			return mobile;
		}else{
			return "";
		}
	}
	/**
	 * 获取项目名称
	 * @param context
	 * @return
	 */
	public static String getAppName(Context context){
		PackageManager pm = context.getPackageManager();
		return  context.getApplicationInfo().loadLabel(pm).toString();
	}
	
	
	/**
	 * 将时间字符串“2016-05-09”转换成“5月9日”
	 * @param time
	 * @return
	 */
	public static String getTranFormatTime(String time){
		String[] dealTime=time.split("-");
		//先将字符串转为“2016.05.09”
		String dateString = dealTime[0]+"."+dealTime[1]+"."+dealTime[2];
	    SimpleDateFormat sdfS2D = new SimpleDateFormat("yyyy.MM.dd");
	    SimpleDateFormat sdfD2S = new SimpleDateFormat("yyyy.M.d");
	    try {
			String date=sdfD2S.format(sdfS2D.parse(dateString)).trim().toString();
			String[] str=date.split("\\.");
			return str[1]+"月"+str[2]+"日";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	/**
	 * 将时间秒数转换为时间格式131425648  2016/10/2
	 * @param longTime
	 * @return
	 */
	public static String getTranFormatLongTime(String longTime){
		String time="";
		try {
			Long timeLong = Long.parseLong(longTime)*1000;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date = sdf.parse(sdf.format(timeLong)); //你要的日期格式
			time=sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time; 
	}
	/**
	 * 将时间秒数转换为时间格式131425648  2016-10-2
	 * @param longTime
	 * @return
	 */
	public static String getTranFormatLongTimeWithdip(String longTime){
		String time="";
		try {
			Long timeLong = Long.parseLong(longTime)*1000;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			Date date = sdf.parse(sdf.format(timeLong)); //你要的日期格式
			time=sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time; 
	}
	/**
	 * 将时间秒数转换为时间格式131425648  2016-10-2
	 * @param longTime
	 * @return
	 */
	public static String getTranFormatLongTimeAll(String longTime){
		String time="";
		try {
			Long timeLong = Long.parseLong(longTime)*1000;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = sdf.parse(sdf.format(timeLong)); //你要的日期格式
			time=sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time; 
	}
	/**
	 * 将时间毫秒数转换为时间格式131425648  HH:mm:ss
	 * @param timeLong
	 * @return
	 */
	public static String getDaoTime(long timeLong){
		String time="";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date date = sdf.parse(sdf.format(timeLong)); //你要的日期格式
			time=sdf.format(date);
			if(time.split(":")[0].equals("00")){
				time=time.split(":")[1]+":"+time.split(":")[2];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time; 
	}
	
	public static String format(long ms) {//将毫秒数换算成x天x时x分x秒x毫秒
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
//		return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " " + strMilliSecond;
		String time=strHour + ":" + strMinute + ":" + strSecond;
		if(strHour.equals("00")){
			time=strMinute+":"+strMinute;
		}
		return  time;
		}
	
	public static String warmmingFormat(long ms) {//将毫秒数换算成x天x时x分x秒x毫秒
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
		return strDay + "天 " + strHour + ":" + strMinute + ":" + strSecond;
		}
	
	public static int warmmingFormatDay(long ms) {//将毫秒数换算成x天x时x分x秒x毫秒
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;
		int day = (int) (ms / dd);
		return day;
		}
	
	/**
	 * 获取本机ip
	 * @return
	 */
	public static String getMyIp(Context context){
		//获取wifi服务  
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        //判断wifi是否开启  
        if (!wifiManager.isWifiEnabled()) {  
        wifiManager.setWifiEnabled(true);    
        }  
        WifiInfo wifiInfo = wifiManager.getConnectionInfo(); 
        String mac=wifiInfo.getMacAddress();
        int ipAddress = wifiInfo.getIpAddress();   
        String ip = intToIp(ipAddress);
        AppLog.Loge("dcg", "wifi下的ip:"+ip);
        if(ip!=null && !ip.equals("")){
        	return ip;
        }else{
        	AppLog.Loge("dcg", "本地的ip:"+ip);
        	return getLocalIpAddress();
        }
        
        }
	
	public static String getLocalIpAddress()  
    {  
        try  
        {  
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)  
            {  
               NetworkInterface intf = en.nextElement();  
               for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)  
               {  
                   InetAddress inetAddress = enumIpAddr.nextElement();  
                   if (!inetAddress.isLoopbackAddress())  
                   {  
                       return inetAddress.getHostAddress().toString();  
                   }  
               }  
           }  
        }  
        catch (SocketException ex)  
        {  
            AppLog.Loge("WifiPreference IpAddress", ex.toString());  
        }  
        return null;  
    }
	
	
	 private static String intToIp(int i) {       
         
         return (i & 0xFF ) + "." +       
       ((i >> 8 ) & 0xFF) + "." +       
       ((i >> 16 ) & 0xFF) + "." +       
       ( i >> 24 & 0xFF) ;  
    }   
	/**
	 * 随机数函数生成
	 * @return
	 */
	public static String getTradeNo(int n){
		StringBuffer array = new StringBuffer();
		for (int i = 0; i <= 9; i++) {
			array.append(i);
		}
		for (int i = (int) 'a'; i <= (int) 'z'; i++) {
			array.append((char) i);
		}
		for (int i = (int) 'A'; i <= (int) 'Z'; i++) {
			array.append((char) i);
		}
		int length = array.length();
		
		// 存储最后生成的字符串
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < n; i++) {
			// 获得随机位置的字符
			char c = array.charAt((int) (Math.random() * length));
			str.append(c);
		}
		
		AppLog.Loge("dcg", "生成的随机数为："+str.toString());
		return str.toString();
	}
	
	/**
	 * 处理金额显示问题，例如1000.0显示为1000；将1000.030显示为1000.03。
	 * 改，所有相关金额的地方都要显示两位小数。
	 * @param money
	 * @return
	 */
	 public static String dealDoubleMoney(String money) {
		 //所有相关金额的地方都要显示两位小数。
		 return dealWithMoney(Double.parseDouble(money));
		 /*
		 if(!money.contains(".")){
			 return money;
		 }else{
			 int dop=money.lastIndexOf(".");//最后出现小数点.的位置
			 int pi=0;
			 int end=0;
			 //判断最后一个不是"0"，也不是"."出现的位置
			 for(int i=0;i<money.length();i++){
				 if(money.charAt(i)!='0' && money.charAt(i)!='.'){
					 pi=i;
				 }
			}
			 //出现在小数点之前	 
			 if(dop>pi){
				 end=dop;
			 }else{
				 end=pi+1;
			 }
			return  money.substring(0, end);    
		 }
   */
		 } 
		/**
		 * 处理金额显示问题，例如1000.0显示为1000；将1000.030显示为1000.03。
		 * 改，所有相关金额的地方都要显示两位小数。
		 * @param money
		 * @return
		 */
		 public static String dealDoubleMoneyTure(String money) {
			 if(!money.contains(".")){
				 return money;
			 }else{
				 int dop=money.lastIndexOf(".");//最后出现小数点.的位置
				 int pi=0;
				 int end=0;
				 //判断最后一个不是"0"，也不是"."出现的位置
				 for(int i=0;i<money.length();i++){
					 if(money.charAt(i)!='0' && money.charAt(i)!='.'){
						 pi=i;
					 }
				}
				 //出现在小数点之前	 
				 if(dop>pi){
					 end=dop;
				 }else{
					 end=pi+1;
				 }
				return  money.substring(0, end);    
			 }
			 } 
	    /** 
	     * 获取当月的 天数 
	     * */  
	    public static int getCurrentMonthDay() {  
	          
	        Calendar a = Calendar.getInstance();  
	        a.set(Calendar.DATE, 1);  
	        a.roll(Calendar.DATE, -1);  
	        int maxDate = a.get(Calendar.DATE);  
	        return maxDate;  
	    }  
	    
	 /** 
	     * 根据年 月 获取对应的月份 天数 
	     * */  
	    public static int getDaysByYearMonth(int year, int month) {  
	          
	        Calendar a = Calendar.getInstance();  
	        a.set(Calendar.YEAR, year);  
	        a.set(Calendar.MONTH, month - 1);  
	        a.set(Calendar.DATE, 1);  
	        a.roll(Calendar.DATE, -1);  
	        int maxDate = a.get(Calendar.DATE);  
	        return maxDate;  
	    }  
	    
	    /** 
	     * 根据日期 找到对应日期的 星期  返回中文
	     */  
	    public static String getDayOfWeekByDate(String date) {  
	        String dayOfweek = "-1";  
	        try {  
	            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");  
	            Date myDate = myFormatter.parse(date);  
	            SimpleDateFormat formatter = new SimpleDateFormat("E");  
	            String str = formatter.format(myDate);  
	            dayOfweek = str;  
	              
	            AppLog.Loge("dcg", "星期几："+dayOfweek);
	        } catch (Exception e) {  
	            System.out.println("错误!");  
	        }  
	        return dayOfweek;  
	    }


	/**
	     * 根据日期 找到对应日期的 星期  返回int
	     */  
	    public static int getIntDayOfWeekByDate(String date) {  
	        int dayOfweek = 0;  
	        try {  
	            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");  
	            Date myDate = myFormatter.parse(date);
	            Calendar now = Calendar.getInstance();
	            now.setTime(myDate);
	            dayOfweek = now.get(Calendar.DAY_OF_WEEK);
	              
	            AppLog.Loge("dcg", "星期几："+dayOfweek+"");
	        } catch (Exception e) {  
	            System.out.println("错误!");  
	        }  
	        return dayOfweek-1;  
	    }

	/**
		 * 处理两位字符，用作与时间处理  05转为5 10还是10
		 * @param str
		 * @return
		 */
		public static int dealWithIntShow(String str){
			int data=0;
			try {
				if(str.charAt(0)=='0'){
					data=Integer.parseInt(str.charAt(1)+"");
				}else{
					data=Integer.parseInt(str);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return data;
		}
		//判断字符是否为空或者没数据
		public static boolean IsNullString(String str){
			if(!TextUtils.isEmpty(str) && !TextUtils.equals("", str.trim())){
				return false;
			}else{
				return true;
			}
		}
	/**
	 * 验证手否是手机号?
	 *
	 * @param phoneNum
	 *            手机号码
	 * @return
	 */
	public static boolean isPhoneNumber(String phoneNum) {
		// 验证手机的正则表达式
		String str = "^[1][3,4,5,7,8][0-9]{9}$";
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(phoneNum);
		return matcher.matches();
	}
	/**
	 * 判断是否是6-20位的数字和字母
	 *
	 * @param passward
	 * @return
	 */
	public static boolean isPassword(String passward) {
//		String str = "^[0-9a-zA-Z]{6,20}$";//数字或字母
		String str = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";//数字和字母
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(passward);
		return matcher.matches();
	}
		
		//统计一个字符，在一个长字符串中出现的次数
		public static int getCountCharStr(String charStr,String str){
			int x=0;  
	          //遍历长字符串的每个元素    
	          for(int i=0;i<=str.length()-1;i++) {
	              if(TextUtils.equals(str.charAt(i)+"",charStr)){
	                  x++;
	              }  
	          }
	        	  return x;  
		}
		
		//double保留两位小数
		public static String dealWithMoney(double str){
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");  
			return df.format(str);
		}
		
		//判断字符是number
		public static boolean isNumber(String value) {
			 try {
				   Integer.parseInt(value);
				   return true;
				  } catch (NumberFormatException e) {
				   return false;
				  }
		}	
		/**
		 * 网络连接失败String
		 * @param mContext
		 * @return
		 */
		public static String networkError(Context mContext) {
			return mContext.getResources().getString(R.string.string_network_dis);
		}
		
		/**
		 * 将文件流转字符
		 * @param inputStreamReader
		 * @return
		 */
		public static String ConvertToString(InputStreamReader inputStreamReader){  
	        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
	        StringBuilder result = new StringBuilder();  
	        String line = null;  
	        try {  
	            while((line = bufferedReader.readLine()) != null){  
	                result.append(line + "\n");  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try{  
	                inputStreamReader.close();  
	                bufferedReader.close();  
	            }catch(IOException e){  
	                e.printStackTrace();  
	            }  
	        }  
	        return result.toString();  
	    }  
		
		/**
		 * 将字节流转字符
		 * @param inputStream
		 * @return
		 */
	    public static String ConvertToString(InputStream inputStream){  
	        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);  
	        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
	        StringBuilder result = new StringBuilder();  
	        String line = null;  
	        try {  
	            while(!(line = bufferedReader.readLine()).equals("")){  
	                result.append(line + "\n");  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try{  
	                inputStreamReader.close();  
	                inputStream.close();  
	                bufferedReader.close();  
	            }catch(IOException e){  
	                e.printStackTrace();  
	            }  
	        }  
	        return result.toString();  
	    }  
	    
	    /**
	     * 发送message
	     */
	    public static void sendMessage(int what,Object obj,Handler mHandler){
	    	Message msg=new Message();
	    	msg.what=what;
	    	if(obj!=null){
	    		msg.obj=obj;
	    	}
	    	mHandler.sendMessage(msg);
	    } 
		public static String saveBitMapToFile(Context context, String fileName, Bitmap bitmap, boolean isCover) {
		    if(null == context || null == bitmap) {
		        return null;
		    }
		    if(TextUtils.isEmpty(fileName)) {
		        return null;
		    }
		    FileOutputStream fOut = null;
		    try {
		        File file = null;
		        String fileDstPath = "";
		        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
		            // 保存到sd卡
		            fileDstPath = Environment.getExternalStorageDirectory().getAbsolutePath()
		                    + File.separator + "DomainStar" + File.separator + fileName;

		            File homeDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
		                    + File.separator + "DomainStar" + File.separator);
		            if (!homeDir.exists()) {
		                homeDir.mkdirs();
		            }
		        } else {
		            // 保存到file目录
		            fileDstPath = context.getFilesDir().getAbsolutePath()
		                    + File.separator + "DomainStar" + File.separator + fileName;

		            File homeDir = new File(context.getFilesDir().getAbsolutePath()
		                    + File.separator + "DomainStar" + File.separator);
		            if (!homeDir.exists()) {
		                homeDir.mkdir();
		            }
		        }

		        file = new File(fileDstPath);

		        if (!file.exists() || isCover) {
		            // 简单起见，先删除老文件，不管它是否存在。
		            file.delete();

		            fOut = new FileOutputStream(file);
		            if (fileName.endsWith(".jpg")) {
		                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fOut);
		            } else {
		                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		            }
		            fOut.flush();
		            bitmap.recycle();
		        }

		        AppLog.Logi("FileSave", "saveDrawableToFile " + fileName
		                + " success, save path is " + fileDstPath);
		        return fileDstPath;
		    } catch (Exception e) {
		        AppLog.Loge("FileSave", "saveDrawableToFile: " + fileName + " , error" + e);
		        return null;
		    } finally {
		        if(null != fOut) {
		            try {
		                fOut.close();
		            } catch (Exception e) {
		                AppLog.Loge("FileSave", "saveDrawableToFile, close error"+ e);
		            }
		        }
		    }
		}
		
		//版本名
		public static String getVersionName(Context context) {
			return getPackageInfo(context).versionName;
		}
		//版本号
		public static int getVersionCode(Context context) {
			return getPackageInfo(context).versionCode;
		}
		private static PackageInfo getPackageInfo(Context context) {
			PackageInfo pi = null;

			try {
				PackageManager pm = context.getPackageManager();
				pi = pm.getPackageInfo(context.getPackageName(),
						PackageManager.GET_CONFIGURATIONS);

				return pi;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return pi;
		}
		
		/**
		 * 自定义生成UUID
		 * 使用 String.hashCode() 加密序列号
		 * @param context
		 * @return
		 */
		public static String getMyUUID(Context context) {
			 TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		     String tmDevice, tmSerial, tmPhone, androidId;
		    tmDevice = "" + tm.getDeviceId();
		    tmSerial = "" + tm.getSimSerialNumber();
		    androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

		    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
		    String uniqueId = deviceUuid.toString();
		    return uniqueId;
		}
		
		public static String getNowTimeString() {
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置日期格式
			return df.format(new Date());// new Date()为获取当前系统时间
		}
		
		/**
		 * 将View视图转为bitmap
		 * @param view
		 * @return
		 */
		public static Bitmap convertViewToBitmap(View view){
				view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		        view.buildDrawingCache();
		        Bitmap bitmap = view.getDrawingCache();

		        return bitmap;
		}
		
		//获取最大值
		public static long getLongMax(long[] arr){
			int max=0;
			for (int i = 1; i < arr.length; i++) {
				if(arr[i]>arr[max])
					max=i;
			}
			return arr[max];
		}
		
		//获取最小值
		public static long getLongMin(long[] arr){
			int min=0;
			for (int i = 1; i < arr.length; i++) {
				if(arr[i]<arr[min])
					min=i;
			}
			return arr[min];
		}

/**
 *  输入框限制输入Emjoy表情
 * @param et
 */
	public static void filterEmjoyEditText(EditText et) {
		InputFilter emojiFilter = new InputFilter() {
			Pattern emoji = Pattern.compile(
							"[\ud83c\udc00 - \udfff]|[\ud83d\udc00 - \ud83d\udfff]|[\u2600-\u27ff]",
							Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				Matcher emojiMatcher = emoji.matcher(source);
				if (emojiMatcher.find()) {
					return "";
				}
				return null;
			}
		};
		et.setFilters(new InputFilter[] { emojiFilter });
	}

	/**
	 *  输入框限制输入Emjoy表情
	 * @param et
	 */
	public static void filterEmjoyEditText2(int end ,EditText et) {
		InputFilter emojiFilter = new InputFilter() {
			Pattern emoji = Pattern.compile(
					"[\ud83c\udc00 - \udfff]|[\ud83d\udc00 - \ud83d\udfff]|[\u2600-\u27ff]",
					Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
									   Spanned dest, int dstart, int dend) {
				Matcher emojiMatcher = emoji.matcher(source);
				if (emojiMatcher.find()) {
					return "";
				}
				return null;
			}
		};
		et.setFilters(new InputFilter[] {new InputFilter.LengthFilter(end), emojiFilter });
	}

	/**
	 * 根据图片的url路径获得Bitmap对象
	 * @param url
	 * @return
	 */
	public static  Bitmap returnBitmap(String url) {
		URL fileUrl = null;
		Bitmap bitmap = null;

		try {
			fileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			HttpURLConnection conn = (HttpURLConnection) fileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;

	}
	/**
	 * 半隐藏手机号码
	 * @param phone
	 * @return
	 */
	public static  String privatePhone(String phone){
		if(!IsNullString(phone)){
			if(phone.length()>=11){
				return phone.substring(0,3)+"※※※※"+phone.substring(7, 11);
			}else{
				return phone;
			}
		}
		return "";
	}
	/**
	 * 全隐藏手机号码
	 * @param phone
	 * @return
	 */
	public static  String privateAllPhone(String phone){
		if(!IsNullString(phone)){
			if(phone.length()>=11){
				return "※※※※※※※※※※※";
			}else{
				StringBuffer sb=new StringBuffer();
				for (int i = 0; i < phone.length(); i++) {
					sb.append("※");
				}
				return sb.toString();
			}
		}
		return "";
	}
	
	public static boolean isSDcardExist() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	public static File getDiskCacheDir(Context context, String uniqueName) {
		final String cachePath = isSDcardExist() ? getExternalCacheDir(context).getPath() : context.getCacheDir().getPath();
		return new File(cachePath + File.separator + uniqueName);
	}
	public static File getExternalCacheDir(Context context) {
		String pathName = "/Domainstar/apk/";
		String fileName = "";
		creatDir(context, pathName);
		
		StringBuffer sb=new StringBuffer();
		if(isExternalStorageWritable()){
			sb.append(Environment.getExternalStorageDirectory().getPath() +pathName+fileName);
		}else{
			sb.append(context.getFilesDir() +pathName+fileName);
		}
		return new File(sb.toString());
	}
    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable(){
        String state =Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            //sd卡已经挂载，可以进行读写操作了
            return true;
        }
        //sd未挂载，在此进行提示
        return false;
    }
    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable(){
        String state =Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }
    /**
     * 创建新目录
     * @param mContext
     * @param pathName
     * @return
     */
    public static void creatDir(Context mContext,String pathName) {
        //1.判断是否存在sdcard
        String _file="";
        if(isExternalStorageWritable()){//外挂SD卡存在时,保存到sd卡中
            _file=Environment.getExternalStorageDirectory().getPath() + pathName;
        }else{//外挂SD卡不存在时,保存到手机内存中
            _file=mContext.getFilesDir()  + pathName;
        }
        File filePath = new File(_file);
        if (!filePath.exists()) {  //如果文件夹不存在，创建文件夹
            filePath.mkdirs();
        }
    }
	/**
	 * 获取app当前的渠道号或application中指定的meta-mData
	 *
	 * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
	 */
	public static String getAppMetaData(Context context, String key) {
	    if (context == null || TextUtils.isEmpty(key)) {
	        return null;
	    }
	    String channelNumber = "";
	    try {
	        PackageManager packageManager = context.getPackageManager();
	        if (packageManager != null) {
	            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
	            if (applicationInfo != null) {
	                if (applicationInfo.metaData != null) {
	                    channelNumber = applicationInfo.metaData.getString(key);
	                }
	            }
	        }
	    } catch (PackageManager.NameNotFoundException e) {
	        e.printStackTrace();
	    }
	    return channelNumber;
	}
    /**
     * 空判断
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (null == obj)
            return true;
        else if (obj == "")
            return true;
        else if (obj instanceof Integer || obj instanceof Long
                || obj instanceof Double) {
            // if((Integer.parseInt(obj.toString()))==0) return bool;
            try {
                Double.parseDouble(obj + "");
            } catch (Exception e) {
                return true;
            }
        } else if (obj instanceof String) {
            if (((String) obj).length() <= 0)
                return true;
            if ("null".equals(obj))
                return true;
        } else if (obj instanceof Map) {
            if (((Map) obj).size() == 0)
                return true;
        } else if (obj instanceof Collection) {
            if (((Collection) obj).size() == 0)
                return true;
        }
        return false;
    }
}
