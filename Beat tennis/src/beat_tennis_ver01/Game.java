package beat_tennis_ver01;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

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
		// 여기서 x,y 좌표 조정하면서 화면 만들기
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
		// 노트 떨어지는거하고 판정된거 갱신하면서 화면에 출력
		// 노트가 판정선 완전히 넘어가면 미스 판정
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

		// 곡 정보 넣기 (폰트는 언제든지 바꿔도 됨)
		g.drawImage(gameInfo, 0, 660, null);
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(title, 20, 702);
		g.drawImage(judgeimage,460,420,null);
	}
	
	// 키 누르거나 뗐을 때 판정 + 이미지 교환
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
		try {
			dropNotes();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		gamemusic.close();
		this.interrupt();
	}

	/*
	 *	<매우 중요> 
	 *  코드 특성상
	 *  노트가 REACH_TIME초 이후부터 ( REACH_TIME = 노트가 생성되었을 때 판정선에 딱 도달하는 시간 ) 
	 *  떨어지게 되어 있음 ㅇㅇ
	 *  근데 첫번째 노트 오프셋을 0으로 하고
	 *  실행시켜보면 처음에는 문제 없는데
	 *  계속 실행시키면 첫번 노트랑
	 *  다른 노트랑 거리가 점점 멀어짐
	 *  렉 때문인지는 모르겠는데
	 *  고칠수 있다면 수정 바람
	 *  거리 안멀어지게 하기 위해서
	 *  일부러 오프셋 값에 1000 더함 (155번째 줄 참고)
	 *  """그래서 음악 넣을 때 맨 앞에 2초 공백 둬야 할 듯 ㅇㅇ""" ( 1 + REACH_TIME) << GoldWave? 그거 써서 편집 바람. 매우 중요
	 */
	
	// 노트 생성해서 떨어뜨리기
	public void dropNotes() throws IOException, URISyntaxException{
		int i=0;
		int offset = 0; // 첫 노트 시작 시점 (밀리초)
		int gap = 200; // 최단 비트 밀리초
		
		Beat[] beats = null; // 채보 (?)
		
		/*
		 *	beats == 채보
		 *  오프셋 = 첫 노트가 생성되는 시점 (밀리초)
		 *  첫 노트는 오프셋*1000 + 1 + REACH_TIME초 후에 판정선에 도달함 
		 *  gap = 이걸 뭐라 설명해야 될 지 모르겠는데, 노트 간 최소 간격임 (밀리초)
		 *  그 사람이 채보 짜는 거 보니까 이거 쓰면서 굳이 밀리초 단위로 안 적고 하더라 ㅇㅇ
		 */
		// 밑에 채보는 샘플로 찍어본거라 박자 안맞음;;
		if (title.equals("Unknown Brain & Rival - Control (ft. Jex)")) {
			String name = "Control.txt";
			File file = new File(Main.class.getResource("../charts/" + name).toURI());
			InputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			String data = br.readLine();
			String[] data_split = data.split(" ");
			int beat_num = data_split.length;
			String key;
			int beat_time;
			
			beats = new Beat[beat_num];
			for(int j=0; j < beat_num; j++) {
				key = data_split[j].substring(0,1);
				beat_time = Integer.parseInt(data_split[j].substring(1));
				beats[j] = new Beat(offset+gap*beat_time, key);			
			}
		} 
		else if (title.equals("Gradamical -Floor B2")) {
			String name = "Floor B2.txt";
			File file = new File(Main.class.getResource("../charts/" + name).toURI());
			InputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			String data = br.readLine();
			String[] data_split = data.split(" ");
			int beat_num = data_split.length;
			String key;
			int beat_time;
			
			beats = new Beat[beat_num];
			for(int j=0; j < beat_num; j++) {
				key = data_split[j].substring(0,1);
				beat_time = Integer.parseInt(data_split[j].substring(1));
				beats[j] = new Beat(offset+gap*beat_time, key);			
			}
		}
		else if (title.equals("Rob Gasser - Hollow (ft. Veronica Bravo)")) {
			String name = "Hollow.txt";
			File file = new File(Main.class.getResource("../charts/" + name).toURI());
			InputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			String data = br.readLine();
			String[] data_split = data.split(" ");
			int beat_num = data_split.length;
			String key;
			int beat_time;
			
			beats = new Beat[beat_num];
			for(int j=0; j < beat_num; j++) {
				key = data_split[j].substring(0,1);
				beat_time = Integer.parseInt(data_split[j].substring(1));
				beats[j] = new Beat(offset+gap*beat_time, key);			
			}
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
	
	// 판정 ( 키가 눌렸을 때마다 실행됨 )
	public void judge(String input) {
		for(int i=0;i<notelist.size();i++) {
			Note note = notelist.get(i);
			if(input.contentEquals(note.getNoteType())){
				judgeEvent(note.judge());
				break;
			};
		}
	}
	
	// 판정에 따라 텍스트 띄우기 ( Note 클래스에 있는 judge 참고 )
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
