<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login V8</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
<!--===============================================================================================-->
	<style type="text/css">
		.modal {
		display: none; /* Hidden by default */
		position: fixed; /* Stay in place */
		z-index: 1; /* Sit on top */
		left: 0;
		top: 0;
		width: 100%; /* Full width */
		height: 100%; /* Full height */
		overflow: hidden; /* Enable scroll if needed */
		background-color: rgb(0,0,0); /* Fallback color */
		background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
		}
		
		/* Modal Content/Box */
		.modal-content {
			text-align: center;
			background-color: #fefefe;
			margin: 10% auto; /* 10% from the top and centered */
			border: 1px solid #888;
			width: 50%; /* Could be more or less, depending on screen size */                          
		}
		/* The Close Button */
		.close {
			padding: 20px;
			color: #aaa;
			float: right;
			font-size: 28px;
			font-weight: bold;
		}
		.close:hover, .close:focus {
			color: black;
			text-decoration: none;
			cursor: pointer;
		}
		.find {
			font-weight: 800;
			cursor: pointer;
		}
		.find:hover {
			color: #820024;
		}
		.modal-title {
			padding: 20px;
		}
		.modal-title h3{
			padding: 20px;
		}
		.modal-text {
			padding:20px 20px 100px 20px;
		}
		.modal-text p {
			font-size: 17px;
			padding-bottom: 20px;
		}
		#idfind-btn, #pwdfind-btn {
			background-color: #820024;
			color: #fff;
			width: 50%;
			height: 50px;
			border-radius: 10px;
		}
	</style>
	
</head>
<body>
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<div class="login100-form validate-form p-l-55 p-r-55 p-t-178">
					<span class="login100-form-title">
						Login
					</span>
					<input type="hidden" name="command" value="loginAf">
					<div class="wrap-input100 validate-input m-b-16" data-validate="아이디를 입력해주세요">
						<input class="input100" type="text" id="_id" name="id" placeholder="ID">
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100 validate-input" data-validate = "비밀번호를 입력해주세요">
						<input class="input100" type="password" id="_pwd" name="pwd" placeholder="Password">
						<span class="focus-input100"></span>
					</div>
					
					<div style="height: 50px; background-color: #fff; display: none; margin: 0" id="wrong-idpwd-msg" class="wrap-input100 validate-input m-b-16 alert-validate" 
						data-validate="잘못된  아이디와 비밀번호 입니다."></div>
									
					<div class="text-right p-t-13 p-b-23">
						<div align="left" style="width: 150px; float: left">
							<input type="checkbox" id="remember_id"> <span class="txt2">아이디 저장</span>
						</div>
						<span onclick="idFindModal()" class="find">아이디</span> / <span onclick="pwdFindModal()" class="find">비밀번호 찾기</span>
					</div>

					<div class="container-login100-form-btn">
						<button class="login100-form-btn">
							LOGIN
						</button>
					</div>

					<div class="flex-col-c p-t-170 p-b-40">
						<span class="txt1 p-b-9">
							아직 회원이 아니십니까?
						</span>

						<a href="../memcontrol?command=account" class="txt3">
							회원가입
						</a>
					</div>
				</div>
			</div>
			
			<!-- id찾기 Modal -->
			<div id="id-find-modal" class="modal">
				<div class="modal-content">
					<div class="modal-title">
						<span class="close">&times;</span>
						<h3>아이디 찾기</h3>
					</div>
					<div class="modal-text">
						<p>회원가입 시 입력했던 이메일을 입력해주세요.</p>
						<div style="position: relative;">
							<input style="margin-bottom: 30px" class="input100" type="text" id="idfind-email" placeholder="EMAIL">
							<span class="focus-input100"></span>
						</div>
						<p class="result"></p>
						<button type="button" id="idfind-btn">찾기</button>
					</div>
				</div>
			</div>
			
			<!-- pwd찾기 Modal -->
			<div id="pwd-find-modal" class="modal">
				<div class="modal-content">
					<div class="modal-title">
						<span class="close">&times;</span>
						<h3>비밀번호 찾기</h3>
					</div>
					<div class="modal-text">
						<p>아이디를 입력해주세요</p>
						<div style="position: relative;">
							<input style="margin-bottom: 30px" class="input100" type="text" id="pwdfind-id" placeholder="ID">
							<span class="focus-input100"></span>
						</div>
						<p class="result"></p>
						<button type="button" id="pwdfind-btn">찾기</button>
					</div>
				</div>
			</div>
			
		</div>
	</div>
	
<!--===============================================================================================-->
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/daterangepicker/moment.min.js"></script>
	<script src="vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
	<script src="vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
	<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>
	
	<script src="js/login.js"></script>
<!--===============================================================================================-->
	<script type="text/javascript">
	$(function () {
		
		// 아이디 찾기
	 	$("#idfind-btn").click(function () {
	    	var findId;
	    	var email = $("#idfind-email").val();
	    	
	    //	alert(email);
			$.ajax({
				url: "../memcontrol?command=idfind",
				type: "GET",
				data: {email:email},
				async: false,
				dataType: "text",
				success: function (id) {
				//	alert(id.trim());
					findId = id.trim();
				},
				error: function () {
					alert("error");
				}
			});
			
		//	alert(findId);
			if(findId == "") {
				$(".result").eq(0).html("<font style='color: #f00'>존재하지 않는 이메일 입니다</font>");
			}else {
				var len = findId.length;
				findId = findId.substr(0, len-3) + "***";
				$(".result").eq(0).html("찾으신 아이디 : <b>" + findId + "</b>");		
			}
		});	
		// 비밀번호 찾기
		$("#pwdfind-btn").click(function () {
			var id = $("#pwdfind-id").val();
			
			$.ajax({
				url: "../memcontrol?command=pwdfind",
				type: "GET",
				data: {id:id},
				dataType: "text",
				success: function (data) {
					if(data.trim() == "false") {
						$(".result").eq(1).html("<font style='color: #f00'>존재하지 않는 아이디 입니다</font>");
					}else if(data.trim() == "true") {
						$(".result").eq(1).html("해당 아이디에 등록된 이메일로 비밀번호를 전송하였습니다");
					}
				},
				error: function () {
					
				}
			});
		});
	});
	
	</script>
</body>
</html>
