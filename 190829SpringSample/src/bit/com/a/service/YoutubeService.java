package bit.com.a.service;

import java.util.List;

import bit.com.a.model.YoutubeSave;

public interface YoutubeService {

	public boolean writeYoutube(YoutubeSave save);
	
	public YoutubeSave getYoutube(YoutubeSave save);
	
	public List<YoutubeSave> getYoutubeList(YoutubeSave save);
}
