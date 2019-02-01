package spider;

import java.awt.*;
import javax.swing.*;

/**
 * 生成蜘蛛纸牌游戏的帮助栏。
 */

public class AboutDialog extends JDialog {

	private JPanel jPanel = new JPanel();
	private String text = "游戏规则：\n该游戏通过鼠标操作，将电脑多次分发的牌，按照相同花色由大到小排列起来，直到桌面上的牌全部消失，即为胜利。";
	private JTextArea jTextArea = new JTextArea(text);

	/*
	 * 构造方法
	 */
	public AboutDialog() {

		// 设置程序运行图标
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("images/icon.gif");
		this.setIconImage(image);

		this.setTitle("帮助");
		int sizeX = 300, sizeY = 200; // 窗口大小
		this.setSize(sizeX, sizeY);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screensize.width / 2 - sizeX / 2, screensize.height / 2 - sizeY / 2); // 窗口位置
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		Container c = this.getContentPane();
		jTextArea.setEditable(false);
		jTextArea.setSize(260, 200);
		jTextArea.setLineWrap(true); // 文本区自动换行
		jPanel.add(jTextArea);
		c.add(jPanel);
		this.setVisible(true);
	}
}
