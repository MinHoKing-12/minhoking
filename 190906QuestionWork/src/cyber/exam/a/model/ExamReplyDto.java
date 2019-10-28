package cyber.exam.a.model;

import java.io.Serializable;
import java.util.Date;

/*
 DROP TABLE EXAM_REPLY
CASCADE CONSTRAINTS;

DROP SEQUENCE EXAM_REPLY_SEQ;

CREATE TABLE EXAM_REPLY(	
	SEQ NUMBER NOT NULL,
	EXAM_SEQ NUMBER NOT NULL,
	QUESTION_SEQ NUMBER NOT NULL,
	ID VARCHAR2(50) NOT NULL,
	REPLY VARCHAR2(200) NOT NULL,
	SCORE NUMBER NOT NULL,
	REGDATE DATE NOT NULL,
	CONSTRAINT EXAM_REPLY_PK PRIMARY KEY(SEQ)
);

CREATE SEQUENCE EXAM_REPLY_SEQ
START WITH 1
INCREMENT BY 1;

ALTER TABLE EXAM_REPLY ADD CONSTRAINT EXAM_REPLY_FK1
FOREIGN KEY(EXAM_SEQ)
REFERENCES EXAM_INFO(SEQ)

ALTER TABLE EXAM_REPLY ADD CONSTRAINT EXAM_REPLY_FK2
FOREIGN KEY(QUESTION_SEQ)
REFERENCES EXAM_QUESTION(SEQ)

 */
public class ExamReplyDto implements Serializable {

	private int seq;			// seq
	private int exam_seq;		// 시험 seq
	private int question_seq;	// 문제+문항 seq
	private String id;			// 응시자
	private String reply;		// 제출한 답
	private int score;			// 총합
	private Date regdate;		// 제출날
	
	public ExamReplyDto() {
	}

	public ExamReplyDto(int seq, int exam_seq, int question_seq, String id, String reply, int score, Date regdate) {
		super();
		this.seq = seq;
		this.exam_seq = exam_seq;
		this.question_seq = question_seq;
		this.id = id;
		this.reply = reply;
		this.score = score;
		this.regdate = regdate;
	}

	public ExamReplyDto(int exam_seq,  String id) {
		super();
		this.exam_seq = exam_seq;
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getExam_seq() {
		return exam_seq;
	}

	public void setExam_seq(int exam_seq) {
		this.exam_seq = exam_seq;
	}

	public int getQuestion_seq() {
		return question_seq;
	}

	public void setQuestion_seq(int question_seq) {
		this.question_seq = question_seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "ExamReplyDto [seq=" + seq + ", exam_seq=" + exam_seq + ", question_seq=" + question_seq + ", id=" + id
				+ ", reply=" + reply + ", score=" + score + ", regdate=" + regdate + "]";
	}
}
