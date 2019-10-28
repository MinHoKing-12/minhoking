package bit.com.a.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bit.com.a.dao.YoutubeDao;
import bit.com.a.model.YoutubeSave;
import bit.com.a.service.YoutubeService;

@Service
public class YoutubeServiceImpl implements YoutubeService {

	@Autowired
	YoutubeDao youtubedao;

	@Override
	public boolean writeYoutube(YoutubeSave save) {
		return youtubedao.writeYoutube(save);
	}

	@Override
	public YoutubeSave getYoutube(YoutubeSave save) {
		return youtubedao.getYoutube(save);
	}

	@Override
	public List<YoutubeSave> getYoutubeList(YoutubeSave save) {
		return youtubedao.getYoutubeList(save);
	}
	
	
}
