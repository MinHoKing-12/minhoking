<%@page import="util.utilMethod"%>
<%@page import="message.MessageDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>KIWOOM SUPPORTERS - 관리자 페이지</title>
	<link rel="stylesheet" href="admin/css/style.css" type="text/css" media="all" />
	<link rel="shortcut icon"  href="main/images/favicon.ico">
	<style>
	select[name=search-select] {
		padding: 0px;
		float: left;
	}
	#main {
		width: 1300px;
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
		width: 30%; /* Could be more or less, depending on screen size */                     
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
	
	/* hover효과 없애기 */
	.modal-content tr:hover td{
		background: #fff;
	}
	</style>
</head>
<body>
<!-- Header -->
<div id="header">
	<div class="shell">
		<!-- Logo + Top Nav -->
		<div id="top">
			<h1><a href="#">Kiwoom Supporters</a></h1>
			<div id="top-navigation">
				안녕하세요! <strong>관리자님</strong>
				<span>|</span>
				<a href="./admincontrol?command=msgbox">마이페이지</a>
				<span>|</span>
				<a href="./memcontrol?command=logout">로그아웃</a>
			</div>
		</div>
		<!-- End Logo + Top Nav -->
		
		<!-- Main Nav -->
		<div id="navigation">
			<ul>
			    <li><a href="./admincontrol?command=memlist">회원 관리</a></li>
			    <li><a href="./admincontrol?command=sellbbslist&page=1">거래게시판 관리</a></li>
			    <li><a href="./admincontrol?command=freebbslist">자유게시판 관리</a></li>
			    <li><a href="./admincontrol?command=votelist">투표 관리</a></li>
			    <li><a href="./admincontrol?command=calendarlist">경기일정 관리</a><li>
			</ul>
		</div>
		<!-- End Main Nav -->
	</div>
</div>
<!-- End Header -->

<!-- Container -->
<div id="container">
	<div class="shell">
		
		<!-- Small Nav -->
		<div class="small-nav">
			<a href="#">마이페이지</a>
			<span>&gt;</span>
			받은쪽지함
		</div>
		<!-- End Small Nav -->
		
		<!-- Main -->
		<div id="main">
			<div class="cl">&nbsp;</div>
			
			<!-- Content -->
			<div id="content">
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">받은 쪽지함</h2>
						<div class="right">
							<form id="search-frm">
								<input type="hidden" name="command" value="msgbox">
								<select class="field" name="search-select">
									<option value="id">닉네임</option>
									<option value="content">내용</option>
								</select>
								<input type="text" name="search-text" class="field small-field" />
								<input type="button" id="search-btn" class="button" value="검색" />
							</form>
						</div>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<%
					List<MessageDto> list = (List<MessageDto>)request.getAttribute("msglist");
					int totalPages = (int)request.getAttribute("totalPages");
					int currentPage = (int)request.getAttribute("page");
					String select = (String)request.getAttribute("select");
					String text = (String)request.getAttribute("text");
					%>
					<div class="table" align="center">
						<form id="delete-frm">
							<input type="hidden" name="command" value="multi-msgdelete">
							<input type="hidden" name="type" value="receive">
							<table>
							<col width="50"><col width="300"><col width="150"><col width="150"><col width="100"><col width="30">
							<tr>
								<th>번호</th>
								<th>쪽지 내용</th>
								<th>보낸이</th>
								<th>수신일</th>
								<th>수신확인</th>
								<th><input type="checkbox" id="multi-delete"></th>
							</tr>
							<%
							if(list == null || list.size() == 0) {
								%>
								<tr>
									<td colspan="6" align="center">받은 쪽지가 없습니다.</td>
								</tr>
								<%
							}else {
								for(int i = 0; i < list.size(); i++) {
									MessageDto msg = list.get(i);
									%>
									<tr class="msg_tr">
										<td align="center"><%=i+1 %></td>
										<td onclick="showDetail(this)" style="cursor: pointer">
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
										<td align="center"><input type="checkbox" name="delete-check" value=<%=msg.getSeq() %>></td>
									</tr>
									<%
								}
							}
							%>
							<tr>
								<td colspan="6" align="right"><button type="button" class="button" id="delete-btn">쪽지 삭제</button></td>
							</tr>
							</table>
						</form>
						<!-- Pagging -->
						<div class="pagging" align="center">
							<div style="margin: auto">
							<%
							for(int i = 1; i <= totalPages; i++) {
								if(text != null && !text.equals("")) {
								%>
								<a href="admincontrol?command=msgbox&page=<%=i %>&search-select=<%=select %>&search-text=<%=text %>"><%=i %></a>
								<%	
								}else {
								%>
								<a href="admincontrol?command=msgbox&page=<%=i %>"><%=i %></a>
								<%
								}
							}
							%>
							</div>
						</div>
						<!-- End Pagging -->
						
					</div>
					<!-- Table -->
					
				</div>
				<!-- End Box -->
				
				<!-- Detail Modal -->
				<div id="detail-modal" class="modal">
					<!-- Box -->
					<div class="box modal-content">
						<div>
						<span class="close">&times;</span>
						</div>
						<!-- Box Head -->
						<div class="box-head">
							<h2>쪽지읽기</h2>
						</div>
						<!-- End Box Head -->
						<div class="table" align="center">
							<table style="width:100%">
							<tr>
								<th>보낸이</th>
								<td id="detail-from_nick"></td>
							</tr>
							<tr>
								<th>작성일</th>
								<td id="detail-wdate"></td>
							</tr>
							<tr>
								<th>내용</th>
								<td><textarea rows="10" cols="60" id="detail-content" readonly="readonly"></textarea></td>
							</tr>
							<tr>
								<td colspan="2" align="right"><button type="button" id="reply-btn">답장하기</button></td>
							</tr>
							</table>
						</div>
					</div>
					<!-- End Box -->
				</div>
				<!--End Message Modal  -->
				
				<!-- Message Modal -->
				<div id="msg-modal" class="modal">
					<!-- Box -->
					<div class="box modal-content">
						<div>
						<span class="close">&times;</span>
						</div>
						<!-- Box Head -->
						<div class="box-head">
							<h2>쪽지 답장</h2>
						</div>
						<!-- End Box Head -->
		
						<!-- Form -->
						<div class="form">
							<p>
								<label>받는사람 </label>
								<input type="text" id="to_nick" class="field" name="msg-to_nick" readonly="readonly" />
							</p>	
							<p>
								<label>Content</label>
								<textarea class="field" rows="10" cols="60" id="msg-content"></textarea>
							</p>
						</div>
						<!-- End Form -->
						
						<!-- Form Buttons -->
						<div class="buttons">
							<input type="button" class="button" id="write_btn" value="쪽지쓰기" />
						</div>
						<!-- End Form Buttons -->

					</div>
					<!-- End Box -->
				</div>
				<!--End Message Modal  -->
				
			</div>
			<!-- End Content -->
			
			<!-- Sidebar -->
			<div id="sidebar">
				
				<!-- Box -->
				<div class="box">
					
					<!-- Box Head -->
					<div class="box-head">
						<h2>Management</h2>
					</div>
					<!-- End Box Head-->
					
					<div class="box-content">
						<p><a href="admincontrol?command=msgbox">받은 쪽지함</a></p>
						<p><a href="admincontrol?command=msgbox&type=send">보낸 쪽지함</a></p>
						<p><a href="admincontrol?command=admininfo">비밀번호 변경</a></p>					
					</div>
				</div>
				<!-- End Box -->
			</div>
			<!-- End Sidebar -->
			
			<div class="cl">&nbsp;</div>			
		</div>
		<!-- Main -->
	</div>
</div>
<!-- End Container -->

<!-- Footer -->
<div id="footer">
	<div class="shell">
		<span class="left">&copy; 2010 - CompanyName</span>
		<span class="right">
			Design by <a href="http://chocotemplates.com" target="_blank" title="The Sweetest CSS Templates WorldWide">Chocotemplates.com</a>
		</span>
	</div>
</div>
<!-- End Footer -->

<!-- Script -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
$(function () {
	// 답장하기 버튼 클릭 시 답장하기 모달 띄우기
	$("#reply-btn").on('click', function () {
		$("#detail-modal").css("display", "none");
		
		var nick = $("#detail-from_nick").text();
		
		$("#to_nick").val(nick);
		$("#msg-modal").css("display", "block");
	});
	
	// 모달창 끄기
	$(".close").on("click", function () {
		$(".modal").css("display", "none");
	});
	
	// 검색 버튼 클릭시 검색 폼 제출
	$("#search-btn").on("click", function () {
		$("#search-frm").attr("action", "admincontrol").submit();
	});
	
	// 체크 박스 모두 선택!
	$("#multi-delete").on('click', function () {
		$("input[name=delete-check]").prop("checked", $(this).prop("checked"));
	});
	
	// 삭제 버튼 클릭시 삭제 폼 제출
	$("#delete-btn").on('click', function () {
		// 체크박스 미선택 시 제출 안됨
		var checks = $("input[name=delete-check]");
		var isChecked = false;
		
		for (var i = 0; i < checks.length; i++) {
			if(checks.eq(i).prop("checked")) {
				isChecked = true;
			}
		}
		
		if(!isChecked) {
			alert("선택항목을 선택해주세요");
			return;
		}
		
		$("#delete-frm").attr("action", "admincontrol").submit();
	});
	
	// 쪽지작성 Ajax
	$("#write_btn").on('click', function () {
		var command = "writemsg";
		var to_nick = $("#to_nick").val();
		var content = $("#msg-content").val();
		
		if(content.trim() == null || content.trim() == "") {
			alert("내용을 입력해주세요");
			return;
		}
		
		$.ajax({
			url: "admincontrol",
			type: "POST",
			data: { command:command, to_nick:to_nick, content:content },
			dataType: "text",
			success: function ( data ) {
			//	alert( data );
				if(data.trim() == "true"){
					alert("성공적으로 쪽지가 전송되었습니다.");
					$("#msg-content").val("");
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
});

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
//	$("input[name=delete-seq]").val(seq);
	
	$("#detail-modal").css("display", "block");
	
}
</script>
	
</body>
</html>