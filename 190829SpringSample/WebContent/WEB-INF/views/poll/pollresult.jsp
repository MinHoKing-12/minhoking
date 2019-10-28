<%@page import="bit.com.a.model.PollSubDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <!-- 구글 - 하이차트 - 차트 코드 펜 - 스크립트 가져오고 다이브를 넣어준다. -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
    
<%
// 스크립트에 자바 쓰지 마세요 하면 컨트롤러에서 작성 후 보낸다.
// 차트뿌리기!!!!!
List<PollSubDto> list = (List<PollSubDto>)request.getAttribute("pollsublist");

// json 생성
String jsonData = "[";

for(PollSubDto p : list) {
	jsonData = jsonData + "{name:'" + p.getAnswer() + "', y:" + p.getAcount()
	+ "},";
}
// json의 콤마를 삭제하는 작업
jsonData = jsonData.substring(0, jsonData.lastIndexOf(","));
jsonData = jsonData + "]";

request.setAttribute("jsonData", jsonData);
%>

<table class="list_table" style="width: 95%;">
<colgroup>
	<col width="200px"><col width="500px">
</colgroup>

<tr>
	<th>투표번호</th>
	<td style="text-align: left">
		<input type="text" name="pollid" value="${poll.pollid }" size="50" readonly="readonly">
	</td>
</tr>

<tr>
	<th>아이디</th>
	<td style="text-align: left">
		<input type="text" name="id" value="${poll.id }" size="50" readonly="readonly">
	</td>
</tr>

<tr>
	<th>투표기한</th>
	<td style="text-align: left">
		${poll.sdate } ~ ${poll.edate }
	</td>
</tr>

<tr>
	<th>투표내용</th>
	<td style="text-align: left">
		<textarea rows="10" cols="50" readonly="readonly">${poll.question }</textarea>
	</td>
</tr>

<tr>
	<th>투표결과</th>
	<td>
		<div id="container" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
	</td>
</tr>
</table>

<script>
var chart = Highcharts.chart('container', {

    title: {
        text: 'Chart.update'
    },

    subtitle: {
        text: 'Plain'
    },

    xAxis: {
        categories: ['0점대', '10점대', '20점대', '30점대', '40점대', '50점대', '60점대', '70점대', '80점대', '90점대', '100점대']
    },

    series: [{
        type: 'column',
        colorByPoint: true,
        data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
        showInLegend: false
    }]

});


$('#plain').click(function () {
    chart.update({
        chart: {
            inverted: false,
            polar: false
        },
        subtitle: {
            text: 'Plain'
        }
    });
});

$('#inverted').click(function () {
    chart.update({
        chart: {
            inverted: true,
            polar: false
        },
        subtitle: {
            text: 'Inverted'
        }
    });
});

$('#polar').click(function () {
    chart.update({
        chart: {
            inverted: false,
            polar: true
        },
        subtitle: {
            text: 'Polar'
        }
    });
});


</script>