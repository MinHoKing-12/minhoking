package sellcomment;

public class sell_comment_Dto {

	private int seq;
	private String id;
	private String nickname;
	private String content;
	
	private int ref;		// 그룹번호
	private int step;		// 행 번호
	private int depts;		// 깊이
	
	private int parent;
	private int del;
	private int likecount;
	private int report;
	private String date;
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
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getDepts() {
		return depts;
	}
	public void setDepts(int depts) {
		this.depts = depts;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	public int getLikecount() {
		return likecount;
	}
	public void setLikecount(int likecount) {
		this.likecount = likecount;
	}
	public int getReport() {
		return report;
	}
	public void setReport(int report) {
		this.report = report;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	@Override
	public String toString() {
		return "sell_comment_Dto [seq=" + seq + ", id=" + id + ", nickname=" + nickname + ", content=" + content
				+ ", ref=" + ref + ", step=" + step + ", depts=" + depts + ", parent=" + parent + ", del=" + del
				+ ", likecount=" + likecount + ", report=" + report + ", date=" + date + "]";
	}
	
	// 인서트용 생성자
	public sell_comment_Dto(int seq, String id, String content, int ref, int step, int depts, int parent, int del,
			int likecount, int report, String date, String nickname) {
		super();
		this.seq = seq;
		this.id = id;
		this.content = content;
		this.ref = ref;
		this.step = step;
		this.depts = depts;
		this.parent = parent;
		this.del = del;
		this.likecount = likecount;
		this.report = report;
		this.date = date;
		this.nickname = nickname;
	}
	// 댓글용 생성자
	public sell_comment_Dto(String id, String content, int parent) {
		super();
		this.id = id;
		this.content = content;
		this.parent = parent;
	}
	
	// 답글용 생성자
	public sell_comment_Dto(int seq, String id, String content) {
		super();
		this.seq = seq;
		this.id = id;
		this.content = content;
	}
}
