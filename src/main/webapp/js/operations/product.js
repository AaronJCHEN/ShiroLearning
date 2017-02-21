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
                for(i=0;i<result.length;i++){
                    var date = new Date(result[i].commentDate).toDateString();
                    var username = result[i].username;
                    var comments = result[i].comments;
                    var content = "<div class='col-sm-2'>"+username+": </div><div class='col-sm-10'>"
                            +comments+"</div><div class='col-sm-10 col-sm-offset-2'><small>"+date+"</small></div>"
                    $("#comments").append(content);
                }
            },
            error: function (response) {
                alert("error");
            }
        })
    });

    $("#addToCart").click(function(){
        var id = $("#pdctId").val();
        var name = $("#pdctName").html();
        var price = $("#pdctPrice").html();
        var count = 1;
        
        var username = $("#usernameh").val();
        var val = id+"_"+name+"_"+price
        var cookieVal = getCookie(username+"-cart");

        if(cookieVal == null){
            val = val+"_"+count;
            createCookie(username+"-cart",val,"/ShiroTest",2);
        }
        else{
            var res = cookieVal.match(val+"_\\d");
            if(res != null){
                count = parseInt(res[0].substr(-1))+1;
                var repStr = val+"_"+count;
                createCookie(username+"-cart",cookieVal.replace(res[0],repStr),"/ShiroTest",2);
            }
            else{
                val = cookieVal+","+val+"_"+count;
                createCookie(username+"-cart",val,"/ShiroTest",2);
            }
        }

        $('#ajaxMsg').modal('toggle');
        
        /*$.ajax({
            type: "post",
            url: "/ShiroTest/product/addToCart",
            data: {
                id : $("#pdctId").val(),
                name : $("#pdctName").html(),
                price : $("#pdctPrice").html()
            },
            dataType: "text",
            success: function (response) {
                if(response == "success"){
                   $('#ajaxMsg').modal('toggle')
                }
            },
            error : function(response){
                alert("Error");
            }
        });*/
    });
});