<%@page import="bit.com.a.model.myCal"%>
<%@page import="bit.com.a.model.CalendarDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%request.setCharacterEncoding("utf-8") ;%>

<style>
table {
    border-collapse: collapse;    
}

table, td, th {
    border: 1px solid black;
    font-weight: bold;
    font-size: 20px;
}
.sunday{
color: red; 
text-align: left;
vertical-align: top;
background-color: #ccccff;
}
.satday{
color: blue; 
text-align: left;
vertical-align: top;
background-color: #ccccff;
}
.otherday{
color: black; 
text-align: left;
vertical-align: top;
}
.days2{
font-size:20px;
color: #4D6BB3; 
text-align: center;
vertical-align: middle;
}
.days3{
font-size:20px;
color: #4D6BB3; 
text-align: center;
vertical-align: middle;
background-color:#4D6BB3; color:#FFFFFF; line-height:1.7em; font-weight:normal;
}

.innerTable {
    border: 0px ;
}
</style>



<%! // 메소드 선언
public String callist(int year,int month, int day){	
	String s="";
	s+=String.format("<a href='%s?year=%d&month=%d&day=%d'>", 
			"calendarDay.do",year,month,day);
	if(day < 10)s+="&nbsp;";
	s+=String.format("%d",day); //2자리
	s+="</a>";
	return s;
}//
// pen이미지를 선택하면 일정을 작성할수 있다.
public String showPen(int year,int month, int day){	
	String s="";
	String url="calwrite.do";
	String image="<img src='image/pen.gif'/>";
	s=String.format("<a href='%s?year=%d&month=%d&day=%d'>%s</a>",
			url,year,month,day,image);
	return s;
}
//1자라면 0을 붙여 두자로 만들기 1->01
public String two(String msg){
	return msg.trim().length()<2?"0"+msg:msg.trim();
}//
//15자 이상되면 ... 를 이용하여 줄임표시
public String dot3(String msg){
	String s="";
	if(msg.length()>=15){
		s=msg.substring(0,15);
		s+="...";
	}else{
		s=msg.trim();
	}
	return s;
}
public String makeTable(int year,int month, int day,
		List<CalendarDto> lcdtos){
	String s="";
	String dates=(year + "") + two(month + "") + two(day + "");//년월일 8글자 만드는거
		
	s="<table class='innerTable'>";
	s+="<col width='100px'/>";
	for(CalendarDto lcd:lcdtos){ //향상된 for
		if(lcd.getRdate().substring(0,8).equals(dates)){
			s+="<tr bgcolor='#4D6BB3'>";
			s+="<td>";			
			s+="<a href='caldetail.do?seq="+lcd.getSeq()+"'>";
			s+="<font style='font-size:8px;color:#090000'>"; //글씨작게해서15자 들어가게끔
			s+=dot3(lcd.getTitle());
			s+="</font>";
			s+="</a>";
			s+="</td>";
			s+="</tr>";
		}
	}
	s+="</table>";
	return s;
}
%>

<%
List<CalendarDto> list = new ArrayList<CalendarDto>();
Object oflist = request.getAttribute("flist");
if(oflist != null){
	list = (List<CalendarDto>)oflist;
}

myCal jcal = (myCal)request.getAttribute("jcal");

int dayOfWeek = jcal.getDayOfWeek();	// 1 ~ 7
int lastDayOfMonth = jcal.getLastDay();

int year = jcal.getYear();
int month = jcal.getMonth();

// << 연도를 -1
String pp = String.format("<a href='%s?year=%d&month=%d'><img src='image/left.gif'/></a>", 
							"calendar.do", year-1, month);

// < 월을 -1
String p = String.format("<a href='%s?year=%d&month=%d'><img src='image/prec.gif'/></a>", 
							"calendar.do", year, month-1);

// > 월을 +1
String n = String.format("<a href='%s?year=%d&month=%d'><img src='image/next.gif'/></a>", 
							"calendar.do", year, month+1);

// >> 연도를 +1
String nn = String.format("<a href='%s?year=%d&month=%d'><img src='image/last.gif'/></a>", 
							"calendar.do", year+1, month);

// 월별 일정
String url = String.format("%s?year=%d&month=%d", 
							"calendarMonth.do", year, month);
%>

<div style="text-align: left;">
	<a href="<%=url %>">
		<%=year+"년 " + month+"월" %>
	</a>
</div>

<div class="box_border" style="margin-top: 5px; margin-bottom: 10px;">
<table style="width: 85%">
<col width="100px">
<col width="100px">
<col width="100px">
<col width="100px">
<col width="100px">
<col width="100px">
<col width="100px">

<thead>
<tr height="100px">
	<td class="days2" colspan="7">
		<%=pp %><%=p %>
		<font color="red" style="font-size: 24">
			<%=String.format("%d년&nbsp;&nbsp;%d월", year, month) %>		
		</font>&nbsp;
		<%=n %><%=nn %>
	</td>
</tr>

<tr height="100px">
	<th class="days3">일</th>
	<th class="days3">월</th>
	<th class="days3">화</th>
	<th class="days3">수</th>
	<th class="days3">목</th>
	<th class="days3">금</th>
	<th class="days3">토</th>
</tr>

</thead>

<tr height="100px">
<%
for(int i = 1;i < dayOfWeek; i++){
	out.println("<td>&nbsp;</td>");
}

for(int i = 1;i <= lastDayOfMonth; i++){
	
	String hhh = String.format(
					"<a href='%s?year=%d&month=%d&day=%d'>" +
						"<img src='image/pen.gif'/>" +
					"</a>",
							"calwrite.do", year, month, i);
	
	if((i + dayOfWeek - 1) % 7 == 0){	// 토요일
		%>
		<td class="satday"><%=callist(year, month, i) %>&nbsp;<%=showPen(year, month, i) %>
			<%=makeTable(year, month, i, list) %>
		</td>
		<%
	}
	else if((i + dayOfWeek - 1) % 7 == 1){	// 일요일
		%>
		<td class="sunday"><%=callist(year, month, i) %>&nbsp;<%=showPen(year, month, i) %>
			<%=makeTable(year, month, i, list) %>
		</td>
		<%
	}
	else{
		%>
		<td class="otherday"><%=callist(year, month, i) %>&nbsp;<%=showPen(year, month, i) %>
			<%=makeTable(year, month, i, list) %>
		</td>
		<%
	}	
	
	if((i + dayOfWeek - 1) % 7 == 0 && i != lastDayOfMonth){
		%>
		</tr><tr height="100px">
		<%
	}
}

for(int i = 0;i < (7 - (dayOfWeek + lastDayOfMonth - 1)%7)%7; i++){
	out.println("<td>&nbsp;</td>");
}

%>

</tr>


</table>

</div>









