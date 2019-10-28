package bit.com.a.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bit.com.a.dao.BitCalendarDao;
import bit.com.a.model.CalendarParam;
import bit.com.a.model.CalendarDto;
import bit.com.a.service.BitCalendarService;

@Service
public class BitCalendarServiceImpl implements BitCalendarService {

	@Autowired
	BitCalendarDao calendarDao;

	@Override
	public List<CalendarDto> getCalendatList(CalendarDto fcal) throws Exception {	
		return calendarDao.getCalendatList(fcal);		
	}

	@Override
	public boolean writeCalendar(CalendarDto cal) throws Exception {		
		return calendarDao.writeCalendar(cal);		
	}

	@Override
	public CalendarDto getDay(CalendarDto fcal) throws Exception {		
		return calendarDao.getDay(fcal);		
	}

	@Override
	public boolean DayDelete(int seq) throws Exception {
		return calendarDao.DayDelete(seq);
	}

	@Override
	public boolean DayUpdate(CalendarDto dto) throws Exception {
		return calendarDao.DayUpdate(dto);
	}

	@Override
	public List<CalendarDto> getcalendarDay(CalendarDto fcal) throws Exception {
		return calendarDao.getcalendarDay(fcal);
	} 
}
