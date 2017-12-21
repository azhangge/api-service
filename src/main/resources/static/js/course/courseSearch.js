var parameter = {};
var sortOrder = "desc";
var sortType = "time";

function queryKeyWord() {
    parameter.queryWord = $.trim($('#search-key').val());
    searchCourseList(parameter);
}

//排序方式


$(".course-sort-type-container").click(function () {
    var sortTypeTmp = "";
    if (this.id == "course-sort-time") {
        sortTypeTmp = "time";
    } else if (this.id == "course-sort-count") {
        sortTypeTmp = "count";
    } else {
        sortTypeTmp = "time";
    }

    if (sortTypeTmp == sortType) {
        $("#course-sort-" + sortType + " .course-sort-" + sortOrder).removeClass("course-sort-type-active");
        sortOrder = revertSortOrder(sortOrder);
        $("#course-sort-" + sortType + " .course-sort-" + sortOrder).addClass("course-sort-type-active");
    } else {
        $("#course-sort-" + sortType + " .course-sort-type-title").removeClass("course-sort-type-active");
        $("#course-sort-" + sortType + " .course-sort-" + sortOrder).removeClass("course-sort-type-active");

        sortType = sortTypeTmp;
        sortOrder = "asc";
        $("#course-sort-" + sortType + " .course-sort-type-title").addClass("course-sort-type-active");
        $("#course-sort-" + sortType + " .course-sort-" + sortOrder).addClass("course-sort-type-active");
    }
    if (sortType == "count") {
        parameter.orderKey = 1;
    } else {
        parameter.orderKey = 0;
    }
    if (sortOrder == "asc") {
        parameter.order = 0;
    } else {
        parameter.order = 1;
    }
    searchCourseList(parameter);
});


function revertSortOrder(order) {
    if (order == "asc") {
        return "desc"
    }

    return "asc";
}

function getAllCourse() {
    $("#" + parameter.mainCategoryId).removeClass("course-category-active");
    $("#" + parameter.subCategoryId).removeClass("course-category-active");

    parameter.mainCategoryId = undefined;
    parameter.subCategoryId = undefined;

    $("#category-all").addClass("course-category-active");

    searchCourseList(parameter);
}

function getMainCategoryId(event) {
    var mainCategory = event.target;

    $("#category-all").removeClass("course-category-active");
    $("#" + parameter.mainCategoryId).removeClass("course-category-active");
    $("#" + parameter.subCategoryId).removeClass("course-category-active");

    parameter.mainCategoryId = mainCategory.id;
    parameter.subCategoryId = undefined;

    $("#" + mainCategory.id).addClass("course-category-active");

    searchCourseList(parameter);
}

function getSubCategoryId(event) {
    var subCategory = event.target;

    $("#category-all").removeClass("course-category-active");
    $("#" + parameter.mainCategoryId).removeClass("course-category-active");
    $("#" + parameter.subCategoryId).removeClass("course-category-active");

    parameter.subCategoryId = subCategory.id;
    parameter.mainCategoryId = undefined;

    $("#" + subCategory.id).addClass("course-category-active");

    searchCourseList(parameter);
}

//分类、最新、最热点击
function searchCourseList(parameter) {
    var mainCategoryId = parameter.mainCategoryId;
    var subCategoryId = parameter.subCategoryId;
    var url = "/portal/all_course?pageSize=10";
    if (typeof (parameter.queryWord) != "undefined" && parameter.queryWord.length > 0) {
        url = url + "&queryKeyword=" + parameter.queryWord;
    }
    if (typeof (mainCategoryId) != "undefined") {
        url = url + "&mainCategoryId=" + mainCategoryId;
    }
    if (typeof (subCategoryId) != "undefined") {
        url = url + "&subCategoryId=" + subCategoryId;
    }
    if (typeof (parameter.orderKey) != "undefined") {
        url = url + "&orderKey=" + parameter.orderKey;
    }
    if (typeof (parameter.order) != "undefined") {
        url = url + "&order=" + parameter.order;
    }
    var total = {};
    $.ajax({
        url: url,
        type: "GET",
        beforeSend: function () {
            loading();
        },
        success: function (resultSearchList) {
            total.totalSize = resultSearchList.data.total;
            ajaxHtml(resultSearchList);
            $("#page").paging({
                pageNo: 1,
                totalPage: total.totalSize % 10 == 0 ? total.totalSize / 10 : parseInt(total.totalSize / 10) + 1,
                totalSize: total.totalSize,
                callback: function (pageIndex) {
                    pageIndex = pageIndex - 1;
                    $.ajax({
                        url: url + "&pageIndex=" + pageIndex,
                        type: "GET",
                        beforeSend: function () {
                            loading();
                        },
                        success: function (resultSearchListPage) {
                            ajaxHtml(resultSearchListPage);
                        }
                    });
                }
            })
        }
    });
}


//页码条初始化
function goPage(total) {
    var page = total % 10 == 0 ? total / 10 : parseInt(total / 10) + 1;
    $("#page").paging({
        pageNo: 1,
        totalPage: page,
        totalSize: total,
        callback: function (pageIndex) {
            pageIndex = pageIndex - 1;
            $.ajax({
                url: "/portal/all_course?pageIndex=" + pageIndex,
                type: "GET",
                beforeSend: function () {
                    loading();
                },
                success: function (resultModel) {
                    ajaxHtml(resultModel);
                }
            });
        }
    })
}

function buildCourseList(result) {
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
            content += "<h6 style=\"course-list-item-name\">无名课程</h6>";
        } else {
            content += "<h6 class=\"course-list-item-name\">" + result.data.items[i].courseName + "</h6>";
        }

        content += "<div class=\"course-item-info-row\" style=\"margin-top: 8px\">";
        if (result.data.items[i].teacherName == null) {
            content += "<span>未知讲师</span>"
        } else {
            content += "<span>" + result.data.items[i].teacherName + "</span>";
        }

        if (result.data.items[i].classHour == null) {
            content += "<span class=\"float-right\">-- 课时</span>";
        } else {
            content += "<span class=\"float-right\">" + result.data.items[i].classHour + "课时</span>";
        }
        content += "</div>";
        content += end;
    }
    return content;
}

function ajaxHtml(result) {
    var content = "";

    if (result.data == null || result.data.items == null || result.data.items.length == 0) {
        content = "<div class=\"row small-up-1 medium-up-1 large-up-1 course-list\"\n" +
            "                 style=\"height: 360px;padding-top: 0rem;margin: 0;position: relative;text-align:center;\"\n" +
            "                 \">\n" +
            "                <img src=\"/static/image/no_course.png\"\n" +
            "                     style=\"position:absolute; margin-left:50%; left: -55px; top: 120px; vertical-align: middle;\"/>\n" +
            "                <span style=\"vertical-align: middle;text-align: center;padding-top: 80px;\">没有找到相关课程</span>\n" +
            "            </div>"
    } else {
        content = buildCourseList(result);
    }

    $("#course-list").html(content);
}

function loading() {
    $("#course-list").loading({
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


