<%@page import="util.utilMethod"%>
<%@page import="member.MemberDto"%>
<%@page import="votes.VcommnetDto"%>
<%@page import="java.util.List"%>
<%@page import="votes.VoteDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="main/css/main.css">
<style type="text/css">

table.type01 {
    width: 65%;
	border-collapse: collapse;
	text-align: left;
	line-height: 1.5;
	margin: 20px 10px;
}

table.type01 th {

	padding: 10px;
	font-weight: bold;
	vertical-align: top;
    border: 1px solid #ccc; 
	text-align: center;
	color: red;
}
table.type01 td {

	padding: 6.2px;
	vertical-align: top;
	border: 1px solid #ccc;
}
input.update1{
margin-left: 80px;
}
input#btn,input.update1,input.delete1,input.update2,input.noupdate{
    font-size: 11px;
	border: solid 1px #ffffff;
	color : #000000;
	border: 1px solid;
    background: #ffffff;
	zoom: 1;
	display: inline;
	vertical-align: baseline;
	outline: none;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	text-shadow: 0 1px 1px rgba(0,0,0,.3);
	-webkit-border-radius: .5em; 
	-moz-border-radius: .5em;
	border-radius: .5em;

}
input.update1:hover,input.delete1:hover,input.update2:hover,input.noupdate:hover{
    background-color: #ffffff;
}

</style>
<meta charset="UTF-8">
<title>KIWOOM SUPPORTERS - 투표게시판</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<link rel="shortcut icon"  href="main/images/favicon.ico">
</head>
<body>
<body class="is-preload">
	<div id="page-wrapper">

		<!-- Header -->
		<div id="header">

			<!-- My Menu Bar -->
	<%
		String seq = request.getParameter("seq");//선수고유넘버==페이지넘버
		VoteDto dto = (VoteDto) request.getAttribute("who");//어떤선수냐
		VcommnetDto comdto = (VcommnetDto) request.getAttribute("reply");//리플
		List<VcommnetDto> list = (List<VcommnetDto>) request.getAttribute("list");
		
		MemberDto user = (MemberDto) session.getAttribute("login");//로그인한거
		
		
		String slen = (String)request.getAttribute("allpage");
		System.out.println("현재페이지 리스트 갯수: " + slen);
		int len = Integer.parseInt(slen);

        int bbsPage = len / 10;
        if(len % 10 > 0){
	    bbsPage = bbsPage + 1;
	    }
    
		String photoname = "/baseball/";
		photoname += dto.getPhotoname();
	%>
	
    <%
	if (user.getNickname() == null) {
			%>
			<div id="mymenu" style="font-size: 12px;" align="right">
				<a href="../memcontrol?command=login">로그인</a>
				<a href="../login/account.jsp">회원가입</a>
			</div>
			<%
				} else {
			%>
			<div id="mymenu" style="font-size: 12px;" align="right">
				<b><%=user.getNickname()%></b>님 환영합니다!
				 <a href="./mypagecontrol?command=mypage">마이페이지</a>
				 <a href="./memcontrol?command=logout">로그아웃</a>
			</div>
			<%
				}
			%>
			<!-- Logo -->
			<a href="./memcontrol?command=main" id="logo"><img src="./main/images/ks.png" style="width: 400px" ></img></a><br><br>

			<!-- Nav -->
			<nav id="nav">
				<ul>
					<li><a href="./memcontrol?command=main">Home</a></li>
					<li><a href="./calendarcontrol?command=calendar">경기 일정</a></li>
					<li><a href="./sellcontrol?command=list&page=1">거래 게시판</a></li>
					<li><a href="./freeControl?command=list">자유 게시판</a></li>
					<li class="current"><a href="./VoteControl?command=start">투표 게시판</a></li>
					<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank">경기 하이라이트</a></li>
				</ul>
			</nav>

		</div>
	

	<div align="center">
				<img  style="width:100%; height:100%;" src="./vote/voteban.png">
			<input type="hidden" name="command" value="pick"> 
			<input type="hidden" id="hi" name="seq" value=<%=seq%>> 
			<input type="hidden" name="nickname" id="nickname" value=<%=user.getNickname()%>>

            <br><br>
            
			<h1 style="font-size: 40px; color: #820024;">★<%=dto.getName()%>★ 선수에게 투표해주세요</h1>
			<br><br> 
			
			<table style="size: 70%; width: 50px;">
			
			<tr>
			<td align="center">
				 <img alt="이미지없다" style="margin-bottom: 10px" src="<%=request.getContextPath() + photoname%>">
				 <div>
				     <span style="font-weight: bold;">키: <%=dto.getHeight()%></span><br>
					 <span style="font-weight: bold;">몸무게: <%=dto.getWeight()%></span><br>
					 <span style="font-weight: bold;">포지션: <%=dto.getPosition()%></span><br>
				 </div>
			 </td>
			 
			
			</tr>
			</table> 
			<br><br>
			
			<div align="center" style="font-size: 16px">
			<p  style="font-size: 1.5em; font-weight: bold; color: pink; text-shadow: 0 0.9px 1px #000; ">[ 선수를 위한 응원 한마디 ]</p>

			 <table class="type01">
				<col width="200"><col width="600"><col width="100">
				<tr>
					<th>닉네임</th>
					<th></th>
					<th></th>

				</tr>

				<%
					if (list == null || list.size() == 0) {
				%>
				<tr>
					<td colspan="3" align="center">작성된 댓글이 없습니다</td>
				</tr>
				<%
					}

					else {

						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getDel() == 3) { //삭제된글
				%>
				<tr>
					<td colspan="3" style="color: red;" align="center">삭제된 댓글 입니다</td>
				</tr>
				<%
					} else {
				%>

				<tr>
					<td style="font-weight: bold" align="center"><%=list.get(i).getNickname()%></td>

					<%
						if (user.getNickname().equals(list.get(i).getNickname())) {
					%>
					<td class="td"><%=list.get(i).getContent()%>
					<input type="hidden"   class="tddate"  value='<%=list.get(i).getContent()%>'>
					<input type="hidden"   class="deleseq" value='<%=list.get(i).getSeq()%>'> 
					<input style="align-items:flex-end;  padding:0em 0em;  height: 25px;  background-color:rgb(241,241,241);" class="update1" type="button" value="수정"> 
					<input style="align-items:flex-end; padding:0em 0em; height: 25px;   background-color:rgb(241,241,241);" class="delete1" type="button" value="삭제"> 

					</td>

					<td class="ttd" hidden="">
					<textarea class="updatetext" rows="5" cols="50" style="font-size: 15px;"><%=list.get(i).getContent()%></textarea>
						<input type="hidden" class="seq" value='<%=list.get(i).getSeq()%>'>
						<input  class="update2" type="button" value="등록"> 
						<input  class="noupdate" type="button" value="취소">
					</td>
				<td><%=utilMethod.dateEdit(list.get(i).getWdate()) %> </td>
				</tr>
				<%
					}
                 else {
				%>
                    
				<td><%=list.get(i).getContent()%></td>
                <td><%=utilMethod.dateEdit(list.get(i).getWdate())%></td>
				<%
					}
							}
						}
					}
		 %>
			</table>
			</div>
			<% 
           		for(int i = 0;i < bbsPage; i++){
           		
				%>
				
				<a href="VoteControl?command=vote&Page=<%=i%>&seq=<%=seq %>" title="<%=i+1 %>페이지" 
		style="font-size: 13pt; color: #990000; font-weight: bold;">[<%=i+1 %>]</a>&nbsp;
			
			<%}%>
       
			<br><br> 
			<div align="center">
			<span style="font-weight: bold; font-size: 20px; display: inline-flex;"><%=user.getNickname() %>  님 : </span>
	     	<input id="textt" name="text" type="text" maxlength="30" style="width: 700px; height: 10px; margin-left:20px; display: inline-flex; font-size: 17px;"> 
			<input type="button" id="btn" value="등록" style="display: inline-flex; margin-left:20px; min-width: 5em;"> 
			</div>
			
		    <br><br><br><br>
			<input class="button" type="button" value="투표메인으로" onclick="location.href='VoteControl?command=list'"> 
			<input class="button" type="button" value="투표하기" id="pickbtn"> 
			

	<!-- Footer -->
		<div id="footer">

			<!-- Icons -->
			<ul class="icons">
				<li><a
					href="https://twitter.com/hashtag/%ED%82%A4%EC%9B%80%ED%9E%88%EC%96%B4%EB%A1%9C%EC%A6%88"
					target="_blank" class="icon brands fa-twitter"><span
						class="label">Twitter</span></a></li>
				<li><a href="https://www.facebook.com/kiwoomheroesbaseballclub"
					target="_blank" class="icon brands fa-facebook-f"><span
						class="label">Facebook</span></a></li>
				<li><a href="https://www.instagram.com/heroesbaseballclub"
					target="_blank" class="icon brands fa-instagram"><span
						class="label">Instagram</span></a></li>
				<li><a href="https://www.youtube.com/user/heroesbaseballclub"
					target="_blank" class="icon brands fa-youtube"><span
						class="label">Youtube</span></a></li>
			</ul>

			<!-- Copyright -->
			<div class="copyright">
				<ul class="menu">
					<li>&copy; Untitled. All rights reserved</li>
					<li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
				</ul>
			</div>
		</div>


		<!-- Scripts -->
		<script src="main/js/jquery.min.js"></script>
		<script src="main/js/jquery.dropotron.min.js"></script>
		<script src="main/js/browser.min.js"></script>
		<script src="main/js/breakpoints.min.js"></script>
		<script src="main/js/util.js"></script>
		<script src="main/js/main.js"></script>


			
	<script type="text/javascript">
	$(function() {
		
			$("#pickbtn").click(function() {
				location.href="VoteControl?command=pick&seq="+$("#hi").val()+"&nickname=<%=user.getNickname()%>";

			});
			
	
	        $("#btn").click(function() {
	        	
	        	if($("#textt").val() == null || $("#textt").val() == ""){
	        		alert("응원 댓글을 달아주세요!");
	        	}else{
	        	location.href="VoteControl?text="+$("#textt").val()+"&command=command&seq="+$("#hi").val()+"&nickname=<%=user.getNickname()%>";
	        	
	        	}
				});

			$(".update1").click(function() {

				var parent = $(this).parent().parent(); // 수정버튼눌렀을때 부모의 부모(tr 태그 가지고오는거)
				//alert(parent.html());

				parent.children(".td").hide();
				parent.children(".ttd").show();
			});
					
			$(".noupdate").click(function() {

		        var parent = $(this).parent().parent(); // 취소버튼눌렀을때 부모의 부모(tr 태그 가지고오는거)
				//alert(parent.html());
                
		         parent.children(".ttd").hide();
				 parent.children(".td").show();
              });

			$(".update2").click(function() {
				    var parents = $(this).parent(); // 등록버튼눌렀을때 부모의 부모(tr 태그 가지고오는거)
					//alert(parents.html());
					//alert(parents.children(".updatetext").val()); //textArea내용
					//alert(parents.children(".seq").val());
					var text = parents.children(".updatetext").val();
					var seqseq = parents.children(".seq").val();
					location.href = "VoteControl?command=update&text="+ text+ "&seq="+ seqseq+ "&num=" + $("#hi").val();
					});

			$(".delete1").click(function() {
				   var parent = $(this).parent(); // 삭제버튼눌렀을때 부모의 부모(tr 태그 가지고오는거)
					//alert(parent.html());
					//alert(parent.children(".deleseq").val()); // 삭제할 댓글의 내용
					var seq = parent.children(".deleseq").val();
					location.href = "VoteControl?command=deleted&seq="+ seq+ "&num="+ $("#hi").val();
					});
			});
			</script>
		    </div>
	        </div>
            </body>
            </html>