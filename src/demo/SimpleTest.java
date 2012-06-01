/*
* Created on 2003-12-14 by mshen
*/

package demo;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;


/** 
  *@author mshen
*/

public class SimpleTest extends BaseTest{
	public static void main(String[] args) throws IOException
	{
		//构造一个client客户端
		HttpClient httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		TestLogin testLogin = new TestLogin();
		TestMgrKind testMgeKind = new TestMgrKind();
		try{
			//测试Login
			HttpEntity entityLogin = testLogin.loginToSystem(httpclient, "tomcat", "tomcat", "a12345");
			if(entityLogin != null){
				if(EntityUtils.toString(entityLogin,encoding).contains("欢迎您：tomcat"))
				{
					System.out.println("yeah, Right!");
				}else{
					System.out.println("oh, no!");
				}
			}
			//测试添加Kind
			HttpEntity entityAddKind = testMgeKind.addKind(httpclient, "httpclient第一个测试", "第一个测试的描述信息", "a12345");
			if(entityAddKind != null){
				if(EntityUtils.toString(entityAddKind,encoding).contains("httpclient第一个测试"))
				{
					System.out.println("yeah, add success!");
				}else{
					System.out.println("oh, no failed!");
				}
			}
			//测试Kind list
			HttpEntity entityKindList = testMgeKind.kindList(httpclient);
			if(entityKindList != null){
				if(EntityUtils.toString(entityKindList,encoding).contains("房产"))
				{
					System.out.println("yeah, right!");
				}else{
					System.out.println("oh, not found!");
				}
			}
			//测试Update Kind
			HttpEntity entityUpdateKind = testMgeKind.updateKind(httpclient, "16", "httpclient第一个测试更新", "第一个测试的描述信息更新", "a12345");
			if(entityKindList != null){
				if(EntityUtils.toString(entityUpdateKind,encoding).contains("httpclient第一个测试更新"))
				{
					System.out.println("yeah, right!");
				}else{
					System.out.println("oh, not found!");
				}
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			//关闭连接，释放资源
			httpclient.getConnectionManager().shutdown();
		}
	   }
}