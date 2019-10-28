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

<table class="list_table">
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