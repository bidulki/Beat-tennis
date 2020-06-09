package beat_tennis_ver01;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Image;
import java.awt.RenderingHints;

public class Game extends Thread {
	
	//private Image gameInfo = new ImageIcon(Main.class.getResource("../images/gameinfo.jpeg")).getImage(); // 게임 실행 중일 때 곡정보 등등
	//private Image judgementLine = new ImageIcon(Main.class.getResource("../images/judgementLine.jpeg")).getImage(); // 게임 판정선
	//private Image noteRoute = new ImageIcon(Main.class.getResource("../images/noteRoute.jpeg")).getImage(); // 노트 나오는 배경
	//private Image noteRouteLine = new ImageIcon(Main.class.getResource("../images/noteRouteLine.jpeg")).getImage(); // 노트 라인 구분선
	
	public void screenDraw(Graphics2D g) {
		
	}
	
	@Override
	public void run() {
		//여기서 x,y 좌표 조정하면서 화면 만들기
		//g.drawImage(gameInfo, 0, 660, null);
		//g.drawImage(judgementLine, 0, 580, null);
		//g.drawImage(noteRoute, 240, 30, null);
		//g.drawImage(noteRouteLine, 238, 30, null);
		
		//곡 정보 넣기 (폰트는 언제든지 바꿔도 됨)
		//g.setColor(Color.white);
		//g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.KEY_TEXT_ANTIALIAS_ON);
		//g.setFont(new Font("Arial", Font.BOLD, 30));
		//g.drawString("Unknown Brain & Rival - Control (ft. Jex)", 20, 702);
	}
}
