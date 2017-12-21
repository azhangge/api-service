var enrollBo = {};
enrollBo.type = 1;
function enrollInit(enrollParameter) {
    enrollBo.examId = enrollParameter.examId;
    var now = new Date().getTime();

    if (enrollParameter.userEnrollStatus == 1) {
        $('#user-enroll').html(contentEnrolled);
    } else if (enrollParameter.userEnrollStatus == 0 && now > Date.parse(new Date(enrollParameter.enrollBeginTime))
        && Date.parse(new Date(enrollParameter.enrollEndTime)) > now) {
        $('#user-enroll').html(contentEnroll);
    }else {
        $('#user-enroll').html(contentEnrollNon);
    }
}

function enroll() {
    $.ajax({
        type:"POST",
        url:"/enroll",
        contentType:"application/json",
        dataType:"json",
        data:JSON.stringify(enrollBo),
        success:function () {
            window.location.reload();
        }
    })
}

var contentEnrolled = "<div><input style=\"height: 50px;width: 200px\" type=\"button\" value=\"已报名\" disabled = \"disabled\" /></div>";
var contentEnroll = "<div><input style=\"height: 50px;width: 200px\" type=\"button\" value=\"立即报名\" onclick=\"enroll()\"/></div>";
var contentEnrollNon = "<div><input style=\"height: 50px;width: 200px\" type=\"button\" value=\"立即报名\" disabled = \"disabled\" /></div>";
