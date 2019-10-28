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

</head>
<body>
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<form class="login100-form validate-form p-l-55 p-r-55 p-t-178 p-b-100" method="post"
					 	action="../memcontrol">
					<span class="login100-form-title">
						SIGN UP
					</span>
					<input type="hidden" name="command" value="accountAf">
					<div class="m-b-10 m-l-10 f-s">ID</div>
					<div id="alert-id" class="wrap-input100 validate-input m-b-16" data-validate="">
						<input class="input100" type="text" id="_id" name="id" placeholder="ID">
						<span class="focus-input100"></span>
					</div>
					<div class="m-b-10 m-l-10 f-s">비밀번호</div>
					<div id="alert-pwd" class="wrap-input100 validate-input m-b-16" data-validate = "">
						<input class="input100" type="password" id="_pwd" name="pwd" placeholder="PASSWORD">
						<span class="focus-input100"></span>
					</div>
					<div style="height: 50px; background-color: #fff; display: none; margin: 0" id="wrong-idpwd-msg" class="wrap-input100 validate-input m-b-16 alert-validate" 
						data-validate="Caps Lock키가 활성화 되었습니다."></div>
					<div class="m-b-10 m-l-10 f-s">비밀번호 확인</div>
					<div id="alert-pwdchk" class="wrap-input100 validate-input m-b-16" data-validate = "">
						<input class="input100" type="password" id="_pwdchk" name="pwdchk" placeholder="PASSWORD">
						<span class="focus-input100"></span>
					</div>
					<div style="height: 50px; background-color: #fff; display: none; margin: 0" id="wrong-idpwd-msg" class="wrap-input100 validate-input m-b-16 alert-validate" 
						data-validate="Caps Lock키가 활성화 되었습니다."></div>
					<div class="m-b-10 m-l-10 f-s">닉네임</div>
					<div id="alert-nickname" class="wrap-input100 validate-input m-b-16" data-validate="">
						<input class="input100" type="text" id="_nickname" name="nickname" placeholder="NICKNAME">
						<span class="focus-input100"></span>
					</div>
					<div class="m-b-10 m-l-10 f-s">이름</div>
					<div id="alert-name" class="wrap-input100 validate-input m-b-16" data-validate="">
						<input class="input100" type="text" id="_name" name="name" placeholder="NAME">
						<span class="focus-input100"></span>
					</div>
					
					<div class="m-b-10 m-l-10 f-s">주소</div>
					
						<div class="wrap-input100 validate-input m-b-16" data-validate="" style="width: 50%; float:left">
							<input readonly="readonly" class="input100" type="text" id="sample4_postcode" name="postnum" placeholder="POST NUMBER">
							<span class="focus-input100"></span>
						</div>
						
						<div class="m-b-16" style="width: 40%; display: inline-block; margin-left: 10%">
							<input type="button" class="login100-form-btn"
							 	onclick="sample4_execDaumPostcode()" value="우편번호 찾기">
						</div>
					
					<div id="alert-address" class="wrap-input100 validate-input m-b-16" data-validate="">
						<input readonly="readonly" class="input100" type="text" id="sample4_roadAddress" name="address" placeholder="ADDRESS">
						<span class="focus-input100"></span>
					</div>
					
					<div id="alert-addressDetail" class="wrap-input100 validate-input m-b-16" data-validate="">
						<input class="input100" type="text" id="sample4_detailAddress" name="address_detail" placeholder="ADDRESS DETAIL">
						<span class="focus-input100"></span>
					</div>
									
					<div class="m-b-10 m-l-10 f-s">이메일</div>
					<div id="alert-email" class="wrap-input100 validate-input m-b-16" data-validate="">
						<input class="input100" type="text" id="_email" name="email" placeholder="E-MAIL">
						<span class="focus-input100"></span>
					</div>

					<div class="container-login100-form-btn m-t-50 ">
						<button id="create_btn" class="login100-form-btn" type="submit">
							CREATE ACCOUNT
						</button>
					</div>
				</form>
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
	<script src="js/account.js"></script>
<!--===============================================================================================-->
	<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
	


</body>
</html>
