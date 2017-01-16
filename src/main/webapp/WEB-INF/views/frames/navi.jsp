<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/ShiroTest/js/operations/navi.js"></script>
<link rel="stylesheet" type="text/css" href="/ShiroTest/css/operations/menu.css"></link>
<div class="panel panel-default">
	<c:set var="data_template" value="<div class='popover popover-enhance'><div class='popover-content row'></div></div>" />
  	<div class="panel-heading">Category</div>
  	<div id="onlinelist" class="list-group">
		<c:forEach var="i" items="${mainMenu}">
			<a tabindex="${i.id}" id="${i.id}" class="list-group-item" style="cursor: pointer" data-container="body" data-toggle="popover" data-placement="right"
            data-html="true" data-template="${data_template}" data-content="coming soon...">
				${i.name}<i class="fa fa-angle-right pull-right" aria-hidden="true"></i>
			</a>
		</c:forEach>
	</div>
</div>