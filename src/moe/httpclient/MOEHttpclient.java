package moe.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class MOEHttpclient extends MOEBaseClass{
	
	/**
	 * 
	 * @param httpclient
	 * @param formParam
	 * @param URLParam
	 * @return String
	 * @desc execute the get method's request
	 */
	public String HttpClientGetMethod(HttpClient httpclient,Map<String,String> formParam,String URLParam)
	{
		String url = URLParam;
		HttpResponse response;
		HttpEntity entity;
		
		if(formParam.size()!=0)
		{
			Set<String> keySet = formParam.keySet();
			//set value for form items
	        for (Iterator it = keySet.iterator(); it.hasNext();) {
	            String key = (String) it.next();
//	            System.out.println("key is: " + key);
	            url = url + key + formParam.get(key);
//	            System.out.println("urldd is: " + url);
	            
	        }
		}
//		System.out.println("url is: " + url);
		HttpGet httpget = new HttpGet(url);
		try{
			response = httpclient.execute(httpget);
		    if(response != null){
		    	entity = response.getEntity();
		    	if(entity != null){
					return readAndDestoryEntity(entity);
				}
		    }
		}catch (ClientProtocolException e){
			e.printStackTrace();
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			//release connection
			httpget.abort();
		}
		return null;
	}
	/**
	 * 
	 * @param httpclient
	 * @param formParam
	 * @param URLParam
	 * @return String
	 * @desc execute the post method's request, exclude file method
	 */
	
	public String HttpClientPostMethod(HttpClient httpclient,Map<String,String> formParam,String URLParam)
	{
		//response of loginAction.do
		HttpResponse response;
		HttpEntity entity;
		HttpGet httpget = null;
		HttpPost httppost = new HttpPost(URLParam);
		//set form parameters
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Set<String> keySet = formParam.keySet();
		//set value for form items
        for (Iterator it = keySet.iterator(); it.hasNext();) {
            String key = (String) it.next();
            formparams.add(new BasicNameValuePair(key,formParam.get(key)));
//            System.out.println("key is: " + key);
//            System.out.println("value is: " + formParam.get(key));
        }
		try {
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams,encoding);
			httppost.setEntity(uefEntity);
			response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
//			System.out.println("status is: " + statusCode);
			//if statusCode is redirect, execute if code, else return response's result
			if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY) ||
			    (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) ||
			    (statusCode == HttpStatus.SC_SEE_OTHER) ||
			    (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
			         String newUri = response.getLastHeader("Location").getValue();
//			         System.out.println("redirect url is: " + newUri);
			         httpget = new HttpGet(newUri);
			         HttpResponse responseRedirect = httpclient.execute(httpget);
			         if(responseRedirect != null){
					    	entity = responseRedirect.getEntity();
					    	if(entity != null){
								return readAndDestoryEntity(entity);
							}
					    }
			       }
			else{
			    	  if(response != null){
					    	entity = response.getEntity();
					    	if(entity != null){
								return readAndDestoryEntity(entity);
							}
						}
			       }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			httppost.abort();
			if(httpget != null)
			{
				httpget.abort();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param httpclient
	 * @param formParam
	 * @param URLParam
	 * @param filePath
	 * @return String
	 */
	public String DownloadFiles(HttpClient httpclient,Map<String,String> formParam,String URLParam,String filePath)
	{
		HttpResponse response;
		HttpEntity entity = null;
		UrlEncodedFormEntity uefEntity = null;
		HttpPost httppost = new HttpPost(URLParam);
		//set form parameters
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Set<String> keySet = formParam.keySet();
		//set value for form items
        for (Iterator it = keySet.iterator(); it.hasNext();) {
            String key = (String) it.next();
            formparams.add(new BasicNameValuePair(key,formParam.get(key)));
//            System.out.println("key is: " + key);
//            System.out.println("value is: " + formParam.get(key));
        }
        try{
        uefEntity = new UrlEncodedFormEntity(formparams,encoding);
		httppost.setEntity(uefEntity);
		response = httpclient.execute(httppost);
		 if(response != null){
		    	entity = response.getEntity();
		    	return(DownloadFiles(entity,filePath));
		    }
		}catch (ClientProtocolException e){
			e.printStackTrace();
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			//release connection
			httppost.abort();
		}
		return null;
	}
}


