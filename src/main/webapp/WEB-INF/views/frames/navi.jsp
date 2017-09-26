<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/ShiroTest/css/operations/menu.css"></link>
<div id="naviApp">
<div class="panel panel-default">
  	<div class="panel-heading">Category</div>
  	<ul class="list-group">
		<c:forEach var="i" items="${mainMenu}">
			<li class="list-group-item list-group-item-enhance" key="${i.id}" @mouseover="listOn(${i.id})" id="main${i.id}">
				${i.name}
				<ul class="list-group list-sub-group" v-if="items.length != 0">
					<li class="list-group-item list-group-item-enhance" v-for="item in items" id = "{{item.id}}">{{item.name}}</li>
				</ul>
			</li>
		</c:forEach>
	</ul>
</div>
</div>

<script type="text/javascript" src="/ShiroTest/js/operations/navi.js"></script>