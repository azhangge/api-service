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
                url: "/user/exam/list?pageSize=10&pageIndex=" + pageIndex,
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
    var start = "<a class=\"row \" style=\"display:block;margin-top:0;margin-bottom:0.875rem;margin-left: 1.5rem;\">";
    var end = "</a>";
    var content = "";
    for (var i = 0; i < result.data.items.length; i++) {

        content += start;

        if (result.data.items[i].name != null) {
            content += "<div class=\"medium-10 columns\"><div class=\"row\"><span style=\"font-size: 1.5rem;color: black;\" href=\"#\">" + result.data.items[i].name + "</span></div>";
        } else {
            content += "<div class=\"medium-10 columns\"><div class=\"row\"><span style=\"font-size: 1.5rem;color: black;\" href=\"#\">考试名称</span></div>";
        }

        if (result.data.items[i].userScore != null) {
            content += "<div class=\"row\" style=\"padding-left: 1rem;\">分数：<span style=\"font-size: 0.75rem; color: #999;margin-right: 2rem;\">" + result.data.items[i].userScore*1.0 + "</span>";
        } else {
            content += "<div class=\"row\" style=\"padding-left: 1rem;\">分数：<span style=\"font-size: 0.75rem; color: #999;margin-right: 2rem;\">分数</span>";
        }

        if (result.data.items[i].questionNum != null) {
            content += "题数：<span style=\"font-size: 0.75rem; color: #999;margin-right: 2rem;\">" + result.data.items[i].questionNum + "</span>";
        } else {
            content += "题数：<span style=\"font-size: 0.75rem; color: #999;margin-right: 2rem;\">题数</span>";
        }

        if (result.data.items[i].rightNum != null) {
            content += "答对：<span style=\"font-size: 0.75rem; color: #999;\">" + result.data.items[i].rightNum + "</span></div></div>";
        } else {
            content += "答对：<span style=\"font-size: 0.75rem; color: #999;\">答对</span></div></div>";
        }

        if (result.data.items[i].submitTime != null) {
            content += "<div class=\"medium-2 columns\"><span style=\"font-size: 0.75rem; color: #999;\">" + result.data.items[i].submitTime + "</span></div>";
        } else {
            content += "<div class=\"medium-2 columns\"><span style=\"font-size: 0.75rem; color: #999;\"></span></div>";

            content += end;
        }
        $("#test-answer-content-list").html(content);
    }
}


function loading() {
    $("#test-answer-content-list").loading({
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