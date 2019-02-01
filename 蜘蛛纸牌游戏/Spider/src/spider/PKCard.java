package spider;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * ����ֽ�Ƶ����ԣ��������ơ�λ�õ������Ϣ��ͨ����ط���ʵ��ֽ���ƶ����ܡ�
 */

public class PKCard extends JLabel implements MouseListener, MouseMotionListener {

	// ������ĳ�ʼλ��
	private Point mousePoint = null;
	// �������ֽ�Ƶĳ�ʼλ��
	private Point cardInitPoint = null;
	// ֽ���ļ�����Ч����
	private String cardImgName = null;
	// ֽ���ļ�����ɫ
	int cardType = 0;
	// ֽ���ļ�������
	int cardValue = 0;
	// ��һ��ֽ�ƶ���
	PKCard previousCard = null;

	// ֽ�ƶ���
	private Spider spider = null;
	private Container pane = null;
	private boolean canMove = false;
	private boolean isFront = false;

	/**
	 * ��ֽ���ƶ�����point
	 */
	public void moveto(Point point) {
		this.setLocation(point);
	}

	/**
	 * ���췽��
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
	 * �����ܷ��ƶ�
	 */
	public boolean getCanMove() {
		return this.canMove;
	}

	/**
	 * ����϶�ֽ��
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
	 * �����ֽ��
	 */
	public void mousePressed(MouseEvent e) {
		mousePoint = e.getPoint();
		cardInitPoint = this.getLocation();
		this.previousCard = spider.getPreviousCard(this);
	}

	/**
	 * ����ͷ�ֽ��
	 */
	public void mouseReleased(MouseEvent e) {
		if (!this.canMove)
			return;

		// �������ͷ�ֽ�Ƶ�λ��
		Point point = ((JLabel) e.getSource()).getLocation();
		// �ж�����ͷ�λ���Ƿ�����ĳһ��
		int n = this.isWhichColumn(point);
		if (n == -1 || n == this.isWhichColumn(this.cardInitPoint)) {
			this.setNextCardLocation(null);
			spider.table.remove(this.getLocation());
			this.setLocation(this.cardInitPoint);
			spider.table.put(this.cardInitPoint, this);
			return;
		}

		point = spider.getLastCardLocation(n);
		boolean isEmpty = false; // ���г�ʼֽ���Ƿ���ڣ���ʼ�������
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

		// ������г�ʼֽ�Ʋ����ڣ����߸��г�ʼֽ�ƴ��ڣ��Ҹ��Ƶĵ�������һ���Ƶĵ���Сһ�㣬˵�����Ա�����
		// thisΪ���ڱ��ƶ���ֽ�ƣ�cardΪ�����ƶ�ֽ�Ƶ����·�ֽ��
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
			// ������Ƶ���һ�ſ��ƴ��ڣ�����һ��ֽ������Ϊ���棬���ƶ�
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
	 * �ƶ���x��y����λ��
	 */
	public void moving(int x, int y) {
		PKCard card = spider.getNextCard(this);
		Point p = this.getLocation();

		// ������ƶ���������ָ����˳��������
		pane.setComponentZOrder(this, 1);

		// ��Hashtable�б����µĽڵ���Ϣ
		spider.table.remove(p);
		p.x += x;
		p.y += y;

		this.setLocation(p);
		spider.table.put(p, this);

		// ʵ�ֶ���ֽ��ͬʱ�϶�
		if (card != null)
			card.moving(x, y);
	}

	/**
	 * ��ֽ����ʾ����
	 */
	public void turnFrontSide() {
		this.setIcon(new ImageIcon("images/" + cardImgName + ".gif"));
		this.isFront = true;
		this.canMove = true;
	}

	/**
	 * ����ֽ���Ƿ�����ƶ�
	 */
	public void isCanMove(boolean can) {
		// ֽ���ƶ���־��ʼ��
		this.canMove = can;
		// ��ø�����һ���ƶ���
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
	 * �ж����λ���Ƿ����������У��������������򷵻�-1
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
	 * ������һ��ֽ��λ��
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
	 * ���card�ĵ���
	 */
	public int getCardValue() {
		return cardValue;
	}

	/**
	 * ���card�Ļ�ɫ
	 */
	public int getCardType() {
		return cardType;
	}

	public void flashCard(PKCard card) {
		// ����Flash�߳�
		new Flash(card).start();
		// ��ͣ�Ļ����һ���ƣ�ֱ�����
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
		 ** �̵߳�run()���� Ϊֽ�Ƶ��������ð�ɫͼƬ
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
				// ���д���������⣬��swingƤ����Nimbus���ͻ��ע�͵�
				// card.updateUI();
			}
		}
	}
}
