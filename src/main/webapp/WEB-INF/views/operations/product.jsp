<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<ol class="breadcrumb">
    <li>
        <a href="#">Home</a>
    </li>
    <li class="active">${pdct.name}</li>
</ol>
<div class="col-sm-12" style="margin-bottom: 30px;">
    <div class="col-sm-4">
        <img src="${pdct.img}" width="100%">
    </div>
    <div class="col-sm-8">
        <h2>${pdct.name}</h2>
        <hr></hr>
        <h3>Price : <span class="text-danger">${pdct.price}</span></h3>
        <p>
            <button type="button" class="btn btn-primary btn-lg">Buy it</button>
            <button type="button" class="btn btn-default btn-lg">Add to Cart</button>
        </p>
    </div>
</div>
<div class="col-sm-12">
    <div role="tabpanel">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active">
                <a href="#home" aria-controls="home" role="tab" data-toggle="tab">Detail</a>
            </li>
            <li role="presentation">
                <a href="#tab" aria-controls="tab" role="tab" data-toggle="tab">Comments</a>
            </li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="home">...</div>
            <div role="tabpanel" class="tab-pane" id="tab">...</div>
        </div>
    </div>
</div>
