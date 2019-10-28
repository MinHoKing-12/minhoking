<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:requestEncoding value="utf-8"/>      
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" type="text/css" />  
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<!-- cookie 링크 -->
<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css/style.css"/>

<style type="text/css">
#login_wrap {
	margin:10px;
}
#login_wrap th {
	font-weight:bold;
}
#main_wrap { 
	width:800px; 
	margin-left:auto; 
	margin-right:auto; padding:0px; 
}			
#content_wrap { 
	width: 100%; 
	height: 500px; 
	background-image:url("image/park.jpg"); 
	background-repeat:no-repeat; 
	background-position:top center; 
	background-size: 80% 80%;
}
			
.login_title_warp {
	width:500px; 
	color:#FFFFFF; 
	text-align:center; 
	background-color:#3e5fba; 
	border:solid 1px #EFEFEF; 
	font-weight:bold; 
	height:60px;
}

/* table셋팅 */
.content_table { width:98%; border-bottom:1px solid #EFEFEF; border-right:1px solid #EFEFEF; border-collapse:collapse; margin-left:auto; margin-right:auto;  clear:both; }
.content_table td, .content_table th { text-align:center; border-top:1px solid #EFEFEF; border-left:1px solid #EFEFEF; padding:0.3em; }
.content_table th { background-color:#4D6BB3; color:#FFFFFF; line-height: 1.7em; font-weight:normal;}
.content_table td { padding-left:5px; text-align:left; line-height: 1.7em; }
.content_table td.contents { width:100%; height:400px; overflow:auto; }
.content_table th, .content_table td { vertical-align:middle; }

.content_table select { height:19px; border:#CCCCCC solid 1px; vertical-align:middle; line-height: 1.8em; padding-left:0px; }
.content_table select option { margin-right:10px; }

</style>

</head>
<body>

<div id="main_wrap">
	<div id="middle_wrap">
		<div id="content_wrap">
			
			<div style="width: 502px; height: 166px; margin-left: auto; margin-right: auto;
						position: relative; top: 100px;">
								
				<div class="login_title_warp">
					<div style="margin-top: 12px">
						<h2>My Home Page</h2>
					</div>
				</div>
				
				<div id="login_wrap">
					<form action="loginAf.do" name="frmForm" id="_frmForm" method="post">
					
						<table class="content_table" style="width: 75%">
						<colgroup>
							<col style="width:30%">
							<col style="width:70%">
						</colgroup>
						
						<tbody>
						<tr>
							<th style="background: #eeeeee; color: #3e5fba">아이디</th>
							<td>&nbsp;<input type="text" id="_userid" name="id" data-msg="ID를"
									size="20px" title="아이디" style="border: 1px solid #dddddd;">
									<input type="checkbox" id="che_save_id">ID 저장
							</td>
						</tr>
						<tr>
							<th style="background: #eeeeee; color: #3e5fba">패스워드</th>
							<td>&nbsp;<input type="password" id="_pwd" name="pwd" data-msg="패스워드를"
									size="20px" title="패스워드" style="border: 1px solid #dddddd;">
							</td>
						</tr>
						<tr>
							<td colspan="2" style="height: 50px; text-align: center;">
							<span>
								<a href="#none" id="_btnLogin" title="로그인">
									<img alt="" src="./image/login_btn.jpg">
								</a>
								<a href="#none" id="_btnaddMember" title="회원가입">
									<img alt="" src="./image/regi.jpg">
								</a>
							</span>
						</tr>	
						</tbody>
						</table>
					</form>
				</div>
				
				
				
			</div>		
		</div>	
	</div>
</div>

<script type="text/javascript">
$(document).ready(function () {
	
	$("#_btnaddMember").on('click', function () {
		location.href="account.do";
	});
	
	$("#_btnLogin").on('click', function () {
		$("#_frmForm").submit();
	});
	
	$("#che_save_id").click(function () {
		if( $("#che_save_id").is(":checked") ){   //체크박스가 체크 되어있을 때
		//	alert("아이디 저장");
			if ( $("#_userid").val().trim() == "" ){	//id값이 빈칸이었을 경우
				alert("id를 입력해 주십시오");
				$("#che_save_id").prop("checked", false);	//체크박스가 자동으로 풀리게 만들어줌.
			}
			else{	// id 기입 후 
				// 쿠키 저장	쿠키 이름			들어가는 값		기한    1주일	모든 것
				$.cookie("user_id", $("#_userid").val().trim(), {expires:7, path:'/'});
			}
		}
		else{
		//	alert("아이디 저장 삭제");
			//쿠키 삭제				expires를 없애서 삭제하는 것임
			$.removeCookie("user_id", {path:'/'});
		}
		});
	
	$("#_btnLogin").click(function () {
		// 공백이 있을 경우 find_space에 값이 저장 -1일 경우 공백x
		var id = $("#_userid").val();
		var pw = $("#_pwd").val();
		var find_space = $("#_userid").val().trim().search(" ");
		if( $("#_userid").val().trim() == "" ){
			alert("id를 입력해 주십시오");
			//커서가 id커서로 바뀌도록
			$("#_userid").focus();
		}else if (find_space >= 1){
			 alert("id값에 공백이 있으면 안됩니다.");
			$("#_userid").focus();
		}
		else if ( $("#_userid").val().trim().length < 7 ){
			alert("id는 7자 이상 입력해주세요.");
			$("#_userid").focus();
		}
		else if ( $("#_pwd").val().trim() == "" ){
			alert("password를 입력해 주십시오");
			$("#_pwd").focus();
		}
		else if ( $("#_pwd").val().trim().length < 8 ){
			alert("password의 길이가 짧습니다.");
			$("#_pwd").focus();
		}
		else{
			$("#_fromForm").attr("action","loginAf.do").submit();
		}
	});
	
	
	
	});	


</script>
</body>
</html>





