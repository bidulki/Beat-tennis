package beat_tennis_ver01;

// 비트 클래스 구현 (이찬영)
public class Beat {

	private int time; //비트가 떨어지기 시작하는 시점
	private String noteType; // d,f,j,k

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	Beat(int time, String noteType) {
		super();
		this.time = time;
		this.noteType = noteType;
	}
}
