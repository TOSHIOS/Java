package spider;

import java.awt.*;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.plaf.synth.SynthSpinnerUI;

/**
 * 生成蜘蛛纸牌游戏的排行榜
 */

public class List extends JDialog {

	private Spider spider = null;
	private JPanel jPanel = new JPanel();
	private JTextArea jTextArea;

	/*
	 * 构造方法
	 */
	public List(Spider spider, String Text) {
		
		Text = "（仅显示前三名）\n" + Text;
		this.spider = spider;
		// 设置程序运行图标
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("images/icon.gif");
		this.setIconImage(image);

		this.setTitle("排行榜");
		int sizeX = 300, sizeY = 200; // 窗口大小
		this.setSize(sizeX, sizeY);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screensize.width / 2 - sizeX / 2, screensize.height / 2 - sizeY / 2); // 窗口位置
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		Container c = this.getContentPane();
		jTextArea = new JTextArea(Text);
		jTextArea.setEditable(false);
		jTextArea.setSize(300, 200);
		jTextArea.setLineWrap(true); // 文本区自动换行
		jPanel.add(jTextArea);
		c.add(jPanel);
		this.setVisible(true);
	}

	protected void processWindowEvent(WindowEvent e) {
		// 这里需要对进来的WindowEvent进行判断，因为，不仅会有窗口关闭的WindowEvent进来，还可能有其他的WindowEvent进来
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			int option = JOptionPane.showConfirmDialog(null, "是否继续游戏？", "游戏暂停提示", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				super.processWindowEvent(e);
				spider.continueTime();
			} else {
				// 用户选择不退出本程序，因此可以继续留在本窗口
			}
		} else {
			super.processWindowEvent(e);
		}
	}
}