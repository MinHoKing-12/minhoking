package free_bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
//import log.LoggableStatement;

	public class free_bbsDao implements ifree_bbsDao  {
	
	private static free_bbsDao dao = new free_bbsDao();
		
		private free_bbsDao() {
			DBConnection.initConnection();
		}
		
		public static free_bbsDao getInstance() {
			return dao;
		}	
	
	
	@Override
	public List<free_bbsDto> getAllBbsList(int pageNumber, String choice, String text) {
		String sql = " SELECT SEQ, ID, NICKNAME, TITLE, CONTENT, "
					+ " WDATE, BLIKE, DEL, READCOUNT "
					+ "	FROM "; 
	
		sql += " (SELECT ROW_NUMBER()OVER(ORDER BY SEQ DESC) AS RNUM, "
				+ " SEQ, ID, NICKNAME, TITLE, CONTENT, "
				+ " WDATE, BLIKE, DEL, READCOUNT "
				+ " FROM FREE_BBS ";				
	
		// 검색항목과 검색어
		String sqlWord = "";
		if(text.equals("") || choice.equals("")) {
			sqlWord = "";
		}else {
			if(choice.equals("title")) {
				sqlWord = " WHERE DEL = 0 AND TITLE LIKE '%" + text.trim() + "%' ";
			}
			else if(choice.equals("nickname")) {
				sqlWord = " WHERE DEL = 0 AND NICKNAME LIKE '%" + text.trim() + "%' ";
			}	
			else if(choice.equals("content")) {
				sqlWord = " WHERE DEL = 0 AND CONTENT LIKE '%" + text.trim() + "%' ";
			}else if(choice.equals("id")) {
				sqlWord = " WHERE DEL = 0 AND ID LIKE '%" + text.trim() + "%' ";
			}
		}
		 
		sql += sqlWord;	
			
		sql += " ORDER BY SEQ DESC )";
		sql += " WHERE RNUM >= ? AND RNUM <= ? "; 
				
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<free_bbsDto> list = new ArrayList<>();
		
		int allcount = 0;;
		
		int start, end;
		
		try {
			conn = DBConnection.getConnection();
			//psmt = new LoggableStatement(conn, sql);	
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, (pageNumber-1) * 10);
			psmt.setInt(2, pageNumber * 10);
	
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				allcount++;
				free_bbsDto dto = new free_bbsDto(rs.getInt(1),	 		// seq
													rs.getString(2), 	// nickname
													rs.getString(3), 	// nickname
													rs.getString(4),	// title
													rs.getString(5), 	// content
													rs.getString(6),	// wdate
													rs.getInt(7), 		// del
													rs.getInt(8),		// blike
													rs.getInt(9), 		// readcount
													allcount);		
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);			
		}
	
		return list;
	}	
		
		
	public List<free_bbsDto> getBbsList(int pageNumber) {
			
			String sql = " SELECT SEQ, ID, NICKNAME, TITLE, CONTENT, "
						+ " WDATE, BLIKE, DEL, READCOUNT "
						+ " FROM (SELECT ROW_NUMBER() OVER(ORDER BY SEQ DESC) AS RNUM, "
							+ " SEQ, ID, NICKNAME, TITLE, CONTENT, WDATE, "
							+ " BLIKE, DEL, READCOUNT "
							+ " FROM FREE_BBS "
							+ " WHERE DEL = 0 "
							+ " ORDER BY WDATE DESC ) A"						
					+ " WHERE DEL = 0 AND"						// 	1 삭제(화면표시X) 
					+ " RNUM > ? " + "AND" + " RNUM <= ? ";
			
			Connection conn = null;
			PreparedStatement psmt = null;		
			
			ResultSet rs = null;		
			List<free_bbsDto> list = new ArrayList<free_bbsDto>();
			
			System.out.println(sql);
			
			try {
				conn = DBConnection.getConnection();
				psmt = conn.prepareStatement(sql);
				//psmt = new LoggableStatement(conn, sql);
				psmt.setInt(1, (pageNumber-1) * 20);
				psmt.setInt(2, pageNumber * 20);
								
				rs = psmt.executeQuery();
				//System.out.println("쿼리: "+	((LoggableStatement)psmt).getQueryString());
				
				while(rs.next()) {
					free_bbsDto dto = new free_bbsDto(	rs.getInt("SEQ"),
														rs.getString("ID"),
														rs.getString("NICKNAME"),
														rs.getString("TITLE"),
														rs.getString("CONTENT"),
														rs.getString("WDATE"),
														rs.getInt("BLIKE"),
														rs.getInt("DEL"),
														rs.getInt("READCOUNT")	);
				
					list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
		
		}
		
	public free_bbsDto getDetailBbs(int seq) {
		
		 System.out.println("free_bbsDto getDetailBbs()");
	  	
	     String sql = " SELECT SEQ, ID, NICKNAME, TITLE, CONTENT, WDATE, DEL, BLIKE, READCOUNT " 
		              + " FROM FREE_BBS"
			   		  + " WHERE SEQ = ?";	   
			     
	     Connection conn = null;         // DB에 정보를 얻어올 때
	     PreparedStatement psmt = null;   // SQL구문을 넣을 때
	     ResultSet rs = null;          // 그것에 대한 결과값을 리턴 받을 때
	     
	     free_bbsDto dto = null;
	     
	     try {
	        conn = DBConnection.getConnection();
	        psmt = conn.prepareStatement(sql);   // sql 문장을 넣는다
	        psmt.setInt(1, seq);
	        
	        rs = psmt.executeQuery();
	        
	        if(rs.next()) {
	           int _seq = rs.getInt("SEQ");
	           String id = rs.getString("ID");
	           String nickname = rs.getString("NICKNAME");
	           String title = rs.getString("TITLE");
	           String content = rs.getString("CONTENT");
	           String wdate = String.valueOf(rs.getDate("WDATE"));           
	           int del = rs.getInt("DEL");
	           int blike = rs.getInt("BLIKE");
	           int readcount = rs.getInt("READCOUNT");
	           			            
	          dto = new free_bbsDto(_seq, id, nickname, title, content, wdate, del, blike, readcount);
	         
	        }         
	        
	     } catch (SQLException e) {
	     
	        e.printStackTrace();
	     }   finally {
	        DBClose.close(conn, psmt, rs);
	     }
	     
	     return dto;
		
	}
	
	@Override
	public void readCount(int seq) {
		
	     String sql = " UPDATE FREE_BBS " + "SET READCOUNT = READCOUNT + 1" + " WHERE SEQ = ? ";
	
	      Connection conn = null;
	      PreparedStatement psmt = null;
	
	      int count = 0;
	      
	      try {
	         conn = DBConnection.getConnection();
	         psmt = conn.prepareStatement(sql);
	
	         psmt.setInt(1, seq);
	
	         count = psmt.executeUpdate();
	
	         System.out.println(sql);
	      } catch (SQLException e) {
	         
	         e.printStackTrace();
	      } finally {
	         DBClose.close(conn, psmt, null);
	      }
		
	}
	
	@Override
	public boolean addBbs(free_bbsDto dto) {
		
		 String sql = " INSERT INTO FREE_BBS(SEQ, ID, NICKNAME, TITLE, CONTENT, WDATE, DEL, READCOUNT, BLIKE) "	    		  	 
	                 + " VALUES(SEQ_FREE_BBS.NEXTVAL, ?, ?, "					// SEQ, ID, NICKNAME
							 + " ?, ?, "										// TITLE, CONTENT
			                 + " SYSDATE, 0, " 								// WDATE, DEL
							 + " 0, 0)";									// READCOUNT, BLIKE, REPORT
	      
	      System.out.println("sql:" + sql);
	
	      int count = 0;
	      
	      Connection conn = null;         // DB에 정보를 얻어올 때
	      PreparedStatement psmt = null;   // SQL구문을 넣을 때
	           	      
	      try {
	        conn = DBConnection.getConnection();
	        psmt = conn.prepareStatement(sql);   // sql 문장을 넣는다
	        
	        psmt.setString(1, dto.getId());                    
	        psmt.setString(2, dto.getNickname());
	        psmt.setString(3, dto.getTitle());		
			psmt.setString(4, dto.getContent());
			
			count = psmt.executeUpdate();
			
	      } catch (SQLException e) {
	      
	         e.printStackTrace();
	      }   finally {
	         DBClose.close(conn, psmt, null);
	      }
	      return count>0?true:false;
	}
	
	@Override
	public boolean deleteBbs(int seq) {
	  String sql = " UPDATE FREE_BBS " 
				+ " SET DEL = ? "
				+ " WHERE SEQ = ? ";                 
	  
	  System.out.println("sql:" + sql);
	  System.out.println("1/6 suc");
	  int count = 0;
	  
	  Connection conn = null;         // DB에 정보를 얻어올 때
	  PreparedStatement psmt = null;   // SQL구문을 넣을 때
	        
	  try {
	    conn = DBConnection.getConnection();
	    psmt = conn.prepareStatement(sql);   // sql 문장을 넣는다
	    
	    psmt.setInt(1, 1);	
	    psmt.setInt(2, seq);
				         	         	
		count = psmt.executeUpdate();
		System.out.println("3/6 addMember suc");
		
	  } catch (SQLException e) {
	  
	     e.printStackTrace();
	  }   finally {
	     DBClose.close(conn, psmt, null);
	  }
	  return count>0?true:false;
	}
		
	@Override
	public boolean updateBbs(free_bbsDto dto) {
		String sql = " UPDATE FREE_BBS " 
				+ " SET TITLE = ? " + "," + " CONTENT = ? "
				+ " WHERE SEQ = ?";
	  
	  System.out.println("sql:" + sql);
	  System.out.println("1/6 suc");
	  int count = 0;
	  
	  Connection conn = null;         // DB에 정보를 얻어올 때
	  PreparedStatement psmt = null;   // SQL구문을 넣을 때
	        
	  try {
	    conn = DBConnection.getConnection();
	    psmt = conn.prepareStatement(sql);   // sql 문장을 넣는다
	    
	    psmt.setString(1, dto.getTitle());             
	    psmt.setString(2, dto.getContent());		
		psmt.setInt(3, dto.getSeq());
		         	         	
		count = psmt.executeUpdate();
		System.out.println("3/6 addMember suc");
		
	  } catch (SQLException e) {
	  
	     e.printStackTrace();
	  }   finally {
	     DBClose.close(conn, psmt, null);
	  }
	  return count>0?true:false;
	}
	
	@Override
	public List<free_bbsDto> searchBbsList(String choice, String text) {
		System.out.println("dao searchBbsList");
		System.out.println("검색항목 : " + choice );
		System.out.println("검색텍스트: " + text );
		
		String sql = "";
		
		sql = " SELECT SEQ, ID, NICKNAME, TITLE, CONTENT, "
				+ "  WDATE, BLIKE, DEL, READCOUNT "
				+ " FROM FREE_BBS ";
	
		String sqlWord = "";
		if(text.equals("")) {
			sqlWord = " WHERE DEL = 0 ";
		}else {
			
			if(choice.equals("title")) {
				sqlWord = " WHERE DEL = 0 AND TITLE LIKE '%" + text.trim() + "%' ";
			}
			else if(choice.equals("nickname")) {
				sqlWord = " WHERE DEL = 0 AND NICKNAME LIKE '%" + text.trim() + "%' ";
			}	
			else if(choice.equals("content")) {
				sqlWord = " WHERE DEL = 0 AND CONTENT LIKE '%" + text.trim() + "%' ";
			}
		
		}
		 
		sql += sqlWord;		
		
		sql += " ORDER BY WDATE DESC ";
	
		//System.out.println("search sql : " + sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<free_bbsDto> list = new ArrayList<free_bbsDto>();
		
		try {
			conn = DBConnection.getConnection();
				System.out.println("1/6 getBbsList suc");
		
			psmt = conn.prepareStatement(sql);
			//psmt = new LoggableStatement(conn, sql);
			//psmt.setString(1, text.trim());	
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getBbsList suc");
				
			//System.out.println("쿼리: "+	((LoggableStatement)psmt).getQueryString());
			
			while(rs.next()) {
				free_bbsDto dto = new free_bbsDto(  rs.getInt(1),	 	// seq
													rs.getString(2), 	// nickname
													rs.getString(3), 	// nickname
													rs.getString(4),	// title
													rs.getString(5), 	// content
													rs.getString(6),	// wdate
													rs.getInt(7), 		// del
													rs.getInt(8),		// blike
													rs.getInt(9) 		// readcount
													);		
				list.add(dto);
		}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
	}

	@Override
	public List<free_bbsDto> getBbsPagingList(int pageNumber, String choice, String text) {
		String sql = " SELECT SEQ, ID, NICKNAME, TITLE, CONTENT, "
					+ "  TO_CHAR(WDATE,'yyyy-MM-dd HH24:MI')AS WDATE, BLIKE, DEL, READCOUNT "
					+ "		FROM "; 
		
			sql += " (SELECT ROW_NUMBER()OVER(ORDER BY WDATE DESC) AS RNUM, "
					+ " SEQ, ID, NICKNAME, TITLE, CONTENT, "
					+ " WDATE, BLIKE, DEL, READCOUNT "
					+ " FROM FREE_BBS ";				
		
		// 검색항목과 검색어
		String sqlWord = "";
		if(text.equals("") || choice.equals("")) {
			sqlWord = " WHERE DEL = 0 ";
		}else {
			if(choice.equals("title")) {
				sqlWord = " WHERE DEL = 0 AND TITLE LIKE '%" + text.trim() + "%' ";
			}
			else if(choice.equals("nickname")) {
				sqlWord = " WHERE DEL = 0 AND NICKNAME='" + text.trim() + "' ";
			}	
			else if(choice.equals("content")) {
				sqlWord = " WHERE DEL = 0 AND CONTENT LIKE '%" + text.trim() + "%' ";
			}
		}
		 
		sql += sqlWord;	
			
		sql += " ORDER BY WDATE DESC )";
		sql += " WHERE RNUM >= ? AND RNUM <= ? "; 
				
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<free_bbsDto> list = new ArrayList<>();
		
		int start, end;
		start = 1 + 10 * pageNumber;
		end = 10 + 10 * pageNumber;
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
		//	psmt = new LoggableStatement(conn, sql);	
			psmt.setInt(1, start);
			psmt.setInt(2, end);			

			//System.out.println("List 쿼리: "+	((LoggableStatement)psmt).getQueryString());
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				free_bbsDto dto = new free_bbsDto(rs.getInt(1),	 		// seq
													rs.getString(2), 	// nickname
													rs.getString(3), 	// nickname
													rs.getString(4),	// title
													rs.getString(5), 	// content
													rs.getString(6),	// wdate
													rs.getInt(7), 		// del
													rs.getInt(8),		// blike
													rs.getInt(9) // readcount
													);		
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);			
		}
		
		return list;
	}

	@Override
	public void likeCountPlus(int seq) {
		 String sql = " UPDATE FREE_BBS " + "SET BLIKE = BLIKE + 1" + " WHERE SEQ = ? ";
		
		  System.out.println("★★★★★★★★★★★★★★★★@FREEBBS CONTORLLER likeCountPlus(int seq) : " + seq  );
		 
	      Connection conn = null;
	      PreparedStatement psmt = null;
	
	      
	      try {
	         conn = DBConnection.getConnection();
	         psmt = conn.prepareStatement(sql);
	
	         psmt.setInt(1, seq);
	         
	         System.out.println(sql);
	         psmt.executeUpdate(); 
	        
	      } catch (SQLException e) {
	         
	         e.printStackTrace();
	      } finally {
	         DBClose.close(conn, psmt, null);
	      }
     }

	@Override
	public int showLikecount(int seq) {
		 System.out.println("@free_bbsDao showLikecount()");
	  	
	     String sql = " SELECT BLIKE " 
		              + " FROM FREE_BBS"
			   		  + " WHERE SEQ = ?";	   
			     
	     Connection conn = null;    
	     PreparedStatement psmt = null;
	     ResultSet rs = null;          
	     
	     int likecount = 0;
	     
	     try {
	        conn = DBConnection.getConnection();
	        psmt = conn.prepareStatement(sql);   // sql 문장을 넣는다
	        
	        psmt.setInt(1, seq);
	        
	        rs = psmt.executeQuery();
	        
	        if(rs.next()) {
	            likecount = rs.getInt("BLIKE");
	        }         
	        
	     } catch (SQLException e) {
	     
	        e.printStackTrace();
	     }   finally {
	        DBClose.close(conn, psmt, rs);
	     }
	     
	     return likecount;
	}

	@Override
	public void likeCountMinus(int seq) {
		 String sql = " UPDATE FREE_BBS " + "SET BLIKE = BLIKE - 1" + " WHERE SEQ = ? ";
		
	      Connection conn = null;
	      PreparedStatement psmt = null;
	
	      
	      try {
	         conn = DBConnection.getConnection();
	         psmt = conn.prepareStatement(sql);
	
	         psmt.setInt(1, seq);
	
	         psmt.executeUpdate();
	         
	         System.out.println(sql);
	      } catch (SQLException e) {
	         
	         e.printStackTrace();
	      } finally {
	         DBClose.close(conn, psmt, null);
	      }
		
		}

	@Override
	public int allPosting(String choice, String text) {
		int all = 0;   // 총 게시물 저장할 변수
        
        String sql =  " SELECT count(*) count " 
                    + " FROM FREE_BBS "
                    + " WHERE DEL = 0 ";
        
        String sqlWord = "";
		if(text.equals("") || choice.equals("")) {
			sqlWord = "";
		}else {
			if(choice.equals("title")) {
				sqlWord = " AND TITLE LIKE '%" + text.trim() + "%' ";
			}
			else if(choice.equals("nickname")) {
				sqlWord = " AND NICKNAME='" + text.trim() + "' ";
			}	
			else if(choice.equals("content")) {
				sqlWord = " AND CONTENT LIKE '%" + text.trim() + "%' ";
			}
		}
		
		sql += sqlWord;	 
        
           Connection conn = null;         // DB에 정보를 얻어올 때
           PreparedStatement psmt = null;   // SQL구문을 넣을 때
           ResultSet rs = null;          // 그것에 대한 결과값을 리턴 받을 때
           
        try {
           conn = DBConnection.getConnection();
           psmt = conn.prepareStatement(sql);
           rs = psmt.executeQuery();
           
           if(rs.next()) {
              all = rs.getInt("count");
              System.out.println(all);
           }
           
        } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
        } finally {
           DBClose.close(conn, psmt, rs);
        }
        return all;
	}

	@Override
	public int allAdminPosting(String choice, String text) {
		int all = 0;   // 총 게시물 저장할 변수
        
        String sql =  " SELECT count(*) count " 
                    + " FROM FREE_BBS ";
         
        String sqlWord = "";
		if(text.equals("") || choice.equals("")) {
			sqlWord = "";
		}else {
			if(choice.equals("title")) {
				sqlWord = " WHERE TITLE LIKE '%" + text.trim() + "%' ";
			}
			else if(choice.equals("nickname")) {
				sqlWord = " WHERE NICKNAME='" + text.trim() + "' ";
			}	
			else if(choice.equals("content")) {
				sqlWord = " WHERE CONTENT LIKE '%" + text.trim() + "%' ";
			}else if(choice.equals("id")) {
				sqlWord = " WHERE CONTENT ID '%" + text.trim() + "%' ";
			}
		}
		
		sql += sqlWord;	
        
        
           Connection conn = null;         // DB에 정보를 얻어올 때
           PreparedStatement psmt = null;   // SQL구문을 넣을 때
           ResultSet rs = null;          // 그것에 대한 결과값을 리턴 받을 때
           
        try {
           conn = DBConnection.getConnection();
           psmt = conn.prepareStatement(sql);
           rs = psmt.executeQuery();
           
           if(rs.next()) {
              all = rs.getInt("count");
              System.out.println(all);
           }
           
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
           DBClose.close(conn, psmt, rs);
        }
        return all;
	}

	@Override
	public boolean adminDeletePage(int[] page) {
		String sql = " DELETE "
	   		     + " FROM FREE_BBS "
	   		     + " WHERE SEQ= ? ";
	     
		     Connection conn = null;
		     PreparedStatement psmt = null;
		     
		     int count[] = new int[page.length];
		     
		     try {
		        conn = DBConnection.getConnection();
		        conn.setAutoCommit(false);
		        psmt = conn.prepareStatement(sql);
		        
		        for (int i = 0; i < page.length; i++) {
		           psmt.setInt(1, page[i]);
		           psmt.addBatch(); 
		        }
		        count = psmt.executeBatch();
		                                
		        conn.commit();
		        
		        System.out.println("@관리자 <4.게시판 페이지> 삭제 완료");
		        
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
		     boolean bool = true;   // 정상적으로 종료
		     for (int i = 0; i < count.length; i++) {
		        if(count[i] != -2) {   // -2 == 정상종료 : 정상종료가 안됬다면
		        	bool = false;	// 안됐네
		           break; 
		        }
		     }
		     return bool;
	}
	
	
	// mypage용!!
	@Override
	public List<free_bbsDto> getMyList(String id, int page, String select, String str) {
		String sql = " SELECT SEQ, ID, NICKNAME, TITLE, CONTENT, "
				+ " WDATE, BLIKE, DEL, READCOUNT "
				+ "	FROM "; 

		sql += " (SELECT ROW_NUMBER()OVER(ORDER BY SEQ DESC) AS RNUM, "
				+ " SEQ, ID, NICKNAME, TITLE, CONTENT, "
				+ " WDATE, BLIKE, DEL, READCOUNT "
				+ " FROM FREE_BBS "
				+ " WHERE DEL=0 AND ID=? ";
		
		if(str != null && !str.equals("")) {
			if(select.equals("title")) {
				sql += " AND TITLE LIKE ? ";
			}else if(select.equals("content")) {
				sql += " AND CONTENT LIKE ? ";
			}
		}
		
		sql += " ORDER BY SEQ DESC )";
		sql += " WHERE RNUM >= ? AND RNUM <= ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<free_bbsDto> list = new ArrayList<free_bbsDto>();
		
		
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
				free_bbsDto dto = new free_bbsDto(rs.getInt(1),	 		// seq
						rs.getString(2), 	// nickname
						rs.getString(3), 	// nickname
						rs.getString(4),	// title
						rs.getString(5), 	// content
						rs.getString(6),	// wdate
						rs.getInt(7), 		// del
						rs.getInt(8),		// blike
						rs.getInt(9));		// readcount		
				
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
		String sql = " SELECT COUNT(*) FROM FREE_BBS WHERE DEL = 0 AND ID=? ";
		
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
		int page = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			
			if(str != null && !str.equals("")) {
				psmt.setString(2, "%" + str + "%");
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
		
		if(count % 10 == 0) {
			page = count / 10;
		}else {
			page = count / 10 + 1;
		}
			
		return page;
	}

	@Override
	public int bbsCount(String id) {
		String sql = " SELECT COUNT(*) FROM FREE_BBS WHERE DEL = 0 AND ID=? ";

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
	public List<free_bbsDto> getMyLikeList(String id, int page) {
		String sql = " SELECT SEQ, ID, NICKNAME, TITLE, CONTENT, WDATE, BLIKE, DEL, READCOUNT " +
					" FROM ( " +
					" SELECT ROW_NUMBER()OVER(ORDER BY SEQ DESC) RNUM, B.SEQ, B.ID, B.NICKNAME, TITLE, CONTENT, WDATE, BLIKE, DEL, READCOUNT " + 
					" FROM FREE_BBS B, (SELECT PAGE FROM FREE_BBS_LIKE WHERE ID=?) C " + 
					" WHERE B.SEQ = C.PAGE AND B.DEL = 0 ) " +
					" WHERE RNUM >= ? AND RNUM <= ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<free_bbsDto> list = new ArrayList<free_bbsDto>();
		
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setInt(2, page*10-9);
			psmt.setInt(3, page*10);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				free_bbsDto dto = new free_bbsDto(rs.getInt(1),	 		// seq
						rs.getString(2), 	// id
						rs.getString(3), 	// nickname
						rs.getString(4),	// title
						rs.getString(5), 	// content
						rs.getString(6),	// wdate
						rs.getInt(7), 		// del
						rs.getInt(8),		// blike
						rs.getInt(9));		// readcount		
				
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
				" FROM FREE_BBS B, (SELECT PAGE FROM FREE_BBS_LIKE WHERE ID=?) C " + 
				" WHERE B.SEQ = C.PAGE AND B.DEL = 0 ";
	
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		int page = 0;
		
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
		
		if(count % 10 == 0) {
			page = count / 10;
		}else {
			page = count / 10 + 1;
		}
			
		return page;
	}

	
	}
