package cyber.exam.a.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cyber.exam.a.dao.cyberMemberDao;
import cyber.exam.a.model.CyberMemberDto;
import cyber.exam.a.service.cyberMemberService;

@Service
public class cyberMemberServiceImpl implements cyberMemberService {

	@Autowired
	cyberMemberDao cyberDao;
	
	@Override
	public boolean addMember(CyberMemberDto dto) {
		return cyberDao.addMember(dto);
	}

	@Override
	public boolean checkId(String id) {
		return cyberDao.checkId(id);
	}

	@Override
	public CyberMemberDto LoginCheck(CyberMemberDto dto) {
		return cyberDao.LoginCheck(dto);
	}

}
