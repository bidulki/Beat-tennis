package beat_tennis_ver01;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// KeyListener class 틀 생성 및 제작 (민경환, 이찬영)
public class KeyListener extends KeyAdapter{

	// 말 그대로 키 리스너 ㅇㅇ
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		if(BeatTennis.game == null)
			return;
		
		if (e.getKeyCode() == KeyEvent.VK_D) {
			BeatTennis.game.pressD();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_F) {
			BeatTennis.game.pressF();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_J) {
			BeatTennis.game.pressJ();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_K) {
			BeatTennis.game.pressK();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(BeatTennis.game == null)
			return;
		
		if (e.getKeyCode() == KeyEvent.VK_D) {
			BeatTennis.game.releaseD();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_F) {
			BeatTennis.game.releaseF();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_J) {
			BeatTennis.game.releaseJ();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_K) {
			BeatTennis.game.releaseK();
		}
	}
}
