//页码条初始化
function pageInit(total) {
    var page = total % 10 == 0 ? total / 10 : parseInt(total / 10) + 1;
    $("#page").paging({
        pageNo: 1,
        totalPage: page,
        totalSize: total,
        callback: function (pageIndex) {
            pageIndex = pageIndex - 1;
            $.ajax({
                url: "/user_operate?type=1&pageIndex=" + pageIndex,
                type: "GET",
                beforeSend: function () {
                    loading();
                },
                success: function (result) {
                    ajaxHtml(result);
                }
            });
        }
    })
}


function ajaxHtml(result) {
    var start = "<div class=\"column course-item-container\">";
    var end = "</a></div>";
    var content = "";
    for (var i = 0; i < result.data.items.length; i++) {
        content += start;
        content += "<a href=\"/course/" + result.data.items[i].courseId + " \" target=\"_blank\">";
        if (result.data.items[i].thumbnailPath == null) {
            content += "<img class=\"thumbnail course-item-thumbnail\" src=\"/static/image/default_course_thumbnail.png\"/>"
        } else {
            content += "<img class=\"thumbnail course-item-thumbnail\" src=\"" + result.data.items[i].thumbnailPath + "\"/>";
        }

        if (result.data.items[i].courseName == null) {
            content += "<h6 style=\"padding-left: 0.825rem;\"></h6>";
        } else {
            content += "<h6 style=\"padding-left: 0.825rem;\">" + result.data.items[i].courseName + "</h6>";
        }

        content += "<div class=\"course-item-info-row\">";
        if (result.data.items[i].teacherName == null) {
            content += "<span></span>"
        } else {
            content += "<span>" + result.data.items[i].teacherName + "</span>";
        }

        if (result.data.items[i].courseSecond == null) {
            content += "<span class=\"float-right\"></span>";
        } else {
            content += "<span class=\"float-right\">" + result.data.items[i].courseSecond + "课时</span>";
        }
        content += "</div>";
        content += end;
    }
    $("#user-favorite-course").html(content);
}


function loading() {
    $("#user-favorite-course").loading({
        loadingWidth:240,
        selector:"course-list",
        name:'test',
        direction:'column',
        type:'origin',
        originDivWidth:40,
        originDivHeight:40,
        originWidth:6,
        originHeight:6,
        smallLoading:false,
        loadingMaskBg:'rgba(0,0,0,0.2)'
    });
}
