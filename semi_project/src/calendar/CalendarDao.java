package calendar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
import calendar.CalendarDto;

public class CalendarDao implements iCalendar {

	private static CalendarDao dao = new CalendarDao();
	
	private CalendarDao() {
		DBConnection.initConnection();
	}
	
	public static CalendarDao getInstance() {
		return dao;
	}
	
	public List<CalendarDto> getCalendarList(String id, String yyyyMM) {
		
		String sql = " SELECT SEQ, ID, TEAM, STADIUM, SCHE, WDATE, OUTCOME , MEMO " 
					+ " FROM (SELECT ROW_NUMBER() OVER(PARTITION BY SUBSTR(SCHE, 1, 8) ORDER BY SCHE ASC) RNUM, "
					+ " 		SEQ, ID, TEAM, STADIUM, SCHE, WDATE, OUTCOME, MEMO "
					+ " 		FROM CALENDAR "
					+ " 		WHERE ID=? AND SUBSTR(SCHE, 1, 6)=? ) "		
					+ " WHERE RNUM BETWEEN 1 AND 5 ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<CalendarDto> list = new ArrayList<CalendarDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getCalendarList suc");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, yyyyMM.trim());
			System.out.println("2/6 getCalendarList suc");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getCalendarList suc");
			
			while(rs.next()) {
				int i = 1;
				CalendarDto dto = new CalendarDto(  rs.getInt(i++), 
													rs.getString(i++), 
													rs.getString(i++), 
													rs.getString(i++), 
													rs.getString(i++), 
													rs.getString(i++),
													rs.getString(i++),
													rs.getString(i++),
													rs.getString(i++));				
				list.add(dto);
			}		
			System.out.println("4/6 getCalendarList suc");
			
		} catch (Exception e) {
			System.out.println("getCalendarList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);			
		}
		
		return list;
	}
	
	
	@Override
	public CalendarDto getDay(int seq) {

		  String sql =
		  " SELECT SEQ, TEAM, STADIUM, SCHE, WDATE, OUTCOME, MEMO " +
		  " FROM CALENDAR " + " WHERE SEQ=? ";
				
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		CalendarDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("2/6 S getDay");	
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("3/6 S getDay");	
			
			rs = psmt.executeQuery();
			System.out.println("4/6 S getDay");	
			
			while(rs.next()){
				dto = new CalendarDto(); 
				dto.setSeq(rs.getInt(1));
				dto.setTeam(rs.getString(2));
				dto.setStadium(rs.getString(3));
				dto.setSche(rs.getString(4));
				dto.setWdate(rs.getString(5));
				dto.setOutcome(rs.getString(6));	
				dto.setMemo(rs.getString(7));
			
			}	
			System.out.println("5/6 S getDay");
						
		} catch (Exception e) {			
			e.printStackTrace();
		} finally{
			DBClose.close(conn, psmt, rs);	
			System.out.println("6/6 S getDay");			
		}		
		
		return dto;
	}
	
	@Override
	public List<CalendarDto> getDayList() {
		
		String sql = " SELECT SEQ, TEAM, CALENDAR.STADIUM, SCHE, WDATE, CALENDAR.OUTCOME, MEMO, TEAM.IMGNAME, RESULT.OCNAME " + 
						" FROM CALENDAR LEFT JOIN TEAM " + 
						" ON CALENDAR.TEAM = TEAM.NAME " + 
						" LEFT JOIN RESULT " + 
						" ON CALENDAR.OUTCOME = RESULT.OUTCOME " + 
						" ORDER BY SCHE ASC ";

		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<CalendarDto> list = new ArrayList<CalendarDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 S getDayList");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 S getDayList");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 S getDayList");
			
			while(rs.next()) {
				int i = 1;
				CalendarDto dto = new CalendarDto();
				dto.setSeq(rs.getInt(i++));
				dto.setTeam(rs.getString(i++));
				dto.setStadium(rs.getString(i++));
				dto.setSche(rs.getString(i++));
				dto.setWdate(rs.getString(i++));
				dto.setOutcome(rs.getString(i++));
				dto.setMemo(rs.getString(i++));
				dto.setImgname(rs.getString(i++));
				dto.setOcname(rs.getString(i++));

				list.add(dto);
			}	
			System.out.println("4/6 S getDayList");
			
		} catch (Exception e) {
			System.out.println("F getDayList");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);			
		}
		
		return list;
	}
	
	
	@Override
	public boolean addCalendar(CalendarDto cal) {
		String sql = " INSERT INTO CALENDAR(SEQ, TEAM, STADIUM, SCHE, WDATE, OUTCOME , MEMO ) "
					+ " VALUES(SEQ_CAL.NEXTVAL, ?, ?, ?, SYSDATE, ? ,? ) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 addCalendar suc");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, cal.getTeam());
			psmt.setString(2, cal.getStadium());
			psmt.setString(3, cal.getSche());
			psmt.setString(4, cal.getOutcome());
			psmt.setString(5, cal.getMemo());
			System.out.println("2/6 addCalendar suc");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 addCalendar suc");
			
			
		} catch (Exception e) {
			System.out.println("addCalendar fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);			
		}
		
		return count>0?true:false;
	}
	
	
	
	@Override
	public boolean deleteCalendar(int seq) {
		
		String sql = " DELETE FROM CALENDAR"
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("2/6 S deleteCalendar");	
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("3/6 S deleteCalendar");	
			
			count = psmt.executeUpdate();
			System.out.println("4/6 S deleteCalendar");				
			
		} catch (Exception e) {			
			e.printStackTrace();
		} finally{
			DBClose.close(conn, psmt, null);			
			System.out.println("5/6 S deleteCalendar");	
		}
		
		return count>0?true:false;
	}
	
	
	@Override
	public boolean updateCalendar(CalendarDto dto) {
		
		String sql = " UPDATE CALENDAR SET "
				+ " TEAM=?, STADIUM=?, SCHE=?, WDATE=SYSDATE, OUTCOME=?, MEMO=? "
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("2/6 S updateCalendar");	
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getTeam());
			psmt.setString(2, dto.getStadium());
			psmt.setString(3, dto.getSche());
			psmt.setString(4, dto.getOutcome());
			psmt.setString(5, dto.getMemo());
			psmt.setInt(6, dto.getSeq());
			System.out.println("3/6 S updateCalendar");	
			
			count = psmt.executeUpdate();
			System.out.println("4/6 S updateCalendar");	
			
		} catch (Exception e) {			
			e.printStackTrace();
		} finally{
			DBClose.close(conn, psmt, null);	
			System.out.println("5/6 S updateCalendar");
		}
		
		return count>0?true:false;
	}
	
	
}










