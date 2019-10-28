package votes;

import java.io.Serializable;

public class VcommnetDto  implements Serializable{

	private int seq;
	private String id;
	private String content;
	private int playernum;
	private String wdate;
	private int del;
	private String nickname;
	
	public VcommnetDto() {
		
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPlayernum() {
		return playernum;
	}

	public void setPlayernum(int playernum) {
		this.playernum = playernum;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public VcommnetDto(int seq, String id, String content, int playernum, String wdate, int del, String nickname) {
		super();
		this.seq = seq;
		this.id = id;
		this.content = content;
		this.playernum = playernum;
		this.wdate = wdate;
		this.del = del;
		this.nickname = nickname;
	}
	

	public VcommnetDto(String id, String content, int playernum) {
		super();
		this.id = id;
		this.content = content;
		this.playernum = playernum;
	}

	@Override
	public String toString() {
		return "VcommnetDto [seq=" + seq + ", id=" + id + ", content=" + content + ", playernum=" + playernum
				+ ", wdate=" + wdate + ", del=" + del + ", nickname=" + nickname + "]";
	}
	


}

	