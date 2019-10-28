package bit.com.a.dao;

import java.util.List;

import bit.com.a.model.YoutubeSave;

public interface YoutubeDao {
	
	public boolean writeYoutube(YoutubeSave save);
	
	public YoutubeSave getYoutube(YoutubeSave save);
	
	public List<YoutubeSave> getYoutubeList(YoutubeSave save);

}
