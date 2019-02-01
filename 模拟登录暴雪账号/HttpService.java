package hstest;

import c.*;

import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.sun.jna.platform.win32.WinUser.MSG;

public class HttpService {

	long time;
	String para1;
	String para2;

	/**
	 * �û���
	 */
	private String accountName;
	/**
	 * ����
	 */
	private String password;

	/**
	 * ��¼cookie
	 */
	 private List<HttpCookie> listCookie;

	/**
	 * ��¼��¼����
	 */
	private LoginParam loginParam;

	/**
	 * ��¼�ض�����ַ
	 */
	private String location = null;

	// private String jSessionid = null;// "06EE2B217BEF41A5E4C71AB9A14805DE";

	// sessionid��δ��ã��п���Ϊ�����ͨ����¼�ɹ����õĲ��ֲ����������ɣ����Ǵӷ�������ȡ��
	private String sessionid = null;// "3867b7e493aab1105650c6fef3af0174";

	/**
	 * ���췽��
	 * 
	 * @param accountName
	 * @param password
	 */
	public HttpService(String accountName, String password) {

		this.accountName = accountName;
		this.password = password;

		CookieManager manager = new CookieManager();
		// ����cookie���ԣ�ֻ��������Ի���������cookie����������Internet���������������͵�cookie
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		// manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
		CookieHandler.setDefault(manager);

//		 CookieStore cookieStore = manager.getCookieStore();
//		 listCookie = cookieStore.getCookies();
//		 System.out.println(listCookie);
	}

	/**
	 * ��ó�ʼ����
	 */
	public void init() {
		String path = Constant.INIT_URL;
		String data = "{\"inputs\":[{\"input_id\":\"account_name\",\"value\":\"" + accountName + "\"}]}";
		StringBuffer msg = null;
		msg = sendPostRequest(path, data);
		loginParam = new LoginParam(msg.toString(), this.password);
		System.out.println(msg);
	}

	/**
	 * ��¼
	 */
	public void login() {
		String path = Constant.LOGIN_URL;
		time = new Date().getTime();
		System.out.println(time);
		String data = "accountName=" + accountName + "&password=" + password + "&useSrp=true" + "&publicA="
				+ loginParam.get_publicA() + "&clientEvidenceM1=" + loginParam.get_clientEvidenceM1()
				+ "&persistLogin=on" + "&csrftoken=" + loginParam.get_csrftoken() + "&sessionTimeout=" + time;
		System.out.println(data);
		StringBuffer msg = null;
		msg = sendPostRequest(path, data);
		// System.out.println(msg);
	}

	/**
	 * ��¼�ɹ��󣬻���û���Ϣ
	 */
	public void getUserInfo() {
		String path = location;
		StringBuffer msg = null;
		msg = sendGetRequest(path);
		System.out.println(msg);
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray("[" + msg + "]");
			para1 = (String) jsonArray.getJSONObject(0).get("nimps");
			para2 = (String) jsonArray.getJSONObject(0).get("ps");
			System.out.println("nimps:" + para1 + "\n" + "ps:" + para2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����û���ϸ��Ϣ
	 */
	public void getUserInfoDetail() {
		String path = Constant.USERINFODETAIL_URL;
		StringBuffer msg = null;
		msg = sendGetRequest(path);
		while (msg.substring(0, 5).equals("login")) {
			msg = sendGetRequest(path);
		}
		System.out.println(msg);
	}

	/**
	 * ��֤sessionid�Ƿ���ȷ
	 */
	public void getSessionid() {

		String s = c.a(String.valueOf(time), para1, para2);
		System.out.println(s);
		String url = "https://hstgcep.ot.netease.com/hstgcep-1.0/signin?ts=" + String.valueOf(time) + "&sign=" + s
				+ "&uname=" + para1 + "&token=" + para2;
		System.out.println(url);
		StringBuffer m = null;
		m = sendGetRequest(url);
		System.out.println(m);
		
		System.out.println(sessionid);
		String path = Constant.SESSION_URL + m;
		StringBuffer msg = null;
		msg = sendGetRequest(path);
		System.out.println(msg);
	}

	public StringBuffer sendPostRequest(String path, String data) {
		URL url = null;
		StringBuffer msg = null;
		try {
			url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Linux; Android 6.0.1; OPPO R9 Plusm A Build/MMB29M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36");
			// conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Length", data.length() + "");
			conn.setRequestProperty("Connection", "keep-alive");

			// ��ͬ��ַ�ύ�������ڲ���
			if (path.equals(Constant.INIT_URL)) {
				conn.setRequestProperty("Content-Type", "application/json");
			} else if (path.equals(Constant.LOGIN_URL)) {
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			}

			conn.setDoOutput(true);
			conn.getOutputStream().write(data.getBytes());

			// ��ȡ��Ӧ��
			int code = conn.getResponseCode();
			System.out.println("��Ӧ��:" + code);
			// ���������Ϊ200
			if (code == 200) {
				InputStream in = conn.getInputStream();
				msg = transStream(in);
				return msg;
			}
			if (code == 302) {
				location = conn.getHeaderField("Location");
				System.out.println("location: " + location);
				System.out.println("��¼�ɹ���\n���ڻ�ȡ�û���Ϣ...");
				return msg;
			}
			// System.out.println(conn.getHeaderFields());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public StringBuffer sendGetRequest(String path) {
		URL url = null;
		StringBuffer msg = null;
		try {
			url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Linux; Android 6.0.1; OPPO R9 Plusm A Build/MMB29M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);
			// ��ȡ��Ӧ��
			int code = conn.getResponseCode();
			System.out.println("��Ӧ��:" + code);
			// ���������Ϊ200
			if (code == 200) {
				InputStream in = conn.getInputStream();
				msg = transStream(in);
				return msg;
			}
			if (code == 403) {
				msg = sendGetRequest(path);
				return msg;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ��Ӧ����
	 * 
	 * @param in
	 * @return
	 */
	public StringBuffer transStream(InputStream in) {
		StringBuffer sb = new StringBuffer();
		// ��ȡ��Ӧ����
		try {
			if (in != null) {
				int len = -1;
				byte[] data = new byte[1024];
				while ((len = in.read(data)) != -1) {
					String s = new String(data, 0, len, "UTF-8");
					sb.append(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb;
	}

}
