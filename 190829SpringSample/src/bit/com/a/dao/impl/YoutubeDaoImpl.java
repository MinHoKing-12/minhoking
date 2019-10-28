package bit.com.a.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bit.com.a.dao.YoutubeDao;
import bit.com.a.model.YoutubeSave;

@Repository
public class YoutubeDaoImpl implements YoutubeDao {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	String ns = "Youtube.";
	
	// Youtube 동영상 저장
	@Override
	public boolean writeYoutube(YoutubeSave save) {
		int n = sqlSession.insert(ns + "writeYoutube", save);
		return n>0?true:false;
	}

	@Override
	public YoutubeSave getYoutube(YoutubeSave save) {
		return sqlSession.selectOne(ns + "getYoutube", save);
	}

	@Override
	public List<YoutubeSave> getYoutubeList(YoutubeSave save) {

		return sqlSession.selectList(ns + "getYoutubeList", save);
	}
}
