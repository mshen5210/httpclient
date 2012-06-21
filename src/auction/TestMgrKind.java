package auction;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import test.BaseTest;

public class TestMgrKind extends BaseTest{
	/**
	 * @Desc 测试Kind List功能
	 */
	public HttpEntity kindList(HttpClient httpclient){
		//创建httppost
		HttpPost httppostclient = new HttpPost(kindList);
		HttpResponse response;
		try{
			response = httpclient.execute(httppostclient);
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
	
	/**
	 * @Desc 测试addKind功能
	 */
	public HttpEntity addKind(HttpClient httpclient,String kindname,String kinddesc,String vercode){
		//创建httppost
		HttpPost httppostclient = new HttpPost(addKind);
		UrlEncodedFormEntity uefEntityKind;
		HttpResponse response;
		//创建参数队列
		List<NameValuePair> formparamsKind = new ArrayList<NameValuePair>();
		formparamsKind.add(new BasicNameValuePair("name",kindname));
		formparamsKind.add(new BasicNameValuePair("desc",kinddesc));
		formparamsKind.add(new BasicNameValuePair("vercode",vercode));
		try{
			uefEntityKind = new UrlEncodedFormEntity(formparamsKind,encoding);
			httppostclient.setEntity(uefEntityKind);
			response = httpclient.execute(httppostclient);
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
	/**
	 * @Desc 测试updateKind功能
	 */
	public HttpEntity updateKind(HttpClient httpclient,String kindId,String kindname,String kinddesc,String vercode){
		//创建httppost
		HttpPost httppostclient = new HttpPost(updateKind);
		UrlEncodedFormEntity uefEntityKind;
		HttpResponse response;
		//创建参数队列
		List<NameValuePair> formparamsKind = new ArrayList<NameValuePair>();
		formparamsKind.add(new BasicNameValuePair("id",kindId));
		formparamsKind.add(new BasicNameValuePair("kindName",kindname));
		formparamsKind.add(new BasicNameValuePair("kindDesc",kinddesc));
		formparamsKind.add(new BasicNameValuePair("vercode",vercode));
		try{
			uefEntityKind = new UrlEncodedFormEntity(formparamsKind,encoding);
			httppostclient.setEntity(uefEntityKind);
			response = httpclient.execute(httppostclient);
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
	
	/**
	 * @Desc 测试deleteKind功能
	 */
	public HttpEntity delKind(HttpClient httpclient,String kindId){
		//创建httppost
		HttpPost httppostclient = new HttpPost(delKind);
		UrlEncodedFormEntity uefEntityKind;
		HttpResponse response;
		//创建参数队列
		List<NameValuePair> formparamsKind = new ArrayList<NameValuePair>();
		formparamsKind.add(new BasicNameValuePair("id",kindId));
		try{
			uefEntityKind = new UrlEncodedFormEntity(formparamsKind,encoding);
			httppostclient.setEntity(uefEntityKind);
			response = httpclient.execute(httppostclient);
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
