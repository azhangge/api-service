function clearInput(){
	var v = $("#search-input")[0].value;
	if(v=="课程搜索"){
		$("#search-input")[0].value = "";
	}
}
function searchBOxOnBlur(){
	var v = $("#search-input")[0].value;
	if(v==""){
		$("#search-input")[0].value = "课程搜索"
	}
}

function findLessonsByName(){
	var name = $("#search-input")[0].value;
	if (name == '课程搜索') {
		name='';
	}
	location.href="/#";
}
function keyLogin() {
	//回车键的键值为13
	if (event.keyCode == 13){
		document.getElementById("searchLesson").click(); //调用搜索按钮
	}
}


