<%@page import="votes.VcommnetDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
 <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
 <script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>
 <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
 <title>KIWOOM SUPPORTERS - 투표게시판  > 댓글관리</title>
 <link rel="stylesheet" href="admin/css/style.css" type="text/css" media="all" />
 <link rel="shortcut icon"  href="main/images/favicon.ico">


<style type="text/css">


</style>

<body>
<%
List<VcommnetDto> list = (List<VcommnetDto>)request.getAttribute("list");

String id = (String)request.getAttribute("id");
String sbbslen = (String)request.getAttribute("bbslen");

int bbslen = Integer.parseInt(sbbslen);

 int bbsPage = bbslen / 10;
		if(bbslen % 10 > 0)  {
			bbsPage = bbsPage + 1;
	}


%>

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
			    <li><a href="./admincontrol?command=freebbslist">자유게시판 관리</a></li>
			    <li><a href="./admincontrol?command=votelist" class="active">투표 관리</a></li>
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
			투표 게시판 > 댓글관리
		</div>
		<!-- End Small Nav -->
		<!-- Main -->
		<div id="main">
			<div class="cl">&nbsp;</div>
			
			<!-- Content -->
			
			<div id="content">
			<div style=" margin-top: 30px;">
			<h1 style="color: #a54165; font-family: sans-serif;">[ <%=id %> ] 님이 작성한 댓글</h1>
			</div>	
				<!-- Box -->
				<div class="box" style="padding: 20px; margin-top: 35px; width: 900px;">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">댓글관리</h2>
					</div>
					<!-- End Box Head -->	
 
					<!-- Table -->
					 <div class="table">
					 <form id="frm" >
					 <input type="hidden" name="command" value="votedelete">
					 <input type="hidden" name="id" value=<%=id%>>
				     <table>
				     <col width="48"><col width="100"><col width="600"><col width="200">
				     <tr>
				       <th><input type="checkbox" id="check" value=""> </th>
				       <th>게시글 번호</th>
				       <th>댓글 내용</th>
				       <th>작성 날짜</th>
				     </tr>
				     <%for(int i = 0 ; i < list.size(); i++ ) 
				     {
				     %>
				     <tr>
				     <td align="center"><input type="checkbox" class="check" name="seq" value="<%=list.get(i).getSeq()%>"></td>
				     <td align="center"><%=list.get(i).getPlayernum()%></td>
				     <td><%=list.get(i).getContent()%></td>
				     <td><%=list.get(i).getWdate()%></td>
				     </tr>
				     <%
				     }
				     %>
				     </table>
				     </form>
						
						<!-- Pagging -->
						<div class="pagging">
						<input type="button" class="button" id="deletebtn" value="회원의 댓글 삭제" />
						<div class="right">
						
						<%for(int i=0; i < bbsPage; i++){ %>
							
							<a href="admincontrol?command=votedetail&id=<%=id%>&Page=<%=i%>" title="<%=i+1 %>페이지"><%=i+1%></a>&nbsp;
						<%} %>
						
						</div>
						</div> 
						<!-- End Pagging -->
						
					</div>
					<!-- Table -->
					
				</div>
				<!-- End Box -->
				
			</div>
		</div>
	  </div>
     </div>
     <script type="text/javascript">
$(document).ready(function () {
	$("#check").click(function() {
		
		if($("#check").prop("checked")){	
	     	$("input.check").prop("checked",true);
	   }else{
		   $("input.check").prop("checked",false);
	    }	
		$("input.check").click(function() {
			$("#check").prop("checked",false);
		});
	});
	
	$("#deletebtn").on('click', function () {
		var checks = $("input[name=seq]");
        var isChecked = false;
		
		for (var i = 0; i < checks.length; i++) {
			if(checks.eq(i).prop("checked")) {
				isChecked = true;
			}
		}
		if(!isChecked) {
			alert("삭제할 댓글을 선택해주세요");
			return;
		}
		  $("#frm").attr("action", "admincontrol").submit();	
	});
});


</script>
</body>
</html>