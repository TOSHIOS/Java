package spider;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * 定义纸牌的属性，包括名称、位置等相关信息；通过相关方法实现纸牌移动功能。
 */

public class PKCard extends JLabel implements MouseListener, MouseMotionListener {

	// 鼠标点击的初始位置
	private Point mousePoint = null;
	// 鼠标点击的纸牌的初始位置
	private Point cardInitPoint = null;
	// 纸牌文件名有效部分
	private String cardImgName = null;
	// 纸牌文件名花色
	int cardType = 0;
	// 纸牌文件名点数
	int cardValue = 0;
	// 上一张纸牌对象
	PKCard previousCard = null;

	// 纸牌对象
	private Spider spider = null;
	private Container pane = null;
	private boolean canMove = false;
	private boolean isFront = false;

	/**
	 * 将纸牌移动到点point
	 */
	public void moveto(Point point) {
		this.setLocation(point);
	}

	/**
	 * 构造方法
	 */
	public PKCard(String name, Spider spider) {

		super();
		this.cardImgName = name;
		this.cardType = new Integer(name.substring(0, 1)).intValue();
		this.cardValue = new Integer(name.substring(2)).intValue();
		this.spider = spider;
		this.pane = this.spider.getContentPane();
		this.canMove = false;
		this.isFront = false;
		this.setIcon(new ImageIcon("images/rear.gif"));
		this.setSize(71, 96);
		this.setLocation(50, 50);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setVisible(true);
	}

	/**
	 * 返回能否移动
	 */
	public boolean getCanMove() {
		return this.canMove;
	}

	/**
	 * 鼠标拖动纸牌
	 */
	public void mouseDragged(MouseEvent e) {
		if (canMove) {
			int x = 0;
			int y = 0;
			Point p = e.getPoint();
			x = p.x - mousePoint.x;
			y = p.y - mousePoint.y;
			this.moving(x, y);
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * 鼠标点击纸牌
	 */
	public void mousePressed(MouseEvent e) {
		mousePoint = e.getPoint();
		cardInitPoint = this.getLocation();
		this.previousCard = spider.getPreviousCard(this);
	}

	/**
	 * 鼠标释放纸牌
	 */
	public void mouseReleased(MouseEvent e) {
		if (!this.canMove)
			return;

		// 获得鼠标释放纸牌的位置
		Point point = ((JLabel) e.getSource()).getLocation();
		// 判断鼠标释放位置是否属于某一列
		int n = this.isWhichColumn(point);
		if (n == -1 || n == this.isWhichColumn(this.cardInitPoint)) {
			this.setNextCardLocation(null);
			spider.table.remove(this.getLocation());
			this.setLocation(this.cardInitPoint);
			spider.table.put(this.cardInitPoint, this);
			return;
		}

		point = spider.getLastCardLocation(n);
		boolean isEmpty = false; // 该列初始纸牌是否存在，初始假设存在
		PKCard card = null;
		if (point == null) {
			point = spider.getColumnFirstLocation(n);
			isEmpty = true;
		} else {
			card = (PKCard) spider.table.get(point);
		}

		if (spider.getLastCardLocation(n).y == 5) {
			point.y = 25;
			isEmpty = true;
		}

		// 如果该列初始纸牌不存在，或者该列初始纸牌存在，且该牌的点数比上一张牌的点数小一点，说明可以被放置
		// this为正在被移动的纸牌，card为接受移动纸牌的最下方纸牌
		if (isEmpty || (this.cardValue + 1 == card.getCardValue())) {
			point.y += 40;
			if (isEmpty)
				point.y -= 20;
			this.setNextCardLocation(point);
			spider.table.remove(this.getLocation());
			point.y -= 20;
			this.setLocation(point);
			spider.table.put(point, this);
			this.cardInitPoint = point;
			// 如果该牌的上一张卡牌存在，将上一张纸牌设置为正面，可移动
			if (this.previousCard != null) {
				this.previousCard.turnFrontSide();
				this.previousCard.isCanMove(true);
			}
			// this.isCanMove(true);
		} else {
			this.setNextCardLocation(null);
			spider.table.remove(this.getLocation());
			this.setLocation(this.cardInitPoint);
			spider.table.put(this.cardInitPoint, this);
			return;
		}
		point = spider.getLastCardLocation(n);
		card = (PKCard) spider.table.get(point);
		if (card.getCardValue() == 1) {
			point.y -= 240;
			card = (PKCard) spider.table.get(point);
			if (card != null && card.canMove) {
				spider.isFinish(n);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	/**
	 * 移动（x，y）个位置
	 */
	public void moving(int x, int y) {
		PKCard card = spider.getNextCard(this);
		Point p = this.getLocation();

		// 将组件移动到容器中指定的顺序索引。
		pane.setComponentZOrder(this, 1);

		// 在Hashtable中保存新的节点信息
		spider.table.remove(p);
		p.x += x;
		p.y += y;

		this.setLocation(p);
		spider.table.put(p, this);

		// 实现多张纸牌同时拖动
		if (card != null)
			card.moving(x, y);
	}

	/**
	 * 让纸牌显示正面
	 */
	public void turnFrontSide() {
		this.setIcon(new ImageIcon("images/" + cardImgName + ".gif"));
		this.isFront = true;
		this.canMove = true;
	}

	/**
	 * 设置纸牌是否可以移动
	 */
	public void isCanMove(boolean can) {
		// 纸牌移动标志初始化
		this.canMove = can;
		// 获得该牌上一张牌对象
		PKCard card = spider.getPreviousCard(this);
		if (card != null && !card.isFront) {
			card.canMove = false;
			return;
		}
		if (card != null && card.isFront) {
			if (!can) {
				if (!card.canMove) {
					return;
				} else {
					card.isCanMove(can);
				}
			} else {
				if (this.cardValue + 1 == card.getCardValue() && this.cardType == card.getCardType()) {
					card.isCanMove(can);
				} else {
					card.isCanMove(false);
				}
			}
		}
	}

	/**
	 * 判断鼠标位置是否在其他列中，不在其他列中则返回-1
	 */
	public int isWhichColumn(Point p) {
		int x, y;
		x = p.x;
		y = p.y;
		int m = (x - 20) / 101;
		if (m >= 0 && m < 10 && m != (mousePoint.x + cardInitPoint.getLocation().x - 20) / 101) {
				return m;
		} else
			return -1;
	}

	/**
	 * 设置下一张纸牌位置
	 */
	public void setNextCardLocation(Point point) {

		PKCard card = spider.getNextCard(this);
		if (card != null) {
			if (point == null) {
				card.setNextCardLocation(null);
				spider.table.remove(card.getLocation());
				card.setLocation(card.cardInitPoint);
				spider.table.put(card.cardInitPoint, card);
			} else {
				point = new Point(point);
				point.y += 20;
				card.setNextCardLocation(point);
				point.y -= 20;
				spider.table.remove(card.getLocation());
				card.setLocation(point);
				spider.table.put(card.getLocation(), card);
				card.cardInitPoint = card.getLocation();
			}
		}
	}

	/**
	 * 获得card的点数
	 */
	public int getCardValue() {
		return cardValue;
	}

	/**
	 * 获得card的花色
	 */
	public int getCardType() {
		return cardType;
	}

	public void flashCard(PKCard card) {
		// 启动Flash线程
		new Flash(card).start();
		// 不停的获得下一张牌，直到完成
		if (spider.getNextCard(card) != null) {
			card.flashCard(spider.getNextCard(card));
		}
	}

	class Flash extends Thread {
		private PKCard card = null;

		public Flash(PKCard card) {
			this.card = card;
		}

		/*
		 ** 线程的run()方法 为纸牌的正面设置白色图片
		 */
		public void run() {
			boolean is = false;
			ImageIcon icon = new ImageIcon("images/white.gif");
			for (int i = 0; i < 4; i++) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (is) {
					// this.card.setOpaque(true);
					this.card.turnFrontSide();
					is = !is;
				} else {
					// this.card.setOpaque(false);
					this.card.setIcon(icon);
					is = !is;
				}
				// 本行代码存在问题，与swing皮肤包Nimbus相冲突，注释掉
				// card.updateUI();
			}
		}
	}
}
