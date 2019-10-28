<%@page import="calendar.CalendarDto"%>
<%@page import="java.util.Calendar"%>
<%@page import="member.MemberDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%!    
// 일정 쓰기
public String writecal(int year, int month, int day){
	
	String str = "";
	String image = "<img src='images/calendar/pen.png' width='10px' height='10px'>";
	str = String.format("<a href='%s&year=%d&month=%d&day=%d'>%s</a>", 
								"admincontrol?command=writecal", year, month, day, image);
	
	return str;	
}
%>


 <%!
// nvl함수
public boolean nvl(String msg){ 
	return msg == null || msg.trim().equals("")?true:false;
}


 
//일에 0붙여서 맞춰줌
public String two(String msg){
	return msg.trim().length()<2?"0"+ msg.trim():msg.trim(); 
}



// 각 날짜별로 테이블 생성 & 수정
public String makeTable(int year, int month, int day, List<CalendarDto> list){
	String str = "";
	String dates = (year + "") + two(month + "") + two(day + "");
	
	str += "<table frame=void style= table-layout: fixed>";
	str += "<col width='98'>";
	
	for(CalendarDto dto : list){
		if(dto.getSche().substring(0, 8).equals(dates)){
			str += "<img src='images/calendar/" + dto.getOcname() + "' width='30px' height='30px' align='right'>";
			str += "<tr bgcolor='transparent' align='center'>";
			str += "<td>";
			str += "<a style= text-decoration:none; href='admincontrol?command=updatecal&seq=" + dto.getSeq() + "'>";
			str += "<font style='font-size:15px; color:black;'>";
			
			str += "<strong>"+dto.getTeam()+"</strong>"					
					+ "<br><img src='images/calendar/" + dto.getImgname() + "' width='70px' height='55px'>"
					+ "<br><small><font style=color:#6d6d6d>"+dto.getStadium().substring(0,2)+"</small>" 
					+ "<br>" + dto.getSche().substring(8,10)+":"+dto.getSche().substring(10,12);
					
				//	+ "<br>"+ dto.getCancel();
			
			str += "</font>";
			str += "</a>";
			str += "</td>";
			str += "</tr>";
		}
	}
	str += "</table>";
	return str;
}
%>

<%
List<CalendarDto> list = (List<CalendarDto>)request.getAttribute("list");
for(int i = 0; i < list.size(); i++){
	System.out.println(list.get(i));
}
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>KIWOOM SUPPORTERS - 경기 일정관리</title>
	<link rel="stylesheet" href="admin/css/style.css" type="text/css" media="all" />
	<link rel="shortcut icon"  href="main/images/favicon.ico">
	<style>
	#main {
		width: 1300px;
	}
	.table tr:hover td {
		background: #fff;
	}
	</style>
</head>
<body>
<!-- Header -->
<div id="header">
	<div class="shell">
		<!-- Logo + Top Nav -->
		<div id="top">
			<h1><a href="#">Kiwoom Supporters</a></h1>
			<div id="top-navigation">
				안녕하세요! <strong>관리자님</strong>
				<span>|</span>
				<a href="./admincontrol?command=msgbox">마이페이지</a>
				<span>|</span>
				<a href="./memcontrol?command=logout">로그아웃</a>
			</div>
		</div>
		<!-- End Logo + Top Nav -->
		
		<!-- Main Nav -->
		<div id="navigation">
			<ul>
			    <li><a href="./admincontrol?command=memlist">회원 관리</a></li>
			    <li><a href="./admincontrol?command=sellbbslist&page=1">거래게시판 관리</a></li>
			    <li><a href="./admincontrol?command=freebbslist">자유게시판 관리</a></li>
			    <li><a href="./admincontrol?command=votelist">투표 관리</a></li>
			    <li><a href="./admincontrol?command=calendarlist" class="active">경기일정 관리</a><li>
			</ul>
		</div>
		<!-- End Main Nav -->
	</div>
</div>
<!-- End Header -->

<!-- Container -->
<div id="container">
	<div class="shell">
		
		<!-- Small Nav -->
		<div class="small-nav">
			<a href="#">경기일정</a>
			<span>&gt;</span>
			경기일정표
		</div>
		<!-- End Small Nav -->
		
		<!-- Main -->
		<div id="main">
			<div class="cl">&nbsp;</div>
			
			<!-- Content -->
			<div id="content">
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">경기 일정</h2>
						<div class="right">
							
						</div>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<div class="table" align="center">
					
						<%			
						Calendar cal = Calendar.getInstance();
						cal.set(Calendar.DATE, 1);
						
						String syear = request.getParameter("year");
						String smonth = request.getParameter("month");
						
						int year = cal.get(Calendar.YEAR);
						if(nvl(syear) == false){		// 파라미터가 넘어온 경우
							year = Integer.parseInt(syear);
						}
						
						int month = cal.get(Calendar.MONTH) + 1;
						if(nvl(smonth) == false){
							month = Integer.parseInt(smonth);
						}
						
						if(month < 1){
							month = 12;
							year--;
						}
						if(month > 12){
							month = 1;
							year++;
						}
						
						cal.set(year, month - 1, 1);	// 연월일 셋팅 완료
						
						// 요일
						int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
						
						// <<		year--
						String pp = String.format("<a href='%s&year=%d&month=%d'>" 
												+ "<img src='./images/calendar/btnPPrev.png' width='20px' height='20px'></a>", 
												"./admincontrol?command=calendarlist", year-1, month);
						
						// <		month--
						String p = String.format("<a href='%s&year=%d&month=%d'>" 
												+ "<img src='./images/calendar/btnPrev.png'width='10px' height='20px'></a>", 
												"./admincontrol?command=calendarlist", year, month-1);
						
						// >		month++
						String n = String.format("<a href='%s&year=%d&month=%d'>" 
												+ "<img src='./images/calendar/btnNext.png'width='10px' height='20px'></a>", 
												"./admincontrol?command=calendarlist", year, month+1);
						
						// >>		year++
						String nn = String.format("<a href='%s&year=%d&month=%d'>" 
												+ "<img src='./images/calendar/btnNNext.png'width='20px' height='20px'></a>", 
												"./admincontrol?command=calendarlist", year+1, month);
						
						%>
								
						<table style="table-layout: fixed" >
							<col width=150><col width=150><col width=150><col width=150><col width=150><col width=150><col width=150>
							<tr height="120" >
								<td colspan="7" align="center" style="padding-top: 20px;">
									<%=pp %>&nbsp;&nbsp;<%=p %>&nbsp;
							 		<font color="#bb1b39" style="font-size: 35px; font-weight: bold">
										<%=String.format("&nbsp;%d년&nbsp;%2d월&nbsp;", year, month) %>
									</font>
									<%=n %>&nbsp;&nbsp;<%=nn %>
								</td>
							</tr>
							
							<tr height="50" bordercolor="red">
								<th align="center" style="color: #bb1b39">SUN</th>
								<th align="center">MON</th>
								<th align="center">TUE</th>
								<th align="center">WED</th>
								<th align="center">THU</th>
								<th align="center">FRI</th>
								<th align="center">SAT</th>
							</tr>
							
							<tr height="120" width="100" align="left" valign="top">
							<%
							// 위쪽 빈칸
							for(int i = 1;i < dayOfWeek; i++){
								%>	
								<td style="background-color: #ffffff" >&nbsp;</td>
								<%
							}
							%>
							
							<%--날짜 --%>
							
							<%
							
							int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
							for(int i = 1;i <= lastDay; i++){	
								%>
								
								<td class="day" ><%=i %>&nbsp;&nbsp;<%=writecal(year, month, i) %>
									<%=makeTable(year, month, i, list) %>
								</td>
								
								<%
								if((i + dayOfWeek - 1) % 7 == 0 && i != lastDay){
									%>
									</tr>
									<tr height="120" width="100" align="left" valign="top" >
									<%
								}
 							}
							%>
							
							
							<%-- 밑칸 --%>
							<%
							cal.set(Calendar.DATE, lastDay);	// 마지막 날짜로 셋팅
							int weekday = cal.get(Calendar.DAY_OF_WEEK); // 마지막 날짜의 요일을 구함
							for(int i = 0;i < 7 - weekday; i++ ){
								%>
								<td style="background-color: #ffffff">&nbsp;</td>
								<%
							}
							%>
							</tr>
						</table>	
						
					</div>
					<!-- Table -->
					
				</div>
				<!-- End Box -->
				
			</div>
			<!-- End Content -->
			
			<div class="cl">&nbsp;</div>			
		</div>
		<!-- Main -->
	</div>
</div>
<!-- End Container -->

<!-- Footer -->
<div id="footer">
	<div class="shell">
		<span class="left">&copy; 2010 - CompanyName</span>
		<span class="right">
			Design by <a href="http://chocotemplates.com" target="_blank" title="The Sweetest CSS Templates WorldWide">Chocotemplates.com</a>
		</span>
	</div>
</div>
<!-- End Footer -->


<!-- Script -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">

$(function () {

});

</script>
</body>
</html>