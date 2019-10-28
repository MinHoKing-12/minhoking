package votes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
import votes.VResultDto;

public class VResultDao implements iVResultDao {

	private static VResultDao dao = null;

	// singleton
	public static VResultDao getInstance() {
		if (dao == null) {
			dao = new VResultDao();
		}
		return dao;
	}

	public VResultDao() {
		DBConnection.initConnection();
	}

	// 투표하는것
	public boolean Pickplayer(VResultDto dto) {

		String sql = " INSERT INTO VOTE_BBS ( SEQ, ID, PLAYNUM ) " 
				   + " VALUES ( SEQ_VOTE.NEXTVAL, ? , ? )";

		Connection conn = null;
		PreparedStatement psmt = null;

		System.out.println("sql:" + sql);

		int count = 0;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getId());
			psmt.setInt(2, dto.getPlaynum());

			count = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}

		return count > 0 ? true : false;
	}

	// 투표함에 동일 아이디 있는지 조회
	public boolean votenick(String id) {
		String sql = " SELECT * FROM VOTE_BBS V , MEMBER B " 
					+ " WHERE V.ID = B.ID AND V.ID = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		boolean isExist = false;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();

			if (rs.next()) {
				isExist = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}

		return isExist;

	}

	// 관리자페이지 페이징하려고 총갯수
	public int getAllpick(int searchnum) {

		String sql = " SELECT COUNT(*) FROM VOTE_BBS ";

		if (searchnum == 0) {

			sql += "";
		} else {
			sql += " WHERE PLAYNUM = ? ";
		}

		System.out.println("getAllpick: " + sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		int bbsLen = 0;

		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getAllpick suc");
			psmt = conn.prepareStatement(sql);

			if (searchnum != 0) {
				psmt.setInt(1, searchnum);
			}

			System.out.println("2/6 getAllpick suc");
			rs = psmt.executeQuery();

			if (rs.next()) {
				bbsLen = rs.getInt(1);
			}
			System.out.println("3/6 getAllpick suc");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return bbsLen;
	}

	@Override
	public List<VResultDto> VotepagingList(int page, int searchnum) {
		String sql = "SELECT SEQ, ID, PLAYNUM " + " FROM ";
		
				sql += " ( SELECT ROW_NUMBER()OVER(ORDER BY SEQ) AS RNUM,  " 
				    + " SEQ, ID, PLAYNUM ";

		System.out.println("listDao page : " + page);
		System.out.println("listDao searchnum : " + searchnum);
		if (searchnum == 0) {

			     sql += " FROM VOTE_BBS ) ";
		} else {
			sql += " FROM VOTE_BBS  " + " WHERE PLAYNUM = ? ) ";

		}
		sql += " WHERE RNUM >= ? AND RNUM <= ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		System.out.println("sql: " + sql);

		List<VResultDto> list = new ArrayList<VResultDto>();

		int start, end;
		start = 1 + 10 * page;
		end = 10 + 10 * page;

		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 VotepagingList suc");

			psmt = conn.prepareStatement(sql);
			if (searchnum != 0) {
				psmt.setInt(1, searchnum);
				psmt.setInt(2, start);
				psmt.setInt(3, end);
			} else {

				psmt.setInt(1, start);
				psmt.setInt(2, end);
			}

			System.out.println("2/6 VotepagingList suc");

			rs = psmt.executeQuery();
			System.out.println("3/6 VotepagingList suc");

			while (rs.next()) {

				VResultDto dto = new VResultDto(rs.getInt(1), rs.getString(2), rs.getInt(3));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(" paginglist 실패 ");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}

		return list;
	}

	
	// 1등 투표자 인원수 구하기
	 public int allwinner(int playnum) {
		String sql = " SELECT COUNT(*) FROM VOTE_BBS" 
					+ " WHERE PLAYNUM = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		int allwinner = 0;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, playnum);

			rs = psmt.executeQuery();

			if (rs.next()) {
				allwinner = rs.getInt(1);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return allwinner;
	}

	// 이벤트랜덤추첨
	public VResultDto FindWinner(int playnum, int Rnum) {
		String sql = " SELECT ID " 
				  + " FROM ( SELECT ROW_NUMBER()OVER(ORDER BY SEQ) AS RNUM, SEQ, ID, PLAYNUM "
				  + " FROM VOTE_BBS " 
				  + " WHERE PLAYNUM = ? ) " 
				  + " WHERE RNUM = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		System.out.println("FindWinner 1/6 suc ");

		VResultDto dto = null;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("FindWinner 2/6 suc ");
			psmt.setInt(1, playnum);
			psmt.setInt(2, Rnum);

			rs = psmt.executeQuery();
			System.out.println("FindWinner 3/6 suc ");

			if (rs.next()) {

				String id = rs.getString(1);

				dto = new VResultDto(id);

			}
			System.out.println("FindWinner 4/6 suc ");
		}

		catch (SQLException e) {
			System.out.println("FindWinner 실패");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);

		}
		return dto;
	}

	// 당첨자에게 메세지 보내기
	public boolean winmessage(String to, String from, String content) {
		String sql = " INSERT INTO MESSAGE( SEQ, TO_ID, FROM_ID, CONTENT, READ, WDATE )"
				+ " VALUES (MESSAGE_SEQ.NEXTVAL, ?,?,?, 0 , SYSDATE ) ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		System.out.println("sql:" + sql);

		int count = 0;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, to);
			psmt.setString(2, from);
			psmt.setString(3, content);

			count = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}

		return count > 0 ? true : false;
	}

}
