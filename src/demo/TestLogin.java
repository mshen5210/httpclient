package demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class TestLogin extends BaseTest{
	
	public HttpEntity loginToSystem(HttpClient httpclient,String username,String password,String vercode)
	{
		//创建httppost
		HttpPost httppost = new HttpPost(login);
		UrlEncodedFormEntity uefEntity;
		HttpResponse response;
		//创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("username",username));
		formparams.add(new BasicNameValuePair("password",password));
		formparams.add(new BasicNameValuePair("vercode",vercode));	
		try{
			uefEntity = new UrlEncodedFormEntity(formparams,encoding);
			httppost.setEntity(uefEntity);
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				return entity;
			}
		}catch (ClientProtocolException e){
			e.printStackTrace();
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
