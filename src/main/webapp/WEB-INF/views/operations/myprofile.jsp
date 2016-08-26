<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
	#title{
		border-bottom:1px solid #ccc;
	}
	
	body{
		background-color:#eee;
	}
	
	.container{
		background-color:#fff;
	}
</style>
<div class="col-sm-12">
	<h1 id="title">Personal Profile</h1>
	<form class="form-horizontal">
	  <div class="form-group">
	    <label for="username" class="col-sm-2 control-label">User Name</label>
	    <div class="col-sm-10">
	      <input type="username" class="form-control" id="username" placeholder="User Name" value="${username }" disabled>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
	    <div class="col-sm-10">
	      <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
	    </div>
	  </div>
	  <div class="form-group">
	  	<label for="roles" class="col-sm-2 control-label">Roles</label>
	    <div class="col-sm-10">
	    <c:forEach var="i" items="${roleType }" varStatus="status">
	      <div class="checkbox" id="roles">
	          <label>
	          	<input type="checkbox"> ${i }
	          </label>
	      </div>
	    </c:forEach>
	    </div>
	  </div>
	  <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	      <button type="submit" class="btn btn-default">Sign in</button>
	    </div>
	  </div>
	</form>
</div>