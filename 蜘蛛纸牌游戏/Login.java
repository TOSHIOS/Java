package spider;

import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.Timer;

import javax.swing.*;
import javax.swing.UIManager.*;

import java.sql.*;

public class Login extends JFrame implements ActionListener {

	public static String NAME;
	private Container pane = null;
	private JTextField loginField;
	private JTextField pwdField;
	private JButton loginButton;
	private JButton registerButton;

	private static Connection cn;

	public Login() {
		// 设置程序运行图标
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("images/icon.gif");
		this.setIconImage(image);
		// 界面属性设置
		this.setTitle("蜘蛛纸牌登录界面");
		int sizeX = 400, sizeY = 300; // 窗口大小
		this.setSize(sizeX, sizeY);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screensize.width / 2 - sizeX / 2, screensize.height / 2 - sizeY / 2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		pane = this.getContentPane();
		pane.setLayout(null);

		JLabel loginLabel = new JLabel("登录名");
		loginLabel.setBounds(95, 67, 72, 18);
		loginLabel.setForeground(Color.YELLOW);
		pane.add(loginLabel);

		loginField = new JTextField(20);
		loginField.setBounds(202, 64, 86, 25);
		pane.add(loginField);
		loginField.setColumns(10);

		JLabel pwdLabel = new JLabel("密  码");
		pwdLabel.setForeground(Color.YELLOW);
		pwdLabel.setBounds(95, 118, 72, 18);
		pane.add(pwdLabel);

		pwdField = new JPasswordField(20);
		pwdField.setBounds(202, 115, 86, 25);
		pane.add(pwdField);

		loginButton = new JButton("登录");
		loginButton.setBounds(90, 180, 83, 27);
		loginButton.addActionListener(this);
		pane.add(loginButton);

		registerButton = new JButton("注册");
		registerButton.setBounds(200, 180, 83, 27);
		registerButton.addActionListener(this);
		pane.add(registerButton);

		ImageIcon imageBackground = new ImageIcon("images/background.gif");
		JLabel background = new JLabel(imageBackground);
		background.setOpaque(false);
		pane.add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, 400, 300);
		
		this.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
		new Login();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String loginStr = loginField.getText();
		String pwdStr = pwdField.getText();
		// String loginStr = "admin";
		// String pwdStr = "123456";
		NAME = loginStr;
		joinDB();
		if (e.getActionCommand() == "登录") {
			if (runDB(loginStr, pwdStr) && !loginStr.equals("") && !pwdStr.equals("")) {
				JOptionPane.showMessageDialog(this, "登录成功！", "登录", JOptionPane.PLAIN_MESSAGE);
				Spider spider = new Spider();
				spider.setVisible(true);
			} else
				JOptionPane.showMessageDialog(this, "登录失败！\n请重新登录！", "登录", JOptionPane.PLAIN_MESSAGE);
		}
		if (e.getActionCommand() == "注册" && !loginStr.equals("") && !pwdStr.equals("")) {
			if (insertDB(loginStr, pwdStr))
				JOptionPane.showMessageDialog(this, "注册成功！\n请重新登录！", "注册", JOptionPane.PLAIN_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "注册失败！\n用户名重复！\n请重新注册！", "注册", JOptionPane.PLAIN_MESSAGE);
		}

	}

	public boolean joinDB() {
		String urlwindows = "jdbc:sqlserver://127.0.0.1:1433;databaseName=spider ;user=sa;password=123456";
		try {
			// 建立连接
			System.out.println("准备连接！！！");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			cn = DriverManager.getConnection(urlwindows);
			System.out.println("连接成功！！！");

			return true;
		} catch (Exception e) {
			System.out.println("连接失败！！！");
			// e.printStackTrace();
		}

		return false;
	}

	public boolean runDB(String loginStr, String pwdStr) {
		try {
			String sql = "select * from spider_table where login = '" + loginStr + "' and pwd = '" + pwdStr + "'";
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (!rs.next())
				return false;
			while (rs.next()) {
				String s = rs.getString("login");
				System.out.println(s);
			}
			System.out.println("运行sql语句成功");
			return true;
		} catch (Exception e) {
			System.out.println("运行sql语句失败");
			// e.printStackTrace();
		}
		return false;
	}

	public boolean insertDB(String loginStr, String pwdStr) {
		try {
			String sql = "insert into spider_table values('" + loginStr + "' , '" + pwdStr + "' , NULL)";
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			System.out.println("运行sql语句成功");
			return true;
		} catch (Exception e) {
			System.out.println("运行sql语句失败");
			// e.printStackTrace();
		}
		return false;
	}

	public static boolean updateDB(String recordStr) {
		try {
			String sql = "update spider_table set record = '" + recordStr + "' where login = '" + Login.NAME + "'";
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			System.out.println("运行sql语句成功");
			return true;
		} catch (Exception e) {
			System.out.println("运行sql语句失败");
			// e.printStackTrace();
		}
		return false;
	}

	public static String[] selectDB() {
		String[] str = new String[100];
		try {
			String sql = "select login,record from spider_table where record is not NULL order by record";
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int i = 0;
			System.out.println("s");
			while (rs.next()) {
				String login = rs.getString("login");
				String record = rs.getString("record");
				int j = i + 1;
				str[i] = "   " + j + "、" + login + "\t: " + record;
				i++;
			}
			System.out.println("运行sql语句成功");
			return str;
		} catch (Exception e) {
			System.out.println("运行sql语句失败");
			// e.printStackTrace();
		}
		return null;
	}
}
