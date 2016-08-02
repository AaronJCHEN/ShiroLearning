<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- saved from url=(0038)http://v3.bootcss.com/examples/signin/-->
<html lang="zh-CN">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！-->
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Signin Template for Bootstrap</title>
    <!-- Bootstrap core CSS-->
    <link href="/ShiroTest/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ShiroTest/css/font-awesome.min.css" rel="stylesheet">
    <link href="/ShiroTest/css/signin.css" rel="stylesheet">
    <script type="text/javascript" src="/ShiroTest/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/ShiroTest/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/ShiroTest/js/init.js"></script>
  </head>
  <body>
    <div class="container">
      <form id="login" method="post" action="submit" class="form-signin">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" class="sr-only">Username</label>
        <input id="inputEmail" type="text" placeholder="Username" required="" autofocus="" name="username" class="form-control">
        <label for="inputPassword" class="sr-only">Password</label>
        <input id="inputPassword" type="password" placeholder="Password" required="" name="password" class="form-control">
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <button id="sbmt" type="button" class="btn btn-lg btn-primary col-sm-6">Sign in</button>
        <button id="rgst" type="button" data-toggle="modal" data-target="#reg" class="btn btn-lg btn-default col-sm-6">Register</button>
      </form>
      <div id="reg" role="dialog" aria-labelledby="gridSystemModalLabel" class="modal fade">
        <div role="document" class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">&times;</span></button>
              <h4 id="gridSystemModalLabel" class="modal-title">Please Input information</h4>
            </div>
            <div class="modal-body">
              <form id="register" method="post" action="register" class="form-group">
                <div class="form-group">
                  <label for="regName">Username</label>
                  <input id="regName" type="text" placeholder="Username" required="" autofocus="" name="username" class="form-control">
                </div>
                <div class="form-group">
                  <label for="regPassword">Password</label>
                  <input id="regPassword" type="password" placeholder="Password" required="" name="password" class="form-control">
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" data-dismiss="modal" class="btn btn-default">Close</button>
              <button id="rgstsbmt" type="button" class="btn btn-primary">submit</button>
            </div>
          </div>
          <!-- /.modal-content-->
        </div>
        <!-- /.modal-dialog-->
      </div>
      <!-- /.modal-->
    </div>
  </body>
</html>