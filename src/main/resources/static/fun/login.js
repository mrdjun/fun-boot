$(function () {
    $("#btnSubmit").click(function () {
        login();
    })
});


function login() {
    $.modal.loading($("#btnSubmit").data("loading"));
    var loginName = $.common.trim($("input[name='username']").val());
    var password = $.common.trim($("input[name='password']").val());

    $.ajax({
        url: ctx + "/api/user/login",
        type: "post",
        data: {
            "loginName": loginName,
            "password": password
            // "validateCode" : validateCode,
            // "rememberMe": rememberMe
        },
        success: function (res) {
            if (res.code === 200) {
                console.log(res.data);
                window.location = "index";
            } else {
                $.modal.closeLoading();

                $.modal.msg(res.msg);
            }
        }
    });
}
