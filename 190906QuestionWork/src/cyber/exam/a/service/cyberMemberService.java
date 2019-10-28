package cyber.exam.a.service;

import cyber.exam.a.model.CyberMemberDto;

public interface cyberMemberService {

	public boolean addMember(CyberMemberDto dto);
	
	public boolean checkId(String id);
	
	public CyberMemberDto LoginCheck(CyberMemberDto dto);
}
