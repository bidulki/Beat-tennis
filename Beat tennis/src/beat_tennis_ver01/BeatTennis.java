package beat_tennis_ver01;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	private ImageIcon songstartbuttonimage = new ImageIcon(Main.class.getResource("../images/songstartbutton.png"));
	private ImageIcon songstartbuttonselectedimage = new ImageIcon(Main.class.getResource("../images/songstartbuttonselected.png"));
	//private ImageIcon backbuttonimage = new ImageIcon(Main.class.getResource("../images/backbutton.png"));
	//private ImageIcon backbuttonselectedimage = new ImageIcon(Main.class.getResource("../images/backbuttonselected.png"));
	
	private JButton quitbutton = new JButton(quitbuttonimage);
	private JButton startbutton = new JButton(startbuttonimage);
	private JButton optionbutton = new JButton(optionbuttonimage);
	private JButton leftbutton = new JButton(leftbuttonimage);
	private JButton rightbutton = new JButton(rightbuttonimage);
	private JButton songstartbutton = new JButton(songstartbuttonimage);
	//private JButton backbutton = new JButton(backbuttonimage);
	
	private Image background = new ImageIcon(Main.class.getResource("../images/background3.jpeg")).getImage();
	private JLabel menubar = new JLabel(new ImageIcon(Main.class.getResource("../images/menubar.png")));

	private int mouseX, mouseY;

	private boolean ismainscreen = false;
	private boolean isgamescreen = false;
	
	ArrayList<Track> tracklist = new ArrayList<Track>();
	private Image selectedimage;
	//private Image titleimage = new ImageIcon(Main.class.getResource("../images/song1_thumbnail_title.png")).getImage(); -> 곡 타이틀 이미지 넣는 코드
	private Music selectedMusic;
	private int nowselected=0;
	
	public static Game game = new Game();
	
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
		
		addKeyListener(new KeyListener());

		Music IntroMusic = new Music("bgm1.mp3", false);
		IntroMusic.start();
		
		//Tracklist에 곡 추가, 원하는 곡 추가 후, 이름 바꾸기
		tracklist.add(new Track("song1_thumbnail.png", "Control Cutting.mp3", "Unknown Brain & Rival - Control (ft. Jex).mp3"));
		tracklist.add(new Track("song2_thumbnail.png", "Control Cutting.mp3", "Unknown Brain & Rival - Control (ft. Jex).mp3"));
		tracklist.add(new Track("song3_thumbnail.png", "Control Cutting.mp3", "Unknown Brain & Rival - Control (ft. Jex).mp3"));
		
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
				IntroMusic.close();
				selectTrack(nowselected);
				startbutton.setVisible(false);
				optionbutton.setVisible(false);
				songstartbutton.setVisible(true);
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
		
		songstartbutton.setBounds(510, 550, 260, 100);
		songstartbutton.setBorderPainted(false);
		songstartbutton.setContentAreaFilled(false);
		songstartbutton.setFocusPainted(false);
		songstartbutton.setVisible(false);
		songstartbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				songstartbutton.setIcon(songstartbuttonselectedimage);
				songstartbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				songstartbutton.setIcon(songstartbuttonimage);
				songstartbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				gameStart(nowselected);// 게임 시작 구현
			}
		});
		add(songstartbutton);
		
		// backbutton을 구현하면 활성화하기!!
		/*
		backbutton.setBounds(20, 50, 60, 60);
		backbutton.setBorderPainted(false);
		backbutton.setContentAreaFilled(false);
		backbutton.setFocusPainted(false);
		backbutton.setVisible(false);
		backbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				backbutton.setIcon(backbuttonselectedimage);
				backbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				backbutton.setIcon(backbuttonimage);
				backbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				backMain();
			}
		});
		add(backbutton);
		 */
	}
	
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		if (ismainscreen) {
			g.drawImage(selectedimage, 440, 100, null);
			//g.drawImage(titleimage, 440, 70, null); -> 곡 타이틀 넣기
		}
		// Game Screen
		if (isgamescreen) {
			game.screenDraw(g);
		}
		paintComponents(g);
		this.repaint();
	}
	
	public void selectTrack(int nowselected) {
		if(selectedMusic != null)
			selectedMusic.close();
		selectedimage = new ImageIcon(Main.class.getResource("../images/"+tracklist.get(nowselected).getThumbnail())).getImage();
		selectedMusic = new Music(tracklist.get(nowselected).getHighlights(),true);
		selectedMusic.start();
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
	
	// When the game starts
	public void gameStart(int nowSelected) {
		if(selectedMusic != null) 
			selectedMusic.close();
		ismainscreen = false;
		leftbutton.setVisible(false);
		rightbutton.setVisible(false);
		songstartbutton.setVisible(false);
		//backbutton.setVisible(true); -> backbutton 구현되면 주석 풀어주기!
		isgamescreen = true;
	}
	
	// When the user wants go to back
	public void backMain() {
		ismainscreen = true;
		leftbutton.setVisible(true);
		rightbutton.setVisible(true);
		songstartbutton.setVisible(true);
		//backbutton.setVisible(false); -> backbutton 구현되면 주석 풀어주기!
		selectTrack(nowselected);
		isgamescreen = false;
	}
}
