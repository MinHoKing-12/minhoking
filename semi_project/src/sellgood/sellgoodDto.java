package sellgood;
/*
CREATE TABLE LIKE_BBS(
SEQ NUMBER(8) PRIMARY KEY,
ID VARCHAR2(20) NOT NULL,
ACOUNT NUMBER(8) NOT NULL
);
 */
public class sellgoodDto {

	private int seq;
	private String id;
	private int acount;
	
	public sellgoodDto() {
	}
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAcount() {
		return acount;
	}
	public void setAcount(int acount) {
		this.acount = acount;
	}
	@Override
	public String toString() {
		return "sellgoodDto [seq=" + seq + ", id=" + id + ", acount=" + acount + "]";
	}

	public sellgoodDto(int seq, String id, int acount) {
		super();
		this.seq = seq;
		this.id = id;
		this.acount = acount;
	}
}
