<%@page import="free_bbs.free_bbs_commentDto"%>
<%@page import="free_bbs.free_bbs_commentDao"%>
<%@page import="free_bbs.free_bbsDto"%>
<%@page import="member.MemberDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
free_bbs_commentDao commentDao = free_bbs_commentDao.getInstance();
/* public int getReCommentListCnt(int parent); */
List<free_bbs_commentDto> list = (List<free_bbs_commentDto>)request.getAttribute("replyList");

free_bbsDto bbsdto = null;
if(request.getAttribute("bbsdto")!=null){
	bbsdto = (free_bbsDto)request.getAttribute("bbsdto");
}

int len = (int)request.getAttribute("allReply");
System.out.println("현재페이지의 총 댓글의 갯수는 " + len);
int replyPage = len / 10;
if(len % 10 > 0){
	replyPage = replyPage + 1;
}

int seqVal = (int)request.getAttribute("seqVal");
System.out.println("현재페이지의 게시글 번호는 " + seqVal);

int replyCount = (int)request.getAttribute("replyCount");

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
			게시물 리스트 관리
			<span>&gt;</span>
			게시물 상세 관리
		</div>
		<!-- End Small Nav -->
		
		<!-- Main -->
		<div id="main">
			<div class="cl">&nbsp;</div>
			
			<!-- Content -->
			<div id="content">
				
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2>게시물 상세내용</h2>
					</div>
					<div class="table" align="left">
					<table>
								<col width="130"><col width="160"><col width="160">
								<col width="130"><col width="130"><col width="130"><col width="130">
								<!-- <col width="100"><col width="100">
								<col width="130"><col width="100"><col width="100"><col width="100"><col width="100"> -->
							<tr>
								<th>글번호</th>
								<th>아이디</th>
								<th>닉네임</th>
								<th>삭제여부</th>
								<th>추천수</th>
								<th>조회수</th>
								<th>댓글수</th>
							</tr>
								<tr>
									<td align="center"><%=bbsdto.getSeq() %></td>
									<td align="center"><%=bbsdto.getId() %></td>
									<td align="center"><%=bbsdto.getNickname() %></td>
									<td align="center"><%=bbsdto.getDel() %></td>
									<td align="center"><%=bbsdto.getBlike() %></td>
									<td align="center"><%=bbsdto.getReadcount() %></td>
									<td align="center"><%=replyCount %></td>
								</tr>
							
							</table>
					</div>
					<br>
					<div align="left">
					<table border="0">
								<col width="110"><col width="460">
							<tr height='30'>
								<th>제목</th>
								<td><%=bbsdto.getTitle() %></td>
							</tr>
							<tr height='30'>
								<th>작성날짜</th>
								<td><%=bbsdto.getWdate() %></td>
							</tr>	
							<tr height='30'>
								<th>작성내용</th>
								<td><textarea class="field size1" rows="10" cols="120"
									readonly="readonly"><%=bbsdto.getContent() %></textarea></td>
							</tr>
							
							</table>
							<br>
					</div>
					<!-- End Box Head -->
					
				
				</div>
				<!-- End Box -->
				
				
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">댓글 리스트</h2>
						<!-- <div class="right">
							<form id="search-frm">
								<input type="hidden" name="command" value="freebbslist">
								<select class="field" name="choice">
									<option value="id">아이디</option>
									<option value="nickname">닉네임</option>
									<option value="content">내용</option>
								</select>
								<input type="text" name="text" class="field small-field" />
								<input type="button" id="search-btn" class="button" value="검색" />
							</form>
						</div> -->
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					
					<div class="table" align="center">
						<form id="delete-frm">	
						    <input type="hidden" name="page" value="<%=seqVal%>">
							<input type="hidden" name="command" value="multi-replyDelete">
							<table>
								<col width="50"><col width="70"><col width="80"><col width="250"><!-- <col width="100"><col width="100">
								<col width="130"><col width="100"><col width="100"><col width="100"><col width="100"> -->
							<tr>
								<th>번호</th>
								<th>게시글번호</th>
								<th>댓글번호</th>
								<th>내용</th>
								<th>아이디</th>
								<th>닉네임</th>
								
								<th>삭제여부</th>
								<th>작성날짜</th>
								<th>추천수</th>
								<th>답글수</th>
								<th><input type="checkbox" id="multi-delete"></th>
							</tr>
							<%
							if(list == null || list.size() == 0) {
								%>
								<tr>
									<td colspan="11" align="center">댓글 리스트가 없습니다.</td>
								</tr>
								<%
							}else {
								for(int i = 0; i < list.size(); i++) {
									free_bbs_commentDto dto = list.get(i);
									%>
									<tr>
										<td align="center"><%=i+1 %></td>
										<td align="center"><%=dto.getPage() %></td>
										<td align="center"><%=dto.getSeq() %></td>
										<td align="center"><%=dto.getContent() %></td>
										<td align="center"><%=dto.getId() %></td>
										<td align="center"><%=dto.getNickname() %></td>										
										
										<td align="center"><%=dto.getDel() %></td>
										<td align="center"><%=dto.getWdate() %></td>
										<td align="center"><%=dto.getBlike() %></td>
										<td align="center"><%=commentDao.getReCommentListCnt(dto.getSeq()) %></td>
										<td align="center"><input type="checkbox" name="delete-check" value=<%=dto.getSeq() %>></td>
									</tr>
									<%
								}
							}
							%>
							<tr>
								<td colspan="11" align="right"><button type="button" class="button" id="delete-btn">댓글 삭제</button></td>
							</tr>
							</table>
						</form>
						<!-- Pagging -->
					 	<div class="pagging" style="text-align: center">
							<%for(int i = 0;i < replyPage; i++){%>
								<a href="./admincontrol?command=forbbsDetail&seqVal=<%=seqVal%>&rPageNumber=<%=i+1%>" title="<%=i+1 %>페이지" 
								style="font-size: 15pt; color: #990000; font-weight: bold;">[<%=i+1 %>]</a>&nbsp;
							<%}%>
						</div> 
						<!-- End Pagging -->
						
					</div>
					
					
					
					
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