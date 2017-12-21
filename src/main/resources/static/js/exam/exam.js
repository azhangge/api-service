var userSubmitAnswer = {}
var answers = new Array();
var answersMap = {};
var questionIdsMap = {};
function getQuestionSet(object) {
    userSubmitAnswer.questionSetId = object.questionSetId;
    userSubmitAnswer.questionSetInsId = object.questionSetInsId;
    userSubmitAnswer.type = 2;

    for (var i = 0; i < object.questions.length; i++) {
        questionIdsMap[object.questions[i].questionId] = object.questions[i].type;
        var answerTemp = {};
        answerTemp.questionId = object.questions[i].questionId;
        answerTemp.userAnswer = "";
        answersMap[object.questions[i].questionId] = answerTemp;
    }
}

function getChoice() {
    for (var key in questionIdsMap){
        if (questionIdsMap[key] == 1 || questionIdsMap[key] == 3){
            var choiceValue = $("input:radio[name=" + key + "]:checked").val();
            if (typeof (choiceValue) != "undefined") {
                var answer = answersMap[key];
                answer.userAnswer = choiceValue;
                answersMap[key] = answer;
            }
        }
        if (questionIdsMap[key] == 2){
            var choiceValue = "";
            $("input:checkbox[name=" + key + "]:checked").each(function(){
                choiceValue += $(this).val();
                if (typeof (choiceValue) != "undefined") {
                    var answer = answersMap[key];
                    answer.userAnswer = choiceValue;
                    answersMap[key] = answer;
                }
            })
        }
    }
}

function userAnswerConvert() {
    for (var key in answersMap) {
        answers.push(answersMap[key]);
    }
}

function submit() {
    getChoice();
    userAnswerConvert();
    userSubmitAnswer.answers = answers;
    $.ajax({
        url: "/test_exam/submit",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(userSubmitAnswer),
        contentType: "application/json",
        success: function (result) {
            if (result.retCode == 1) {
                alert("提交成功");
                window.location.href = '/user/exam/score/list';
            } else {
                alert("提交失败");
            }
        }
    })
}