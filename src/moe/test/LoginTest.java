package moe.test;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import moe.MoeBaseTest;
import moe.httpclient.MOEHttpclient;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginTest extends MoeBaseTest{
	private HttpClient httpclient =null;
	private MOEHttpclient testLogin = null;
	
	@Before
    public void setUp(){
		httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		testLogin = new MOEHttpclient();
    }
	
	@Test
	public void testLogin()
	{
		testLogin.HttpClientGetMethod(httpclient, new LinkedHashMap<String,String>() , indexurl);
		Map<String,String> formParamofLogin = new LinkedHashMap<String,String>();
		formParamofLogin.put("userid", "s0007762i");
		formParamofLogin.put("password", "123456");
		formParamofLogin.put("hiddenAction", "valid");
		try{
			String entityLogin = testLogin.HttpClientPostMethod(httpclient, formParamofLogin, login);
			Document doc = Jsoup.parse(entityLogin);
			assertTrue(doc.text().contains("S0007762I name (Main Administrator)"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		Map<String,String> formParamofLoginOut = new LinkedHashMap<String,String>();
		formParamofLoginOut.put("hiddenAction", "logout");
		try{
			testLogin.HttpClientPostMethod(httpclient, formParamofLoginOut, login);
//			Document doc = Jsoup.parse(entityLogin);
//			System.out.println(doc.text());
//			assertTrue(doc.text().contains("For authorized use only. Unauthorized use is strictly prohibited."));
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			testLogin = null;
			httpclient.getConnectionManager().shutdown();
		}
	}
	
}
