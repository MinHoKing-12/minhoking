package free_bbs;

public class free_bbs_likeDto {

	private int seq;	
	private int page;
	private String id;
	
	public free_bbs_likeDto(int page, String id) {
		super();
		this.page = page;
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
		
	
	
	

	
	
}
