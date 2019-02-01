package hstest;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginParam {

	/**
	 * ��ҳ��Ӧ����
	 */
	private String msg = null;

	/**
	 * ����
	 */
	private String password = null;

	/**
	 * ��¼���ֲ��� publicA, clientEvidenceM1 ��username, pass, modulus, generator, salt���
	 * ���У�username��modulus��generator��saltΪ��ҳ��Ӧ���� passΪ�û����������
	 */
	private String[] param = new String[2];

	/**
	 * ���췽��
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
	 * �������
	 * 
	 * @return pass
	 */
	public String get_pass() {
		String pass = this.password;
		return pass;
	}

	/**
	 * ��ҳ��Ӧ�������黯
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
	 * ��ò���modules
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
	 * ��ò���generator
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
	 * ��ò���username ��usenameΪ��ҳ��Ӧ���ݷ��ص�username�������û������accountName
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
	 * ��ò���salt
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
	 * ��ò���publicB
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
	 * ��ò���csrftoken
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
	 * ��ò���publicA
	 * 
	 * ��Կ
	 * 
	 * @return publicA
	 */
	public String get_publicA() {
		String publicA = null;
		publicA = param[0];
		return publicA;
	}

	/**
	 * ��ò���clientEvidenceM1
	 * 
	 * ƾ��
	 * 
	 * @return clientEvidenceM1
	 */
	public String get_clientEvidenceM1() {
		String clientEvidenceM1 = null;
		clientEvidenceM1 = param[1];
		return clientEvidenceM1;
	}
}
