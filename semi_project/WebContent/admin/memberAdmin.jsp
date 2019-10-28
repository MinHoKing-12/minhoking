<%@page import="member.MemberDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>KIWOOM SUPPORTERS - 회원 관리</title>
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
	
	/* dropdown menu */
	.mem_tr {
		position: relative;
	}
	.dropdown {
		list-style: none;
		display: none;
		position: absolute;
		margin-top: 10px;
		margin-left: 80px;
		background-color: #eee;
		color: #000;
		z-index: 1;
	}
	.dropdown li {
		padding: 15px;
	}
	.dropdown li:hover {
		background-color: #820024;
		color: #fff;
		cursor: pointer;
	}
	.td_id:hover .dropdown {
		display: block;
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
			    <li><a href="./admincontrol?command=memlist" class="active">회원 관리</a></li>
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
			<a href="#">회원관리</a>
			<span>&gt;</span>
			회원리스트
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
						<h2 class="left">회원 리스트</h2>
						<div class="left" style="margin-left: 20px;">
							(전체 회원 수 : <b><%=request.getAttribute("totalMemCount") %>명</b>)
						</div>
						<div class="right">
							<form id="search-frm">
								<input type="hidden" name="command" value="memlist">
								<select class="field" name="search-select">
									<option value="id">아이디</option>
									<option value="nickname">닉네임</option>
									<option value="name">이름</option>
									<option value="address">주소</option>
									<option value="email">이메일</option>
								</select>
								<input type="text" name="search-text" class="field small-field" />
								<input type="button" id="search-btn" class="button" value="검색" />
							</form>
						</div>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<%
					List<MemberDto> list = (List<MemberDto>)request.getAttribute("list");
					int totalPages = (int)request.getAttribute("totalPages");
					int currentPage = (int)request.getAttribute("page");
					String select = (String)request.getAttribute("select");
					String text = (String)request.getAttribute("text");
					%>
					<div class="table" align="center">
						<form id="delete-frm">
							<input type="hidden" name="command" value="multi-memdelete">
							<table>
							<col width="50"><col width="130"><col width="100"><col width="70"><col width="80"><col width="340"><col width="200"><col width="30">
							<tr>
								<th>번호</th>
								<th>아이디</th>
								<th>닉네임</th>
								<th>이름</th>
								<th>우편번호</th>
								<th>주소</th>
								<th>이메일</th>
								<th><input type="checkbox" id="multi-delete"></th>
							</tr>
							<%
							if(list == null || list.size() == 0) {
								%>
								<tr>
									<td colspan="7" align="center">회원 리스트가 없습니다.</td>
								</tr>
								<%
							}else {
								for(int i = 0; i < list.size(); i++) {
									MemberDto dto = list.get(i);
									%>
									<tr class="mem_tr">
										<input type="hidden" class="target_point" value=<%=dto.getPoint() %>>
										<input type="hidden" class="target_jdate" value=<%=dto.getJdate() %>>
										<input type="hidden" class="target_id" value=<%=dto.getId() %>>
										<td align="center"><%=i+1 %></td>
										<td align="center" class="td_id">
											<%=dto.getId() %>
											<ul class="dropdown">
												<li class="detail">상세 회원정보</li>
												<li class="write_msg">쪽지 쓰기</li>
											</ul>
										</td>
										<td align="center" class="target_nick"><%=dto.getNickname() %></td>
										<td align="center" class="target_name"><%=dto.getName() %></td>
										<td align="center" class="target_postnum"><%=dto.getPostnum() %></td>
										<td class="target_address"><%=dto.getAddress() + " " + dto.getAddress_detail() %></td>
										<td class="target_email"><%=dto.getEmail() %></td>
										<td align="center"><input type="checkbox" name="delete-check" value=<%=dto.getId() %>></td>
									</tr>
									<%
								}
							}
							%>
							<tr>
								<td colspan="8" align="right"><button type="button" class="button" id="delete-btn">회원정보 삭제</button></td>
							</tr>
							</table>
						</form>
						<!-- Pagging -->
						<div class="pagging" align="center">
							<div style="margin: auto">
							<%
							for(int i = 1; i <= totalPages; i++) {
								if(text != null && !text.equals("")){
									System.out.println("검색 페이징");
									%>
									<a href="admincontrol?command=memlist&search-select=<%=select %>&search-text=<%=text %>&page=<%=i %>"><%=i %></a>
									<%
								}else {
									%>
									<a href="admincontrol?command=memlist&page=<%=i %>"><%=i %></a>
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
				
				
				<!-- Message Modal -->
				<div id="msg-modal" class="modal">
					<!-- Box -->
					<div class="box modal-content">
						<div>
						<span class="close">&times;</span>
						</div>
						<!-- Box Head -->
						<div class="box-head">
							<h2>쪽지 쓰기</h2>
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
				
				<!-- Detail Modal -->
				<div id="detail-modal" class="modal">
					<!-- Box -->
					<div class="box modal-content">
						<div>
						<span class="close">&times;</span>
						</div>
						<!-- Box Head -->
						<div class="box-head">
							<h2>상세 회원정보</h2>
						</div>
						<!-- End Box Head -->
						<div class="table" align="center">
							<table style="width:100%">
							<tr>
								<th>아이디</th>
								<td id="detail-id"></td>
							</tr>
							<tr>
								<th>닉네임</th>
								<td id="detail-nickname"></td>
							</tr>
							<tr>
								<th>우편번호</th>
								<td id="detail-postnum"></td>
							</tr>
							<tr>
								<th>주소</th>
								<td id="detail-address"></td>
							</tr>
							<tr>
								<th>이메일</th>
								<td id="detail-email"></td>
							</tr>
							<tr>
								<th>회원포인트</th>
								<td id="detail-point"></td>
							</tr>
							<tr>
								<th>가입일</th>
								<td id="detail-jdate"></td>
							</tr>		
							</table>
						</div>
					</div>
					<!-- End Box -->
				</div>
				<!--End Message Modal  -->

			</div>
			<!-- End Content -->
			
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
	// 상세정보 클릭
	$(".detail").on("click", function () {
		var trTag = $(this).parent().parent().parent();
		var id = trTag.children(".target_id").val();
		var nickname = trTag.children(".target_nick").text();
		var name = trTag.children(".target_name").text();
		var postnum = trTag.children(".target_postnum").text();
		var address = trTag.children(".target_address").text();
		var email = trTag.children(".target_email").text();
		var point = trTag.children(".target_point").val();
		var jdate = trTag.children(".target_jdate").val();
		
		$("#detail-id").text(id);
		$("#detail-nickname").text(nickname);
		$("#detail-name").text(name);
		$("#detail-postnum").text(postnum);
		$("#detail-address").text(address);
		$("#detail-email").text(email);
		$("#detail-point").text(point);
		$("#detail-jdate").text(jdate);
		
		
		$("#detail-modal").css("display", "block");
	});
	
	// 쪽지쓰기 클릭
	$(".write_msg").on("click", function () {
		var trTag = $(this).parent().parent().parent();
		// alert(trTag.children(".target_nick").text());
		var nick = trTag.children(".target_nick").text();
		
		$("#to_nick").val(nick);
		$("#msg-modal").css("display", "block");
	});
	
	// 모달창 끄기
	$(".close").on("click", function () {
		$(".modal").css("display", "none");
	});
	
	// 쪽지작성 Ajax
	$("#write_btn").on('click', function () {
	//	alert("쪽지쓰기 클릭");
		
		var command = "writemsg";
		var to_nick = $("#to_nick").val();
		var content = $("#msg-content").val();
		
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
		
		if(confirm("선택하신 회원정보를 삭제하시겠습니까?")) {
			$("#delete-frm").attr("action", "admincontrol").submit();
		}
	});
});
</script>
	
</body>
</html>