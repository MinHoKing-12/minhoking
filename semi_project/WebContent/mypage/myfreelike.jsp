<%@page import="free_bbs.free_bbsDto"%>
<%@page import="util.utilMethod"%>
<%@page import="message.MessageDto"%>
<%@page import="java.util.List"%>
<%@page import="member.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>KIWOOM SUPPORTERS - 마이페이지</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="main/css/main.css" />
		<style type="text/css">
		ul.links li.active {
			font-weight: 900;
		}
		a {
			border-bottom: none;
		}
		.tabs{
		  list-style: none;
		  margin: 0;
		  padding: 0;
		}
		.tabs li{
		  background: none;
		  color: #222;
		  display: inline-block;
		  padding: 10px 15px;
		  cursor: pointer;
		}
		 
		.tabs li.current{
		  background: #820024;
		  color: #fff;
		}
		
		table {
			margin: 0;
			border: none;
			border-top: 1px solid #820024;
			border-collapse: collapse;
		}
		th,td {
			border: none;
			padding: 5px;
			border-bottom: 1px solid #820024;
			background-color: #fff;
			font-size: 15px;
		}
		.tb_head th {
			background-color: #820024;
			color: white;
		}
		
		.paging {
			color: #474747;
			border: none;
			padding: 0 5px;
		}
		input[type=text] {
			font-size: 13px;
			padding: 8px;
			margin: 0 3%;
			width: 60%;
			display: inline;
		}
		select {
			width: 10%;
			padding: 5px;
			font-size: 15px;
		}
		.search-div {
			margin: 20px 0;
			text-align: center;
		}
		#search-btn {
			width: 20%;
			line-height: 30px;
			font-size: 15px;
			padding: 5px;
		}
		
		/* paging */
		.paging {
		padding: 0 5px;
		}
		.paging:hover {
			background-color: #820024;
			color: #fff;
		}
		.current {
			background-color: #820024;
			color: #fff;
		}
		
		</style>
			<link rel="shortcut icon"  href="main/images/favicon.ico">
	</head>
<body class="is-preload">
	<div id="page-wrapper">

		<!-- Header -->
		<div id="header">
		
			<!-- My Menu Bar -->
			<%
			// 짐 받는 곳!
			MemberDto user = (MemberDto)session.getAttribute("login");
			String grade = (String)request.getAttribute("grade");
			String imgname = (String)request.getAttribute("imgname");
			int newMsg = (int)request.getAttribute("newMsg");
			
			if(user == null) {
			%>
				<div id="mymenu" style="font-size: 12px;" align="right">
					<a href="./memcontrol?command=login">로그인</a>|<a href="./login/account.jsp">회원가입</a>
				</div>
			<%
			}else {
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
						<li><a href="./calendarcontrol?command=calendar">경기 일정</a></li>
						<li><a href="./sellcontrol?command=list&page=1">거래 게시판</a></li>
						<li><a href="./freeControl?command=list">자유 게시판</a></li>
						<li><a href="./VoteControl?command=start">투표 게시판</a></li>
						<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank">경기 하이라이트</a></li>
					</ul>
				</nav>

		</div>

		<!-- Main -->
		<section class="wrapper style1">
			<div class="container">
				<div class="row gtr-200">
					<div class="col-4 col-12-narrower">
						<div id="sidebar">

							<!-- Sidebar -->

							<section>
								<h3>MyPage</h3>
								<div class="mem-info">
									<p>
										<img alt="이미지없음" src="./images/grade/<%=imgname %>" width=20px, height=20px>
											<b><%=grade %></b><br>
										안녕하세요! <%=user.getNickname() %>님<br>
										<img alt="이미지없음" src="./images/mypage/email.png" width=20px, height=20px> 새 쪽지 : <%=newMsg %>개
									</p>
								</div>
								
							</section>
														
							<section>
								<ul class="links">
									<li><a href="mypagecontrol?command=mymsgbox">나의 쪽지함</a></li>
									<li><a href="mypagecontrol?command=myfreebbs">나의 게시글</a></li>
									<li class="active"><a href="mypagecontrol?command=myfreelike">> 좋아요한 게시글</a></li>
									<li><a href="mypagecontrol?command=myinfo">회원정보 수정</a></li>
									<li><a href="mypagecontrol?command=member-withdrawal">회원탈퇴</a></li>
								</ul>
							</section>

						</div>
					</div>
					<div class="col-8  col-12-narrower imp-narrower">
						<div id="content">
							<div>
								<ul class="tabs">
									<li id="myfreelike" class="current">자유게시판</li>
									<li id="myselllike">거래게시판</li>
								</ul>
							</div>

							<div>
								<%
								List<free_bbsDto> list = (List<free_bbsDto>)request.getAttribute("myfreeList");
								%>
								<table border="1" class="type01">
								<col width="70"><col width="500"><col width="150"><col width="200"><col width="70"><col width="70">
								<tr class="tb_head">
								   <th>번호</th>
								   <th>제목</th>
								   <th>작성자</th>
								   <th>작성일</th>
								   <th>조회수</th>
								   <th>좋아요</th>
								</tr>
								
								<%
								if(list == null || list.size() == 0){  //글이 하나도 없을때
								   %>
								   <tr>
								      <td colspan="6" align="center">좋아요한 글이 없습니다</td>
								   </tr>
								   <%
								}else{
								   for(int i = 0; i < list.size(); i++){
									   free_bbsDto bbs = list.get(i); 
									   
								   %>
								   
								   <tr>
								      <th><%=i + 1 %></th>
								      
								       <td class="seqclick" >        
								         <a href="freeControl?command=forDetail&seqVal=<%=bbs.getSeq() %>"><%=bbs.getTitle() %></a>
								         <input type='hidden' value='<%=bbs.getSeq() %>'>
								       </td> 
								      
								      <td align="center"><%=bbs.getNickname() %></td>
								      <td align="center"><%=utilMethod.dateEdit(bbs.getWdate()) %></td>
								      <td align="center"><%=bbs.getReadcount() %></td>
								      <td align="center"><%=bbs.getBlike() %></td>
								   <%
								   }
								}
								%>
								<!-- Paging -->
								<tr>
									<td colspan ="6" align="center">
									<%
									int totalPages = (int)request.getAttribute("totalPages");
									String select = (String)request.getAttribute("select");
									String str = (String)request.getAttribute("searchText");
									int currentPage = (int)request.getAttribute("page");
									
									for(int i = 1; i <= totalPages; i++) {
										if(str != null && !str.equals("")) {
											%>
											<a class="paging" href="mypagecontrol?command=myfreelike&page=<%=i %>&search-select=<%=select %>&search-text=<%=str %>"><%=i %></a>
											<%
										}else {
											%>
											<a class="paging" href="mypagecontrol?command=myfreelike&page=<%=i %>"><%=i %></a>
											<%
										}
									}
									%>
									</td>
								</tr>
								</table>
								<div class="search-div">
									<form id="search-form" action="mypagecontrol">
										<input type="hidden" name="command" value="myfreelike">
										<select name="search-select">
											<option value="title">제목</option>
											<option value="content">내용</option>
										</select>
										<input type="text" name="search-text">
										<input type="submit" id="search-btn" value="검색">
									</form>
								</div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</section>

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

	</div>

	<!-- Scripts -->
	<script src="main/js/jquery.min.js"></script>
	<script src="main/js/jquery.dropotron.min.js"></script>
	<script src="main/js/browser.min.js"></script>
	<script src="main/js/breakpoints.min.js"></script>
	<script src="main/js/util.js"></script>
	<script src="main/js/main.js"></script>
	
	<script type="text/javascript">
	
	$(function () {
		$('.tabs li').click(function(){
			var type = $(this).attr("id");
			//	alert(type);
			location.href="mypagecontrol?command=" + type;
		});
		
		// current page
		var currentPage = <%=currentPage %>
		var totalPages = <%=totalPages %>
		
		for (var i = 1; i <= totalPages; i++) {
			if(i == currentPage) {
				$(".paging").eq(i-1).addClass("current");
			}
		}
		
	});
	
	</script>

</body>
</html>