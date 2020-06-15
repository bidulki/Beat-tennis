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

// beat tennis 클래스 구현 (대부분 이찬영)
// 백그라운드 이미지 제작 : 김도현
// 스프라이트 이미지 제작 : 이찬영
@SuppressWarnings("serial")
public class BeatTennis extends JFrame {

	// 이미지 import (이찬영)
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
	private ImageIcon backbuttonimage = new ImageIcon(Main.class.getResource("../images/backbutton.png"));
	private ImageIcon backbuttonselectedimage = new ImageIcon(Main.class.getResource("../images/backbuttonselected.png"));
	
	// 버튼 변수 선언(이찬영)
	private JButton quitbutton = new JButton(quitbuttonimage); // 우측 상단 닫기 버튼
	private JButton startbutton = new JButton(startbuttonimage); // 시작
	private JButton optionbutton = new JButton(optionbuttonimage); // 옵션 버튼? 기능은 아직 미구현인듯
	private JButton leftbutton = new JButton(leftbuttonimage); // 곡 왼쪽 선택
	private JButton rightbutton = new JButton(rightbuttonimage); // 곡 오른쪽 선택
	private JButton songstartbutton = new JButton(songstartbuttonimage); // 곡 시작
	private JButton backbutton = new JButton(backbuttonimage); // 뒤로가기
	
	private Image background = new ImageIcon(Main.class.getResource("../images/background3.jpeg")).getImage();
	private JLabel menubar = new JLabel(new ImageIcon(Main.class.getResource("../images/menubar.png"))); //상단 메뉴바

	Music IntroMusic = new Music("bgm1.mp3", false);
	
	private int mouseX, mouseY;

	private boolean ismainscreen = false;
	private boolean isgamescreen = false;
	
	// 트랙을 담는 arraylist & util (이찬영)
	ArrayList<Track> tracklist = new ArrayList<Track>();
	private Image selectedimage;
	private Music selectedMusic;
	private int nowselected=0;
	
	// beattennis 안에서 게임 시작할 때 생성할 game 클래스
	public static Game game;
	
	// 생성자
	public BeatTennis() {
		
		//Tracklist에 곡 추가 (민경환)
		tracklist.add(new Track("song1_thumbnail.png", "Control Cutting.mp3", "Unknown Brain & Rival - Control (ft. Jex).mp3","Unknown Brain & Rival - Control (ft. Jex)"));
		tracklist.add(new Track("song2_thumbnail.png", "Floor B2 Cutting.mp3", "Gradamical -Floor B2.mp3","Gradamical -Floor B2"));
		tracklist.add(new Track("song3_thumbnail.png", "Hollow Cutting.mp3", "Rob Gasser - Hollow (ft. Veronica Bravo).mp3","Rob Gasser - Hollow (ft. Veronica Bravo)"));
		
		// gui 설정 (이찬영)
		setUndecorated(true);
		setTitle("Beat Tennis");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(0, 0, 0, 0));
		setVisible(true);
		setLayout(null);
		
		// d,f,j,k 입력 받을 keylistener
		addKeyListener(new KeyListener());

		IntroMusic.start();
	
		// 버튼들 위치 설정, 기능 추가 & add (이찬영)
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
		
		// (이찬영)
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
		
		// (이찬영)
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
				enterMain();
			}
		});
		add(startbutton);
		
		// (이찬영)
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

		// (이찬영)
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
		
		// (이찬영)
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
		
		// (이찬영)
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
		
		// 메인화면으로 돌아가는 버튼 (왼쪽 화살표) (민경환)
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
	}
	
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	// 상황에 따른 screendraw (이찬영)
	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		if (ismainscreen) {
			g.drawImage(selectedimage, 440, 100, null);
		}
		// Game Screen (민경환)
		if (isgamescreen) {
			game.screenDraw(g);
		}
		paintComponents(g);
		this.repaint();
	}
	
	// 곡 선택 (이찬영)
	public void selectTrack(int nowselected) {
		if(selectedMusic != null)
			selectedMusic.close();
		selectedimage = new ImageIcon(Main.class.getResource("../images/"+tracklist.get(nowselected).getThumbnail())).getImage();
		selectedMusic = new Music(tracklist.get(nowselected).getHighlights(),true);
		selectedMusic.start();
	}
	
	// 곡 왼쪽 선택 (이찬영)
	public void selectLeft() {
		if(nowselected == 0)
			nowselected = tracklist.size()-1;
		else
			nowselected--;
		selectTrack(nowselected);
	}
	
	// 곡 오른쪽 선택 (이찬영)
	public void selectRight() {
		if(nowselected == tracklist.size()-1)
			nowselected = 0;
		else
			nowselected++;
		selectTrack(nowselected);
	}
	
	// When the game starts (민경환)
	public void gameStart(int nowSelected) {
		if(selectedMusic != null) 
			selectedMusic.close();
		ismainscreen = false;
		leftbutton.setVisible(false);
		rightbutton.setVisible(false);
		songstartbutton.setVisible(false);
		backbutton.setVisible(true);
		isgamescreen = true;
		game = new Game(tracklist.get(nowSelected).getTitle(),tracklist.get(nowSelected).getGamemusic());
		game.start();
		setFocusable(true);
		requestFocus();
	}
	
	// When the user wants go to back (민경환)
	public void backMain() {
		ismainscreen = true;
		leftbutton.setVisible(true);
		rightbutton.setVisible(true);
		songstartbutton.setVisible(true);
		backbutton.setVisible(false);
		game.close();
		selectTrack(nowselected);
		isgamescreen = false;
	}
	
	public void enterMain() {
		startbutton.setVisible(false);
		optionbutton.setVisible(false);
		background = new ImageIcon(Main.class.getResource("../images/background_transparency.jpeg")).getImage();
		songstartbutton.setVisible(true);
		ismainscreen = true;
		leftbutton.setVisible(true);
		rightbutton.setVisible(true);
		IntroMusic.close();
		selectTrack(nowselected);
	}
}
