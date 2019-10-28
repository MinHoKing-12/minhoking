<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="box_border" style="margin: 5px 0 10px 0">

<form action="" name="frmForm1" id="_frmForm" method="post">

<table style="margin: 3px auto 3px auto; border: 0; padding: 0">
<tr>
	<td>검색 : </td>
	<td style="padding-left: 5px">
		<input type="text" id="_s_keyword" name="s_keyword" 
		value="${empty s_keyword?'':s_keyword }" size="40">
	</td>
	<td style="padding-left: 5px">
		<span class="button blue">
			<button type="button" id="_btnSearch">검색</button>
		</span>
	</td>
</tr>
</table>
</form>
</div>

<div id="_youtube_">
	<iframe id="_youtube" width="640" height="360" 
		src="http://www.youtube.com/embed/" frameborder="0" allowfullscreen></iframe>
</div>

<table class="list_table" style="width: 85%">
<colgroup>
	<col style="width: 70px">
	<col style="width: auto">
	<col style="width: 100px">
	<col style="width: 50px">
</colgroup>

<thead>
	<tr>
		<th>순서</th><th>제목</th><th>고유번호</th><th>저장</th>
	</tr>
</thead>

<tbody>
<c:if test="${empty yulist }">
<tr>
	<td colspan="4">작성 목록이 없습니다.</td>
</tr>
</c:if>
<c:forEach items="${yulist }" var="bbs" varStatus="vs">
<tr class="_hover_tr">
	<td>${vs.count }</td>
	<td style="text-align: left;" id="_v${bbs.vname }ed2" onclick="getyoutube2('${bbs.vname}')">
		<div class="c_vname" vname='${bbs.vname }'>
		${bbs.title }
		</div>
	</td>
	<td>
		${bbs.vname }
	</td>
	<!-- 이미지를 누르는 순간 내 동영상 목록에 저장 -->
	<td onclick="getyoutube('${login.id}', '${bbs.vname }')">
		<img alt="" src="image/save.png" class="ck_seq" vname='${bbs.vname }' id='_v${bbs.vname }ed'
			loginId='${login.id }' title="${bbs.title }" keyword='${empty s_keyword?"":s_keyword }'/>
	</td>
</tr>

</c:forEach>
</tbody>
</table>

<script type="text/javascript">
$(document).ready(function () {
	// 동영상 가리기
	$("#_youtube_").hide();
	
	// 리스트가 들어온 후, 마우스 올렸을 때
	$("#_hover_tr").mouseover(function () {
		$(this).children().css("background-color", "#f0f5ff");
	}).mouseout(function () {
		$(this).children().css("background-color", "#ffffff");
	});
});

$("#_btnSearch").click(function name() {
	$("#_frmForm").attr({ "target":"_self", "action":"yutube.do" }).submit();
});

$(".c_vname").on('click', function () {
	$("#_youtube_").show();
//	alert($(this).attr("vname"));
	$("#_youtube").attr("src", "http://www.youtube.com/embed/" +$(this).attr("vname"));
});

$(".ck_seq").click(function () {
//	alert("클릭");

	var id = $(this).attr("loginId");
//	alert(id);
	var vname = $(this).attr("vname");
//	alert(vname);
	var title = $(this).attr("title");
//	alert(title);
	var category = $(this).attr("keyword");
//	alert(category);

	// 동영상 저장 하는 ajax
	$.ajax({
		type:"post",
		// ajax 상대경로 설정
		url:"<%=application.getContextPath() %>/youtubesave.do",
		// ajax 동기화	처리 하면서 넘어간다 : 비동기 // 전부다 넘어간 후 처리한다 :동기 
		async:true,	//defual값
		data:"id="+id+"&vname="+vname+"&title="+title+"&category="+category,
		success:function (msg) {
		//	alert("success");
			alert("성공적으로" + msg.vname + "이 저장되었습니다");
		},
		error:function () {
			alert("error");
		}
	});
});

function getyoutube2(vname) {
	
	$("#_v" + vname + "ed2").css("background-color", "#ff0000");
}

</script>
