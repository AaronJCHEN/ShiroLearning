$(function(){
    $("#m1").click(function(){
        $("#m1").popover("toggle");
    });
    
    $("#m1").on('show.bs.popover',function () {
        var content = "<div class='col-sm-12'><div class='col-sm-2'>Pdct</div><div class='col-sm-8'><div class='col-sm-4 popover-split'>aaa</div><div class='col-sm-4 popover-split'>aaa</div><div class='col-sm-4 popover-split'>aaa</div></div></div>";
        $("#m1").attr("data-content",content);
    });
});