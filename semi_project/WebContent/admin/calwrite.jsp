<%@page import="calendar.CalendarDto"%>
<%@page import="java.util.Calendar"%>
<%@page import="member.MemberDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%
String year = (String)request.getAttribute("year");
String month = (String)request.getAttribute("month");
String day = (String)request.getAttribute("day");

MemberDto user = (MemberDto)session.getAttribute("login");

Calendar cal = Calendar.getInstance();

int tyear = cal.get(Calendar.YEAR);
int tmonth = cal.get(Calendar.MONTH) + 1;
int tday = cal.get(Calendar.DATE);
int thour = cal.get(Calendar.HOUR_OF_DAY); 
int tmin = cal.get(Calendar.MINUTE);

System.out.println("calwrite.jsp 입니다");
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
		width: 900px;
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
			<a href="admincontrol?command=calendarlist">경기일정</a>
			<span>&gt;</span>
			경기일정 등록
		</div>
		<!-- End Small Nav -->
		
		<!-- Main -->
		<div id="main">
			<div class="cl">&nbsp;</div>
			
			<!-- Content -->
			<div id="content" align="center">
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2>경기일정 등록</h2>
					</div>
					<!-- End Box Head -->
					
					<form id="cal" method="post">
						
						<!-- Form -->
						<div class="form">
							<input type = "hidden" name="command" value="writeAf">
							<table class="type01">
							<col width="100"><col width="80">
							<tr>
								<td>상대 팀</td>
								<td>
									<select name="team" class="field size3">
										<option>상대팀을 선택</option>
										<option value="SK">SK</option>
										<option value="두산">두산</option>
										<option value="LG">LG</option>
										<option value="NC">NC</option>
										<option value="KT">KT</option>
										<option value="KIA">KIA</option>
										<option value="삼성">삼성</option>
										<option value="롯데">롯데</option>
										<option value="한화">한화</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td>구장</td>
								<td>
									<select name="stadium" class="field size3">
										<option>구장을 선택</option>
										<option value="고척">고척</option>
										<option value="잠실">잠실</option>
										<option value="문학">문학</option>
										<option value="사직">사직</option>
										<option value="수원">수원</option>
										<option value="광주">광주</option>
										<option value="포항">포항</option>
										<option value="대전">대전</option>
										<option value="대구">대구</option>
										<option value="창원">창원</option>
										<option value="청주">청주</option>
										<option value="울산">울산</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td>일정</td>
								<td>
									<p class="inline-field">
									<select name="year" class="field size2">
									<%
										for(int i = tyear - 5;i < tyear + 6; i++){
											%>
											<option <%=year.equals(i+"")?"selected='selected'":"" %>
												value="<%=i %>"><%=i %></option>
												
											<%
										}
									%>
									</select>년
									
									<select name="month" class="field size2">
									<%
										for(int i = 1;i <= 12; i++){
											%>
											<option <%=month.equals(i+"")?"selected='selected'":"" %>
												value="<%=i %>"><%=i %></option>
												
											<%
										}
									%>	
									</select>월
									
									<select name="day" class="field size2">
									<%
										for(int i = 1;i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
											%>
											<option <%=day.equals(i+"")?"selected='selected'":"" %>
												value="<%=i %>"><%=i %></option>
												
											<%
										}
									%>
									</select>일
									
									<select name="hour" class="field size2">
									<%
										for(int i = 0;i < 24; i++){
											%>
											<option <%=(thour + "").equals(i+"")?"selected='selected'":"" %>
												value="<%=i %>"><%=i %></option>
												
											<%
										}
									%>	
									</select>시
									
									<select name="min" class="field size2">
									<%
										for(int i = 0;i < 60; i++){
											%>
											<option <%=(tmin + "").equals(i+"")?"selected='selected'":"" %>
												value="<%=i %>"><%=i %></option>
												
											<%
										}
									%>	
									</select>분
									</p>
								</td>
							</tr>
							
							<tr>
								<td>승패여부</td>
								<td>
									<select name="outcome" class="field size2">
										<option value="경기 전">경기 전</option>
										<option value="우승">우승</option>
										<option value="패배">패배</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>메모</td>
								<td>
									<textarea rows="10" cols="60" name="memo"> </textarea>
								</td>
							</tr>
							</table>
							
						</div>
						<!-- End Form -->
						
						<!-- Form Buttons -->
						<div class="buttons">
							<input type="button" class="button" value="일정쓰기" id="writebtn">
						</div>
						<!-- End Form Buttons -->
					</form>
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

$(document).ready(function () {
	$("select[name='month']").change(setDay);
	
	$("#writebtn").click(write);
});

function setDay() {
	// 년도와 달을 통해서 마지막 날짜를 구한다
	var year = $("select[name='year']").val() + "";
	var month = $("select[name='month']").val() + "";
	
	var lastday = (	new Date( year, month, 0 ) ).getDate();

	// select 날짜적용
	var str = "";
	for(i = 1;i <= lastday; i++){
		str += "<option value='" + i + "'>" + i + "</option>";
	}
	
	$("select[name='day']").html( str );	
}


function write() {
		$("#cal").attr("action", "./admincontrol?").submit();
	}

</script>
</body>
</html>