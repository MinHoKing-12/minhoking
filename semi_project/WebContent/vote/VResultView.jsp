<!DOCTYPE html>
<%@page import="votes.VoteDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
  <meta charset="utf-8">
  <title>column chart</title>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
</head>
 
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
 
<body>

<%
List<VoteDto> list = (List<VoteDto>)request.getAttribute("result");

String jsonlike = "[";

for(VoteDto v : list){
	
	jsonlike += "{name:'" +  v.getSeq() +'.'+ v.getName() + "',y:" + v.getLikes() + "},";
}
jsonlike = jsonlike.substring(0,jsonlike.lastIndexOf(","));
jsonlike += "]";
System.out.println(jsonlike);
request.setAttribute("jsonlike", jsonlike);
%>

<div id="container">

<script type="text/javascript">


Highcharts.chart('container', {
	  chart: {
	    type: 'column'
	  },
	  title: {
	    text: '[당신의 선수에게 투표하세요] 결과 그래프',
	    	style: {
	    		fontWeight : 'bold',
	    		fonSize: '20px',
	    		fontFamily: 'Verdana, sans-serif'
	    	}
	  },
	  subtitle: {
	    text: ''
	  },
	  xAxis: {
	    type: 'category',
	    labels: {
	      rotation: -18,
	   
	      style: {
	    		color: 'black',
		    	fontWeight: 'bold',
		        fontSize: '14px',
		        fontFamily: 'Verdana, sans-serif'
	      }
	    }
	  },
	  yAxis: {
	    min: 0,
	    title: {
	      text: '득표수'
	    }
	  },
	  legend: {
	    enabled: false
	  },
	  tooltip: {
	    pointFormat: '투표수 : <b>{point.y: 1f} 표</b>'
	  },
	  series: [{
	    name: '선수',
	    data:
	    <%=request.getAttribute("jsonlike")%>,
	    color: "#7B68EE",
	    style: {
	     fontSize: '10px'
	    },
	    
	    dataLabels: {
	      enabled: true,
	      rotation: -90,
	      color: '#FFFFFF',
	      align: 'right',
	      format: '{point.y: 1f} 표', // one decimal
	      y: 100, // 10 pixels down from the top
	      style: {
	        fontSize: '15px',
	        fontFamily: 'Verdana, sans-serif'
	      }
	    }
	  }]
	});
</script>
</div>
</body>
</html>


