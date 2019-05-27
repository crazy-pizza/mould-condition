function sendRequest(url,data,callback) {
    $.ajax({
        type:"POST",
        url: url,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        dataType : 'json',
        timeout : 20000,
        success: function (jsonResult) {
            //登陆失效
            if(jsonResult.code == 001){
                alert(jsonResult.msg)
                $.removeCookie('accessToken',{path:"/"})
                window.location.href = "/static/login.html";
            }else if (jsonResult.code == 000) {
                callback(jsonResult)
            }else {
                alert(jsonResult.msg)
            }
        },
        error: function (data) {
            alert("系统异常，请稍后重试！");
        }
    });

}
