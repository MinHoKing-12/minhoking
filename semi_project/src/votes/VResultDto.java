package votes;

public class VResultDto {
	
	private int seq;
	private String id;
	private int playnum;
	private String nickname;
	
	
	
	public VResultDto(String id) {
		super();
		this.id = id;
	}

	public VResultDto() {
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

	public int getPlaynum() {
		return playnum;
	}

	public void setPlaynum(int playnum) {
		this.playnum = playnum;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public VResultDto(int seq, String id, int playnum, String nickname) {
		super();
		this.seq = seq;
		this.id = id;
		this.playnum = playnum;
		this.nickname = nickname;
	}

	public VResultDto(String id, int playnum) {
		super();
		this.id = id;
		this.playnum = playnum;
	}
	
	

	public VResultDto(int seq, String id, int playnum) {
		super();
		this.seq = seq;
		this.id = id;
		this.playnum = playnum;
	}


	@Override
	public String toString() {
		return "VResultDto [seq=" + seq + ", id=" + id + ", playnum=" + playnum + ", nickname=" + nickname + "]";
	}
	
	
	}
	
		
	