package free_bbs;

public class free_bbs_comment_likeDto {

	private int seq;
	private int replyseq;
	private String id;
	
	public free_bbs_comment_likeDto(int replyseq, String id) {
		super();
		this.replyseq = replyseq;
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getReplyseq() {
		return replyseq;
	}

	public void setReplyseq(int replyseq) {
		this.replyseq = replyseq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
