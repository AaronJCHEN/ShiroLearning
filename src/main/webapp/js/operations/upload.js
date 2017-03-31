$(function(){
    var tempResponse;

    $("#browse").click(function(){
         $("#pdctList").click();
    });

    $("#pdctList").change(function(){
        var fileName = $("#pdctList").val();
         $("#uploadPreview").val(fileName);
    })

    $("#preview").click(function(){
        var formData = new FormData();
        formData.append('pdctList', $('#pdctList')[0].files[0]);
        $.ajax({
            type: "post",
            url: "/ShiroTest/admin/uploadPreview",
            cache: false,
            data: formData,
            contentType: false,
            processData: false,
            dataType: "json",
            success: function (response) {
                for(i=0;i<response.length;i++){
                    var item = response[i];
                    var code = "<tr><td>"+i+"</td>"+"<td>"+item.name+"</td>"+"<td>"+item.price+"</td>"
                    +"<td>"+item.category+"</td>"+"<td>"+item.amount+"</td>"+"<td>"+item.adminName+"</td></tr>";
                    $("tbody").append(code);
                }
                $("#preview").attr("disabled", "disabled");
                $("#upload").removeAttr("disabled");
                tempResponse = response;
            },
            error : function(XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest.status);
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            }
        });
    });

    $("#upload").click(function(){
        $.ajax({
            type: "post",
            url: "/ShiroTest/admin/uploadToDB",
            contentType:"application/json",
            data: JSON.stringify(tempResponse),
            dataType: "text",
            success: function (response) {
                alert("Update "+response+" records successfully");
                tempResponse = null;
            },
            error : function(XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest.status);
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            }
        });
    });
});