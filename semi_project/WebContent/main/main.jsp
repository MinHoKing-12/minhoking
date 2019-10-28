<%@page import="member.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>KIWOOM SUPPORTERS</title>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
	<link rel="stylesheet" href="./css/main.css" />
	<link rel="shortcut icon"  href="images/favicon.ico">

</head>
<body class="is-preload" style="background-color: black">
	<div id="page-wrapper">

		<!-- Header -->
		<div id="header">
		
		<!-- My Menu Bar -->
			<%
			if(session.getAttribute("login") == null) {
			%>
				<div id="mymenu" style="font-size: 12px;" align="right">
					<a href="../memcontrol?command=login">로그인</a>|<a href="../login/account.jsp">회원가입</a>
				</div>
			<%
			}else {
				MemberDto user = (MemberDto)session.getAttribute("login");
				%>
				<div id="mymenu" style="font-size: 12px;" align="right">
					<b><%=user.getNickname() %></b>님 환영합니다!   
					<a href="../mypagecontrol?command=mymsgbox">마이페이지</a>|<a href="../memcontrol?command=logout">로그아웃</a>
				</div>
				<%
			}
			%>
			<!-- Logo -->
				<a href="../memcontrol?command=main" id="logo"><img src="images/ks.png" style="width: 400px" ></img></a><br><br>

			<!-- Nav -->
				<nav id="nav">
					<ul>
						<li class="current"><a href="../memcontrol?command=main">Home</a></li>
						<li><a href="../calendarcontrol?command=calendar">경기 일정</a></li>
						<li><a href="../sellcontrol?command=list&page=1">거래 게시판</a></li>
						<li><a href="../freeControl?command=list">자유 게시판</a></li>
						<li><a href="../VoteControl?command=start">투표 게시판</a></li>
						<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank">경기 하이라이트</a></li>
					</ul>
				
				</nav>

		</div>

		<!-- Banner -->
		<section id="banner">
			<header>
				<em><h2>NEW HEROES KIWOOM HEROES</h2> : KIWOOM SUPPORTERS</em>
			</header>
		</section>

		
		<section class="wrapper style1">
			<div class="container" align="center" >
				<div class="row gtr-200">
			<div>
			<br><br>
			<h2>7월의 MVP</h2>
						<div class="box highlight" style="float:left; padding: 3px" >
						<iframe width="430" height="300" src="https://www.youtube.com/embed/CP8VE0nG8_c" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
							<h5>[7월MVP]수훈선수 송성문</h5>
							<p style="color: gray;">2019.08.14</p>
						</div>
		
						<div class="box highlight" style="float:left; padding: 3px">
						<iframe width="430" height="300" src="https://www.youtube.com/embed/pGCMj0TAWDI" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
							<h5>[7월MVP]우수타자 샌즈</h5>
							<p style="color: gray;">2019.08.14</p>
						</div>
					
						<div class="box highlight" style="float:left; padding: 3px">
						<iframe width="430" height="300" src="https://www.youtube.com/embed/72oWYwB9AIY" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
							<h5>[7월MVP]우수투수 브리검</h5>
							<p style="color: gray;">2019.08.14</p>
						</div>
						
						<a href="https://www.youtube.com/channel/UC_MA8-XEaVmvyayPzG66IKg"><img src="images/more.png" style="margin-left:30px;  margin-top: 120px; width: 50px; height: 50px "></a>
						
					
					</div>
				</div>
			</div>
		</section>

		<!-- Posts -->
		<section class="wrapper style1">
			<div class="container" align="center">

				<!-- 티켓,투표 -->
				<br>
					<div style="display: inline-block; margin: 20px; border-radius: 10px;"> <!-- inline-block : div 나란히 붙이는 tag -->
						<h4 style="color: gray;">티켓 예매 바로가기</h4>
						<a href="http://ticket.interpark.com/Contents/Sports/GoodsInfo?SportsCode=07001&TeamCode=PB003">
						<img src="images/ticket.png"></a>
					</div>
					<div style="display: inline-block; margin: 20px; border-radius: 10px;">		
						<h4 style="color: gray;">투표 바로가기</h4>
						<a href="../VoteControl?command=list">
						<img src="images/vote.png"></a>							
					</div>
				

				<!-- SNS 링크 -->
				<br><br><br>
						<div style="margin: auto; align-content: center;">
							<a href="https://twitter.com/hashtag/%ED%82%A4%EC%9B%80%ED%9E%88%EC%96%B4%EB%A1%9C%EC%A6%88">
							<img src="images/twitter.png" alt="" style="width: 20%; margin: 5px"/></a>
							
							<a href="https://www.facebook.com/kiwoomheroesbaseballclub" > 
							<img src="images/facebook.png" alt="" style="width: 20%; margin: 5px"/></a>
							
							<a href="https://www.instagram.com/heroesbaseballclub" > 
							<img src="images/insta.png" alt="" style="width: 20%; margin: 5px"/></a>
							
							<a href="https://www.youtube.com/user/heroesbaseballclub"> 
							<img src="images/youtube.png" alt="" style="width: 20%; margin: 5px"/></a>
						</div>
					</div>
				</section>						
			</div>
		

							
		<!-- CTA -->
		<section id="cta" class="wrapper style3">
			<div class="container">
				<header>
					<h2>We are Kiwoom Supporters</h2>
				</header>
			</div>
		</section>

		<!-- Footer -->
		<div id="footer">
			<!-- Icons -->
			<ul class="icons">
				<li><a href="https://twitter.com/hashtag/%ED%82%A4%EC%9B%80%ED%9E%88%EC%96%B4%EB%A1%9C%EC%A6%88" target="_blank" class="icon brands fa-twitter"><span class="label"></span></a></li>
				<li><a href="https://www.facebook.com/kiwoomheroesbaseballclub" target="_blank" class="icon brands fa-facebook-f"><span class="label"></span></a></li>
				<li><a href="https://www.instagram.com/heroesbaseballclub" target="_blank" class="icon brands fa-instagram"><span class="label"></span></a></li>
				<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank" class="icon brands fa-youtube"><span class="label"></span></a></li>
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
	<script src="./js/jquery.min.js"></script>
	<script src="./js/jquery.dropotron.min.js"></script>
	<script src="./js/browser.min.js"></script>
	<script src="./js/breakpoints.min.js"></script>
	<script src="./js/util.js"></script>
	<script src="./js/main.js"></script>
	
</body>
</html>