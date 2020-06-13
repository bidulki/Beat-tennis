package beat_tennis_ver01;

public class Beat {

	private int time;
	private String noteType;

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
