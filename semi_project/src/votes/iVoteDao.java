package votes;

import java.util.List;

import votes.VoteDto;

public interface iVoteDao {

	public List<VoteDto> getVotelist();
	public List<VoteDto> ranklist();
	public VoteDto whoplayer(int seq);
	public void likecount(int seq);
	public List<VoteDto> findList(String search);
	//public List<VoteDto> whowinner();
}
