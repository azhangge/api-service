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
                url: "/enroll/user/list?pageIndex=" + pageIndex,
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
    var start = "<a class=\"row \" style=\"display:block;margin:0.875rem 0;padding: 0 2rem;\"><div class=\"medium-9 columns\"><div class=\"row\">";
    var end = "</a>";
    var content = "";
    for (var i = 0; i < result.data.items.length; i++) {

        content += start;

        if (result.data.items[i].examName != null) {
            content += "<span style=\"font-size: 1.5rem;color: black;margin-right: 2rem;\" href=\"#\">"+ result.data.items[i].examName +"</span>";
        } else {
            content += "<span style=\"font-size: 1.5rem;color: black;margin-right: 2rem;\" href=\"#\">"+ result.data.items[i].examName +"</span>";
        }

        if (result.data.items[i].duration != null) {
            content += "<span style=\"font-size: 0.5rem; color: #999;\">时长:</span><span style=\"font-size: 0.5rem; color: #999;\">"+ result.data.items[i].duration +"</span><span style=\"font-size: 0.5rem; color: #999;\">分钟</span></div>";
        } else {
            content += "<span style=\"font-size: 0.5rem; color: #999;\">时长:</span><span style=\"font-size: 0.5rem; color: #999;\">0</span><span style=\"font-size: 0.5rem; color: #999;\">分钟</span></div>";
        }

        if (result.data.items[i].beginTime != null) {
            content += "<div class=\"row\"><span style=\"font-size: 0.5rem; color: #999;margin-left: 1rem;margin-right: 0.5rem;\">开放时间</span><span style=\"font-size: 0.5rem;\">"+ result.data.items[i].beginTime +"</span>";
        } else {
            content += "<div class=\"row\"><span style=\"font-size: 0.5rem; color: #999;margin-left: 1rem;margin-right: 0.5rem;\">开放时间</span><span style=\"font-size: 0.5rem;\">开始时间</span>";
        }

        if (result.data.items[i].endTime != null) {
            content += "<span style=\"font-size: 0.5rem; color: #999;margin: 0 0.5rem;\">至</span><span style=\"font-size: 0.75rem;\">"+ result.data.items[i].endTime +"</span></div></div>";
        } else {
            content += "<span style=\"font-size: 0.5rem; color: #999;margin: 0 0.5rem;\">至</span><span style=\"font-size: 0.75rem;\">结束时间</span></div></div>";
        }

        if (result.data.items[i].orgName != null){
            content += "<div class=\"medium-2 columns\"><span style=\"font-size: 0.75rem; color: #999;\">"+ result.data.items[i].orgName +"</span></div><div class=\"medium-1 columns\">\n" +
                "       <span style=\"font-size: 0.75rem; color: #999;\">退出报名</span></div>";
        }else {
            content += "<div class=\"medium-2 columns\"><span style=\"font-size: 0.75rem; color: #999;\">组织方</span></div><div class=\"medium-1 columns\">\n" +
                "       <span style=\"font-size: 0.75rem; color: #999;\">退出报名</span></div>";
        }

        content += end;
    }
    $("#exam-plan-list").html(content);
}

function unEnroll(event) {
    var enrollBo = {};
    enrollBo.type = 2;
    enrollBo.examId = event.target.id;
    $.ajax({
        type: "POST",
        url: "/enroll",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(enrollBo),
        success: function () {
            window.location.reload();
        }
    })
}


function loading() {
    $("#exam-plan-list").loading({
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
