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
                url: "/user_operate?type=9&pageIndex=" + pageIndex,
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

        content += "<div class=\"row \" style=\"border-bottom: 1px solid #e9e9e9;margin: 1rem 2rem;\">" +
            "<a class=\"medium-11 columns\" href='/questionsets/'" + result.data.items[i].questionSetId + ">";

        if (result.data.items[i].name == null) {
            content += "<div class=\"medium-8 columns\"><span style=\"font-size: 1.5rem;color: black;\" >题集名称</span>"
        } else {
            content += "<div class=\"medium-8 columns\"><span style=\"font-size: 1.5rem;color: black;\" >" + result.data.items[i].name + "</span></div>"
        }
        if (result.data.items[i].creatorName == null) {
            content += "<div class=\"medium-2 columns\">" +
                "            <span style=\"font-size: 0.75rem; color: #999;\" >创建人</span>" +
                "            </div>";
        } else {
            content += "<div class=\"medium-2 columns\">" +
                "            <span style=\"font-size: 0.75rem; color: #999;\" >" + result.data.items[i].creatorName + "</span>" +
                "            </div>";
        }

        if (result.data.items[i].createDate == null) {
            content += "<div class=\"medium-2 columns\">" +
                "            <span style=\"font-size: 0.75rem; color: #999;\">上架时间</span>" +
                "            </div>";
        } else {
            content += "<div class=\"medium-2 columns\">" +
                "            <span style=\"font-size: 0.75rem; color: #999;\">" + result.data.items[i].createDate + "</span>" +
                "            </div>";
        }

        if (result.data.items[i].descriptions == null) {
            content += "<div class=\"medium-12 columns\" style=\"\">\n" +
                "            <p style=\"color: #999;margin-top:0.5rem;margin-bottom:0rem; padding-bottom:1rem;\" >题集说明</p>\n" +
                "            </div></a>";
        } else {
            content += "<div class=\"medium-12 columns\" style=\"\">\n" +
                "            <p style=\"color: #999;margin-top:0.5rem;margin-bottom:0rem; padding-bottom:1rem;\" >" + result.data.items[i].descriptions + "</p>\n" +
                "            </div></a>";
        }

        content += "<div class=\"medium-1 columns\" style=\"text-align: center;cursor: pointer\">" +
            "                        <i class=\"fa fa-star\" id=\"" + result.data.items[i].questionSetId + "\" onclick=\"unFavorite(event)\"></i>" +
            "                    </div></div></div>";

    }
    $("#user-favorite-question-set").html(content);
}

function unFavorite(event) {
    var operateBo = {};
    var objectId = [];
    objectId.push(event.target.id);
    operateBo.type = 10;
    operateBo.objectId = objectId;
    $.ajax({
        type: "POST",
        url: "/user_operate",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(operateBo),
        success: function () {
            window.location.reload();
        }
    })
}

function favoriteQuestionSet(questionSetId) {
    var operateBo = {};
    var objectId = [];
    objectId.push(questionSetId);
    operateBo.type = 9;
    operateBo.objectId = objectId;
    $.ajax({
        type: "POST",
        url: "/user_operate",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(operateBo),
        success:function () {

        }
    })
}

function unFavoriteQuestionSet(questionSetId) {
    var operateBo = {};
    var objectId = [];
    objectId.push(questionSetId);
    operateBo.type = 10;
    operateBo.objectId = objectId;
    $.ajax({
        type: "POST",
        url: "/user_operate",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(operateBo),
        success:function () {

        }
    })
}


function loading() {
    $("#user-favorite-question-set").loading({
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