function submitComment() {
    var comment = {};
    comment.courseId = courseId;
    comment.comment = $("#course-comment-text").val();
    comment.star = $("#poll-star").val();
    if(comment.star == null || comment.star < 1){
        alert("请您为课程打分");return;
    }
    if (comment.comment == null || $.trim(comment.comment).length < 1){
        alert("请输入您的评论");return;
    }
    $.ajax({
        type:"post",
        contentType:"application/json",
        dataType:"json",
        data:JSON.stringify(comment),
        url:"/course/comment",
        success:function (result) {
            if (result.retCode == 1){
                alert("感谢您的评价");
                $.ajax({
                    url: "/course/comment?courseId=" + courseId + "&page=" + 0,
                    type: "GET",
                    success: function (result) {
                        ajaxHtml(result);
                    }
                });
            }else {
                alert(result.message);
            }
        },
    })
}



function favoriteCourse(courseId) {
    var operateBo = {};
    var objectId = [];
    operateBo.type = 1;
    objectId.push(courseId);
    operateBo.objectId = objectId;
    $.ajax({
        type: "POST",
        url: "/user_operate",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(operateBo),
        success: function (result) {
            if(result.retCode == 0){
                alert(result.message);
            } else {

            }
        }
    })
}

function joinCourse() {
    var operateBo = {};
    var objectId = [];
    operateBo.type = 3;
    objectId.push(courseId);
    operateBo.objectId = objectId;
    $.ajax({
        type: "POST",
        url: "/user_operate",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(operateBo),
        async:false,
        success: function (result) {
            if (result.retCode == 1){
                alert("欢迎您学习新课程");
                $("#join_course_id").attr("disabled", true);
            }
            if(result.retCode == 0){
                alert(result.message);
            } else {
                changeClass();
            }
        }
    })
}

function unFavoriteCourse(courseId) {
    var operateBo = {};
    var objectId = [];
    objectId.push(courseId);
    operateBo.type = 4;
    operateBo.objectId = objectId;
    $.ajax({
        type: "POST",
        url: "/user_operate",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(operateBo),
        success: function (result) {
            if(result.retCode==0){
                alert(result.message);
            } else {
            }
        }
    })
}

function pageInit(total, courseId) {
    var page = total % 10 == 0 ? total / 10 : parseInt(total / 10) + 1;
    $("#page").paging({
        pageNo: 1,
        totalPage: page,
        totalSize: total,
        callback: function (pageIndex) {
            pageIndex = pageIndex - 1;
            $.ajax({
                url: "/course/comment?courseId=" + courseId + "&page=" + pageIndex,
                type: "GET",
                beforeSend: function () {

                },
                success: function (result) {
                    ajaxHtml(result);
                }
            });
        }
    })
}

function ajaxHtml(result) {
    var start = "<div style=\"margin-top: 3px;\"><div class=\"row\" style=\"padding: 4px 16px;\">";
    var end = "</div>";
    var content = "";
    for (var i = 0; i < result.data.items.length; i++) {
        content += start;
        if (result.data.items[i].userFilePath != null){
            content += "<img src=\"" + result.data.items[i].userFilePath + "\" style=\"height: 20px;width: 20px\"/>"
        }else {
            content += "<i class=\"fa fa-user-circle\" aria-hidden=\"true\" style=\"height: 20px;width: 20px\"/>"
        }
        for (var j=0;j<result.data.items[i].star;j++) {
            content += "<i  class=\"fa fa-star\" style=\"color: red;\"></i>"
        }

        content += "<span style=\"font-size: 12px;color: blue;float: right\">" + result.data.items[i].createDate + "</span></div>";

        content += "<div class=\"row\" style=\"padding: 4px 16px;\"><span style=\"margin-left: 20px;font-weight: bold\">" + result.data.items[i].comment + "</span></div>";

        content += end;
    }
    $("#course-comment-list").html(content);
}