package beat_tennis_ver01;

public class Track {
	
	private String thumbnail; // �����
	private String highlights; // �� �������� �� ���̶���Ʈ �Ϻκ�
	private String gamemusic; // ���� ��
	
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnnail) {
		this.thumbnail = thumbnnail;
	}
	public String getHighlights() {
		return highlights;
	}
	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}
	
	public String getGamemusic() {
		return gamemusic;
	}
	public void setGamemusic(String gamemusic) {
		this.gamemusic = gamemusic;
	}
	
	public Track(String thumbnail, String highlights, String gamemusic) {
		super();
		this.thumbnail = thumbnail;
		this.highlights = highlights;
		this.gamemusic = gamemusic;
	}
}
