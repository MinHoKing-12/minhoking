<%@page import="free_bbs.free_bbs_commentDao"%>
<%@page import="free_bbs.free_bbsDto"%>
<%@page import="member.MemberDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
free_bbs_commentDao commentDao = free_bbs_commentDao.getInstance();
List<free_bbsDto> list = (List<free_bbsDto>)request.getAttribute("list");

int len = (int)request.getAttribute("allPosting");
int bbsPage = len / 10;
if(len % 10 > 0){
bbsPage = bbsPage + 1;
}

%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>KIWOOM SUPPORTERS - 자유게시판 관리</title>
	<link rel="stylesheet" href="admin/css/style.css" type="text/css" media="all" />
	<link rel="shortcut icon"  href="main/images/favicon.ico">
	<style>
	select[name=search-select] {
		padding: 0px;
		float: left;
	}
	#main {
		width: 100%;
	}
	#minsu{
		position: relative;
		right : 275px;
		top : -2.5px;
	}
	#movemove{
		position: relative;
		right : -70px;
	}
	.table{
		width: 100%;
	}
	.container{
		width: 1300px;
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
			    <li><a href="./admincontrol?command=freebbslist" class="active">자유게시판 관리</a></li>
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
			<a href="#">자유게시판 관리</a>
			<span>&gt;</span>
			게시물 리스트
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
						<h2 class="left">게시물 리스트</h2>
						<div class="right">
							<form id="search-frm">
								<input type="hidden" name="command" value="freebbslist">
								<div id='movemove'>
									<select id='minsu' class="field" name="choice">
										<option value="id">아이디</option>
										<option value="nickname">닉네임</option>
										<option value="title">제목</option>
										<option value="content">내용</option>
									</select>
									<input type="text" name="text" class="field small-field" />
									<input type="button" id="search-btn" class="button" value="검색" />
								</div>
							</form>
						</div>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					
					<div class="table" align="center">
						<form id="delete-frm">	
							<input type="hidden" name="command" value="multi-bbsdelete">
							<table style="width:100%">
								<col width="80"><col width="110"><col width="370"><col width="100"><col width="100">
								<col width="160"><col width="150"><col width="90"><col width="100"><col width="100"><col width="80">
							<tr>
								<th>번호</th>
								<th>글번호</th>
								<th>제목</th>
								<th>아이디</th>
								<th>닉네임</th>
								
								<th>삭제여부</th>
								<th>작성날짜</th>
								<th>조회수</th>
								<th>추천수</th>
								<th>댓글수</th>
								<th><input type="checkbox" id="multi-delete"></th>
							</tr>
							<%
							if(list == null || list.size() == 0) {
								%>
								<tr>
									<td colspan="11" align="center">게시물 리스트가 없습니다.</td>
								</tr>
								<%
							}else {
								for(int i = 0; i < list.size(); i++) {
									free_bbsDto dto = list.get(i);
									%>
									<tr>
										<td align="center"><%=i+1 %></td>
										<td align="center"><%=dto.getSeq() %></td>
										
										<td align="center" class='seqclick'>
											<a style='cursor:pointer'><%=dto.getTitle() %></a>
											<input type='hidden' value='<%=dto.getSeq() %>'>
										</td>
										
										<td align="center"><%=dto.getId() %></td>
										<td align="center"><%=dto.getNickname() %></td>										
										<td align="center"><%=dto.getDel() %></td>
										<td align="center"><%=dto.getWdate() %></td>
										<td align="center"><%=dto.getReadcount() %></td>
										<td align="center"><%=dto.getBlike() %></td>
										<td align="center"><%=commentDao.getCommentListCnt(dto.getSeq()) %></td>
										<td align="center"><input type="checkbox" name="delete-check" value=<%=dto.getSeq() %>></td>
									</tr>
									<%
								}
							}
							%>
							<tr>
								<td colspan="11" align="right"><button type="button" class="button" id="delete-btn">게시물 삭제</button></td>
							</tr>
							</table>
						</form>
						<!-- Pagging -->
						<div class="pagging" style="text-align: center">
							<%for(int i = 0;i < bbsPage; i++){%>
								<a href="./admincontrol?command=freebbslist&pageNumber=<%=i+1%>" title="<%=i+1 %>페이지" 
								style="font-size: 15pt; color: #990000; font-weight: bold;">[<%=i+1 %>]</a>&nbsp;
							<%}%>
							
							
						</div>
						<!-- End Pagging -->
						
					</div>
					<!-- Table -->
					
				</div>
				<!-- End Box -->
			
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
			
	</div>
</div>
<!-- End Footer -->

<!--form to get detail  -->
<form action="admincontrol" id="detailFrm" method="get">
	<input type = "hidden" id="seqVal" name="seqVal" value="">		
	<input type = "hidden" name="command" value="forbbsDetail">
</form>

<!-- Script -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	
	$('.seqclick').on('click',function(){
		var seqVal = $(this).find('input[type="hidden"]').val()
		$('#seqVal').val(seqVal);
		
		$('#detailFrm').submit();
	});	
});

$(function () {
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
});
</script>
	
</body>
</html>