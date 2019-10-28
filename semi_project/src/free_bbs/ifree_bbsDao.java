package free_bbs;

import java.util.List;

public interface ifree_bbsDao {

	
	public List<free_bbsDto> getAllBbsList(int pageNumber, String choice, String text);
	
	public List<free_bbsDto> getBbsList(int pageNumber);
	// detail
	public free_bbsDto getDetailBbs(int seq);
	// add 
	public boolean addBbs(free_bbsDto dto);
	// delete
	public boolean deleteBbs(int seq);
	// update
	public boolean updateBbs(free_bbsDto dto);
	//search
	public List<free_bbsDto> searchBbsList(String choice, String text);
	//paging
	public List<free_bbsDto> getBbsPagingList(int pageNumber, String choice, String text);
	// readcount++
	public void readCount(int seq);
	// likecount++
	public void likeCountPlus(int seq);
	// likecount--
	public void likeCountMinus(int seq);
	// show likecount
	public int showLikecount(int seq);
	
	//all posting
	public int allPosting(String choice, String text);
	//all admin postingCount
	public int allAdminPosting(String choice, String text);
	//all admin delete page 
	public boolean adminDeletePage(int[] page);
	
	
	// My page용 Method
	public List<free_bbsDto> getMyList(String id, int page, String select, String str);
	public int getMyListCount(String id, String select, String str);
	public int bbsCount(String id);
	public List<free_bbsDto> getMyLikeList(String id, int page);
	public int getMyLikeListCount(String id);

	
}
