package hstest;

import c.*;

public class Login {

	private static String accountName = "404157941@qq.com";
	private static String password = "wc2456123009";
	
	public static void main(String[] args) {

		User mUser = new User(accountName, password);

		HttpService httpService =new HttpService(mUser.getAccountName(), mUser.getPassword());

		httpService.init(); // ��ó�ʼ����
		httpService.login(); // ��¼
		httpService.getUserInfo(); // ��¼�ɹ��󣬻���û���Ϣ 
//		httpService.getUserInfoDetail(); // ����û���ϸ��Ϣ
		httpService.getSessionid(); // sessionidΪ��ȡ¯ʯ������Ҫ���ݵĲ���

		
		
	}
	
}
