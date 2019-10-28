package bit.com.a.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bit.com.a.dao.BitMemberDao;
import bit.com.a.model.MemberDto;
import bit.com.a.service.BitMemberService;

@Service
public class BitMemberServieceImpl implements BitMemberService {

	@Autowired		// DI(Dependency Injection(의존성주입) 의존 객체를 생성하여 넘겨주는 것을 의미 
	//자동으로 spring framework가 (객체) 생성을 해줌.. a클래스가 b클래스에 의존할 때 b 오브젝트를 a가 직접 생성하지 않고 넘겨준다.
	private BitMemberDao bitMemberDao; 		//그냥 선언만 해 놓음
	// private BitMemberDao = new BitMemberDaoImpl; // 같은 말이지만 스프링에서는 사용 하지 마세요.
	
	
	public boolean addmember(MemberDto mem) throws Exception {
		return bitMemberDao.addmember(mem);
	}
	
	@Override
	public MemberDto logincheck(MemberDto mem) throws Exception {
		return bitMemberDao.logincheck(mem);
	}

	@Override
	public boolean idCheck(String id) throws Exception {
		return bitMemberDao.idCheck(id);
	}
	
	
}
