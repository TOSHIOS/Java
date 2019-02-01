package hstest;

/**
 * 用户信息
 *
 */
public class User {

	/**
	 * 用户名
	 */
	private String accountName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 验证码
	 */
	private String check;

	/**
	 * 构造函数
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造函数
	 * 
	 * @param accountName
	 * @param password
	 */
	public User(String accountName, String password) {
		this.accountName = accountName;
		this.password = password;
	}

	/**
	 * 设置用户名
	 * 
	 * @param accountName
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * 设置密码
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取用户名
	 * 
	 * @return accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * 获取密码
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置验证码
	 * 
	 * @param check
	 */
	public void setCheck(String check) {
		this.check = check;
	}

	/**
	 * 获取验证码
	 * 
	 * @return check
	 */
	public String getCheck() {
		return check;
	}
}
