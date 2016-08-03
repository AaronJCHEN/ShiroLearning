<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script type="text/javascript" src="js/tools/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="js/tools/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
<title>System</title>
</head>
<body>
<tiles:insertAttribute name="header" />  
<div id="maincontainer">
	<div class="col-xs-2">
		<tiles:insertAttribute name="navi" />
	</div>
	<div class="col-xs-10">
		<tiles:insertAttribute name="body" />
	</div>
</div>
</body>
</html>