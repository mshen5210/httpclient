package demo;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import auction.TestLogin;
import auction.TestMgrKind;

public class HttpResponseDemo {
	public static void main(String[] args) throws Exception
	{
		HttpClient httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		TestMgrKind testMgeKind = new TestMgrKind();
		TestLogin testLogin = new TestLogin();
		try{
			 testLogin.loginToSystem(httpclient, "tomcat", "tomcat", "a12345");		
			 HttpEntity entityKindList = testMgeKind.kindList(httpclient);
			 String content = EntityUtils.toString(entityKindList);
			 Document doc = Jsoup.parse(content);
			 System.out.println(doc.getElementsByTag("table").get(3).text().contains("·¿²ú"));
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
}
