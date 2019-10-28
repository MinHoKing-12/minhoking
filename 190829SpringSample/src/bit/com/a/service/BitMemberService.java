package bit.com.a.service;

import bit.com.a.model.MemberDto;

public interface BitMemberService {

	public MemberDto logincheck(MemberDto mem) throws Exception;
		
	public boolean addmember(MemberDto mem) throws Exception;
	
	public boolean idCheck(String id) throws Exception;
	
	 
}
