
(function ($) {
    "use strict";
    
    var user_id = $.cookie("user_id");
    if(user_id != null){	// 지정한 쿠키가 있을 경우
    //	alert("쿠키있음!");
    	$("#_id").val( user_id );	// id를 텍스트박스에 기입
    	$("#remember_id").attr("checked", "checked");	// 체크박스에 체크
    }
    
    $("#_id").blur(function() {
    	if($("#remember_id").is(":checked")) {
    		$.cookie("user_id", $("#_id").val().trim(), {expires:7, path:'/'});
        }
    });

    $("#remember_id").click(function () {
    	if($("#remember_id").is(":checked")){	// 체크가 되있을 경우
    	//	alert("체크됨!");
    		if($("#_id").val().trim() == ""){
    			showValidate($("#_id"));
    			$("#remember_id").prop("checked", false);
    		}else{	// 정상기입 한 경우
    			// 쿠키 저장									기한			모든경로
    			$.cookie("user_id", $("#_id").val().trim(), {expires:7, path:'/'});
    		}
    	}else{
    	//	alert("체크 해제!");		      기한을 없앰
    		$.removeCookie("user_id", {path:'/'});
    	}
    });
    
    var input = $('.validate-input .input100');
    
    $('.login100-form-btn').on('click', function(){
    	
    	$("#wrong-idpwd-msg").css("display", "none");
    	
    	var input_id = $("#_id").val();
        var input_pwd = $("#_pwd").val();
    	var check = false;
    	
        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                check = true;
            }
        }
        if(check) {
        	return;
        }

        $.ajax({
			url: "../memcontrol",
			type: "POST",
			data: { command:'loginAf', id:input_id, pwd:input_pwd },
			success: function ( data ) {
				if(data.trim() == "member") {
					location.href="../memcontrol?command=main";
				}else if(data.trim() == "admin") {
					location.href="../admincontrol?command=memlist";
				}else if(data.trim() == "false") {
					$("#wrong-idpwd-msg").css("display", "block");
					
				}
			},
			error: function () {
				alert("error");				
			}
		});

    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });
    
    $('.close').click(function () {
    	$(".modal").css("display", "none");
    });

    function validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();
        $(thisAlert).removeClass('alert-validate');
    }

})(jQuery);

function idFindModal() {
	$("#id-find-modal").css("display", "block");
	$("#idfind-email").val("");
	$(".result").html("");
}

function pwdFindModal() {
	$("#pwd-find-modal").css("display", "block");
}
