<%@page import="votes.VResultDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
 <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
 <script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>
 <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
 <title>KIWOOM SUPPORTERS - 투표게시판 관리</title>
 <link rel="stylesheet" href="admin/css/style.css" type="text/css" media="all" />
 <link rel="shortcut icon"  href="main/images/favicon.ico">

<style type="text/css">
        /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }
    
        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto; /* 15% from the top and centered */
            padding: 20px;
            border: 1px solid #888;
            width: 30%; /* Could be more or less, depending on screen size */                          
        }
        /* The Close Button */
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        .table tr:hover td {
 		   background: #de82b4;
	    }
</style>

</head>
<body>
<%
List<VResultDto> list = (List<VResultDto>)request.getAttribute("list"); 
//페이징
String sbbslen = (String)request.getAttribute("bbslen");
int bbslen = Integer.parseInt(sbbslen);
	int bbsPage = bbslen / 10;
	if(bbslen % 10 > 0){
		bbsPage = bbsPage + 1;
	}
//당첨자ID	
 String pickId = (String)request.getAttribute("pickId");

//투표막기
String count = (String)request.getAttribute("count");
if(count == null){
	count = "0";
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
			투표게시판 관리
		</div>
		<!-- End Small Nav -->
		
		<!-- Main -->
		<div id="main">
			<div class="cl">&nbsp;</div>
			
			<!-- Content -->
			<div id="content">
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">투표회원 목록</h2>
					
						<div class="right">
							<label>선수 번호로 검색</label>
							<input id="numtext" type="text" class="field small-field" numberOnly  />
							<script type="text/javascript">
							$("input:text[numberOnly]").on("keyup",function() {
								$(this).val($(this).val().replace(/[^0-9]/g,""));
							});
							</script>
							<input id="btn" type="submit" class="button" value="search" />
						    
						   <script type="text/javascript">
							$(function() {
								$("#btn").click(function() {
								var num = $("#numtext").val();
								if(num == null || num == ""){
									alert("검색할 선수번호를 입력해주세요");
								}
								else{
							 location.href = "admincontrol?command=votelist&num="+ $("#numtext").val();	
								}
								});
							});
						</script>
						

						
						    <!-- The Modal -->
                      <div id="myModal" class="modal">
 
					      <!-- Modal content -->
					      <div class="modal-content">
					        <span class="close">&times;</span>                                                               

					        <p style="color: black; font-size: 17px">  
					        <%=pickId %>님  event당첨!
					        <br><br><br> 
					                  당첨자에게  message를 보냈습니다.
					        <br><br>
					        </p>
					        <br><br>
					      </div>
					    </div>
					    					   
						<script type="text/javascript">
					  		var pickId = '<%=pickId%>';
					
						    // Get the modal
					        var modal = document.getElementById('myModal');
					        // Get the <span> element that closes the modal
					        var span = document.getElementsByClassName("close")[0];
					        // When the user clicks on the button, open the modal 
					      	if(pickId != "null"){
					           modal.style.display = "block";   
					      	}
					        // When the user clicks on <span> (x), close the modal
					        span.onclick = function() {
					            modal.style.display = "none";
					        }					 
					        // When the user clicks anywhere outside of the modal, close it
					        window.onclick = function(event) {
					            if (event.target == modal) {
					                modal.style.display = "none";
					            }
					        }
						</script>
				
						</div>
					</div>
					<!-- End Box Head -->	
 
					<!-- Table -->
					<div class="table">
				     <table>
				     <col width="200"><col width="300"><col width="200">
				     <tr>
				       
				       <th>회원 번호</th>
				       <th>ID</th>
				       <th>투표한 선수 번호</th>
				     </tr>
				     <%for(int i = 0 ; i < list.size(); i++ ) 
				     {
				     %>
				     <tr class="tr" style="cursor: pointer">
				     <td class="manager"><%=list.get(i).getSeq()%></td>
				     <td class="manager"><%=list.get(i).getId()%></td>
				     <td class="manager"><%=list.get(i).getPlaynum() %></td>
				     </tr>
				     <%
				     }
				     %>
				     </table>
				      
						
						<!-- Pagging -->
						<div class="pagging">
						<div class="right">
						<%for(int i=0; i < bbsPage; i++){ %>
							
							<a href="admincontrol?command=votelist&Page=<%=i%>" title="<%=i+1 %>페이지"><%=i+1%></a>&nbsp;
						<%} %>
						</div>
						</div>
						<!-- End Pagging -->
						
					</div>
					<!-- Table -->
					
				</div>
				<!-- End Box -->
				
				<!-- Box -->
			<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">투표현황</h2>
						
					</div>
					<!-- End Box Head -->	
 
					<!-- Table -->
					<div class="table">
				      	<iframe style="width: 745px; height: 420px;"  class="myFrame"
							src="VoteControl?command=result"></iframe>
					</div>
					<!-- Table -->
					
				</div>
				<!-- End Box -->

			</div>
			<!-- End Content -->
			<!-- Sidebar -->
			<div id="sidebar">
			
			<!-- Box -->
				<div class="box">
					
					<!-- Box Head -->
					<div class="box-head" align="center">
						<h2>★  추첨 Event ★</h2>
					</div>
					<!-- End Box Head-->
					
					<div class="box-content" align="center">
					     mvp 선수번호 :
					    <input id="picktext" type="text" size="1px" class="field small-field"  numberOnly  />
					    <br><br>
						<input  id="pick" type="button" class="button" value="Go추첨 " />		
					</div>
					<script type="text/javascript">
					$("input:text[numberOnly]").on("keyup",function() {
						$(this).val($(this).val().replace(/[^0-9]/g,""));
					});
					
					
					$("#pick").click(function() {
					    
						
						//버튼 눌렀을때 컨트롤러로 가기
						var picktext = $("#picktext").val()
						if(picktext == "" || picktext == null){
							alert("mvp 선수번호를 입력해 주세요");
						}
						else if(<%=count%> == "3"){
							alert("추첨을 이미 했습니다.");
						}
						else{
						location.href= "admincontrol?command=lotto&mvPlayer="+$("#picktext").val();
						}
  						}); 

					</script>		
				</div>
				<!-- End Box -->
				</div>
			<!-- End Sidebar -->

			
				<!-- Sidebar2 -->
			<div id="sidebar">
			
			<!-- Box2 -->
				<div class="box">
					
					<!-- Box Head2 -->
					<div class="box-head" align="center">
					<h2>★  추첨 Event 당첨자 ★</h2>
					</div>
					<!-- End Box Head2-->
					
					<div class="box-content" align="center">
					<script type="text/javascript">
					
					var pick = '<%=pickId%>';
					
					if(pick == "null"){
						document.write("<p style='font-size: 18px'>  </p>");
					}
					else if(pick != "null"){
						document.write("<p style='font-size: 18px'>당첨자 ID:"+ '<%=pickId%>' +"</p>");
					}
					</script> 					
					</div>
				</div>
				<!-- End Box2 -->
				</div>
			<!-- End Sidebar2 -->
			<div class="cl">&nbsp;</div>			
		</div>
		<!-- Main -->
	</div>
</div>
<!-- End Container -->

<!-- Footer -->
<div id="footer">
	<div class="shell">
		<span class="left">&copy; 2010 - CompanyName</span>
		<span class="right">
			Design by <a href="http://chocotemplates.com" target="_blank" title="The Sweetest CSS Templates WorldWide">Chocotemplates.com</a>
		</span>
	</div>
</div>
<!-- End Footer -->
<script type="text/javascript">
$(function() {
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
	
	$(".manager").click(function () {	
		var manager = $(this).parent();
		//ID값 얻어오기
		var id = manager.children().eq(1).html();
		//alert(seq);
		location.href="admincontrol?command=votedetail&id="+id;
	});
	
});


</script>
	
</body>
</html>