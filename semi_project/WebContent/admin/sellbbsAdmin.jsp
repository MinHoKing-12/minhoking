<%@page import="util.utilMethod"%>
<%@page import="sellbbs.sell_Dto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    	List<sell_Dto> list = (List<sell_Dto>)request.getAttribute("list");
    	int size = (int)request.getAttribute("count");
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
#container {
	font-size: 12px;
}
#main {
	width: 1350px;
}
#content {
	width: 80%;
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
						<h2 class="left">거래게시판 목록(오래된 순으로 출력합니다.)</h2>
						<div class="right">
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
						</div>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<div class="table">
					<form id="deleteForm" action="admincontrol">
					<table>
					<col width="50">
					<tr>
						<th>순서</th>
						<th>닉네임</th>
						<th>제     목</th>
						<th>조회수</th>
						<th>관심수</th>
						<th>작성일</th>
						<th>삭제여부</th>
						<th>가     격</th>
						<th>카테고리</th>
						<th>상품카테고리</th>
						<th>거래완료여부</th>
						<th>파일이름</th>
						<th><input type="checkbox" id="parentcheck"></th>
						</tr>
					
					<%
						for(int i = 0; i < list.size(); i++){
							sell_Dto dto = list.get(i);
					%>
						<tr class="trTag" style="cursor: pointer">
							<td class="tdTag" align="center"><%=dto.getSeq() %></td>
							<td class="tdTag" align="center"><%=utilMethod.dot7(dto.getNickname()) %></td>
							<td class="tdTag" align="center"><%=utilMethod.dot7(dto.getTitle()) %></td>
							<td class="tdTag" align="center"><%=dto.getRcount() %></td>
							<td class="tdTag" align="center"><%=dto.getAcount() %></td>
							<td class="tdTag" align="center"><%=utilMethod.dateEdit(dto.getWdate()) %></td>
							<td class="tdTag" align="center"><%=dto.getDel() %></td>
							<td class="tdTag" align="center"><%=utilMethod.PriceConvert(dto.getPrice())%></td>
							<td class="tdTag" align="center"><%=dto.getCategory() %></td>
							<td class="tdTag" align="center"><%=dto.getPcategory() %></td>
							<td class="tdTag" align="center"><%=dto.getComplete() %></td>
							<td class="tdTag" align="center"><%=dto.getFilename() %></td>
							<td align="center"><input type="checkbox" value="<%=dto.getSeq() %>" name="childcheck" style="cursor: pointer"></td>
						</tr>
					<%
						}
					%>
						<tr>
							<td colspan="13" align="right"><button type="button" class="button" id="deleteSell">게시글 삭제</button></td>
						</tr>
						</table>
						<input type="hidden" name="command" value="delete">
						<!-- Pagging -->
						</form>
						<div class="pagging">
							<%
							String searchValue = (String)request.getAttribute("searchValue");
							String text = (String)request.getAttribute("text");
							for(int c=1; c<size + 1; c++){
								if(searchValue == null){
							%>
							<a href="./admincontrol?command=sellbbslist&page=<%=c %>"><%=c %></a>
							<%
							}else if(searchValue != null){
							%>
							<a href="./admincontrol?command=sellsearch&page=<%=c %>&searchValue=<%=searchValue %>&searchText=<%=text %>"><%=c %></a>
							<%	
								}
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
	
	$("#searchValueBtn").click(function () {
		if($("#searchValue").val() == "선택"){
			alert("검색 카테고리를 설정하세요.");
		}else if($("#searchText").val() == ""){
			alert("검색 내용을 입력하세요");
		}else{
			var searchValue = $("#searchValue").val();
			var searchText = $("#searchText").val();
			
			location.href = "admincontrol?command=sellsearch&searchText="+searchText+"&searchValue="+searchValue;
		}
	});
		$("#searchText").keydown(function(key) {
			if(key.keyCode == 13){
			if($("#searchValue").val() == "선택"){
				alert("검색 카테고리를 설정하세요.");
			}else if($("#searchText").val() == ""){
				alert("검색 내용을 입력하세요");
			}else{
				var searchValue = $("#searchValue").val();
				var searchText = $("#searchText").val();
				location.href = "admincontrol?command=sellsearch&searchText="+searchText+"&searchValue="+searchValue;
			}
		}	
	});

	$("#deleteSell").on("click", function () {
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
		$("#deleteForm").attr("action","").submit();
	});
		
	$(".tdTag").click(function () {
		var trTag = $(this).parent();
//		alert(trTag.children().html());
		var parentSeq = trTag.children().html();
		location.href="admincontrol?command=selldetail&seq="+parentSeq+"&page=1";
	});
});
</script>	
</body>
</html>