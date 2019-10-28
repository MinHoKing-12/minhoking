<%@page import="member.MemberDto"%>
<%@page import="java.util.List"%>
<%@page import="votes.VoteDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>KIWOOM SUPPORTERS - 투표게시판</title>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<link rel="shortcut icon"  href="main/images/favicon.ico">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="main/css/main.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">

#img {
	cursor: pointer;
}

#img:hover {
	box-shadow: 1px 1px 3px 3px #560018;
	border-radius: 50%;
}
.modal-dialog.modal-fullsize {
	width: 100%;
	height: 100%;
	margin: 0;
	padding: 0;
}

.modal-content.modal-fullsize {
	height: 100%;
	min-height: 100%;
	width: 100%;
	border-radius: 0;
}

#grabtn {
	padding-left: 15em;
}

.big {
	transform: scale(1);
	-webkit-transform: scale(1);
	-moz-transform: scale(1);
	-ms-transform: scale(1);
	-o-transform: scale(1);
	transition: all 0.3s ease-in-out;
}

.big:hover {
	transform: scale(1.1);
	-webkit-transform: scale(1.1);
	-moz-transform: scale(1.1);
	-ms-transform: scale(1.1);
	-o-transform: scale(1.1);
}

.img {
	width:  230px;
	height: 230px;
	overflow: hidden
}

select {
width: 150px;
padding: .1em .3em;
border: 2px solid #820024;
font-size: 15px;
font-family: inherit;
background: url('./vote/arrow.png') no-repeat 95% 50%;
border-radius: 0px;
-webkit-appearance: none;
-moz-appearance: none;
appearance: none;
}

.search-box {

	border: 1px solid #333;
    outline: 0;
    border-radius: 30px;
}
.search-box:focus {
	border: 2px solid #820024;

</style>
</head>

<body class="is-preload">
	<div id="page-wrapper">

		<!-- Header -->
		<div id="header">

			<!-- My Menu Bar -->
			<%
			    System.out.println("뷰 들어왔다");
				List<VoteDto> list = (List<VoteDto>) request.getAttribute("filename");
				//select박스 고정시켜주기위한 val
				String val = (String) request.getAttribute("val");

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
					<li><a href="./sellcontrol?command=list&page=1">거래 게시판</a></li>
					<li><a href="./freeControl?command=list">자유 게시판</a></li>
					<li class="current"><a href="./VoteControl?command=start">투표 게시판</a></li>
					<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank">경기 하이라이트</a></li>
				</ul>
			</nav>

		</div>


		<!-- main -->
		<img  style="width:100%; height:100%;" src="./vote/voteban.png">
		<div align="center">
				<div style="font-size: 13px; font-family: sans-serif; font-weight: bold; color: darkred;" align="center">선수에게 투표하시면 [mvp선수찾기] 이벤트에 자동으로 응모됩니다, 당첨확인은 '마이페이지' 쪽지함에서 확인할 수 있습니다.</div>
				<br><br>
                    
				<div class="selectbox" align="right">
					<select id="select">
						<option value="이름순" <c:if test="${val eq '이름순'}">selected</c:if>>이름순</option>
						<option value="랭킹순" <c:if test="${val eq '랭킹순'}">selected</c:if>>랭킹순</option>
					</select> <input style="min-width: 0em; min-height:0em; padding: 0em .5em 0 .5em; font-size: 13px; margin-right: 30px;" id="choice" type="button" value="선택">
				</div>

				<script type="text/javascript">
					$(function() {

						$("#choice").click(function() {
                              if ($("#select").val() == ("이름순")) {
                            	  location.href = "VoteControl?command=list&val=이름순"
                            			  } else if ($("#select").val() == ("랭킹순")) {
                            				  location.href = "VoteControl?command=rank&val=랭킹순"
                            						  }
                              });
						});
				</script>
				<br>

				<div align="left" style=" margin-left: 3px" id="grabtn">

					<input type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#myFullsizeModal" value="투표결과 한눈에 그래프보기">
				</div>
				
                <div class="search-wrapper" align="center">
				<input style="font-size:14px; padding: .2em; width: 300px; height: 30px; display: inline-flex;" type="text" name="focus" class="search-box" id="search"  placeholder="내선수 검색">
				<input  style="min-width: 0em; min-height:0em; padding: 0em .5em 0 .5em;  display: inline-flex; font-size: 13px;" type="button" id="searchbtn" value="검색">
				</div>
				
				<script type="text/javascript">
					$(function() {
						$("#searchbtn").click(function() {

							location.href = "VoteControl?command=search&name="+ $("#search").val();
							});
						});
				</script>
				<br><br>

				<%
				//사진 정렬 테이블 만들어주기 
					//테이블 행 정해주기 
					int rows = 0;
					//테이블 행개수 5개씩 자르기
					if (list.size() % 5 == 0) {
						rows = list.size() / 5;
					} else {
						rows = list.size() / 5 + 1;
					}
					
					String name = "";
					String filename = "";
					String position = "";
					int height = 0;
					int weight = 0;
					int seq = 0;
					int likes = 0;
				%>
				<table style="width: 60%;">
					<%for (int j = 0; j < rows; j++) {%>
					<tr>
						<%for (int i = j * 5; i < j * 5 + 5; i++) //인덱스넘버 0~4 , 5~10
						{
							if (i < list.size()) {
						%>
						<td align="center" style="padding: 20px">
							<%
								filename = "/baseball/";
											filename += list.get(i).getPhotoname();

											name = list.get(i).getName();
											seq = list.get(i).getSeq();
											likes = list.get(i).getLikes();
											
							%> <!-- 이미지를 클릭했을때  '컨트롤러'로 seq랑  command 넘겨주기 -->
							<div class="img">
								<div class="big">
									<img style="width: 230px; height: 230px;" name="img"  id="img"  src="<%=request.getContextPath() + filename%>"
									          onclick="location.href='VoteControl?command=vote&seq=<%=seq%>'">
								</div>
							</div> 
							<label><%=name%></label>
							<img style="width: 15px; height: 15px;" src="./baseball/h.png"> <label><%=likes%></label> 
							<input type="hidden" name="seq" value=<%=seq%>> 

						</td>

						<%
							//사진 자료가없을때는 빈칸인 테이블로 부르기
						} else {
						%>
						<td></td>
						<%	}	} %>
					</tr>
					<%
					}
					%>
				</table>

		</div>

		<!-- Footer -->
		<div id="footer">

			<!-- Icons -->
			<ul class="icons">
				<li><a
					href="https://twitter.com/hashtag/%ED%82%A4%EC%9B%80%ED%9E%88%EC%96%B4%EB%A1%9C%EC%A6%88"
					target="_blank" class="icon brands fa-twitter"><span
						class="label">Twitter</span></a></li>
				<li><a href="https://www.facebook.com/kiwoomheroesbaseballclub"
					target="_blank" class="icon brands fa-facebook-f"><span
						class="label">Facebook</span></a></li>
				<li><a href="https://www.instagram.com/heroesbaseballclub"
					target="_blank" class="icon brands fa-instagram"><span
						class="label">Instagram</span></a></li>
				<li><a href="https://www.youtube.com/user/heroesbaseballclub"
					target="_blank" class="icon brands fa-youtube"><span
						class="label">Youtube</span></a></li>
			</ul>

			<!-- Copyright -->
			<div class="copyright">
				<ul class="menu">
					<li>&copy; Untitled. All rights reserved</li>
					<li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
				</ul>
			</div>
		</div>


		<!-- Scripts -->
		<script src="main/js/jquery.min.js"></script>
		<script src="main/js/jquery.dropotron.min.js"></script>
		<script src="main/js/browser.min.js"></script>
		<script src="main/js/breakpoints.min.js"></script>
		<script src="main/js/util.js"></script>
		<script src="main/js/main.js"></script>

		<div class="modal fade" id="myFullsizeModal" tabindex="-1"
			role="dialog" aria-labelledby="myFullsizeModalLabel">
			<div class="modal-dialog modal-fullsize" role="document">
				<div class="modal-content modal-fullsize">

					<!-- Modal Header -->
					<div class="modal-header">
						<h2 class="modal-title">투표결과 그래프</h2>

					</div>

					<!-- Modal body -->
					<div class="modal-body">

						<iframe style="width: 98vw; height: 30vw;" class="myFrame"
							src="VoteControl?command=result"></iframe>

					</div>

					<div align="center">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>

					</div>
				</div>
			</div>
		</div>

	</div>

</body>
</html>









