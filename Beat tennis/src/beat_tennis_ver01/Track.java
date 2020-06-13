package beat_tennis_ver01;

public class Track {
	
	private String title; // 곡명
	private String thumbnail; // 썸네일
	private String highlights; // 곡 선택했을 때 하이라이트 일부분
	private String gamemusic; // 전제 곡
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
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
	
	public Track(String thumbnail, String highlights, String gamemusic, String title) {
		super();
		this.thumbnail = thumbnail;
		this.highlights = highlights;
		this.gamemusic = gamemusic;
		this.title = title;
	}
}
