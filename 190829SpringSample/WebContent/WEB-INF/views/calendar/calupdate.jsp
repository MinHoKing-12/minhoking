<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:requestEncoding value="UTF-8"/>

<script type="text/javascript">

</script>

<form id="frm" action="calupdateAf.do" method="post">
<input type="hidden" name="seq" value="${cal.seq }">
<table class="list_table" style="width:85%;">

<colgroup>
<col style="width:200px;" />
<col style="width:auto;" />
</colgroup>

<tr>
	<th>아이디</th>
	<td style="text-align: left">${cal.id}</td>
</tr>
<tr>
	<th>제목</th>
	<td style="text-align: left">
		<input type="text" name="title" value="${cal.title}" style="width: 300px">
	</td>
</tr>
<tr>
	<th>일정</th>
	<td style="text-align: left">${cal.wdate}</td>
</tr>
<tr>
	<th>내용</th>
	<td style="text-align: left"><textarea  name='content' rows="20" cols="60" 
	>${cal.content}</textarea></td>
</tr>
<tr>
	<td colspan="2" align="center">
		<input type="submit" value="수정완료">
	</td>
</tr>

</table>
</form>
<a href='${urls}'>일정보기</a>