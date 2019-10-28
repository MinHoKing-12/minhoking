package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import db.DBClose;
import db.DBConnection;

public class MemberDao implements iMember{
	private static MemberDao dao = new MemberDao();
	
	private MemberDao() {
		DBConnection.initConnection();
	}
	
	public static MemberDao getInstance() {
		return dao;
	}

	@Override
	public boolean addMember(MemberDto dto) {
		String sql = " INSERT INTO MEMBER "
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, 0, SYSDATE, 3) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPwd());
			psmt.setString(3, dto.getNickname());
			psmt.setString(4, dto.getName());
			psmt.setInt(5, dto.getPostnum());
			psmt.setString(6, dto.getAddress());
			psmt.setString(7, dto.getAddress_detail());
			psmt.setString(8, dto.getEmail());
			
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return count>0?true:false;
	}

	@Override
	public boolean duplicateCheck(String type, String str) {
		String sql = " SELECT * FROM MEMBER ";
		
		if(type.equals("id")) {
			sql += " WHERE ID=? ";
		}else if(type.equals("nickname")) {
			sql += " WHERE NICKNAME=? ";
		}else if(type.equals("email")) {
			sql += " WHERE EMAIL=? ";
		}
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		boolean isExist = false;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, str);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				isExist = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return isExist;
		
	}
	
	@Override
	public boolean login(String id, String pwd) {
		
		String sql = " SELECT * FROM MEMBER "
				+ " WHERE ID=? AND PWD=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		boolean isS = false;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pwd);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				isS = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return isS;
	}

	@Override
	public String getId(String nickname) {
		String sql = " SELECT ID FROM MEMBER "
				+ " WHERE NICKNAME=?";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String id = "";
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nickname);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				id = rs.getString(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return id;
	}

	@Override
	public MemberDto getMemberDto(String id) {
		String sql = " SELECT ID, PWD, NICKNAME, NAME, POSTNUM, ADDRESS, ADDRESS_DETAIL, EMAIL, POINT, JDATE, AUTH "
				+ "	FROM MEMBER "
				+ " WHERE ID=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MemberDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDto(rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getInt(5),
									rs.getString(6),
									rs.getString(7),
									rs.getString(8),
									rs.getInt(9),
									rs.getString(10),
									rs.getInt(11));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return dto;
	}

	@Override
	public String getMemberGrade(String id) {
		
		String sql = " SELECT G.NAME " + 
				" FROM MEMBER M, MEMBER_GRADE G " + 
				" WHERE M.POINT >= G.MIN_POINT " + 
				"	AND M.POINT < G.MAX_POINT " +
				" 	AND M.ID=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String grade = "";
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				grade = rs.getString(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return grade;
	}

	@Override
	public String getGradeImg(String grade) {
		String sql = " SELECT IMG_NAME FROM MEMBER_GRADE WHERE NAME=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String imgname = "";
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, grade);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				imgname = rs.getString(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return imgname;
	}

	@Override
	public boolean updateMember(MemberDto dto) {
		
		String sql = " UPDATE MEMBER "
				+ " SET PWD=?, NICKNAME=?, POSTNUM=?, ADDRESS=?, ADDRESS_DETAIL=?, EMAIL=? "
				+ " WHERE ID=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getPwd());
			psmt.setString(2, dto.getNickname());
			psmt.setInt(3, dto.getPostnum());
			psmt.setString(4, dto.getAddress());
			psmt.setString(5, dto.getAddress_detail());
			psmt.setString(6, dto.getEmail());
			psmt.setString(7, dto.getId());
			
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return count>0?true:false;

	}

	@Override
	public String idfind(String email) {
		String sql = " SELECT ID FROM MEMBER "
				+ " WHERE EMAIL=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String id = "";
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, email);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				id = rs.getString(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return id;
	}
	
	@Override
	public void sendEmail(MemberDto dto) {
		// Mail Server 설정
		String user = "kiwoom127@gmail.com";
		String password = "zldnatjvhxjwm";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com"); 
		prop.put("mail.smtp.port", 465);	// naver는 587 
		prop.put("mail.smtp.auth", "true"); 
		prop.put("mail.smtp.ssl.enable", "true"); 
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		// 세션생성
		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
		System.out.println("세션생성 성공");
		
		try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            
            System.out.println(dto.getEmail());
            //수신자메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(dto.getEmail())); 
            // Subject
            message.setSubject("Kiwoom Supporters 임시 비밀번호입니다"); //메일 제목을 입력
            // Text
            String text = 	" <div align=\"center\"> " + 
		            		"<h2>안녕하세요! 키움 서포터즈입니다.</h2> " + 
		            		"<hr> " + 
		            		"<p> " + dto.getId() +  
		            		"회원님의 임시 비밀번호를 발급해드립니다.<br> " + 
		            		"<font style='color: #f00'>로그인 후 꼭 비밀번호를 변경해주시기 바랍니다.</font><br> " + 
		            		"<b>임시 비밀번호 : " + dto.getPwd() + "</b><br> " + 
		            		"</p> " + 
		            		"<hr> ";
            message.setContent(text, "text/html; charset=utf-8");    //메일 내용을 입력
            // send the message
            Transport.send(message); ////전송
            System.out.println("message sent successfully");
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

	}

	@Override
	public void tempPassword(String id) {
		
		String pwd = "";
		for (int i = 0; i < 4; i++) {
			pwd += (char)((Math.random() * 26) + 97);
			pwd += (char)((Math.random() * 10) + 48);
		}
		System.out.println(pwd);
		
		String sql = " UPDATE MEMBER "
				+ " SET PWD=? "
				+ " WHERE ID=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pwd);
			psmt.setString(2, id);
			
			psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
	}
	
	@Override
	public int getTotalPages(String select, String text) {
		String sql = " SELECT COUNT(*) FROM MEMBER WHERE AUTH=3 ";
		if(text != null && !text.equals("")) {
			if(select.equals("id")) {
				sql += " AND ID=? ";
			}else if(select.equals("nickname")) {
				sql += " AND NICKNAME=? ";
			}else if(select.equals("name")) {
				sql += " AND NAME=? ";
			}else if(select.equals("address")) {
				sql += " AND ADDRESS LIKE ? ";
			}else if(select.equals("email")) {
				sql += " AND EMAIL LIKE ? ";
			}
		}
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		int totalPages = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			if(text != null && !text.equals("")) {
				if(select.equals("id") || select.equals("nickname") || select.equals("name")) {
					psmt.setString(1, text);
				}else {
					psmt.setString(1, "%" + text + "%");
				}
			}
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			if(count % 10 == 0) {
				totalPages = count / 10;
			}else {
				totalPages = count / 10 + 1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return totalPages;
	}
	
	@Override
	public List<MemberDto> getMemberList(String select, String text, int page) {
		String sql = 	" SELECT ID, PWD, NICKNAME, NAME, POSTNUM, ADDRESS, ADDRESS_DETAIL, EMAIL, POINT, JDATE, AUTH " + 
						" FROM (SELECT ROW_NUMBER()OVER(ORDER BY ID) RNUM, ID, PWD, NICKNAME, " + 
								" NAME, POSTNUM, ADDRESS, ADDRESS_DETAIL, EMAIL, POINT, JDATE, AUTH " + 
								" FROM MEMBER WHERE AUTH = 3 ";
		if(text != null && !text.equals("")) {
			if(select.equals("id")) {
				sql += " AND ID=? ";
			}else if(select.equals("nickname")) {
				sql += " AND NICKNAME=? ";
			}else if(select.equals("name")) {
				sql += " AND NAME=? ";
			}else if(select.equals("address")) {
				sql += " AND ADDRESS LIKE ? ";
			}else if(select.equals("email")) {
				sql += " AND EMAIL LIKE ? ";
			}
		}
		
		sql += ") WHERE RNUM >= ? AND RNUM <= ? ";
				
		System.out.println("getMemberList select : " + select);
		System.out.println("getMemberList text : " + text);
		System.out.println("getMemberList page : " + page);
		System.out.println("getMemberList sql : " + sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<MemberDto> list = new ArrayList<MemberDto>();
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			if(text != null && !text.equals("")) {
				if(select.equals("id") || select.equals("nickname") || select.equals("name")) {
					psmt.setString(1, text);
				}else {
					psmt.setString(1, "%" + text + "%");
				}
				psmt.setInt(2, page * 10 - 9);
				psmt.setInt(3, page * 10);
			}else {
				psmt.setInt(1, page * 10 - 9);
				psmt.setInt(2, page * 10);
			}
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				MemberDto dto = new MemberDto(rs.getString(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getInt(5),
										rs.getString(6),
										rs.getString(7),
										rs.getString(8),
										rs.getInt(9),
										rs.getString(10),
										rs.getInt(11));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}
	/*
	// 5. 자유게시판의 내가 쓴 댓글을 지운다.
	String sql5 = " DELETE FROM FREE_BBS_COMMENT WHERE ID=? ";
	
	// 6. 자유게시판의 내가 쓴 글을 지우기 위해 해당 게시글의 댓글을 먼저 지운다...
	String sql6 = " DELETE " 
				+ " FROM FREE_BBS_COMMENT C, (SELECT SEQ FROM FREE_BBS WHERE ID=?) B "
				+ " WHERE PARENT = B.SEQ; ";
	*/
	@Override
	public boolean deleteMember(String id) {
		// 1. 거래게시판의 내가 쓴 댓글을 지운다.
		String sql1 = " DELETE FROM SELL_COMMENT WHERE ID=? ";
		
		// 2. 거래게시판의 내가 쓴 글을 지운다. 근데,, 해당 게시글의 댓글을 먼저 지운다...
		String sql2 = " DELETE " 
					+ " FROM( SELECT * "
							+ " FROM SELL_COMMENT C, (SELECT * FROM SELL_BBS WHERE ID=?) M " 
							+ " WHERE M.SEQ = C.PARENT) ";
		// 3. 해당 게시글의 좋아요 카운트를 지운다...
		String sql3 = " DELETE " 
					+ " FROM( SELECT * "
							+ " FROM LIKE_BBS L, (SELECT * FROM SELL_BBS WHERE ID=?) M " 
							+ " WHERE M.SEQ = L.ACOUNT) ";
		// 4. 거래게시판의 내가 쓴 글을 지운다.
		String sql4 = " DELETE FROM SELL_BBS WHERE ID=? ";
		
		
		// 5. 자유게시판의 내가 쓴 댓글을 지운다. 근데,, 해당 댓글의 좋아요 부터 지운다
		String sql5 = " DELETE " + 
						" FROM ( SELECT * FROM FREE_BBS_COMMENT_LIKE L, " + 
									" (SELECT SEQ FROM FREE_COMMENT WHERE ID=?) C " + 
								" WHERE C.SEQ = L.REPLYSEQ) ";
		
		
		// 6. 자유게시판의 내가 쓴 댓글을 지운다.
		String sql6 = " DELETE FROM FREE_COMMENT WHERE ID=? ";
		
		// 7. 자유게시판의 내가 쓴 글을 지운다. 근데,, 해당 게시글의 댓글을 먼저 지운다... 근데,, 해당 게시글의 좋아요 카운트부터 지운다..
		String sql7 = " DELETE " + 
				"FROM (SELECT * FROM " + 
						" (SELECT C.SEQ FROM " + 
							" (SELECT SEQ FROM FREE_BBS WHERE ID=?) B, FREE_COMMENT C " + 
								" WHERE B.SEQ = C.PAGE) C, FREE_BBS_COMMENT_LIKE L " + 
							" WHERE C.SEQ = L.REPLYSEQ) ";
		
		// 8. 자유게시판의 내가 쓴 글을 지운다. 근데,, 해당 게시글의 댓글을 이제 지울 수 있다!!!
		String sql8 = " DELETE " 
					+ " FROM( SELECT * "
							+ " FROM FREE_COMMENT C, (SELECT * FROM FREE_BBS WHERE ID=?) M " 
							+ " WHERE M.SEQ = C.PAGE) ";
		// 9. 해당 게시글의 좋아요 카운트를 지운다...
		String sql9 = " DELETE " 
					+ " FROM( SELECT * "
							+ " FROM FREE_BBS_LIKE L, (SELECT * FROM FREE_BBS WHERE ID=?) M " 
							+ " WHERE M.SEQ = L.PAGE) ";
		// 10. 자유게시판의 내가 쓴 글을 지운다.
		String sql10 = " DELETE FROM FREE_BBS WHERE ID=? ";
		
		// 11. 내가 작성 또는 받은 메세지들을 삭제한다.
		String sql11 = " DELETE "
				+ " FROM MESSAGE "
				+ " WHERE TO_ID=? OR FROM_ID=? ";
		
		// 12. 투표게시판의 내가 쓴 댓글을 삭제한다...
		String sql12 = " DELETE FROM VOTE_COMMENT "
					 + " WHERE ID=? ";
		
		// 13. 투표게시판의 나의 투표기록을 삭제한다.
		String sql13 = " DELETE FROM VOTE_BBS WHERE ID=? ";
		
		// 14. 탈퇴!!!!!!!!!!!!!!!!
		String sql14 = " DELETE FROM MEMBER WHERE ID=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("1. 거래게시판의 댓글 삭제");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("2. 거래게시판의 게시글 삭제 - 해당 게시글 댓글삭제");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql3);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("3. 거래게시판의 게시글 삭제 - 해당 게시글 좋아요 삭제");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql4);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("4. 거래게시판의 게시글 삭제 완료");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql5);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("5. 자유게시판 나의 댓글 삭제 - 코멘트 좋아요 삭제");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql6);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("6. 자유게시판 나의 댓글 삭제!");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql7);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("8. 자유게시판의 게시글 삭제 - 해당 게시글 댓글삭제 - 해당 댓글의 좋아요 삭제");
			
			psmt = conn.prepareStatement(sql8);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("8. 자유게시판의 게시글 삭제 - 해당 게시글 댓글삭제");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql9);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("9. 자유게시판의 게시글 삭제 - 해당 게시글 좋아요삭제");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql10);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("10. 자유게시판의 게시글 삭제 완료");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql11);
			psmt.setString(1, id);
			psmt.setString(2, id);
			count = psmt.executeUpdate();
			System.out.println("11. 나의 메세지 모두 삭제");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql12);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("12. 투표게시판 댓글 삭제");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql13);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("13. 투표게시판 투표기록 삭제");
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql14);
			psmt.setString(1, id);
			count = psmt.executeUpdate();
			System.out.println("14. 회원탈퇴!!!!!!!!!!!!!!!!!!!!!!!");
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBClose.close(conn, psmt, null);
		}
		
		
		return count>0?true:false;
	}

	@Override
	public boolean multiDelete(String[] delarr) {
		
		boolean isS = false;
		
		for (int i = 0; i < delarr.length; i++) {
			isS = deleteMember(delarr[i]);
			System.out.println("isS : " + isS);
			if(isS == false) {
				break;
			}
		}
		
		return isS;
	}

	@Override
	public int memCount() {
		
		String sql = " SELECT COUNT(*) FROM MEMBER WHERE AUTH=3 ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return count;
	}

	@Override
	public boolean pwdUpdate(String id, String pwd) {
		String sql = " UPDATE MEMBER "
				+ " SET PWD=? "
				+ " WHERE ID=? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pwd);
			psmt.setString(2, id);
			
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return count>0?true:false;
	}
	
	@Override
	public MemberDto getSellMemberDto(String nickname) {
		String sql = " SELECT ID, PWD, NICKNAME, NAME, POSTNUM, ADDRESS, ADDRESS_DETAIL, EMAIL, POINT, JDATE, AUTH "
				+ "	FROM MEMBER "
				+ " WHERE NICKNAME=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MemberDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nickname);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDto(rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getInt(5),
									rs.getString(6),
									rs.getString(7),
									rs.getString(8),
									rs.getInt(9),
									rs.getString(10),
									rs.getInt(11));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return dto;
	}
		
}
