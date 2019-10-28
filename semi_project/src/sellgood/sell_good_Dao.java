package sellgood;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBClose;
import db.DBConnection;

public class sell_good_Dao implements isell_good_Dao {

	// 싱글톤
	private static sell_good_Dao sell_good_dao = new sell_good_Dao();
	
	private sell_good_Dao() {
	}
	
	public static sell_good_Dao getInstance() {
		return sell_good_dao;
	}

	@Override
	public void like(sellgoodDto dto) {
		String sql = " INSERT INTO LIKE_BBS(SEQ, ID, ACOUNT) "
				   + " VALUES(SEQ_LIKE.NEXTVAL, ?, ?) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		System.out.println("1/6 like succ");
		int num = 0;
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, dto.getId());
			psmt.setInt(2, dto.getAcount());
			System.out.println("2/6 like succ");
			num = psmt.executeUpdate();
			System.out.println("3/6 like succ");
		} catch (SQLException e) {
			System.out.println("4/6 getSellList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
	}

	@Override
	public boolean pickId(int seq, String id) {
		
		String sql = " SELECT * FROM LIKE_BBS "
				   + " WHERE ACOUNT = ? AND ID = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		boolean exist = false;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			psmt.setString(2, id);
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exist;
	}

	@Override
	public boolean pickDeleteId(int seq, String id) {
		
		String sql = " DELETE FROM LIKE_BBS "
				   + " WHERE ACOUNT = ? AND ID = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		int num = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			psmt.setString(2, id);
			
			num = psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num>0?true:false;
	}
}
