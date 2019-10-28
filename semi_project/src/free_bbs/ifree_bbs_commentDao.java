package free_bbs;

import java.util.List;


public interface ifree_bbs_commentDao {

	public List<free_bbs_commentDto> getAdminBbsCommentList(int pageSeq, int rPageNumber, String choice, String text);
	
	public List<free_bbs_commentDto> getBbsCommentList(int parentSeq, int rPageNumber, int orderVal);
	
	public List<free_bbs_commentDto> getBbsCommentLikeList(int parentSeq);
	// 대댓글 총 갯수 count
	public int getReCommentListCnt(int parent);
	// 댓글 총 갯수 count(DEL = 0)
	public int getCommentListCnt(int page);
	// 관리자페이지 댓글 총 갯수 count
	public int getAdminCommentListCnt(int page, String choice, String text);
	
	//댓글 추가
	public boolean addReply(free_bbs_commentDto dto);
	//댓글 정보GET
	public free_bbs_commentDto getFreeBbsReply(int parentSeq);
	//댓글 삭제
	public boolean deleteReply(int replySeq);
	//댓글 수정     
	public boolean updateReply(int replySeq, String content);
	
//★대댓글		
	//대댓글 추가
	public boolean addReReply(free_bbs_commentDto dto);
	//대댓글 필터
	public List<free_bbs_commentDto> getReCommentList(int parent);
	//대댓글 삭제
	public boolean delteReReply(int reReplySeq);
	
//★BLIKE
	// likecount++
	public void cBlikePlus(int replySeq);
	// likecount--
	public void cBlikeMinus(int replySeq);
	// show likecount
	public int showrReplyLikecount(int replySeq);
	
//★관리자 댓글, 대댓글 삭제
	public boolean adminDeleteAllReply(int[] page);
//★관리자 게시글 상세페이지 댓글 삭제
	public boolean adminDeleteAllReply2(int[] parent);

	// MYPAGE용 METHOD
	public int myCommentCount(String id);

	
}
