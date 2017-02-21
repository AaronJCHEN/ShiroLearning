<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/ShiroTest/js/util/CookieUtil.js"></script>
<script type="text/javascript" src="/ShiroTest/js/operations/product.js"></script>
<!--model part-->
<div class="modal fade" tabindex="-1" role="dialog" id="ajaxMsg">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-header bg-success">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Add successfully</h4>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<ol class="breadcrumb">
    <li>
        <a href="javascript:history.go(-1);">Home</a>
    </li>
    <li class="active" id="pdctName">${pdct.name}</li>
</ol>
<div class="col-sm-12" style="margin-bottom: 30px;">
    <div class="col-sm-4">
        <img src="${pdct.img}" width="100%">
    </div>
    <div class="col-sm-8">
        <h2>${pdct.name} <small>(${browseTimes} readed)</small></h2>
        <p>
            <c:forEach var = "tag" items="${pdct.tags}">
                <span class="bg-info">${tag}</span>
            </c:forEach>
        </p>
        <hr></hr>
        <h3>Price : <span class="text-danger" id="pdctPrice">${pdct.price}</span></h3>
        <div class="input-group col-sm-4" style="margin-bottom: 10px;">
            <input type="text" class="form-control" placeholder="Search for...">
            <span class="input-group-btn">
                <button class="btn btn-default" type="button">UP</button>
                <!--<button class="btn btn-default" type="button">DOWN</button>-->
            </span>
        </div><!-- /input-group -->
        <p>
            <button type="button" class="btn btn-primary btn-lg">Buy it</button>
            <button type="button" class="btn btn-default btn-lg" id="addToCart">Add to Cart</button>
        </p>
    </div>
    
</div>
<div class="col-sm-12">
    <div role="tabpanel">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active">
                <a href="#detail" aria-controls="detail" role="tab" data-toggle="tab">Detail</a>
            </li>
            <li role="presentation">
                <a href="#comments" aria-controls="comments" role="tab" data-toggle="tab">Comments</a>
            </li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="detail">...</div>
            <div role="tabpanel" class="tab-pane" id="comments">
                
            </div>
            <input type="hidden" id="pdctId" value="${pdct.id}" />
        </div>
    </div>
</div>
