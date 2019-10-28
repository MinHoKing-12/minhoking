<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:requestEncoding value="utf-8"/> 
    
<style>
table {
    border-collapse: collapse;
}

table, td, th {
    border: 1px solid black;
}
.sunday{
color: red; 
text-align: left;
vertical-align: top;
}
.satday{
color: blue; 
text-align: left;
vertical-align: top;
}
.otherday{
color: black; 
text-align: left;
vertical-align: top;
}
.days2{
font-size:20px;
color: #4D6BB3; 
text-align: center;
vertical-align: middle;
}
.days3{
font-size:20px;
color: #4D6BB3; 
text-align: center;
vertical-align: middle;
background-color:#4D6BB3; color:#FFFFFF; line-height:1.7em; font-weight:normal;
}

.innerTable {
    border: 0px ;
}
</style>

<br>

<div style="text-align: left;">
${year }년

<!-- 1 2 3 4 5 6 7 8 9 10 11 12 --> 
<c:forEach begin="1" end="12" step="1" var="m">
<c:url var="url" value="calendarMonth.do">	
<c:param name="year" value="${year }"/>
<c:param name="month" value="${m }"/>	
</c:url>

<a href="${url }">${m }월</a>

</c:forEach>
</div>

<br>
<table class="list_table" style="width: 85%">
<col width="100"><col width="200"><col width="450"><col width="50">

<tr bgcolor="#09bbaa">
	<th>번호</th><th>시간</th><th>제목</th><th>삭제</th>
</tr>	

<tr>
	<th colspan="4">${year }년 ${month }월</th>
</tr>

<c:forEach items="${callist }" var="cal" varStatus="vs">
<tr>
	<td>${vs.count }</td>
	<td>${cal.wdate }</td>
	<td style="text-align: left;">
		<a href="caldetail.do?seq=${cal.seq }">
			${cal.title }
		</a>
	</td>
	
	<td>
		<form action="caldel.do" method="post">
			<input type="hidden" name="seq" value="${cal.seq }">
			<input type="submit" value="일정삭제">
		</form>
	</td>	
</tr>

</c:forEach>

</table>



















