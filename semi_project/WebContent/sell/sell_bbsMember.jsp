<%@page import="member.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<%
	MemberDto dto = (MemberDto)request.getAttribute("member");
	String grade = (String)request.getAttribute("grade");
	String imgname = (String)request.getAttribute("imgname");
%>
<html>
	<head>
		<title>My Page</title>
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
		<style type="text/css">
a {
	border-bottom: none;
}
table {
	border: none;
	border-top: 1px solid #710b24;
	border-collapse: collapse;
			
}
th,td {
	border: none;
	padding: 5px;
	border-bottom: 1px solid #710b24;
	font-size: 20px;
}
.tb_head th {
	background-color: #710b24;
	color: white;
}
.myButton {
	-moz-box-shadow:inset 0px 0px 0px 0px #a6827e;
	-webkit-box-shadow:inset 0px 0px 0px 0px #a6827e;
	box-shadow:inset 0px 0px 0px 0px #a6827e;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #7d5d3b), color-stop(1, #634b30));
	background:-moz-linear-gradient(top, #7d5d3b 5%, #634b30 100%);
	background:-webkit-linear-gradient(top, #7d5d3b 5%, #634b30 100%);
	background:-o-linear-gradient(top, #7d5d3b 5%, #634b30 100%);
	background:-ms-linear-gradient(top, #7d5d3b 5%, #634b30 100%);
	background:linear-gradient(to bottom, #7d5d3b 5%, #634b30 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#7d5d3b', endColorstr='#634b30',GradientType=0);
	background-color:#7d5d3b;
	-moz-border-radius:13px;
	-webkit-border-radius:13px;
	border-radius:13px;
	border:2px solid #54381e;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:14px;
	padding:10px 21px;
	text-decoration:none;
	text-shadow:0px 2px 0px #4d3534;
}
.myButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #634b30), color-stop(1, #7d5d3b));
	background:-moz-linear-gradient(top, #634b30 5%, #7d5d3b 100%);
	background:-webkit-linear-gradient(top, #634b30 5%, #7d5d3b 100%);
	background:-o-linear-gradient(top, #634b30 5%, #7d5d3b 100%);
	background:-ms-linear-gradient(top, #634b30 5%, #7d5d3b 100%);
	background:linear-gradient(to bottom, #634b30 5%, #7d5d3b 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#634b30', endColorstr='#7d5d3b',GradientType=0);
	background-color:#634b30;
}
.myButton:active {
	position:relative;
	top:1px;
}
</style>
</head>
<body class="is-preload">
		<div align="center" style="padding: 200px 0 0 0">
			<table>
				<tr>
					<th>등급</th>
					<td>
						<img alt="이미지없음" src="./images/grade/<%=imgname %>" width=20px, height=20px>
						<b><%=grade %></b><br>
					</td>
				</tr>
				<tr>
					<th>ID</th>
						<td><%=dto.getId() %></td>
				</tr>
				<tr>
					<th>닉네임</th>
					<td><%=dto.getNickname()%></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><%=dto.getEmail() %></td>
				</tr>
			</table>
			</div>
			<br>
			 <div align="center">
			 <a href="#" class="myButton">쪽지보내기</a>
			 </div>
		
<script type="text/javascript">
	
</script>
</body>
</html>