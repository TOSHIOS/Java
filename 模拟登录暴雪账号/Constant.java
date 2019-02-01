package hstest;

public class Constant {

	/**
	 * 初始网址
	 */
	public static final String INIT_URL="https://www.battlenet.com.cn/login/srp?csrfToken=true";
	
	/**
	 * 登录网址
	 */
	// "https://www.battlenet.com.cn/login/zh/?ref=https://www.battlenet.com.cn/oauth/authorize?client_id%3Dnetease-d3-site%26response_type%3Dcode%26scope%3Did%2Bbattletag%2Blogout%26redirect_uri%3Dhttps%253A%252F%252Faccount.bnet.163.com%252Fbattlenet%252Flogin%253Finner_client_id%253Dow%2526inner_redirect_uri%253Dhttp%25253A%25252F%25252Fow.blizzard.cn%25252Fbattlenet%25252Flogin%25253Fredirect_url%25253Dhttp%2525253A%2525252F%2525252Fow.blizzard.cn%2525252Fcareer%2525252F&app=oauth";
	// "https://www.battlenet.com.cn/login/zh/?ref=https://www.battlenet.com.cn/zh/&app=com-root";
	public static final String LOGIN_URL="https://www.battlenet.com.cn:443/login/zh/?ref=https://www.battlenet.com.cn/oauth/authorize?client_id%3Dnetease-wow-site%26response_type%3Dcode%26scope%3Daccount.basic%2Baccount.full%2Bwow.profile%2Bcommerce.entitlements%2Bcommerce.wow.ctc%2Bcommerce.wow.ctc:modify%2Bwow.cn%26redirect_uri%3Dhttps%253A%252F%252Faccount.bnet.163.com%252Fbattlenet%252Flogin%253Finner_client_id%253Dhosapp%2526inner_redirect_uri%253Dhttp%25253A%25252F%25252Fssapp.ot.netease.com%25252Fhosapp-1.0%25252Fnebnlogin%25253Ffromapp%25253Dhstgc%252526regid%25253Djbqbx9b8oppor918615999d828w1ywps%2525252Cniepush%2525252Ccom.netease.hearthstoneapp%252526subscriber%25253D5f4be99ccd84026b38ab9b2cf96efc99&app=oauth";
	
	/**
	 * 用户详细信息网址
	 */
	public static final String USERINFODETAIL_URL = "http://ssapp.ot.netease.com:80/hosapp-1.0/getssprofile";
	
	/**
	 * 验证sessionid是否正确网址
	 */
	public static final String SESSION_URL = "https://hstgcep.ot.netease.com:443/hstgcep-1.0/comment/msgcnt?sessionid=";
	
}
