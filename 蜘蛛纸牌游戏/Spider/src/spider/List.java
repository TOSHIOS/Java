package spider;

import java.awt.*;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.plaf.synth.SynthSpinnerUI;

/**
 * ����֩��ֽ����Ϸ�����а�
 */

public class List extends JDialog {

	private Spider spider = null;
	private JPanel jPanel = new JPanel();
	private JTextArea jTextArea;

	/*
	 * ���췽��
	 */
	public List(Spider spider, String Text) {
		
		Text = "������ʾǰ������\n" + Text;
		this.spider = spider;
		// ���ó�������ͼ��
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("images/icon.gif");
		this.setIconImage(image);

		this.setTitle("���а�");
		int sizeX = 300, sizeY = 200; // ���ڴ�С
		this.setSize(sizeX, sizeY);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screensize.width / 2 - sizeX / 2, screensize.height / 2 - sizeY / 2); // ����λ��
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		Container c = this.getContentPane();
		jTextArea = new JTextArea(Text);
		jTextArea.setEditable(false);
		jTextArea.setSize(300, 200);
		jTextArea.setLineWrap(true); // �ı����Զ�����
		jPanel.add(jTextArea);
		c.add(jPanel);
		this.setVisible(true);
	}

	protected void processWindowEvent(WindowEvent e) {
		// ������Ҫ�Խ�����WindowEvent�����жϣ���Ϊ���������д��ڹرյ�WindowEvent��������������������WindowEvent����
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			int option = JOptionPane.showConfirmDialog(null, "�Ƿ������Ϸ��", "��Ϸ��ͣ��ʾ", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				super.processWindowEvent(e);
				spider.continueTime();
			} else {
				// �û�ѡ���˳���������˿��Լ������ڱ�����
			}
		} else {
			super.processWindowEvent(e);
		}
	}
}