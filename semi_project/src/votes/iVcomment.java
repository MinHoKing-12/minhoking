package votes;

import java.util.List;

import votes.VcommnetDto;

public interface iVcomment {
	
	public boolean addcomment(VcommnetDto dto);
	public VcommnetDto reply(int playernum);
	public boolean Updatereply(String content,int seq);
	public boolean deletereply(int del, int seq);
	public int getAllComment(int seq);
	public List<VcommnetDto> getBbsPagingList(int playnum ,int page);
	public String getId(String nickname);
	public int getAllvoteComment(String id);
	public List<VcommnetDto> votePagingList(String id, int page);
	public boolean managerdelete(int[] seq);
	
	// mypage용 method
	public int myCommentCount(String id);
}
