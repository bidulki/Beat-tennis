package beat_tennis_ver01;

/*
 * 메인 메소드 구현 (김도현)
 */

public class Main {

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final int REACH_TIME = 1; // 노트가 생성됐을 때 몇 초 후에 떨어질지 결정하는 함수
	public static int NOTE_SPEED = 8; // SLEEP_TIME당 떨어지는 픽셀 수
	public static int SLEEP_TIME = 10; // 한번 NOTE_SPEED씩 떨어뜨릴때마다 이만큼 슬립
	
	public static void main(String[] args) {
		
		new BeatTennis();

	}

}
