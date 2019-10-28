package cyber.exam.a.dao;

import cyber.exam.a.model.CyberMemberDto;

public interface cyberMemberDao {

	public boolean addMember(CyberMemberDto dto);
	
	public boolean checkId(String id);
	
	public CyberMemberDto LoginCheck(CyberMemberDto dto);
}
