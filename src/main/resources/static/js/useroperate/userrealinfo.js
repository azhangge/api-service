function submitUserRealInfo(){
    $.ajax({
        url: "/account/realinfo/realNameAuth",
        type: "POST",
        async:false,
        data:$('#userRealInfoFm').serialize(),
        success: function (result) {
            alert(result.message);
            window.location.reload();
        }
    });
}

function uploadImg(fileId,inputId,imageId){
    var formData = new FormData();
    var files = document.getElementById(fileId).files;
    if(files != null && files.length != 0){
        if("image" == files[0].type.substring(0,5)){
            formData.append("file", files[0]);
            $.ajax({
                url: "/fs/upload",
                type: "POST",
                data: formData,
                async:false,
                /**
                 *必须false才会自动加上正确的Content-Type
                 */
                contentType: false,
                /**
                 * 必须false才会避开jQuery对 formdata 的默认处理
                 * XMLHttpRequest会对 formdata 进行正确的处理
                 */
                processData: false,
                success: function (result) {
                    if (result.retCode == 1) {
                        $('#'+inputId).val(result.data.fileId);
                        $.ajax({
                            url: '/account/realinfo/filePath',
                            data: result.data,
                            type:'GET',
                            async:false,
                            success:function(filePath) {
                                $('#'+imageId).attr('src',filePath);
                                $('#'+imageId).show();
                            }
                        });
                        alert("上传成功！");
                    }else{
                        alert(result.message);
                    }
                },
                error: function () {
                    alert("上传失败！");
                }
            });
        }else{
            alert("上传的文件格式不符，请重新上传！");
        }
    }else{
        alert("未选择上传文件！");
    }
}