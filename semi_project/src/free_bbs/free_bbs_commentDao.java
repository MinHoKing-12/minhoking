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


public class free_bbs_commentDao implements ifree_bbs_commentDao{

	private static free_bbs_commentDao dao = new free_bbs_commentDao();
	
	private free_bbs_commentDao() {
	
	}
	
	public static free_bbs_commentDao getInstance() {
		return dao;
	}	
	
	@Override
	public List<free_bbs_commentDto> getAdminBbsCommentList(int pageSeq, int rPageNumber, String choice, String text) {
		  String sql ="";
			   
		   sql = " SELECT SEQ, ID, NICKNAME, CONTENT, " 
				    + " WDATE, DEL, BLIKE, PARENT, PAGE, RNUM "
				  	+ " FROM (SELECT ROW_NUMBER()OVER(ORDER BY SEQ DESC "
				  	+ " ) AS RNUM, "
				  	+ " SEQ, ID, NICKNAME, CONTENT, WDATE, DEL, BLIKE, PARENT, PAGE "
				  	+ " FROM FREE_COMMENT "; 
				  	
		  	String sqlWord = "";
			if(text.equals("") || choice.equals("")) {
				sqlWord = "";	
			}else {
				if(choice.equals("nickname")) {
					sqlWord = " WHERE NICKNAME NICKNAME '%" + text.trim() + "%' ";
				}else if(choice.equals("content")) {
					sqlWord = " WHERE CONTENT LIKE '%" + text.trim() + "%' ";
				}else if(choice.equals("id")) {
					sqlWord = " WHERE ID LIKE '%" + text.trim() + "%' ";
				}
			}
			 
			sql += sqlWord;	
				
			sql += " ORDER BY SEQ DESC ) A";
			sql += " WHERE PAGE = ? AND RNUM >= ? AND RNUM <= ? "; 		  	
		  	
		    Connection conn = null;
			PreparedStatement psmt = null;		
			
			ResultSet rs = null;		
			List<free_bbs_commentDto> list = new ArrayList<free_bbs_commentDto>();
			
			int rnum1 = (rPageNumber-1) * 10;
			int rnum2 = rPageNumber * 10;
			
			try {
				conn = DBConnection.getConnection();
				psmt = conn.prepareStatement(sql);

				psmt.setInt(1, pageSeq);
				psmt.setInt(2, rnum1);
				psmt.setInt(3, rnum2);

				rs = psmt.executeQuery();
				
				while(rs.next()) {
					free_bbs_commentDto dto = new free_bbs_commentDto( rs.getInt("SEQ"),
																		rs.getString("ID"),
																		rs.getString("NICKNAME"),
																		rs.getString("CONTENT"),
																		rs.getString("WDATE"),
																		rs.getInt("DEL"),
																		rs.getInt("BLIKE"),
																		rs.getInt("PARENT"),
																		rs.getInt("PAGE")
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
	public List<free_bbs_commentDto> getBbsCommentList(int parentSeq, int rPageNumber, int orderVal) {
	   System.out.println("★dao getBbsCommentList parentSe9q : " + parentSeq);
	   System.out.println("★dao getBbsCommentList rPageNumber : " + rPageNumber);
	   System.out.println("★dao getBbsCommentList orderVal : " + orderVal);
		
	   String sql ="";
	   
	   if(orderVal == 1) { //최신순			   
		   
		   sql = " SELECT SEQ, ID, NICKNAME, CONTENT, " 
				    + " WDATE, DEL, BLIKE, PARENT, PAGE, RNUM "
				  	+ " FROM (SELECT ROW_NUMBER()OVER(ORDER BY WDATE DESC "
				  	+ " ) AS RNUM, "
				  	+ " SEQ, ID, NICKNAME, CONTENT, WDATE, DEL, BLIKE, PARENT, PAGE "
				  	+ " FROM FREE_COMMENT " 
				  	+ " WHERE DEL = 0 AND PAGE = ? AND PARENT = 0"
				  	+ " ORDER BY WDATE DESC "
				  	+ " ) A "
				  	+ " WHERE RNUM > ? AND RNUM <= ? ";
		   
	   }else if(orderVal == 2) { //좋아요순
		   
		   sql = " SELECT SEQ, ID, NICKNAME, CONTENT,"
		   			+ " WDATE, DEL, BLIKE, PARENT, PAGE, RNUM "
				  	+ " FROM ("
				  	+ "SELECT ROWNUM AS RNUM, A.* "
				  	+ "FROM ("
				  	+ "SELECT SEQ, ID, NICKNAME, CONTENT, WDATE, DEL, BLIKE, PARENT, PAGE "
				  	+ " FROM FREE_COMMENT " 
				  	+ " WHERE DEL = 0 AND PAGE = ? AND PARENT = 0"
				  	+ " ORDER BY BLIKE DESC "
				  	+ " )A "
				  	+ ")AA"
				  	+ " WHERE RNUM > ? AND RNUM <= ? ";
	   }else if(orderVal == 3) { //과거순
		   
		   sql = " SELECT SEQ, ID, NICKNAME, CONTENT, " 
				    + " WDATE, DEL, BLIKE, PARENT, PAGE, RNUM "
				  	+ " FROM (SELECT ROW_NUMBER()OVER(ORDER BY WDATE ASC "
				  	+ " ) AS RNUM, "
				  	+ " SEQ, ID, NICKNAME, CONTENT, WDATE, DEL, BLIKE, PARENT, PAGE "
				  	+ " FROM FREE_COMMENT " 
				  	+ " WHERE DEL = 0 AND PAGE = ? AND PARENT = 0"
				  	+ " ORDER BY WDATE ASC "
				  	+ " ) A "
			  	+ " WHERE RNUM > ? AND RNUM <= ? ";
		   
	   }
	  
		Connection conn = null;
		PreparedStatement psmt = null;		
		
		ResultSet rs = null;		
		List<free_bbs_commentDto> list = new ArrayList<free_bbs_commentDto>();
		
		int rnum1 = (rPageNumber-1) * 5;
		int rnum2 = rPageNumber * 5;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);   
			//psmt = new LoggableStatement(conn, sql);
			psmt.setInt(1, parentSeq);
			psmt.setInt(2, rnum1);
			psmt.setInt(3, rnum2);
			
			System.out.println(sql);
			
			//System.out.println("쿼리: "+	((LoggableStatement)psmt).getQueryString());

			rs = psmt.executeQuery();
			
			while(rs.next()) {
				free_bbs_commentDto dto = new free_bbs_commentDto( rs.getInt("SEQ"),
																	rs.getString("ID"),
																	rs.getString("NICKNAME"),
																	rs.getString("CONTENT"),
																	rs.getString("WDATE"),
																	rs.getInt("DEL"),
																	rs.getInt("BLIKE"),
																	rs.getInt("PARENT"),
																	rs.getInt("PAGE")
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
	public boolean addReply(free_bbs_commentDto dto) {	// (NICKNAME, CONTENT, PAGE)
		
		 String sql = " INSERT INTO FREE_COMMENT(SEQ, ID, NICKNAME, CONTENT, WDATE, DEL, BLIKE, PARENT, PAGE) "	    		  	 
	                + " VALUES(SEQ_FREE_COMMENT.NEXTVAL, ?, ?, "				// SEQ, ID, NICKNAME
							 + " ?, SYSDATE, "									// CONTENT, WDATE
			                 + " 0, 0, " 										// DEL, BLIKE
							 + " 0, ?)";										// PARENT, PAGE
	     
	     System.out.println("sql:" + sql);
	
	     int count = 0;
	     
	     Connection conn = null;         // DB에 정보를 얻어올 때
	     PreparedStatement psmt = null;   // SQL구문을 넣을 때
	     //ResultSet rs = null;
	          	      
	     try {
	       conn = DBConnection.getConnection();
	       psmt = conn.prepareStatement(sql);   // sql 문장을 넣는다
	       
	       psmt.setString(1, dto.getId());
	       psmt.setString(2, dto.getNickname());                    
	       psmt.setString(3, dto.getContent());		
		   psmt.setInt(4, dto.getPage());
			
			count = psmt.executeUpdate();
			
	     } catch (SQLException e) {
	     
	        e.printStackTrace();
	     }   finally {
	        DBClose.close(conn, psmt, null);
	     }
	     return count>0?true:false;
	}

	@Override
	public free_bbs_commentDto getFreeBbsReply(int parentSeq) {
		 System.out.println("free_bbsDto getDetailBbs()");
		  	
	     String sql = " SELECT SEQ, ID, NICKNAME, CONTENT, WDATE, DEL, BLIKE, PARENT " 
		              + " FROM FREE_COMMENT"
			   		  + " WHERE PARENT = ?";	   
			     
	     Connection conn = null;         
	     PreparedStatement psmt = null;  
	     ResultSet rs = null;         
	     
	     free_bbs_commentDto dto = null;
	     
	     try {
	        conn = DBConnection.getConnection();
	        psmt = conn.prepareStatement(sql); 
	        psmt.setInt(1, parentSeq);
	        
	        rs = psmt.executeQuery();
	        
	        if(rs.next()) {
	           int seq = rs.getInt("SEQ");
	           String nickname = rs.getString("NICKNAME");
	           String content = rs.getString("CONTENT");
	           String wdate = String.valueOf(rs.getDate("WDATE"));           
	           String id = rs.getString("ID");
	           int del = rs.getInt("DEL");
	           int blike = rs.getInt("BLIKE");
	           int parent = rs.getInt("PARENT");
	           int page = rs.getInt("PAGE");
	           
	          dto = new free_bbs_commentDto(seq, id, nickname, content, wdate, del, blike, parent, page);
	         
	        }         
	        
	     } catch (SQLException e) {
	     
	        e.printStackTrace();
	     }   finally {
	        DBClose.close(conn, psmt, rs);
	     }
	     
	     return dto;		
	}

	@Override
	public boolean deleteReply(int replySeq) {
		String sql = " UPDATE FREE_COMMENT " 
				+ " SET DEL = ? "
				+ " WHERE SEQ = ? ";                 
	  
	  System.out.println("sql:" + sql);
	  System.out.println("1/6 suc");
	  int count = 0;
	  
	  Connection conn = null;         
	  PreparedStatement psmt = null;   
	        
	  try {
	    conn = DBConnection.getConnection();
	    psmt = conn.prepareStatement(sql);   
	    
	    psmt.setInt(1, 1);	
	    psmt.setInt(2, replySeq);
				         	         	
		count = psmt.executeUpdate();
		
	  } catch (SQLException e) {
	  
	     e.printStackTrace();
	  }   finally {
	     DBClose.close(conn, psmt, null);
	  }
	  return count>0?true:false;
	}

	@Override
	public boolean updateReply(int replySeq, String content) {
		String sql = " UPDATE FREE_COMMENT " 
				+ " SET CONTENT = ? " 
				+ " WHERE SEQ = ?";
	  
	  System.out.println("sql:" + sql);
	  int count = 0;
	  
	  Connection conn = null;         // DB에 정보를 얻어올 때
	  PreparedStatement psmt = null;   // SQL구문을 넣을 때
	 // ResultSet rs = null;
	        
	  try {
	    conn = DBConnection.getConnection();
	    psmt = conn.prepareStatement(sql);   // sql 문장을 넣는다
	    
	    psmt.setString(1, content);             
	    psmt.setInt(2, replySeq);		
		         	         	
		count = psmt.executeUpdate();
		
	  } catch (SQLException e) {
	  
	     e.printStackTrace();
	  }   finally {
	     DBClose.close(conn, psmt, null);
	  }
	  return count>0?true:false;
		
	}

	@Override
	public boolean addReReply(free_bbs_commentDto dto) {
		 String sql = " INSERT INTO FREE_COMMENT(SEQ, ID, NICKNAME, CONTENT, WDATE, DEL, BLIKE, PARENT, PAGE) "	    		  	 
	                + " VALUES(SEQ_FREE_COMMENT.NEXTVAL, ?, ?, "					// SEQ, ID, NICKNAME
							 + " ?, SYSDATE, "										// CONTENT, WDATE
			                 + " 0, 0, " 											// DEL, BLIKE
							 + " ?, ?)";											// PARENT, PAGE
	     	
	     System.out.println("sql:" + sql);
	
	     int count = 0;
	     
	     Connection conn = null;         // DB에 정보를 얻어올 때
	     PreparedStatement psmt = null;   // SQL구문을 넣을 때
	          	      
	     try {
	       conn = DBConnection.getConnection();
	       psmt = conn.prepareStatement(sql);   // sql 문장을 넣는다
	       
	       psmt.setString(1, dto.getId());
	       psmt.setString(2, dto.getNickname());                    
	       psmt.setString(3, dto.getContent());		
	       psmt.setInt(4, dto.getParent());
	       psmt.setInt(5, dto.getPage());
			
			count = psmt.executeUpdate();
			
	     } catch (SQLException e) {
	     
	        e.printStackTrace();
	     }   finally {
	        DBClose.close(conn, psmt, null);
	     }
	     return count>0?true:false;
	}

	@Override
	public List<free_bbs_commentDto> getReCommentList(int parent) {
		System.out.println("★@CONTROLLER 대댓글의 부모REPLY SEQ : " + parent);
		
		String sql = " SELECT SEQ, ID, NICKNAME, CONTENT, "
					+ " WDATE, DEL, BLIKE, PARENT, PAGE "
					+ " FROM FREE_COMMENT "
					+ " WHERE DEL = 0 AND PARENT = ? "			// 	DEL:1 삭제(화면표시X), PARENT:1 일반댓글 
					+ " ORDER BY WDATE DESC ";
		
		Connection conn = null;
		PreparedStatement psmt = null;		
		
		ResultSet rs = null;		
		List<free_bbs_commentDto> list = new ArrayList<free_bbs_commentDto>();
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);                                                    
			psmt.setInt(1, parent);
			
			System.out.println(sql);
			
			rs = psmt.executeQuery();
		
			while(rs.next()) {
				free_bbs_commentDto dto = new free_bbs_commentDto( rs.getInt("SEQ"),
																	rs.getString("ID"),
																	rs.getString("NICKNAME"),
																	rs.getString("CONTENT"),
																	rs.getString("WDATE"),
																	rs.getInt("DEL"),
																	rs.getInt("BLIKE"),
																	rs.getInt("PARENT"),
																	rs.getInt("PAGE")
																	);
				
			System.out.println("dao getBbsCommentList WHILE(rs.next())");			
			list.add(dto);
			System.out.println("dao getBbsCommentList END");
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
				
	}

	@Override
	public void cBlikePlus(int replySeq) {
		 String sql = " UPDATE FREE_COMMENT " 
				 	+ "SET BLIKE = BLIKE + 1" 
				 	+ " WHERE SEQ = ? ";
			
		 // System.out.println("★★★★★★★★★★★★★★★★@FREEBBS CONTORLLER likeCountPlus(int seq) : " + seq  );
		 
	      Connection conn = null;
	      PreparedStatement psmt = null;
	      
	      try {
	         conn = DBConnection.getConnection();
	         psmt = conn.prepareStatement(sql);
	
	         psmt.setInt(1, replySeq);
	         
	         System.out.println(sql);
	         psmt.executeUpdate(); 
	        
	      } catch (SQLException e) {
	         
	         e.printStackTrace();
	      } finally {
	         DBClose.close(conn, psmt, null);
	      }
	}

	@Override
	public void cBlikeMinus(int replySeq) {
		String sql = " UPDATE FREE_COMMENT " 
					+ "SET BLIKE = BLIKE - 1" 
					+ " WHERE SEQ = ? ";
		
	      Connection conn = null;
	      PreparedStatement psmt = null;
	
	      
	      try {
	         conn = DBConnection.getConnection();
	         psmt = conn.prepareStatement(sql);
	
	         psmt.setInt(1, replySeq);
	
	         psmt.executeUpdate();
	         
	         System.out.println(sql);
	      } catch (SQLException e) {
	         
	         e.printStackTrace();
	      } finally {
	         DBClose.close(conn, psmt, null);
	      }
		
	}

	@Override
	public int showrReplyLikecount(int replySeq) {
		 System.out.println("@free_bbsDao showLikecount()");
		  	
	     String sql = " SELECT BLIKE " 
		              + " FROM FREE_COMMENT"
			   		  + " WHERE SEQ = ?";	   
			     
	     Connection conn = null;    
	     PreparedStatement psmt = null;
	     ResultSet rs = null;          
	     
	     int likecount = 0;
	     
	     try {
	        conn = DBConnection.getConnection();
	        psmt = conn.prepareStatement(sql);   // sql 문장을 넣는다
	        
	        psmt.setInt(1, replySeq);
	        
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
	public List<free_bbs_commentDto> getBbsCommentLikeList(int parentSeq) {
		System.out.println("★dao getBbsCommentList parentSeq : " + parentSeq);
		

		  String sql = " SELECT SEQ, ID, NICKNAME, CONTENT, " +
				  		" WDATE, DEL, BLIKE, PARENT, PAGE, RNUM "
				  	+ " FROM (SELECT ROW_NUMBER()OVER(ORDER BY BLIKE DESC) AS RNUM, "
					  	+ " SEQ, ID, NICKNAME, CONTENT, WDATE, DEL, BLIKE, PARENT, PAGE "
					  	+ " FROM FREE_COMMENT " 
					  	+ " WHERE DEL = 0 AND PAGE = ? AND PARENT = 0 AND BLIKE != 0"
					  	+ " ORDER BY BLIKE DESC ) A "
				  	+ " WHERE RNUM > 0 AND RNUM <= 3 ";
		  
			Connection conn = null;
			PreparedStatement psmt = null;		
			
			ResultSet rs = null;		
			List<free_bbs_commentDto> list = new ArrayList<free_bbs_commentDto>();
			
			try {
				conn = DBConnection.getConnection();
				psmt = conn.prepareStatement(sql);                                                    
				psmt.setInt(1, parentSeq);
				rs = psmt.executeQuery();
				
				System.out.println(sql);
				
				while(rs.next()) {
					free_bbs_commentDto dto = new free_bbs_commentDto( rs.getInt("SEQ"),
																		rs.getString("ID"),	
																		rs.getString("NICKNAME"),
																		rs.getString("CONTENT"),
																		rs.getString("WDATE"),
																		rs.getInt("DEL"),
																		rs.getInt("BLIKE"),
																		rs.getInt("PARENT"),
																		rs.getInt("PAGE")
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
	public int getReCommentListCnt(int parent) {
		String sql = " SELECT COUNT(*) AS COUNT "
			  	+ " FROM FREE_COMMENT " 
			  	+ " WHERE DEL = 0  AND PARENT = ? ";
		
		int cnt = 0;
		Connection conn = null;
		PreparedStatement psmt = null;		
		
		ResultSet rs = null;		
	
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);                                                    
			psmt.setInt(1, parent);
			rs = psmt.executeQuery();
			
			System.out.println(sql);
		           
	        if(rs.next()) {
	           cnt = rs.getInt("COUNT");
	           System.out.println(cnt);
	        }
	       
	        } catch (Exception e) {
	           e.printStackTrace();
	        } finally {
	           DBClose.close(conn, psmt, rs);
	        }
	      return cnt;
	}

	@Override
	public int getCommentListCnt(int page) {
		String sql = " SELECT COUNT(*) AS COUNT "
			  	+ " FROM FREE_COMMENT " 
			  	+ " WHERE DEL = 0  AND PARENT = 0 AND PAGE = ? ";
		
		int cnt = 0;
		Connection conn = null;
		PreparedStatement psmt = null;		
		
		ResultSet rs = null;		
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);                                                    
			psmt.setInt(1, page);
			
			rs = psmt.executeQuery();
			
			
			System.out.println(sql);
		           
	        if(rs.next()) {
	           cnt = rs.getInt("COUNT");
	           System.out.println(cnt);
	        }
	       
	        } catch (Exception e) {
	           e.printStackTrace();
	        } finally {
	           DBClose.close(conn, psmt, rs);
	        }
	      return cnt;
	}

	@Override
	public boolean adminDeleteAllReply(int[] page) {
		String sql = " DELETE "
	   		     + " FROM FREE_COMMENT "
	   		     + " WHERE PAGE= ? ";
	     
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
		        
		        System.out.println("@관리자 <3.댓글+대댓글> 삭제 완료");
		        
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

	@Override
	public int getAdminCommentListCnt(int page, String choice, String text) {
		String sql = " SELECT COUNT(*) AS COUNT "
			  	+ " FROM FREE_COMMENT " 
			  	+ " WHERE PARENT = 0 AND PAGE = ? ";
		
		 String sqlWord = "";
			if(text.equals("") || choice.equals("")) {
				sqlWord = "";
			}else {
				if(choice.equals("id")) {
					sqlWord = " AND ID LIKE '%" + text.trim() + "%' ";
				}
				else if(choice.equals("nickname")) {
					sqlWord = " AND NICKNAME='" + text.trim() + "' ";
				}	
				else if(choice.equals("content")) {
					sqlWord = " AND CONTENT LIKE '%" + text.trim() + "%' ";
				}
			}
			
			sql += sqlWord;	 
	        
		int cnt = 0;
		Connection conn = null;
		PreparedStatement psmt = null;		
		
		ResultSet rs = null;		
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);                                                    
			psmt.setInt(1, page);
			
			rs = psmt.executeQuery();
			
			
			System.out.println(sql);
		           
	        if(rs.next()) {
	           cnt = rs.getInt("COUNT");
	           System.out.println(cnt);
	        }
	       
	        } catch (Exception e) {
	           e.printStackTrace();
	        } finally {
	           DBClose.close(conn, psmt, rs);
	        }
	      return cnt;
	}

	@Override
	public boolean adminDeleteAllReply2(int[] seq) {
		String sql = " DELETE "
	   		     + " FROM FREE_COMMENT "
	   		     + " WHERE SEQ= ? ";
	     
		     Connection conn = null;
		     PreparedStatement psmt = null;
		     
		     int count[] = new int[seq.length];
		     
		     try {
		        conn = DBConnection.getConnection();
		        conn.setAutoCommit(false);
		        psmt = conn.prepareStatement(sql);
		        
		        for (int i = 0; i < seq.length; i++) {
		           psmt.setInt(1, seq[i]);
		           psmt.addBatch(); 
		        }
		        count = psmt.executeBatch();
		                                
		        conn.commit();
		        
		        System.out.println("@관리자 <2.댓글> 삭제 완료");
		        
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

	@Override
	public boolean delteReReply(int reReplySeq) {

		  String sql = " UPDATE FREE_COMMENT " 
					+ " SET DEL = ? "
					+ " WHERE SEQ = ? ";                 
		  
		  System.out.println("sql:" + sql);
		  System.out.println("1/6 suc");
		  int count = 0;
		  
		  Connection conn = null;         
		  PreparedStatement psmt = null;   
		        
		  try {
		    conn = DBConnection.getConnection();
		    psmt = conn.prepareStatement(sql);   
		    
		    psmt.setInt(1, 1);	
		    psmt.setInt(2, reReplySeq);
					         	         	
			count = psmt.executeUpdate();
			
		  } catch (SQLException e) {
		  
		     e.printStackTrace();
		  }   finally {
		     DBClose.close(conn, psmt, null);
		  }
		  return count>0?true:false;
	}



	// MYPAGE용 METHOD
	public int myCommentCount(String id) {
		String sql = " SELECT COUNT(*) FROM FREE_COMMENT WHERE ID=? ";
		
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
		

