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
import votes.VcommnetDto;

public class VcommentDao implements iVcomment {

	public VcommentDao() {
		DBConnection.initConnection();
	}
	
	
	private static VcommentDao dao = null;

	// singleton
	public static VcommentDao getInstance() {
		if (dao == null) {
			dao = new VcommentDao();
		}
		return dao;
	}

	// 댓글추가
	public boolean addcomment(VcommnetDto dto) {

		String sql = " INSERT INTO VOTE_COMMENT ( SEQ, ID, CONTENT, PLAYERNUM, WDATE, DEL ) "
				+ " VALUES ( SEQ_VOTE_COMMENT.NEXTVAL, ?,?,?,SYSDATE,0 ) ";

		Connection conn = null;
		PreparedStatement psmt = null;

		System.out.println("sql:" + sql);

		int count = 0;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getContent());
			psmt.setInt(3, dto.getPlayernum());

			count = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}

		return count > 0 ? true : false;
	}

	// 닉네임 이용해서 id 찾는 함수
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

			if (rs.next()) {
				id = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return id;
	}

	// 선수의 응원댓글 위한
	public VcommnetDto reply(int playernum) {

		String sql = " SELECT SEQ, M.ID, CONTENT, PLAYERNUM, DEL , WDATE, NICKNAME "
					+ " FROM VOTE_COMMENT V , MEMBER M " 
					+ " WHERE V.ID = M.ID AND PLAYERNUM = ? ";

		System.out.println(sql);

		Connection conn = null; // DB에 정보를 얻어올 때
		PreparedStatement psmt = null; // SQL구문을 넣을 때
		ResultSet rs = null; // 그것에 대한 결과값을 리턴 받을 때

		VcommnetDto comdto = null;

		try {

			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("1 선수댓글 dao");
			psmt.setInt(1, playernum);
			System.out.println("2 선수댓글 dao");
			rs = psmt.executeQuery();

			if (rs.next()) { // 쿼리문을 통해서 리턴받는

				int seq = rs.getInt(1);
				String id = rs.getString(2);
				String content = rs.getString(3);
				int _playernum = rs.getInt(4);
				int del = rs.getInt(5);
				String wdate = rs.getString(6);
				String nickname = rs.getString(7);

				comdto = new VcommnetDto(seq, id, content, _playernum, wdate, del, nickname);
			}

		} catch (SQLException e) {
			System.out.println("선수댓글 오류다");
			e.printStackTrace();
		} finally {

			DBClose.close(conn, psmt, rs);
		}
		return comdto;
	}

	// 댓글수정
	public boolean Updatereply(String content, int seq) {

		String sql = " UPDATE VOTE_COMMENT " 
		           + " SET " 
		           + " CONTENT = ?" 
		           + " WHERE SEQ = ? ";

		System.out.println(sql);

		Connection conn = null; // DB에 정보를 얻어올 때
		PreparedStatement psmt = null; // SQL구문을 넣을 때
		ResultSet rs = null; // 그것에 대한 결과값을 리턴 받을 때

		int count = 0;

		try {

			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, content);
			psmt.setInt(2, seq);

			count = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			DBClose.close(conn, psmt, rs);
		}
		return count > 0 ? true : false;
	}

	// 댓글삭제
	public boolean deletereply(int del, int seq) {

		String sql = " UPDATE VOTE_COMMENT SET " 
				   + " DEL =  ?  " 
				   + " WHERE  SEQ = ? ";

		System.out.println(sql);

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		int count = 0;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, del);
			psmt.setInt(2, seq);

			count = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}

		return count > 0 ? true : false;

	}

	// paging
	public int getAllComment(int seq) {
		String sql = " SELECT COUNT(*) " 
				   + " FROM VOTE_COMMENT" 
				   + " WHERE PLAYERNUM = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		int bbsLen = 0;

		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getAllcomment suc");

			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 getAllcomment suc");

			psmt.setInt(1, seq);

			rs = psmt.executeQuery();

			if (rs.next()) {
				bbsLen = rs.getInt(1);
			}
			System.out.println("3/6 getAllcomment suc");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return bbsLen;
	}

	// 페이징 rownum
	public List<VcommnetDto> getBbsPagingList(int playnum, int page) {
		String sql = "SELECT SEQ, ID, CONTENT, PLAYERNUM, WDATE, DEL, NICKNAME " 
	               + " FROM ";

		sql += " (SELECT ROW_NUMBER()OVER(ORDER BY WDATE DESC) AS RNUM, "
				+ " SEQ, M.ID AS ID, CONTENT, PLAYERNUM, WDATE, DEL, NICKNAME "
				+ " FROM VOTE_COMMENT V , MEMBER M WHERE V.ID = M.ID AND PLAYERNUM = ? ) ";

		sql += " WHERE RNUM >= ? AND RNUM <= ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		System.out.println("sql:" + sql);

		List<VcommnetDto> list = new ArrayList<VcommnetDto>();

		int start, end;
		start = 1 + 10 * page;
		end = 10 + 10 * page;

		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 paginglist suc");

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, playnum);
			psmt.setInt(2, start);
			psmt.setInt(3, end);
			System.out.println("2/6 paginglist suc");

			rs = psmt.executeQuery();
			System.out.println("3/6 paginglist suc");

			while (rs.next()) {
				VcommnetDto dto = new VcommnetDto(

						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6),
						rs.getString(7));

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
	
	 public boolean managerdelete(int[] seq) {
		      String sql = " DELETE "
		    		  	 + " FROM VOTE_COMMENT "
		    		  	 + " WHERE SEQ = ? ";
		      
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
		         count = psmt.executeBatch();   // 메모리에 올라와있던 쿼리구문 실행 후 실행 결과를 배열로 반환한다. (-2 : 정상종료)
		                                 // 이 부분에서 에러가 발생하면 커밋이 되지 않고 예외발생부로 이동
		         conn.commit();
		         
		         System.out.println("multidel complete");
		         
		      } catch (SQLException e) {
		         e.printStackTrace();
		         
		         try {
		            conn.rollback();   // 롤백!
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
		      
		      boolean isS = true;   // 정상적으로 종료
		      
		      for (int i = 0; i < count.length; i++) {
		         if(count[i] != -2) {   // -2 == 정상종료 : 정상종료가 안됬다면
		            isS = false;	// 안됐네
		            break; 
		         }
		      }
		      
		      return isS;
		   }
	
	  // 관리자 paging
		public int getAllvoteComment(String id) {
			String sql = " SELECT COUNT(*) " 
					   + " FROM VOTE_COMMENT" 
					   + " WHERE ID = ? ";

			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;

			int bbsLen = 0;

			try {
				conn = DBConnection.getConnection();
				System.out.println("1/6 getAllcomment suc");

				psmt = conn.prepareStatement(sql);
				System.out.println("2/6 getAllcomment suc");

				psmt.setString(1, id);

				rs = psmt.executeQuery();

				if (rs.next()) {
					bbsLen = rs.getInt(1);
				}
				System.out.println("3/6 getAllcomment suc");
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				DBClose.close(conn, psmt, rs);
			}
			return bbsLen;
		}
	

	//관리자 list페이징
	public List<VcommnetDto> votePagingList(String id, int page) {
		String sql = "SELECT SEQ, ID, CONTENT, PLAYERNUM, WDATE, DEL, NICKNAME " 
	               + " FROM ";

		sql += " (SELECT ROW_NUMBER()OVER(ORDER BY WDATE DESC) AS RNUM, "
				+ " SEQ, M.ID AS ID, CONTENT, PLAYERNUM, WDATE, DEL, NICKNAME "
				+ " FROM VOTE_COMMENT V , MEMBER M WHERE V.ID = M.ID AND M.ID = ? ) ";

		sql += " WHERE RNUM >= ? AND RNUM <= ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		System.out.println("sql:" + sql);

		List<VcommnetDto> list = new ArrayList<VcommnetDto>();

		int start, end;
		start = 1 + 10 * page;
		end = 10 + 10 * page;

		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 paginglist suc");

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setInt(2, start);
			psmt.setInt(3, end);
			System.out.println("2/6 paginglist suc");

			rs = psmt.executeQuery();
			System.out.println("3/6 paginglist suc");

			while (rs.next()) {
				VcommnetDto dto = new VcommnetDto(

						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6),
						rs.getString(7));

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

	
	// mypage용 함수
	@Override
	public int myCommentCount(String id) {
		String sql = " SELECT COUNT(*) FROM VOTE_COMMENT WHERE ID=? ";
		
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
		}
		
		return count;
	}

}
