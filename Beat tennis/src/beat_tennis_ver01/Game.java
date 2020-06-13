package beat_tennis_ver01;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread {

	private Image gameInfo = new ImageIcon(Main.class.getResource("../images/gameinfo.png")).getImage();
	private Image judgementLine = new ImageIcon(Main.class.getResource("../images/judgementline.png")).getImage();
	private Image noteRouteD = new ImageIcon(Main.class.getResource("../images/noteroute.png")).getImage();
	private Image noteRouteF = new ImageIcon(Main.class.getResource("../images/noteroute.png")).getImage();
	private Image noteRouteJ = new ImageIcon(Main.class.getResource("../images/noteroute.png")).getImage();
	private Image noteRouteK = new ImageIcon(Main.class.getResource("../images/noteroute.png")).getImage();
	private Image noteRouteLine = new ImageIcon(Main.class.getResource("../images/noterouteline.png")).getImage();
	private Image judgeimage;

	private String title;
	private String musicpath;
	private Music gamemusic;
	
	ArrayList<Note> notelist = new ArrayList<Note>();

	public Game(String title, String musicpath) {
		this.title = title;
		this.musicpath = musicpath;
		gamemusic = new Music(this.musicpath, false);
	}

	public void screenDraw(Graphics2D g) {
		// ���⼭ x,y ��ǥ �����ϸ鼭 ȭ�� �����
		g.drawImage(noteRouteD, 434, 30, null);
		g.drawImage(noteRouteF, 538, 30, null);
		g.drawImage(noteRouteJ, 642, 30, null);
		g.drawImage(noteRouteK, 746, 30, null);
		g.drawImage(noteRouteLine, 430, 30, null);
		g.drawImage(noteRouteLine, 534, 30, null);
		g.drawImage(noteRouteLine, 638, 30, null);
		g.drawImage(noteRouteLine, 742, 30, null);
		g.drawImage(noteRouteLine, 846, 30, null);
		g.drawImage(judgementLine, 430, 580, null);
		// ��Ʈ �������°��ϰ� �����Ȱ� �����ϸ鼭 ȭ�鿡 ���
		// ��Ʈ�� ������ ������ �Ѿ�� �̽� ����
		for (int i = 0; i < notelist.size(); i++) {
			Note note = notelist.get(i);
			if(note.getY() > 620) {
				judgeimage = new ImageIcon(Main.class.getResource("../images/miss.png")).getImage();
			}
			if(!note.isProceeded()) {
				notelist.remove(i);
				i--;
			}
			note.screenDraw(g);
		}

		// �� ���� �ֱ� (��Ʈ�� �������� �ٲ㵵 ��)
		g.drawImage(gameInfo, 0, 660, null);
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(title, 20, 702);
		g.drawImage(judgeimage,460,420,null);
	}
	
	// Ű �����ų� ���� �� ���� + �̹��� ��ȯ
	public void pressD() {
		judge("D");
		noteRouteD = new ImageIcon(Main.class.getResource("../images/noteroutepressed.png")).getImage();
	}

	public void pressF() {
		judge("F");
		noteRouteF = new ImageIcon(Main.class.getResource("../images/noteroutepressed.png")).getImage();
	}

	public void pressJ() {
		judge("J");
		noteRouteJ = new ImageIcon(Main.class.getResource("../images/noteroutepressed.png")).getImage();
	}

	public void pressK() {
		judge("K");
		noteRouteK = new ImageIcon(Main.class.getResource("../images/noteroutepressed.png")).getImage();
	}

	public void releaseD() {
		noteRouteD = new ImageIcon(Main.class.getResource("../images/noteroute.png")).getImage();
	}

	public void releaseF() {
		noteRouteF = new ImageIcon(Main.class.getResource("../images/noteroute.png")).getImage();
	}

	public void releaseJ() {
		noteRouteJ = new ImageIcon(Main.class.getResource("../images/noteroute.png")).getImage();
	}

	public void releaseK() {
		noteRouteK = new ImageIcon(Main.class.getResource("../images/noteroute.png")).getImage();
	}

	@Override
	public void run() {
		dropNotes();
	}

	public void close() {
		gamemusic.close();
		this.interrupt();
	}

	/*
	 *	<�ſ� �߿�> 
	 *  �ڵ� Ư����
	 *  ��Ʈ�� REACH_TIME�� ���ĺ��� ( REACH_TIME = ��Ʈ�� �����Ǿ��� �� �������� �� �����ϴ� �ð� ) 
	 *  �������� �Ǿ� ���� ����
	 *  �ٵ� ù��° ��Ʈ �������� 0���� �ϰ�
	 *  ������Ѻ��� ó������ ���� ���µ�
	 *  ��� �����Ű�� ù���� ��Ʈ��
	 *  �ٸ� ��Ʈ�� �Ÿ��� ���� �־���
	 *  �� ���������� �𸣰ڴµ�
	 *  ��ĥ�� �ִٸ� ���� �ٶ�
	 *  �Ÿ� �ȸ־����� �ϱ� ���ؼ�
	 *  �Ϻη� ������ ���� 1000 ���� (155��° �� ����)
	 *  """�׷��� ���� ���� �� �� �տ� 2�� ���� �־� �� �� ����""" ( 1 + REACH_TIME) << GoldWave? �װ� �Ἥ ���� �ٶ�. �ſ� �߿�
	 */
	
	// ��Ʈ �����ؼ� ����߸���
	public void dropNotes() {
		int i=0;
		int offset = 0; // ù ��Ʈ ���� ���� (�и���)
		int gap = 200; // �ִ� ��Ʈ �и���
		
		Beat[] beats = null; // ä�� (?)
		
		/*
		 *	beats == ä��
		 *  ������ = ù ��Ʈ�� �����Ǵ� ���� (�и���)
		 *  ù ��Ʈ�� ������*1000 + 1 + REACH_TIME�� �Ŀ� �������� ������ 
		 *  gap = �̰� ���� �����ؾ� �� �� �𸣰ڴµ�, ��Ʈ �� �ּ� ������ (�и���)
		 *  �� ����� ä�� ¥�� �� ���ϱ� �̰� ���鼭 ���� �и��� ������ �� ���� �ϴ��� ����
		 */
		// �ؿ� ä���� ���÷� ���Ŷ� ���� �ȸ���;;
		if (title.equals("Unknown Brain & Rival - Control (ft. Jex)")) {
			beats = new Beat[] { 
					new Beat(offset, "D"), 
					new Beat(offset+gap, "F"),
					new Beat(offset+gap*2, "J"),
					new Beat(offset+gap*4, "K"),
					new Beat(offset+gap*4, "D"),
					new Beat(offset+gap*8, "K"),
					new Beat(offset+gap*10, "J"),
					new Beat(offset+gap*11, "F"),
					new Beat(offset+gap*12, "D"),
					new Beat(offset+gap*14, "J"),
					new Beat(offset+gap*15, "F"),
					new Beat(offset+gap*17, "D"),
					new Beat(offset+gap*17, "F"),
					};
		} 
		else if (title.equals("���2")) {
		}
		else if (title.equals("���3")) {
		}
		gamemusic.start();
		while(i < beats.length && !isInterrupted()) {

			if(beats[i].getTime() +1000 <= gamemusic.getTime()) {
				Note note = new Note(beats[i].getNoteType());
				note.start();
				notelist.add(note);
				i++;
			}
		}
	}
	
	// ���� ( Ű�� ������ ������ ����� )
	public void judge(String input) {
		for(int i=0;i<notelist.size();i++) {
			Note note = notelist.get(i);
			if(input.contentEquals(note.getNoteType())){
				judgeEvent(note.judge());
				break;
			};
		}
	}
	
	// ������ ���� �ؽ�Ʈ ���� ( Note Ŭ������ �ִ� judge ���� )
	public void judgeEvent(String judge) {
		if(judge.contentEquals("Late")) {
			judgeimage = new ImageIcon(Main.class.getResource("../images/late.png")).getImage();
		}
		if(judge.contentEquals("Good")) {
			judgeimage = new ImageIcon(Main.class.getResource("../images/good.png")).getImage();
		}
		if(judge.contentEquals("Cool")) {
			judgeimage = new ImageIcon(Main.class.getResource("../images/cool.png")).getImage();
		}
		if(judge.contentEquals("Early")) {
			judgeimage = new ImageIcon(Main.class.getResource("../images/early.png")).getImage();
		}
	}
}
