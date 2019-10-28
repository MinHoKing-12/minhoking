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
		<link rel="shortcut icon"  href="main/images/favicon.ico">
	<style type="text/css">
	a {
		border-bottom: none;
	}
	
	ul.links li.active {
		font-weight: 900;
	}
	
	#category {
		width : 140px;
		float: right;
	}
	.tabs{
	  list-style: none;
	  width: 400px;
	  margin: 0;
	  padding: 0;
	  float: left;
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
	
	/* search */
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
	
	/* Modal Css */
	.modal {
		display: none; /* Hidden by default */
		position: fixed; /* Stay in place */
		z-index: 1; /* Sit on top */
		left: 0;
		top: 0;
		width: 100%; /* Full width */
		height: 100%; /* Full height */
		overflow: hidden; /* Enable scroll if needed */
		background-color: rgb(0,0,0); /* Fallback color */
		background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
	}
	
	/* Modal Content/Box */
	.modal-content {
		background-color: #fefefe;
		margin: 10% auto; /* 10% from the top and centered */
		padding: 20px;
		border: 1px solid #888;
		width: 50%; /* Could be more or less, depending on screen size */                          
	}
	/* The Close Button */
	.close {
		color: #aaa;
		float: right;
		font-size: 28px;
		font-weight: bold;
	}
	.close:hover, .close:focus {
		color: black;
		text-decoration: none;
		cursor: pointer;
	}

	table {
		border: none;
		border-top: 1px solid #820024;
		border-collapse: collapse;
	}
	th,td {
		border: none;
		padding: 5px;
		border-bottom: 1px solid #820024;
		font-size: 15px;
	}
	th {
		background-color: #820024;
		color: white;
	}
	
	#send_btn {
		background-color: #820024;
		color: white;
		cursor: pointer;
		border-radius: 5px;
		border: 0;
		width: 100%;
		height: 50px;
	}
	
	#send_btn:disabled {
		background-color: #999999;
	}
	
	input[type=text], textarea {
		padding: 5px;
		font-size: 15px;
	}
	
	/* check msg */
	#_to_nick {
		position: reletive;
		display: inline-block;
	}
	.checkMsg {
		margin-top: 2px;
	 	display: none;
		position: absolute;
		background-color: #fff;
		border: 2px solid #c80000;
		font-size: 12px;
		font-weight: bolder;
		color: #c80000;
		box-shadow: 2px 2px 2px #aaaaaa;
		border-radius: 3px;
		z-index: 1;
		padding: 3px 10px;
	}
	.check-result {
		margin: 0;
	}
	/* paging anchor */
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
								<li class="active"><a href="mypagecontrol?command=mymsgbox">> 나의 쪽지함</a></li>
								<li><a href="mypagecontrol?command=myfreebbs">나의 게시글</a></li>
								<li><a href="mypagecontrol?command=myfreelike">좋아요한 게시글</a></li>
								<li><a href="mypagecontrol?command=myinfo">회원정보 수정</a></li>
								<li><a href="mypagecontrol?command=member-withdrawal">회원탈퇴</a></li>
							</ul>
						</section>

					</div>
				</div>
				<div class="col-8  col-12-narrower imp-narrower">
					<div id="content">
						<div id = "tab-bar">
							<ul class="tabs">
								<li class="current" id="receive">받은쪽지함</li>
								<li id="send">보낸쪽지함</li>
							</ul>
							<div id="category">
								<select id="read-sort" style="width: 140px;">
									<option id="total" value="total">전체</option>
									<option id="read" value="read">읽음</option>
									<option id="not-read" value="not-read">안읽음</option>
								</select>
							</div>
						</div>
						
						<div>
							<form id="recv-msg-form" method="post">
							<input type="hidden" name="command" value="multidelmsg">
							<input type="hidden" name="type" value="receive">
							<table>
							<col width=400><col width=150><col width=150><col width=100><col width=50>
							<tr class="tb_head">
								<th>내용</th>
								<th>보낸이</th>
								<th>수신일</th>
								<th>수신확인</th>
								<th align="center"><input type="checkbox" id="recv-multidel"></th>
							</tr>
							<%
							List<MessageDto> list = (List<MessageDto>)request.getAttribute("msgList");
							int totalPages = (int)request.getAttribute("totalPages");
							int currentPage = (int)request.getAttribute("page");
							String select = (String)request.getAttribute("select");
							String str = (String)request.getAttribute("searchText");
							String category = (String)request.getAttribute("category");
							
								if(list == null || list.size() == 0) {
									%>
									<tr>
									<td colspan="5" align="center">받은 메세지가 없습니다</td>
									</tr>
									<%
								}else {
									for(MessageDto msg : list){
									%>
									<tr>
										<td onclick="showDetail(this)" style="cursor: pointer;">
											<input type="hidden" value=<%=msg.getSeq() %>>
											<input type="hidden" value="<%=msg.getContent() %>">
											<input type="hidden" value="<%=msg.getFrom_nick() %>">
											<input type="hidden" value="<%=msg.getTo_nick() %>">
											<input type="hidden" value="<%=msg.getWdate() %>">
											<%=utilMethod.msgContent(msg.getContent()) %>
										</td>
										<td align="center"><%=msg.getFrom_nick() %></td>
										<td align="center"><%=utilMethod.dateEdit(msg.getWdate()) %></td>
										<td align="center">
										<%
										if(msg.getRead() == 0) {
											%>
											읽지않음
											<%
										}else {
											%>
											읽음
											<%
										}
										%>
										</td>
										<td align="center"><input type="checkbox" class="recv-delcheck" name="delcheck" value=<%=msg.getSeq() %>></td>
									</tr>
									<%
									}
								}
								%>
								<tr>
									<td colspan="5" align="right">
										<button type="button" id="msgwrite-btn">쪽지작성</button>
										<button type="button" id="recv-delbtn">쪽지삭제</button>
									</td>
								</tr>
								<tr>
									<td colspan="5" align="center">
									<%
									for(int i = 1; i <= totalPages; i++){
										if(str != null && !str.equals("")) {
										%>
										<a class="paging" href="mypagecontrol?command=mymsgbox&type=receive&page=<%=i %>&search-select=<%=select %>&search-text=<%=str %>"><%=i %></a>
										<%
										}else if(category != null) {
										%>
										<a class="paging" href="mypagecontrol?command=mymsgbox&type=receive&page=<%=i %>&category=<%=category %>"><%=i %></a>
										<%
										}
										else {
										%>
										<a class="paging" href="mypagecontrol?command=mymsgbox&type=receive&page=<%=i %>"><%=i %></a>
										<%
										}
									}
									%>
									</td>
								</tr>
								</table>
								</form>
								<div class="search-div">
									<form id="search-form" action="mypagecontrol">
										<input type="hidden" name="command" value="mymsgbox">
										<input type="hidden" name="type" value="receive">
										<select name="search-select">
											<option value="id">닉네임</option>
											<option value="content">내용</option>
										</select>
										<input type="text" name="search-text">
										<input type="submit" id="search-btn" value="검색">
									</form>
								</div>
							</div>
						</div>
							
							
						<!-- Write Modal -->
						<div id="write-modal" class="modal">
							<div class="modal-content">
								<span class="close">&times;</span>
								<%
								String to_nick = (String)request.getAttribute("to_nick");
								System.out.println(to_nick);
								%>
								<h2>쪽지 작성</h2>
								<input type="hidden" name="from_nick" value="<%=user.getNickname() %>">
								<input type="hidden" id="write-command" name="command" value="writemsgAf">
								<table>
								<tr>
									<th>보낸사람</th>
									<td><%=user.getNickname() %></td>
								</tr>
								<tr>
									<th>받을사람</th>
									<td>
										<input type="text" id="_to_nick" name="to_nick">
										<div id="nick-check" class="checkMsg">
											<span class="check-result"></span>
										</div>
									</td>
								</tr>
								<tr>
									<th>내용</th>
									<td>
										<textarea cols="30" rows="10" id="_content" name="content" placeholder="내용을 입력해주세요"></textarea>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<button type="button" id="send_btn" disabled="disabled">보내기</button>
									</td>
								</tr>
								</table>
							</div>
						</div>
						
						<!-- Detail Modal -->
						<div id="detail-modal" class="modal">
							<div class="modal-content">
								<span class="close">&times;</span>
								<h2>받은 쪽지 읽기</h2>
								<table>
								<tr>
									<th>보낸사람</th>
									<td id="detail-from_nick"></td>
								</tr>
								<tr>
									<th>작성일</th>
									<td id="detail-wdate"></td>
								</tr>
								<tr>
									<th>내용</th>
									<td><textarea cols="30" rows="10" id="detail-content" readonly="readonly"></textarea></td>
								</tr>
								<tr>
									<td colspan="2" align="right">
										<button type="button" id="reply_btn">답장하기</button>
										<button type="button" id="delete_btn" >쪽지삭제</button>
									</td>
								</tr>
								</table>
								
								<!-- delete form -->
								<form id="delete-frm">
									<input type="hidden" name="command" value="deletemsg">
									<input type="hidden" name="delete-seq">
									<input type="hidden" name="type" value="receive">
								</form>
								
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
				location.href="mypagecontrol?command=mymsgbox&type=" + type;
			});
			
			// 수신여부 셀렉트박스 선택 시
			$("#read-sort").on('change', function () {
			//	alert($(this).val());
				category = $(this).val();
				location.href="mypagecontrol?command=mymsgbox&category=" + category;
			});
			
			// 수신여부 선택된 값이 셀렉트박스에 선택되있도록 하기
			var sel_category = "<%=category %>";
			if(sel_category != null) {
				for(var i = 0; i < $("#read-sort").children().length; i++) {
					if($("#read-sort").children().eq(i).val() == sel_category) {
						$("#read-sort").children().eq(i).attr("selected", "selceted");
						break;
					}
				}
			}
			
			
			$("#msgwrite-btn").on('click', function () {
				$("#write-modal").css("display", "block");
			});
			$(".close").on("click", function () {
				$(".modal").css("display", "none");
				$("#_to_nick").val("");
				$("textarea[name=content]").val("");
			});
			
			// current page
			var currentPage = <%=currentPage %>
			var totalPages = <%=totalPages %>
			
			for (var i = 1; i <= totalPages; i++) {
				if(i == currentPage) {
					$(".paging").eq(i-1).addClass("current");
				}
			}
			
			// msg-wrtie script
			var nickcheck = true;
			var contentcheck = true;
			
			$("#_to_nick").on('blur', function () {
				
				var to_nick = $("#_to_nick").val();
				
				if(to_nick == "") {
					$(".check-result").text("닉네임을 입력해주세요");
					$(".checkMsg").css("display", "block");
					nickcheck = false;
					return;
				}
				
				$.ajax({
					url: "memcontrol?command=nickcheck",
					data: { nickname:to_nick },
					type: "GET",
					dataType: "text",
					async: false,
					success: function ( data ) {
						if(data.trim() == "false") {
							$(".check-result").text("존재하지 않는 닉네임입니다");
							nickcheck = false;
						}else {
							nickcheck = true;
						}
					},
					error: function () {
						alert("error");
					}
				});
				
				if(!nickcheck) {
					$(".checkMsg").css("display", "block");
				}else {
					$(".checkMsg").css("display", "none");
					
					if(contentcheck && nickcheck) {
						$("#send_btn").removeAttr("disabled");
					}else {
						$("#send_btn").attr("disabled", "disabled");
					}
				}
			});
			
			$("#_content").on('keyup', function() {
				var content = $(this).val();
				if(content.trim() == "") {
					contentcheck = false;
				}else {
					contentcheck = true;
				}
				
				if(contentcheck && nickcheck) {
					$("#send_btn").removeAttr("disabled");
				}else {
					$("#send_btn").attr("disabled", "disabled");
				}
			});
			
			
			// 메세지작성 Ajax
			$("#send_btn").on('click', function () {
				var command = $('#write-command').val();
				var from_nick = $('input[name=from_nick]').val();
				var to_nick = $("input[name=to_nick]").val();
				var content = $("textarea[name=content]").val();
				
			//	alert(content);
				
				$.ajax({
					url: "mypagecontrol",
					type: "POST",
					data: { command:command, from_nick:from_nick, to_nick:to_nick, content:content },
					dataType: "text",
					success: function ( data ) {
					//	alert( data );
						if(data.trim() == "true"){
							alert("성공적으로 쪽지가 전송되었습니다.");
							$("input[name=to_nick]").val("");
							$("textarea[name=content]").val("");
							$(".modal").css("display", "none");
						}else if(data.trim() == "false"){
							alert("쪽지쓰기를 실패했습니다. 다시 시도해주세요");
						}
					},
					error: function () {
						alert("error");
					}
				});
			});
			
			$("#recv-multidel").click(function () {
				if($("#recv-multidel").prop("checked")) {
					$("input:checkbox[class='recv-delcheck']").prop("checked", true);
				}else if(!$("#recv-multidel").prop("checked")) {
					$("input:checkbox[class='recv-delcheck']").prop("checked", false);
				}
			});
			
			// Multi-delete
			$("#recv-delbtn").click(function () {
				if(!checkFunc("recv-delcheck")) {
					alert("선택한 항목이 없습니다");
					return;
				}
				
				$("#recv-msg-form").attr("action", "mypagecontrol").submit();
			});
			
			// 답장하기 버튼
			$("#reply_btn").click(function () {
				$("#detail-modal").css("display", "none");	// 디테일창 끄고
				$("#_to_nick").val($("#detail-from_nick").text());	// 디테일창의 from_nick을 write창의 to_nick으로
				$("#write-modal").css("display", "block");	// 글쓰기창 키자
			});
			
			// 삭제하기 버튼
			$("#delete_btn").click(function () {
				if(confirm("쪽지를 삭제하시겠습니까?")) {
					$("#delete-frm").attr("action", "mypagecontrol").submit();
				}
				
			});
			
		});
		
		
		function checkFunc( checkbox ) {
			var delCheckbox = document.getElementsByClassName( checkbox );
			var count = 0;
			for (var i = 0; i < delCheckbox.length; i++) {
				if(delCheckbox[i].checked){
					count++;
				}
			}
			
			return count>0?true:false;
		}
		
		// detail-modal 띄우기
		function showDetail( obj ) {
			var seq = obj.childNodes[1].value;
			var content = obj.childNodes[3].value;
			var from_nick = obj.childNodes[5].value;
			var to_nick = obj.childNodes[7].value;
			var wdate = obj.childNodes[9].value;
			
			// 받은 쪽지함에서 읽을 경우에만 read = 1로 변경
			$.ajax({
				url: "mypagecontrol?command=msgread",
				type: "GET",
				data: {seq:seq},
			});
			
			$("#detail-from_nick").text(from_nick);
			$("#detail-wdate").text(wdate);
			$("#detail-content").val(content);
			$("input[name=delete-seq]").val(seq);
			
			$("#detail-modal").css("display", "block");
			
		}
		
		</script>

</body>
</html>