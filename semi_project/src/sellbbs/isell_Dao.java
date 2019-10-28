package sellbbs;

import java.util.List;

import sellbbs.sell_Dto;

public interface isell_Dao {
	
	public List<sell_Dto> getSellList();
		
	public boolean Sell_Insert(String id, String category, String pcategory, String filename, String title, String content, int price);
	
	public sell_Dto detailSell(int seq);		// 상세화면
	
	public void readCount(int seq);				// 조회수
	
	public boolean deleteSell(int seq);			// 삭제(del 번호 1로 변경)
	
	public boolean updateSell(sell_Dto dto);
	
	public List<sell_Dto> getFindList(int page, String text, String searchValue);
	
	public List<sell_Dto> getCategoryList(int page, String searchValue);
	
	public List<sell_Dto> getSellList(int size);			// 중고게시판 페이징
	
	public int pageCount(String selectValue, String context);	// 검색창 갯수 새는 것
	
	public int pageCountMain();									// 메인 페이지 갯수 새는 것
	
	public int pageCategory(String searchValue);				// 카테고리 갯수 새는 것
	
	public void likeCount(int seq);				// 좋아요
	
	public int getLikeCount(int seq);			// 좋아요 갯수 가져오기
	
	public void likeDisCount(int seq);			// 좋아요 빼기
	
	public boolean completeSell(int seq);		// 거래완료(COMPLETE 번호 1로 변경)
	
	public boolean multidelsellbbs(int[] seq);   // 관리자모드 게시판 멀티 삭제
	
	public boolean multidelsellComment(int[] seq);	// 게시판 삭제 하기 위한 댓글 삭제
	
	public boolean multidelsellLike(int[] seq);		// 게시판 삭제 하기 위한 좋아요 삭제
	
	public List<sell_Dto> getSellListadmin(int size);	// 관리자 게시판 리스트 가져오기
	
	public boolean adminmultidelsellComment(int[] seq);
	
	// My page 나의 거래게시글 불러오기
	public List<sell_Dto> getMyList(String id, int page, String select, String str);
	public int getMyListCount(String id, String select, String str);
	public int bbsCount(String id);
	public List<sell_Dto> getMyLikeList(String id, int page);
	public int getMyLikeListCount(String id);
}
