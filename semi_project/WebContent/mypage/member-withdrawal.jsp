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
	input[type=password] {
		padding: 5px;
		font-size: 18px;
	}
	table {
		border: none;
		border-top: 1px solid #820024;
		border-collapse: collapse;
	}
	th,td {
		border: none;
		padding: 5px 10px;
		border-bottom: 1px solid #820024;
	}
	th {
		border-right: 1px solid #820024;
		font-weight: bolder;
	}
	.first-row th, .first-row td{
		border-top: 3px solid rgb(130, 0, 36, 0.6)
	}
	.last-row th, .last-row td{
		border-bottom: 3px solid rgb(130, 0, 36, 0.6);
	}
	#withdrawal-frm {
		text-align: center;
	}
	#content{
		font-size: 15px;
	}
	.subtitle {
		font-size: 17px;
		font-weight: 900;
		color: #820024;
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
									<li><a href="mypagecontrol?command=myfreelike">좋아요한 게시글</a></li>
									<li><a href="mypagecontrol?command=myinfo">회원정보 수정</a></li>
									<li class="active"><a href="mypagecontrol?command=member-withdrawal">> 회원탈퇴</a></li>
								</ul>
							</section>

						</div>
					</div>
					<div class="col-8  col-12-narrower imp-narrower">
						<div id="content">
							<h2>회원탈퇴</h2>
							<div>
								<p>아래의 내용을 확인해주세요</p>
								<p>
									<span style="color: #f00; font-weight: bolder;">"회원 탈퇴 즉시 회원 정보는 모두 삭제되며, 재가입 시에도 복원되지 않습니다"</span>
									<br>- 삭제되는 정보 : 작성한 쪽지, 받은 쪽지, 작성한 게시글, 작성한 댓글, 회원등급 포인트
								</p>
								<div>
								<%
								int recvMsg = (int)request.getAttribute("receiveMsg");
								int sendMsg = (int)request.getAttribute("sendMsg");
								int bbsCount = (int)request.getAttribute("bbsCount");
								int commCount = (int)request.getAttribute("commCount");
								%>
									<span class='subtitle'>나의 정보 (※탈퇴 후 전부 삭제됩니다)</span>
									<table>
									<col width="100"><col width="200">
									<tr class='first-row'>
										<th>이름</th>
										<td><%=user.getName() %></td>
									</tr>
									<tr>
										<th>작성한 쪽지</th>
										<td><%=sendMsg %>개</td>
									</tr>
									<tr>
										<th>받은 쪽지</th>
										<td><%=recvMsg %>개</td>
									</tr>
									<tr>
										<th>작성한 게시글</th>
										<td><%=bbsCount %>개</td>
									</tr>
									<tr>
										<th>작성한 댓글</th>
										<td><%=commCount %>개</td>
									</tr>
									<tr class='last-row'>
										<th>나의 포인트</th>
										<td><%=user.getPoint() %>점</td>
									</tr>
									</table>
								</div>
								<div>
									<span class='subtitle'>정확한 본인 확인을 위해 비밀번호를 입력해주세요.</span>
									<form id="withdrawal-frm">
									<input type="hidden" name="id" value="<%=user.getId() %>">
									<input type="hidden" name="command" value="withdrawal-check">
									<table>
									<tr class="first-row last-row">
										<th style="border-right: 1px solid #820024;">회원아이디</th>
										<th style="border-right: 1px solid #820024;"><%=user.getId() %></th>
										<td style="border-right: 1px solid #820024;">비밀번호</td>
										<td><input type="password" name="pwd"></td>
									</tr>
									</table>
									</form>
									<div align="center">
										<button type="button" id="withdrawal-btn">회원탈퇴</button>
									</div>
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
		$("#withdrawal-btn").click(function () {
			var id = $("input[name=id]").val();
			var pwd = $("input[name=pwd]").val();
			var command = $("input[name=command]").val();
		//	alert(id);
		//	alert(pwd);
		//	alert(command);
			
			$.ajax({
				url: "./memcontrol",
				type: "POST",
				data: { command:command, id:id, pwd:pwd },
				dataType: "text",
				success: function (data) {
				//	alert(data.trim());
					if(data.trim() == "true") {
						location.href = "mypagecontrol?command=withdrawalAf";
					}else if(data.trim() == "false") {
						alert("비밀번호가 잘못되었습니다");
					}
				},
				error: function () {
					alert("error");
				}
			});
		});
	});
	</script>

</body>
</html>