package cyber.exam.a.dao;

import java.util.List;

import cyber.exam.a.model.ExamDto;
import cyber.exam.a.model.ExamQuestionDto;
import cyber.exam.a.model.ExamReplyDto;
import cyber.exam.a.model.ReplyBean;

public interface cyberExamDao {

	// 리스트 불러오기
	public List<ExamDto> getExamlist();
	public int isFinish(ExamReplyDto reply);
	
	// 문제 만들기
	public void makeExam(ExamDto dto);
	public void makeExamSub(ExamQuestionDto Questiondto);
	
	// 문제 풀기
	public ExamDto getExam(int seq);
	public List<ExamQuestionDto> getExamList(int seq);
	
	public void ExamSubmit(ExamReplyDto dto);
	public void ExamCount(ExamReplyDto dto);
	
	// 결과
	public List<ExamReplyDto> getExamReply(ExamReplyDto rdto);
	public int ExamScore(ExamReplyDto rdto);
	
	// 시험지 푼사람의 점수들
	public List<ExamReplyDto> getExamTotalScore(int seq);
}
