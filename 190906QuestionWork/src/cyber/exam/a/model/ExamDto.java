package cyber.exam.a.model;

import java.io.Serializable;
import java.util.Date;

/*
DROP TABLE EXAM_INFO
CASCADE CONSTRAINTS;

DROP SEQUENCE EXAM_INFO_SEQ;

CREATE TABLE EXAM_INFO(
	SEQ NUMBER NOT NULL,
	ID VARCHAR2(50) NOT NULL,
	SUBJECT VARCHAR2(100) NOT NULL,
	TITLE VARCHAR2(200) NOT NULL,
	QUESTIONCOUNT NUMBER NOT NULL,
	TOTALCOUNT NUMBER NOT NULL,
	LIMIT_TIME NUMBER NOT NULL,
	STARTDATE DATE NOT NULL,
	FINISHDATE DATE NOT NULL,
	REGDATE DATE NOT NULL,
	CONSTRAINT EXAM_INFO_PK PRIMARY KEY(SEQ)
);

CREATE SEQUENCE EXAM_INFO_SEQ
START WITH 1
INCREMENT BY 1;

ALTER TABLE EXAM_INFO ADD CONSTRAINT EXAM_INFO_FK
FOREIGN KEY(ID)
REFERENCES EXAM_MEMBER(ID);
*/
public class ExamDto implements Serializable {
	
	private int seq;			// seq
	private String id;			// 시험 만든 사람 ID
	private String subject;		// 과목
	private String title;		// 시험 제목
	private int questioncount;			// 문제의 수
	private int totalcount;		// 이 시험에 참여한 사람의 수
	private int limit_time;		// 시험 시간
	private Date startdate;		// 시작일
	private Date finishdate;	// 종료일
	private Date regdate;		// 시험 등록일
	
	private boolean finish;
	
	public ExamDto() {
	}

	public ExamDto(int seq, String id, String subject, String title, int questioncount, int totalcount, int limit_time,
			Date startdate, Date finishdate, Date regdate, boolean finish) {
		super();
		this.seq = seq;
		this.id = id;
		this.subject = subject;
		this.title = title;
		this.questioncount = questioncount;
		this.totalcount = totalcount;
		this.limit_time = limit_time;
		this.startdate = startdate;
		this.finishdate = finishdate;
		this.regdate = regdate;
		this.finish = finish;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getQuestioncount() {
		return questioncount;
	}

	public void setQuestioncount(int questioncount) {
		this.questioncount = questioncount;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getLimit_time() {
		return limit_time;
	}

	public void setLimit_time(int limit_time) {
		this.limit_time = limit_time;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getFinishdate() {
		return finishdate;
	}

	public void setFinishdate(Date finishdate) {
		this.finishdate = finishdate;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	@Override
	public String toString() {
		return "ExamDto [seq=" + seq + ", id=" + id + ", subject=" + subject + ", title=" + title + ", questioncount="
				+ questioncount + ", totalcount=" + totalcount + ", limit_time=" + limit_time + ", startdate="
				+ startdate + ", finishdate=" + finishdate + ", regdate=" + regdate + ", finish=" + finish + "]";
	}

	

}
