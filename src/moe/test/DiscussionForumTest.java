package moe.test;

import static org.junit.Assert.*;

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

public class DiscussionForumTest extends MoeBaseTest{
	private HttpClient httpclient =null;
	private MOEHttpclient DFTest = null;
	@Before
    public void setUp(){
		httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		DFTest = new MOEHttpclient();
		DFTest.HttpClientGetMethod(httpclient, new LinkedHashMap<String,String>() , indexurl);
		Map<String,String> formParamofLogin = new LinkedHashMap<String,String>();
		formParamofLogin.put("userid", "s0007762i");
		formParamofLogin.put("password", "123456");
		formParamofLogin.put("hiddenAction", "valid");
		DFTest.HttpClientPostMethod(httpclient, formParamofLogin, login);
    }
	
	@After
	public void tearDown(){
		Map<String,String> formParamofLoginOut = new LinkedHashMap<String,String>();
		formParamofLoginOut.put("hiddenAction", "logout");
		
		try{
			DFTest.HttpClientPostMethod(httpclient, formParamofLoginOut, login);
//			Document doc = Jsoup.parse(entityLoginOut);
//			System.out.println(doc.text());
//			assertTrue(doc.text().contains("For authorized use only. Unauthorized use is strictly prohibited."));
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			DFTest = null;
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	@Test
	public void testDFHomePage()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "populate");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFHomePage);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("The Discussion Forum is a platform where staff can exchange views freely on educational issues, or otherwise. To facilitate meaningful discussions, we seek your cooperation in maintaining Netiquette on this forum."));
		assertTrue(docASM.text().contains("My MOE"));			
	}
	
	@Test
	public void testBoardListPage()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("?strBoardId=", "7");
		String entityDFhomepage = DFTest.HttpClientGetMethod(httpclient, formParam, DFBoardListPage);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("CREATEThread2"));
		assertTrue(docASM.text().contains("Locked Thread"));
		assertTrue(docASM.text().contains("Thread you have posted in"));
	}
	
	@Test
	public void testThreadListPage()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("gotoPage", "");
		formParam.put("gotoPage", "");
		formParam.put("threadForm.strTopicId", "");
		formParam.put("threadForm.strTopicName", "");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strThreadId", "836");
		formParam.put("threadForm.strBoardName", "BlueSky");
		formParam.put("threadForm.hidOrder", "DESC");
		formParam.put("hiddenAction", "hits");
		formParam.put("threadForm.hidSortKey", "LASTPOST_ON");
		formParam.put("pageNo", "null");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFBoardListPage);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("Home » Discussion Forum » My MOE» BlueSky"));
		assertTrue(docASM.text().contains("CREATEThread2"));
		
	}
	
	@Test
	public void testPostCreateOn()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("?hiddenAction=", "PostingAuditTrail");
		formParam.put("&threadForm.strPostId=", "11268");
		String entityDFhomepage = DFTest.HttpClientGetMethod(httpclient, formParam, DFPostingMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
		assertTrue(docASM.text().contains("Posted Content"));
		assertTrue(docASM.text().contains("Posted on:24-Oct-2011 01:38:26 PM"));
		assertTrue(docASM.text().contains("CREATEThread"));
//		System.out.println(docASM.text());
	}
	
	@Test
	public void testLikePost()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("gotoPage", "");
		formParam.put("gotoPage", "");
		formParam.put("hidMarkFlag", "");
		formParam.put("threadForm.hiddenAction", "");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("JsThread", "836");
		formParam.put("strThreadId", "836");
		formParam.put("threadForm.strThreadId", "836");
		formParam.put("hidReadFlag", "11268");
		formParam.put("hidReadFlag", "11299");
		formParam.put("hidReadFlag", "11302");
		formParam.put("hidReadFlag", "11313");
		formParam.put("hidReadFlag", "11314");
		formParam.put("hidReadFlag", "11420");
		formParam.put("threadForm.strPostId", "11420");
		formParam.put("hidReadFlag", "11429");
		formParam.put("hidOrder", "ASC");
		formParam.put("hidSortKey", "posting");
		formParam.put("hiddenAction", "PostingLike");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFPostingMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.getElementById("postsContents").getElementsByTag("tr").get(6).text());
//		assertTrue(docASM.getElementById("postsContents").getElementsByTag("tr").get(6).text().contains("You like this post"));
		assertTrue(docASM.text().contains("Unlike"));		
	}
	
	@Test
	public void testUnLikePost()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("gotoPage", "");
		formParam.put("gotoPage", "");
		formParam.put("hidMarkFlag", "");
		formParam.put("threadForm.hiddenAction", "");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("JsThread", "836");
		formParam.put("strThreadId", "836");
		formParam.put("threadForm.strThreadId", "836");
		formParam.put("hidReadFlag", "11268");
		formParam.put("hidReadFlag", "11299");
		formParam.put("hidReadFlag", "11302");
		formParam.put("hidReadFlag", "11313");
		formParam.put("hidReadFlag", "11314");
		formParam.put("hidReadFlag", "11420");
		formParam.put("threadForm.strPostId", "11420");
		formParam.put("hidReadFlag", "11429");
		formParam.put("hidOrder", "ASC");
		formParam.put("hidSortKey", "posting");
		formParam.put("hiddenAction", "PostingUnLike");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFPostingMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.getElementById("postsContents").getElementsByTag("tr").get(6).text());
		assertTrue(docASM.text().contains("Like"));		
	}
	@Test
	public void testProEditPost()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "PostingEdit");
		formParam.put("threadForm.strPostId", "11888");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFPostingMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("Post reply"));		
		assertTrue(docASM.text().contains("mshen test 0619 two"));	
		assertTrue(docASM.text().contains("MOE is not responsible or liable for the post content My Forum users have made available from external sites or resources."));	
	}
	
	@Test
	public void testEditPost()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "PostingModify");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strPostId", "11888");
		formParam.put("threadForm.strPosting", "");
		formParam.put("threadForm.strPostingNew", "<p>mshen test 0619 two mshen testing</p><p>&nbsp;</p>");
		formParam.put("threadForm.strPostingQuote", "");
		formParam.put("threadForm.strThreadId", "836");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFPostingMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("Posting has been modified successfully. Thank you."));		
	}
	
	@Test
	public void testProQuotePost()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "PostingQuote");
		formParam.put("threadForm.strPostId", "11268");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFPostingMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("QUOTE:"));		
		assertTrue(docASM.text().contains("Posted By : S0003585B nickname Posted On : 24-Oct-2011 01:38:26 PM"));
	}
	
	@Test
	public void testQuotePost()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "PostMessage");
		formParam.put("__checkbox_threadForm.strHQReply", "Y");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strHQReply", "Y");
		formParam.put("threadForm.strPostId", "");
		formParam.put("threadForm.strPosting", "");
		formParam.put("threadForm.strPostingNew", "mshen testing quote");
		formParam.put("threadForm.strPostingQuote", "");
		formParam.put("threadForm.strQuotePost", "<p>QUOTE:</p><p><strong>Posted By : </strong>S0003585B nickname <strong>Posted On : </strong>24-Oct-2011 01:38:26 PM</p><p>ioiuoyyyyy[video width=\"160\" height=\"120\" align=\"center\" url=\"http://v.youku.com/v_show/id_XMzA1MzMyODgw.html\"]</p>");
		formParam.put("threadForm.strThreadId", "836");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFPostingMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("Posting has been created successfully. Thank you."));		
	}
	
	@Test
	public void testReportToAdmin()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("sendMailForm.email", "");
		formParam.put("sendMailForm.id", "11268");
		formParam.put("sendMailForm.flag", "A");
		formParam.put("hiddenAction", "adminmail");
		formParam.put("sendMailForm.surveyOrCons", "F");
		formParam.put("sendMailForm.message", "mshen testing again");
		formParam.put("sendMailForm.subject", "My Forum Admin Alert Message from S0007762I name");
		formParam.put("sendMailForm.name", "S0003585B nickname");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFSendMail);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("Email was sent sucessfully except for those with invalid domain."));
	}
	
	@Test
	public void testDeletePost()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("gotoPage", "");
		formParam.put("gotoPage", "");
		formParam.put("hidMarkFlag", "");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("JsThread", "836");
		formParam.put("strThreadId", "836");
		formParam.put("threadForm.strThreadId", "836");
		formParam.put("hidReadFlag", "11268");
		formParam.put("hidReadFlag", "11299");
		formParam.put("hidReadFlag", "11302");
		formParam.put("hidReadFlag", "11313");
		formParam.put("hidReadFlag", "11314");
		formParam.put("hidReadFlag", "11420");
		formParam.put("hidReadFlag", "11429");
		formParam.put("threadForm.strPostId", "11302");
		formParam.put("hidOrder", "ASC");
		formParam.put("hidSortKey", "posting");
		formParam.put("hiddenAction", "PostingDelete");
		formParam.put("threadForm.hiddenAction", "PostingList");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFPostingMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
		assertFalse(docASM.text().contains("sdfdsfasdfasdfasdf mshen testing"));
	}
	@Test
	public void testProPostReplyAll()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "PostingEdit");
		formParam.put("threadForm.strPostId", "");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFPostingMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("Post reply"));		
		assertTrue(docASM.text().contains("HQ Reply"));	
		assertTrue(docASM.text().contains("MOE is not responsible or liable for the post content My Forum users have made available from external sites or resources."));	
	}
	@Test
	public void testPostReplyAll()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "PostMessage");
		formParam.put("__checkbox_threadForm.strHQReply", "Y");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strHQReply", "Y");
		formParam.put("threadForm.strPostId", "");
		formParam.put("threadForm.strPosting", "");
		formParam.put("threadForm.strPostingNew", "mshen testing post reply all");
		formParam.put("threadForm.strPostingQuote", "");
		formParam.put("threadForm.strThreadId", "836");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFPostingMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("Posting has been created successfully. Thank you."));
	}
	@Test
	public void testNewThread()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("__checkbox_threadForm.objFormPoll.mutAns", "Y");
		formParam.put("__checkbox_threadForm.strHQReply", "Y");
		formParam.put("hiddenAction", "NewThread");
		formParam.put("threadForm.includePoll", "Y");
		formParam.put("threadForm.objFormPoll.answer1", "one");
		formParam.put("threadForm.objFormPoll.answer2", "two");
		formParam.put("threadForm.objFormPoll.answer3", "three");
		formParam.put("threadForm.objFormPoll.answer4", "");
		formParam.put("threadForm.objFormPoll.answer5", "");
		formParam.put("threadForm.objFormPoll.expDt", "30-Jun-2012");
		formParam.put("threadForm.objFormPoll.hiddenAction", "create");
		formParam.put("threadForm.objFormPoll.imgPer", "");
		formParam.put("threadForm.objFormPoll.imgSize", "");
		formParam.put("threadForm.objFormPoll.mutAns", "Y");
		formParam.put("threadForm.objFormPoll.pollId", "");
		formParam.put("threadForm.objFormPoll.pubId", "");
		formParam.put("threadForm.objFormPoll.pubTitle", "");
		formParam.put("threadForm.objFormPoll.question", "mshen testing question");
		formParam.put("threadForm.objFormPoll.res1", "");
		formParam.put("threadForm.objFormPoll.res2", "");
		formParam.put("threadForm.objFormPoll.res3", "");
		formParam.put("threadForm.objFormPoll.res4", "");
		formParam.put("threadForm.objFormPoll.res5", "");
		formParam.put("threadForm.objFormPoll.showRes", "Y");
		formParam.put("threadForm.objFormPoll.startDt", "19-Jun-2012");
		formParam.put("threadForm.objFormPoll.tempExpDt", "30-Jun-2012");
		formParam.put("threadForm.objFormPoll.tempStartDt", "19-Jun-2012");
		formParam.put("threadForm.objFormPoll.total", "mshen testing post reply all");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strHQReply", "Y");
		formParam.put("threadForm.strModerationReq", "N");
		formParam.put("threadForm.strPostId", "");
		formParam.put("threadForm.strPosting", "<p>mshen test 0619 two</p>");
		formParam.put("threadForm.strSecurity", "N");
		formParam.put("threadForm.strThreadId", "");
		formParam.put("threadForm.strTitle", "mshen test 0619 two");
		formParam.put("threadForm.strTopicId", "");
		formParam.put("threadForm.strTopicModerationReq", "false");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFThreadMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("Thread created successfully"));
	}
	
	@Test
	public void testAdminToolsEdit()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "ModifyThread");
		formParam.put("threadForm.objFormPoll.expDt", "");
		formParam.put("threadForm.objFormPoll.hiddenAction", "create");
		formParam.put("threadForm.objFormPoll.imgPer", "");
		formParam.put("threadForm.objFormPoll.imgSize", "");
		formParam.put("threadForm.objFormPoll.pollId", "");
		formParam.put("threadForm.objFormPoll.pubId", "");
		formParam.put("threadForm.objFormPoll.pubTitle", "");
		formParam.put("threadForm.objFormPoll.res1", "");
		formParam.put("threadForm.objFormPoll.res2", "");
		formParam.put("threadForm.objFormPoll.res3", "");
		formParam.put("threadForm.objFormPoll.res4", "");
		formParam.put("threadForm.objFormPoll.res5", "");
		formParam.put("threadForm.objFormPoll.startDt", "");
		formParam.put("threadForm.objFormPoll.total", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strHQReply", "Y");
		formParam.put("threadForm.strModerationReq", "N");
		formParam.put("threadForm.strPostId", "");
		formParam.put("threadForm.strSecurity", "N");
		formParam.put("threadForm.strThreadId", "1052");
		formParam.put("threadForm.strTitle", "mshen test 0619 two again");
		formParam.put("threadForm.strTopicId", "");
		formParam.put("threadForm.strTopicModerationReq", "false");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFThreadMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("Thread modified successfully"));
	}
	
	@Test
	public void testAdminToolsLock()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "LockThread");
		formParam.put("__checkbox_objForm.res1", "Y");
		formParam.put("__checkbox_objForm.res2", "Y");
		formParam.put("__checkbox_objForm.res3", "Y");
		formParam.put("hidMarkFlag", "");
		formParam.put("hidOrder", "ASC");
		formParam.put("JsThread", "1052");
		formParam.put("objForm.multiple", "Y");
		formParam.put("objForm.pollId", "239");
		formParam.put("objForm.published", "");
		formParam.put("objForm.showRes", "Y");
		formParam.put("strThreadId", "1052");
		formParam.put("threadForm.hiddenAction", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strHQReply", "Y");
		formParam.put("threadForm.strModerationReq", "N");
		formParam.put("threadForm.strPostId", "");
		formParam.put("threadForm.strThreadId", "1052");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		DFTest.HttpClientPostMethod(httpclient, formParam, DFThreadMaintain);
		Map<String,String> formParamList = new LinkedHashMap<String,String>();
		formParamList.put("?strBoardId=", "7");
		String entityDFhomepage = DFTest.HttpClientGetMethod(httpclient, formParamList, DFBoardListPage);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		System.out.println(docASM.getElementsByClass("lockedThreadIcon").html().contains("<div class=\"lockedThreadIcon\">"));
//		assertTrue(docASM.getElementsByClass("lockedThreadIcon").html().contains("<div class=\"lockedThreadIc\">"));
	}
	
	@Test
	public void testAdminToolsUnLock()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "UnLockThread");
		formParam.put("__checkbox_objForm.res1", "Y");
		formParam.put("__checkbox_objForm.res2", "Y");
		formParam.put("__checkbox_objForm.res3", "Y");
		formParam.put("hidMarkFlag", "");
		formParam.put("hidOrder", "ASC");
		formParam.put("JsThread", "1052");
		formParam.put("objForm.multiple", "Y");
		formParam.put("objForm.pollId", "239");
		formParam.put("objForm.published", "");
		formParam.put("objForm.showRes", "Y");
		formParam.put("strThreadId", "1052");
		formParam.put("threadForm.hiddenAction", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strHQReply", "Y");
		formParam.put("threadForm.strModerationReq", "N");
		formParam.put("threadForm.strPostId", "");
		formParam.put("threadForm.strThreadId", "1052");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		DFTest.HttpClientPostMethod(httpclient, formParam, DFThreadMaintain);
		Map<String,String> formParamList = new LinkedHashMap<String,String>();
		formParamList.put("?strBoardId=", "7");
		String entityDFhomepage = DFTest.HttpClientGetMethod(httpclient, formParamList, DFBoardListPage);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		System.out.println(docASM.getElementsByClass("lockedThreadIcon").html().contains("<div class=\"lockedThreadIcon\">"));
//		assertTrue(docASM.getElementsByClass("lockedThreadIcon").html().contains("<div class=\"lockedThreadIc\">"));
	}
	
	@Test
	public void testAdminToolsStick()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "AdminStickyThread");
		formParam.put("__checkbox_objForm.res1", "Y");
		formParam.put("__checkbox_objForm.res2", "Y");
		formParam.put("__checkbox_objForm.res3", "Y");
		formParam.put("hidMarkFlag", "");
		formParam.put("hidOrder", "ASC");
		formParam.put("hidSortKey", "posting");
		formParam.put("JsThread", "1052");
		formParam.put("objForm.multiple", "Y");
		formParam.put("objForm.pollId", "239");
		formParam.put("objForm.pubId", "");
		formParam.put("objForm.published", "");
		formParam.put("objForm.showRes", "Y");
		formParam.put("strThreadId", "1052");
		formParam.put("threadForm.hiddenAction", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strPostId", "");
		formParam.put("threadForm.strThreadId", "1052");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		DFTest.HttpClientPostMethod(httpclient, formParam, DFThreadMaintain);
		Map<String,String> formParamList = new LinkedHashMap<String,String>();
		formParamList.put("?strBoardId=", "7");
		String entityDFhomepage = DFTest.HttpClientGetMethod(httpclient, formParamList, DFBoardListPage);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
//		assertTrue(docASM.getElementsByClass("lockedThreadIcon").html().contains("<div class=\"lockedThreadIc\">"));
	}
	@Test
	public void testAdminToolsUnStick()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "AdminUnStickyThread");
		formParam.put("__checkbox_objForm.res1", "Y");
		formParam.put("__checkbox_objForm.res2", "Y");
		formParam.put("__checkbox_objForm.res3", "Y");
		formParam.put("hidMarkFlag", "");
		formParam.put("hidOrder", "ASC");
		formParam.put("hidSortKey", "posting");
		formParam.put("JsThread", "1052");
		formParam.put("objForm.multiple", "Y");
		formParam.put("objForm.pollId", "239");
		formParam.put("objForm.pubId", "");
		formParam.put("objForm.published", "");
		formParam.put("objForm.showRes", "Y");
		formParam.put("strThreadId", "1052");
		formParam.put("threadForm.hiddenAction", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strPostId", "");
		formParam.put("threadForm.strThreadId", "1052");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		DFTest.HttpClientPostMethod(httpclient, formParam, DFThreadMaintain);
		Map<String,String> formParamList = new LinkedHashMap<String,String>();
		formParamList.put("?strBoardId=", "7");
		String entityDFhomepage = DFTest.HttpClientGetMethod(httpclient, formParamList, DFBoardListPage);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
//		assertTrue(docASM.getElementsByClass("lockedThreadIcon").html().contains("<div class=\"lockedThreadIc\">"));
	}
	
	@Test
	public void testAdminToolsMark()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "markSpecial");
		formParam.put("__checkbox_objForm.res1", "Y");
		formParam.put("__checkbox_objForm.res2", "Y");
		formParam.put("__checkbox_objForm.res3", "Y");
		formParam.put("hidMarkFlag", "");
		formParam.put("hidOrder", "ASC");
		formParam.put("hidSortKey", "posting");
		formParam.put("JsThread", "1052");
		formParam.put("objForm.multiple", "Y");
		formParam.put("objForm.pollId", "239");
		formParam.put("objForm.pubId", "");
		formParam.put("objForm.published", "");
		formParam.put("objForm.showRes", "Y");
		formParam.put("strThreadId", "1052");
		formParam.put("threadForm.hiddenAction", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strPostId", "undefined");
		formParam.put("threadForm.strThreadId", "1052");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		DFTest.HttpClientPostMethod(httpclient, formParam, DFThreadMaintain);
		Map<String,String> formParamList = new LinkedHashMap<String,String>();
		formParamList.put("?strBoardId=", "7");
		String entityDFhomepage = DFTest.HttpClientGetMethod(httpclient, formParamList, DFBoardListPage);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
//		assertTrue(docASM.getElementsByClass("lockedThreadIcon").html().contains("<div class=\"lockedThreadIc\">"));
	}
	
	@Test
	public void testAdminToolsUnMark()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "unmarkSpecial");
		formParam.put("__checkbox_objForm.res1", "Y");
		formParam.put("__checkbox_objForm.res2", "Y");
		formParam.put("__checkbox_objForm.res3", "Y");
		formParam.put("hidMarkFlag", "");
		formParam.put("hidOrder", "ASC");
		formParam.put("hidSortKey", "posting");
		formParam.put("JsThread", "1052");
		formParam.put("objForm.multiple", "Y");
		formParam.put("objForm.pollId", "239");
		formParam.put("objForm.pubId", "");
		formParam.put("objForm.published", "");
		formParam.put("objForm.showRes", "Y");
		formParam.put("strThreadId", "1052");
		formParam.put("threadForm.hiddenAction", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strPostId", "undefined");
		formParam.put("threadForm.strThreadId", "1052");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		DFTest.HttpClientPostMethod(httpclient, formParam, DFThreadMaintain);
		Map<String,String> formParamList = new LinkedHashMap<String,String>();
		formParamList.put("?strBoardId=", "7");
		String entityDFhomepage = DFTest.HttpClientGetMethod(httpclient, formParamList, DFBoardListPage);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
//		assertTrue(docASM.getElementsByClass("lockedThreadIcon").html().contains("<div class=\"lockedThreadIc\">"));
	}
	
	@Test
	public void testThreadToolsPrint()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "PrintThread");
		formParam.put("ThreadMaintain_strThreadId", "1052");
		formParam.put("strPostId", "11885");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("JsThread", "1052");
		formParam.put("hidMarkFlag", "");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFThreadMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
//		assertTrue(docASM.getElementsByClass("lockedThreadIcon").html().contains("<div class=\"lockedThreadIc\">"));
	}
	
	@Test
	public void testThreadToolsShareViaEmail()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("alertFriendForm.content", "You have been invited to join in a new discussion that is happening in myforum. Why wait? Click on the url below to join in the conversation today! (http://moeuxau05.moe.gov.sg/oi/login.jsp?module=F&id=1052&from=TE)");
		formParam.put("alertFriendForm.emailId", "S0007762I name");
		formParam.put("alertFriendForm.iD", "1052");
		formParam.put("alertFriendForm.subject", "Invitation to a new myforum discussion thread");
		formParam.put("alertFriendForm.to", "mshen@cn.ufinity.com");
		formParam.put("alertFriendForm.url", "http://moeuxau05.moe.gov.sg/oi/login.jsp?module=F&id=1052&from=TE");
		formParam.put("hiddenAction", "sendMail");
		formParam.put("module", "Forum");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, AlertFriendAction);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("Thank you for referring this Discussion topic to your friend"));
	}
	
	@Test
	public void testThreadToolsMarkAsRead()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "markRead");
		formParam.put("__checkbox_objForm.res1", "Y");
		formParam.put("__checkbox_objForm.res2", "Y");
		formParam.put("__checkbox_objForm.res3", "Y");
		formParam.put("hidMarkFlag", "");
		formParam.put("hidOrder", "ASC");
		formParam.put("hidSortKey", "posting");
		formParam.put("JsThread", "1052");
		formParam.put("objForm.multiple", "Y");
		formParam.put("objForm.pollId", "239");
		formParam.put("objForm.pubId", "");
		formParam.put("objForm.published", "");
		formParam.put("objForm.showRes", "Y");
		formParam.put("strThreadId", "1052");
		formParam.put("threadForm.hiddenAction", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strPostId", "");
		formParam.put("threadForm.strThreadId", "1052");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFMarkAsRead);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
//		assertTrue(docASM.text().contains("Thank you for referring this Discussion topic to your friend"));
	}
	
	@Test
	public void testThreadToolsMarkAsUnRead()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "markUnRead");
		formParam.put("__checkbox_objForm.res1", "Y");
		formParam.put("__checkbox_objForm.res2", "Y");
		formParam.put("__checkbox_objForm.res3", "Y");
		formParam.put("hidMarkFlag", "");
		formParam.put("hidOrder", "ASC");
		formParam.put("hidSortKey", "posting");
		formParam.put("JsThread", "1052");
		formParam.put("objForm.multiple", "Y");
		formParam.put("objForm.pollId", "239");
		formParam.put("objForm.pubId", "");
		formParam.put("objForm.published", "");
		formParam.put("objForm.showRes", "Y");
		formParam.put("strThreadId", "1052");
		formParam.put("threadForm.hiddenAction", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strPostId", "");
		formParam.put("threadForm.strThreadId", "1052");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFMarkAsRead);
		Document docASM = Jsoup.parse(entityDFhomepage);
		System.out.println(docASM.text());
//		assertTrue(docASM.text().contains("Thank you for referring this Discussion topic to your friend"));
	}
	
	@Test
	public void testThreadToolsBookMark()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "TrackThread");
		formParam.put("__checkbox_objForm.res1", "Y");
		formParam.put("__checkbox_objForm.res2", "Y");
		formParam.put("__checkbox_objForm.res3", "Y");
		formParam.put("hidMarkFlag", "");
		formParam.put("hidOrder", "ASC");
		formParam.put("hidSortKey", "posting");
		formParam.put("JsThread", "1052");
		formParam.put("objForm.multiple", "Y");
		formParam.put("objForm.pollId", "239");
		formParam.put("objForm.pubId", "");
		formParam.put("objForm.published", "");
		formParam.put("objForm.showRes", "Y");
		formParam.put("strThreadId", "1052");
		formParam.put("threadForm.hiddenAction", "");
		formParam.put("threadForm.pageNo", "1");
		formParam.put("threadForm.strBoardId", "7");
		formParam.put("threadForm.strPostId", "");
		formParam.put("threadForm.strThreadId", "1052");
		formParam.put("threadList", "");
		formParam.put("threadListType", "");
		formParam.put("winObj", "[object Window]");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFThreadMaintain);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("Ministry Of Education Discussion Forum Thread Bookmark created successfully"));
	}
	
	@Test
	public void testViewUpdateInLatest()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("hiddenAction", "populate");
		formParam.put("hidMode", "12");
		String entityDFhomepage = DFTest.HttpClientPostMethod(httpclient, formParam, DFViewInLatest);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("BlueSky"));
		assertTrue(docASM.text().contains("Recent 25 Hours"));
	}
	@Test
	public void testMemberProfileOfNickName()
	{
		Map<String,String> formParam = new LinkedHashMap<String,String>();
		formParam.put("?nric=", "44676767686869653e");
		formParam.put("&postId=", "11883");
		formParam.put("&hiddenAction=", "populatePoster");
		formParam.put("&isEncrpyt=", "yes");
		String entityDFhomepage = DFTest.HttpClientGetMethod(httpclient, formParam, MemberProfileAction);
		Document docASM = Jsoup.parse(entityDFhomepage);
//		System.out.println(docASM.text());
		assertTrue(docASM.text().contains("readin"));
	}
}
