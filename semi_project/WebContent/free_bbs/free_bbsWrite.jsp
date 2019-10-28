
<%@page import="member.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<% 

	// 로그인세션
	String LoginId = "";
	String LoginNick = "";

	MemberDto user = (MemberDto)session.getAttribute("login");
	if(user == null){
		
	}else if(user!=null){
		LoginId = user.getId();
		LoginNick = user.getNickname();
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>KIWOOM SUPPORTERS - 자유게시판</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="../main/css/main.css">
		<link rel="shortcut icon"  href="../main/images/favicon.ico">
		<link href="../free_bbs/freecss/global.css" rel="stylesheet" type="text/css" />
<style type="text/css">
/* button{
	background-color: white;
}
table.type01 th { 
    padding: 10px;
    font-weight: bold;
    vertical-align: top;
    border: 1px solid #ccc;
    text-align: center;    
    background-color: white;
    color: red; 
}
table.type01 td {
    padding: 10px;
    vertical-align: top;
    border: 1px solid #ccc;
    background-color: white; 
} */
#nav ul li.current:before {
    -moz-transform: rotateZ(45deg);
    -webkit-transform: rotateZ(45deg);
    -ms-transform: rotateZ(45deg);
    transform: rotateZ(45deg);
    width: 0.75em;
    height: 0.75em;
    content: '';
    display: block;
    position: absolute;
    bottom: -0.5em;
    /* left: 30px; */
    margin-left: -0.375em;
    background-color: #820024;
    background-image: url(images/bg01.png);
}
.al{
	text-align: center;
}
.writebtn{
   position: relative;
   margin-left: 45%;
}


</style>

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
				user = (MemberDto)session.getAttribute("login");
				%>
				<div id="mymenu" style="font-size: 12px;" align="right">
					<b><%=user.getNickname() %></b>님 환영합니다!   <a href="./mypagecontrol?command=mymsgbox">마이페이지</a>|<a href="./memcontrol?command=logout">로그아웃</a>
				</div>
				<%
			}
			%>
			<!-- Logo -->
				<a href="../memcontrol?command=main" id="logo"><img src="../main/images/ks.png" style="width: 400px" ></img></a><br><br>

			<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a href="../memcontrol?command=main">Home</a></li>
						<li><a href="../calendarcontrol?command=calendar">경기 일정</a></li>
						<li><a href="../sellcontrol?command=list&page=1">거래 게시판</a></li>
						<li class="current"><a href="../freeControl?command=list">자유 게시판</a></li>
						<li><a href="../VoteControl?command=start">투표 게시판</a></li>
						<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank">경기 하이라이트</a></li>
					</ul>
				</nav>
		</div>
	<!-- Main -->
	<img  style="width:100%; height: 100%;" alt="bb" src="../images/free_bbs/freeban.png">
		<!-- <section class="wrapper style1"> -->
		<br><br>
			<div class="container">
				<div id="content">
			

<div align="center">
<div align="center">
<form id="frm" method="post">
<input type="hidden" name="id" id="id" value=<%=LoginId %> >
<input type="hidden" name="nickname" id="nickname" value=<%=LoginNick %> >
<input type = "hidden" name="command" value="bbsWrite">

<form action="../freeControl?command=updateAf" method="post" enctype="multipart/form-data">

<table border="0" class="type01" style="width: 80%; height: 80%">
	<col width="150"><col width="500">
	<tr>
		<th class='al'>
		닉네임
		</th>
		<td>
			<%=LoginNick %>
		</td>
	</tr>
	
	<tr>
		<th class='al'>
		제목
		</th>
		<td>
		<input type="text" name="title" id="title" style="width:900px;">
		</td>
	</tr>
	
	<tr>
		<th class='al'>
		내용
		</th>
		<td>
		<!-- <input type="text" name="content" id="content" style="width:600px; height:300px;"> -->		
		<textarea id="content" name="content" rows="15" cols="68"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="align-items : center">
			<input type="button" value="글쓰기" class="writebtn" onclick="func()"> &nbsp;
		</td>
	</tr>
</table>
</form>
</div>


</div>
<!-- </section> -->

<script type="text/javascript">
function func() {
	//title, content null값 체크	

		var title = document.getElementById("title").value;
		var content = document.getElementById("content").value;
		
		if(title == ""){
			alert("title is empty");
		}else if(content == ""){
			alert("content is empty");
		}else {				
			$("#frm").attr("action", "../freeControl").submit();			
		}			
	}
</script>

	<!-- Footer -->
		<div id="footer">
		
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

	<!-- Scripts -->
		<script src="../main/js/jquery.min.js"></script>
		<script src="../main/js/jquery.dropotron.min.js"></script>
		<script src="../main/js/browser.min.js"></script>
		<script src="../main/js/breakpoints.min.js"></script>
		<script src="../main/js/util.js"></script>
		<script src="../main/js/main.js"></script>


</div>
</body>


</html>