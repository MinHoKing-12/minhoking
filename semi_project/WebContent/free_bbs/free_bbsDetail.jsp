
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="member.MemberDto"%>
<%@page import="free_bbs.free_bbs_comment_likeDao"%>
<%@page import="free_bbs.free_bbs_likeDao"%>
<%@page import="java.util.List"%>
<%@page import="free_bbs.free_bbs_commentDto"%>
<%@page import="free_bbs.free_bbs_commentDao"%>
<%@page import="free_bbs.free_bbsDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
public static String dateEdit(String strDate) {
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    String str = "";
    long min = 0;
    long hour = 0;
    Date w_date = new Date();
    
    try{
       w_date = sdf.parse(strDate);
       Date today = new Date();
       long diff = today.getTime() - w_date.getTime();
       min = diff / (1000 * 60);
       hour = diff / (1000 * 60 * 60);
    }catch (Exception e) {
       e.printStackTrace();
    }
    
    if(min < 60) {
       str = min + "분 전";
    }else if (min >= 60 && hour < 24) {
       str = hour + "시간 전";
    }else if (hour >= 24) {
       str = new SimpleDateFormat("yyyy-MM-dd").format(w_date);
    }
    
    return str;
 }
%>



<%!
// 로그인세션
String LoginId = "";
String LoginNick = "";
%>
<%
// seq : bblist -> controller -> 여기
int seq = (int)(request.getAttribute("seq"));
System.out.println("free_bbsDetail.jsp int Seq : " + seq);
MemberDto user = (MemberDto)session.getAttribute("login");
if(user == null){
	
}else if(user!=null){
	LoginId = user.getId();
	LoginNick = user.getNickname();
}

//여기detailpage likecount
int likecount = (int)(request.getAttribute("likecount"));
System.out.println("여기 seq:" + seq + "detail페이지의 likecount는 " + likecount + "입니다");

//like check
free_bbs_likeDao likedao = free_bbs_likeDao.getInstance();
boolean boolLike = likedao.checkLike(seq, LoginId);
System.out.println("♥♥♥이 페이지의 하트를 누른 적 있습니까?♥♥♥ : " + boolLike);

//rPageNumber
int rPageNumber = 1;
if(request.getAttribute("rPageNumber") != null){
	rPageNumber = (int)(request.getAttribute("rPageNumber"));
	System.out.println("☆현재 댓글pageNumber는 " + rPageNumber);
}

//replyCount
int replyCount = 0;
if(request.getAttribute("replyCount") != null){
	replyCount = (int)(request.getAttribute("replyCount"));
	System.out.println("☆현재 페이지의 총 댓글갯수는 " + replyCount);
}

//페이지넘버 * 5
int rPageNumber5 = rPageNumber*5; 
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>KIWOOM SUPPORTERS - 자유게시판</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
	<link rel="stylesheet" href="./main/css/main.css" />
	<link rel="shortcut icon"  href="main/images/favicon.ico">
	<link href="./sellcss/global.css" rel="stylesheet" type="text/css" />
<style type="text/css">
table.type01 {
    border-collapse: collapse;
    text-align: left;
    line-height: 1.5;
    margin : 20px 10px;
}
table.type01 th { 
    /* width: 150px; */
    padding: 10px;
    font-weight: bold;
    vertical-align: top;
    border: 1px solid #ccc;
    text-align: center;    
    background-color: white;
    color: #820024; 
}
table.type01 td {
    /* width: 350px; */
    padding: 10px;
    vertical-align: top;
    border: 1px solid #ccc;
    background-color: white; 
}

table.type02 {
    border-collapse: collapse;
    text-align: left;
    line-height: 1.5;
    margin : 20px 10px;
}
table.type02 th { 
    /* width: 150px; */
    padding: 10px;
    font-weight: bold;
    vertical-align: top;
    border: 0px solid #ccc;
    text-align: center;    
    background-color: white;
    color: #820024; 
}
table.type02 td {
    /* width: 350px; */
    padding: 10px;
    vertical-align: top;
    border: 0px solid #ccc;
    background-color: white; 
}

button{
	background-color: #820024;
	
}
.replyBtn{
	background-color: yellow;
}
.bestReply{
	background-color: red;
}
.photoimg {
	width: 270px;
	height: 200px;
}
.glassimg {
	width: 20px;
	height: 20px;
}
a:link { color: black; text-decoration: none;}
a:visited { color: black; text-decoration: none;}
a:hover { color: #820024; text-decoration: none;} 

#like-div {
 width: 100%;  
 height: 100%;  
 top: 0px;
 left: 0px;
 position: fixed;
 
 margin-left: fixed; 
 margin-right: auto;
 margin-top: auto;
 margin-bottom: auto;

 display:none;  
 opacity: 0.7;  
 background-color: #fff;  
 z-index: 99;  
 text-align: center; 
 }
#like-image {  
position: absolute;  
top: 50%;  
left: 44%; 
 z-index: 100; 
 vertical-align: middle;
}
 
 #loading {
 width: 100%;  
 height: 100%;  
 top: 0px;
 left: 0px;
 position: fixed;  
 display: none;  
 opacity: 0.7;  
 margin-left: auto; 
 margin-right: auto;
 background-color: #fff;  
 z-index: 99;  
 text-align: center; } 
 
 #loading-image {  
 position: absolute;  
 top: 50%;  
 left: 44%;
vertical-align: middle;
 z-index: 100; }
 
 select {
direction:rtl;
width: 115px;
height: 35px;
padding: .1em .3em;
border: 0px solid #820024;
font-size: 15px;
font-family: inherit;
background: url('./free_bbs/sorting.png') no-repeat -10% 55%;
border-radius: 0px;
-webkit-appearance: none;
-moz-appearance: none;
appearance: none;

position: relative;
right : -30px;
top : -4.0px;

}
p{
position: relative;  
top: 11px;
}

.replyAdding{
   position: relative;
   margin-left: 45%;
}
#marginn{
   position: relative;
   margin-top: 12px; 
}
.reReplyDelBtn{
   position: relative;
   margin-top: 30px;
}
.love{
	border-bottom: none;
	border-left: none;
	border-right: none;
}

</style>


</head>

<body class="is-preload">
<div id="loading"><img id="loading-image" src="images/free_bbs/loading.gif" alt="Loading" /></div>
<div id="like-div"><img id="like-image" src="images/free_bbs/heartgif.gif" alt="Like" /></div>

<div id="page-wrapper">
	<!-- Header -->
		<div id="header">
		
	<!-- My Menu Bar -->
			<%
			if(session.getAttribute("login") == null) {
			%>
				<div id="mymenu" style="font-size: 12px;" align="right">
					<a href="./memcontrol?command=login">로그인</a>|<a href="./login/account.jsp">회원가입</a>
				</div>
			<%
			}else {
				user = (MemberDto)session.getAttribute("login");
				%>
				<div id="mymenu" style="font-size: 12px;" align="right">
					<b><%=user.getNickname() %></b>님 환영합니다!   <a href="./mypagecontrol?command=mymsgbox">마이페이지</a>|<a href="./memcontrol?command=logout">로그아웃</a>
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
						<li class="current"><a href="./freeControl?command=list">자유 게시판</a></li>
						<li><a href="./VoteControl?command=start">투표 게시판</a></li>
						<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank">경기 하이라이트</a></li>
					</ul>
				</nav>
		</div>
	<!-- Main -->
	<img  style="width:100%; height: 100%;" alt="bb" src="images/free_bbs/freeban.png">
	<!-- 	<section class="wrapper style1"> -->
	<br><br>
			<div class="container">
				<div id="content">
			

<div align="center">


<!-- ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ  -->


<%
	free_bbs.free_bbsDao dao = free_bbs.free_bbsDao.getInstance(); 
	
	// detailList에서 넘겨받은 seq, id
	//dao.readCount(seq);	
		
	free_bbsDto dto = dao.getDetailBbs(seq);
	
	// list에서 클릭한 후 ID값을 DAO:detailGet에 넣고 DTO로 받아온 값
	int dSeq = dto.getSeq();
	String dId = dto.getId();
	String dNickname = dto.getNickname();				// 게시판 작성 닉네임		
	String dTitle = dto.getTitle();
	String dContents = dto.getContent();
	String dWdate = dto.getWdate();
	int dReadcount = dto.getReadcount();
	int dBlike = dto.getBlike();
	int dReport = dto.getReport();	
	
	System.out.println("detail페이지 로그인 닉네임 " + LoginNick);
	System.out.println("detail페이지 작성자 닉네임 " + dNickname);
%>

<div align="center">
<form action="freeControl" id="detailFrm" method="post">
<input type = "hidden" id="delOrUp" name="command" value=""> 		<!--value값은 수정/삭제 버튼 눌렀을 때 set  -->
<input type="hidden" name="seq" value=<%=dSeq %>>
<table border="1" class="type01">
	<col width="100"><col width="500">
	<tr>
		<th >
			닉네임
		</th>
		<td>
		<%=dNickname %>
		</td>
	</tr>
	
	<tr>
		<th>
			조회수
		</th>
		<td>
		<%=dReadcount %>
		</td>
	</tr>
		
	<tr>
		<th>
			작성 날짜
		</th>
		<td>
			<%=dWdate %> 
		</td>
	</tr>	
		
	<tr>
		<th>
			제목
		</th>
		<td>
			<%=dTitle %>
		</td>
	</tr>
	
	<tr height="250px">
		<th>
			내용
		</th>
		<td>
			<%=dContents %>
		</td>
	</tr>
	<tr class='love'>
		<td colspan="2" align="center" class="heartPosition">
		<%if(boolLike == true){		// 이미 눌려져있을 때(가득찬 하트) 아이디가 이미 있을 때
		%>
			<img style="cursor: pointer" src='images/free_bbs/like.png' class="fullHeart" onclick="unlikefunc()" alt="x" width="25" height="25">
				<%-- <br><span class='likeCount'><%=	likecount %></span> --%>
		<%
		}else{
		%>
			<img style="cursor: pointer" src='images/free_bbs/like2.png' class="emptyHeart" onclick="likefunc()" alt="x" width="25" height="25">
		<%
		}
		%>
		<br><span class='likeCount'><%=likecount %></span>		
		</td>
		
	</tr>
</table>
</form>

		<%
		if (LoginId.equals(dId)) {
		%>
		
		<button type="button" id="deleteBtn">삭제</button>
		<button type="button" id="updateBtn">수정</button>
		<%
			}
		%>

<!-- REPLY 댓글 -->

<%
//★comment like checking
free_bbs_comment_likeDao clikedao = free_bbs_comment_likeDao.getInstance();
//boolean clikeBool = clikedao.checkReplyLike(replySeq, id);
%>
	
<%
List<free_bbs_commentDto> list = (List<free_bbs_commentDto>)request.getAttribute("replyList");
List<free_bbs_commentDto> replylistByseq = (List<free_bbs_commentDto>)request.getAttribute("replylistByseq");
%>	
<form action="freeControl" id="replyFrm" method="post">
<input type ="hidden" name="command" value="forReply">	
<input type="hidden" name="replyId" value=<%=LoginId %>>
<input type="hidden" name="parentSeq" value=<%=dSeq %>>			<!-- 게시글 seq값을 가지고 controller로 데려가기  -->
<br>
<!-- 댓글 입력받는 부분  -->
<div align="center" >
<table border="1" id='replyAddTable' class="type02">  <!-- class="type02" -->
<tr style='width:200' >	
	<td width=30></td>
	<%if(!LoginId.equals("")){ %>
	<td align="center" width="140">
		<input type="hidden" name="replyNickname" style="width:150px;" readonly="readonly" value="<%=LoginNick %>">
		<p><b><%=LoginNick %></b></p>			 
	</td>
	<td >
		<input type="text" name="replyContent" id='replyC' style="width:770px; display : inline-flex;" >
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" id="replyBtn">등록</button>
	</td>	
	<td width=100></td>
	<%} %>
</tr>
</table>
</div>
<%if(replyCount!=0){%>
<div style="width: 300px; float: right; margin-bottom:15px;">
	<select id="order" onchange="orderFunc()">
		<!-- <option id="orderStandard" value="" selected='selected'>정렬기준</option> -->
		<option value="1" selected='selected' style="height:50">최근 날짜순</option>
		<option value="2">인기 댓글순</option>
		<option value="3">과거 날짜순</option>
	</select>
</div>
<%} %>
<div id= "replyTbody">

</div>
<table border="1" id="replyTable" class="type02" >  <!-- class="type02" -->
	<col width="140"><col width="400"><col width="65"><col width="70"><col width="70"><col width="85">
	<!-- <col width="15%"><col width="40%"><col width="15%"><col width="15%"><col width="15%"><col width="15%"> -->
	

<tbody id="replyTable2">

   <!-- 댓글 최신순 5개 -->

<%
   for(int i = 0; i < replylistByseq.size(); i++){
	   int i2= i + 1; 
	   free_bbs_commentDto mac = replylistByseq.get(i);	   
%>

   <tr>      
       <th>        
         <%=mac.getNickname() %>      
           
       </th> 
       <td>
       	 <div class="<%=mac.getSeq() %>"><%=mac.getContent() %></div>
       </td>
       <td>
       		<font size='3px'><%=mac.getWdate()%></font> 
       </td>
       <td align="center" class="replyLikeChecking">
    	<span id="<%=mac.getSeq() %>">
<%if(clikedao.checkReplyLike(mac.getSeq(), LoginId)==false || LoginId=="") {
%>		<!-- ♡  -->
       	 <img src='images/free_bbs/like2.png' id="<%=mac.getSeq() %>emptyHeart" class="replyEmptyHeart" replySeqforLike="<%=mac.getSeq() %>" alt="x" width="25" height="25" style='cursor: pointer'>	       	 
<%   
    }else{     	
%>   	<!-- ♥ -->
    	<img src='images/free_bbs/like.png' id="<%=mac.getSeq() %>fullHeart" class="replyFullHeart" replySeqforLike="<%=mac.getSeq() %>" alt="x" width="25" height="25" style='cursor: pointer'>
<%
    }
%>	    
	    </span> 
	    <br><span class="<%=mac.getSeq() %>likeCount"><font size=3px;><%=mac.getBlike() %></font></span>	
    	</td>
       <td>
       		<%if(mac.getReplyCnt()!=0){ %>
       			<button type="button" id='marginn' class="<%=mac.getSeq() %>noAnswer" onclick="reReply(<%=mac.getSeq() %>)" replysequence="<%=mac.getSeq() %>" >답글<span class="<%=mac.getSeq() %>reReplyCount"><%=mac.getReplyCnt() %>개 모두보기</span></button>
       		<%}else{ %>
       			<button type="button" id='marginn' class="<%=mac.getSeq() %>noAnswer" onclick="reReply(<%=mac.getSeq() %>)" replysequence="<%=mac.getSeq() %>" >답글</button>
       		<%} %>
       		<button type="button" id='marginn' class="<%=mac.getSeq() %>yesAnswer" onclick="reReplydel(<%=mac.getSeq() %>)" style="display:none;" >접기</button> 	
       </td>        
       
       <% if( LoginId.equals(mac.getId()) ){ %>   
       <td align='left'>       		
       		<button type="button" id="rDeleteBtn" value="<%=mac.getSeq() %>" class="rDeleteBtn">삭제</button>&nbsp;
       		<button type="button" id="rUpdateBtn" class="rUpdateBtn" value="<%=mac.getContent() %>" value2="<%=mac.getSeq() %>" >수정</button>
       		<div class="<%=mac.getContent() %>"></div>       		
       </td>
      <%} %>
   </tr>   
    
   <!--대댓글  -->
   <tr class='reReplyTd'></tr>
   
   <tr id="<%=mac.getSeq() %>block"></tr>								   <!-- 답글 입력 삽입 부분  -->
    
   <tbody style="display:hidden" class="<%=mac.getSeq() %>block2"></tbody> <!--  답글 삽입 부분 -->
<% } %>  

</tbody>
</table>

</form>

</div>
<!--♥ Form for reply delete + update  -->
<form action="freeControl" id="operatingReplyFrm" method="post">
	<input type = "hidden" id="command" name="command" value="">
	<input type = "hidden" name="bbsSeq" value="<%=seq %>">	
	<input type = "hidden" id="hdnReplySeq" name="replySeq" value="">				
	<input type = "hidden" id="hdnUdtReplyContent" name="hdnUdtReplyContent" value="">
</form>
<!--♥ Form for reReply Add(부모님댓글 seq, 대댓글 content)  -->
<form action="freeControl" id="reReplyFrm" method="post">
	<input type = "hidden" name="command" value="reReplyAdd">
	<input type="hidden" name="seq" value=<%=dSeq %>>						
	<input type = "hidden" name="reReplyNickname"value="<%=LoginNick %>">	
	<input type = "hidden" id="parentReplySeq" name="parentReplySeq" value="">		
	<input type = "hidden" id="reReplyContent" name="reReplyContent" value="">				
</form>


<%free_bbs_commentDao cDao = free_bbs_commentDao.getInstance();
List<free_bbs_commentDto> reReList = cDao.getReCommentList(1); %>

<!-- 스크립트여기  -->

</div>
<!-- </section> -->
</div>

<script>
	
	var _rPageNumber = 1;
	
	$(document).ready(function () {
		$("#replyTable:last-of-type").find('tr:last').after("<tr class='addingTR'><td colspan='6'><button type='button' class='replyAdding'>댓글 더보기</button></td></tr>");
		//$("#replyTable").append("<button class='replyAdding'>댓글 더보기</button>");
	}); 
	
	$(document).on('click','.replyAdding',function(){
	//$(document).ready(function() {
	//	$(window).scroll(function(){
			var order = $("#order").val();
			//alert("정렬기준 : " + order);
				
			$('.addingTR').remove();
			
			var pageSeq = <%=seq %>;
	        var loginId = "<%=LoginId %>";
	        
	        _rPageNumber++;
			
		        $.ajax({
					url: "freeControl?command=addReplylist",
					type: "GET",
					data: {_rPageNumber:String(_rPageNumber), pageSeq:String(pageSeq), loginId:loginId, order:order},
					datatype: "json",
					success: function( obj ){
						
						console.log('success');
						var data = JSON.parse(obj);
							
						//alert(data.length);
						
						//	$("#loading").attr('style', "display:block;")
						//setTimeout(function() {	
						//		$("#loading").attr('style', "display:none;")
								additionalReply(data);
						//	},1200);
							
						
					},
					error: function(){
						alert("error");
					}
			   });  
		// });   
	});
	
	//작업중
	function orderFunc(){
		
		$('#replyTable').remove();
	
			var order = $("#order").val();
			
			$('#orderStandard').val(order);
			
			console.log(order);
			
			var pageSeq = <%=seq %>;                                             
	        var loginId = "<%=LoginId %>";
	         
	        _rPageNumber = "1";
			
		       $.ajax({
					url: "freeControl?command=addReplylist",
					type: "GET",
					data: {_rPageNumber:String(_rPageNumber), pageSeq:String(pageSeq), loginId:loginId, order:order},
					datatype: "json",
					success: function( obj ){
						console.log('success');
						var data = JSON.parse(obj);
						  $("#loading").attr('style', "display:block;");
					        setTimeout(function() {
								$("#loading").attr('style', "display:none;");
							},900);
						sortingReplylist(data);	
							
					},
					error: function(){
						alert("error");
					}
			   }); 
		
	}
	
	function sortingReplylist(data){
		 
		 var str = "";
			
		 	str +=  "<table border='1' class='type02' id='replyTable'>";
		    str += "<col width='120'><col width='400'><col width='65'><col width='70'><col width='110'><col width='65'>";
			
		    for(var i=0; i< data.length; i++){
			
				//시퀀스
				str += "<tr class='"+data[i].seq+"replySpace2'>";
				//닉네임
				str += "<th>" + data[i].nickname + "</th>"	;
				//내용
				str += "<td>" + "<div class='" + data[i].seq +"'>"+ data[i].content + "</div></td>";
				//날짜
				str += "<td><font size='3px'>" + data[i].wdate +"</font></td>";				
				
				str += "<td align='center' class='replyLikeChecking'>";
				str += "<span id='" + data[i].seq + "'>";
					
				if(data[i].likecheck == false ){
						str += "<img src='images/free_bbs/like2.png' id='"+data[i].seq+"emptyHeart' class='replyEmptyHeart' replySeqforLike='"; 	
				}else if(data[i].likecheck == true){
						str += "<img src='images/free_bbs/like.png' id='"+data[i].seq+"fullHeart' class='replyFullHeart' replySeqforLike='";
				}
				
				str += data[i].seq +"' alt='x' width='25' height='25' style='cursor:pointer'>";
				str += "</span><br><span class='"+data[i].seq+"likeCount'><font size=3px>"+data[i].blike+"</font></span></td>";
								
				if(data[i].reReplyCount != false ){	
					//답글
					//str += "<td><button type='button' class='noAnswer2' id='" + data[i].seq + "noAnswer2' onclick='reReply(" + data[i].seq + ")'";
					//str += " replysequence='"+data[i].seq+"' >답글"+data[i].reReplyCount+"개 모두보기</button>";
					str += "<td><button type='button' class='noAnswer2' id='" + data[i].seq + "noAnswer2' onclick='reReply(" + data[i].seq + ")'";
					str += " replysequence='"+data[i].seq+"' >답글";
					str += "<span class='"+ data[i].seq +"reReplyCount'>" + data[i].reReplyCount+"개 모두보기</span></button>";
				}else{
					str += "<td><button type='button' class='noAnswer2' id='" + data[i].seq + "noAnswer2' onclick='reReply(" + data[i].seq + ")'";
					str += " replysequence='"+data[i].seq+"' >답글</button>";
				}
								
				//답글2
				str += "<button type='button' class='yesAnswer2' id='" + data[i].seq + "yesAnswer2' onclick='reReplydel("+ data[i].seq + ")'";
				str += " style='display:none;' replysequence='"+data[i].seq+"'>접기</button></td>";
				
				
				//수정,삭제
				if('<%=LoginId%>'!=null){
					if((data[i].id)=='<%=LoginId%>'){
						str += "<td><button type='button' id='rDeleteBtn' value='"+data[i].seq+"' class='rDeleteBtn'>삭제 </button><br>";
						str += "<button type='button' id='rUpdateBtn' class='rUpdateBtn' value='"+data[i].content;
						str += "' value2='" + data[i].seq + "' >수정</button><div class='"+data[i].content+"' </div></td></tr>";
					}
				}	
			}
			str += "<tr class='addingTR'><td colspan='6'><button type='button' class='replyAdding'>댓글 더보기</button></td></tr>";
			str += "</table>";
			
			$("#replyTbody").append(str);
		
		}
	
	function additionalReply(data){
		for(var i=0; i< data.length; i++){
		 
			var str = "";
			
			//시퀀스
			str += "<tr class='"+data[i].seq+"replySpace2'>";
			//닉네임
			str += "<th>" + data[i].nickname + "</th>"	;
			//내용
			str += "<td>" + "<div class='" + data[i].seq +"'>"+ data[i].content + "</div></td>";
			
			str += "<td><font size='3px'>" + data[i].wdate +"</font></td>";
			
			str += "<td align='center' class='replyLikeChecking'>";
			str += "<span id='" + data[i].seq + "'>";
				
			if(data[i].likecheck == false ){
					str += "<img src='images/free_bbs/like2.png' id='"+data[i].seq+"emptyHeart' class='replyEmptyHeart' replySeqforLike='"; 	
			}else if(data[i].likecheck == true){
					str += "<img src='images/free_bbs/like.png' id='"+data[i].seq+"fullHeart' class='replyFullHeart' replySeqforLike='";
			}
			
			str += data[i].seq +"' alt='x' width='25' height='25' style='cursor:pointer'>";
			str += "</span><br><span class='"+data[i].seq+"likeCount'><font size=3px>"+data[i].blike+"</font></span></td>";
			
			if(data[i].reReplyCount != false ){	
				//답글
				str += "<td><button type='button' class='noAnswer2' id='" + data[i].seq + "noAnswer2' onclick='reReply(" + data[i].seq + ")'";
				str += " replysequence='"+data[i].seq+"' >답글";
				str += "<span class='"+ data[i].seq +"reReplyCount'>" + data[i].reReplyCount+"개 모두보기</span></button>";
			}else{
				str += "<td><button type='button' class='noAnswer2' id='" + data[i].seq + "noAnswer2' onclick='reReply(" + data[i].seq + ")'";
				str += " replysequence='"+data[i].seq+"' >답글</button>";
			}
			//답글2
			str += "<button type='button' class='yesAnswer2' id='" + data[i].seq + "yesAnswer2' onclick='reReplydel("+ data[i].seq + ")'";
			str += " style='display:none;' replysequence='"+data[i].seq+"'>접기" +"</button></td>";
			
			//수정,삭제
			if('<%=LoginId%>'!=null){
				if((data[i].id)=='<%=LoginId%>'){
				str += "<td><button type='button' id='rDeleteBtn' value='"+data[i].seq+"' class='rDeleteBtn'>삭제 </button><br>";
				str += "<button type='button' id='rUpdateBtn' class='rUpdateBtn' value='"+data[i].content;
				str += "' value2='" + data[i].seq + "' >수정</button><div class='"+data[i].content+"' </div></td></tr>";
				}
			}		
			str += "<tr class='addingTR'><td colspan='6'><button type='button' class='replyAdding'>댓글 더보기</button></td></tr>";
			$("#replyTable:last-of-type").find('tr:last').after(str);
			
		}			 
		//$("#replyTable:last-of-type").find('tr:last').after("<tr class='addingTR'><td colspan='6'><button type='button' class='replyAdding'>댓글 더보기</button></td></tr>");	
	}	


	var likeClick = 1;
	
	// ♥REPLY LIKE ADD
	$(document).ready(function(){
		$(document).on('click','.replyEmptyHeart',function(){
			
			var replySeq = $(this).attr("replySeqforLike");
			var loginId = "<%=LoginId %>";
			var replySeqId = "#" + replySeq + "emptyHeart";
			var replySeqlikeCount = "." + replySeq + "likeCount";
			
			var replySeqId2 = replySeq + "fullHeart";		// append해줄 id
			
			$.ajax({
				url: "freeControl?command=replylikeAdd",
				type: "GET",
				data: { replySeq:replySeq, loginId:loginId },
				datatype: "json",
				success: function( obj ){
					var data = JSON.parse(obj);
					if(data.result == true) {
						$(replySeqlikeCount).text(data.likeCount);
						$(replySeqId).remove();
						$("#like-div").attr('style', "display:block;")
						setTimeout(function() {
							$("#like-div").attr('style', "display:none;")
						},700);
						$('#' + replySeq).prepend("<img src='images/free_bbs/like.png' id=" + replySeqId2 + " class='replyFullHeart' replySeqforLike=" + replySeq + " alt='x' width='25' height='25' style='cursor: pointer'>");
						<%-- id="<%=mac.getSeq() %>fullHeart" --%>
					} 
				},
				error: function(){
					alert("error");
				},
				complete: function () {
					
				}
			});
			
		});	
	});	
	
	// ♥REPLY LIKE CANCEL
	$(document).ready(function(){
		$(document).on('click','.replyFullHeart',function(){
			var loginId = "<%=LoginId %>";
			var replySeq = $(this).attr("replySeqforLike");
			var replySeqId = "#" + replySeq + "emptyHeart";		
			
			var replySeqlikeCount = "." + replySeq + "likeCount";
			
			var replySeqId2 ="#" + replySeq + "fullHeart";	 // 삭제 해줄 ♥ ID		
			var replySeqId = replySeq + "emptyHeart";	 // 다시 append해줄 ♡ID
			
			//var data = {"replySeq":replySeq, "loginId": loginId};
			var data= new Object();
			data.replySeq = replySeq;
			data.loginId = loginId;
			
			JSON.stringify(data);
			
			console.log(data);
			
				$.ajax({
					url: "freeControl?command=replylikeCancel",
					type: "GET",
					data: data,
					datatype: "json",
					success: function( obj ){
						var data = JSON.parse(obj);
						if(data.result == true) {
							$(replySeqlikeCount).text(data.likeCount);
							$(replySeqId2).remove();						
							$('#' + replySeq).prepend("<img src='images/free_bbs/like2.png' id='" + replySeqId + "' class='replyEmptyHeart' replySeqforLike=" + replySeq + " alt='x' width='25' height='25' style='cursor: pointer'>"); 
						} 
					},
					error: function(){
						alert("error");
					},
					complete: function () {
						
					}
			});
		 		
		});	
	});
	
	
	// ♥LIKE ADD (PAGE)
	function likefunc() {
		
		var seq = <%=dSeq %>;
		var loginId = "<%=LoginId %>";
		
		$.ajax({
			url: "freeControl?command=likeAdd",
			type: "GET",
			data: { seq:seq, loginId:loginId },
			datatype: "json",
			success: function( obj ){
				var data = JSON.parse(obj);
				if(data.result == true) {
					$(".likeCount").text(data.likeCount);
					//$(".emptyHeart").attr("src", "images/free_bbs/like.png");
					$(".emptyHeart").remove();
					$("#like-div").attr('style', "display:block;")
					setTimeout(function() {
						$("#like-div").attr('style', "display:none;")
					},700);
					
					$('.heartPosition').prepend("<img src='images/free_bbs/like.png' class='fullHeart' onclick='unlikefunc()' style='cursor: pointer' alt='x' width='25' height='25'>");
					
				}
			},
			error: function(){
				alert("error");
			},
			complete: function () {
				
			}
		});
	}
	
	// ♥LIKE CANCEL (PAGE)
	function unlikefunc() {
		
		var seq = '<%=dSeq %>';
		var loginId = '<%=LoginId %>';
		
		var data = {"seq":seq, "loginId": loginId};
		
		$.ajax({
			url: "freeControl?command=likeCancel",
			type: "GET",
			data: data, /* { seq:seq, loginId:loginId }, */
			datatype: "json",
			success: function( obj ){
				var data = JSON.parse(obj);
				if(data.result == true) {
					$(".likeCount").text(data.likeCount);
					$(".fullHeart").remove();
					$('.heartPosition').prepend("<img src='images/free_bbs/like2.png' class='emptyHeart' onclick='likefunc()' style='cursor: pointer' alt='x' width='25' height='25'>");
					
				}
			},
			error: function(){
				alert("error");
			},
			complete: function () {
				
			}
		});
	}	
	// ㅠㅠ
	function reReply(replysequence) {
		
		$(".addingTR").remove();
		
		var removeLoc = "." + replysequence + "noAnswer";	// hide
		$(removeLoc).hide();
		var btnAppendLoc = "." + replysequence + "here";
		
		var createLoc = "." + replysequence + "yesAnswer";
		
		var reReplyBlock = "#" + replysequence + "block:last-of-type";
		var reReplyBlock2 = "." + replysequence + "block2";
		
		$(reReplyBlock).show();
		$(reReplyBlock2).show();
		$(createLoc).show();	// 닫기버튼 show
		 
		if(!'<%=LoginId%>'==""){
			 $(reReplyBlock).after("<td align='right' class='"+ replysequence +"block'><img src='images/free_bbs/re.png'></td><td colspan='5' class='"+ replysequence +"block'><b>"+'<%=LoginNick %>'+"</b><br>" + 
											"<input type='text' class='reReplyContent' style='width:620px; height:170px; display : inline-flex;'>&nbsp;" + 
											"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='reReplyBtn'>등록</button></td>");
		}else if('<%=LoginId%>'==""){
			 $(reReplyBlock).after("<td align='right' class='"+ replysequence +"block'><img src='images/free_bbs/re.png'></td><td colspan='5' class='"
					 	+ replysequence +"block'><br>"
					 	+ "<input type='text' value='로그인 후 이용해주세요' style='width:600px; height:160px;' readonly='readonly'><br>" 
					 	+ "</td>");
		}
		
		$('#parentReplySeq').val(replysequence);
			 
		 $.ajax({
				url: "freeControl?command=reReply",
				type: "GET",
				data: { seq:replysequence },
				datatype: "json",
				success: function( obj ){
					console.log(obj, typeof obj); 
						
						var data = JSON.parse(obj);
						replyList(data,replysequence);//호이스팅	
				},
				error: function(){
					alert("error");
				}
		 });
	}  
	
	function replyList(data,replysequence){
		var reReplyBlock2 = "." + replysequence + "block2";
		//alert(data.length);
		for(var i=0; i< data.length; i++){
			
			var str = "";
			
			str += "<tr id='"+ replysequence +"pale'><td class='"+ replysequence +"block'><img src='images/free_bbs/re.png' align='right'>"
			str += "</td><td colspan='2' class='"+ replysequence +"block'><b>"+ data[i].nickname + "</b>&nbsp;&nbsp;<font size='3px'>" + data[i].wdate +"</font><br><br>" + data[i].content + "<br><br>" + "</td>";
			
			str += "<td class='"+ replysequence +"block'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='reReplyDelBtn' " 
			str += " value='"+data[i].seq+"' value2='" + replysequence + "'>삭제</button></td></tr><br><br>";
			
			$(reReplyBlock2).append(str);
			
			/* $(reReplyBlock2).append("<tr><td class='"+ replysequence +"block'></td><td colspan='5' class='"+ replysequence +"block'><b>"+ data[i].nickname + "</b><br><br>" + data[i].content + "<br>" + data[i].wdate + "</td></tr>"); */
			  			 
		} 
	}
	
	function reReplydel(replysequence){
		
		var createLoc = "." + replysequence + "yesAnswer";
		$(createLoc).hide();
		
		var removeLoc = "." + replysequence + "noAnswer";
		$(removeLoc).show();
		
		var replySpace = '.' + replysequence + "replySpace";
		$(replySpace).remove();
		
		var reReplyBlock = "." + replysequence + "block";
		$(reReplyBlock).remove();
	
	}
	
	//작업중	
	//대댓글 삭제
	$(document).ready(function(){
		$(document).on('click','.reReplyDelBtn',function(){
			//db삭제
			var reReplyDelSeq = $(this).val();
			//화면삭제
			var replysequence = $(this).attr("value2");
			var reReplypale = "#" + replysequence + "pale";
			
			
			  var resetCount = "." + replysequence + "reReplyCount"; // 좋아요
			
			$.ajax({
				url: "freeControl?command=reReplyDel",
				type: "POST",
				data: { reReplyDelSeq:reReplyDelSeq, replysequence:replysequence },
				datatype: "json",
				success: function( obj ){
				//	alert("연결성공");
					var data = JSON.parse(obj);
					var bool = data.result;
					if(bool){ $(reReplypale).remove();	};
					var count = data.count;	// 삭제 후 댓글좋아요 카운트
					//alert(count);
						if(count!=0){
							$(resetCount).html(count + "개 모두보기");
						}else {
							$(resetCount).html("");
						}
					//alert(count);
				},
				error: function(){
					alert("error");
				},
				complete: function () {
					
				}
			});
			
		});
	});	

	//reReply2 추가 댓글 remove, close
	$(document).on("click",".yesAnswer2",function(){
			var replysequence = $(this).attr("replysequence");
			
			var removeLoc2 = "#" + replysequence + "noAnswer2";
			$(removeLoc2).show();
			var createLoc2 = "#" + replysequence + "yesAnswer2";
			$(createLoc2).hide();
	
			var replyBlock3 = '.' + replysequence + "block3";
			$(replyBlock3).remove();
			
		});	
	
	//reReply2 open, append
	//ㅠㅠ
	$(document).on("click",".noAnswer2",function(){
			
			var replysequence = $(this).attr("replysequence");
			
			var removeLoc2 = "#" + replysequence + "noAnswer2";
			$(removeLoc2).hide();
			
			var createLoc2 = "#" + replysequence + "yesAnswer2";
			$(createLoc2).show();
			
			 $.ajax({
					url: "freeControl?command=reReply",
					type: "GET",
					data: { seq:replysequence },
					datatype: "json",
					success: function( obj ){
						
					//	alert('success');
						console.log(obj, typeof obj); 
							
							var data = JSON.parse(obj);
							replyList2(data,replysequence);//호이스팅	
					},
					error: function(){
						alert("error");
					}
			 });	
			
			var replySpace2 = '.' + replysequence + "replySpace2";
			
<%-- 			if(!'<%=LoginId%>'==""){
				 $(reReplyBlock).after("<td align='right' class='"+ replysequence +"block'><img src='images/free_bbs/re.png'></td><td colspan='5' class='"+ replysequence +"block'><b>"+'<%=LoginNick %>'+"</b><br>" + 
												"<input type='text' class='reReplyContent' style='width:600px; height:160px;'><br>" +
												"<button type='button' class='reReplyBtn'>등록</button></td>");
			}else if('<%=LoginId%>'==""){
				 $(reReplyBlock).after("<td align='right' class='"+ replysequence +"block'><img src='images/free_bbs/re.png'></td><td colspan='5' class='"
						 	+ replysequence +"block'><br>"
						 	+ "<input type='text' value='로그인 후 이용해주세요' style='width:600px; height:160px;' readonly='readonly'><br>" 
						 	+ "</td>");
			} --%>
			
			
			<%-- $(reReplyBlock).after("<td align='right' class='"+ replysequence +"block'><img src='images/free_bbs/re.png'></td><td colspan='5' class='"+ replysequence +"block'><b>"+'<%=LoginNick %>'+"</b><br>" + 
					"<input type='text' class='reReplyContent' style='width:620px; height:170px; display : inline-flex;'>&nbsp;" + 
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='reReplyBtn'>등록</button></td>"); --%>
			
			if(!'<%=LoginId%>'==""){
				$(replySpace2).after("<tr class='"+ replysequence +"block3'><td align='right'><img src='images/free_bbs/re.png' class='"+ replysequence +"block3'></td><td colspan='5' class='"+ replysequence +"block3'><b>"+'<%=LoginNick %>'+"</b><br>" + 
						"<input type='text' class='reReplyContent' style='width:620px; height:170px; display : inline-flex;'>" +
						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='reReplyBtn'>등록</button></td><tr>");
			}else{
				$(replySpace2).after("<td align='right' class='"+ replysequence +"block'><img src='images/free_bbs/re.png'></td><td colspan='5' class='"
					 	+ replysequence +"block'><br>"
					 	+ "<input type='text' value='로그인 후 이용해주세요' style='width:600px; height:160px;' readonly='readonly'><br>" 
					 	+ "</td>");	
			}
	
	});
	
	function replyList2(data,replysequence){
		var replySpace2 = '.' + replysequence + "replySpace2";
			//alert("replyList2");
			//alert(data.length);
			
		for(var i=0; i< data.length; i++){
			
			str = "";
			str += "<tr id='"+ replysequence +"pale'>";
			str += "<td class='"+ replysequence +"block3'><img src='images/free_bbs/re.png' align='right'></td>";
			str += "<td colspan='2' class='"+ replysequence +"block3'><b>"+ data[i].nickname + "</b>&nbsp;&nbsp;<font size='3px'>" + data[i].wdate + "</font><br><br>" + data[i].content + "<br><br>" + "</td>";
			
			str += "<td class='"+ replysequence +"block'>&nbsp;&nbsp;<button type='button' class='reReplyDelBtn' ";
			str += " value='"+data[i].seq+"' value2='" + replysequence + "'>삭제</button></td><br><br>"; 
			str += "</tr>";
			
			
			/*
			str += "<tr id='"+ replysequence +"pale'><td class='"+ replysequence +"block'>"
			str += "</td><td colspan='3' class='"+ replysequence +"block'><b>"+ data[i].nickname + "</b><br><br>" + data[i].content + "<br>" + data[i].wdate + "</td>";
			
			str += "<td class='"+ replysequence +"block'><button type='button' class='reReplyDelBtn' " 
			str += " value='"+data[i].seq+"' value2='" + replysequence + "'>삭제</button></td></tr>";
			*/
			
			//$(replySpace2).after("<tr class='"+ replysequence +"block3'><td class='"+ replysequence +"block3'><img src='images/free_bbs/re.png' align='right'></td><td colspan='5' class='"+ replysequence +"block3'><b>"+ data[i].nickname + "</b><br><br>" + data[i].content + "<br>" + data[i].wdate + "</td></tr>");
			
			$(replySpace2).after(str);	
			
			
		} 
	}
	
	/*  ★reReply add */
	$(document).ready(function(){		
		$(document).on("click",".reReplyBtn",function(){
			var reReplyContent = $(".reReplyContent").val();		
			
			if(reReplyContent==""){
				alert("내용을 입력해주세요");
			}else{
				$('#reReplyContent').val(reReplyContent);
				$('#reReplyFrm').submit();
			}
			
		});	
	});
	
	// ★update
	$(document).ready(function(){
		$('#updateBtn').on('click',function(){
			$('#delOrUp').val("forUpdate");
			$('#detailFrm').submit();
		});	
	});
	
	// ★delete
	$(document).ready(function(){
		$('#deleteBtn').on('click',function(){
			$('#delOrUp').val("deleteDetail");
			$('#detailFrm').submit();
		});	
	});
	
	// ♥REPLY add
	$(document).ready(function(){
		$('#replyBtn').on('click',function(){			
			
			var content = $('#replyC').val();
			
			if(content == "") {
				alert("내용을 입력해주세요");
			}else{
				$('#replyFrm').submit();	
			}
		});	
	});
	
	// ♥REPLY DELETE
	$(document).ready(function(){
			
		$(document).on('click', '.rDeleteBtn', function(){
			
			$('#command').val("forReplyDelete"); 
			
			var replySeq = $(this).val();
			$('#hdnReplySeq').val(replySeq);
		
			$('#operatingReplyFrm').submit();
		});	
	});
	
	// ♥REPLY UPDATE READY
	$(document).ready(function(){
		
		$(document).on('click', '.rUpdateBtn', function(){
		/* $(".rUpdateBtn").on("click", function () { */
			//alert($(this).val());
			
			var replyContent = $(this).val();
			var replySeq = $(this).attr("value2");
			
			//alert(replyContent);
			$('.' + replySeq).html('<input type="text" class="updatingContent" value="' + replyContent +'" style="width:410px;">');			
			//alert("value2 seq : " + $(this).attr("value2"));		-->   ♥ seq값 구분 성공!!!!!!!!!!!!!!!!!!!
			
			//$(this).replaceWith('<button type="button" value="' + replySeq + '"class="rUpdateBtn2">수정2</button>');
			$(this).remove();
			$('.' + replyContent).html('<button type="button" value="' + replySeq + '"class="rUpdateBtn2">수정</button>' );
			
		});
	});
	
	// ♥REPLY UPDATE SEND TO CONTROLLER
	$(document).ready(function(){
		
		$(document).on("click",".rUpdateBtn2",function(){
						
			var replySeq = $(this).val();
			//alert("REPLYUPDATE SEND replySeq : " + replySeq);
			
			var updateReplyContent = $(".updatingContent").val();
			//alert("REPLYUPDATE replyContent : " + updateReplyContent);
			
			$('#command').val("forReplyUpdate");			// command 수정용으로 세팅
			
		// seq 받아와서 보낼 form안 hidden에 setting					
			$('#hdnReplySeq').val(replySeq);
		// content 받아와서 보낼 form안 hidden에 setting
			$('#hdnUdtReplyContent').val(updateReplyContent);
			$('#operatingReplyFrm').submit();
		
		});	
	});

</script>
<!-- Footer -->
		<div id="footer">
		
		<!-- Icons -->
						<ul class="icons">
						<li><a href="https://twitter.com/hashtag/%ED%82%A4%EC%9B%80%ED%9E%88%EC%96%B4%EB%A1%9C%EC%A6%88" target="_blank" class="icon brands fa-twitter"><span class="label">Twitter</span></a></li>
						<li><a href="https://www.facebook.com/kiwoomheroesbaseballclub" target="_blank" class="icon brands fa-facebook-f"><span class="label">Facebook</span></a></li>
						<li><a href="https://www.instagram.com/heroesbaseballclub" target="_blank" class="icon brands fa-instagram"><span class="label">Instagram</span></a></li>
						<li><a href="https://www.youtube.com/user/heroesbaseballclub" target="_blank" class="icon brands fa-youtube"><span class="label">Youtube</span></a></li>
					</ul>
	
		<!-- Copyright -->
			<div class="copyright">
				<ul class="menu">
					<li>&copy; Untitled. All rights reserved</li><li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
				</ul>
			</div>

	</div>

	<!-- Scripts -->
		<script src="./main/js/jquery.min.js"></script>
		<script src="./main/js/jquery.dropotron.min.js"></script>
		<script src="./main/js/browser.min.js"></script>
		<script src="./main/js/breakpoints.min.js"></script>
		<script src="./main/js/util.js"></script>
		<script src="./main/js/main.js"></script>



</body>		
</html>