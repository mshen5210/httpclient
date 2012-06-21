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


public class ASMTest extends MoeBaseTest{
	private HttpClient httpclient =null;
	private MOEHttpclient asmTest = null;
	@Before
    public void setUp(){
		httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		asmTest = new MOEHttpclient();
		asmTest.HttpClientGetMethod(httpclient, new LinkedHashMap<String,String>() , indexurl);
		Map<String,String> formParamofLogin = new LinkedHashMap<String,String>();
		formParamofLogin.put("userid", "s0007762i");
		formParamofLogin.put("password", "123456");
		formParamofLogin.put("hiddenAction", "valid");
		try{
			asmTest.HttpClientPostMethod(httpclient, formParamofLogin, login);
		}catch (Exception e){
			e.printStackTrace();
		}
    }
	
	@After
	public void tearDown(){
		Map<String,String> formParamofLoginOut = new LinkedHashMap<String,String>();
		formParamofLoginOut.put("hiddenAction", "logout");
		
		try{
			asmTest.HttpClientPostMethod(httpclient, formParamofLoginOut, login);
//			Document doc = Jsoup.parse(entityLoginOut);
//			System.out.println(doc.text());
//			assertTrue(doc.text().contains("For authorized use only. Unauthorized use is strictly prohibited."));
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			asmTest = null;
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	@Test
	public void testAsmLoadPage()
	{
		try{
			String entityASMhomepage = asmTest.HttpClientGetMethod(httpclient, new LinkedHashMap<String,String>(), asmHomePage);
			Document docASM = Jsoup.parse(entityASMhomepage);
			assertTrue(docASM.text().contains("Last 6 ASM Letters / Editor's Notes"));
//			System.out.println(docASM.text());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddASMAsDraft(){
		//params for add a asm as draft
		Map<String,String> formParamofDraft = new LinkedHashMap<String,String>();
		formParamofDraft.put("asmLetterForm.letterId", "");
		formParamofDraft.put("asmLetterForm.contactNo", "123456");
		formParamofDraft.put("asmLetterForm.letter", "mshen test 0618 draft");
		formParamofDraft.put("asmLetterForm.topic", "mshen test 0618 title draft");
		formParamofDraft.put("hiddenAction", "draft");
		try{
			String entityASMhomepage = asmTest.HttpClientPostMethod(httpclient, formParamofDraft, asmAddPage);
			Document docASM = Jsoup.parse(entityASMhomepage);
			assertTrue(docASM.text().contains("mshen test 0618 title draft"));
		}catch (Exception e){
			e.printStackTrace();
		}
			
//		System.out.println(docASM.text());
	}
	
	@Test
	public void testAddASMAsSubmit(){
		//params for add a asm as draft
		Map<String,String> formParamofDraft = new LinkedHashMap<String,String>();
		formParamofDraft.put("asmLetterForm.letterId", "");
		formParamofDraft.put("asmLetterForm.contactNo", "123456");
		formParamofDraft.put("asmLetterForm.letter", "mshen test 0618 submit");
		formParamofDraft.put("asmLetterForm.topic", "mshen test 0618 title submit");
		formParamofDraft.put("hiddenAction", "submit");
		try{
			String entityASMhomepage = asmTest.HttpClientPostMethod(httpclient, formParamofDraft, asmAddPage);
			Document docASM = Jsoup.parse(entityASMhomepage);
			assertTrue(docASM.text().contains("mshen test 0618 title submit"));
		}catch (Exception e){
			e.printStackTrace();
		}
//		System.out.println(docASM.text());
	}
	
	@Test
	public void testASMEmailToFriend(){
		//params for send email to a friend
		Map<String,String> formParamofEmail = new LinkedHashMap<String,String>();
		formParamofEmail.put("alertFriendForm.content", "<p>Draft Letter Content Message</p><p>test 0613 draft</p>");
		formParamofEmail.put("alertFriendForm.emailId", "S0005064J name");
		formParamofEmail.put("alertFriendForm.iD", "749");
		formParamofEmail.put("alertFriendForm.subject", "ASM Letter-test 0613 two topic draft");
		formParamofEmail.put("alertFriendForm.to", "mshen@cn.ufinity.com");
		formParamofEmail.put("alertFriendForm.url", "http://moeuxau05.moe.gov.sg/oi/login.jsp?module=ASMDRAFT&id=749");
		formParamofEmail.put("hiddenAction", "sendMail");
		formParamofEmail.put("module", "ASMDRAFT");
		try{
			String entityASMEmail = asmTest.HttpClientPostMethod(httpclient, formParamofEmail, asmSendEmailToFriend);
			Document docASM = Jsoup.parse(entityASMEmail);
			assertTrue(docASM.text().contains("Email message has been successfully sent to"));
			assertTrue(docASM.text().contains("mshen@cn.ufinity.com"));
		}catch (Exception e){
			e.printStackTrace();
		}
//		System.out.println(docASM.text());
	}
	
	@Test
	public void testDeleteDraftASM(){
		//params for delete draft asm
		Map<String,String> formParamDelete = new LinkedHashMap<String,String>();
		formParamDelete.put("asmLetterForm.contactNo", "123456");
		formParamDelete.put("asmLetterForm.letter", "<p>mshen test 0615</p>");
		formParamDelete.put("asmLetterForm.letterId", "750");
		formParamDelete.put("asmLetterForm.topic", "mshen test 0615 title");
		formParamDelete.put("hiddenAction", "delete");
		try{		
			String entityASMEmail = asmTest.HttpClientPostMethod(httpclient, formParamDelete, asmAddPage);
			Document docASM = Jsoup.parse(entityASMEmail);
			assertFalse(docASM.text().contains("Email message has been successfully sent to"));
	//		System.out.println(docASM.text());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testASMPrintPreview(){
		//params for print preview
		Map<String,String> formParamPrint = new LinkedHashMap<String,String>();
		formParamPrint.put("?hiddenAction=", "PrintPreview");
		formParamPrint.put("&hidLetterID=", "752");
		try{
			String entityASMEmail = asmTest.HttpClientGetMethod(httpclient, formParamPrint, asmHomePage);
			Document docASM = Jsoup.parse(entityASMEmail);
			assertTrue(docASM.text().contains("Ask Senior Management"));
	//		System.out.println(docASM.text());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testASMDetailsFirstPage(){
		//params for asm details
		Map<String,String> formParamDetails = new LinkedHashMap<String,String>();
		try{
			String entityASMOnePage = asmTest.HttpClientGetMethod(httpclient, formParamDetails, asmViewPast);
			Document docASM = Jsoup.parse(entityASMOnePage);
			assertTrue(docASM.text().contains("Pages : [ 1 ] 2 3 4 5 6 7"));
	//		System.out.println(docASM.text());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testASMDetailsOtherPages(){
		//params for asm details
		Map<String,String> formParamDetails = new LinkedHashMap<String,String>();
		formParamDetails.put("edNotesPageNo", "5");
		formParamDetails.put("gotoPage", "");
		formParamDetails.put("lettersPageNo", "3");
		try{
			String entityASMMoreThenOnePage = asmTest.HttpClientPostMethod(httpclient, formParamDetails, asmViewPast);
			Document docASM = Jsoup.parse(entityASMMoreThenOnePage);
			assertTrue(docASM.text().contains("Pages : 1 2 3 4 [ 5 ] 6 7"));
			assertTrue(docASM.text().contains("Pages : 1 2 [ 3 ] 4 5 6 7 ... 11"));
	//		System.out.println(docASM.text());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testASMDetailsGotoPages(){
		//params for asm details
		Map<String,String> formParamDetails = new LinkedHashMap<String,String>();
		formParamDetails.put("edNotesPageNo", "6");
		formParamDetails.put("gotoPage", "6");
		formParamDetails.put("lettersPageNo", "0");
		try{
			String entityASMMoreThenOnePage = asmTest.HttpClientPostMethod(httpclient, formParamDetails, asmViewPast);
			Document docASM = Jsoup.parse(entityASMMoreThenOnePage);
			assertTrue(docASM.text().contains("Pages : 1 2 3 4 5 [ 6 ] 7"));
			assertTrue(docASM.text().contains("Pages : [ 1 ] 2 3 4 5 ... 11"));
	//		System.out.println(docASM.text());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testASMManagement(){
		try{
			String entityASMManagement = asmTest.HttpClientGetMethod(httpclient, new LinkedHashMap<String,String>(), ASMManagement);
			Document docASM = Jsoup.parse(entityASMManagement);
			assertTrue(docASM.text().contains("Our Senior Management"));
			assertTrue(docASM.text().contains("Expand All"));
	//		System.out.println(docASM.text());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAboutASM(){
		try{
			String entityASMAbout = asmTest.HttpClientGetMethod(httpclient, new LinkedHashMap<String,String>(), ASMAbout);
			Document docASM = Jsoup.parse(entityASMAbout);
			assertTrue(docASM.text().contains("About Ask Senior Management"));
			assertTrue(docASM.text().contains("A.What is ASM all about?"));
			System.out.println(docASM.text());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
