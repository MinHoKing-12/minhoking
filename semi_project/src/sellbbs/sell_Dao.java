package sellbbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
import sellbbs.sell_Dto;

public class sell_Dao implements isell_Dao {
	
	//싱글톤
	private static sell_Dao sell_dao = new sell_Dao();
	
	private sell_Dao() {
		
	}
	
	public static sell_Dao getInstance() {
		return sell_dao;
	}

	@Override
	public List<sell_Dto> getSellList() {
		
		String sql = " SELECT SEQ, B.ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, PRICE, CATEGORY, "  
					  +	" PCATEGORY, COMPLETE, REPORT, FILENAME, M.NICKNAME "  
					  +	" FROM SELL_BBS B, MEMBER M "  
					  +	" WHERE B.ID = M.ID ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<sell_Dto> list = new ArrayList<sell_Dto>();
		
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("1/6 getSellList suc");
			
			rs = psmt.executeQuery();
			System.out.println("2/6 getSellList suc");
			
			while(rs.next()) {
				sell_Dto dto = new sell_Dto(rs.getInt(1),
											rs.getString(2),
											rs.getString(3),
											rs.getString(4),
											rs.getInt(5),
											rs.getInt(6),
											rs.getString(7),
											rs.getInt(8),
											rs.getInt(9),
											rs.getString(10),
											rs.getString(11),
											rs.getInt(12),
											rs.getInt(13),
											rs.getString(14),
											rs.getString(15));
				list.add(dto);
				System.out.println("3/6 getSellList suc");
			}
			
		} catch (SQLException e) {
			System.out.println("4/6 getSellList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}

	@Override
	public boolean Sell_Insert(String id, String category, String pcategory, String filename, String title, String content, int price) {
		String sql = " INSERT INTO SELL_BBS "
				   + " (SEQ, ID, TITLE, CONTENT, RCOUNT, ACOUNT, "
				   + " WDATE, DEL, PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME) "
				   + " VALUES(SELL_SEQ.NEXTVAL, ?, ?, ?, 0, "
				   + " 0, SYSDATE, 0, ?, ?, ?, 0, 0, ?) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int num = 0;
		System.out.println("1/6 Sell_Insert suc");
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id.trim());
			psmt.setString(2, title.trim());
			psmt.setString(3, content.trim());
			psmt.setInt(4, price);
			psmt.setString(5, category.trim());
			psmt.setString(6, pcategory.trim());
			psmt.setString(7, filename.trim());
			System.out.println("2/6 Sell_Insert suc");
			
			num = psmt.executeUpdate();
			System.out.println("3/6 Sell_Insert suc");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return num>0?true:false;
	}

	@Override
	public sell_Dto detailSell(int seq) {
		String sql = " SELECT SEQ, B.ID AS ID, TITLE, CONTENT, RCOUNT, ACOUNT, "
				   + " WDATE, PRICE, DEL, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME"
				   + " FROM SELL_BBS B, MEMBER M "
				   + " WHERE B.ID = M.ID AND SEQ =? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		sell_Dto dto = null;
		
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 detailSell suc");
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/6 detailSell suc");
			rs = psmt.executeQuery();
			System.out.println("3/6 detailSell suc");
			if(rs.next()) {
				int _seq = rs.getInt("SEQ");
				String id = rs.getString("ID");
				String title = rs.getString("TITLE");
				String content = rs.getString("content");
				int rcount = rs.getInt("RCOUNT");
				int acount = rs.getInt("ACOUNT");
				String date = rs.getString("WDATE");
				int price = rs.getInt("PRICE");
				int del = rs.getInt("DEL");
				String category = rs.getString("CATEGORY");
				String pcategory = rs.getString("PCATEGORY");
				int complete = rs.getInt("COMPLETE");
				int report = rs.getInt("REPORT");
				String filename = rs.getString("FILENAME");
				String nickname = rs.getString("NICKNAME");
				dto = new sell_Dto(_seq, 
								   id, 
								   title, 
								   content, 
								   rcount, 
								   acount, 
								   date, 
								   del, 
								   price, 
								   category, 
								   pcategory,
								   complete, 
								   report, 
								   filename,
								   nickname);
			System.out.println("4/6 detailSell suc");
		}
		} catch (SQLException e) {
			System.out.println("5/6 detailSell fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return dto;
	}

	@Override
	public void readCount(int seq) {
		String sql = " UPDATE SELL_BBS "
				+ "	SET RCOUNT = RCOUNT + 1 "
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		System.out.println("1/6 readCount suc");
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, seq);
			System.out.println("2/6 readCount suc");
			rs = psmt.executeQuery();
			System.out.println("3/6 readCount suc");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
	}

	@Override
	public boolean deleteSell(int seq) {
		
		String sql = " UPDATE SELL_BBS "
				+ " SET DEL=1 "
				+ " WHERE SEQ=?";
		System.out.println("1/6 deleteSell suc");
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int num = 0;
		System.out.println("2/6 deleteSell suc");
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, seq);
			
			num = psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("5/6 deleteSell fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return num>0?true:false;
	}

	@Override
	public boolean updateSell(sell_Dto dto) {
		
		System.out.println(dto.toString());
		
		String sql = " UPDATE SELL_BBS "
				   + " SET CONTENT =?, TITLE =?, PRICE =?, CATEGORY =?, PCATEGORY =? ";
		if(!dto.getFilename().trim().equals("")) {
			sql = sql + " ,FILENAME=? ";
		}
		sql = sql + " WHERE SEQ =? ";
		
		System.out.println("update sql : " + sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int num = 0;
		System.out.println("1/6 updateSell suc");
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 updateSell suc");
			psmt.setString(1, dto.getContent());
			psmt.setString(2, dto.getTitle());
			if(!dto.getFilename().trim().equals("")) {
				psmt.setInt(3, dto.getPrice());
				psmt.setString(4, dto.getCategory());
				psmt.setString(5, dto.getPcategory());
				psmt.setString(6, dto.getFilename());
				psmt.setInt(7, dto.getSeq());
			}else {
				psmt.setInt(3, dto.getPrice());
				psmt.setString(4, dto.getCategory());
				psmt.setString(5, dto.getPcategory());
				psmt.setInt(6, dto.getSeq());
				}
			System.out.println("3/6 updateSell suc");
			
			num = psmt.executeUpdate();
			System.out.println("4/6 updateSell suc");
		} catch (SQLException e) {
			System.out.println("5/6 updateSell fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return num>0?true:false;
	}

	@Override
	public List<sell_Dto> getFindList(int page, String text, String searchValue) {
		//BBS에서 검색 되는 NICKNAME을 찾아서 뽑아 줘야 합니다.
		String sql = " SELECT SEQ, ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, "
		  		   + " PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME "
		  		   + " FROM "  
		  		   + " (SELECT ROW_NUMBER()OVER(ORDER BY WDATE DESC) AS RNUM, " 
		  		   + " SEQ, B.ID AS ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, "
		  	  	   + " PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME"
		  		   + " FROM SELL_BBS B, MEMBER M "
		  		   + " WHERE B.ID = M.ID AND DEL = 0 ";
		
		if(searchValue.equals("닉네임")) {
			sql = sql + " AND NICKNAME =? ";
		}else if(searchValue.equals("제목")){
			sql = sql + " AND TITLE LIKE ? ";
		}
		sql = sql  + " ORDER BY WDATE DESC) "  
		  		   + " WHERE RNUM >=? AND RNUM <=? AND DEL = 0 "
		  		   + " ORDER BY SEQ DESC";
		List<sell_Dto> list = new ArrayList<sell_Dto>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("1/6 getFindList suc");
			
			if(searchValue.equals("닉네임")) {
				psmt.setString(1, text);
			}else if(searchValue.equals("제목")){
				psmt.setString(1, "%" + text + "%");
			}
			
			psmt.setInt(2, page*12-11);
			psmt.setInt(3, page*12);
			

			rs = psmt.executeQuery();
			System.out.println("2/6 getFindList suc");
			
			while(rs.next()) {
				sell_Dto dto = new sell_Dto(rs.getInt(1),
											rs.getString(2),
											rs.getString(3),
											rs.getString(4),
											rs.getInt(5),
											rs.getInt(6),
											rs.getString(7),
											rs.getInt(8),
											rs.getInt(9),
											rs.getString(10),
											rs.getString(11),
											rs.getInt(12),
											rs.getInt(13),
											rs.getString(14),
											rs.getString(15));
				list.add(dto);
				System.out.println("3/6 getFindList suc");
			}
			
		} catch (SQLException e) {
			System.out.println("4/6 getFindList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}

	@Override
	public List<sell_Dto> getCategoryList(int page, String searchValue) {
		
		String sql = " SELECT SEQ, ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, "
		  		   + " PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME "
		  		   + " FROM "  
		  		   + " (SELECT ROW_NUMBER()OVER(ORDER BY WDATE DESC) AS RNUM, " 
		  		   + " SEQ, B.ID AS ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, "
		  	  	   + " PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME "
		  		   + " FROM SELL_BBS B, MEMBER M "
		  		   + " WHERE B.ID = M.ID AND DEL = 0 ";
		if(searchValue.equals("삽니다")){
			sql = sql + " AND CATEGORY LIKE ? ";
		}else if(searchValue.equals("팝니다")){
			sql = sql + " AND CATEGORY LIKE ? ";
		}else if(searchValue.equals("의류")){
			sql = sql + " AND PCATEGORY LIKE ? ";
		}else if(searchValue.equals("장비")){
			sql = sql + " AND PCATEGORY LIKE ? ";
		}else if(searchValue.equals("사인")){
			sql = sql + " AND PCATEGORY LIKE ? ";
		}else if(searchValue.equals("티켓")){
			sql = sql + " AND PCATEGORY LIKE ? ";
		}else if(searchValue.equals("기타")){
			sql = sql + " AND PCATEGORY LIKE ? ";
		}	   
		sql = sql  + " ORDER BY WDATE DESC) "  
		  		   + " WHERE RNUM >=? AND RNUM <=? AND DEL = 0 "
		  		   + " ORDER BY SEQ DESC ";
				
		List<sell_Dto> list = new ArrayList<sell_Dto>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("1/6 getFindList suc");
			
			if(searchValue.equals("삽니다") || searchValue.equals("팝니다")) {
				psmt.setString(1, searchValue);
			} else{
				psmt.setString(1, searchValue);
			}
			
			psmt.setInt(2, page*12-11);
			psmt.setInt(3, page*12);
			

			
			rs = psmt.executeQuery();
			System.out.println("2/6 getFindList suc");
			
			while(rs.next()) {
				sell_Dto dto = new sell_Dto(rs.getInt(1),
											rs.getString(2),
											rs.getString(3),
											rs.getString(4),
											rs.getInt(5),
											rs.getInt(6),
											rs.getString(7),
											rs.getInt(8),
											rs.getInt(9),
											rs.getString(10),
											rs.getString(11),
											rs.getInt(12),
											rs.getInt(13),
											rs.getString(14),
											rs.getString(15));
				list.add(dto);
				System.out.println("3/6 getFindList suc");
			}
			
		} catch (SQLException e) {
			System.out.println("4/6 getFindList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}

	/*
	  " String sql = " SELECT SEQ, NICKNAME, CONTENT, REF, STEP, DEPTS, " +
	  " PARENT, DEL, LIKECOUNT, REPORT, WDATE " + " FROM SELL_COMMENT " +
	  " WHERE PARENT =? " + " ORDER BY REF DESC, STEP ASC ";
	 */
	@Override
	public List<sell_Dto> getSellList(int size) {
		String sql = " SELECT SEQ, ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, "
		  		   + " PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME "
		  		   + " FROM "  
		  		   + " (SELECT ROW_NUMBER()OVER(ORDER BY WDATE DESC) AS RNUM, " 
		  		   + " SEQ, B.ID AS ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, "
		  	  	   + " PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME"
		  		   + " FROM SELL_BBS B, MEMBER M "
		  		   + " WHERE B.ID = M.ID AND DEL = 0 "  
		  		   + " ORDER BY WDATE DESC) "  
		  		   + " WHERE RNUM >=? AND RNUM <=? AND DEL = 0 ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<sell_Dto> list = new ArrayList<sell_Dto>();
		
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("1/6 getSellList suc");
			psmt.setInt(1, size*12-11);
			psmt.setInt(2, size*12);
			
			rs = psmt.executeQuery();
			System.out.println("2/6 getSellList suc");
			
			while(rs.next()) {
				
				sell_Dto dto = new sell_Dto(rs.getInt(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getInt(5),
								rs.getInt(6),
								rs.getString(7),
								rs.getInt(8),
								rs.getInt(9),
								rs.getString(10),
								rs.getString(11),
								rs.getInt(12),
								rs.getInt(13),
								rs.getString(14),
								rs.getString(15));
				list.add(dto);
				System.out.println("3/6 getSellList suc");
			}
			
		} catch (SQLException e) {
			System.out.println("4/6 getSellList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}

	@Override
	public int pageCount(String selectValue, String context) {
			
			String sql = " SELECT COUNT(*) FROM SELL_BBS B, MEMBER M"
					   + " WHERE B.ID = M.ID AND DEL = 0 ";
			
			if(context == null) {
			}	
				if(selectValue.equals("닉네임")) {
					sql = sql + " AND NICKNAME =? ";
					
				}else if(selectValue.equals("제목")) {
					sql = sql + " AND TITLE LIKE ? ";	
				}
			sql = sql + " ORDER BY SEQ DESC ";
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			int count = 0;
			try {
				conn = DBConnection.getConnection();
				psmt = conn.prepareStatement(sql);
			if(context == null) {
			}
				if(selectValue.equals("닉네임")) {
					psmt.setString(1, context);
				}else if(selectValue.equals("제목")){
					psmt.setString(1, "%" + context + "%");
				}
			rs = psmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt(1);
				System.out.println("검색 갯수 구하는 거 : " + count);
			}
			} catch (SQLException e) {
				
				e.printStackTrace();
			} finally {
				DBClose.close(conn, psmt, rs);
			}
			if(count % 12 == 0){
				count = (count / 12);
			}else{
				count = (count / 12 + 1);
			}
		return count;
	}

	@Override
	public int pageCountMain() {
		String sql = " SELECT COUNT(*) FROM SELL_BBS WHERE DEL = 0 ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);		
			rs = psmt.executeQuery();
				while(rs.next()) {
					count = rs.getInt(1);
				System.out.println("갯수 구하는 거 : " + count);
		}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		if(count % 12 == 0){
			count = (count / 12);
		}else{
			count = (count / 12 + 1);
		}
	return count;
}

	@Override
	public int pageCategory(String searchValue) {
		String sql = " SELECT COUNT(*) "
				   + " FROM SELL_BBS B, MEMBER M "
				   + " WHERE B.ID = M.ID AND DEL = 0 ";
		
		if(searchValue.equals("삽니다")){
			sql = sql + " AND CATEGORY LIKE ? ";
		}else if(searchValue.equals("팝니다")){
			sql = sql + " AND CATEGORY LIKE ? ";
		}else if(searchValue.equals("의류")){
			sql = sql + " AND PCATEGORY LIKE ? ";
		}else if(searchValue.equals("장비")){
			sql = sql + " AND PCATEGORY LIKE ? ";
		}else if(searchValue.equals("사인")){
			sql = sql + " AND PCATEGORY LIKE ? ";
		}else if(searchValue.equals("티켓")){
			sql = sql + " AND PCATEGORY LIKE ? ";
		}else if(searchValue.equals("기타")){
			sql = sql + " AND PCATEGORY LIKE ? ";
		}
		sql = sql + " ORDER BY SEQ DESC ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
		
			if(searchValue.equals("삽니다") || searchValue.equals("팝니다")) {
				psmt.setString(1, searchValue);
			} else{
				psmt.setString(1, searchValue);
			}
			rs = psmt.executeQuery();
				while(rs.next()) {
					count = rs.getInt(1);
					System.out.println("검색 갯수 구하는 거 : " + count);
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBClose.close(conn, psmt, rs);
			}
				if(count % 12 == 0){
					count = (count / 12);
				}else{
					count = (count / 12 + 1);
				}
	return count;
}
	@Override
	public void likeCount(int seq) {
		String sql = " UPDATE SELL_BBS "
				+ "	SET ACOUNT = ACOUNT + 1 "
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int num = 0;
		System.out.println("1/6 readCount suc");
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, seq);
			System.out.println("2/6 readCount suc");
			num = psmt.executeUpdate();
			System.out.println("3/6 readCount suc");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
	}

	@Override
	public int getLikeCount(int seq) {
		String sql = " SELECT ACOUNT "
				   + " FROM SELL_BBS "
				   + " WHERE SEQ = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int num = 0;
		
			try {
				conn = DBConnection.getConnection();
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, seq);
				
				rs = psmt.executeQuery();
				while(rs.next()) {
					num = rs.getInt(1);
					System.out.println(num);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return num;
	}

	@Override
	public void likeDisCount(int seq) {
		String sql = " UPDATE SELL_BBS "
				+ "	SET ACOUNT = ACOUNT - 1 "
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int num = 0;
		System.out.println("1/6 likeDisCount suc");
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, seq);
			System.out.println("2/6 likeDisCount suc");
			num = psmt.executeUpdate();
			System.out.println("3/6 likeDisCount suc");
		} catch (SQLException e) {
			System.out.println("4/6 likeDisCount fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
	}

	@Override
	public boolean completeSell(int seq) {
		String sql = " UPDATE SELL_BBS "
				+ " SET COMPLETE = 1 "
				+ " WHERE SEQ=?";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int num = 0;
		System.out.println("1/6 completeSell suc");
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, seq);
			System.out.println("2/6 completeSell suc");
			num = psmt.executeUpdate();
			System.out.println("3/6 completeSell suc");
		} catch (SQLException e) {
			System.out.println("4/6 completeSell fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return num>0?true:false;
	}
	
		//관리자 모드 삭제 하기(테이블 삭제)
		public boolean multidelsellbbs(int[] seq) {
		      String sql = " DELETE "
		    		  	 + " FROM SELL_BBS "
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
		//댓글 삭제
		@Override
		public boolean multidelsellComment(int[] seq) {
		      String sql = " DELETE "
		    		     + " FROM SELL_COMMENT "
		    		     + " WHERE PARENT= ? ";
		      
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

		@Override
		public boolean multidelsellLike(int[] seq) {
			String sql = " DELETE "
	    		     + " FROM LIKE_BBS "
	    		     + " WHERE ACOUNT= ? ";
	      
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

		@Override
		public List<sell_Dto> getSellListadmin(int size) {
			String sql = " SELECT SEQ, ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, "
			  		   + " PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME "
			  		   + " FROM "  
			  		   + " (SELECT ROW_NUMBER()OVER(ORDER BY SEQ DESC) AS RNUM, " 
			  		   + " SEQ, B.ID AS ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, "
			  	  	   + " PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME"
			  		   + " FROM SELL_BBS B, MEMBER M "
			  		   + " WHERE B.ID = M.ID AND DEL = 0 "  
			  		   + " ORDER BY SEQ DESC) "  
			  		   + " WHERE RNUM >=? AND RNUM <=? AND DEL = 0 ";
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			List<sell_Dto> list = new ArrayList<sell_Dto>();
			
			
			try {
				conn = DBConnection.getConnection();
				psmt = conn.prepareStatement(sql);
				System.out.println("1/6 getSellList suc");
				psmt.setInt(1, size*12-11);
				psmt.setInt(2, size*12);
				
				rs = psmt.executeQuery();
				System.out.println("2/6 getSellList suc");
				
				while(rs.next()) {
					
					sell_Dto dto = new sell_Dto(rs.getInt(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getInt(5),
									rs.getInt(6),
									rs.getString(7),
									rs.getInt(8),
									rs.getInt(9),
									rs.getString(10),
									rs.getString(11),
									rs.getInt(12),
									rs.getInt(13),
									rs.getString(14),
									rs.getString(15));
					list.add(dto);
					System.out.println("3/6 getSellList suc");
				}
				
			} catch (SQLException e) {
				System.out.println("4/6 getSellList fail");
				e.printStackTrace();
			} finally {
				DBClose.close(conn, psmt, rs);
			}
			return list;
		}

		@Override
		public boolean adminmultidelsellComment(int[] seq) {
		      String sql = " DELETE "
		    		     + " FROM SELL_COMMENT "
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
		
		// My page 나의 거래게시글 불러오기
		@Override
		public List<sell_Dto> getMyList(String id, int page, String select, String str) {
			String sql = " SELECT SEQ, ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, "
			  		   + " PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME "
			  		   + " FROM "  
			  		   + " (SELECT ROW_NUMBER()OVER(ORDER BY WDATE DESC) AS RNUM, " 
			  		   + " SEQ, B.ID AS ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, "
			  	  	   + " PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME"
			  		   + " FROM SELL_BBS B, MEMBER M "
			  		   + " WHERE B.ID = M.ID AND DEL = 0 AND B.ID=? ";
			
			if(str != null && !str.equals("")) {
				if(select.equals("title")) {
					sql += " AND TITLE LIKE ? ";
				}else if(select.equals("content")) {
					sql += " AND CONTENT LIKE ? ";
				}
				
			}
			
			sql += " ORDER BY WDATE DESC) "  
					+ " WHERE RNUM >=? AND RNUM <=?";
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			List<sell_Dto> list = new ArrayList<sell_Dto>();
			
			
			try {
				conn = DBConnection.getConnection();
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, id);
				
				if(str != null && !str.equals("")) {
					psmt.setString(2, "%" + str + "%");
					psmt.setInt(3, page*10-9);
					psmt.setInt(4, page*10);
				}else {
					psmt.setInt(2, page*10-9);
					psmt.setInt(3, page*10);
				}
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					sell_Dto dto = new sell_Dto(rs.getInt(1),
												rs.getString(2),
												rs.getString(3),
												rs.getString(4),
												rs.getInt(5),
												rs.getInt(6),
												rs.getString(7),
												rs.getInt(8),
												rs.getInt(9),
												rs.getString(10),
												rs.getString(11),
												rs.getInt(12),
												rs.getInt(13),
												rs.getString(14),
												rs.getString(15));
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
		public int getMyListCount(String id, String select, String str) {
			String sql = " SELECT COUNT(*) FROM SELL_BBS WHERE DEL = 0 AND ID=? ";
			
			if(str != null && !str.equals("")) {
				if(select.equals("title")) {
					sql += " AND TITLE LIKE ? ";
				}else if(select.equals("content")) {
					sql += " AND CONTENT LIKE ? ";
				}
				
			}
			
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			int count = 0;
			try {
				conn = DBConnection.getConnection();
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, id);
				if(str != null && !str.equals("")) {
					psmt.setString(2, "%" + str + "%");
				}
				
				rs = psmt.executeQuery();
				while(rs.next()) {
					count = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBClose.close(conn, psmt, rs);
			}
			
			if(count % 10 == 0){
				count = (count / 10);
			}else{
				count = (count / 10 + 1);
			}
			
			return count;
		}
		
		@Override
		public int bbsCount(String id) {
			String sql = " SELECT COUNT(*) FROM SELL_BBS WHERE DEL = 0 AND ID=? ";

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
		
		@Override
		public List<sell_Dto> getMyLikeList(String id, int page) {
			String sql = " SELECT SEQ, ID, TITLE, CONTENT, RCOUNT, ACOUNT, WDATE, DEL, PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME " + 
					" FROM " + 
					"( SELECT ROW_NUMBER()OVER(ORDER BY SEQ DESC) RNUM, SEQ, B.ID, TITLE, CONTENT, RCOUNT, B.ACOUNT, WDATE, DEL, PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME, NICKNAME " + 
					" FROM MEMBER M, ( SELECT B.SEQ, B.ID, TITLE, CONTENT, RCOUNT, B.ACOUNT, WDATE, DEL, " + 
					"					PRICE, CATEGORY, PCATEGORY, COMPLETE, REPORT, FILENAME " + 
					"					FROM SELL_BBS B, (SELECT ACOUNT FROM LIKE_BBS WHERE ID=?) C " + 
					"					WHERE B.SEQ = C.ACOUNT AND B.DEL = 0 ) B " + 
					" WHERE M.ID = B.ID ) " +
					" WHERE RNUM >=? AND RNUM <=? ";
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			List<sell_Dto> list = new ArrayList<sell_Dto>();
			
			
			try {
				conn = DBConnection.getConnection();
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, id);
				psmt.setInt(2, page*10-9);
				psmt.setInt(3, page*10);
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					sell_Dto dto = new sell_Dto(rs.getInt(1),
												rs.getString(2),
												rs.getString(3),
												rs.getString(4),
												rs.getInt(5),
												rs.getInt(6),
												rs.getString(7),
												rs.getInt(8),
												rs.getInt(9),
												rs.getString(10),
												rs.getString(11),
												rs.getInt(12),
												rs.getInt(13),
												rs.getString(14),
												rs.getString(15));
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
		public int getMyLikeListCount(String id) {
			String sql = " SELECT COUNT(*) " + 
						" FROM SELL_BBS B, (SELECT ACOUNT FROM LIKE_BBS WHERE ID=?) C " +
						" WHERE B.SEQ = C.ACOUNT AND B.DEL = 0 ";
			
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
			
			if(count % 10 == 0){
				count = (count / 10);
			}else{
				count = (count / 10 + 1);
			}
			
			return count;
		}
}
