<%@page import="sellcomment.sell_comment_Dto"%>
<%@page import="sellcomment.sell_comment_Dao"%>
<%@page import="sellcomment.isell_comment_Dao"%>
<%@page import="util.utilMethod"%>
<%@page import="member.MemberDto"%>
<%@page import="java.util.List"%>
<%@page import="sellbbs.sell_Dto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%


List<sell_Dto> list = (List<sell_Dto>)request.getAttribute("selllist");
int size = (int)request.getAttribute("count");
isell_comment_Dao commentDao = sell_comment_Dao.getInstance();

for(int i =0; i< list.size(); i++){
	sell_Dto dto = list.get(i);
	if(dto.getDel() == 0){
	}
}

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
		<title>Sell Board</title>
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="./main/css/main.css" />
		<link href="./sellcss/global.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon"  href="main/images/favicon.ico">
		<style type="text/css">
.photoimg {
	width: 270px;
	height: 200px;
}
.glassimg {
	width: 20px;
	height: 20px;
}
a:link { color: black; text-decoration: none;}
a:visited { color: black; text-decoration: none;}
.detail:hover { color: #820024; text-decoration: none;} 
h3{
	font-size: 32px;
 	font-weight: 600;
	letter-spacing: -0.6px;
 	color: #212529;
 	text-align: center;
   	margin-bottom: 40px;
}
.empty{
	width: 130px;
   	border-top: 2px solid #820024;
   	margin: 0 auto;
   	margin-bottom: 50px;
}
.cards-wrap {
	width: 900px;
    margin: 0 auto;
    text-align: left;
}
.card-top {
    position: relative;
  	text-align: left;
   	display: inline-block;
   	border-radius: 8px;
   	border: 1px solid #e9ecef;
   	/* width: calc(25% - 34px); */
   	width : 170px;
   	margin-right: 50px;
   	margin-bottom: 40px;
}

.top-category {
    width: 795px;
    margin: 0 auto;
    text-align: right;
    margin-bottom: 30px;
}
.photo {
    width: 100%;
    position: relative;
    height: 160px;
    overflow: hidden;
    border-radius: 8px 8px 0 0;
    background-color: #f8f9fa;
}
.card-desc {
	padding: 15px;
}
.card-title {
    font-size: 17px;
    font-weight: 600;
    letter-spacing: -0.6px;
    color: #212529;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin-bottom: 6px;
    line-height: 1.2;
}
.card-category {
    font-size: 15px;
    color: #868e96;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin-bottom: 8px;
    line-height: 1.3;
}
.card-price {
    font-size: 15px;
    font-weight: 600;
    padding-bottom: 8px;
    border-bottom: 1px solid #e9ecef;
    line-height: 1.3;
    color: #820024;
}
.card-count {
    padding: 12px 0;
    color: #adb5bd;
    font-size: 13px;

}
.content {
	width: 1400px;
	height: 1544px;
}
.card-write{
    width: 800px;
    margin: 0 auto;
    text-align: right;
    margin-bottom: 30px;
}
.card-paging{
    width: 660px;
    margin: 0 auto;
    text-align: center;
    margin-bottom: 30px;
}
.card-top:active {
	box-shadow: 3px 4px 5px rgba(130,0,36,0.2);
}
.card-top:hover {
	box-shadow: 3px 4px 5px rgba(130,0,36,0.2);
}
.detail:hover {
	background-color: #FFE3EE;
	cursor: pointer;
}
select {
	width: 150px;
	padding: .1em .3em;
	border: 2px solid #820024;
	font-size: 15px;
	font-family: inherit;
	background: url('./sellbbsimage/arrow.png') no-repeat 95% 50%;
	border-radius: 0px;
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
}
.paging {
	padding: 0 5px;
}
.paging:hover {
	background-color: #820024;
	color: #fff;
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
								<li><a href="./calendarcontrol?command=calendar">경기 일정</a></li>
								<li class="current"><a href="./sellcontrol?command=list&page=1">거래 게시판</a></li>
								<li><a href="./freeControl?command=list">자유 게시판</a></li>
								<li><a href="./VoteControl?command=start">투표 게시판</a></li>
								<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank">경기 하이라이트</a></li>
							</ul>
						</nav>
				</div>

			<!-- Main -->
			<img  style="width:100%; height: 100%;" alt="bb" src="./sellbbsimage/sellban.png">
				<!-- <section class="wrapper style1"> -->
				<br><br>
					<div class="container">
						<div id="content">

							<!-- Content -->
								
								  <div class="top-category">
								  <select name="categoryBar" id="categoryBar" style="height: 30px; width: 100px; font-size: 15px">
								  	<option value="카테고리" selected="selected">카테고리</option>
								  	<option value="전체보기">전체보기</option>
								  	<option value="-----">-----------</option>
								  	<option value="삽니다">삽니다</option>
								  	<option value="팝니다">팝니다</option>
								  	<option value="-----">-----------</option>
								  	<option value="의류">의류</option>
								  	<option value="장비">장비</option>
								  	<option value="사인">사인</option>
								  	<option value="티켓">티켓</option>
								  	<option value="기타">기타</option> 
								  </select>
								  </div>
								  <section class="cards-wrap">
								  	
								  <%
									  for(int i =0; i < list.size(); i++){
										  sell_Dto dto = list.get(i);
										  List<sell_comment_Dto> commentDto = commentDao.getSell_Comment_List(dto.getSeq());
										  if(dto.getDel() == 0 && dto.getComplete() == 0){
								  %>
								 <article class="card-top">
								 	<div class="photo">
									<a class="detail" href="sellcontrol?command=detail&seq=<%=dto.getSeq() %>">
									<img class="photoimg" alt="물건팜" src="./upload/<%=dto.getFilename()%>">
									</div>
									<div class="card-desc">
									<div class="card-title">
										<b><%=utilMethod.dot7(dto.getTitle())%></b>
									</div>
									<div class="card-category">
										<b><%=dto.getCategory() + " º " + dto.getPcategory() %></b>
									</div>
									<div class="card-price">
										<%=utilMethod.PriceConvert(dto.getPrice()) %>
									</div>
									<div class="card-count">
										조회<%=dto.getRcount() + " · 관심" + dto.getAcount() +" · 댓글" + commentDto.size()%>
									</div>
									</div>
									</a>
								</article>
								  <%
								 }else if(dto.getDel() == 0 && dto.getComplete() == 1){
								 %>
								 <article class="card-top">
								 	<div class="photo">
									<a href="sellcontrol?command=detail&seq=<%=dto.getSeq() %>">
									<img class="photoimg" alt="물건팜" src="./upload/<%=dto.getFilename()%>">
									</div>
									<div class="card-desc">
									<div class="card-title">
										<del><b style="color: # a0a0a0"><%=utilMethod.dot7(dto.getTitle())%></b></del>
									</div>
									<div class="card-category">
										<b><%=dto.getCategory() + " · " + dto.getPcategory() %></b>
									</div>
									<div class="card-price">
										<del><b style="color: # a0a0a0"><%=utilMethod.PriceConvert(dto.getPrice()) %></b></del>
									</div>
									<div class="card-count">
										조회<%=dto.getRcount() + " · 관심" + dto.getAcount() + " · 댓글" + commentDto.size() %>
									</div>
									</div>
									</a>
								</article>	  		
									<%	
									  	}
									  }
								  %>
								  </section>
								</div>
								  <div class="card-write">
								  <%
								  if(mem == null){
								  %>

								  <%
								  }else{
								  %>
								  <input type="button" value="글쓰기" onclick="location.href='sellcontrol?command=add'">
								  <%
								  }
								  %>
								  </div>
								  <div class="card-paging">
								    <% 
								    String categoryValue = (String)request.getAttribute("categoryValue");
								    String searchValue = (String)request.getAttribute("searchValue");
								    String text = (String)request.getAttribute("text");
									for(int c=1; c < size + 1; c++){
										if(categoryValue == null && searchValue == null){
									//		System.out.println("여기1");
									%>
									<a class="paging" href="sellcontrol?command=list&page=<%=c %>"><%=c %></a>
									<%
									}else if(searchValue != null){
									//	System.out.println("여기2");
									%>
									<a class="paging" href="sellcontrol?command=search&page=<%=c %>&searchValue=<%=searchValue %>&text=<%=text %>"><%=c %></a>
									<%
									}else if(categoryValue != null){
									//	System.out.println("여기3");
									%>
									<a class="paging" href="sellcontrol?command=searchCategory&page=<%=c %>&categoryValue=<%=categoryValue %>"><%=c %></a>
									<%
										}
									}
									%>
								  </div>
								  <div class="card-serach" align="center">
								  <select style="height: 30px; width: 80px" name="searchsel" id="searchsel">
								  	<option value="선택">선택</option>
								  	<option value="닉네임">닉네임</option>
								  	<option value="제목">제목</option>
								  </select>
								  <%
								  if(text != null){
								  %>
								  <input type="search" name="searchText" size="60px" style="height: 30px" id="searchText" value="<%=text %>">
								  <%
								  }else{
								  %>
								  <input type="search" name="searchText" size="60px" style="height: 30px" id="searchText">
								  <%
								  }
								  %>
								  <img class="glassimg" src="./sellbbsimage/001-magnifying-glass.png"></img>
								 </div>
								  </div>
								  	<!-- 카테고리 셀렉티드 -->
								  <%for(int i = 0; i < 12; i++){%>
								  	<script type="text/javascript">
								  	 $(document).ready(function () {
								    // 이 자체가 페런트 값이 됩니다. 반복문 있을 때에는 밑에 태그에서 패런트를 불러서 최상위 값을 찾아야 합니다.
								  	var selectTag = $("#categoryBar");
								 // alert(selectTag.html());
								  	var selected = selectTag.children().eq(<%=i %>).val().trim();
								  	if(selected == "<%=categoryValue %>"){
								  	//	alert(selected);
								  	selectTag.children().eq(<%=i %>).attr("selected", "selected");
								  	}
								  	});
								  	</script>
								  <%
								  }
								  %>
								  <!-- 검색 셀렉티드 -->
								  <%
								  for(int i=0; i<12; i++){
								  %>
								  <script type="text/javascript">
								  $(document).ready(function () {
								   var selectTag = $("#searchsel");
								   var selected = selectTag.children().eq(<%=i %>).val().trim();
								   if(selected == "<%=searchValue %>"){
									  selectTag.children().eq(<%=i %>).attr("selected", "selected");  
								   }
								  });
								  </script>
								  <%
								  }
								  %>
								<script type="text/javascript">
								$(function () {
									$("#searchText").keydown(function(key) {
										if(key.keyCode == 13){
											if($("#searchsel").val() == "선택"){
												alert("검색 카테고리를 설정하세요.");
											}else if($("#searchText").val() == ""){
												alert("검색 내용을 입력하세요");
											}else{
												var searchValue = $("#searchsel").val();
												var text = $("#searchText").val();
												location.href = "sellcontrol?command=search&page=1&searchValue="+searchValue+"&text="+text;
											}	
										}
									});
									
									$(".glassimg").click(function () {
										if($("#searchsel").val() == "선택"){
											alert("검색 카테고리를 설정하세요.");
										}else if($("#searchText").val() == ""){
											alert("검색 내용을 입력하세요");
										}else{
											var searchValue = $("#searchsel").val();
											var text = $("#searchText").val();
											location.href = "sellcontrol?command=search&page=1&searchValue="+searchValue+"&text="+text;
										}
									});
									
									$("#categoryBar").on("change", function() {
										var categoryValue = $("#categoryBar").val();
										if(categoryValue == "카테고리"){
											alert("값을 다시 선택 해 주세요");
										}else if(categoryValue=="전체보기"){
											location.href = "sellcontrol?command=list&page=1";
										}else if(categoryValue=="-----"){
											alert("값을 다시 선택 해 주세요");
										}else{
										location.href = "sellcontrol?command=searchCategory&page=1&categoryValue="+categoryValue;
										}
									});
								});
							</script>
				<!-- </section> -->

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
			<script src="./main/js/jquery.min.js"></script>
			<script src="./main/js/jquery.dropotron.min.js"></script>
			<script src="./main/js/browser.min.js"></script>
			<script src="./main/js/breakpoints.min.js"></script>
			<script src="./main/js/util.js"></script>
			<script src="./main/js/main.js"></script>

	</body>
</html>