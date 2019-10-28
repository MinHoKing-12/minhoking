<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 코어태그 사용 하기 위한 링크 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

</head>
<body>
<c:set var="mem" value="${login }"/>

<div align="center">
<form action="bbswriteAf.do">
<input type="hidden" value="1" name="page">
<table class="list_table">
<tr>
	<th>ID</th>
	<td style="text-align: left">
		<input type="text" id="id" style="width: 100%"  value='<c:out value="${mem.id }"></c:out>' readonly="readonly" name="id">
	</td>
</tr>
<tr>
	<th>제목</th>
	<td align="left">
		<input type="text" id="title" placeholder="제목" name="title" style="width: 100%;"><br>
	</td>
</tr>
<tr>
	<th colspan="2">내용</th>
</tr>
<tr>
	<td colspan="2">
	<!-- <textarea rows="15" cols="130" placeholder="내용 입력" id="text" name="content"></textarea> -->
	<textarea name="ir1" id="ir1" rows="10" cols="100">에디터에 기본으로 삽입할 글(수정 모드)이 없다면 이 value 값을 지정하지 않으시면 됩니다.</textarea>
	</td>
</tr>
<tr>
	<td colspan="2">
	<input type="submit" id="subBtn" value="글쓰기">
	</td>
</tr>
</table>
</form>
</div>


</body>
</html>