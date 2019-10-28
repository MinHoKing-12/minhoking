package cyber.exam.a.service;

import java.util.List;

import cyber.exam.a.model.ExamDto;
import cyber.exam.a.model.ExamQuestionDto;
import cyber.exam.a.model.ExamReplyDto;

public interface cyberExamService {
	
	public List<ExamDto> getExamlist(String id);

	public void makeExam(ExamDto dto);
	public void makeExamSub(ExamQuestionDto Questiondto);
	
	public ExamDto getExam(int seq);
	public List<ExamQuestionDto> getExamList(int seq);
	
	public void ExamSubmit(ExamReplyDto dto);
	public void ExamCount(ExamReplyDto dto);
	
	public int ExamScore(ExamReplyDto rdto);
	public List<ExamReplyDto> getExamReply(ExamReplyDto rdto);
	
	public List<ExamReplyDto> getExamTotalScore(int seq);
}
