package beat_tennis_ver01;
/*
 * 김도현
 * Music class 구현
 * 외부 라이브러리 javazoom.jl.player.Player을 사용하니 
 * build path -> configure build path에서  jl1.0.1.jar 파일 경로 지정하세요
 * 
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread {
	private Player player;
	private boolean isLoop;
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	//생성자
	public Music(String name, boolean isLoop) {
		try {
			this.isLoop = isLoop;
			//상대경로 지정, music 파일에 음악 파일 넣으세요 
			file = new File(Main.class.getResource("../music/" + name).toURI());
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	// 현재 실행되고 있는 시간 반환 
	public int getTime() {
		if(player == null)
			return 0;
		return player.getPosition();
	}
	//음악 종료 메소드
	public void close() {
		isLoop = false;
		player.close();
		this.interrupt();
	}
	//쓰레드에서 상속
	public void run() {
		try {
			do {
				//음악 실행
				player.play();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			}while(isLoop);//isLoop가 true이면 무한 반복 실행

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
