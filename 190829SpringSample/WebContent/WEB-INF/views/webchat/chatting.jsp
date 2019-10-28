<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

채팅명 : <input type="text" id="nickname">

<input type="button" id="enterBtn" value="입장" onclick="connect()">

<input type="button" id="exitBtn" value="나가기" onclick="disconnect()">
<br><br>

<h1>대화영역</h1>
<textarea rows="10" cols="50" id="chattingArea"></textarea>

<br>
<input type="text" id="message" size="50">
<input type="button" id="sendBtn" value="전송" onclick="send()"> 

<script>

// 웹 소켓 변수
var wsocket;

// 네트워크는 하나씩 다 해보고 나갈 것
// 입장 버튼을 클릭시에 호출 함수
function connect() {
	// 이미 소켓이 생성 되어있거나 소켓이 닫히지 않았을 때 막아주는 조건문
	if(wsocket != undefined && wsocket.readyState != WebSocket.CLOSE){
		alert("이미 입장하셨습니다");
		return;
	}
	
	// Web Socket 생성
	if($("#nickname").val() == "미농"){
		wsocket = new WebSocket("ws://localhost:8090/190829SpringSample/echo.do");
	}else {
		wsocket = new WebSocket("ws://192.168.0.57:8090/190829SpringSample/echo.do");	
	}
	alert("wsocket:" +wsocket)
	
	wsocket.onopen = onOpen;
	wsocket.onmessage = onMessage;
	wsocket.onclose = onClose;
}

function disconnect() {
	wsocket.close();
	location.href="chatting.do";
}

// onopen(연결), onmessage(메세지), onclose(끊겼을 때)

function onOpen(evt) {
	appendMessage("연결되었습니다");
}
// 서버로부터 메시지가 도착 했을 때 자동 호출되는 함수
function onMessage(evt) {
	var data = evt.data;
	if(data.substring(0,4) == "msg:"){ 		/* 1번째 글자부터 4번째 글자까지 */
		appendMessage(data.substring(4));	/* 5번째 글자부터 마지막 글자까지 */
	}
}

function onClose(evt) {
	appendMessage("연결 종료");
}

function send() {
	
	var nickname = $("#nickname").val();
	var msg = $("#message").val();
	
	// 실제 전송 부분
	wsocket.send("msg:" + nickname + " : " + msg);
	$("#message").val("");
}

function appendMessage(msg) {
	
	// 메세지를 추가 하고 개행
	$("#chattingArea").append(msg + "<br>");
	
	// 높이 구하고 위로 밀어버리는 것
	var chatAreaHeight = $("#chattingArea").height();
	
	var maxScroll = $("#chattingArea").height() - chatAreaHeight;
	
	$("#chattingArea").scrollTop(maxScroll);
}
</script>

