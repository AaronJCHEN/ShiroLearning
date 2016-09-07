<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${not empty recent}">
<div class="panel panel-default">
  	<div class="panel-heading">Recent browse history</div>
  	<ul id="recentHistory" class="list-group">
  		<c:forEach var="i" items="${recent }">
  			<li class="list-group-item"><a href="/ShiroTest/product/${i.id }">${i.name }</a></li>
  		</c:forEach>
  	</ul>
</div>
</c:if>