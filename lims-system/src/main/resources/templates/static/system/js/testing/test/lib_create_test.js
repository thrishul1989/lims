//导出样本编号
function downloadCode() {
    var sheetId = $("#id").val();
    $.ajax({
        type : "POST",
        traditional: true,
        url : "/testing/downloadCode/library",
        data : {
            id : sheetId
        },
        async : false,
        success : function(data) {
            $("#formValue").val(data);
            $("#hiddForm").submit();
        },
        error : function() {
            alert("failed");
        }
    });
}

//导出数据
function downloadData(){
	var sheetId = $("#id").val();
	$.ajax({
		 type:"POST",
		 traditional: true,
		 url:"/testing/downloadLibCre",
		 data:{sheetId : sheetId},
		 async: false,
		 success:function(data){
			$("#formValue").val(data);
			$("#hiddForm").submit();
	    },
		 error:function(){
			alert("failed");
		 }
	}); 
}