package free_bbs;

public class free_bbsDto {
	
	private int seq;
	private String id;
	private String nickname;
	private String title;
	private String content;
	private String wdate;
	private int del;
	private int blike;
	private int report;
	private int readcount;
	private int all;
	
	public free_bbsDto(int seq, String id, String nickname, String title, String content, String wdate, int blike, int del, int readcount, int all) {
		super();
		this.seq = seq;
		this.id = id;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.wdate = wdate;
		this.del = del;
		this.blike = blike;
		this.readcount = readcount;
		this.all = all;
	}
	
	public free_bbsDto(int seq, String id, String nickname, String title, String content, String wdate, int blike, int del, int readcount) {
		super();
		this.seq = seq;
		this.id = id;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.wdate = wdate;
		this.del = del;
		this.blike = blike;
		this.readcount = readcount;
	}
	
	
	public free_bbsDto(String id,String nickname, String title, String content) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
	}

	public free_bbsDto(int seq, String title, String content) {
		super();
		this.seq = seq;
		this.title = title;
		this.content = content;
	}

	
	
	public int getAll() {
		return all;
	}

	public void setAll(int all) {
		this.all = all;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public int getReport() {
		return report;
	}
	public void setReport(int report) {
		this.report = report;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "free_bbsDto [seq=" + seq + ", nickname=" + nickname + ", title=" + title + ", content=" + content
				+ ", wdate=" + wdate + ", del=" + del + ", blike=" + blike + ", report=" + report + ", readcount="
				+ readcount + "]";
	}
	
	
	
	
}
