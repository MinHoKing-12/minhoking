<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<div align="center" class="divmargin">
<form id="frm">
<input type="hidden" name="_seq" value="${pdsdto.seq }">
<table border="1" class="list_table">
<col width="100"><col width="300">
<tr>
	<th>아이디</th>
	<td>
		<input type="text" value="${login.id }" name="id" readonly="readonly" style="width: 98%">
	</td>
</tr>
<tr>
	<th>제목</th>
	<td>
		<input type="text" value="${pdsdto.title }" id="_title" name="title" readonly="readonly" style="width: 98%">
	</td>
</tr>
<tr>
	<th>작성일</th>
	<td>
		<input type="text" value="${pdsdto.regdate }" name="date" readonly="readonly" style="width: 98%">
	</td>
</tr>
<tr>
	<th>파일</th>
	<td style="text-align: left">
		<input type="button" name="btnDown" value="다운로드"
		 onclick="filedowns('${pdsdto.filename}', '${pdsdto.seq }')" >
		 ${pdsdto.oldfilename }
	</td>
</tr>
<tr>
	<th>내용</th>
	<td>
		<textarea rows="20" cols="10" id="_content" name="content" readonly="readonly" style="width: 98%">${pdsdto.content }</textarea>
	</td>
</tr>
<tr>
	<td colspan="2">
		<input type="button" value="돌아가기" id="backBtn">
		<input type="button" value="수정" id="upBtn">
	</td>
</tr>
</table>
</form>
</div>

<form name="file_Down" action="fileDownload.do" method="post">
	<input type="hidden" name="filename">
	<input type="hidden" name="seq">
</form>

<script type="text/javascript">

function filedowns(filename, seq) {
	var doc = document.file_Down;
	doc.filename.value = filename;
	doc.seq.value = seq;
	doc.submit();
}

$(document).ready(function name() {
	
	$("#backBtn").click(function () {
		location.href = "pdslist.do";
	});
	
	$("#upBtn").click(function () {
		alert("수정창으로 이동 합니다");
		$("#frm").attr("action", "pdsupdate.do").submit();
	});
});

</script>
