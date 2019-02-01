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
		// ���ó�������ͼ��
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("images/icon.gif");
		this.setIconImage(image);
		// ������������
		this.setTitle("֩��ֽ�Ƶ�¼����");
		int sizeX = 400, sizeY = 300; // ���ڴ�С
		this.setSize(sizeX, sizeY);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screensize.width / 2 - sizeX / 2, screensize.height / 2 - sizeY / 2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		pane = this.getContentPane();
		pane.setLayout(null);

		JLabel loginLabel = new JLabel("��¼��");
		loginLabel.setBounds(95, 67, 72, 18);
		loginLabel.setForeground(Color.YELLOW);
		pane.add(loginLabel);

		loginField = new JTextField(20);
		loginField.setBounds(202, 64, 86, 25);
		pane.add(loginField);
		loginField.setColumns(10);

		JLabel pwdLabel = new JLabel("��  ��");
		pwdLabel.setForeground(Color.YELLOW);
		pwdLabel.setBounds(95, 118, 72, 18);
		pane.add(pwdLabel);

		pwdField = new JPasswordField(20);
		pwdField.setBounds(202, 115, 86, 25);
		pane.add(pwdField);

		loginButton = new JButton("��¼");
		loginButton.setBounds(90, 180, 83, 27);
		loginButton.addActionListener(this);
		pane.add(loginButton);

		registerButton = new JButton("ע��");
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
		if (e.getActionCommand() == "��¼") {
			if (runDB(loginStr, pwdStr) && !loginStr.equals("") && !pwdStr.equals("")) {
				JOptionPane.showMessageDialog(this, "��¼�ɹ���", "��¼", JOptionPane.PLAIN_MESSAGE);
				Spider spider = new Spider();
				spider.setVisible(true);
			} else
				JOptionPane.showMessageDialog(this, "��¼ʧ�ܣ�\n�����µ�¼��", "��¼", JOptionPane.PLAIN_MESSAGE);
		}
		if (e.getActionCommand() == "ע��" && !loginStr.equals("") && !pwdStr.equals("")) {
			if (insertDB(loginStr, pwdStr))
				JOptionPane.showMessageDialog(this, "ע��ɹ���\n�����µ�¼��", "ע��", JOptionPane.PLAIN_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "ע��ʧ�ܣ�\n�û����ظ���\n������ע�ᣡ", "ע��", JOptionPane.PLAIN_MESSAGE);
		}

	}

	public boolean joinDB() {
		String urlwindows = "jdbc:sqlserver://127.0.0.1:1433;databaseName=spider ;user=sa;password=123456";
		try {
			// ��������
			System.out.println("׼�����ӣ�����");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			cn = DriverManager.getConnection(urlwindows);
			System.out.println("���ӳɹ�������");

			return true;
		} catch (Exception e) {
			System.out.println("����ʧ�ܣ�����");
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
			System.out.println("����sql���ɹ�");
			return true;
		} catch (Exception e) {
			System.out.println("����sql���ʧ��");
			// e.printStackTrace();
		}
		return false;
	}

	public boolean insertDB(String loginStr, String pwdStr) {
		try {
			String sql = "insert into spider_table values('" + loginStr + "' , '" + pwdStr + "' , NULL)";
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			System.out.println("����sql���ɹ�");
			return true;
		} catch (Exception e) {
			System.out.println("����sql���ʧ��");
			// e.printStackTrace();
		}
		return false;
	}

	public static boolean updateDB(String recordStr) {
		try {
			String sql = "update spider_table set record = '" + recordStr + "' where login = '" + Login.NAME + "'";
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			System.out.println("����sql���ɹ�");
			return true;
		} catch (Exception e) {
			System.out.println("����sql���ʧ��");
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
				str[i] = "   " + j + "��" + login + "\t: " + record;
				i++;
			}
			System.out.println("����sql���ɹ�");
			return str;
		} catch (Exception e) {
			System.out.println("����sql���ʧ��");
			// e.printStackTrace();
		}
		return null;
	}
}
