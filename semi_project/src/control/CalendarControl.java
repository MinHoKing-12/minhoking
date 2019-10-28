package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import calendar.CalendarDao;
import calendar.CalendarDto;
import calendar.iCalendar;
import member.MemberDao;
import member.MemberDto;
import member.iMember;
import util.utilMethod;

public class CalendarControl extends HttpServlet{

   
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      req.setCharacterEncoding("utf-8");
       
      String command = req.getParameter("command");
      iMember memdao = MemberDao.getInstance();
      iCalendar caldao = CalendarDao.getInstance();

      //calendar main
      if(command.equals("calendar")) {
    	 List<CalendarDto> list = caldao.getDayList();
    	 
    	 // parameter 짐풀기
    	 String year = req.getParameter("year");
    	 String month = req.getParameter("month");
    	 
    	 // attribute 짐싸기
    	 req.setAttribute("list", list);
    	 req.setAttribute("year", year);
    	 req.setAttribute("month", month);
    	 
    	
    	 // 짐들고 forward방식으로 calendar.jsp로 이동!!
         req.getRequestDispatcher("./calendar/calendar.jsp").forward(req, resp);

      //calendar write
      }
   }
   
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	  
      
     
   }

}