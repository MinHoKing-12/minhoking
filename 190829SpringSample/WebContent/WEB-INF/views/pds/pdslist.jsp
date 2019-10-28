<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="box_border" style="margin-top: 5px; margin-bottom: 10px">
<form action="" name="frmForm1" id="_frmFormSearch" method="post">
<table style="margin: 3px auto 3px auto">
<tr>
	<td>검색</td>
	<td style="padding-left: 5px">
		<select id="_s_category" name="s_category">
			<option value="" selected="selected">선택</option>
			<option value="title">제목</option>
			<option value="content">내용</option>
			<option value="writer">작성자</option>
		</select>
	</td>
	<td style="padding-left: 5px">
		<input type="text" id="_s_keyword" name="s_keyword">
	</td>사용자
	<td style="padding-left: 5px">
		<span class="button blue">
			<button type="button" id="_btnSearch">검색</button>
		</span>
	</td>
</tr>
</table>

<input type="hidden" name="pageNumber" id="_pageNumber" value="${(empty pageNumber)?0: pageNumber}">

<input type="hidden" name="recordCountPerPage" id="_recordCountPerPage" value="${(empty recordCountPerPage)?0:recordCountPerPage }">

</form>
</div>
<table class="list_table" style="width: 85%">
<colgroup>
<col width="50"><col width="100"><col width="300"><col width="50">
<col width="50"><col width="50"><col width="100"><col width="50">
</colgroup>

<thead>
<tr>
	<th>번호</th><th>작성자</th><th>제목</th><th>다운로드</th>
	<th>조회수</th><th>다운수</th><th>작성일</th><th>삭제</th>
</tr>
</thead>

<tbody>

<c:forEach var="pds" items="${pdslist }" varStatus="vs">
<tr class="_hover_tr">
	<td>${vs.count }</td>
	<td>${pds.id }</td>
	<td style="text-align: left;">
		<a href="pdsdetail.do?seq=${pds.seq }">
		 	${pds.title }
		 </a>
	</td>
	<td>
		<input type="button" name="btnDown" value="다운로드"
		 onclick="filedowns('${pds.filename}', '${pds.seq }')" >
	</td>
	<td>${pds.readcount }</td>
	<td>${pds.downcount }</td>
	<td>
		<font size="1">${pds.regdate }</font>
	</td>
	<c:if test="${login.id eq pds.id }">
	<td>
		<img alt="" src="image/del.png" data_file_seq="${pds.seq }"
		class="btn_fileDelete">
	</td>
	</c:if>
	
</tr>
</c:forEach>

</tbody>
</table>

<!-- 추가 버튼 -->
<div id="button.wrap">
	<span class="button blue">
		<button type="button" id="_btnAdd">자료추가</button>
	</span>
</div>

<!-- 다운로드 버튼 클릭시 -->
<form name="file_Down" action="fileDownload.do" method="post">
	<input type="hidden" name="filename">
	<input type="hidden" name="seq">
</form>

<div id="paging_wrap">
													  <!-- 한번 만 읽어라 -->
	<jsp:include page="/WEB-INF/views/pds/paging.jsp" flush="false">
		<jsp:param name="pageNumber" value="${pageNumber }"/>
		<jsp:param name="totalRecordCount" value="${totalRecordCount }"/>
		<jsp:param name="pageCountPerScreen" value="${pageCountPerScreen }"/>
		<jsp:param name="recordCountPerPage" value="${recordCountPerPage }"/>
	</jsp:include>
</div>


<script type="text/javascript">

$("#_s_category").val( "${s_category}" ).attr("selected","selected");

$("#_s_keyword").val( "${s_keyword}" );

// 네임 접근 방법
function filedowns(filename, seq) {
	var doc = document.file_Down;
	doc.filename.value = filename;
	doc.seq.value = seq;
	doc.submit();
}



$(".btn_fileDelete").on('click', function () {
//	alert("삭제");
	//사용자 정의 시퀀스 갖는 법
//	alert($(this).attr("data_file_seq"));
	location.href = "pdsdelete.do?seq="+$(this).attr("data_file_seq");
});


$("#_btnAdd").on('click', function () {
//	alert("자료추가");
	location.href = "pdswrite.do";
});



function goPage( pageNumber ) {
	$("#_pageNumber").val(pageNumber);
	$("#_frmFormSearch").attr("action", "pdslist.do").submit();
}

$("#_btnSearch").click(function () {
	alert("클릭");
	$("#_pageNumber").val("0");
	$("#_frmFormSearch").attr("action", "pdslist.do").submit();
});

</script>
