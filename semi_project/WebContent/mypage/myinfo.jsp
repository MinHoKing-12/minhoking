<%@page import="util.utilMethod"%>
<%@page import="message.MessageDto"%>
<%@page import="java.util.List"%>
<%@page import="member.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>My Page</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="main/css/main.css" />
		<link rel="stylesheet" type="text/css" href="login/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="login/css/util.css" />
			<link rel="shortcut icon"  href="main/images/favicon.ico">
		<style type="text/css">
		ul.links li.active {
			font-weight: 900;
		}
		
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
			font-size: 13px;
		}
		.tb_head th {
			background-color: #710b24;
			color: white;
		}
		
		.validate-input {
			position: relative;
		}
		
		.alert-validate::before {
		  content: attr(data-validate);
		  position: absolute;
		  z-index: 1000;
		  max-width: 70%;
		  background-color: #fff;
		  border: 1px solid #c80000;
		  border-radius: 14px;
		  padding: 4px 25px 4px 10px;
		  top: 50%;
		  -webkit-transform: translateY(-50%);
		  -moz-transform: translateY(-50%);
		  -ms-transform: translateY(-50%);
		  -o-transform: translateY(-50%);
		  transform: translateY(-50%);
		  right: 10px;
		  pointer-events: none;
		
		  font-family: Ubuntu-Bold;
		  color: #c80000;
		  font-size: 13px;
		  line-height: 1.4;
		  text-align: left;
		
		  visibility: hidden;
		  opacity: 0;
		
		  -webkit-transition: opacity 0.4s;
		  -o-transition: opacity 0.4s;
		  -moz-transition: opacity 0.4s;
		  transition: opacity 0.4s;
		}
		.alert-validate::after {
		  content: "\f06a";
		  font-family: FontAwesome;
		  display: block;
		  position: absolute;
		  z-index: 1100;
		  color: #c80000;
		  font-size: 16px;
		  top: 50%;
		  -webkit-transform: translateY(-50%);
		  -moz-transform: translateY(-50%);
		  -ms-transform: translateY(-50%);
		  -o-transform: translateY(-50%);
		  transform: translateY(-50%);
		  right: 16px;
		}
		
		.alert-validate::before {
		  visibility: visible;
		  opacity: 1;
		}
		
		input[type=text], input[type=password] {
			padding: 10px;
			font-size: 17px;
		}
		.sub {
			font-size: 17px;
			font-weight: 800;
		}
		</style>
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
					<a href="../memcontrol?command=login">로그인</a>|<a href="../login/account.jsp">회원가입</a>
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
									<li class="active"><a href="mypagecontrol?command=myinfo">> 회원정보 수정</a></li>
									<li><a href="mypagecontrol?command=member-withdrawal">회원탈퇴</a></li>
								</ul>
							</section>

						</div>
					</div>
					<div class="col-8  col-12-narrower imp-narrower">
						<div id="content">

							<!-- Content -->
							<%
							MemberDto userInfo = (MemberDto)request.getAttribute("loginInfo");
							System.out.println(userInfo.toString());
							%>
							
							<div class="limiter">
							<div class="container-login100">
								<div class="wrap-login100">
									<form class="validate-form p-l-55 p-r-55 p-b-100" method="post" action="memcontrol?command=update">
										<h3>회원정보 수정</h3>
										<input type="hidden" name="command" value="update">
										<div class="sub m-b-10 m-l-10 f-s">ID</div>
										<div id="alert-id" class="validate-input m-b-16" data-validate="">
											<input class="input100" type="text" id="_id" name="id" placeholder="ID" value="<%=userInfo.getId() %>" readonly="readonly">
										</div>
										<div class="sub m-b-10 m-l-10 f-s">비밀번호</div>
										<div id="alert-pwd" class="validate-input m-b-16" data-validate = "">
											<input class="input100" type="password" id="_pwd" name="pwd" placeholder="PASSWORD">
										</div>
										<div style="height: 50px; background-color: #fff; display: none; margin: 0" id="wrong-idpwd-msg" class="wrap-input100 validate-input m-b-16 alert-validate" 
											data-validate="Caps Lock키가 활성화 되었습니다."></div>
										<div class="sub m-b-10 m-l-10 f-s">비밀번호 확인</div>
										<div id="alert-pwdchk" class="validate-input m-b-16" data-validate = "">
											<input class="input100" type="password" id="_pwdchk" name="pwdchk" placeholder="PASSWORD">
										</div>
										<div style="height: 50px; background-color: #fff; display: none; margin: 0" id="wrong-idpwd-msg" class="wrap-input100 validate-input m-b-16 alert-validate" 
											data-validate="Caps Lock키가 활성화 되었습니다."></div>
										<div class="sub m-b-10 m-l-10 f-s" >닉네임</div>
										<div id="alert-nickname" class="validate-input m-b-16" data-validate="">
											<input class="input100" type="text" id="_nickname" name="nickname" value="<%=userInfo.getNickname() %>" placeholder="NICKNAME">
										</div>
										<div class="sub m-b-10 m-l-10 f-s">이름</div>
										<div id="alert-name" class="validate-input m-b-16" data-validate="">
											<input class="input100" type="text" id="_name" name="name" value="<%=userInfo.getName() %>" placeholder="NAME" readonly="readonly">
										</div>
										
										<div class="sub m-b-10 m-l-10 f-s">주소</div>
										
											<div class=" validate-input m-b-16" data-validate="" style="width: 50%; float:left">
												<input readonly="readonly" class="input100" type="text" id="sample4_postcode" name="postnum"  value=<%=userInfo.getPostnum() %> placeholder="POST NUMBER">
											</div>
											
											<div class="m-b-16" style="width: 40%; display: inline-block; margin-left: 10%">
												<button type="button" class="login100-form-btn"
												 	onclick="sample4_execDaumPostcode()">우편번호 찾기</button>
											</div>
										
										<div id="alert-address" class="validate-input m-b-16" data-validate="">
											<input readonly="readonly" class="input100" type="text" id="sample4_roadAddress" name="address" value="<%=userInfo.getAddress() %>" placeholder="ADDRESS">
										</div>
										
										<div id="alert-addressDetail" class="validate-input m-b-16" data-validate="">
											<input class="input100" type="text" id="sample4_detailAddress" name="address_detail" value="<%=userInfo.getAddress_detail() %>" placeholder="ADDRESS DETAIL">
										</div>
														
										<div class="sub m-b-10 m-l-10 f-s">이메일</div>
										<div id="alert-email" class="validate-input m-b-16" data-validate="">
											<input class="input100" type="text" id="_email" name="email" value="<%=userInfo.getEmail() %>" placeholder="E-MAIL">
										</div>
					
										<div class="container-login100-form-btn m-t-50">
											<button id="update_btn" class="login100-form-btn" type="submit">
												회원정보 수정
											</button>
										</div>
									</form>
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
	<script type="text/javascript"></script>

	<!-- Scripts -->
		<script src="main/js/jquery.min.js"></script>
		<script src="main/js/jquery.dropotron.min.js"></script>
		<script src="main/js/browser.min.js"></script>
		<script src="main/js/breakpoints.min.js"></script>
		<script src="main/js/util.js"></script>
		<script src="main/js/main.js"></script>
		
		<script src="mypage/js/myinfo.js"></script>
		<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

</body>
</html>