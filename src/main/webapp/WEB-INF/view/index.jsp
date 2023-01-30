<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page session="false"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js">
</script>

<html>
<head>
<title>Home</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#execute").click(function(){
			alert("이제 만듭니다 !!~~");
			
			url="/make";
			
			var content = $("#content").val();
			$("#img").attr("src",url+"?content="+content);
		});
	});
</script>
</head>
<body>
	<h1>Zxing(Zebra Crossing) Code Test</h1>

	<div>
		<input id="content" type="text" name="content" /> 
		<input type="button" id="execute" value="QR코드 생성" />
		<img id="img" style="display:none;" onload="this.style.display = 'block'"/>
	</div>
</body>
</html>