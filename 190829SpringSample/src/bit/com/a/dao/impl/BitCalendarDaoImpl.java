package bit.com.a.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bit.com.a.dao.BitCalendarDao;
import bit.com.a.model.CalendarParam;
import bit.com.a.model.CalendarDto;

@Repository
public class BitCalendarDaoImpl implements BitCalendarDao {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	private String ns = "Calendar.";

	@Override
	public List<CalendarDto> getCalendatList(CalendarDto fcal) throws Exception {
		List<CalendarDto> callist = sqlSession.selectList(ns+"getCalendarList", fcal);
		return callist;
	}

	@Override
	public boolean writeCalendar(CalendarDto cal) throws Exception {
		int n = sqlSession.insert(ns + "writeCalendar", cal); 
		return n>0?true:false;
	}

	@Override
	public CalendarDto getDay(CalendarDto fcal) throws Exception {
		CalendarDto dto = sqlSession.selectOne(ns + "getDay", fcal);
		return dto;
	}

	@Override
	public boolean DayDelete(int seq) throws Exception {
		
		int n = sqlSession.delete(ns + "DayDelete", seq);
		
		return n>0?true:false;
	}

	@Override
	public boolean DayUpdate(CalendarDto dto) throws Exception {

		int n = sqlSession.update(ns + "DayUpdate", dto);
		
		return n>0?true:false;
	}

	@Override
	public List<CalendarDto> getcalendarDay(CalendarDto fcal) throws Exception {
		
		List<CalendarDto> callist = sqlSession.selectList(ns+"getcalendarDay", fcal);
		return callist;
	}
	

}
