<%@page import="sellcomment.sell_comment_Dto"%>
<%@page import="util.utilMethod"%>
<%@page import="sellbbs.sell_Dto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    	List<sell_comment_Dto> list = (List<sell_comment_Dto>)request.getAttribute("commentlist");
    	sell_Dto dto = (sell_Dto)request.getAttribute("detailsell");
    	
    	int size = (int)request.getAttribute("count");
    	List<sell_comment_Dto> commentCount = (List<sell_comment_Dto>)request.getAttribute("commentCount");
    %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>KIWOOM SUPPORTERS - 거래게시판 관리</title>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<link rel="stylesheet" href="admin/css/style.css" type="text/css" media="all" />
	<link rel="shortcut icon"  href="main/images/favicon.ico">
<style type="text/css">
#main {
	width: 1290px;
}
#content {
	width: 75%;
}
.table th {
	text-align: center;
}
select {
	height: 21px;
	position: relative;
    bottom: 4px;
}
.deleteSell {
	position: relative;
	left: 900px;
}
#footer {
    position:absolute;
    bottom:0;
    width:100%;  
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
			    <li><a href="./admincontrol?command=sellbbslist&page=1" class="active">거래게시판 관리</a></li>
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
			<a href="#">게시판</a>
			<span>&gt;</span>
			거래게시판 목록
			<span>&gt;</span>
			거래게시판 상세 목록
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
						<h2>상세 게시글</h2>
					</div>
					<!-- End Box Head -->
					<div class="table" align="left">
						<table>
						<col width="130"><col width="160"><col width="160">
						<col width="130"><col width="130"><col width="130"><col width="130">
						<tr>
							<th>게시글번호</th>
							<th>아이디</th>
							<th>닉네임</th>
							<th>삭제여부</th>
							<th>관심수</th>
							<th>조회수</th>
							<th>댓글수</th>
						</tr>
						<tr>
							<td align="center"><%=dto.getSeq() %></td>
							<td align="center"><%=dto.getId() %></td>
							<td align="center"><%=dto.getNickname() %></td>
							<td align="center"><%=dto.getDel() %></td>
							<td align="center"><%=dto.getAcount() %></td>
							<td align="center"><%=dto.getRcount() %></td>
							<td align="center"><%=commentCount.size() %></td>
						</tr>
						
						</table>
					</div>
					<br>
					<div align="left">
					<table border="0">
						<col width="110"><col width="460">
						<tr height="30">
							<th>제목</th>
							<td><%=dto.getTitle() %></td>
						</tr>
						<tr height="30">
							<th>작성날짜</th>
							<td><%=utilMethod.dateEdit(dto.getWdate()) %></td>
						</tr>
						<tr height="30">
							<th style="position: relative; bottom: 80px;">작성내용</th>
							<td>
							<textarea class="filed size1" rows="10" cols="120" readonly="readonly"><%=dto.getContent() %></textarea>
							</td>
						</tr>
					</table>
					</div>
					
				</div>
				<!-- End Box -->
				
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">댓글 목록</h2>
						<div class="right">
						<!--
						<form id="frm">
						 <label>
							<select name="searchValue" id="searchValue">
								<option value="선택">카테고리</option>
								<option value="닉네임">닉네임</option>
								<option value="제목">제목</option>
							</select>
							</label> 
							<input type="hidden" value="sellsearch" name="command">
							<input type="text" class="field small-field" name="searchText" id="searchText" />
							<input type="button" id="searchValueBtn" value="검색"/>
							</form>
							-->
						</div>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<div class="table">
					<form id="deleteForm" action="admincontrol?command=commentdelete" method="get">
					<input type="hidden" value="<%=dto.getSeq() %>" name="detailSeq">
					<table>
					<col width="130"><col width="160"><col width="160">
					<col width="130"><col width="130"><col width="130"><col width="130">
					<tr>
						<th>순서</th>
						<th>게시글 번호</th>
						<th>작성자</th>
						<th>내용</th>
						<th>삭제</th>
						<th>작성일</th>
						<th><input type="checkbox" id="parentcheck"></th>
					</tr>
						<%
						for(int i = 0; i<list.size();i++){
							sell_comment_Dto commentdto = list.get(i);
						%>
						<tr class="trTag" align="center">
							<td><%=commentdto.getSeq() %></td>
							<td><%=commentdto.getParent() %></td>
							<td><%=commentdto.getNickname() %></td>
							<td><%=utilMethod.dot13(commentdto.getContent()) %></td>
							<td><%=commentdto.getDel() %></td>
							<td><%=utilMethod.dateEdit(commentdto.getDate()) %></td>
							<td><input type="checkbox" value="<%=commentdto.getSeq()%>" name="childcheck"></td>
						</tr>
						<%
						}
						%>
						<tr>
							<td colspan="7" align="right"><button type="button" class="button" id="deleteSell">댓글 삭제</button></td>
						</tr>
						</table>
						<input type="hidden" name="command" value="commentdelete">
						<!-- Pagging -->
						</form>
						<div class="pagging">
						<%
						for(int c=1; c<size + 1; c++){
						%>
						<a href="./admincontrol?command=selldetail&seq=<%=dto.getSeq() %>&page=<%=c %>"><%=c %></a>
						<%	
						}
						%>
						</div>
						<!-- End Pagging -->
						
					</div>
					<!-- Table -->
					
				</div>
				<!-- End Box -->

			</div>
			<!-- End Content -->
			
			<!-- Sidebar -->
			<div id="sidebar">

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
<script type="text/javascript">
$(document).ready(function () {
	// 체크 하면 전체 체크 됐다가 안됐다가 하는 jquery문
	$("#parentcheck").click(function () {
		if($("#parentcheck").prop("checked")){
		$("input[name=childcheck]").prop("checked",true);
	}else{
		$("input[name=childcheck]").prop("checked",false);
	}
	});
		
	$("#deleteSell").click(function () {
		// 체크박스 미 선택 시 제출 안됨
		var checks = $("input[name=childcheck]");
		var isChecked = false;
		
		for (var i = 0; i< checks.length; i++){
			if(checks.eq(i).prop("checked")){
				isChecked = true;
			}
		}
		
		if(!isChecked) {
			alert("삭제 항목을 선택해 주세요");
			return;
		}
		$("#deleteForm").submit();
	}); 
	
	$("#searchValueBtn").click(function () {
		var searchValue = $("#searchValue").val();
		var searchText = $("#searchText").val();
	//	alert(searchValue);
	//	alert(searchText);
	//	location.href=""
	});
});
</script>	
</body>
</html>