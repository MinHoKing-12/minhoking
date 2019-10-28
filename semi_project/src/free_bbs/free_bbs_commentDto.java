package free_bbs;

public class free_bbs_commentDto {

	private int seq;
	private String id;
	private String nickname;
	private String content;
	private String wdate;
	private int del;
	private int blike;
	private int parent;
	private int page;
	private int replyCnt;
	private int reReplyCnt;
	
	
	public free_bbs_commentDto(int page, String id, String nickname, String content) {
		super();
		this.page = page;
		this.id = id;
		this.nickname = nickname;
		this.content = content;
	}
	
	public free_bbs_commentDto(String nickname, String content, int parent, int page) {
		super();
		this.nickname = nickname;
		this.content = content;
		this.parent = parent;
		this.page = page;
	}

	public free_bbs_commentDto(int seq, String id, String nickname, String content, String wdate, int del, int blike, int parent, int page) {
		super();
		this.seq = seq;
		this.id = id;
		this.nickname = nickname;
		this.content = content;
		this.wdate = wdate;
		this.del = del;
		this.blike = blike;
		this.parent = parent;
		this.page = page;
	}
	
	
	public free_bbs_commentDto(int seq, String id, String nickname, String content, String wdate, int del, int blike,
			int parent, int page, int reReplyCnt) {
		super();
		this.seq = seq;
		this.id = id;
		this.nickname = nickname;
		this.content = content;
		this.wdate = wdate;
		this.del = del;
		this.blike = blike;
		this.parent = parent;
		this.page = page;
		this.reReplyCnt = reReplyCnt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public int getBlike() {
		return blike;
	}

	public void setBlike(int blike) {
		this.blike = blike;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getReplyCnt() {
		return replyCnt;
	}

	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}

	public int getReReplyCnt() {
		return reReplyCnt;
	}

	public void setReReplyCnt(int reReplyCnt) {
		this.reReplyCnt = reReplyCnt;
	}
	
	
}
