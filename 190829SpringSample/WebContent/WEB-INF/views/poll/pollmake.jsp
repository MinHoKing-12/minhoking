<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
Calendar cal = Calendar.getInstance();
int tyear = cal.get(Calendar.YEAR);
int tmonth = cal.get(Calendar.MONTH) + 1;
int tday = cal.get(Calendar.DATE);
%>    
    
<form action="pollmakeAf.do" method="post">

<table class="list_table" style="width: 85%">
<colgroup>
	<col width="200px"><col width="500px">
</colgroup>

<tr>
	<th>아이디</th>
	<td style="text-align: left;">
		${login.id }<input type="hidden" name="id" value="${login.id }">
	</td>
</tr>
<tr>
	<th>투표기한</th>
	<td style="text-align: left">
		<select name="syear">
		<%
		for(int i = tyear; i < tyear + 6; i++){
			%>
			<option <%=(tyear+"").equals(i+"")?"selected='selected'":"" %> value="<%=i %>"><%=i %></option>
			<%
		}
		%>
		</select>년
		
		<select name="smonth">
		<%
		for(int i = 1; i <= 12; i++){
			%>
			<option <%=(tmonth+"").equals(i+"")?"selected='selected'":"" %> value="<%=i %>"><%=i %></option>
			<%
		}
		%>	
		
		</select>월
		
		<select name="sday">
		<%
							//월마다 다른 일을 가져오는 부분
		for(int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
			%>
			<option <%=(tday+"").equals(i+"")?"selected='selected'":"" %> value="<%=i %>"><%=i %></option>
			<%
		}
		%>	
		</select>일
		~
		<select name="eyear">
		<%
		for(int i = tyear; i < tyear + 6; i++){
			%>
			<option <%=(tyear+"").equals(i+"")?"selected='selected'":"" %> value="<%=i %>"><%=i %></option>
			<%
		}
		%>
		</select>년
		
		<select name="emonth">
		<%
		for(int i = 1; i <= 12; i++){
			%>
			<option <%=(tmonth+"").equals(i+"")?"selected='selected'":"" %> value="<%=i %>"><%=i %></option>
			<%
		}
		%>	
		
		</select>월
		
		<select name="eday">
		<%
							//월마다 다른 일을 가져오는 부분
		for(int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
			%>
			<option <%=(tday+"").equals(i+"")?"selected='selected'":"" %> value="<%=i %>"><%=i %></option>
			<%
		}
		%>	
		</select>일
	</td>
</tr>

<tr>
	<th>투표 내용</th>
	<td style="text-align: left;">
		<textarea rows="10" cols="50" name="question" placeholder="투표 내용을 입력하세요"></textarea>
	</td>
</tr>

<tr>
	<th>투표 문항수</th>
	<td style="text-align: left">
		<select name="itemcount" onchange="pollchange(this)">
		<%
		for(int i = 2; i <= 10; i++){
			%>
			<option <%=(4+"").equals(i+"")?"selected='selected'":"" %> value="<%=i %>"><%=i %></option>
			<%
		}
		%>
		</select>개
	</td>
</tr>

<tr>
	<th>투표 상세 문항</th>
	<td style="text-align: left">
		<%
			for(int i = 1; i <= 10; i++){
				%>
				<!-- for문에 아이디 값을 하나씩 -->
				<div id='poll<%=i %>'>
					<%=(i+"") %>번:<input type="text" name="poll<%=i %>" size="60"><br>
				</div>
				<%
			}
		%>
	</td>
</tr>

<tr>
	<td colspan="2"> 
		<input type="submit" value="투표만들기" >
	</td>
</tr>

</table>
</form>


<script type="text/javascript">
$(document).ready(function () {
	
	for(i = 5; i<=10; i++){
		$("#poll" + i).hide();
	}
	
});

function pollchange( me ) {	// 보기의 갯수를 갱신한다.
	
	var num = me.options[me.selectedIndex].value;
//	alert(num);

	for(i = 1; i<=10; i++){		// 몽땅 초기화
	//	document.getElementById("poll" + i).value = "";
		// 네임 접근 시 붙여서 사용!!
		$("inpㅇut[name=poll"+i+"]").val("");
		$("#poll" + i).hide();
	}

	for(i = 1; i <=num; i++){	// 보기의 갯수에 맞춰 다시 보여준다.
		$("#poll" + i).show();
	}
}

</script>
