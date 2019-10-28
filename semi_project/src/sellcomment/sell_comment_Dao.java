package sellcomment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;

public class sell_comment_Dao implements isell_comment_Dao {

	//싱글톤
	private static sell_comment_Dao dao = new sell_comment_Dao();
	
	private sell_comment_Dao() {
		
	}
	
	public static sell_comment_Dao getInstance() {
		return dao;
	}
	
	public List<sell_comment_Dto> getSell_Comment_List(int seq) {
		
		String sql = " SELECT SEQ, M.ID, CONTENT, REF, STEP, DEPTS, "
				   + " PARENT, DEL, LIKECOUNT, REPORT, WDATE, NICKNAME "
				   + " FROM SELL_COMMENT C, MEMBER M"
				   + " WHERE C.ID = M.ID AND PARENT =? "
				   + " ORDER BY REF DESC, STEP ASC ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<sell_comment_Dto> list = new ArrayList<sell_comment_Dto>();
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("1/6 getSell_Comment_List1 suc");
			psmt.setInt(1, seq);
			rs = psmt.executeQuery();
			System.out.println("2/6 getSell_Comment_List1 suc");
			while(rs.next()) {
				
				sell_comment_Dto dto
				= new sell_comment_Dto(rs.getInt(1), 
									   rs.getString(2), 
									   rs.getString(3),
									   rs.getInt(4),
									   rs.getInt(5),
									   rs.getInt(6),
									   rs.getInt(7),
									   rs.getInt(8),
									   rs.getInt(9),
									   rs.getInt(10),
									   rs.getString(11),
									   rs.getString(12));
				list.add(dto);
				System.out.println("3/6 getSell_Comment_List1 suc");
			}
		} catch (SQLException e) {
			System.out.println("4/6 getSell_Comment_List1 fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
	}

	@Override
	public boolean sell_Comment_insert(sell_comment_Dto dto) {
		
		String sql = " INSERT INTO SELL_COMMENT "
				   + " (SEQ, ID, CONTENT, REF, STEP, DEPTS, PARENT, "
				   + " DEL, LIKECOUNT, REPORT, WDATE) "
				   + " VALUES(SELL_COMMENT_SEQ.NEXTVAL, ?, ?, "
				   + " (SELECT NVL(MAX(REF), 0)+1 FROM SELL_COMMENT), "
				   + " 0, 0, "
				   + " ?, 0, 0, 0, SYSDATE) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int num = 0;
		
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId().trim());
			psmt.setString(2, dto.getContent().trim());
			psmt.setInt(3, dto.getParent());
			
			num = psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return num>0?true:false;
	}

	@Override
	public boolean sell_Comment_delete(int seq) {
		String sql = " UPDATE SELL_COMMENT "
				   + " SET DEL=1 "
				   + " WHERE SEQ=? ";
		
		System.out.println("1/6 sell_Comment_delete suc");
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int num = 0;
		System.out.println("2/6 sell_Comment_delete suc");
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, seq);
			System.out.println("3/6 sell_Comment_delete suc");
			num = psmt.executeUpdate();
			System.out.println("4/6 sell_Comment_delete suc");
		} catch (SQLException e) {
			System.out.println("5/6 sell_Comment_delete fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return num>0?true:false;
	}

	@Override
	public boolean comment_answer(int bbsseq, int commandSeq, sell_comment_Dto comment) {
		//update
		String sql1 = " UPDATE SELL_COMMENT "
				    + " SET STEP = STEP + 1 "
				    + " WHERE REF=(SELECT REF FROM SELL_COMMENT WHERE SEQ=?)"
				    + " AND STEP > (SELECT STEP FROM SELL_COMMENT WHERE SEQ=?) ";
		
		//insert
		String sql2 = " INSERT INTO SELL_COMMENT "
				    + " (SEQ, ID, CONTENT, REF, STEP, DEPTS, PARENT, DEL, LIKECOUNT, REPORT, WDATE ) "
				    + " VALUES(SELL_COMMENT_SEQ.NEXTVAL, ?, ?, "
				    + " (SELECT REF FROM SELL_COMMENT WHERE SEQ=? ), "
				    + " (SELECT STEP FROM SELL_COMMENT WHERE SEQ=? ) + 1, "
				    + " (SELECT DEPTS FROM SELL_COMMENT WHERE SEQ=? ) + 1, "
				    + " ?, 0, 0, 0, SYSDATE) " ;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			System.out.println("1/6 comment_answer success");
			
			//update
			psmt = conn.prepareStatement(sql1);
			psmt.setInt(1, commandSeq);
			psmt.setInt(2, commandSeq);
			System.out.println("2/6 comment_answer success");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 comment_answer success");
			
			// psmt 초기화
			psmt.clearParameters();
			
			System.out.println("dto 들어가기전 : " + comment.toString());
			//insert
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, comment.getId());
			psmt.setString(2, comment.getContent());
			psmt.setInt(3, commandSeq);	//ref
			psmt.setInt(4, commandSeq);	//step
			psmt.setInt(5, commandSeq);	//depth
			psmt.setInt(6, bbsseq);	//parent
			System.out.println("4/6 comment_answer success");
			count = psmt.executeUpdate();
			conn.commit();
			System.out.println("5/6 comment_answer success");
		} catch (SQLException e) {
			System.out.println("7/6 comment_answer fail");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBClose.close(conn, psmt, null);
			System.out.println("6/6 comment_answer success");
		}
		return count>0?true:false;
	}

	@Override
	public boolean comment_update(int seq, String content) {
		String sql = " UPDATE SELL_COMMENT "
				   + " SET CONTENT = ?"
				   + " WHERE SEQ = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int num = 0;
		
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("1/6 update suc");
			psmt.setString(1, content);
			psmt.setInt(2, seq);
			
			num = psmt.executeUpdate();
			System.out.println("2/6 update suc");
		} catch (SQLException e) {
			System.out.println("3/6 update fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return num>0?true:false;
	}

	@Override
	public List<sell_comment_Dto> getSell_Comment_List(int seq, int size) {
		String sql = " SELECT SEQ, ID, CONTENT, REF, STEP, DEPTS, PARENT, DEL, "
		  		   + " LIKECOUNT, REPORT, WDATE, NICKNAME "
		  		   + " FROM "  
		  		   + " (SELECT ROW_NUMBER()OVER(ORDER BY REF DESC, STEP ASC) AS RNUM, " 
		  		   + " SEQ, C.ID, CONTENT, REF, STEP, DEPTS, PARENT, DEL, " 
		  		   + " LIKECOUNT, REPORT, WDATE, NICKNAME "
		  		   + " FROM SELL_COMMENT C, MEMBER M"
		  		   + " WHERE C.ID = M.ID AND PARENT = ? "
		  		   + " ORDER BY REF DESC, STEP ASC) "
		  		   + " WHERE RNUM >=? AND RNUM <=? AND PARENT = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<sell_comment_Dto> list = new ArrayList<sell_comment_Dto>();
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("1/6 getSell_Comment_List suc");
			psmt.setInt(1, seq);
			psmt.setInt(2, size*5-4);
			psmt.setInt(3, size*5);
			psmt.setInt(4, seq);
			System.out.println("2/6 getSell_Comment_List suc");
			rs = psmt.executeQuery();
			System.out.println("3/6 getSell_Comment_List suc");
			while(rs.next()) {
				sell_comment_Dto dto
				= new sell_comment_Dto(rs.getInt(1), 
									   rs.getString(2), 
									   rs.getString(3),
									   rs.getInt(4),
									   rs.getInt(5),
									   rs.getInt(6),
									   rs.getInt(7),
									   rs.getInt(8),
									   rs.getInt(9),
									   rs.getInt(10),
									   rs.getString(11),
									   rs.getString(12));
				list.add(dto);
				System.out.println("4/6 getSell_Comment_List suc");
			}
		} catch (SQLException e) {
			System.out.println("5/6 getSell_Comment_List fail");
			e.printStackTrace();
		} DBClose.close(conn, psmt, rs);
		return list;
	}

	@Override
	public int commentCount(int seq) {
		String sql = " SELECT COUNT(*) "
				   + " FROM SELL_COMMENT "
				   + " WHERE PARENT = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		System.out.println("1/6 commentCount suc");
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);	
			psmt.setInt(1, seq);
			System.out.println("2/6 commentCount suc");
			rs = psmt.executeQuery();
			System.out.println("3/6 commentCount suc");
				while(rs.next()) {
			count = rs.getInt(1);
				System.out.println("갯수 구하는 거 : " + count);
				System.out.println("4/6 commentCount suc");
		}
		} catch (SQLException e) {
			System.out.println("4/6 commentCount fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		if(count % 5 == 0){
			count = (count / 5);
		}else{
			count = (count / 5 + 1);
		}
		return count;
	}
	
	
	// mypage용 method
	@Override
	public int myCommentCount(String id) {
		String sql = " SELECT COUNT(*) FROM SELL_COMMENT WHERE ID=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);	
			psmt.setString(1, id);
			rs = psmt.executeQuery();
		
			while(rs.next()) {
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
