
//본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.

	function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                               
            }
        }).open();
    }

(function ($) {
	
	
	
	$('.validate-form').on('submit',function(){
	    var check = true;
	        
        var pwd = $("#_pwd").val();
        var pwdchk = $("#_pwdchk").val();
        var nickname = $("#_nickname").val();
        var address = $("#sample4_roadAddress").val();
        var addressdetail = $("#sample4_detailAddress").val();
        var email = $("#_email").val();

        var pwdcheck = pwdcheckFunc(pwd);
        if(!pwdcheck){
        	showValidate($("#alert-pwd"), pwdcheckMsg);
        }
        
        var pwdchkcheck = pwdchkcheckFunc(pwd, pwdchk);
        if(!pwdchkcheck){
        	showValidate($("#alert-pwdchk"), pwdchkcheckMsg);
        }
        
        var nicknamecheck = nicknamecheckFunc(nickname);
        if(!nicknamecheck ){
        	showValidate($("#alert-nickname"), nicknamecheckMsg);
        }
        
        
        var addresscheck = addresscheckFunc(address, addressdetail);
        if(!addresscheck) {
        	showValidate($("#alert-addressDetail"), addresscheckMsg);
        }
        
        var emailcheck = emailcheckFunc(email);
        if(!emailcheck){
        	showValidate($("#alert-email"), emailcheckMsg);
        }
        
        if(!pwdcheck || !pwdchkcheck || !nicknamecheck 
        		|| !emailcheck || !addresscheck){
        	check  = false;
        }
	    
        
		return check;
        
	});
	
	$('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });
	
    function showValidate( alertbox, checkMsg) {
    	alertbox.attr('data-validate', checkMsg);
    	alertbox.addClass('alert-validate')
    }
    
    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }
    
    function pwdcheckFunc( pwd ) {
    	var reg1 = /^[a-zA-Z0-9]{7,20}$/;
    	var checkNum = pwd.search(/[0-9]/g);
    	var checkEng = pwd.search(/[a-zA-Z]/g);
    	
    	if(pwd == ""){
    		pwdcheckMsg= "비밀번호를 입력해주세요";
    		return false;
    	}else if(!reg1.test(pwd)) {
    		pwdcheckMsg = "숫자와 영문자 조합으로 7~20자리를 사용해야 합니다";
    		return false;
    	}else if(checkNum < 0 || checkEng < 0) {
    		pwdcheckMsg = "숫자와 영문자를 혼용해야 합니다";
    		return false;
    	}else {
    		pwdcheckMsg = "사용가능한 비밀번호입니다";
    		return true;
    	}
    }
    
    function pwdchkcheckFunc( pwd, pwdchk) {
    	if(pwdchk == "") {
    		pwdchkcheckMsg= "비밀번호를 입력해주세요";
    		return false;
    	}else if( pwd != pwdchk ) {
    		pwdchkcheckMsg = "비밀번호가 일치하지 않습니다";
    		return false;
    	}else {
    		pwdchkcheckMsg = "비밀번호가 일치합니다";
    		return true;
    	}
    }
    
    function nicknamecheckFunc(nickname) {
    	var nickcheck = true;
    	
        if(nickname == "") {
    		nicknamecheckMsg = "닉네임을 입력해주세요";
    		return false;
    	}
       
        $.ajax({
			url: "memcontrol?command=nickcheck&type=update",
			data: { nickname:nickname },
			type: "GET",
			dataType: "text",
			async: false,
			success: function ( data ) {
			//	alert(data);
				if(data.trim() == "true") {
					nicknamecheckMsg = "이미 존재하는 닉네임 입니다";
					nickcheck = false;
				}else if(data.trim() == "false") {
					nicknamecheckMsg = "사용 가능한 닉네임 입니다";
					nickcheck = true;
	        	}
			},
			error: function () {
				alert("error");
			}
		});
        return nickcheck;
    }
    
    function addresscheckFunc(address, addressdetail) {
    	if(addressdetail == "") {
    		addresscheckMsg = "주소를 입력해주세요";
    		return false;
    	}
    	return true;
    }
    
    function emailcheckFunc( email ) {
    	if(email == ""){
    		emailcheckMsg = "이메일을 입력해주세요";
    		
      		return false
    	}else {
    		$.ajax({
				url: "memcontrol?command=emailcheck&type=update",
				data: { email:email },
				type: "GET",
				dataType: "text",
				async: false,
				success: function ( data ) {
				//	alert(data);
					if(data.trim() == "true") {
						emailcheckMsg = "이미 존재하는 이메일 입니다";
						emailcheck = false;
					}else if(data.trim() == "false") {
						emailcheckMsg = "사용 가능한 이메일 입니다";
						emailcheck = true;
		        	}
				},
				error: function () {
					alert("error");
				}
			});
    		return emailcheck;
    	}
    } 
   

})(jQuery);