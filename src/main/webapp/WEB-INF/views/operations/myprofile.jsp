<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="/ShiroTest/js/operations/myprofile.js"></script>
<link rel="stylesheet" href="/ShiroTest/css/operations/myprofile.css" />
<script type="text/javascript">
$(function(){
	var myRoles = ${myRoles};
	for(r in myRoles){
		$("[name='"+myRoles[r]+"']").prop("checked","checked");
	}
});
</script>
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
	      <div class="checkbox">
	          <label>
	          	<input type="checkbox" name="${i }" id="roles"> ${i }
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