<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/ShiroTest/js/operations/upload.js"></script>
<div class="row">
    <div class="col-sm-6 col-sm-offset-3">
        <div class="col-sm-6">
            <div class="input-group">
                <input type="text" class="form-control" id="uploadPreview" disabled>
                <span class="input-group-btn">
                  <button class="btn btn-default" type="button" id="browse">Browse...</button>
                </span>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="btn-group" role="group" aria-label="upload">
                <button type="button" class="btn btn-default" id="preview">Preview</button>
                <button type="button" class="btn btn-default" id="upload" disabled="disabled">Upload</button>
            </div>
        </div>
    </div>
</div>
<table class="table">
    <thead>
        <th>#</th>
        <th>Product_Name</th>
        <th>Price</th>
        <th>Category</th>
        <th>Amount</th>
        <th>Admin_Name</th>
    </thead>
    <tbody>

    </tbody>
</table>
<input type="file" name="pdctList" id="pdctList" style="display: none"/>