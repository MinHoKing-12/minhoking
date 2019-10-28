package free_bbs;

public interface ifree_bbs_comment_likeDao {

	public boolean addReplyLike(int replySeq, String id);
	
	public boolean cancelReplyLike(int replySeq, String id);
	
	public boolean checkReplyLike(int replySeq, String id);
	
	public boolean adminDeleteReplyLike(int[] page);
	
	public boolean adminDeleteReplyLike2(int[] replySeq);
	
}
