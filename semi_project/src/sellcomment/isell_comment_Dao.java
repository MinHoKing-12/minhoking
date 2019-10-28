package sellcomment;

import java.util.List;

public interface isell_comment_Dao {

	public List<sell_comment_Dto> getSell_Comment_List(int seq);	// 댓글 리스트 불러오기
	
	public boolean sell_Comment_insert(sell_comment_Dto dto);		// 댓글 쓰기
	
	public boolean sell_Comment_delete(int seq);
	
	public boolean comment_answer(int bbsseq, int commandSeq, sell_comment_Dto comment);
	
	public boolean comment_update(int seq, String content);
	
	public List<sell_comment_Dto> getSell_Comment_List(int seq, int size);
	
	public int commentCount(int seq);
	
	// mypage용 method
	public int myCommentCount(String id);
}
