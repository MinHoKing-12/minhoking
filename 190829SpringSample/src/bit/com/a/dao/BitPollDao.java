package bit.com.a.dao;

import java.util.List;

import bit.com.a.model.PollDto;
import bit.com.a.model.PollSubDto;
import bit.com.a.model.Voter;

public interface BitPollDao {

	// set
	// 투표 게시판 불러오는 리스트
	public List<PollDto> getPollList();
	// 중복 투표 여부 확인
	public int isVote(Voter voter);
	
	// 투표 만들기
	public void makePoll(PollDto poll);					// 뭐만드는거?
	public void makePollSub(PollSubDto pollsub);		// 얘도 뭐 만드는거?
	
	// 투표 하기
	public PollDto getPoll(PollDto dto);
	public List<PollSubDto> getPollSubList(PollDto dto);
	
	// 투표
	public void pollingVoter(Voter voter);
	public void pollingPoll(Voter voter);
	public void pollingSub(Voter voter);
}
