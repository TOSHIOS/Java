package spider;

import java.awt.event.*;

import javax.swing.*;

/**
 * ����֩��ֽ����Ϸ�Ĳ˵�����ʵ�ֲ˵����и���������¼������� ��Ҫ����3��ģ�飺ͼ���û����湹������������ӿڵ�ʵ�֣���ʾ��ִ�в������̡߳�
 */

public class SpiderMenuBar extends JMenuBar implements ActionListener {

	Spider spider = null;
	// �˵���
	JMenu jNewGame = new JMenu("��Ϸ");
	JMenu jGrade = new JMenu("�Ѷȵȼ�");
	JMenu jHelp = new JMenu("����");
	// ����Ϸ���˵���
	JMenuItem jItemOpen = new JMenuItem("����");
	JMenuItem jItemPlayAgain = new JMenuItem("���·���");
	JMenuItem jItemOperator = new JMenuItem("��ʾ���в���");
	JMenuItem jItemStopOrContinue = new JMenuItem("��ͣ�����");
	JMenuItem jItemList = new JMenuItem("���а�");
	JMenuItem jItemExit = new JMenuItem("�˳�");
	// ���Ѷ�ѡ�񡱵�ѡ��
	JRadioButtonMenuItem jItemEasy = new JRadioButtonMenuItem("�򵥣���һ��ɫ");
	JRadioButtonMenuItem jItemNormal = new JRadioButtonMenuItem("�м���˫��ɫ");
	JRadioButtonMenuItem jItemHard = new JRadioButtonMenuItem("�߼����Ļ�ɫ");
	// ���������˵���
	JMenuItem jItemAbout = new JMenuItem("����");

	boolean isStopOrContinue = true;

	/*
	 * ���췽��
	 */
	public SpiderMenuBar(Spider spider) {

		this.spider = spider;
		// ����Ϸ���˵���
		jNewGame.add(jItemOpen);
		jNewGame.add(jItemPlayAgain);
		jNewGame.add(jItemOperator);
		jItemOpen.addActionListener(this);
		jItemPlayAgain.addActionListener(this);
		jItemOperator.addActionListener(this);
		// �ָ���
		jNewGame.addSeparator();
		// ��ͣ�����
		jNewGame.add(jItemStopOrContinue);
		jItemStopOrContinue.addActionListener(this);
		// �ָ���
		jNewGame.addSeparator();
		// ���а�
		jNewGame.add(jItemList);
		jItemList.addActionListener(this);
		// �ָ���
		jNewGame.addSeparator();
		// �˳�
		jNewGame.add(jItemExit);
		jItemExit.addActionListener(this);

		// �Ѷȵȼ�ѡ��ѡ��
		jGrade.add(jItemEasy);
		jGrade.add(jItemNormal);
		jGrade.add(jItemHard);
		jItemEasy.addActionListener(this);
		jItemNormal.addActionListener(this);
		jItemHard.addActionListener(this);
		// ���Ѷȵȼ�Ĭ����ѡ
		jItemEasy.setSelected(true);
		// ����������ѡ����һ��ѡ������
		ButtonGroup group = new ButtonGroup();
		group.add(jItemEasy);
		group.add(jItemNormal);
		group.add(jItemHard);

		// ���������˵���
		jHelp.add(jItemAbout);
		jItemAbout.addActionListener(this);

		this.add(jNewGame);
		this.add(jGrade);
		this.add(jHelp);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "����") {
			// spider.newGame();
			spider.restartTime();
		}
		if (e.getActionCommand() == "���·���")
			spider.distribute();
		if (e.getActionCommand() == "��ʾ���в���")
			new Show().start();
		if (e.getActionCommand() == "��ͣ�����") {
			if (isStopOrContinue == true) {
				spider.stopTime();
				isStopOrContinue = false;
			} else {
				spider.continueTime();
				isStopOrContinue = true;
			}
		}
		if (e.getActionCommand() == "���а�") {
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

		if (e.getActionCommand() == "�˳�") {
			spider.dispose();
			System.exit(0);
		}

		if (e.getActionCommand() == "�򵥣���һ��ɫ") {
			spider.setGrade(Spider.EASY);
			spider.newGame();
		}
		if (e.getActionCommand() == "�м���˫��ɫ") {
			spider.setGrade(Spider.NORMAL);
			spider.newGame();
		}
		if (e.getActionCommand() == "�߼����Ļ�ɫ") {
			spider.setGrade(Spider.HARD);
			spider.newGame();
		}

		if (e.getActionCommand() == "����")
			new AboutDialog();
	}

	/**
	 ** �����̣߳���ʾ����ִ�еĲ���
	 */
	class Show extends Thread {
		public void run() {
			spider.showEnableOperator();
		}
	}
}
