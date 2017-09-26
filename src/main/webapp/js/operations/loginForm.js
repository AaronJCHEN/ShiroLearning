var loginForm = new Vue({
    el : '#login',
    data : {
      username : '',
      showList : false
    },
    computed : {
      usernameCount : function(){
        if(this.username.length > 6)
          return true;
        else
          return false;
      },
      doShowList : function(){
        if(this.username == '' && !this.showList){
          this.showList = true;
          return false;
        }
        else if((this.username != '' && !this.showList) || (this.username == '' && this.showList))
          return false;
        else
          return true;
      },
      options : function(){
        var tails = ['gmail.com','sina.com','163.com','outlook.com','qq.com']
        var tailsWithoutAt = ['@gmail.com','@sina.com','@163.com','@outlook.com','@qq.com']
        var address = this.username;
        if(address == null || address == '')
          return tailsWithoutAt;
        else{
          if(address.indexOf('@')== -1)
            return tailsWithoutAt;
          else{
            if(address.substring(address.indexOf('@')+1) == '')
              return tails;
            else{
              return [];
            }
          }
        }
      }
    },
    methods : {
      handleNameChoose : function(address){
        this.username = address;
        this.showList = false;
      },
      submitForm : function(event){
          if(this.username == null || this.username == '')
            event.preventDefault();
      }
    }
})