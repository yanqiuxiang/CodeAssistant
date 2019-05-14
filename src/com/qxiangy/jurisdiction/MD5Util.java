package com.qxiangy.jurisdiction;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;


public class MD5Util {
	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr){
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++){
			int val = md5Bytes[i] & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}
	
	public static void main(String[] args) {
		
		System.out.println(convertMD5("12345"));
	}
	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */ 
	public static String convertMD5(String inStr){

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++){
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}
	
	/**
	 * 生成随机密码
	 * @param length
	 * @return
	*/
	 public static String getRandomString(int length) { 
	 StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyz"); 
		 StringBuffer sb = new StringBuffer(); 
		 Random r = new Random(); 
		 int range = buffer.length(); 
		 for (int i = 0; i < length; i ++) { 
			 sb.append(buffer.charAt(r.nextInt(range)));
		 }
		 	return sb.toString(); 
		}
	 
	//MD5 值计算
	public final  static String MD5(String s) {
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};       
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	

	public final static String MD5Code(String s)
	{
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		try
		{
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++)
			{
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static short makeShortFromByte(byte a, byte b)
	{
		short sa = a;
		short sb = b;
		if (sa < 0) sa += 256;
		if (sb < 0) sb += 256;
		return (short) (sa + (sb << 8));
	}


	public static String getSafeVerifyCode(String strValue)
	{
		byte[] buf = null;
		try
		{
			buf = strValue.getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		if (buf == null) return strValue;
		StringBuilder sb = new StringBuilder();
		long n=0;
		for (int i = 0; i < buf.length; i++)
		{
			short v = buf[i];
			if (v < 0) v += 256;// 转化成无符号
			v ^= buf.length % 256;
			v += i * 2;
			v = (short) (v % 256);
			sb.append(String.format("%02X", v));
			n+=v;
		}

		String strResult=MD5Code(sb.toString());
		
		if(n<=0) n=1;
		for(int i=0;i<n%3;i++)
			strResult=MD5Code(strResult);
		return strResult;
	}
	
	
	public static String makeDeviceCode()
	{
			long now = System.currentTimeMillis();
			BigInteger value = new BigInteger("1529000000000");
			now -= value.longValue();
			
			StringBuilder sb=new StringBuilder();
			sb.append(String.format("%d", now));
			
			while(sb.length()<15)
			{
					byte ch =(byte) ( (now+(int)(Math.random()*100))%10);
					sb.insert(0, (char)(ch+'0'));
			}
			
			
			return sb.toString();
	}
	
	public static String encoderAuthString(String password)
	{
		String str = String.format("%s,%d,%d", password, System.currentTimeMillis() / 1000, (new Date()).getTime() % 10000);

		byte[] buf = str.getBytes();
		byte ch1 = buf[buf.length - 1];
		byte ch2 = buf[buf.length - 2];

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buf.length - 2; i++)
		{
			buf[i] ^= ch1;
			buf[i] += ch2;
			sb.append(String.format("%02x", buf[i]));
		}
		sb.append(String.format("%02x", ch2));
		sb.append(String.format("%02x", ch1));
		return (sb.toString());
	}
}
