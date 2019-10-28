package message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;

public class MessageDao implements iMessage {
	
	private static MessageDao dao = new MessageDao();
	
	private MessageDao() {
		DBConnection.initConnection();
	}
	
	public static MessageDao getInstance() {
		return dao;
	}
	
	@Override
	public int getPageCount(String type, String id, String select, String str) {
		String sql = " SELECT COUNT(*) FROM MESSAGE ";
		
		if(type.equals("receive")) {
			sql += " WHERE TO_ID=? ";
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					sql += " AND FROM_ID=? ";
				}else if(select.equals("content")) {
					sql += " AND CONTENT LIKE ? ";
				}
			}
		}else if(type.equals("send")) {
			sql += " WHERE FROM_ID=? ";
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					sql += " AND TO_ID=? ";
				}else if(select.equals("content")) {
					sql += " AND CONTENT LIKE ? ";
				}
			}
		}
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		int pages = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					psmt.setString(2, str);
				}else if(select.equals("content")) {
					psmt.setString(2, "%" + str + "%");
				}
			}
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		if(count % 5 == 0) {
			pages = count / 5;
		}else {
			pages = (count / 5) + 1;
		}
		return pages;
	}

	@Override
	public List<MessageDto> getMsgList(String type, int page, String id, String select, String str) {
		
		String sql = " SELECT SEQ, TO_ID, TO_NICK, FROM_ID, FROM_NICK, CONTENT, READ, WDATE " + 
					" FROM (SELECT ROW_NUMBER() OVER(ORDER BY WDATE DESC) RNUM, " + 
							" SEQ, TO_ID, M1.NICKNAME AS TO_NICK, FROM_ID, M2.NICKNAME AS FROM_NICK, CONTENT, READ, WDATE " + 
							" FROM MESSAGE A, MEMBER M1, MEMBER M2 " + 
					" WHERE A.TO_ID = M1.ID AND FROM_ID = M2.ID ";
					
		if(type.equals("receive")) {
			sql += " AND TO_ID = ? ";
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					sql += " AND FROM_ID=? ";
				}else if(select.equals("content")) {
					sql += " AND CONTENT LIKE ? ";
				}
			}
			
		}else if(type.equals("send")) {
			sql += " AND FROM_ID = ? ";
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					sql += " AND TO_ID=? ";
				}else if(select.equals("content")) {
					sql += " AND CONTENT LIKE ? ";
				}
			}
		}
		
		sql += " )WHERE RNUM BETWEEN ? AND ? ";
		
		System.out.println(sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<MessageDto> list = new ArrayList<MessageDto>();
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					psmt.setString(2, str);
				}else if(select.equals("content")) {
					psmt.setString(2, "%" + str + "%");
				}
				psmt.setInt(3, (page * 5) - 4);
				psmt.setInt(4, page * 5);
			}else {
				psmt.setInt(2, (page * 5) - 4);
				psmt.setInt(3, page * 5);
			}
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				MessageDto dto = new MessageDto(rs.getInt(1),
												rs.getString(2),
												rs.getString(3),
												rs.getString(4),
												rs.getString(5),
												rs.getString(6),
												rs.getInt(7),
												rs.getString(8));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
	}
	
	
	
	@Override
	public List<MessageDto> getMsgList(String type, int page, String id, String select, String str, String category) {
		
		String sql = " SELECT SEQ, TO_ID, TO_NICK, FROM_ID, FROM_NICK, CONTENT, READ, WDATE " + 
					" FROM (SELECT ROW_NUMBER() OVER(ORDER BY WDATE DESC) RNUM, " + 
							" SEQ, TO_ID, M1.NICKNAME AS TO_NICK, FROM_ID, M2.NICKNAME AS FROM_NICK, CONTENT, READ, WDATE " + 
							" FROM MESSAGE A, MEMBER M1, MEMBER M2 " + 
					" WHERE A.TO_ID = M1.ID AND FROM_ID = M2.ID ";
					
		if(type.equals("receive")) {
			sql += " AND TO_ID = ? ";
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					sql += " AND FROM_ID=? ";
				}else if(select.equals("content")) {
					sql += " AND CONTENT LIKE ? ";
				}
			}
			if(category != null) {
				if(category.equals("read")) {
					sql += " AND READ = 1 ";
				}else if(category.equals("not-read")) {
					sql += " AND READ = 0 ";
				}
			}
			
		}else if(type.equals("send")) {
			sql += " AND FROM_ID = ? ";
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					sql += " AND TO_ID=? ";
				}else if(select.equals("content")) {
					sql += " AND CONTENT LIKE ? ";
				}
			}
			
			if(category != null) {
				if(category.equals("read")) {
					sql += " AND READ = 1 ";
				}else if(category.equals("not-read")) {
					sql += " AND READ = 0 ";
				}
			}
		}
		
		sql += " )WHERE RNUM BETWEEN ? AND ? ";
		
		System.out.println(sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<MessageDto> list = new ArrayList<MessageDto>();
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					psmt.setString(2, str);
				}else if(select.equals("content")) {
					psmt.setString(2, "%" + str + "%");
				}
				psmt.setInt(3, (page * 5) - 4);
				psmt.setInt(4, page * 5);
			}else {
				psmt.setInt(2, (page * 5) - 4);
				psmt.setInt(3, page * 5);
			}
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				MessageDto dto = new MessageDto(rs.getInt(1),
												rs.getString(2),
												rs.getString(3),
												rs.getString(4),
												rs.getString(5),
												rs.getString(6),
												rs.getInt(7),
												rs.getString(8));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
	}
	
	
	@Override
	public int getPageCount(String type, String id, String select, String str, String category) {
		String sql = " SELECT COUNT(*) FROM MESSAGE ";
		
		if(type.equals("receive")) {
			sql += " WHERE TO_ID=? ";
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					sql += " AND FROM_ID=? ";
				}else if(select.equals("content")) {
					sql += " AND CONTENT LIKE ? ";
				}
			}
			if(category != null) {
				if(category.equals("read")) {
					sql += " AND READ = 1 ";
				}else if(category.equals("not-read")) {
					sql += " AND READ = 0 ";
				}
			}
		}else if(type.equals("send")) {
			sql += " WHERE FROM_ID=? ";
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					sql += " AND TO_ID=? ";
				}else if(select.equals("content")) {
					sql += " AND CONTENT LIKE ? ";
				}
			}
			if(category != null) {
				if(category.equals("read")) {
					sql += " AND READ = 1 ";
				}else if(category.equals("not-read")) {
					sql += " AND READ = 0 ";
				}
			}
		}
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		int pages = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			if(str != null && !str.equals("")) {
				if(select.equals("id")) {
					psmt.setString(2, str);
				}else if(select.equals("content")) {
					psmt.setString(2, "%" + str + "%");
				}
			}
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		if(count % 5 == 0) {
			pages = count / 5;
		}else {
			pages = (count / 5) + 1;
		}
		return pages;
	}
	
	@Override
	public boolean writeMsg(MessageDto dto) {
		String sql = " INSERT INTO MESSAGE "
				+ " VALUES(MESSAGE_SEQ.NEXTVAL, ?, ?, ?, 0, SYSDATE) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getTo_id());
			psmt.setString(2, dto.getFrom_id());
			psmt.setString(3, dto.getContent());
						
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return count>0?true:false;
	}

	@Override
	public MessageDto getMessageDto(int seq) {
		String sql = " SELECT * FROM MESSAGE "
					+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MessageDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				dto = new MessageDto(rs.getInt(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getString(6),
									rs.getInt(7),
									rs.getString(8));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return dto;
	}

	@Override
	public void readMessage(int seq) {
		String sql = " UPDATE MESSAGE "
				+ " SET READ = 1 "
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
						
			psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
	}
	
	public boolean deleteMessage(int seq) {
		String sql = " DELETE "
				+ " FROM MESSAGE"
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
						
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return count>0?true:false;
	}

	@Override
	public boolean multidelMessage(int[] seq) {
		String sql = " DELETE "
				+ " FROM MESSAGE"
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count[] = new int[seq.length];
		
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(sql);
			
			for (int i = 0; i < seq.length; i++) {
				psmt.setInt(1, seq[i]);
				psmt.addBatch(); // 쿼리 실행을 하지 않고 쿼리 구문을 메모리에 올려두었다가, 실행 명령이 있으면 한번에 DB쪽으로 쿼리를 날린다.
			}
			count = psmt.executeBatch();	// 메모리에 올라와있던 쿼리구문 실행 후 실행 결과를 배열로 반환한다. (-2 : 정상종료)
											// 이 부분에서 에러가 발생하면 커밋이 되지 않고 예외발생부로 이동
			conn.commit();
			
			System.out.println("multidel complete");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			try {
				conn.rollback();	// 롤백!
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBClose.close(conn, psmt, null);
		}
		
		boolean isS = true;	// 정상적으로 종료됬다는 가정 하에
		
		for (int i = 0; i < count.length; i++) {
			if(count[i] != -2) {	// -2 == 정상종료 : 정상종료가 안됬다면
				isS = false;	// false로 바꾸고
				break;	// for문을 빠져나와라
			}
		}
		
		return isS;
	}
	
	@Override
	public int notReadMsgCount(String id) {
		String sql = " SELECT COUNT(*) FROM MESSAGE "
					+ " WHERE READ = 0 "
					+ " GROUP BY TO_ID " 
					+ " HAVING TO_ID=? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
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
	public int msgCount(String type, String id) {
		String sql = " SELECT COUNT(*) FROM MESSAGE ";
		
		if(type.equals("receive")) {
			sql += " GROUP BY TO_ID " 
					+ " HAVING TO_ID=? ";
		}else if(type.equals("send")) {
			sql += " GROUP BY FROM_ID " 
					+ " HAVING FROM_ID=? ";
		}
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
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
	
	
}
