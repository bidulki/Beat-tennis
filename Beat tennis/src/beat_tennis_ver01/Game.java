package beat_tennis_ver01;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Robot;
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

// Game 클래스 생성 및 틀 제작(민경환)
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
	private int score = 0;
	private boolean game_end = false;
	
	ArrayList<Note> notelist = new ArrayList<Note>();

	public Game(String title, String musicpath) {
		this.title = title;
		this.musicpath = musicpath;
		gamemusic = new Music(this.musicpath, false);
	}
	
	public void screenDraw(Graphics2D g) {
		// x,y 좌표 조정하면서 화면 만들기 (민겨오한)
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

		// 곡 정보 넣기 (민경환)
		g.drawImage(gameInfo, 0, 660, null);
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(title, 20, 702);
		g.drawImage(judgeimage,460,420,null);
		
		// 키 입력 정보
		g.drawString("D", 474, 650);
		g.drawString("F", 578, 650);
		g.drawString("J", 682, 650);
		g.drawString("K", 786, 650);
		g.setFont(new Font("Elephant", Font.BOLD, 30));
		
		// 점수 표시 (민경환)
		String str_score = Integer.toString(score);
		g.drawString(str_score, 1100, 702);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		
		// 게임 끝난 후 최종 점수 출력 (민경환)
		if(game_end) {
			g.setColor(Color.black);
			g.drawString("Final Score", 560, 220);
			g.drawString(str_score, 600, 260);
			g.setFont(new Font("Elephant", Font.BOLD, 30));
		}
	}
	
	// 키 누르거나 뗐을 때 판정 + 이미지 교환
	// 키 인식 틀 및 키음 생성 (민경환)
	public void pressD() {
		judge("D");
		noteRouteD = new ImageIcon(Main.class.getResource("../images/noteroutepressed.png")).getImage();
		new Music("Keysound.mp3", false).start();
	}

	public void pressF() {
		judge("F");
		noteRouteF = new ImageIcon(Main.class.getResource("../images/noteroutepressed.png")).getImage();
		new Music("Keysound.mp3", false).start();
	}

	public void pressJ() {
		judge("J");
		noteRouteJ = new ImageIcon(Main.class.getResource("../images/noteroutepressed.png")).getImage();
		new Music("Keysound.mp3", false).start();
	}

	public void pressK() {
		judge("K");
		noteRouteK = new ImageIcon(Main.class.getResource("../images/noteroutepressed.png")).getImage();
		new Music("Keysound.mp3", false).start();
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
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		gamemusic.close();
		game_end = true;
		this.interrupt();
	}

	/*
	 *	<매우 중요> 
	 *  코드상 로직 : 노트가 REACH_TIME초 이후부터 ( REACH_TIME = 노트가 생성되었을 때 판정선에 딱 도달하는 시간 ) 떨어짐
	 *  하지만 첫번째 노트 오프셋을 0으로 하면 노트 간 간격이 일정하게 나오지 않는 문제가 발생. 
	 *  해소를 위해 노트 간 간격의 간격 문제 해소를 위해  오프셋 값에 1000 추가
	 */
	
	// 노트 생성해서 떨어뜨리기
	public void dropNotes() throws IOException, URISyntaxException, AWTException{
		int i=0;
		int offset = 0; // 첫 노트 시작 시점 (밀리초)
		int gap = 200; // 최단 비트 밀리초
		String name= ""; //(김도현)노래 이름, 채보 파일 접근에 사용
		
		Beat[] beats = null; // 채보
		
		/*
		 *	beats == 채보
		 *  오프셋 = 첫 노트가 생성되는 시점 (밀리초)
		 *  첫 노트는 오프셋*1000 + 1 + REACH_TIME초 후에 판정선에 도달함 
		 *  gap = 노트 간 최소 간격 (밀리초)
		 */
		
		if (title.equals("Unknown Brain & Rival - Control (ft. Jex)")) {
			name = "Control";
			gap = 300;
		} 
		
		else if (title.equals("Gradamical -Floor B2")) {
			name = "Floor B2";
		}
		
		else if (title.equals("Rob Gasser - Hollow (ft. Veronica Bravo)")) {
			name = "Hollow";
		}
		/*
		 * (김도현)
		 * charts 파일에 있는 각 노래 별 채보 txt 파일을 읽어서
		 * 키 버튼과 시간 정보를 뽑아낸 다음
		 * 비트를 키버튼과 시간 정보를 통해 생성해내는 코드
		 */
		File file = new File(Main.class.getResource("../charts/" + name + ".txt").toURI());
		InputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);	
		
		String data = br.readLine();
		String[] data_split = data.split(" ");		
		int beat_num = data_split.length;
		String key;
		double beat_time;
			
		beats = new Beat[beat_num];
			
		for(int j=0; j < beat_num; j++) {
			key = data_split[j].substring(0,1);
			beat_time = Double.parseDouble(data_split[j].substring(1));
			beats[j] = new Beat((int)(offset+gap*beat_time), key);			
		}
		br.close(); isr.close(); fis.close();
		
		
		gamemusic.start();
		
		while(i < beats.length && !isInterrupted()) {
			if(beats[i].getTime() + 1000 <= gamemusic.getTime()) {
				Note note = new Note(beats[i].getNoteType());
				note.start();
				notelist.add(note);
				i++;
			}
		}
		
		// 노트와 음악의 delay를 없애기 위해 프로세스 1500ms동안 정지 (민경환)
		Robot tRobot = new Robot();
		tRobot.delay(1500);
		
		close();
	}
	
	// 판정 (키가 눌렸을 때마다 실행됨)
	public void judge(String input) {
		for(int i=0;i<notelist.size();i++) {
			Note note = notelist.get(i);
			if(input.contentEquals(note.getNoteType())){
				judgeEvent(note.judge());
				break;
			};
		}
	}
	
	// 판정에 따라 텍스트 띄우기 (Note 클래스에 있는 judge 참고)
	// 판정 당 점수 추가 (민경환)
	public void judgeEvent(String judge) {
		if(judge.contentEquals("Late")) {
			judgeimage = new ImageIcon(Main.class.getResource("../images/late.png")).getImage();
			score += 100;
		}
		if(judge.contentEquals("Good")) {
			judgeimage = new ImageIcon(Main.class.getResource("../images/good.png")).getImage();
			score += 200;
		}
		if(judge.contentEquals("Cool")) {
			judgeimage = new ImageIcon(Main.class.getResource("../images/cool.png")).getImage();
			score += 300;
		}
		if(judge.contentEquals("Early")) {
			judgeimage = new ImageIcon(Main.class.getResource("../images/early.png")).getImage();
			score += 100;
		}
	}
}
