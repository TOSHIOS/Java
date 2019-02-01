package spider;

import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Time {
	private JLabel timeLabel;
	private long time;
	private String str;
	private boolean stopFlag = false;
	private long stoptime;
	private long continuetime;
	private long differencetime = 0;
	private TimerTask task;

	public Time(final JLabel timeLabel) {
		this.timeLabel = timeLabel;
		differencetime = 0;
		time = System.currentTimeMillis();
		task = new TimerTask() {
			@Override
			public void run() {
				if (!stopFlag) {
					str = String.format("%1$tM:%1$tS:%1$1tL", System.currentTimeMillis() - time - differencetime);
					// if (differencetime == 0) {
					// str = String.format("%1$tM:%1$tS:%1$1tL", System.currentTimeMillis() - time);
					// } else {
					// System.out.println("@@");
					// str = String.format("%1$tM:%1$tS:%1$1tL", System.currentTimeMillis() - time -
					// differencetime);
					// }
					timeLabel.setText(str.substring(0, 7));
				} else {
					str = timeLabel.getText();
					timeLabel.setText(str);
				}
			}
		};
		Timer timer=new Timer();
		timer.schedule(task, 1, 100);
	}

	public void restartTime() {
		Spider spider=new Spider();
		spider.setVisible(true);
		//time = System.currentTimeMillis();
	}

	public void stopTime() {
		stopFlag = true;
		stoptime = System.currentTimeMillis();
	}

	public void continueTime() {
		stopFlag = false;
		continuetime = System.currentTimeMillis();
		differencetime += continuetime - stoptime;
	}
}