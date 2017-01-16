$(function(){
    $("[data-toggle='popover']").popover();

    $("[data-toggle='popover']").click(function(){
        //The blur method need to be updated.

        var thisItem = $(this);
        $.ajax({
            type: "post",
            url: "/ShiroTest/index/getSubMenu",
            data: {
                mainMenu : thisItem.attr("id"),
            },
            dataType: "text",
            success: function (response) {
                var content = "<div class='col-sm-12'><div class='col-sm-2'>Kind</div><div class='col-sm-10'>";
                var result = JSON.parse(response);
                for(i=0;i<result.length;i++){
                    content = content + "<div id='"+result[i].id+"' class='col-sm-3 popover-split'>"+result[i].name+"</div>"
                }
                content = content + "</div></div>";
                thisItem.attr("data-content",content);
                thisItem.popover("show");
            },
            error: function (response) {
                
            }
        });
    });

    // $("[data-toggle='popover']").blur(function () { 
    //     $(this).popover("hide");
    // });

    // $("[data-toggle='popover']").on('show.bs.popover',function () {
    //     $.ajax({
    //         type: "post",
    //         url: "/ShiroTest/index/getSubMenu",
    //         data: {
    //             mainMenu : $(this).attr("id"),
    //         },
    //         dataType: "json",
    //         success: function (response) {
    //             alert("aaaa");
    //             return "a";
    //         },
    //         error: function (response) {
    //             alert("error")
    //             return "b";
    //         }
    //     });
    //     var content = "<div class='col-sm-12'><div class='col-sm-2'>Pdct</div><div class='col-sm-8'><div class='col-sm-4 popover-split'>aaa</div><div class='col-sm-4 popover-split'>aaa</div><div class='col-sm-4 popover-split'>aaa</div></div></div>";
    //     $(this).attr("data-content",content);
    // });
});