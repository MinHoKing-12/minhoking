package cyber.exam.a.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cyber.exam.a.dao.cyberExamDao;
import cyber.exam.a.model.ExamDto;
import cyber.exam.a.model.ExamQuestionDto;
import cyber.exam.a.model.ExamReplyDto;
import cyber.exam.a.service.cyberExamService;

@Service
public class cyberExamServiceImpl implements cyberExamService {

	@Autowired
	cyberExamDao examdao;

	@Override
	public List<ExamDto> getExamlist(String id) {
		
		List<ExamDto> list = examdao.getExamlist();
		
		List<ExamDto> plist = new ArrayList<ExamDto>();
		
		for (ExamDto exam : list) {
			int seq = exam.getSeq();	// 시험 번호
			
			int count = examdao.isFinish(new ExamReplyDto(seq, id));
			
			if(count >= 1) {
				exam.setFinish(true);
			}else {
				exam.setFinish(false);
			}
			plist.add(exam);
		}
		return plist;
	}

	@Override
	public void makeExam(ExamDto dto) {
		examdao.makeExam(dto);
	}

	@Override
	public void makeExamSub(ExamQuestionDto Questiondto) {
		examdao.makeExamSub(Questiondto);
	}

	@Override
	public ExamDto getExam(int seq) {
		return examdao.getExam(seq);
	}

	@Override
	public List<ExamQuestionDto> getExamList(int seq) {
		return examdao.getExamList(seq);
	}

	@Override
	public void ExamSubmit(ExamReplyDto dto) {
		examdao.ExamSubmit(dto);
	}

	@Override
	public void ExamCount(ExamReplyDto dto) {
		examdao.ExamCount(dto);
	}

	@Override
	 public int ExamScore(ExamReplyDto rdto) {
		return examdao.ExamScore(rdto);
	}

	@Override
	public List<ExamReplyDto> getExamReply(ExamReplyDto rdto) {
		return examdao.getExamReply(rdto);
	}

	@Override
	public List<ExamReplyDto> getExamTotalScore(int seq) {
		return examdao.getExamTotalScore(seq);
	}
	
}
