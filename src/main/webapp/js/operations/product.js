$(function(){
    $("[aria-controls='comments']").on('show.bs.tab', function (e) {
        $.ajax({
            type: "post",
            url: "/ShiroTest/comment/byProduct",
            data: {
                productId : $("#pdctId").val(),
                pageNum: $("#pageNum").val()
            },
            dataType: "text",
            success: function (response) {
                var result = JSON.parse(response);
                alert("aaaa");
            },
            error: function (response) {
                alert("error");
            }
        })
    });
});