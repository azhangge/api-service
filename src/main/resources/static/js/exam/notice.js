//初始化分页条
function pageInit(total) {
    var page = total % 10 == 0 ? total / 10 : parseInt(total / 10) + 1;
    $("#page").paging({
        pageNo: 1,
        totalPage: page,
        totalSize: total,
        callback: function (pageIndex) {
            pageIndex = pageIndex - 1;
            $.ajax({
                url: "/notice/exam/user?pageSize=10&pageIndex=" + pageIndex,
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
    var content = "";
    for (var i = 0; i < result.data.items.length; i++) {

        if (result.data.items[i].examId == null) {
            content += "<div class=\"column course-item-container\"><a href=\"#\">";
        } else {
            content += "<div class=\"column course-item-container\"><a href=\"/notice/exam/detail/" + result.data.items[i].examId + ">";
        }

        if (result.data.items[i].noticeName == null) {
            content += "<span style=\"padding-left: 0.825rem;\">通知名称</span>";
        } else {
            content += "<span style=\"padding-left: 0.825rem;\">"+ result.data.items[i].noticeName +"</span>";
        }

        if (result.data.items[i].publishTime == null) {
            content += "<span style=\"float: right\">发布时间</span></a></div>";
        } else {
            content += "<span style=\"float: right\">"+ result.data.items[i].publishTime +"</span></a></div>";
        }

    }
    $("#notice-list").html(content);
}

function loading() {
    $("#notice-list").loading({
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