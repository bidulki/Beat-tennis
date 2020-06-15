package beat_tennis_ver01;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// KeyListener class Ʋ ���� �� ���� (�ΰ�ȯ, ������)
public class KeyListener extends KeyAdapter{

	// �� �״�� Ű ������ ����
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
