package bit.com.a.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bit.com.a.dao.BitMemberDao;
import bit.com.a.model.MemberDto;


@Repository
public class BitMemberDaoImpl implements BitMemberDao {

	// 어플리케이션에 있는 세션을 사용
	@Autowired
	SqlSession sqlSession;
	
	String namespace = "Member.";
	
	@Override
	public boolean addmember(MemberDto mem) throws Exception{
		
		int n = sqlSession.insert(namespace + "addmember", mem);
		
		return n>0?true:false;
	}

	@Override
	public MemberDto logincheck(MemberDto mem) throws Exception {
		
		MemberDto dto = sqlSession.selectOne(namespace+"loginCheck", mem);
		
		return dto;
	}

	@Override
	public boolean idCheck(String id) throws Exception {
		
		String check = sqlSession.selectOne(namespace+"idCheck", id);
		boolean a = false;
		if(check == null || check.equals("")) {
			a = false;
		}else {
			a = true;
		}
		
		return a;
	}
	
	

}
