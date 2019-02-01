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
 * ����֩��ֽ����Ϸ��ܣ�ʵ����Ϸ�з���������ֽ�Ƶ�������ɡ�λ�ðڷŵȡ�
 */

public class Spider extends JFrame implements MouseListener {

	private Container pane = null;

	// �Ѷȵȼ�Ϊ����
	public static final int EASY = 1;
	// �Ѷȵȼ�Ϊ����ͨ
	public static final int NORMAL = 2;
	// �Ѷȵȼ�Ϊ����
	public static final int HARD = 3;
	// �趨��ʼ�Ѷȵȼ�Ϊ��
	private int grade = Spider.EASY;
	// ����ֽ������
	private PKCard cards[] = new PKCard[104]; // ��ɫ�����ƣ���104����
	private JLabel groundLabel[] = null; // ÿ�г�ʼ��
	private boolean isFront; // ��ʶֽ��������
	// ���ư�ť
	private JLabel distributeLabel = null;
	private int distributeLabelX = 860, distributeLabelY = 560;
	// ����
	private int columnCards = 10;
	private int colorNumber = 4; // ֽ�ƻ�ɫ����

	// �ѷ�������
	private int distributedNumber = 0;
	private int columnEach = 0; // ����ѭ������
	private int columnN = 0; // ��������

	// ��ֵ�ԣ���Ϊ�������ֵ꣬Ϊֽ������
	Hashtable table = null;

	// �洢�Ѿ����ֽ������
	private int finishGroup = 0;

	public static Time time;
	private JLabel timeLabel;
	/**
	 * ���췽��
	 */
	public Spider() {
		// ���ó�������ͼ��
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("images/icon.gif");
		this.setIconImage(image);
		// ������������
		this.setTitle("֩��ֽ�� - " + Login.NAME);
		int sizeX = 1024, sizeY = 742; // ���ڴ�С
		this.setSize(sizeX, sizeY);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screensize.width / 2 - sizeX / 2, screensize.height / 2 - sizeY / 2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setJMenuBar(new SpiderMenuBar(this));
		pane = this.getContentPane();
		// ���ñ�����ɫ
		pane.setBackground(new Color(0, 112, 26));
		pane.setLayout(null);

		distributeLabel = new JLabel();
		distributeLabel.setBounds(distributeLabelX, distributeLabelY, 121, 96);
		distributeLabel.addMouseListener(this);
		pane.add(distributeLabel);
		
		timeLabel = new JLabel("ʱ��");
		Font font = new Font("����", Font.PLAIN, 25);
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
		// System.out.println("�ѷ���");
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
	 * �ַ�ֽ��
	 */
	public void distribute() {
		columnN = 0;
		columnEach = 0;
		for (int i = 0; i < 10; i++) {
			// �ƶ�û��ʱ���ؿ�
			if (distributedNumber + i >= 104) {
				distributedNumber = 104;
				return;
			}
			final Point lastPoint = this.getLastCardLocation(i);
			lastPoint.y += 20; // ���ֽ�Ƶ���ֹλ��

			final Point firstPoint = cards[distributedNumber + i].getLocation(); // ���ֽ�Ƶ���ʼλ��

			// final int ii = i;
			// final Point pp = firstPoint;
			// final int speedX = (firstPoint.x - lastPoint.x) / 100;
			// final int speedY = (firstPoint.y - lastPoint.y) / 100;
			// final int flag = 0;
			// pp.x -= speedX;// �����ƶ����λ��
			// pp.y -= speedY;
			// if (pp.x <= lastPoint.x || pp.y <= lastPoint.y) {// ���������ָ������,����ֹ�ƶ�
			// cards[distributedNumber + ii].moveto(lastPoint);
			// //flag=1;
			// }
			// cards[distributedNumber + ii].setLocation(pp.x, pp.y);// ����λ��
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
			// pp.x -= speedX;// �����ƶ����λ��
			// pp.y -= speedY;
			// if (pp.x <= lastPoint.x || pp.y <= lastPoint.y) {// ���������ָ������,����ֹ�ƶ�
			// table.remove(firstPoint);
			// cards[distributedNumber + ii].moveto(lastPoint);
			// table.put(lastPoint, cards[distributedNumber + ii]);
			// System.gc();
			// }
			// cards[distributedNumber + ii].setLocation(pp.x, pp.y);// ����λ��
			// // repaint();
			// }
			// }
			// });
			// t.start();
			// if (flag==1) {// ���������ָ������,����ֹ�ƶ�
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
			// �����ڸǵ�һ��ֽ��
			this.pane.setComponentZOrder(cards[distributedNumber + i], 1);
		}
		distributedNumber += 10;
	}

	/**
	 * ȡ�õ�column�����һ���Ƶ�λ��
	 */
	public Point getLastCardLocation(int column) {
		Point point = new Point(20 + column * 101, 25);
		PKCard card = (PKCard) this.table.get(point);
		// �����һ���Ʒ����й̶�ֵ
		if (card == null) {
			point.x = 20 + column * 101;
			point.y = 25 - 20;
			return point;
		}
		// ȡ�õ�column�����һ���Ƶ�λ��
		while (card != null) {
			point = card.getLocation();
			card = this.getNextCard(card);
		}
		return point;
	}

	/**
	 * ����card�·���һ���Ƶ�λ��
	 */
	public PKCard getNextCard(PKCard card) {
		Point point = new Point(card.getLocation());
		point.y += 20;
		card = (PKCard) table.get(point);
		return card;
	}

	/**
	 * ����card�Ϸ���һ���Ƶ�λ��
	 */
	public PKCard getPreviousCard(PKCard card) {
		Point point = new Point(card.getLocation());
		point.y -= 20; // ����card�Ϸ����Ƶ�������
		card = (PKCard) table.get(point);
		return card;
	}

	/**
	 * ��ʼ��Ϸ
	 */
	public void newGame() {
		this.initCards();
		this.setCardsLocation();
		isFront = false;
		// Ԥ�ȷ���4�ֱ������ϵ�ֽ��
		for (int i = 0; i < 4; i++)
			this.distribute();
		isFront = true;
		// �ٷ���1���������ϵ�ֽ��
		this.distribute();

		JOptionPane.showMessageDialog(this, "��ʼ��Ϸ��", "��ʼ", JOptionPane.PLAIN_MESSAGE);
		time = new Time(timeLabel);
	}

	/**
	 * ֽ�Ƴ�ʼ��ֵ
	 */
	public void initCards() {
		// ���ֽ���ѱ���ֵ��������ӿ�ܵ��������ȥ
		if (cards[0] != null) {
			for (int i = 0; i < 104; i++) {
				pane.remove(cards[i]);
			}
		}

		// ͨ���Ѷȵȼ���ΪcolorNumber��ɫ������ֵ
		if (this.grade == Spider.EASY) {
			colorNumber = 1;
		} else if (this.grade == Spider.NORMAL) {
			colorNumber = 2;
		} else {
			colorNumber = 4;
		}

		// ����ֽ��
		for (int i = 1; i <= 8; i++) {
			// ÿ��13���ƣ�A ~ K
			for (int j = 1; j <= 13; j++) {
				// (i % colorNumber + 1)Ϊֽ��ͼƬ�ļ�����һ���ԣ�1~4����jΪ�ڶ����ԣ�1~13��
				cards[(i - 1) * 13 + j - 1] = new PKCard((i % colorNumber + 1) + "-" + j, this);
			}
		}
		this.randomCards();
	}

	/**
	 * ���ϴ��
	 */
	public void randomCards() {
		// 104��ֽ���������������������52��
		for (int i = 0; i < 52; i++) {
			int a = (int) (Math.random() * 104);
			int b = (int) (Math.random() * 104);
			PKCard temp = cards[a];
			cards[a] = cards[b];
			cards[b] = temp;
		}
	}

	/**
	 ** �����Ѷȵȼ�
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * ����ֽ�Ƶ�λ�� ���ü�ֵ�ԣ���Ϊ�������ֵ꣬Ϊֽ������
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
	 * ��ø��еĳ�ʼֽ��λ��
	 */
	public Point getColumnFirstLocation(int column) {
		return new Point(groundLabel[column].getLocation());
	}

	/**
	 * �ж���Ϸ�Ƿ����
	 * 
	 * @param column
	 */
	public void isFinish(int column) {
		Point point = this.getLastCardLocation(column);
		PKCard card = (PKCard) this.table.get(point);
		do {
			this.table.remove(point);
			card.moveto(new Point(20 + finishGroup * 10, 580));
			// ������ƶ���������ָ����˳�����������θ���
			pane.setComponentZOrder(card, 1);
			// ��ֽ���µ������Ϣ����Hashtable
			this.table.put(card.getLocation(), card);
			card.isCanMove(false);
			point = this.getLastCardLocation(column);
			if (point == null)
				card = null;
			else
				card = (PKCard) this.table.get(point);
		} while (card != null && card.getCanMove());
		finishGroup++;
		// ���8����ȫ����ϳɹ�������ʾ�ɹ��ĶԻ���
		if (finishGroup == 8) {
			Login.updateDB(timeLabel.getText());
			time.stopTime();
			JOptionPane.showMessageDialog(this, "��ϲ�㣬˳��ͨ����\n" + "�û�����"+ Login.NAME  + "\nͨ��ʱ�䣺" + timeLabel.getText(), "�ɹ�",
					JOptionPane.PLAIN_MESSAGE);

		}
		if (card != null) {
			card.turnFrontSide();
			card.isCanMove(true);
		}
	}

	/**
	 * ��ʾ���ƶ��Ĳ���
	 */
	public void showEnableOperator() {
		int x = 0; // �����������
		out: while (true) {
			Point point = null;
			PKCard card = null;
			// ���ÿһ�����·���ֽ��
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
			// �������������·���ֽ��ͬʱ�ƶ���ֽ�ƣ�ֱ����ò���ͬʱ�ƶ���ֽ��
			while (this.getPreviousCard(card) != null && this.getPreviousCard(card).getCanMove()) {
				card = this.getPreviousCard(card);
			}
			if (columnEach == 10) {
				columnEach = 0;
			}
			// ѭ��10��
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
					// �����ɫ�������Ϲ�����������ʾ�߳�
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
				JOptionPane.showMessageDialog(this, "��Ǹ��ͨ��ʧ�ܣ�", "ʧ��", JOptionPane.PLAIN_MESSAGE);
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
