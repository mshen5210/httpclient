/*
* Created on 2003-12-14 by mshen
*/

package test;
import static org.junit.Assert.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import auction.*;


/** 
  *@author mshen
*/
public class SimpleTest extends BaseTest{
	private HttpClient httpclient;
	private String kindId = "10";
	@Before
    public void setUp() throws Exception {
		//从配置文件中获取kindDao实例
		httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		TestLogin testLogin = new TestLogin();
		testLogin.loginToSystem(httpclient, "tomcat", "tomcat", "a12345");		
    }
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void TestLogin() 
	{
		TestLogin testLogin = new TestLogin();
		try{
			HttpEntity entityLogin = testLogin.loginToSystem(httpclient, "tomcat", "tomcat", "a12345");
			String content = EntityUtils.toString(entityLogin,encoding);
			Document doc = Jsoup.parse(content);
			assertTrue(doc.getElementsByTag("table").first().text().contains("欢迎您：tomcat")); 
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	@Test
	public void TestKindList()
	{
		TestMgrKind testMgeKind = new TestMgrKind();
		try{
			HttpEntity entityKindList = testMgeKind.kindList(httpclient);
			String content = EntityUtils.toString(entityKindList,encoding);
			Document doc = Jsoup.parse(content);
			assertEquals(doc.getElementsByTag("table").get(3).getElementsByTag("tr").size(),7);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	@Test
	public void TestAddKind()
	{
		TestMgrKind testMgeKind = new TestMgrKind();
		try{
			HttpEntity entityAddKind = testMgeKind.addKind(httpclient, "httpclient第一个测试", "第一个测试的描述信息", "a12345");
			String content = EntityUtils.toString(entityAddKind,encoding);
			Document doc = Jsoup.parse(content);
			assertTrue(doc.getElementsByTag("table").get(3).text().contains("httpclient第一个测试"));
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}		
	}
	
	@Test
	public void TestUpdateKind()
	{
		TestMgrKind testMgeKind = new TestMgrKind();
		try{
			HttpEntity entityUpdateKind = testMgeKind.updateKind(httpclient, kindId, "httpclient第一个测试更新", "第一个测试的描述信息更新", "a12345");
			String content = EntityUtils.toString(entityUpdateKind,encoding);
			Document doc = Jsoup.parse(content);
			assertTrue(doc.getElementsByTag("table").get(3).text().contains("httpclient第一个测试更新"));
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	@Test
	public void TestDelKind()
	{
		TestMgrKind testMgeKind = new TestMgrKind();
		try{
			HttpEntity entityDelKind = testMgeKind.delKind(httpclient, kindId);
			String content = EntityUtils.toString(entityDelKind,encoding);
			Document doc = Jsoup.parse(content);
			assertTrue(doc.text().contains("删除成功"));
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
}