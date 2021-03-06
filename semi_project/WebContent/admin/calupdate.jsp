<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="calendar.CalendarDto"%>
<%@page import="java.util.Calendar"%>
<%@page import="member.MemberDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
public String toDates(String mdate){
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
	
	String s = mdate.substring(0, 4) + "-" 	// yyyy
			+ mdate.substring(4, 6) + "-"	// MM
			+ mdate.substring(6, 8) + " " 	// dd
			+ mdate.substring(8, 10) + ":"	// hh
			+ mdate.substring(10, 12) + ":00"; 
	Timestamp d = Timestamp.valueOf(s);
	
	return sdf.format(d);	
}

public String toOne(String msg){	// 08 -> 8
	return msg.charAt(0)=='0'?msg.charAt(1) + "": msg.trim();
}
%>    
<%
Calendar cal = Calendar.getInstance();
int tyear = cal.get(Calendar.YEAR);

CalendarDto dto = (CalendarDto)request.getAttribute("cal");
MemberDto user = (MemberDto)session.getAttribute("login");

String year = dto.getSche().substring(0, 4);
String month = toOne(dto.getSche().substring(4, 6));
String day = toOne(dto.getSche().substring(6, 8));
String hour = toOne(dto.getSche().substring(8, 10));
String min = toOne(dto.getSche().substring(10, 12));

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
			경기일정 수정
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
					
					<form action="admincontrol" method="post">
						
						<!-- Form -->
						<div class="form">
							<input type = "hidden" name="command" value="updateAf">
							<input type="hidden" name="seq" value="<%=dto.getSeq() %>">
							<table class="type01">
							<col width="100"><col width="80">
							<tr>
								<td>상대 팀</td>
								<td>
									<select name="team" class="field size3">
										<option value="<%=dto.getTeam()%>"><%=dto.getTeam()%></option>
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
										<option value="<%=dto.getStadium()%>"><%=dto.getStadium().substring(0,2)%></option>
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
											<option <%=(hour + "").equals(i+"")?"selected='selected'":"" %>
												value="<%=i %>"><%=i %></option>
												
											<%
										}
									%>	
									</select>시
									
									<select name="min" class="field size2">
									<%
										for(int i = 0;i < 60; i++){
											%>
											<option <%=(min + "").equals(i+"")?"selected='selected'":"" %>
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
									<textarea rows="10" cols="60" name="memo"><%=dto.getMemo() %></textarea>
								</td>
							</tr>
							</table>
							
						</div>
						<!-- End Form -->
						
						<!-- Form Buttons -->
						<div class="buttons">
							<input type="button" value="수정" id="updateBtn" onclick="modify()">
							<input type="button" value="삭제" onclick="location.href='admincontrol?command=deletecal&seq=<%=dto.getSeq() %>'">
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
function modify() {
	var f = document.forms[0];
	alert(f)
	f.submit();
}
</script>
</body>
</html>