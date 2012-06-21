package moe.test;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import moe.MoeBaseTest;
import moe.httpclient.MOEHttpclient;

public class SurveyTest extends MoeBaseTest{
	
	
	private HttpClient httpclient =null;
	private MOEHttpclient surveyTest = null;
	@Before
    public void setUp(){
		httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		surveyTest = new MOEHttpclient();
		surveyTest.HttpClientGetMethod(httpclient, new LinkedHashMap<String,String>() , indexurl);
		Map<String,String> formParamofLogin = new LinkedHashMap<String,String>();
		formParamofLogin.put("userid", "S0004723J");
		formParamofLogin.put("password", "123456");
		formParamofLogin.put("hiddenAction", "valid");
		surveyTest.HttpClientPostMethod(httpclient, formParamofLogin, login);
    }
	
	@After
	public void tearDown(){
		Map<String,String> formParamofLoginOut = new LinkedHashMap<String,String>();
		formParamofLoginOut.put("hiddenAction", "logout");
		
		try{
			surveyTest.HttpClientPostMethod(httpclient, formParamofLoginOut, login);
//			Document doc = Jsoup.parse(entityLoginOut);
//			System.out.println(doc.text());
//			assertTrue(doc.text().contains("For authorized use only. Unauthorized use is strictly prohibited."));
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			surveyTest = null;
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	@Test
	public void testSurveyHomePage()
	{
		try{
			String entitySurvey = surveyTest.HttpClientGetMethod(httpclient, new LinkedHashMap<String,String>(), surveyHomePage);
			Document docASM = Jsoup.parse(entitySurvey);
			assertTrue(docASM.text().contains("Current Survey(s)"));
			assertTrue(docASM.text().contains("Past Survey(s)"));
	//		System.out.println(docASM.text());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	

	@Test
	public void testStartSurvey()
	{
		Map<String, String> surveyMap = new LinkedHashMap<String, String>();
		surveyMap.put("hiddenAction", "startSurvey");
		surveyMap.put("strSurveyId", "304");
		try{
			String entitySurvey = surveyTest.HttpClientPostMethod(httpclient, surveyMap, surveyHomePage);
			Document docASM = Jsoup.parse(entitySurvey);
			System.out.println(docASM.text());
			assertTrue(docASM.text().contains("Current Survey"));
			assertTrue(docASM.text().contains("test 0614"));
			assertTrue(docASM.getElementById("saveButton").text().contains("Save as Draft"));
			assertTrue(docASM.getElementById("submitButton").text().contains("Submit"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSurveyDetailsPage()
	{
		Map<String, String> surveyMap = new LinkedHashMap<String, String>();
		surveyMap.put("hiddenAction", "SurveyDetails");
		surveyMap.put("strSurveyId", "304");
		try{
			String entitySurvey = surveyTest.HttpClientPostMethod(httpclient, surveyMap, surveyHomePage);
			Document docASM = Jsoup.parse(entitySurvey);
			assertTrue(docASM.text().contains("Current Survey"));
			assertTrue(docASM.text().contains("test 0614"));
			assertTrue(docASM.getElementById("viewResultButton").text().contains("Start Survey"));
	//		System.out.println(docASM.text());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveSurveyAsDraft()
	{
		Map<String, String> surveyMap = new LinkedHashMap<String, String>();
		surveyMap.put("hiddenAction", "SecQstRspSave");
		surveyMap.put("objSurvey.responseList[0].strQstType", "R");
		surveyMap.put("objSurvey.responseList[0].strQuestionId", "2868");
		surveyMap.put("objSurvey.responseList[0].strRbResponse", "R1");
		surveyMap.put("objSurvey.strNextSectionId", "813");
		surveyMap.put("objSurvey.strSectionId", "813");
		surveyMap.put("objSurvey.strSurveyId", "304");
		try{
			String entitySurvey = surveyTest.HttpClientPostMethod(httpclient, surveyMap, surveySaveAsDraft);
			Document docASM = Jsoup.parse(entitySurvey);	
			assertTrue(docASM.getElementById("1R1").toString().contains("checked=\"true\""));
	//		System.out.println(docASM.text());
	//		System.out.println(docASM.getElementById("1R1"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSaveSurveySubmit()
	{
		Map<String, String> surveyMap = new LinkedHashMap<String, String>();
		surveyMap.put("hiddenAction", "SurveyUserSubmit");
		surveyMap.put("objSurvey.responseList[0].strQstType", "R");
		surveyMap.put("objSurvey.responseList[0].strQuestionId", "2868");
		surveyMap.put("objSurvey.responseList[0].strRbResponse", "R1");
		surveyMap.put("objSurvey.strNextSectionId", "813");
		surveyMap.put("objSurvey.strSectionId", "813");
		surveyMap.put("objSurvey.strSurveyId", "304");
		try{
			String entitySurvey = surveyTest.HttpClientPostMethod(httpclient, surveyMap, surveySaveAsDraft);
			Document docASM = Jsoup.parse(entitySurvey);	
			assertTrue(docASM.text().contains("We value your views and suggestions. Thank you for your feedback"));
	//		System.out.println(docASM.text());
	//		System.out.println(docASM.getElementById("1R1"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDownloadFilesFromPastSurvey(){
		Map<String, String> surveyMap = new LinkedHashMap<String, String>();
		surveyMap.put("objSurvey.strSurveyId", "214");
		surveyMap.put("hiddenAction", "SurveyDownloadAttachFile");
		try{
			String entitySurvey = surveyTest.DownloadFiles(httpclient, surveyMap, surveyHomePage,"c:/surveyDownload.txt");
	//		Document docASM = Jsoup.parse(entitySurvey);	
			assertTrue(entitySurvey.contains("download success"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDownloadSurveyResult(){
		Map<String, String> surveyMap = new LinkedHashMap<String, String>();
		surveyMap.put("objSurvey.strSurveyId", "224");
		surveyMap.put("hiddenAction", "SurveyDownlaod");
		try{
			String entitySurvey = surveyTest.DownloadFiles(httpclient, surveyMap, surveyHomePage,"c:/surveyResultDownload.pdf");
	//		Document docASM = Jsoup.parse(entitySurvey);	
			assertTrue(entitySurvey.contains("download success"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
