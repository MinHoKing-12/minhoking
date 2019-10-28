<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<style type="text/css">

</style>
</head>
<body>
<c:set var="mem" value="${login }"></c:set>
<c:set var="dto" value="${dto }"/>
<div align="center" style="margin-top: 100px">
<form id="frm">
<input type="hidden" name="seq" value="${dto.seq }">
<table border="1" class="list_table">
<tr>
		<th style="width: 45px">조회수</th>
		<td><input type="text" value="${dto.readcount }" readonly="readonly" style="width: 100%"></td>
</tr>
<tr>
		<th style="width: 45px">작성일</th>
		<td><input type="text" value="${dto.wdate }" readonly="readonly" style="width: 100%"></td>
</tr>
<tr>
	<th style="width: 45px">ID</th>
	<td>
		<input type="text" id="id" value="${dto.id }" readonly="readonly" name="id" style="width: 100%">
	</td>
</tr>
<tr>
	<th style="width: 45px">제목</th>
	<td>
		<input type="text" id="title" name="title" style="width: 100%;" value="${dto.title }" ><br>
	</td>
</tr>

<tr>
	<th colspan="2" style="text-align: left">내용</th>
</tr>
<tr>
	<td colspan="2">
	<textarea rows="15" cols="130" id="text" name="content" ><c:out value="${dto.content }"></c:out> </textarea>
	</td>
</tr>
<tr>
	<td style="width: 45px" colspan="2">
		<input type="button" id="backBtn" value="돌아가기">
		<input type="button" id="upBtn" value="수정완료">

	</td>
</tr>
</table>

</form>
</div>

<script type="text/javascript">
$(document).ready(function () {
	

	$("#backBtn").on('click', function () {
		location.href = "bbslist.do";
	});
	
	$("#upBtn").on('click', function () {
		$("#frm").attr("action", "bbsupdateAf.do").submit();
	});
});

</script>
</body>
</html>