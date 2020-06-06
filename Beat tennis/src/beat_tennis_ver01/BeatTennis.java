package beat_tennis_ver01;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BeatTennis extends JFrame {

	private Image screenImage;
	private Graphics screenGraphic;

	private Image background = new ImageIcon(Main.class.getResource("../images/background3.jpeg")).getImage();
	
	private ImageIcon quitbuttonimage = new ImageIcon(Main.class.getResource("../images/quitbutton.png"));
	private ImageIcon quitbuttonselectedimage = new ImageIcon(Main.class.getResource("../images/quitbuttonselected.png"));
	private ImageIcon startbuttonimage = new ImageIcon(Main.class.getResource("../images/startbutton.png"));
	private ImageIcon startbuttonselectedimage = new ImageIcon(Main.class.getResource("../images/startbuttonselected.png"));
	private ImageIcon optionbuttonimage = new ImageIcon(Main.class.getResource("../images/optionbutton.png"));
	private ImageIcon optionbuttonselectedimage = new ImageIcon(Main.class.getResource("../images/optionbuttonselected.png"));
	private ImageIcon leftbuttonimage = new ImageIcon(Main.class.getResource("../images/leftbutton.png"));
	private ImageIcon leftbuttonselectedimage = new ImageIcon(Main.class.getResource("../images/leftbuttonselected.png"));
	private ImageIcon rightbuttonimage = new ImageIcon(Main.class.getResource("../images/rightbutton.png"));
	private ImageIcon rightbuttonselectedimage = new ImageIcon(Main.class.getResource("../images/rightbuttonselected.png"));

	private JButton quitbutton = new JButton(quitbuttonimage);
	private JButton startbutton = new JButton(startbuttonimage);
	private JButton optionbutton = new JButton(optionbuttonimage);
	private JButton leftbutton = new JButton(leftbuttonimage);
	private JButton rightbutton = new JButton(rightbuttonimage);

	private JLabel menubar = new JLabel(new ImageIcon(Main.class.getResource("../images/menubar.png")));

	private int mouseX, mouseY;

	private boolean ismainscreen = false;
	
	ArrayList<Track> tracklist = new ArrayList<Track>();
	private Image selectedimage;
	private Music selectedMusic;
	private int nowselected=0;
	
	public BeatTennis() {
		setUndecorated(true);
		setTitle("Beat Tennis");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(0, 0, 0, 0));
		setVisible(true);
		setLayout(null);

		Music bgm1 = new Music("bgm1.mp3", false);
		bgm1.start();
		
		//tracklist.add(new Track(String thumbnail, String highlights, String gamemusic)) -> 곡 추가
		quitbutton.setBounds(1250, 0, 30, 30);
		quitbutton.setContentAreaFilled(false);
		quitbutton.setFocusPainted(false);
		quitbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitbutton.setIcon(quitbuttonselectedimage);
				quitbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				quitbutton.setIcon(quitbuttonimage);
				quitbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		add(quitbutton);

		menubar.setBounds(0, 0, 1280, 30);
		menubar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menubar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menubar);

		startbutton.setBounds(850, 130, 360, 180);
		startbutton.setBorderPainted(false);
		startbutton.setContentAreaFilled(false);
		startbutton.setFocusPainted(false);
		startbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startbutton.setIcon(startbuttonselectedimage);
				startbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startbutton.setIcon(startbuttonimage);
				startbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				startbutton.setVisible(false);
				optionbutton.setVisible(false);
			 // background = new ImageIcon(...);     -> start 버튼 눌렀을 때 표시할 배경 ㅇㅇ
				ismainscreen = true;
				leftbutton.setVisible(true);
				rightbutton.setVisible(true);
				
			}
		});
		add(startbutton);

		optionbutton.setBounds(850, 360, 360, 180);
		optionbutton.setBorderPainted(false);
		optionbutton.setContentAreaFilled(false);
		optionbutton.setFocusPainted(false);
		optionbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				optionbutton.setIcon(optionbuttonselectedimage);
				optionbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				optionbutton.setIcon(optionbuttonimage);
				optionbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		add(optionbutton);
		
		leftbutton.setBounds(390, 550, 100, 100);
		leftbutton.setBorderPainted(false);
		leftbutton.setContentAreaFilled(false);
		leftbutton.setFocusPainted(false);
		leftbutton.setVisible(false);
		leftbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftbutton.setIcon(leftbuttonselectedimage);
				leftbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				leftbutton.setIcon(leftbuttonimage);
				leftbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				selectLeft();
			}
		});
		add(leftbutton);
		
		rightbutton.setBounds(790, 550, 100, 100);
		rightbutton.setBorderPainted(false);
		rightbutton.setContentAreaFilled(false);
		rightbutton.setFocusPainted(false);
		rightbutton.setVisible(false);
		rightbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rightbutton.setIcon(rightbuttonselectedimage);
				rightbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				rightbutton.setIcon(rightbuttonimage);
				rightbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				selectRight();
			}
		});
		add(rightbutton);
	}
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		if (ismainscreen)
			g.drawImage(selectedimage, 440, 100, null);
		paintComponents(g);
		this.repaint();
	}
	
	public void selectTrack(int nowselected) {
		if(selectedMusic != null) {
			selectedMusic.close();
		selectedimage = new ImageIcon(Main.class.getResource("../images/"+tracklist.get(nowselected).getThumbnail())).getImage();
		selectedMusic = new Music(tracklist.get(nowselected).getHighlights(),true);
		selectedMusic.start();
		}
	}
	
	public void selectLeft() {
		if(nowselected == 0)
			nowselected = tracklist.size()-1;
		else
			nowselected--;
		selectTrack(nowselected);
	}
	
	public void selectRight() {
		if(nowselected == tracklist.size()-1)
			nowselected = 0;
		else
			nowselected++;
		selectTrack(nowselected);
	}
}
