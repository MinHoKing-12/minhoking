<%@page import="sellcomment.sell_comment_Dao"%>
<%@page import="sellbbs.sell_Dao"%>
<%@page import="sellcomment.sell_comment_Dto"%>
<%@page import="sellbbs.isell_Dao"%>
<%@page import="sellcomment.isell_comment_Dao"%>
<%@page import="util.utilMethod"%>
<%@page import="member.MemberDto"%>
<%@page import="java.util.List"%>
<%@page import="sellbbs.sell_Dto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	isell_comment_Dao comment_dao = sell_comment_Dao.getInstance();
    	sell_Dto dto = (sell_Dto)request.getAttribute("selldetail");
    	System.out.println("디테일 화면 dto: " +dto.toString());
    	int count = (int)request.getAttribute("count");
    	int page1 = (int)request.getAttribute("page");
    	System.out.println("count : " + count);
    	isell_Dao dao = sell_Dao.getInstance();
    	int seq = dto.getSeq();
    	
    	 List<sell_comment_Dto> commentDto = comment_dao.getSell_Comment_List(dto.getSeq());
    	List<sell_comment_Dto> list = comment_dao.getSell_Comment_List(seq, page1);
    	dao.readCount(seq);
    	
    	Object ologin = session.getAttribute("login");
    	MemberDto mem = (MemberDto)ologin;
    %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Sell Detail</title>
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="./main/css/main.css" />
		<link href="./sellcss/global.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon"  href="main/images/favicon.ico">
		<style type="text/css">

.article-image {
	position: relative;
    width: 1200px;
    margin: 0 auto;
}
input[type=button] {
	min-width: 0;
}
.empty {
	width: 800px;
   	border-top: 1px solid gray;
   	margin: 0 auto;
   	margin-bottom: 50px;
}
.photo img{
    width: 600px;
    position: relative;
    height: 500px;
    overflow: hidden;
    border-radius: 8px 8px 0 0;
    background-color: #f8f9fa;
}

.space-between {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
}
.card-price {
	font-size: 15px;
    font-weight: 600;
    padding-bottom: 8px;
    border-bottom: 1px solid #e9ecef;
    line-height: 1.3;
    color: #ff8a3d;
}
.divname {
	width: 800px;
	text-align: left;
}
.divefile {
	width: 80px;
	height: 100px;
	text-align : left;
	background-color: yellow;
}
.commentupdate{
	height: 18px;
	width: 18px;
}
.commentcomment {
	height: 18px;
	width: 18px;
}
.deleteComment {
	height: 18px;
	width: 18px;
}
.btnDown {
	width: 40px;
	height: 30px;
}
input[type=button] {
	padding: 0.5em;
	line-height: inherit;
	font-size: 15px;
}
.card-count {
    padding: 12px 0;
    color: #adb5bd;
    font-size: 13px;
}
.comment {
	font-size: 14px;
}
.imginner:hover {
	   cursor:pointer;
       transform:scale(1.1); 
      -o-transform:scale(1.1); 
      -moz-transform:scale(1.1);
      -webkit-transform:scale(1.1);
       transition: transform .35s;  
      -o-transition: transform .35s;
      -moz-transition: transform .35s;
      -webkit-transition: transform .35s;
}
.modal {
      display: none; /* Hidden by default */
      position: fixed; /* Stay in place */
      z-index: 1; /* Sit on top */
      left: 0;
      top: 0;
      width: 100%; /* Full width */
      height: 100%; /* Full height */
      overflow: auto; /* Enable scroll if needed */
      background-color: rgb(0,0,0); /* Fallback color */
      background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}
        /* Modal Content/Box */
.modal-content {
      background-color: #fefefe;
      margin: 15% auto;
      padding: 20px;
      border: 1px solid #888;
      width: 450px;
   	  height: 320px;
}
        /* The Close Button */
.close {
      color: #aaa;
      float: right;
      font-size: 28px;
      font-weight: bold;
      padding: 4px;
}
.close:hover, .close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
}
.modalTable table {
	border-top: 1px solid #820024;
	border-collapse: collapse;

}
.modalTable th{
	border: 1px solid #820024;
	padding: 5px;
}
.modalTable td {
	border: none;
	padding: 5px;
	border: 1px solid #820024;
	font-size: 16px;
	text-align: center;
}
.modalTable th, td{
	height: auto;
}
.tb_head th {
	background-color: #820024;
	color: white;
}
.myButton {
	-moz-box-shadow:inset 0px 0px 0px 0px #a6827e;
	-webkit-box-shadow:inset 0px 0px 0px 0px #a6827e;
	box-shadow:inset 0px 0px 0px 0px #a6827e;
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#7d5d3b', endColorstr='#634b30',GradientType=0);
	background-color:#820024;
	-moz-border-radius:13px;
	-webkit-border-radius:13px;
	border-radius:13px;
	border:2px solid #54381e;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:14px;
	padding:10px 21px;
	text-decoration:none;
	text-shadow:0px 2px 0px #4d3534;
}
.myButton:active {
	position:relative;
	top:1px;
}
	/* Modal Css */
.sendmodal {
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
.sendmodal-content {
		background-color: #fefefe;
		margin: 10% auto; /* 10% from the top and centered */
		padding: 20px;
		border: 1px solid #888;
		width: 50%; /* Could be more or less, depending on screen size */                          
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
				<section class="wrapper style1">
					<div class="container">
						<div id="content">

							<!-- Content -->
								
								 <div align="center" style="margin-top: 10px">
									<form id="frm">
									<!-- <input type="hidden" name="command" value="update"> -->
									
											<section class="article-images">
												<div class="photo">
												<img alt="물건팜" src="./upload/<%=dto.getFilename()%>" class="imginner">
												</div>
											<br>
											</section>
											<div class="divname">
											<div class="empty" style="padding-top: 20px">
												<input type="hidden" id="hidden_myBtn" value="<%=dto.getNickname() %>" onclick="memberInfo()">
												<span id="myBtn" onclick="memberInfo()"><%=dto.getNickname() %></span>
												<span style="float: right;"><%=dto.getCategory() %> · <%=dto.getPcategory() %> · <%=utilMethod.dateEdit(dto.getWdate())%></span>
												<div>
												<b><%=dto.getTitle() %></b>
												<span style="float: right;"><%=dto.getFilename() %></span>
												<input style="float: right; padding: 0;" type="button" value="파일" class="btnDown"
												 onclick="location.href='./filedown?filename=<%=dto.getFilename()%>&seq=<%=dto.getSeq()%>'">
											    <div class="card-price">
												<%=utilMethod.PriceConvert(dto.getPrice()) %>
												</div>
												</div>
												<div class="empty">
								  				</div>
												</div>
												<div class="divtext">
												<%if(dto.getComplete() == 0){ %>
								  				<span id="text" name="text" readonly="readonly" style="width: 100%"><%=dto.getContent() %></span>
								  				<%
												}else{
								  				%>
								  				<span id="text" name="text" readonly="readonly" style="width: 100%"><del>거래가 완료되었습니다.</del></span>
								  				<%
												}
								  				%>
								  				<br><br>
								  					<div class="card-count">
													조회<%=dto.getRcount() %> · 관심 <span id="likesnum"><%=dto.getAcount()%></span>  · 댓글 <%=commentDto.size()%>
													</div>
								  				<div class="empty">
								  				</div>
								  				</div>
											</div>
											<br>
											<input type="button" id="backBtn" value="돌아가기">
									<%
									System.out.println("삭제번호 : " + dto.getComplete());
									if(mem.getNickname().trim().equals(dto.getNickname().trim()) && dto.getComplete() == 0){		/* 닉넴 == 세션 닉넴 */
									%>
										<input type="button" id="upBtn" value="수정" onclick="update1()">
										<input type="button" id="delBtn" value="삭제" onclick="delete1()">
										<input type="button" id="likeBtn" value="관심" onclick="like1()">
										<input type="button" id="completeBtn" value="거래완료" onclick="complete1()">
									<%
									}else if(dto.getComplete() == 0){
									%>
										<input type="button" id="likeBtn" value="관심" onclick="like1()">
									<%
									}
									%>
									</form>
									</div>
									<br>
									<div align="center">
									<table border="1" style="width: 860px" class="replay">
									<col width="110"><col width="400"><col width="100">
									<tr>
										<th>닉네임</th>
										<th>내용</th>
										<th>작성시간</th>
									</tr>
										<%
											for(int i = 0; i < list.size(); i++){
												sell_comment_Dto comment_dto = list.get(i);
												if(comment_dto.getDel() == 0){
										%>
									<tr>
										<td align="center"><%=comment_dto.getNickname() %></td>
										<td>
												<span style="text-align: right">
												<%=utilMethod.arrow(comment_dto.getDepts()) %><%=comment_dto.getContent() %>
												<input type="hidden" value="<%=comment_dto.getSeq() %>" class="commentSeq">
												<%if(mem.getNickname().equals(comment_dto.getNickname())){%>
												<input type="hidden" value="<%=comment_dto.getSeq() %>" class="commentSeq">
												<%-- <a href=sellcontrol?command=commentDelete&commentseq=<%=comment_dto.getSeq()%>&bbsseq=<%=dto.getSeq()%>>삭제</a> --%>
												<img alt="삭제" src="./sellbbsimage/002-cancel.png" class="deleteComment"
												onclick="location.href='sellcontrol?command=commentDelete&commentseq=<%=comment_dto.getSeq()%>&bbsseq=<%=dto.getSeq()%>'">
												<img alt="수정" src="./sellbbsimage/revice.png" class="commentupdate">
												<!-- <input type="button" class="commentupdate" value="수정"> -->
												<%}%>
												<!-- <input type="button" class="commentcomment" value="답글쓰기"> -->
												<img alt="댓글" src="./sellbbsimage/edit.png" class="commentcomment">
												<div class="commentAdd2">
												<textarea rows="1" cols="5" name="comment2Text" class="comment2Text" id="comment2Text" maxlength="60"></textarea>
												<input type="button" value="작성 완료" class="commentwrite2">
												</div>
												</span>
										</td>
										<td><%=utilMethod.dateEdit(comment_dto.getDate()) %></td>
									</tr>
									<%
											}else if(comment_dto.getDel() == 1){
												%>
												<tr>
										<td><%=comment_dto.getNickname() %></td>
										<td>
												<span style="text-align: right">
												<%=utilMethod.arrow(comment_dto.getDepts()) %>삭제 된 댓글입니다.
												</span>
										</td>
										<%if(true){ %>	<!-- 로그인 세션과 현재 아이디 조건 달기 -->
										<td></td>
										
										<%} %> 
									</tr>
									<%	
											}	
										}
									%>
									<tr>
										<td colspan="3">
											<input type="button" id="commentBtn" value="댓글쓰기" style="width: 200px">
										</td>
									</tr>
									</table>
									</div>
									  <div align="center">
									    <% 
										for(int c=1; c < count + 1; c++){
										%>
										<a href="sellcontrol?command=detail&seq=<%=dto.getSeq() %>&page=<%=c %>"><%=c %></a>
										<%
										}
										%>
										</div>
									
									<div align="center" id="commentAdd">
									<table border="1" style="width: 800px; height: 10px">
									<col width="108px">
									<tr>
										<td>
											<textarea rows="1" cols="5" name="commentText" id="commentText" placeholder="글 입력 후 완료를 눌러주세요"></textarea>
											<input type="button" value="작성 완료" id="commentwrite">
										</td>
									</tr>
									</table>
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
		
		<div id="myModal" class="modal">
			<div class="modal-content">
				<span class="close">&times;</span>
			<table class="modalTable" style="font-size: 14px; height: 100px; width: 96%" >
				<tr>
					<th>등급</th>
					<td>
						<img id="classimg" alt="이미지없음" width=20px, height=20px><b id="grade"></b>
					</td>
				</tr>
				<tr>
					<th>ID</th>
					<td><span id="dtoid"></span></td>
				</tr>
				<tr>
					<th>닉네임</th>
					<td><span id="dtonickname"></span></td>
				</tr>
				<tr>
					<th>주소</th>
					<td><span id="dtoaddress"></span></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><span id="dtoemail"></span></td>
				</tr>
			</table>
			<div align="center">
			 <%if(mem.getNickname().equals(dto.getNickname())){ %>
			  <span style="font-size: 14px; color: gray" >같은 사람에게는 쪽지를 보낼 수 없습니다.</span>
			 <%
			 }else{
			 %>
			  <span class="myButton" id="msgwrite-btn">쪽지보내기</span>
			 <%
			 }
			 %>
			 </div>
			</div>
		</div>
		
				<!-- Write modal -->
		<div id="write-modal" class="sendmodal">
			<div class="sendmodal-content">
				<span class="close">&times;</span>
				<div align="center">
					<h2>쪽지 작성</h2>
					<input type="hidden" name="from_nick" value="<%=mem.getNickname() %>">
					<input type="hidden" id="write-command" name="command" value="writemsgAf">
					<table>
					<tr>
						<th>보낸사람</th>
						<td><%=mem.getNickname() %></td>
					</tr>
					<tr>
						<th>받을사람</th>
						<td>
							<input type="text" id="_to_nick" name="to_nick" value="<%=dto.getNickname()%>">
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
							<button type="button" id="send_btn">보내기</button>
						</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
		
		
		<!-- Scripts -->
			<script type="text/javascript">
			function delete1() {
				location.href = "sellcontrol?command=deletesell&page=1&seq=<%=dto.getSeq() %>";
			}
			function complete1() {
				location.href = "sellcontrol?command=complete&page=1&seq=<%=dto.getSeq() %>";
			}
			
			function like1() {
				var seqNum = <%=dto.getSeq() %>;
			//	alert(seqNum);
				$.ajax({
					url: "./sellcontrol?command=likes",
					type: "GET",
					data: {seq:seqNum},
					// 한개의 값이면 text, 2개이상 json받은 후 parse
					datatype: "json",
					success:function( obj ) {
							
							$("#likesnum").text(obj.trim());
					},
					error:function () {
						alert("에러");
					}
				});
				<%-- location.href = "sellcontrol?command=likes&page=1&seq=<%=dto.getSeq() %>"; --%>
			}
			
			function memberInfo() {
				
				var nickname = document.getElementById("hidden_myBtn").value;
			//	alert(nickname);
				$.ajax({
					url: "./sellcontrol?command=memberDetail",
					type: "GET",
					data: {nickname:nickname},
					datatype:"json",
					success:function ( obj ) {
					//	alert("성공");
						var data = JSON.parse(obj);
				//		alert(data.id);
						$("#dtoid").text(data.id.trim());
				//		alert(data.nickname);
						$("#dtonickname").text(data.nickname.trim());
				//		alert(data.address);
						$("#dtoaddress").text(data.address.trim());
				//		alert(data.email);
						$("#dtoemail").text(data.email.trim());
				//		alert(data.grade);
						$("#grade").text(data.grade.trim());
				//		alert(data.imgname);
						$("#classimg").attr("src","./images/grade/"+data.imgname.trim());
					},
					error:function(){
						alert("에러");
					}
				});
				modal.style.display = "block";
			}
			
			
			function update1() {
				location.href = "sellcontrol?command=updateSell&page=1&seq=<%=dto.getSeq() %>";
			}
			//JUQERY 시작
			$(function name() {
				
				// write modal
				$("#msgwrite-btn").on('click', function () {
					$("#myModal").css("display","none");
					$("#write-modal").css("display", "block");
				});
				$(".close").on("click", function () {
					$("#write-modal").css("display", "none");
				});
				
				// 쪽지 보내기 ajax
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
							if(data.trim() == "true"){
								alert("성공적으로 쪽지가 전송되었습니다.");
								$("input[name=to_nick]").val("");
								$("textarea[name=content]").val("");
								$("#write-modal").css("display", "none");
							}else if(data.trim() == "false"){
								alert("쪽지쓰기를 실패했습니다. 다시 시도해주세요");
							}
						},
						error: function () {
							alert("error");
						}
					});
				});
				
				var count = 1;
				$("#commentAdd").hide();
				$(".commentAdd2").hide();
				$("#backBtn").click(function () {
					location.href = "sellcontrol?command=list&page=1";
				});	
				
				$("#commentBtn").on("click", function() {
					if(count%2 == 1){
					$("#commentAdd").show();
					}else{
					$("#commentAdd").hide();
					}
					count++;
				});
				$("#commentwrite").click(function () {
					var content = $("#commentText").val();
					if(content.trim() == "" || content == null){
						alert("내용이 비었습니다.");
					}else{
						location.href = "sellcontrol?command=commentadd&content="+content+"&seq=<%=dto.getSeq() %>&id=<%=mem.getId() %>";
					}
				});
				
				$(".commentupdate").click(function name() { // 수정하기
					// 버튼의 상위 태그 찾기
					var tdTag = $(this).parent();	// 칸의 수정칸 눌렀을 경우 상위 태그 찾기
						/* 		
						alert(tdTag.html);			// tdTag 내용
						alert(tdTag.length);		// tdTag의 길이
						alert(tdTag.children());	// 상위 태그의 자식
						alert(tdTag.children().eq(0).val());	// td태그의 자식의 0번째 값
						 */
					tdTag.children(".commentAdd2").toggle('fast', function() {
						tdTag.children(".comment2Text").addClass("placeholder", "수정하실 내용을 입력하세요.");
				});
					
					$(".commentwrite2").click(function() {		//수정
						var content = tdTag.children().children().eq(0).val();	// 텍스트 페이지 값
						if(content.trim() == ""){

						}else{
						if(tdTag.children().length == 6){
						var commentSeq = tdTag.children().eq(0).val();			// 댓글 시퀀스 값
						}else if(tdTag.children().length == 7) {
						var commentSeq = tdTag.children().eq(1).val();
						}
						location.href = "sellcontrol?command=commentUpdate&content="+content+"&commentseq="+commentSeq+"&bbsseq=<%=dto.getSeq() %>";
						}
					});
				});
				
				$(".commentcomment").click(function () {
					var tdTag = $(this).parent();
				//	alert(tdTag.children().length);
				//	alert(tdTag.html());
					/* 
					if(count%2 == 1){
						tdTag.children(".commentAdd2").show();
					}else{
						tdTag.children(".commentAdd2").hide();
					}
					 */
					 // 나왔다 안나왔다가 하기.(토글 이용)
					 tdTag.children(".commentAdd2").toggle('fast', function() {
					 });
				//	var commentBtn = tdTag.children().children().eq(1).val();	// 버튼
					
					$(".commentwrite2").click(function () {						// 답글쓰기
						var content = tdTag.children().children().eq(0).val();	// 텍스트 페이지 값
						if(content == ""){
							alert("텍스트가 없습니다.");
						}else{
					//	alert(tdTag.children().length);							alert은 버그 나올 때 확인용 입니다.
						if(tdTag.children().length == 3){
						var commentSeq = tdTag.children().eq(0).val();
						}else if(tdTag.children().length == 4){
						var commentSeq = tdTag.children().eq(1).val();
						}else if(tdTag.children().length == 6){
						var commentSeq = tdTag.children().eq(0).val();			// 댓글 시퀀스 값
						}else if(tdTag.children().length == 7) {
						var commentSeq = tdTag.children().eq(1).val();
						}
					//	alert(content);
					//	alert(commentSeq);
						location.href = "sellcontrol?command=comment2add&content="+content+"&commentseq="+commentSeq+"&bbsseq=<%=dto.getSeq() %>";
						}
					});
					/* 
					
					if(count%2 == 1){
						$("#commentAdd2").show();
						}else{
						$("#commentAdd2").hide();
						} */
						count++;
				});
			});
			</script>
			<script src="./main/js/jquery.min.js"></script>
			<script src="./main/js/jquery.dropotron.min.js"></script>
			<script src="./main/js/browser.min.js"></script>
			<script src="./main/js/breakpoints.min.js"></script>
			<script src="./main/js/util.js"></script>
			<script src="./main/js/main.js"></script>
			<script type="text/javascript">
	        // Get the modal
	        var modal = document.getElementById('myModal');
	        // Get the button that opens the modal
	        var btn = document.getElementById("myBtn");
	        // Get the <span> element that closes the modal
	        var span = document.getElementsByClassName("close")[0];                                          
	        // When the user clicks on <span> (x), close the modal
	        span.onclick = function() {
	            modal.style.display = "none";
	        }
	        // When the user clicks anywhere outside of the modal, close it
	        window.onclick = function(event) {
	            if (event.target == modal) {
	                modal.style.display = "none";
	            }
	        }

			</script>
	</body>
</html>