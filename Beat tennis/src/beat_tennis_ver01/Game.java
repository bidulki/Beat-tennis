package beat_tennis_ver01;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Image;
import java.awt.RenderingHints;

public class Game extends Thread {
	
	//private Image gameInfo = new ImageIcon(Main.class.getResource("../images/gameinfo.jpeg")).getImage(); // ���� ���� ���� �� ������ ���
	//private Image judgementLine = new ImageIcon(Main.class.getResource("../images/judgementLine.jpeg")).getImage(); // ���� ������
	//private Image noteRoute = new ImageIcon(Main.class.getResource("../images/noteRoute.jpeg")).getImage(); // ��Ʈ ������ ���
	//private Image noteRouteLine = new ImageIcon(Main.class.getResource("../images/noteRouteLine.jpeg")).getImage(); // ��Ʈ ���� ���м�
	
	public void screenDraw(Graphics2D g) {
		
	}
	
	@Override
	public void run() {
		//���⼭ x,y ��ǥ �����ϸ鼭 ȭ�� �����
		//g.drawImage(gameInfo, 0, 660, null);
		//g.drawImage(judgementLine, 0, 580, null);
		//g.drawImage(noteRoute, 240, 30, null);
		//g.drawImage(noteRouteLine, 238, 30, null);
		
		//�� ���� �ֱ� (��Ʈ�� �������� �ٲ㵵 ��)
		//g.setColor(Color.white);
		//g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.KEY_TEXT_ANTIALIAS_ON);
		//g.setFont(new Font("Arial", Font.BOLD, 30));
		//g.drawString("Unknown Brain & Rival - Control (ft. Jex)", 20, 702);
	}
}
