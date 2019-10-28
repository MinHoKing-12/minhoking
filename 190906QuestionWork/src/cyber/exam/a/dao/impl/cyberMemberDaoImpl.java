package cyber.exam.a.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cyber.exam.a.model.CyberMemberDto;

@Repository
public class cyberMemberDaoImpl implements cyber.exam.a.dao.cyberMemberDao {

	@Autowired
	SqlSession sqlSession;
	
	String namespace = "Member.";
	
	@Override
	public boolean addMember(CyberMemberDto dto) {
		
		int n = sqlSession.insert(namespace + "addMember", dto);
		
		return n>0?true:false;
	}

	@Override
	public boolean checkId(String id) {
		
		String check = sqlSession.selectOne(namespace+"CheckId", id);
		boolean a = false;
		if(check == null || check.equals("")) {
			a = false;
		}else {
			a = true;
		}
		return a;
	}

	@Override
	public CyberMemberDto LoginCheck(CyberMemberDto dto) {
		
		CyberMemberDto mem = sqlSession.selectOne(namespace + "LoginCheck", dto);
		
		return mem;
	}

}
