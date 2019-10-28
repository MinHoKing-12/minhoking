package bit.com.a.service;

import java.util.List;

import bit.com.a.model.PollBean;
import bit.com.a.model.PollDto;
import bit.com.a.model.PollSubDto;
import bit.com.a.model.Voter;

public interface BitPollService {

	// 리스트와 isVote를 합치는 작업을 서비스에서 한다.
	public List<PollDto> getPolllist(String id);
	
	public void makePoll(PollBean pbean);
	
	public PollDto getPoll(PollDto dto);
	
	public List<PollSubDto> getPollSubList(PollDto dto);
	
	public void polling(Voter voter);
			
}
