var naviApp = new Vue({
    el : "#naviApp",
    data : {
        items : []
    },
    methods : {
         listOn : function(index){
            var subName = ["ary1","ary2","ary3","ary4"];
            var subMenus = sessionStorage.getItem(subName[index-1]);
            var self = this;
            if(subMenus == null){
                $.ajax({
                    type: "post",
                    url: "/ShiroTest/index/getSubMenu",
                    data: {
                        mainMenu : index,
                    },
                    dataType: "text",
                    success: function (response) {
                        if(response != '' && response !='[]'){
                            self.items = JSON.parse(response);
                            sessionStorage.setItem(subName[index-1],response)
                        }
                        else{
                            sessionStorage.setItem(subName[index-1],[null]);
                            this.items = [];
                        }
                    }
                });
            }
            else{
                if(subMenus !=null && subMenus != ''){
                    this.items = JSON.parse(subMenus);
                }
                else{
                    this.items = [];
                }
            }
         }
    },
});