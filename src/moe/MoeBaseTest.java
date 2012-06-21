package moe;

public class MoeBaseTest {
	public static final String url = "http://localhost:8081/oi/";
	public static final String encoding = "UTF-8";
	//define login page's URL
	public static final String indexurl = url + "jsp/index.jsp";
	public static final String login = url + "loginAction.do";
	//define asm page's URL
	public static final String asmHomePage = url + "asmHome.do";
	public static final String asmAddPage = url + "ASMView.do";
	public static final String asmSendEmailToFriend = url + "AlertFriendAction.do";
	public static final String asmViewPast = url + "asmViewPast.do";
	public static final String ASMManagement = url + "ASMManagement.do";
	public static final String ASMAbout = url + "ASMAbout.do";
	//define survey page's URL
	public static final String surveyHomePage = url + "UserSurvey.do";
	public static final String surveySaveAsDraft = url + "UserSurveyResponse.do";
	//define Discussion Forum page's URL
	public static final String DFHomePage = url + "WebForumListingAction.do";
	public static final String DFBoardListPage = url + "ThreadList.do";
	public static final String DFPostingMaintain = url + "PostingMaintain.do";
	public static final String DFThreadMaintain = url + "ThreadMaintain.do";
	public static final String DFSendMail = url + "SendMail.do";
	public static final String AlertFriendAction = url + "AlertFriendAction.do";
	public static final String DFMarkAsRead = url + "ThreadMaintain.do?markThredId=1052&markId=11885";
	public static final String DFViewInLatest = url + "WebForumListingAction.do";
	public static final String MemberProfileAction = url + "MemberProfileAction.do";
}
