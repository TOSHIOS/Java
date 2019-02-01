package hstest;

import c.*;

public class Login {

	private static String accountName = "404157941@qq.com";
	private static String password = "wc2456123009";
	
	public static void main(String[] args) {

		User mUser = new User(accountName, password);

		HttpService httpService =new HttpService(mUser.getAccountName(), mUser.getPassword());

		httpService.init(); // 获得初始参数
		httpService.login(); // 登录
		httpService.getUserInfo(); // 登录成功后，获得用户信息 
//		httpService.getUserInfoDetail(); // 获得用户详细信息
		httpService.getSessionid(); // sessionid为爬取炉石数据需要传递的参数

		
		
	}
	
}
