package member;

import java.util.List;

public interface iMember {
	
	public boolean addMember(MemberDto dto);	// 멤버 추가
	
	public boolean duplicateCheck(String type, String id);	// 중복확인 (type == id, nickname, email)
	
	public boolean login(String id, String pwd);	// login -- id/pwd check
	
	public String getId(String nickname);	// Nickname를 이용하여 id값 가져오기
	
	public MemberDto getMemberDto(String id);	// MemberDto 얻기
	
	public String getMemberGrade(String id);	// 멤버 등급 가져오기 
	
	public String getGradeImg(String grade);	// 멤버 등급으로 등급 이미지이름 가져오기
	
	public boolean updateMember(MemberDto dto);	// 멤버 수정
	
	public String idfind(String email);	// e-mail로 id찾기
	
	public void sendEmail(MemberDto dto);	// 이메일 보내기
	
	public void tempPassword(String id);	// 임시 비밀번호 변경
	
	public int getTotalPages(String select, String text);	// 페이지 갯수 구하기 
	
	public List<MemberDto> getMemberList(String select, String text, int page);	// 관리자용 멤버리스트 (관리자 제외)

	public boolean deleteMember(String id);		// 회원탈퇴, 관리자 멤버 삭제
	
	public boolean multiDelete(String[] delarr);	// Multi-delete

	public int memCount();	// 전체 회원 수

	public boolean pwdUpdate(String id, String pwd);
	
	
	// 거래게시판용 method
	public MemberDto getSellMemberDto(String nickname);
	
}
