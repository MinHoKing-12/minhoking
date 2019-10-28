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
import votes.VoteDto;

public class VoteDao implements iVoteDao {

	public VoteDao() {
		DBConnection.initConnection();
	}

	private static VoteDao dao = null;

	// singleton
	public static VoteDao getInstance() {
		if (dao == null) {
			dao = new VoteDao();
		}
		return dao;
	}

	/*
	 * // 1등선수 뽑아주기 public List<VoteDto> whowinner() { String sql =
	 * " SELECT * FROM PLAYER_INFO " + " ORDER BY LIKES DESC ";
	 * System.out.println(sql);
	 * 
	 * Connection conn = null; PreparedStatement psmt = null; ResultSet rs = null;
	 * 
	 * List<VoteDto> list = new ArrayList<VoteDto>();
	 * 
	 * try {
	 * 
	 * conn = DBConnection.getConnection(); psmt = conn.prepareStatement(sql); rs =
	 * psmt.executeQuery();
	 * 
	 * if (rs.next()) {
	 * 
	 * int seq = rs.getInt(1); String name = rs.getString(2); int height =
	 * rs.getInt(3); int weight = rs.getInt(4); String position = rs.getString(5);
	 * String photoname = rs.getString(6); int likes = rs.getInt(7);
	 * 
	 * VoteDto dto = new VoteDto(seq, name, height, weight, position, photoname,
	 * likes);
	 * 
	 * list.add(dto);
	 * 
	 * }
	 * 
	 * } catch (SQLException e) {
	 * 
	 * e.printStackTrace(); } finally {
	 * 
	 * DBClose.close(conn, psmt, rs); } return list; }
	 */

	// list들어오는거(선수정보 빼주려고)
	public List<VoteDto> getVotelist() {

		String sql = " SELECT SEQ, NAME, HEIGHT, WEIGHT, POSITION, PHOTONAME, LIKES " 
		           + " FROM PLAYER_INFO ";

		System.out.println(sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		List<VoteDto> list = new ArrayList<VoteDto>();

		try {

			conn = DBConnection. getConnection();
			System.out.println("1/6 VoteList suc");

			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 VoteList suc");

			rs = psmt.executeQuery();
			System.out.println("3/6 VoteList suc");

			while (rs.next()) {

				int seq = rs.getInt(1);
				String name = rs.getString(2);
				int height = rs.getInt(3);
				int weight = rs.getInt(4);
				String position = rs.getString(5);
				String photoname = rs.getString(6);
				int likes = rs.getInt(7);

				VoteDto vote = new VoteDto(seq, name, height, weight, position, photoname, likes);

				list.add(vote);

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			DBClose.close(conn, psmt, rs); 
		}
		return list;
	}

	// select박스 랭킹순 하기위해
	public List<VoteDto> ranklist() {

		String sql = " SELECT SEQ, NAME, HEIGHT, WEIGHT, POSITION, PHOTONAME, LIKES " 
		        + " FROM PLAYER_INFO "
				+ " ORDER BY LIKES DESC ";

		System.out.println(sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		List<VoteDto> list = new ArrayList<VoteDto>();

		try {

			conn = DBConnection.getConnection();
			System.out.println("1/6 ranklist suc");

			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 ranklist suc");

			rs = psmt.executeQuery();
			System.out.println("3/6 ranklist suc");

			while (rs.next()) {

				int seq = rs.getInt(1);
				String name = rs.getString(2);
				int height = rs.getInt(3);
				int weight = rs.getInt(4);
				String position = rs.getString(5);
				String photoname = rs.getString(6);
				int likes = rs.getInt(7);

				VoteDto vote = new VoteDto(seq, name, height, weight, position, photoname, likes);

				list.add(vote);

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			DBClose.close(conn, psmt, rs);
		}
		return list;
	}

	// seq로 선수들 각각의정보 뽑아오는거
	public VoteDto whoplayer(int seq) {

		String sql = " SELECT SEQ, NAME, HEIGHT, WEIGHT, POSITION, PHOTONAME, LIKES " 
		           + " FROM PLAYER_INFO "
				   + " WHERE SEQ = ? ";

		System.out.println(sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		VoteDto vdto = null;

		try {

			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, seq);
			rs = psmt.executeQuery();

			if (rs.next()) {

				int _seq = rs.getInt(1);
				String name = rs.getString(2);
				int height = rs.getInt(3);
				int weight = rs.getInt(4);
				String position = rs.getString(5);
				String photoname = rs.getString(6);
				int likes = rs.getInt(7);

				vdto = new VoteDto(_seq, name, height, weight, position, photoname, likes);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			DBClose.close(conn, psmt, rs);
		}
		return vdto;
	}

	// 검색 리스트
	public List<VoteDto> findList(String search) {

		String sql = " SELECT SEQ, NAME, HEIGHT, WEIGHT, POSITION, PHOTONAME, LIKES " 
		           + " FROM PLAYER_INFO "
				   + " WHERE NAME LIKE ? ";

		System.out.println(sql + ":" + search);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		List<VoteDto> list = new ArrayList<VoteDto>();

		try {

			conn = DBConnection.getConnection();
			System.out.println("1/6 findList suc");

			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 findList suc");

			psmt.setString(1, "%" + search + "%");
			rs = psmt.executeQuery();
			System.out.println("3/6 findList suc");

			while (rs.next()) {

				int seq = rs.getInt(1);
				String name = rs.getString(2);
				int height = rs.getInt(3);
				int weight = rs.getInt(4);
				String position = rs.getString(5);
				String photoname = rs.getString(6);
				int likes = rs.getInt(7);

				VoteDto vote = new VoteDto(seq, name, height, weight, position, photoname, likes);

				list.add(vote);

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			DBClose.close(conn, psmt, rs);
		}
		return list;
	}

	// 투표할때 투표한 like 업뎃
	public void likecount(int seq) {

		String sql = " UPDATE PLAYER_INFO SET " 
				   + " LIKES = LIKES + 1 " 
				   + " WHERE SEQ = ? ";

		System.out.println(sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, seq);

			psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
	}

}
