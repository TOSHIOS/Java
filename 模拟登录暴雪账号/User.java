package hstest;

/**
 * �û���Ϣ
 *
 */
public class User {

	/**
	 * �û���
	 */
	private String accountName;

	/**
	 * ����
	 */
	private String password;

	/**
	 * ��֤��
	 */
	private String check;

	/**
	 * ���캯��
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * ���캯��
	 * 
	 * @param accountName
	 * @param password
	 */
	public User(String accountName, String password) {
		this.accountName = accountName;
		this.password = password;
	}

	/**
	 * �����û���
	 * 
	 * @param accountName
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * ��������
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * ��ȡ�û���
	 * 
	 * @return accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * ��ȡ����
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * ������֤��
	 * 
	 * @param check
	 */
	public void setCheck(String check) {
		this.check = check;
	}

	/**
	 * ��ȡ��֤��
	 * 
	 * @return check
	 */
	public String getCheck() {
		return check;
	}
}
