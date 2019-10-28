<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 자료 올리기, 업로드는 무조건 post 방식 , 멀티파트-->
<form name="frmForm" id="_frmForm" action="pdsupload.do" method="post"
		enctype="multipart/form-data">
<table class="list_table">

<tr>
	<th>아이디</th>
	<td style="text-align: left">
		<input type="text" name="id" readonly="readonly" value="${login.id }" size="50">
	</td>
</tr>

<tr>
	<th>제목</th>
	<td style="text-align: left;">
		<input type="text" name="title" size="50">
	</td>
</tr>

<tr>
	<th>파일 업로드</th>
	<td style="text-align: left;">
		<input type="file" id="_fileload" name="fileload" style="width: 400px">
		<img alt="" src="#" id="_foo">
	</td>
</tr>

<tr>
	<th>내용</th>
	<td style="text-align: left;">
		<textarea rows="10" cols="50" name="content"></textarea>
	</td>
</tr>

<tr>
	<td colspan="2" style="height: 50px; text-align: center;">
		<a href="#none" id="_btnPds" title="자료올리기">
			<img alt="" src="image/bwrite.png">
		</a>
	</td>
</tr>
</table>

</form>
    
<script type="text/javascript">
$("#_btnPds").click(function () {
	alert("자료 업로드");
	// 빈칸 확인
	$("#_frmForm").submit();
});



function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $('#_foo').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#_fileload").change(function() {
    readURL(this);
});


</script>
