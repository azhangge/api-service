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
                url: "/user/test/list?pageSize=10&pageIndex=" + pageIndex,
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

        if (result.data.items[i].name == null) {
            content += "<a class=\"row\"><div class=\"medium-8 columns\"><span style=\"font-size: 1.5rem;color: black;\" href=\"#\">题集名称</span></div>"
        } else {
            content += "<a class=\"row\"><div class=\"medium-8 columns\"><span style=\"font-size: 1.5rem;color: black;\" href=\"#\">" + result.data.items[i].name + "</span></div>"
        }

        if (result.data.items[i].questionNum == null) {
            content += "题数：<span style=\"font-size: 0.75rem; color: #999;\">0</span>"
        } else {
            content += "题数：<span style=\"font-size: 0.75rem; color: #999;\">" + result.data.items[i].questionNum + "</span>"
        }

        if (result.data.items[i].questionNum == null) {
            content += " 答对：<span style=\"font-size: 0.75rem; color: #999;\">0</span></a>"
        } else {
            content += " 答对：<span style=\"font-size: 0.75rem; color: #999;\">" + result.data.items[i].rightNum + "</span></a>"
        }

    }
    $("#test-answer-content-list").html(content);
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
