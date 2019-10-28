package bit.com.a.service;

import java.util.List;

import bit.com.a.model.CalendarParam;
import bit.com.a.model.CalendarDto;

public interface BitCalendarService {
	
	public List<CalendarDto> getCalendatList(CalendarDto fcal) throws Exception;
	
	public boolean writeCalendar(CalendarDto cal) throws Exception;
	
	public CalendarDto getDay(CalendarDto fcal) throws Exception;

	public boolean DayDelete(int seq) throws Exception;
	
	public boolean DayUpdate(CalendarDto dto) throws Exception;
	
	public List<CalendarDto> getcalendarDay(CalendarDto fcal) throws Exception;
}
