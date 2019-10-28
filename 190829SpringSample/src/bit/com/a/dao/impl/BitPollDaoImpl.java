package bit.com.a.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bit.com.a.dao.BitPollDao;
import bit.com.a.model.PollDto;
import bit.com.a.model.PollSubDto;
import bit.com.a.model.Voter;

@Repository
public class BitPollDaoImpl implements BitPollDao {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	String ns = "Poll.";

	@Override
	public List<PollDto> getPollList() {
		return sqlSession.selectList(ns + "getPolllist");
	}

	@Override
	public int isVote(Voter voter) {
		return sqlSession.selectOne(ns + "isVote", voter);
	}

	@Override
	public void makePoll(PollDto poll) {
		sqlSession.insert(ns + "makePoll", poll);
	}

	@Override
	public void makePollSub(PollSubDto pollsub) {
		sqlSession.insert(ns + "makePollSub", pollsub);	
	}

	@Override
	public PollDto getPoll(PollDto dto) {
		return sqlSession.selectOne(ns + "getPoll", dto);
	}

	@Override
	public List<PollSubDto> getPollSubList(PollDto dto) {
		return sqlSession.selectList(ns + "getPollSubList", dto);
	}

	@Override
	public void pollingVoter(Voter voter) {
		sqlSession.insert(ns + "pollingVote", voter);
		
	}

	@Override
	public void pollingPoll(Voter voter) {
		sqlSession.update(ns + "pollingPoll", voter);
		
	}

	@Override
	public void pollingSub(Voter voter) {
		sqlSession.update(ns + "pollingSub", voter);
		
	}
	
}
