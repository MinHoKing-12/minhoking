package bit.com.a.dao;

import bit.com.a.model.MemberDto;

public interface BitMemberDao {
	
	public MemberDto logincheck(MemberDto mem) throws Exception;
	
	public boolean addmember(MemberDto mem) throws Exception;
	
	public boolean idCheck(String id) throws Exception;
	
}
