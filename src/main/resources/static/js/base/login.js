function login() {
    var userName = $.trim($('#login_username').val());
    var passWord = $.trim($('#login_password').val());
    var user = {};
    user.userName = userName;
    user.password = $.md5(passWord);
    user.sysId = "1e1c8e2e-0bea-4781-a2bd-b625fbb39ab5";
    user.type = 1;
    user.device = 0;
    $.ajax({
        url: "/account/loginportal",
        type: "POST",
        data: JSON.stringify(user),
        dataType: 'json',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        success: function (result) {
            alert(result.message);
            window.location.href = window.location.href;
            //window.location.reload(true);
        }
    });

}
