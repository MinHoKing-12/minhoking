package calendar;

import java.util.List;

import calendar.CalendarDto;

public interface iCalendar {

	public List<CalendarDto> getDayList();
	
	public boolean addCalendar(CalendarDto cal);
	
	public CalendarDto getDay(int seq);
	
	public boolean deleteCalendar(int seq);
	
	public boolean updateCalendar(CalendarDto dto);
	
}
