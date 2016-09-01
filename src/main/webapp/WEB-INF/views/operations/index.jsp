<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="i" items="${rproducts }" varStatus="s">
	<c:if test="${s.index % 3 == 0 }">
	<div class="row">
	</c:if>
		<div class="col-sm-3">
		    <div class="thumbnail">
			    <img src="${i.img }" width="100%">
			    <div class="caption">
				    <h3>${i.name }</h3>
				    <p>${i.description }</p>
				    <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
		    	</div>
		    </div>
	    </div>
    <c:if test="${s.index % 3 == 2 }">
	</div>
	</c:if>
</c:forEach>
<!-- <div class="row">
	<div class="col-sm-3">
	    <div class="thumbnail">
		    <img src="/ShiroTest/img/example.png" width="100%">
		    <div class="caption">
			    <h3>Thumbnail label</h3>
			    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
			    <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
	    	</div>
	    </div>
    </div>
    <div class="col-sm-3">
	    <div class="thumbnail">
		    <img src="/ShiroTest/img/example.png" width="100%">
		    <div class="caption">
			    <h3>Thumbnail label</h3>
			    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
			    <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
	    	</div>
	    </div>
    </div>
    <div class="col-sm-3">
	    <div class="thumbnail">
		    <img src="/ShiroTest/img/example.png" width="100%">
		    <div class="caption">
			    <h3>Thumbnail label</h3>
			    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
			    <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
	    	</div>
	    </div>
    </div>
    <div class="col-sm-3">
	    <div class="thumbnail">
		    <img src="/ShiroTest/img/example.png" width="100%">
		    <div class="caption">
			    <h3>Thumbnail label</h3>
			    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
			    <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
	    	</div>
	    </div>
    </div>
</div> -->