package hstest;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginParam {

	/**
	 * 网页响应内容
	 */
	private String msg = null;

	/**
	 * 密码
	 */
	private String password = null;

	/**
	 * 登录部分参数 publicA, clientEvidenceM1 由username, pass, modulus, generator, salt获得
	 * 其中，username、modulus、generator、salt为网页响应内容 pass为用户输入的密码
	 */
	private String[] param = new String[2];

	/**
	 * 构造方法
	 * 
	 * @param msg
	 */
	public LoginParam(String msg, String password) {
		this.msg = msg;
		this.password = password;

		this.param = Evidence.handleAction(get_username(), get_pass(), get_modulus(), get_generator(), get_salt(),
				get_publicB());
	}

	/**
	 * 获得密码
	 * 
	 * @return pass
	 */
	public String get_pass() {
		String pass = this.password;
		return pass;
	}

	/**
	 * 网页响应内容数组化
	 * 
	 * @return jsonArray
	 */
	public JSONArray toJSONArray() {
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray("[" + msg + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	/**
	 * 获得参数modules
	 * 
	 * @return modules
	 */
	public String get_modulus() {
		String modulus = null;
		try {
			modulus = (String) toJSONArray().getJSONObject(0).get("modulus");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return modulus;
	}

	/**
	 * 获得参数generator
	 * 
	 * @return generator
	 */
	public String get_generator() {
		String generator = null;
		try {
			generator = (String) toJSONArray().getJSONObject(0).get("generator");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return generator;
	}

	/**
	 * 获得参数username 此usename为网页响应内容返回的username，并非用户输入的accountName
	 * 
	 * @return username
	 */
	public String get_username() {
		String username = null;
		try {
			username = (String) toJSONArray().getJSONObject(0).get("username");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return username;
	}

	/**
	 * 获得参数salt
	 * 
	 * @return salt
	 */
	public String get_salt() {
		String salt = null;
		try {
			salt = (String) toJSONArray().getJSONObject(0).get("salt");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return salt;
	}

	/**
	 * 获得参数publicB
	 * 
	 * @return publicB
	 */
	public String get_publicB() {
		String publicB = null;
		try {
			publicB = (String) toJSONArray().getJSONObject(0).get("public_B");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return publicB;
	}

	/**
	 * 获得参数csrftoken
	 * 
	 * @return csrftoken
	 */
	public String get_csrftoken() {
		String csrf_token = null;
		try {
			csrf_token = (String) toJSONArray().getJSONObject(0).get("csrf_token");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return csrf_token;
	}

	/**
	 * 获得参数publicA
	 * 
	 * 公钥
	 * 
	 * @return publicA
	 */
	public String get_publicA() {
		String publicA = null;
		publicA = param[0];
		return publicA;
	}

	/**
	 * 获得参数clientEvidenceM1
	 * 
	 * 凭据
	 * 
	 * @return clientEvidenceM1
	 */
	public String get_clientEvidenceM1() {
		String clientEvidenceM1 = null;
		clientEvidenceM1 = param[1];
		return clientEvidenceM1;
	}
}
