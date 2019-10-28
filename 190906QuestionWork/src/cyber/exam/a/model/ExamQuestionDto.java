package cyber.exam.a.model;

import java.io.Serializable;

/*
DROP TABLE EXAM_QUESTION
CASCADE CONSTRAINTS;

DROP SEQUENCE EXAM_QUESTION_SEQ;

CREATE TABLE EXAM_QUESTION(
	SEQ NUMBER NOT NULL,
	EXAM_SEQ NUMBER NOT NULL,
	SCORE NUMBER NOT NULL,
	QUESTION VARCHAR2(500) NOT NULL,
	QUESTION_SUB VARCHAR2(4000),
	FILENAME VARCHAR2(200),
	ORIGINAL_FILENAME VARCHAR2(200),
	CORRECT_ANSWER VARCHAR2(200) NOT NULL,
	ANSWER1 VARCHAR2(200),
	ANSWER2 VARCHAR2(200),
	ANSWER3 VARCHAR2(200),
	ANSWER4 VARCHAR2(200),
	CONSTRAINT EXAM_QUESTION_PK PRIMARY KEY(SEQ)
);

CREATE SEQUENCE EXAM_QUESTION_SEQ
START WITH 1
INCREMENT BY 1;

ALTER TABLE EXAM_QUESTION ADD CONSTRAINT EXAM_QUESTION_FK
FOREIGN KEY(EXAM_SEQ)
REFERENCES EXAM_INFO(SEQ)
 */

public class ExamQuestionDto implements Serializable {

	private int seq;
	private int exam_seq;
	
	private int score;
	private String question;
	private String question_sub;
	
	private String filename;
	private String original_filename;
	
	private String correct_answer;
	
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	
	public ExamQuestionDto() {
	}

	public ExamQuestionDto(int seq, int exam_seq, int score, String question, String question_sub, String filename,
			String original_filename, String correct_answer, String answer1, String answer2, String answer3,
			String answer4) {
		super();
		this.seq = seq;
		this.exam_seq = exam_seq;
		this.score = score;
		this.question = question;
		this.question_sub = question_sub;
		this.filename = filename;
		this.original_filename = original_filename;
		this.correct_answer = correct_answer;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestion_sub() {
		return question_sub;
	}

	public void setQuestion_sub(String question_sub) {
		this.question_sub = question_sub;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getOriginal_filename() {
		return original_filename;
	}

	public void setOriginal_filename(String original_filename) {
		this.original_filename = original_filename;
	}

	public String getCorrect_answer() {
		return correct_answer;
	}

	public void setCorrect_answer(String correct_answer) {
		this.correct_answer = correct_answer;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	@Override
	public String toString() {
		return "ExamQuestionDto [seq=" + seq + ", exam_seq=" + exam_seq + ", score=" + score + ", question=" + question
				+ ", question_sub=" + question_sub + ", filename=" + filename + ", original_filename="
				+ original_filename + ", correct_answer=" + correct_answer + ", answer1=" + answer1 + ", answer2="
				+ answer2 + ", answer3=" + answer3 + ", answer4=" + answer4 + "]";
	}
	
}
