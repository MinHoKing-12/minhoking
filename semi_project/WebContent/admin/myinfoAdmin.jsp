<%@page import="member.MemberDto"%>
<%@page import="util.utilMethod"%>
<%@page import="message.MessageDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
MemberDto user = (MemberDto)session.getAttribute("login");
String pwd = user.getPwd();
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>KIWOOM SUPPORTERS - 관리자 페이지</title>
	<link rel="stylesheet" href="admin/css/style.css" type="text/css" media="all" />
	<link rel="shortcut icon"  href="main/images/favicon.ico">
	<style>
	#main {
		width: 1300px;
	}
	table th {
		text-align: left;
	}
	
	td, th {
		padding: 5px;
	}
	
	/* checkmsg  */
	input[type=password] {
		position: relative;
	}
	
	.checkmsg {
		postion: absolute;
		margin-top: 5px;
		display: none;
		color: #c80000;
	}
	
	</style>
</head>
<body>
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
			    <li><a href="./admincontrol?command=votelist">투표 관리</a></li>
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
			<a href="#">마이페이지</a>
			<span>&gt;</span>
			비밀번호 변경
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
						<h2 class="left">비밀번호 변경</h2>
						<!-- <div class="right">
							<form id="search-frm">
								<input type="hidden" name="command" value="memlist">
								<select class="field" name="search-select">
									<option value="id">아이디</option>
									<option value="nickname">닉네임</option>
									<option value="name">이름</option>
									<option value="address">주소</option>
									<option value="email">이메일</option>
								</select>
								<input type="text" name="search-text" class="field small-field" />
								<input type="button" id="search-btn" class="button" value="검색" />
							</form>
						</div> -->
					</div>
					<!-- End Box Head -->	

					<div class="form">
						<form id="pwdupdate-form" method="post">
						<input type="hidden" name="command" value="pwdupdate">
							<table>
							<tr>
								<th>현재 비밀번호</th>
								<td>
									<input type="password" id="oldpwd" class="field">
									<div class="checkmsg" id="oldpwd-check">
										<span id="oldpwd-checkRe">현재 비밀번호를 입력해주세요</span>
									</div>
								</td>
							</tr>
							<tr>
								<th>변경 비밀번호</th>
								<td>
									<input type="password" id="newpwd" class="field">
									<div class="checkmsg" id="newpwd-check">
										<span id="newpwd-checkRe">변경 비밀번호를 입력해주세요</span>
									</div>
								</td>
							</tr>
							<tr>
								<th>변경 비밀번호 확인</th>
								<td>
									<input type="password" id="newpwdchk" class="field" name="pwd">
									<div class="checkmsg" id="newpwdchk-check">
										<span id="newpwdchk-checkRe">비밀번호 확인을 입력해주세요</span>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="right">
									<button type="button" class="button" id="update-btn">비밀번호 변경</button>
								</td>
							</tr>
							
							</table>
						</form>
					</div>
					
				</div>
				<!-- End Box -->
				
			</div>
			<!-- End Content -->
			
			<!-- Sidebar -->
			<div id="sidebar">
				
				<!-- Box -->
				<div class="box">
					
					<!-- Box Head -->
					<div class="box-head">
						<h2>Management</h2>
					</div>
					<!-- End Box Head-->
					
					<div class="box-content">
						<p><a href="admincontrol?command=msgbox">받은 쪽지함</a></p>
						<p><a href="admincontrol?command=msgbox&type=send">보낸 쪽지함</a></p>
						<p><a href="admincontrol?command=admininfo">비밀번호 변경</a></p>					
					</div>
				</div>
				<!-- End Box -->
			</div>
			<!-- End Sidebar -->
			
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

<!-- Script -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
$(function () {
	var oldpwd_bool = false;
	var newpwd_bool = false;
	var newpwdchk_bool = false;
	
	// 현재 비밀번호 체크
	$("#oldpwd").on('blur', function () {
		var input_oldpwd = $(this).val();
		var oldpwd = <%=pwd %>;
		
		if(input_oldpwd != oldpwd) {
			$("#oldpwd-checkRe").text("잘못된 비밀번호 입니다.");
			$("#oldpwd-check").css("display", "block");
			oldpwd_bool = false;
		}else {
			$("#oldpwd-check").css("display", "none");
			oldpwd_bool = true;
		}
	});
	
	// 변경할 비밀번호 체크
	$("#newpwd").on('blur', function () {
		var newpwd = $(this).val();
		
		$("#newpwd-check").css("display", "block");
		newpwd_bool = pwdcheckFunc(newpwd);
	});
	
	// 비밀번호 확인 체크
	$("#newpwdchk").on('blur', function () {
		var newpwd = $("#newpwd").val();
		var newpwdchk = $(this).val();
		
		if(newpwd != newpwdchk) {
			$("#newpwdchk-checkRe").text("변경 비밀번호가 일치하지 않습니다.")
			$("#newpwdchk-check").css("display", "block");
			newpwdchk_bool = false;
		}else {
			$("#newpwdchk-check").css("display", "none");
			newpwdchk_bool = true;
		}
	});
	
	$("#update-btn").on('click', function () {
		alert(oldpwd_bool);
		alert(newpwd_bool);
		alert(newpwdchk_bool);
		if(oldpwd_bool && newpwd_bool && newpwdchk_bool) {
			$("#pwdupdate-form").attr("action", "admincontrol").submit();
		}else {
			if(!oldpwd_bool) {
				$("#oldpwd-check").css("display", "block");
			}
			if(!newpwd_bool) {
				$("#newpwd-check").css("display", "block");
			}
			if(!newpwdchk_bool) {
				$("#newpwdchk-check").css("display", "block");
			}
		}
	});
});

function pwdcheckFunc( pwd ) {
	var reg1 = /^[a-zA-Z0-9]{8,20}$/;
	var checkNum = pwd.search(/[0-9]/g);
	var checkEng = pwd.search(/[a-zA-Z]/g);
	var newpwd_bool;
	
	if(pwd == ""){
		$("#newpwd-checkRe").text("비밀번호를 입력해주세요");
		newpwd_bool = false;
	}else if(!reg1.test(pwd)) {
		$("#newpwd-checkRe").text("숫자와 영문자 조합으로 8~20자리를 사용해야 합니다");
		newpwd_bool = false;
	}else if(checkNum < 0 || checkEng < 0) {
		$("#newpwd-checkRe").text("숫자와 영문자를 혼용해야 합니다");
		newpwd_bool = false;
	}else {
		$("#newpwd-check").css("display", "none");
		newpwd_bool = true;
	}
	return newpwd_bool;
}
</script>
	
</body>
</html>