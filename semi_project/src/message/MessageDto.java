package message;

import java.io.Serializable;

public class MessageDto implements Serializable {
	private int seq;
	private String to_id;
	private String to_nick;
	private String from_id;
	private String from_nick;
	private String content;
	private int read;
	private String wdate;
	
	public MessageDto() {
	}

	public MessageDto(int seq, String to_id, String to_nick, String from_id, String from_nick, String content, int read,
			String wdate) {
		super();
		this.seq = seq;
		this.to_id = to_id;
		this.to_nick = to_nick;
		this.from_id = from_id;
		this.from_nick = from_nick;
		this.content = content;
		this.read = read;
		this.wdate = wdate;
	}
	
	public MessageDto(String to_id, String from_id, String content) {
		super();
		this.to_id = to_id;
		this.from_id = from_id;
		this.content = content;
	}

	public MessageDto(String to_id, String to_nick, String from_id, String from_nick, String content) {
		super();
		this.to_id = to_id;
		this.to_nick = to_nick;
		this.from_id = from_id;
		this.from_nick = from_nick;
		this.content = content;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTo_id() {
		return to_id;
	}

	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}

	public String getTo_nick() {
		return to_nick;
	}

	public void setTo_nick(String to_nick) {
		this.to_nick = to_nick;
	}

	public String getFrom_id() {
		return from_id;
	}

	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}

	public String getFrom_nick() {
		return from_nick;
	}

	public void setFrom_nick(String from_nick) {
		this.from_nick = from_nick;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	@Override
	public String toString() {
		return "MessageDto [seq=" + seq + ", to_id=" + to_id + ", to_nick=" + to_nick + ", from_id=" + from_id
				+ ", from_nick=" + from_nick + ", content=" + content + ", read=" + read + ", wdate=" + wdate + "]";
	}

	
	
}
