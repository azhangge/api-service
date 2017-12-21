function register() {
    //获取页面参数
    var userName = $.trim($('#username').val());
    var passWord = $.trim($('#password').val());
    var phoneNo  = $.trim($('#phone_no').val());
    var rePassword = $.trim($('#re_password').val());
    var code = $.trim($('#code').val());

    //验证
    if(userName == null || userName == "") {
        alert("用户名为必填项!");
        return false;
    }

    if(!isRegisterUserName){
        return false;
    }

    if(passWord == null || passWord == "" || passWord.length < 4 ||  passWord.length > 20) {
        alert("请输入4-20位用户密码!");
        return false;
    }

    if(rePassword == null || rePassword == "" || rePassword.length < 4 || rePassword.length > 20) {
        alert("请输入4-20位确认密码!");
        return false;
    }

    if(!rePassword == passWord) {
        alert("两次密码输入不一样!");
        return false;
    }

    if(phoneNo == null || phoneNo == "") {
        alert("手机号码为必填项!");
        return false;
    }

    if(!checkPhone(phoneNo)){
        return false;
    }

    if(code == null || code == "") {
        alert("手机验证码为必填项!");
        return false;
    }
    var user = {};
    user.username = userName;
    user.phoneNo = phoneNo;
    user.password = $.md5(passWord);
    user.code = code;
    user.sysId = "1e1c8e2e-0bea-4781-a2bd-b625fbb39ab5";
    user.type = 1;
    user.device = 0;

    $.ajax({
        url: "/account/registerportal",
        type: "POST",
        data: JSON.stringify(user),
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        success: function (result) {
            alert(result.message);
            window.location.reload();
        }
    });

}

/**
 * 发送验证码
 */
function sendCode() {
    var that = this;

    var phoneNo = $.trim($('#phone_no').val());
    if(!checkPhone(phoneNo)){
        return;
    }
    var sysId = "1e1c8e2e-0bea-4781-a2bd-b625fbb39ab5";
    var param = "?phoneNo=" + phoneNo + "&sysId=" + sysId;

    $.ajax({
        url: "/account/code"+param,
        type: "get",
        contentType: 'application/json;charset=UTF-8',
        success: function (result) {
            time();
        }
    });
}

var wait=300;
function time() {
    if (wait == 0) {
        $('#codeBtn').removeAttr("disabled");
        $('#codeBtn').val("免费获取验证码");
        wait = 300;
    } else {
        $('#codeBtn').attr("disabled",'true');
        $("#codeBtn").val("重新发送(" + wait + ")");
        wait--;
        setTimeout(function() {
                time()
            },
            1000);
    }
}

/**
 * 验证手机号码
 * @param phone
 * @returns {boolean}
 */
function checkPhone(phone){
    if(!(/^1[34578]\d{9}$/.test(phone))){
        alert("手机号码有误，请重填");
        return false;
    }
    return true;
}




/**
 *
 * 验证帐号是否合法
 * 校验登录名：只能输入5-20个以字母开头、可带数字、“_”、“.”的字串
 *
 * @param str
 */
function isRegisterUserName(s)
{
    var patrn=/^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/;
    if (!patrn.exec(s)){
        alert("账号输入格式不正确");
        return false
    }
    return true
}