package com.qxiangy.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class UrlRequest {
	
	private static UrlRequest instance = null;
	
	private UrlRequest(){}
	
	public static UrlRequest getInstance(){
		
		if(null==instance){
			
			instance = new UrlRequest();
		}
		
		return instance;
	}
	
	
	/* ��ָ�� URL ����POST����������
	 * @param url
	 * ��������� URL
	 * @param param
	 * ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	 * @return ������Զ����Դ����Ӧ���
	 */
	public static String sendPost(String url, String param)
	{
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try
		{
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			conn.setConnectTimeout(10 * 1000);// ���ó�ʱ
			// ����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// ��ȡURLConnection�����Ӧ�������������utf-8����
	        out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			// �����������
			out.print(param);
			// flush������Ļ���
			out.flush();
			// ����BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null)
			{
				result += line;
			}
		}
		catch (Exception e)
		{
			System.out.println("���� POST ��������쳣��" + e);
			e.printStackTrace();
			return "";
		}
		// ʹ��finally�����ر��������������
		finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
				if (in != null)
				{
					in.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
				return "";
			}
		}
		
		return result;
	}
}
