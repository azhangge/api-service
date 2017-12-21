var parameter = {};
parameter.sortOrder = "asc";

function queryKeyWord() {
    parameter.queryWord = $.trim($('#search-key').val());
    searchCourseList(parameter);
}

function allType() {
    $("#test-type").removeClass("question-set-type-active");
    $("#paper-type").removeClass("question-set-type-active");

    parameter.type = undefined;
    searchCourseList(parameter);

    $("#all-type").addClass("question-set-type-active");
}

function testType() {
    $("#paper-type").removeClass("question-set-type-active");
    $("#all-type").removeClass("question-set-type-active");

    parameter.type = 1;
    searchCourseList(parameter);

    $("#test-type").addClass("question-set-type-active");
}

function paperType() {
    $("#test-type").removeClass("question-set-type-active");
    $("#all-type").removeClass("question-set-type-active");


    parameter.type = 2;
    searchCourseList(parameter);

    $("#paper-type").addClass("question-set-type-active");
}

function sortByOrder() {

    if (parameter.sortOrder == "asc") {
        parameter.sortOrderType = 0;
    } else {
        parameter.sortOrderType = 1;
    }

    $("#sort-time" + " .course-sort-" + parameter.sortOrder).addClass("question-set-type-active");
    parameter.sortOrder = revertSortOrder();
    $("#sort-time" + " .course-sort-" + parameter.sortOrder).removeClass("question-set-type-active");

    searchCourseList(parameter);
}

function searchCourseList(parameter) {
    var url = "/test_paper/list?pageSize=10";
    if (typeof (parameter.queryWord) != "undefined" && parameter.queryWord.length > 0) {
        url = url + "&search=" + parameter.queryWord;
    }
    if (typeof (parameter.type) != "undefined") {
        url = url + "&type=" + parameter.type;
    }
    if (typeof (parameter.sortOrderType) != "undefined" && parameter.sortOrderType == 0) {
        url = url + "&order=0";
    } else {
        url = url + "&order=1";
    }
    var totalSize = 0;

    $.ajax({
        type: "GET",
        url: url,
        beforeSend: function () {
            loading();
        },
        success: function (result) {
            totalSize = result.data.total;
            ajaxHtml(result);
            $("#page").paging({
                pageNo: 1,
                totalPage: totalSize % 10 == 0 ? totalSize / 10 : parseInt(totalSize / 10) + 1,
                totalSize: totalSize,
                callback: function (pageIndex) {
                    pageIndex = pageIndex - 1;
                    $.ajax({
                        url: url + "&pageIndex=" + pageIndex,
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
    });
}

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
                url: "/test_paper/list?pageSize=10&pageIndex=" + pageIndex,
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

    if (result.data == null || result.data.items == null || result.data.items.length == 0) {
        content = "<div style='height: 240px; line-height: 240px;text-align: center;'><h5>没有找到相关题集</h5></div>"
    } else {
        content = buildCourseList(result);
    }

    $("#question-content-list").html(content);
}

function buildCourseList(result) {
    var content = "";
    for (var i = 0; i < result.data.items.length; i++) {
        content += "<a class=\"row \" style=\"display: block;padding: 0.5rem 1rem;\" href=\"/questionsets/" + result.data.items[i].questionSetId + "\">";
        if (result.data.items[i].name == null) {
            content += "<div class=\"medium-8 columns\">\n" +
                "            <span style=\"font-size: 1.125rem;color: black;\" >题集名称</span>"
        } else {
            content += "<div class=\"medium-6 columns\">\n" +
                "            <span style=\"font-size: 1.125rem;color: black;\" >" + result.data.items[i].name + "</span>\n" +
                "            </div>"
        }
        if (result.data.items[i].type == 1) {
            content += "<div class=\"medium-2 columns text-center\">\n" +
                "            <span style=\"font-size: 0.75rem; color: #999;\" >题集</span>\n" +
                "            </div>";
        } else {
            content += "<div class=\"medium-2 columns text-center\">\n" +
                "            <span style=\"font-size: 0.75rem; color: #999;\" >套卷</span>\n" +
                "            </div>";
        }
        if (result.data.items[i].creatorName == null) {
            content += "<div class=\"medium-2 columns text-center\">\n" +
                "            <span style=\"font-size: 0.75rem; color: #999;\" >创建人</span>\n" +
                "            </div>";
        } else {
            content += "<div class=\"medium-2 columns text-center\">\n" +
                "            <span style=\"font-size: 0.75rem; color: #999;\" >" + result.data.items[i].creatorName + "</span>\n" +
                "            </div>";
        }

        if (result.data.items[i].createDate == null) {
            content += "<div class=\"medium-2 columns text-center\">\n" +
                "            <span style=\"font-size: 0.75rem; color: #999;\">上架时间</span>\n" +
                "            </div>";
        } else {
            content += "<div class=\"medium-2 columns text-center\">\n" +
                "            <span style=\"font-size: 0.75rem; color: #999;\">" + result.data.items[i].createDate + "</span>\n" +
                "            </div>";
        }


        if (result.data.items[i].descriptions == null) {
            content += "<div class=\"medium-12 columns\" style=\"\">\n" +
                "            <p style=\"font-size: 0.875rem;color: #999;margin-top:0.5rem;margin-bottom:0rem; padding-bottom:0.5rem; border-bottom: 1px solid #e9e9e9;\" >无</p>\n" +
                "            </div>";
        } else {
            content += "<div class=\"medium-12 columns\" style=\"\">\n" +
                "            <p style=\"font-size: 0.875rem;color: #999;margin-top:0.5rem;margin-bottom:0rem; padding-bottom:0.5rem; border-bottom: 1px solid #e9e9e9;\" >" + result.data.items[i].descriptions + "</p>\n" +
                "            </div>";
        }

        content += "</a>";

    }
    return content;
}

function revertSortOrder() {
    if (parameter.sortOrder == "asc") {
        return "desc"
    }

    return "asc";
}


function loading() {
    $("#question-content-list").loading({
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
