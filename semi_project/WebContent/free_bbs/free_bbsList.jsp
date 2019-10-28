<%@page import="free_bbs.free_bbs_commentDao"%>
<%@page import="member.MemberDto"%>
<%@page import="free_bbs.free_bbsDto"%>
<%@page import="free_bbs.free_bbsDao"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String LoginId = "";
String LoginNick = "";

MemberDto user = (MemberDto)session.getAttribute("login");
if(user == null){
	
}else if(user!=null){
	LoginId = user.getId();
	LoginNick = user.getNickname();
} 

%>


<%
free_bbs_commentDao commentDao = free_bbs_commentDao.getInstance();
List<free_bbsDto> list = (List<free_bbsDto>)request.getAttribute("free_bbsList");
System.out.println("리스트 사이즈 : " + list.size());

int len = (int)request.getAttribute("allPosting");
System.out.println("현재페이지 리스트 갯수: " + len);


int bbsPage = len / 10;
if(len % 10 > 0){
bbsPage = bbsPage + 1;
}


int pageNumber = 1;
if(request.getAttribute("pageNumber") != null){
	pageNumber = (int)(request.getAttribute("pageNumber"));
	System.out.println("현재 자유게시판 pageNumber는 " + pageNumber);
}
 
String searchText = "";
if(request.getAttribute("searchText") != null){
	searchText = (String)request.getAttribute("searchText");
	System.out.println("현재 자유게시판 searchText는 " + searchText);
}
String choice = "";
if(request.getAttribute("choice") != null){
	choice = (String)request.getAttribute("choice");
	System.out.println("현재 자유게시판 choice는 " + choice);
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
		<link rel="stylesheet" href="main/css/main.css">
		<link rel="shortcut icon"  href="main/images/favicon.ico">
		<link href="free_bbs/freecss/global.css" rel="stylesheet" type="text/css" />
<style type="text/css">

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
    background-color: #5f0e0e;
    background-image: url(images/bg01.png);
}
select {
width: 90px;
height: 35px;
padding: .1em .3em;
border: 2px solid #820024;
font-size: 15px;
font-family: inherit;
background: url('./free_bbs/arrow.png') no-repeat 95% 50%;
border-radius: 0px;
-webkit-appearance: none;
-moz-appearance: none;
appearance: none;


}
</style>

</head>
<body>

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
				<a href="./memcontrol?command=main" id="logo"><img src="./main/images/ks.png" style="width: 400px" ></img></a><br><br>

			<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a href="./memcontrol?command=main">Home</a></li>
						<li><a href="./calendarcontrol?command=calendar">경기 일정</a></li>
						<li><a href="./sellcontrol?command=list&page=1">거래 게시판</a></li>
						<li class="current"><a href="./freeControl?command=list">자유 게시판</a></li>
						<li><a href="./VoteControl?command=start">투표 게시판</a></li>
						<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank">경기 하이라이트</a></li>
					</ul>
				</nav>
		</div>
	<!-- Main -->
	<img  style="width:100%; height: 100%;" alt="bb" src="images/free_bbs/freeban.png">
	<br><br>
		<!-- <section class="wrapper style1"> -->
			<div class="container">
				<div id="content">
			

<div align="center">

<table border="1" class="type01">
<col width="100"><col width="430"><col width="150"><col width="200"><col width="80"><col width="80">
<tr>
   <th>글번호</th>
   <th>제목</th>
   <th>닉네임</th>
   <th>작성 날짜</th>
   <th>조회수</th>
   <th>추천수</th>
</tr>

<%
if(list == null || list.size() == 0){  //글이 하나도 없을때
   %>
   <tr>
      <td colspan="6">작성된 글이 없습니다</td>
   </tr>
   <%
}else{
   for(int i = 0; i < list.size(); i++){
	   free_bbsDto bbs = list.get(i); 
	   
   %>
   
   <tr>
      <%-- <th align="center"><%=bbs.getSeq() %></th> --%>
      <th align="center"><%=i+1%></th> 
       <td class="seqclick" >
         <%if(commentDao.getCommentListCnt(bbs.getSeq())!=0){ %>        
        	 <a style="cursor: pointer"><%=bbs.getTitle() %>&nbsp;&nbsp;&nbsp;<img src='images/free_bbs/reply.png' width='23' height='23'>
        	 	<font size="3em" color=" #990000"><b><%=commentDao.getCommentListCnt(bbs.getSeq()) %></b></font></a>
         <%}else{ %>
         	 <a style="cursor: pointer"><%=bbs.getTitle() %></a>
         <%} %>
         
         <input type='hidden' value='<%=bbs.getSeq() %>'>
       </td> 
      
      <td align="center"><%=bbs.getNickname() %></td>
      <td align="center"><%=bbs.getWdate() %></td>
      <td align="center"><%=bbs.getReadcount() %></td>
      <td align="center"><%=bbs.getBlike() %></td>
      
   <%  }  
  
}
%>

</table>
<!--to get detail  -->
<form action="freeControl" id="detailFrm" method="get">
	<input type = "hidden" id="seqVal" name="seqVal">		
	<input type = "hidden" name="command" value="forDetail">
</form>
<!--to write  -->
<form action="freeControl" id="writeFrm" method="get">
	<input type = "hidden" name="command" value="forWrite">
</form>
<!--to search -->
<form action="freeControl" id="searchFrm" method="get">
	<input type = "hidden" name="command" value="list">
	<input type = "hidden" id="searchChoice" name="searchChoice" value="">	<!-- search selected choice-->
	<input type = "hidden" id="searchText" name="searchText" value="">		<!-- search text -->
</form>
<%-- <%if((pageNumber) != 1) {%>
<a class="pagebutton" href="./freeControl?command=list&pageNumber=<%=pageNumber-1 %>" class="pageBtn"><b>before</b></a>
&nbsp;

<%} %>
<%if(pageGroup==1){ %>
	<% for(int i=pageGroup; i<=5; i++){%>			<!--pagegroup * 5  -->		 <!-- 집에 가서  -->
	
		<a href="./freeControl?command=list&pageNumber=<%=i%>"><%= i%></a>	

	<% } %>
<% }else if(pageGroup!=1){ %>
<% for(int i=pageSize*(pageGroup-1); i<=(pageGroup*5); i++){%>			<!--pagegroup * 5  -->		 <!-- 집에 가서  -->
	
		<a href="./freeControl?command=list&pageNumber=<%=i%>"><%= i%></a>	

	<% } %>
<% } %>	

&nbsp;
<%if(list.size() == 20) {%>
	<%if(pageNumber%5 == 0) {%>
		<!-- <a class="nextPageGroup" onclick="nextPage()">after2</a> -->
		<a class="pagebutton" href="./freeControl?command=list&pageNumber=<%=pageNumber+1 %>&pageGroup=<%=pageGroup+1 %>" class="pageBtn"><b>after2</b></a>
	<%}else{ %>
		<a class="pagebutton" href="./freeControl?command=list&pageNumber=<%=pageNumber+1 %>" class="pageBtn"><b>after</b></a>
	<%} %>
<%} %>	 --%>

<%-- <%if((pageNumber) != 1) {%>
<a class="pagebutton" href="./freeControl?command=list&pageNumber=<%=pageNumber-1 %>" class="pageBtn"><b>before</b></a>
&nbsp;
<%}%> --%>

<% 
for(int i = 0;i < bbsPage; i++){
%>
	<a href="freeControl?command=list&pageNumber=<%=i%>" title="<%=i+1 %>페이지" 
	style="font-size: 15pt; color: #990000; font-weight: bold;">[<%=i+1 %>]</a>&nbsp;
<%}%>

<br><br>

<select id="selForsearch" style='display : inline-flex;' >
	<option id="selId" value="nickname" <%if(choice.equals("nickname")){ %>selected <%} %>selected >닉네임</option>
	<option id="title" value="title" <%if(choice.equals("title")){ %>selected <%} %>>제목</option>
	<option id="content" value="content" <%if(choice.equals("content")){ %>selected <%} %>>내용</option>
</select> &nbsp;
<input type="text" id="TextforSearch" style="width:340px; height:36px; display : inline-flex;" value="<%=searchText %>" >
<button type="button" id="searchBtn" '>검색</button>
 	
 <% if(!LoginId.equals("")) {		// 닉네임값 존재하면 글쓰기 버튼 생성
	 %>
	 <button type="button" id="writeBtn" onclick="writeBbs()">글쓰기</button>
<%} %>  
 
 <br><br><br>
</div>

<!-- </section> -->



<script type="text/javascript">

//★search
$(document).ready(function(){
	
	$('#searchBtn').on('click',function(){
		var choice = $("#selForsearch").val();	// id? or title? or content?
		var searchText = $("#TextforSearch").val();
		//alert(choice);
		//alert(searchText);
		
		
		$('#searchChoice').val(choice);
		$('#searchText').val(searchText);
		
		$('#searchFrm').submit();
	});	
});

//★detail
$(document).ready(function(){
	
	$('.seqclick').on('click',function(){
		//alert($(this).find('input[type="hidden"]').val());
		var seqVal = $(this).find('input[type="hidden"]').val()
		
		$('#seqVal').val(seqVal);
		
		$('#detailFrm').submit();
	});	
});

// ★write
$(document).ready(function(){
	
	$('#writeBtn').on('click',function(){
		$('#writeFrm').submit();
	});	
});

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
		<script src="./main/js/jquery.min.js"></script>
		<script src="./main/js/jquery.dropotron.min.js"></script>
		<script src="./main/js/browser.min.js"></script>
		<script src="./main/js/breakpoints.min.js"></script>
		<script src="./main/js/util.js"></script>
		<script src="./main/js/main.js"></script>

</div>

</body>
</html>