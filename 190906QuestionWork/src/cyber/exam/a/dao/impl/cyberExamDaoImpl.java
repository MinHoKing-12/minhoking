package cyber.exam.a.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cyber.exam.a.dao.cyberExamDao;
import cyber.exam.a.model.ExamDto;
import cyber.exam.a.model.ExamQuestionDto;
import cyber.exam.a.model.ExamReplyDto;

@Repository
public class cyberExamDaoImpl implements cyberExamDao {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	String ns = "Exam.";

	@Override
	public List<ExamDto> getExamlist() {
		return sqlSession.selectList(ns + "getExamlist");
	}

	@Override
	public int isFinish(ExamReplyDto reply) {
		return sqlSession.selectOne(ns + "isFinish", reply);
	}

	@Override
	public void makeExam(ExamDto dto) {
		sqlSession.insert(ns + "makeExam", dto);
	}

	@Override
	public void makeExamSub(ExamQuestionDto Questiondto) {
		sqlSession.insert(ns + "makeExamSub", Questiondto);
	}

	@Override
	public ExamDto getExam(int seq) {
		return sqlSession.selectOne(ns + "getExam", seq);
	}

	@Override
	public List<ExamQuestionDto> getExamList(int seq) {
		return sqlSession.selectList(ns + "getExamSubList", seq);
	}

	@Override
	public void ExamSubmit(ExamReplyDto dto) {
		sqlSession.insert(ns + "ExamSubmit", dto);
		
	}
	@Override
	public void ExamCount(ExamReplyDto dto) {
		sqlSession.update(ns + "ExamCount", dto);
		
	}

	@Override
	public int ExamScore(ExamReplyDto rdto) {
		return sqlSession.selectOne(ns + "ExamSocre", rdto);
	}

	@Override
	public List<ExamReplyDto> getExamReply(ExamReplyDto rdto) {
		return sqlSession.selectList(ns + "getExamReply", rdto);
	}

	@Override
	public List<ExamReplyDto> getExamTotalScore(int seq) {
		return sqlSession.selectList(ns + "getExamTotalScore", seq);
	}

	
}
