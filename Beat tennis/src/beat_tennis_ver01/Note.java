package beat_tennis_ver01;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

// 노트 클래스 구현 (이찬영)
public class Note extends Thread {

	private Image notebasicimage = new ImageIcon(Main.class.getResource("../images/notebasic.png")).getImage();

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
	
	// 키에 따라 노트 가로 위치 설정
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

	// 한번 실행될때마다 NOTE_SPEED 픽셀만큼 떨어뜨림
	public void drop() {
		y += Main.NOTE_SPEED;
		if(y > 620) {
			close();
		}
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(notebasicimage, x, y, null);
	}

	// SLEEP_TIME 마다 NOTE_SPEED 픽셀만큼 떨어뜨림
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
	
	// 판정 함수
	// 노트 위치에 따라 판정 결정하고 노트 삭제
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
