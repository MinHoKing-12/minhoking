package bit.com.a.model;

import java.io.Serializable;

public class PdsDto implements Serializable {

	private int seq;
	private String id;
	private String title;
	private String content;
	
	private String filename;		// original 파일 네임 아님 한글 파일명을 올리면 깨지게 되므로 주의하세요 큰용량 쓰지마세요
    private String oldfilename;		// 여기에는 한글이름이 된다???
	private int readcount;			// 조회수
	private int downcount;		// 다운로드 횟수
	
	private String regdate;			// 업로드 시간
	
	public PdsDto() {
	}
	
	public PdsDto(int seq, String id, String title, String content, String filename, String oldfilename, int readcount,
			int downcount, String regdate) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.filename = filename;
		this.oldfilename = oldfilename;
		this.readcount = readcount;
		this.downcount = downcount;
		this.regdate = regdate;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getOldfilename() {
		return oldfilename;
	}

	public void setOldfilename(String oldfilename) {
		this.oldfilename = oldfilename;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public int getdowncount() {
		return downcount;
	}

	public void setdowncount(int downcount) {
		this.downcount = downcount;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "PdsDto [seq=" + seq + ", id=" + id + ", title=" + title + ", content=" + content + ", filename="
				+ filename + ", oldfilename=" + oldfilename + ", readcount=" + readcount + ", downcount="
				+ downcount + ", regdate=" + regdate + "]";
	}
}
