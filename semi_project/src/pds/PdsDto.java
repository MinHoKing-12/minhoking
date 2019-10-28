package pds;

import java.io.Serializable;

public class PdsDto implements Serializable {

	private int seq;
	private String id;
	private String title;
	private String content;
	
	private String filename;		// original 파일 네임 아님 한글 파일명을 올리면 깨지게 되므로 주의하세요 큰용량 쓰지마세요
//  private String oldfilename;		// 여기에는 한글이름이 된다???
	private int readcount;			// 조회수
	private int downloadcount;		// 다운로드 횟수
	
	private String regdate;			// 업로드 시간
	
	public PdsDto() {
	}

	public PdsDto(int seq, String id, String title, String content, String filename, int readcount, int downloadcount,
			String regdate) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.filename = filename;
		this.readcount = readcount;
		this.downloadcount = downloadcount;
		this.regdate = regdate;
	}

	public PdsDto(String id, String title, String content, String filename) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.filename = filename;
	}
	
	

	public PdsDto(int seq, String title, String content, String filename) {
		super();
		this.seq = seq;
		this.title = title;
		this.content = content;
		this.filename = filename;
	}

	public PdsDto(String id, String title, String content, String filename, String regdate) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.filename = filename;
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

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public int getDownloadcount() {
		return downloadcount;
	}

	public void setDownloadcount(int downloadcount) {
		this.downloadcount = downloadcount;
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
				+ filename + ", readcount=" + readcount + ", downloadcount=" + downloadcount + ", regdate=" + regdate
				+ "]";
	}
}
