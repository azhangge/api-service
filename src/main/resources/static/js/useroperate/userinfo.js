function savePersonalInfo(){
    $.ajax({
        url: "/account/user_info/updPersonalInfo",
        type: "POST",
        async:false,
        data:$('#personalInfoFm').serialize(),
        success: function (result) {
            alert(result.message);
            window.location.reload();
        }
    });
}
