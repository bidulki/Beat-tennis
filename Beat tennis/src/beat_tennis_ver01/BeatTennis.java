package beat_tennis_ver01;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class BeatTennis extends JFrame {
	
	private Image screenImage;
	private Graphics screenGraphic;
	private Image background;
	
	public BeatTennis() {
		setTitle("Beat Tennis");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		background = new ImageIcon(Main.class.getResource("../images/background3.jpeg")).getImage();
		Music bgm1 = new Music("bgm1.mp3", true);
		bgm1.start();
	}
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(background, 0,0, null);
		this.repaint();
		
	}
}
