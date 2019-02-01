package spider;

import java.awt.*;
import javax.swing.*;

/**
 * ����֩��ֽ����Ϸ�İ�������
 */

public class AboutDialog extends JDialog {

	private JPanel jPanel = new JPanel();
	private String text = "��Ϸ����\n����Ϸͨ���������������Զ�ηַ����ƣ�������ͬ��ɫ�ɴ�С����������ֱ�������ϵ���ȫ����ʧ����Ϊʤ����";
	private JTextArea jTextArea = new JTextArea(text);

	/*
	 * ���췽��
	 */
	public AboutDialog() {

		// ���ó�������ͼ��
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("images/icon.gif");
		this.setIconImage(image);

		this.setTitle("����");
		int sizeX = 300, sizeY = 200; // ���ڴ�С
		this.setSize(sizeX, sizeY);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screensize.width / 2 - sizeX / 2, screensize.height / 2 - sizeY / 2); // ����λ��
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		Container c = this.getContentPane();
		jTextArea.setEditable(false);
		jTextArea.setSize(260, 200);
		jTextArea.setLineWrap(true); // �ı����Զ�����
		jPanel.add(jTextArea);
		c.add(jPanel);
		this.setVisible(true);
	}
}
