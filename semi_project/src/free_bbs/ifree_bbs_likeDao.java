package free_bbs;

public interface ifree_bbs_likeDao {

	public boolean addLike(int seq, String id);
	
	public boolean cancelLike(int page, String id);
	
	public boolean checkLike(int page, String id);
	
	// 댓글 좋아요	
	//public boolean addReplyLike(free_bbs_likeDto dto);
	
	public boolean adminDeletePageLike(int[] page);
}
