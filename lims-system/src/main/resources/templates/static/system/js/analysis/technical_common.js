

function unqualifiedReport(analysisSampleId) {
	if(checkTaskIsFinished(analysisSampleId)){
		$('#report_dialog').modal('show');
	}else{
		parent.parent.layer.alert("还有任务正在执行,稍后提交", {title: "提示"});
	}
}

function checkTaskIsFinished(analysisSampleId){
	var flag = false;

		$.ajax({
			url:PATH+"/testing/checkTechnicalAnalysisIsFinished.do",
			data:"analysisSampleId="+analysisSampleId,
			async:false,
			success:function(data){
				console.info(data);
				if(data){
					flag = true;
				}else{
					flag = false;
				}
			},
			error:function(data){
				showTip("请稍后再试","系统错误");
				flag = false;
			}
		});
	
	return flag;

}

function recheck(){
	$("#note").text('');
	$("#recheckModal").modal('show');
}



