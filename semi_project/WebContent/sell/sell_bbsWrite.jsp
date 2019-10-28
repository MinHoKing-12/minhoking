<%@page import="util.utilMethod"%>
<%@page import="member.MemberDto"%>
<%@page import="java.util.List"%>
<%@page import="sellbbs.sell_Dto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
Object ologin = session.getAttribute("login");
MemberDto mem = null;
if(ologin == null){
	
}else{
	mem = (MemberDto)ologin;	
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
		<title>Write Sell</title>
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="../main/css/main.css" />
		<link href="../sellcss/global.css" rel="stylesheet" type="text/css" />
			<link rel="shortcut icon"  href="../main/images/favicon.ico">
		<style type="text/css">
.imgshot {
	width: 400px;
	height: 400px;
}
.imginner {
	width: 100%;
	height: 100%;
}
.filebox label {
  display: inline-block;
  padding: .5em .75em;
  color: #fff;
  font-size: inherit;
  line-height: normal;
  vertical-align: middle;
  background-color: #5cb85c;
  cursor: pointer;
  border: 1px solid #4cae4c;
  border-radius: .25em;
  -webkit-transition: background-color 0.2s;
  transition: background-color 0.2s;
}
.filebox label:hover {
  background-color: #6ed36e;
}
.filebox label:active {
  background-color: #367c36;
}
.filebox input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
}
table {
	width: 600px;
}
table th {
	color: #820024; 
	vertical-align : middle;	/* 테이블 가운대로 모으기 */
}
.noborder {
	border: none;
}
input[type="text"]{
	border: 0;
}
input[type="number"]{
	border: 0;
}
.content {
	font-size: 17px;
}
input[type="button"]{
	font-size: 14px;
}
.tdnoborder {
	border: 0;
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
							<a href="../memcontrol?command=login">로그인</a>|<a href="../login/account.jsp">회원가입</a>
						</div>
					<%
					}else {
						MemberDto user = (MemberDto)session.getAttribute("login");
						%>
						<div id="mymenu" style="font-size: 12px;" align="right">
							<b><%=user.getNickname() %></b>님 환영합니다!   <a href="../mypagecontrol?command=mymsgbox">마이페이지</a>|<a href="../memcontrol?command=logout">로그아웃</a>
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
								<li class="current"><a href="../sellcontrol?command=list&page=1">거래 게시판</a></li>
								<li><a href="../freeControl?command=list">자유 게시판</a></li>
								<li><a href="../VoteControl?command=start">투표 게시판</a></li>
								<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank">경기 하이라이트</a></li>
							</ul>
						</nav>
				</div>

			<!-- Main -->
			<img  style="width:100%; height: 100%;" alt="bb" src="../sellbbsimage/sellban.png">
				<section class="wrapper style1">
					<div class="container">
						<div id="content">

							<!-- Content -->
								
								 <div align="center">
									<form id="frm" action="../sellcontrol?command=addProduct" enctype="multipart/form-data" method="post">
									<!-- <input type="hidden" name="command" value="addProduct"> -->
									<input type="hidden" name="id" value="<%= mem.getId()%>">
									<table border="1">
										<col width="100px"><col width="400px">
									
										<tr>
											<th colspan="2">판매 물건 등록</th>
										</tr>
										<tr>
											<th>닉네임</th>
											<td>
												<input type="text" style="width: 100%" value=<%=mem.getNickname() %> readonly="readonly">
											</td>
										</tr>
										<tr>
											<th>카테고리</th>
											<td>
												<select id="sel" name="sel" style="width: 100%">
													<option value="선택">삽니다/팝니다</option>
													<option value="삽니다">삽니다</option>
													<option value="팝니다">팝니다</option>
												</select>
											</td>
										</tr>
										<tr>
											<th>상품</th>
											<td>
												<select id="product" name="product" style="width: 100%">
													<option value="종류">종류</option>
													<option value="의류">의류</option>
													<option value="장비">장비</option>
													<option value="사인">사인</option>
													<option value="티켓">티켓</option>
													<option value="기타">기타</option>
												</select>
											</td>
										</tr>
										<tr>
											<th>상품 사진</th>
											<td>
												<div class="filebox">
  												<label for="ex_file">업로드</label>
 												<input type="file" id="ex_file" name="filename">
												</div>
											</td>
										</tr>
										<tr>
											<th>제목</th>
											<th>
												<input type="text" name="title" id="title" style="width: 100%">
											</th>
										</tr>
										<tr>
											<th>내용</th>
											<th>
												<input type="text" name="contentText" id="contentText" style="width: 100%">
											</th>
										</tr>
										<tr>
											<th>가격</th>
											<th>
												<input type="number" name="price" id="price" style="width: 100%" placeholder="숫자만 입력하세요">
											</th>
										</tr>
										<tr>
											<td class="tdnoborder" colspan="2">
												<input type="button" value="추가" id="addBtn">
												<input type="button" value="돌아가기" onclick="location.href='../sellcontrol?command=list&page=1'">
											</td>
										</tr>
									</table>
									</form>
									</div>
									</div>
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
<script type="text/javascript">
$(document).ready(function () {

	
	$("#addBtn").click(function () {
		var title = $("#title").val().trim();
		var content = $("#contentText").val().trim();
		var price = $("#price").val().trim();
		//숫자 길이를 구하는 것
		var priceLength = price.toString().length;
	//	alert(priceLength);
		if( $("#sel").val().trim() == "선택" ){
			alert("카테고리를 선택해 주세요.");
			$("#sel").focus();
		} else if ( $("#product").val().trim() == "종류" ) {
			alert("상품 카테고리를 선택해 주세요");
			$("#product").focus();
		} else if ( !$("#ex_file").val()){
			alert("상품 사진을 선택해 주세요");
			$("#ex_file").focus();
		} else if ( title == null || title == "" ){
			alert("제목을 입력해 주세요");
			$("#title").focus();
		} else if ( title.length < 4 ) {
			alert("제목은 4글자 이상 입력해 주세요");
			$("#title").focus();
		} else if ( content == null || content == "" ){
			alert("내용을 입력해 주세요");
			$("#content").focus();
		} else if ( priceLength == 0 ){
			alert("가격을 입력해 주세요.");
			$("#price").focus();
		} else if ( priceLength >= 10 ) {
		    alert("10억 이하만 입력 가능합니다.");
			$("#price").focus();
		} else {
			alert("글쓰기 완료");
			$("#frm").submit();
		}
	});
});



</script>
		<!-- Scripts -->
			<script src="../main/js/jquery.min.js"></script>
			<script src="../main/js/jquery.dropotron.min.js"></script>
			<script src="../main/js/browser.min.js"></script>
			<script src="../main/js/breakpoints.min.js"></script>
			<script src="../main/js/util.js"></script>
			<script src="../main/js/main.js"></script>

	</body>
</html>