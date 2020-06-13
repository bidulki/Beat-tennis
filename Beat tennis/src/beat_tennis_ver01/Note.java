package beat_tennis_ver01;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {

	private Image notebasicimage = new ImageIcon(Main.class.getResource("../images/notebasic.png")).getImage();
	/*
	 * y �� � ������ �ʱ�ȭ �ƴµ� ���� � �ǹ̳ĸ� 
	 * ��Ʈ�� ������ �� ��Ȯ�� REACH_TIME �� �Ŀ� �����ϰ� ��
	 * SLEEP_TIME���� NOTE_SPEED �ȼ���ŭ �������� �Ǵµ�
	 * 1000 �и��ʴ� 1000/SLEEP_TIME ��ŭ ����ǹǷ� ����
	 */
	private int x, y = 580 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;
	private String noteType;
	private boolean proceeded = true;

	public String getNoteType() {
		return noteType;
	}
	public boolean isProceeded() {
		return proceeded;
	}
	
	public void close() {
		proceeded = false;
	}
	
	// Ű�� ���� ��Ʈ ���� ��ġ ����
	public Note(String noteType) {
		if (noteType.equals("D"))
			x = 434;
		else if (noteType.equals("F"))
			x = 538;
		else if (noteType.equals("J"))
			x = 642;
		else if (noteType.equals("K"))
			x = 746;
		this.noteType = noteType;
	}

	// �ѹ� ����ɶ����� NOTE_SPEED �ȼ���ŭ ����߸�
	public void drop() {
		y += Main.NOTE_SPEED;
		if(y > 620) {
			close();
		}
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(notebasicimage, x, y, null);
	}

	// SLEEP_TIME ���� NOTE_SPEED �ȼ���ŭ ����߸�
	@Override
	public synchronized void run() {
		try {
			while (true) {
				drop();
				if(proceeded) {
					Thread.sleep(Main.SLEEP_TIME);
				}
				else {
					interrupt();
					break;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	// ���� �Լ�
	// ��Ʈ ��ġ�� ���� ���� �����ϰ� ��Ʈ ����
	public String judge() {
		if(y >= 610) {
			close();
			return "Late";
		}
		else if(y >= 590) {
			close();
			return "Good";
		}
		else if(y >= 570) {
			close();
			return "Cool";
		}
		else if(y >= 550) {
			close();
			return "Good";
		}
		else if(y >= 540) {
			close();
			return "Early";
		}
		return "None";
	}
	
	public int getY() {
		return y;
	}
}
