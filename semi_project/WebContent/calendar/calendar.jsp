<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="calendar.CalendarDao"%>
<%@page import="calendar.iCalendar"%>
<%@page import="calendar.CalendarDto"%>
<%@page import="java.util.Calendar"%>
<%@page import="member.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
			str += "<font style='font-size:15px; color:black;'>";
			
			str += "<strong>"+dto.getTeam()+"</strong>"					
					+ "<br><img src='images/calendar/" + dto.getImgname() + "' width='70px' height='55px'>"
					+ "<br><small><font style=color:#6d6d6d>"+dto.getStadium().substring(0,2)+"</small>" 
					+ "<br>" + dto.getSche().substring(8,10)+":"+dto.getSche().substring(10,12)
					+ "<br>" + dto.getMemo();
			
			str += "</font>";
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

<!DOCTYPE HTML>
<!--
	Arcana by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
		<title>Calendar</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="./main/css/main.css" />
			<link rel="shortcut icon"  href="main/images/favicon.ico">
	</head>
	
	<body class="is-preload">
		<div id="page-wrapper"> 

		<!-- Header -->
		<div id="header">
		
		<!-- My Menu Bar -->
			<%
			if(session.getAttribute("login") == null) {
			%>
				<div id="mymenu" style="font-size: 12px;" align="right">
					<a href="./memcontrol?command=login">로그인</a>|<a href="./login/account.jsp">회원가입</a>
				</div>
			<%
			}else {
				MemberDto user = (MemberDto)session.getAttribute("login");
				%>
				<div id="mymenu" style="font-size: 12px;" align="right">
					<b><%=user.getNickname() %></b>님 환영합니다!   <a href="./mypagecontrol?command=mymsgbox">마이페이지</a>|<a href="./memcontrol?command=logout">로그아웃</a>
				</div>
				<%
			}
			%>
			<!-- Logo -->
				<a href="./memcontrol?command=main" id="logo"><img src="./main/images/ks.png" style="width: 400px" ></img></a><br><br>

			<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a href="./memcontrol?command=main">Home</a></li>
						<li class="current"><a href="./calendarcontrol?command=calendar">경기 일정</a></li>
						<li><a href="./sellcontrol?command=list&page=1">거래 게시판</a></li>
						<li><a href="./freeControl?command=list">자유 게시판</a></li>
						<li><a href="./VoteControl?command=start">투표 게시판</a></li>
						<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank">경기 하이라이트</a></li>
					</ul>
				</nav>
			</div>

			<!-- Main -->
			<img  style="width:100%; height: 100%;" alt="bb" src="./images/calendar/calban.png"><br><br><br>
				<section class="calendar">
					<div class="container" style="width: 55%; height: 55%; line-height: 80%" >
						<div id="content">

									
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
															"./calendarcontrol?command=calendar", year-1, month);
									
									// <		month--
									String p = String.format("<a href='%s&year=%d&month=%d'>" 
															+ "<img src='./images/calendar/btnPrev.png'width='10px' height='20px'></a>", 
															"./calendarcontrol?command=calendar", year, month-1);
									
									// >		month++
									String n = String.format("<a href='%s&year=%d&month=%d'>" 
															+ "<img src='./images/calendar/btnNext.png'width='10px' height='20px'></a>", 
															"./calendarcontrol?command=calendar", year, month+1);
									
									// >>		year++
									String nn = String.format("<a href='%s&year=%d&month=%d'>" 
															+ "<img src='./images/calendar/btnNNext.png'width='20px' height='20px'></a>", 
															"./calendarcontrol?command=calendar", year+1, month);
									
									MemberDto user = (MemberDto)session.getAttribute("login");
									
									%>
									
									<table style="table-layout: fixed" >
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
										
										<td class="day" ><%=i %>
											<%=makeTable(year, month, i, list) %>
										</td>
										
										<%
										if((i + dayOfWeek - 1) % 7 == 0 && i != lastDay){
											%>
											</tr><tr height="120" width="100" align="left" valign="top" >
											
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
						</div>
					</section>
					
			<!-- Footer -->
				<div id="footer" >
					<div>
						<div align="center" style="margin-bottom: 100px">
							<a href="http://ticket.interpark.com/Contents/Sports/GoodsInfo?SportsCode=07001&TeamCode=PB003">
							<img src="./images/calendar/ticket.png"></a>
						</div>
					</div>

					<!-- Icons -->
						<ul class="icons">
						<li><a href="https://twitter.com/hashtag/%ED%82%A4%EC%9B%80%ED%9E%88%EC%96%B4%EB%A1%9C%EC%A6%88" target="_blank" class="icon brands fa-twitter"><span class="label">Twitter</span></a></li>
						<li><a href="https://www.facebook.com/kiwoomheroesbaseballclub" target="_blank" class="icon brands fa-facebook-f"><span class="label">Facebook</span></a></li>
						<li><a href="https://www.instagram.com/heroesbaseballclub" target="_blank" class="icon brands fa-instagram"><span class="label">Instagram</span></a></li>
						<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank" class="icon brands fa-youtube"><span class="label">Youtube</span></a></li>
					</ul>

					<!-- Copyright -->
						<div class="copyright">
							<ul class="menu">
								<li>&copy; Untitled. All rights reserved</li><li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
							</ul>
						</div>
				</div>
		</div>

<!-- Scripts -->
<script src="./main/js/jquery.min.js"></script>
<script src="./main/js/jquery.dropotron.min.js"></script>
<script src="./main/js/browser.min.js"></script>
<script src="./main/js/breakpoints.min.js"></script>
<script src="./main/js/util.js"></script>
<script src="./main/js/main.js"></script>

<script type="text/javascript">
//마우스오버
$(document).ready(function () {
	
	$(".day").hover(function () {
		$(this).css("background", "#e7b9c0");
	}, function () {
		$(this).css("background", "#ffffff");
	});
	
});

// write
$(document).ready(function(){
	
	$('#writeBtn').on('click',function(){
		$('#writeFrm').submit();
	});	
});


</script>

	</body>
</html>