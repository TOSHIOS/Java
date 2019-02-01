package spider;

import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.*;
import javax.swing.UIManager.*;
import javax.swing.border.LineBorder;

/**
 * 生成蜘蛛纸牌游戏框架，实现游戏中方法，包括纸牌的随机生成、位置摆放等。
 */

public class Spider extends JFrame implements MouseListener {

	private Container pane = null;

	// 难度等级为：简单
	public static final int EASY = 1;
	// 难度等级为：普通
	public static final int NORMAL = 2;
	// 难度等级为：难
	public static final int HARD = 3;
	// 设定初始难度等级为简单
	private int grade = Spider.EASY;
	// 生成纸牌数组
	private PKCard cards[] = new PKCard[104]; // 四色八组牌，共104张牌
	private JLabel groundLabel[] = null; // 每列初始牌
	private boolean isFront; // 标识纸牌正反面
	// 发牌按钮
	private JLabel distributeLabel = null;
	private int distributeLabelX = 860, distributeLabelY = 560;
	// 列数
	private int columnCards = 10;
	private int colorNumber = 4; // 纸牌花色数量

	// 已发牌数量
	private int distributedNumber = 0;
	private int columnEach = 0; // 列数循环参数
	private int columnN = 0; // 列数参数

	// 键值对，键为像素坐标，值为纸牌名称
	Hashtable table = null;

	// 存储已经完成纸牌组数
	private int finishGroup = 0;

	public static Time time;
	private JLabel timeLabel;
	/**
	 * 构造方法
	 */
	public Spider() {
		// 设置程序运行图标
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("images/icon.gif");
		this.setIconImage(image);
		// 界面属性设置
		this.setTitle("蜘蛛纸牌 - " + Login.NAME);
		int sizeX = 1024, sizeY = 742; // 窗口大小
		this.setSize(sizeX, sizeY);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screensize.width / 2 - sizeX / 2, screensize.height / 2 - sizeY / 2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setJMenuBar(new SpiderMenuBar(this));
		pane = this.getContentPane();
		// 设置背景颜色
		pane.setBackground(new Color(0, 112, 26));
		pane.setLayout(null);

		distributeLabel = new JLabel();
		distributeLabel.setBounds(distributeLabelX, distributeLabelY, 121, 96);
		distributeLabel.addMouseListener(this);
		pane.add(distributeLabel);
		
		timeLabel = new JLabel("时间");
		Font font = new Font("黑体", Font.PLAIN, 25);
		timeLabel.setFont(font);
		timeLabel.setBounds(sizeX / 2 - 50, sizeY - 130, 100, 30);
		pane.add(timeLabel);

		newGame();
		
		pane.addMouseListener(this);

		this.setVisible(true);
	}

	public void restartTime() {
		this.dispose();
		time.restartTime();
	}
	public void stopTime() {
		time.stopTime();
	}
	public void continueTime() {
		time.continueTime();
	}

	public void mouseClicked(MouseEvent e) {
		this.distribute();
		// System.out.println("已发牌");
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	/**
	 * 分发纸牌
	 */
	public void distribute() {
		columnN = 0;
		columnEach = 0;
		for (int i = 0; i < 10; i++) {
			// 牌堆没牌时返回空
			if (distributedNumber + i >= 104) {
				distributedNumber = 104;
				return;
			}
			final Point lastPoint = this.getLastCardLocation(i);
			lastPoint.y += 20; // 获得纸牌的终止位置

			final Point firstPoint = cards[distributedNumber + i].getLocation(); // 获得纸牌的起始位置

			// final int ii = i;
			// final Point pp = firstPoint;
			// final int speedX = (firstPoint.x - lastPoint.x) / 100;
			// final int speedY = (firstPoint.y - lastPoint.y) / 100;
			// final int flag = 0;
			// pp.x -= speedX;// 计算移动后的位置
			// pp.y -= speedY;
			// if (pp.x <= lastPoint.x || pp.y <= lastPoint.y) {// 如果超过就指定像素,就中止移动
			// cards[distributedNumber + ii].moveto(lastPoint);
			// //flag=1;
			// }
			// cards[distributedNumber + ii].setLocation(pp.x, pp.y);// 更新位置
			// try{
			// Thread.currentThread().sleep(25);
			// }catch(InterruptedException ie){
			// ie.printStackTrace();
			// }

			// final int ii = i;
			// final Point pp = firstPoint;
			// final int speedX = (firstPoint.x - lastPoint.x) / 100;
			// final int speedY = (firstPoint.y - lastPoint.y) / 100;
			// final int flag = 0;
			// Timer t = new Timer(5, new ActionListener() {
			// public void actionPerformed(ActionEvent e) {
			// synchronized(pane) {
			// pp.x -= speedX;// 计算移动后的位置
			// pp.y -= speedY;
			// if (pp.x <= lastPoint.x || pp.y <= lastPoint.y) {// 如果超过就指定像素,就中止移动
			// table.remove(firstPoint);
			// cards[distributedNumber + ii].moveto(lastPoint);
			// table.put(lastPoint, cards[distributedNumber + ii]);
			// System.gc();
			// }
			// cards[distributedNumber + ii].setLocation(pp.x, pp.y);// 更新位置
			// // repaint();
			// }
			// }
			// });
			// t.start();
			// if (flag==1) {// 如果超过就指定像素,就中止移动
			// t.stop();
			// }

			table.remove(cards[distributedNumber + i].getLocation());
			cards[distributedNumber + i].moveto(lastPoint);
			table.put(lastPoint, cards[distributedNumber + i]);

			if (isFront == false)
				;
			if (isFront == true)
				cards[distributedNumber + i].turnFrontSide();
			cards[distributedNumber + i].isCanMove(true);
			// 依次遮盖第一张纸牌
			this.pane.setComponentZOrder(cards[distributedNumber + i], 1);
		}
		distributedNumber += 10;
	}

	/**
	 * 取得第column列最后一张牌的位置
	 */
	public Point getLastCardLocation(int column) {
		Point point = new Point(20 + column * 101, 25);
		PKCard card = (PKCard) this.table.get(point);
		// 无最后一张牌返回列固定值
		if (card == null) {
			point.x = 20 + column * 101;
			point.y = 25 - 20;
			return point;
		}
		// 取得第column列最后一张牌的位置
		while (card != null) {
			point = card.getLocation();
			card = this.getNextCard(card);
		}
		return point;
	}

	/**
	 * 计算card下方的一张牌的位置
	 */
	public PKCard getNextCard(PKCard card) {
		Point point = new Point(card.getLocation());
		point.y += 20;
		card = (PKCard) table.get(point);
		return card;
	}

	/**
	 * 计算card上方的一张牌的位置
	 */
	public PKCard getPreviousCard(PKCard card) {
		Point point = new Point(card.getLocation());
		point.y -= 20; // 计算card上方的牌的纵坐标
		card = (PKCard) table.get(point);
		return card;
	}

	/**
	 * 开始游戏
	 */
	public void newGame() {
		this.initCards();
		this.setCardsLocation();
		isFront = false;
		// 预先发放4轮背面向上的纸牌
		for (int i = 0; i < 4; i++)
			this.distribute();
		isFront = true;
		// 再发放1轮正面向上的纸牌
		this.distribute();

		JOptionPane.showMessageDialog(this, "开始游戏？", "开始", JOptionPane.PLAIN_MESSAGE);
		time = new Time(timeLabel);
	}

	/**
	 * 纸牌初始赋值
	 */
	public void initCards() {
		// 如果纸牌已被赋值，即将其从框架的面板中移去
		if (cards[0] != null) {
			for (int i = 0; i < 104; i++) {
				pane.remove(cards[i]);
			}
		}

		// 通过难度等级，为colorNumber花色数量赋值
		if (this.grade == Spider.EASY) {
			colorNumber = 1;
		} else if (this.grade == Spider.NORMAL) {
			colorNumber = 2;
		} else {
			colorNumber = 4;
		}

		// 八组纸牌
		for (int i = 1; i <= 8; i++) {
			// 每组13张牌，A ~ K
			for (int j = 1; j <= 13; j++) {
				// (i % colorNumber + 1)为纸牌图片文件名第一属性（1~4），j为第二属性（1~13）
				cards[(i - 1) * 13 + j - 1] = new PKCard((i % colorNumber + 1) + "-" + j, this);
			}
		}
		this.randomCards();
	}

	/**
	 * 随机洗牌
	 */
	public void randomCards() {
		// 104张纸牌随机两两交换，共交换52次
		for (int i = 0; i < 52; i++) {
			int a = (int) (Math.random() * 104);
			int b = (int) (Math.random() * 104);
			PKCard temp = cards[a];
			cards[a] = cards[b];
			cards[b] = temp;
		}
	}

	/**
	 ** 设置难度等级
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * 设置纸牌的位置 利用键值对，键为像素坐标，值为纸牌名称
	 */
	public void setCardsLocation() {
		distributedNumber = 0;
		columnEach = 0;
		columnN = 0;
		finishGroup = 0;
		table = new Hashtable();
		int x = distributeLabelX;
		int y = distributeLabelY;
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 10; j++) {
				int n = i * 10 + j;
				if (n >= 104)
					continue;
				pane.add(cards[n]);
				cards[n].moveto(new Point(x, y));
				table.put(new Point(x, y), cards[n]);
			}
			x += 10;
		}
	}

	/**
	 * 获得该列的初始纸牌位置
	 */
	public Point getColumnFirstLocation(int column) {
		return new Point(groundLabel[column].getLocation());
	}

	/**
	 * 判断游戏是否结束
	 * 
	 * @param column
	 */
	public void isFinish(int column) {
		Point point = this.getLastCardLocation(column);
		PKCard card = (PKCard) this.table.get(point);
		do {
			this.table.remove(point);
			card.moveto(new Point(20 + finishGroup * 10, 580));
			// 将组件移动到容器中指定的顺序索引，依次覆盖
			pane.setComponentZOrder(card, 1);
			// 将纸牌新的相关信息存入Hashtable
			this.table.put(card.getLocation(), card);
			card.isCanMove(false);
			point = this.getLastCardLocation(column);
			if (point == null)
				card = null;
			else
				card = (PKCard) this.table.get(point);
		} while (card != null && card.getCanMove());
		finishGroup++;
		// 如果8付牌全部组合成功，则显示成功的对话框
		if (finishGroup == 8) {
			Login.updateDB(timeLabel.getText());
			time.stopTime();
			JOptionPane.showMessageDialog(this, "恭喜你，顺利通过！\n" + "用户名："+ Login.NAME  + "\n通过时间：" + timeLabel.getText(), "成功",
					JOptionPane.PLAIN_MESSAGE);

		}
		if (card != null) {
			card.turnFrontSide();
			card.isCanMove(true);
		}
	}

	/**
	 * 显示可移动的操作
	 */
	public void showEnableOperator() {
		int x = 0; // 所在列数标记
		out: while (true) {
			Point point = null;
			PKCard card = null;
			// 获得每一列最下方的纸牌
			do {
				if (point != null) {
					columnN++;
				}
				point = this.getLastCardLocation(columnN);
				while (point == null || point.y == 5) {
					point = this.getLastCardLocation(++columnN);
					if (columnN == 10)
						columnN = 0;
					x++;
					if (x == 10)
						break out;
				}
				card = (PKCard) this.table.get(point);
			} while (!card.getCanMove());
			// 获得能与该列最下方的纸牌同时移动的纸牌，直至获得不能同时移动的纸牌
			while (this.getPreviousCard(card) != null && this.getPreviousCard(card).getCanMove()) {
				card = this.getPreviousCard(card);
			}
			if (columnEach == 10) {
				columnEach = 0;
			}
			// 循环10列
			for (; columnEach < 10; columnEach++) {
				if (columnEach != columnN) {
					Point p = null;
					PKCard c = null;
					do {
						if (p != null) {
							columnEach++;
						}
						p = this.getLastCardLocation(columnEach);
						int z = 0;
						while (p == null) {
							p = this.getLastCardLocation(++columnEach);
							if (columnEach == 10)
								columnEach = 0;
							if (columnEach == columnN)
								columnEach++;
							z++;
							if (z == 10)
								break out;
						}
						c = (PKCard) this.table.get(p);
					} while (!c.getCanMove());
					// 如果花色点数符合规则，则启动提示线程
					if (c.getCardValue() == card.getCardValue() + 1 && c.getCardType() == card.getCardType()) {
						card.flashCard(card);
						try {
							Thread.sleep(800);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						c.flashCard(c);
						columnEach++;
						if (columnEach == 10) {
							columnN++;
						}
						break out;
					}
				}
			}
			if (distributedNumber > 104)
				JOptionPane.showMessageDialog(this, "抱歉！通关失败！", "失败", JOptionPane.PLAIN_MESSAGE);
			columnN++;
			if (columnN == 10) {
				columnN = 0;
			}
			x++;
			if (x == 10) {
				break out;
			}
		}
	}
}
