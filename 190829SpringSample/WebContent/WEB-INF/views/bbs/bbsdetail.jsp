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
		<input type="text" id="title" name="title" style="width: 100%;" value="${dto.title }" readonly="readonly"><br>
	</td>
</tr>

<tr>
	<th colspan="2" style="text-align: left">내용</th>
</tr>
<tr>
	<td colspan="2">
	<textarea rows="15" cols="130" id="text" name="content" readonly="readonly"><c:out value="${dto.content }"></c:out> </textarea>
	</td>
</tr>
<tr>
	<td style="width: 45px" colspan="2">
		<input type="button" id="backBtn" value="돌아가기">
		<input type="button" id="replayBtn" value="댓글">
	
	<c:if test="${mem.id eq dto.id }">
	<input type="button" id="upBtn" value="수정">
	<input type="button" id="delBtn" value="삭제">
	</c:if>
	</td>
</tr>
</table>
</form>
<div align="center" style="margin-top: 100px" id="replayadd">
<form action="replayAf.do">
<input type="hidden" value="${dto.seq }" name="seq">
<table border="1" class="list_table">
<col width="30px"><col width="350px">
<tr>
	<th>작성자</th>
	<td><input type="text" name="id" style="width: 99%" value="${mem.id }"> </td>
</tr>
<tr>
	<th>제목</th>
	<td><input type="text" name="title" style="width: 99%"></td>
</tr>
<tr>
	<th colspan="2" align="center">내용</th>
</tr>
<tr>
	<td colspan="2">
		<textarea rows="13" cols="50" style="width: 99%" name="content"></textarea>
	</td>
</tr>
<tr>
	<td colspan="2"><input type="submit" value="답글쓰기" id="replaysumbit"></td>
</tr>
</table>
</form>
</div>
</div>

<script type="text/javascript">
$(document).ready(function () {
	
	$("#replayadd").hide();
	
	$("#backBtn").on('click', function () {
		location.href = "bbslist.do";
	});
	
	$("#upBtn").on('click', function () {
		location.href="bbsupdate.do?seq="+ ${dto.seq};
	});
	
	$("#delBtn").on('click', function () {
		location.href="bbsdelete.do?seq="+${dto.seq};
	});
	
	$("#replayBtn").on('click', function () {
		
		$("#replayadd").toggle("fast");
		
		// location.href="replaybbs.do?seq="+${dto.seq};
	});
});

</script>
</body>
</html>