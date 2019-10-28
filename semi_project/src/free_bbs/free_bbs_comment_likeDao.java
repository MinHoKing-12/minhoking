package free_bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBClose;
import db.DBConnection;

public class free_bbs_comment_likeDao implements ifree_bbs_comment_likeDao {

	private static free_bbs_comment_likeDao dao = new free_bbs_comment_likeDao();
	
	private free_bbs_comment_likeDao() {
	
	}
	
	public static free_bbs_comment_likeDao getInstance() {
		return dao;
	}

	@Override
	public boolean addReplyLike(int replySeq, String id) {
		
		String sql = " INSERT INTO FREE_BBS_COMMENT_LIKE(SEQ, REPLYSEQ, ID) "	    		  	 
	                + " VALUES(SEQ_FREE_BBS_COMMENT_LIKE.NEXTVAL, ?, ?) ";		
		
	     System.out.println("sql:" + sql);
	
	     int count = 0;
	     
	     Connection conn = null;        
	     PreparedStatement psmt = null;  
	          	      
	     try {
	       conn = DBConnection.getConnection();
	       psmt = conn.prepareStatement(sql);   
	       
	       psmt.setInt(1, replySeq);                    
	       psmt.setString(2, id);		
			
			count = psmt.executeUpdate();
			
	     } catch (SQLException e) {
	     
	        e.printStackTrace();
	     }   finally {
	        DBClose.close(conn, psmt, null);
	     }
	     return count>0?true:false;
	}

	@Override
	public boolean cancelReplyLike(int replySeq, String id) {
		String sql = " DELETE FROM FREE_BBS_COMMENT_LIKE "	    		  	 
            	+ " WHERE REPLYSEQ = ? AND ID = ? ";		
	
	     System.out.println("sql:" + sql);
	
	     int count = 0;
	     
	     Connection conn = null;        
	     PreparedStatement psmt = null;  
	          	      
	     try {
	       conn = DBConnection.getConnection();
	       psmt = conn.prepareStatement(sql);   
	       
	       psmt.setInt(1, replySeq);                    
	       psmt.setString(2, id);		
			
		   count = psmt.executeUpdate();
			
	     } catch (SQLException e) {
	     
	        e.printStackTrace();
	     }   finally {
	        DBClose.close(conn, psmt, null);
	     }
	     return count>0?true:false;
	}

	@Override
	public boolean checkReplyLike(int replySeq, String id) {
		 
		if(id == "") {
			return false;
		}else {
			 String sql = " SELECT ID " 
		              + " FROM FREE_BBS_COMMENT_LIKE "
			   		  + " WHERE REPLYSEQ = ? AND ID = ?";	   
			     
	     Connection conn = null;         // DB에 정보를 얻어올 때
	     PreparedStatement psmt = null;   // SQL구문을 넣을 때
	     ResultSet rs = null;          // 그것에 대한 결과값을 리턴 받을 때
	     
	     int count = 0;
	     
	     free_bbsDto dto = null;
	     
	     try {
	        conn = DBConnection.getConnection();
	        psmt = conn.prepareStatement(sql);   // sql 문장을 넣는다
	        psmt.setInt(1, replySeq);
	        psmt.setString(2, id);
	        
	        count = psmt.executeUpdate();
	        rs = psmt.executeQuery();
	        
	        if(rs.next()) {
	           String _id = rs.getString("ID");
	        }  
	        
	     } catch (SQLException e) {
	    	 e.printStackTrace();
	     } finally {
	    	 DBClose.close(conn, psmt, rs);
	     }
	     
	     return count>0?true:false;
			
		}
	}

	@Override
	public boolean adminDeleteReplyLike(int[] page) {
		
			String sql = " DELETE FROM FREE_BBS_COMMENT_LIKE "
						+ " WHERE SEQ IN "
								+ " (SELECT SEQ  "
								+ " FROM FREE_COMMENT "
								+ " WHERE PAGE = ? )";
	     
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
		        
		        System.out.println("@관리자 <1.댓글좋아요> 삭제 완료");
		        
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
	public boolean adminDeleteReplyLike2(int[] replySeq) {
		
		String sql = " DELETE FROM FREE_BBS_COMMENT_LIKE "
					+ " WHERE REPLYSEQ = ? ";
						
 
	     Connection conn = null;
	     PreparedStatement psmt = null;
	     
	     int count[] = new int[replySeq.length];
	     
	     try {
	        conn = DBConnection.getConnection();
	        conn.setAutoCommit(false);
	        psmt = conn.prepareStatement(sql);
	        
	        for (int i = 0; i < replySeq.length; i++) {
	           psmt.setInt(1, replySeq[i]);
	           psmt.addBatch(); 
	        }
	        count = psmt.executeBatch();  
	                            
	        conn.commit();
	        
	        System.out.println("@관리자 <1.댓글좋아요> 삭제 완료");
	        
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

}
