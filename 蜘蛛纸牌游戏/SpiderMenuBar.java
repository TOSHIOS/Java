package spider;

import java.awt.event.*;

import javax.swing.*;

/**
 * 生成蜘蛛纸牌游戏的菜单栏，实现菜单栏中各个组件的事件侦听。 主要包括3个模块：图形用户界面构建；组件监听接口的实现；显示可执行操作的线程。
 */

public class SpiderMenuBar extends JMenuBar implements ActionListener {

	Spider spider = null;
	// 菜单栏
	JMenu jNewGame = new JMenu("游戏");
	JMenu jGrade = new JMenu("难度等级");
	JMenu jHelp = new JMenu("帮助");
	// “游戏”菜单项
	JMenuItem jItemOpen = new JMenuItem("开局");
	JMenuItem jItemPlayAgain = new JMenuItem("重新发牌");
	JMenuItem jItemOperator = new JMenuItem("显示可行操作");
	JMenuItem jItemStopOrContinue = new JMenuItem("暂停或继续");
	JMenuItem jItemList = new JMenuItem("排行榜");
	JMenuItem jItemExit = new JMenuItem("退出");
	// “难度选择”单选框
	JRadioButtonMenuItem jItemEasy = new JRadioButtonMenuItem("简单：单一花色");
	JRadioButtonMenuItem jItemNormal = new JRadioButtonMenuItem("中级：双花色");
	JRadioButtonMenuItem jItemHard = new JRadioButtonMenuItem("高级：四花色");
	// “帮助”菜单项
	JMenuItem jItemAbout = new JMenuItem("关于");

	boolean isStopOrContinue = true;

	/*
	 * 构造方法
	 */
	public SpiderMenuBar(Spider spider) {

		this.spider = spider;
		// “游戏”菜单项
		jNewGame.add(jItemOpen);
		jNewGame.add(jItemPlayAgain);
		jNewGame.add(jItemOperator);
		jItemOpen.addActionListener(this);
		jItemPlayAgain.addActionListener(this);
		jItemOperator.addActionListener(this);
		// 分割线
		jNewGame.addSeparator();
		// 暂停或继续
		jNewGame.add(jItemStopOrContinue);
		jItemStopOrContinue.addActionListener(this);
		// 分割线
		jNewGame.addSeparator();
		// 排行榜
		jNewGame.add(jItemList);
		jItemList.addActionListener(this);
		// 分割线
		jNewGame.addSeparator();
		// 退出
		jNewGame.add(jItemExit);
		jItemExit.addActionListener(this);

		// 难度等级选择单选框
		jGrade.add(jItemEasy);
		jGrade.add(jItemNormal);
		jGrade.add(jItemHard);
		jItemEasy.addActionListener(this);
		jItemNormal.addActionListener(this);
		jItemHard.addActionListener(this);
		// 简单难度等级默认已选
		jItemEasy.setSelected(true);
		// 设置三个单选框在一个选择组内
		ButtonGroup group = new ButtonGroup();
		group.add(jItemEasy);
		group.add(jItemNormal);
		group.add(jItemHard);

		// “帮助”菜单项
		jHelp.add(jItemAbout);
		jItemAbout.addActionListener(this);

		this.add(jNewGame);
		this.add(jGrade);
		this.add(jHelp);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "开局") {
			// spider.newGame();
			spider.restartTime();
		}
		if (e.getActionCommand() == "重新发牌")
			spider.distribute();
		if (e.getActionCommand() == "显示可行操作")
			new Show().start();
		if (e.getActionCommand() == "暂停或继续") {
			if (isStopOrContinue == true) {
				spider.stopTime();
				isStopOrContinue = false;
			} else {
				spider.continueTime();
				isStopOrContinue = true;
			}
		}
		if (e.getActionCommand() == "排行榜") {
			spider.stopTime();
			String listStr[] = null;
			listStr = Login.selectDB();
			String str = null;
			for (int i = 0; i < 3; i++) {
				if (listStr[i] == null)
					break;
				if (i == 0)
					str = listStr[i] + "\n";
				else
					str = str + listStr[i] + "\n";
			}
			new List(spider, str);
		}

		if (e.getActionCommand() == "退出") {
			spider.dispose();
			System.exit(0);
		}

		if (e.getActionCommand() == "简单：单一花色") {
			spider.setGrade(Spider.EASY);
			spider.newGame();
		}
		if (e.getActionCommand() == "中级：双花色") {
			spider.setGrade(Spider.NORMAL);
			spider.newGame();
		}
		if (e.getActionCommand() == "高级：四花色") {
			spider.setGrade(Spider.HARD);
			spider.newGame();
		}

		if (e.getActionCommand() == "关于")
			new AboutDialog();
	}

	/**
	 ** 构造线程：显示可以执行的操作
	 */
	class Show extends Thread {
		public void run() {
			spider.showEnableOperator();
		}
	}
}
