function createCookie(name,value,path,expires){
    var cookieStr = name+"="+escape(value);
    if(expires>0){ 
        var date=new Date(); 
        date.setTime(date.getTime()+expires*3600*1000); 
        cookieStr=cookieStr+"; expires="+date.toGMTString(); 
    } 
    if(path!=null & path!=""){
        cookieStr = cookieStr + "; path="+path
    }
    document.cookie = cookieStr;
}

function getCookie(name){
    var cookies = document.cookie;
    var arr = cookies.split(";");
    for(var i=0;i<arr.length;i++){
        if(arr[i].indexOf(name)>-1){
            return arr[i].substr(arr[i].indexOf("=")+1);
        }
    }
    return null
}

function deleteCookie(name){
    var expires = (new Date()).getTime - 1000;
    document.cookie = name + "=''; expires="+expires
}
