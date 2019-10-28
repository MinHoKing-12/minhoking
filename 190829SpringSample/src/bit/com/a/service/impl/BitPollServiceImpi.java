package bit.com.a.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bit.com.a.dao.BitPollDao;
import bit.com.a.model.PollBean;
import bit.com.a.model.PollDto;
import bit.com.a.model.PollSubDto;
import bit.com.a.model.Voter;
import bit.com.a.service.BitPollService;

@Service
public class BitPollServiceImpi implements BitPollService {

	@Autowired
	BitPollDao polldao;

	@Override
	public List<PollDto> getPolllist(String id) {
		
		// 모든 투표 목록을 갖고 온다.
		List<PollDto> list = polldao.getPollList();
		
		// 투표를 할 수 있는지를 정리 해서 넘겨 줄 리스트
		List<PollDto> plist = new ArrayList<PollDto>();
		
		for (PollDto poll : list) {
			int pollid = poll.getPollid();	// 투표 번호
			
			int count = polldao.isVote(new Voter(pollid, -1, id));
			if(count == 1) {		// 투표 했을 경우
				poll.setVote(true);
			}else {					// 투표를 안했을 경우
				poll.setVote(false);
			}
			plist.add(poll);
		}
		return plist;
	}

	@Override
	public void makePoll(PollBean pbean) {
		
		System.out.println("질문 수 : " + pbean.toString());
		// 투표항목	PollDto
		PollDto poll = new PollDto(pbean.getId(), 
								   pbean.getQuestion(), 
								   pbean.getSdate(), 
								   pbean.getEdate(), 
								   pbean.getItemcount(), 
								   0);
		
		polldao.makePoll(poll);
		// 보기들	PollSubDto		
		String answer[] = pbean.getPollnum();
		
		for(int i = 0; i < pbean.getItemcount(); i++) {
			PollSubDto pollsub = new PollSubDto();
		//	pollsub.setPollid(poll.getPollid());
			pollsub.setAnswer(answer[i]);
			polldao.makePollSub(pollsub);
		}
	}

	@Override
	public PollDto getPoll(PollDto dto) {
		return polldao.getPoll(dto);
	}

	@Override
	public List<PollSubDto> getPollSubList(PollDto dto) {
		return polldao.getPollSubList(dto);
	}

	@Override
	public void polling(Voter voter) {
		polldao.pollingVoter(voter);		// 투표
		polldao.pollingSub(voter);			// 보기 카운트
		polldao.pollingPoll(voter);			// 질문 카운트
	}
	
	
}
