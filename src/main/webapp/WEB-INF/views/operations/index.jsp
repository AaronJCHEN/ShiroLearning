<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="url" value="/ShiroTest"></c:set>
<c:forEach var="i" items="${rproducts }" varStatus="s">
	<c:if test="${s.index % 4 == 0 }">
	<div class="row">
	</c:if>
		<div class="col-sm-3">
		    <div class="thumbnail">
			    <img src="${i.img }" width="100%">
			    <div class="caption">
				    <h3>${i.name }</h3>
				    <p>${i.description }</p>
				    <p><a href="${url }/product/${i.id}" class="btn btn-primary" role="button">Button</a></p>
		    	</div>
		    </div>
	    </div>
    <c:if test="${s.index % 4 == 3 }">
	</div>
	</c:if>
</c:forEach>