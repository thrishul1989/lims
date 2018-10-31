


//导出数据
function downloadData(){
	var sheetId = $("#id").val();
	$.ajax({
		 type:"POST",
		 traditional: true,
		 url:"/testing/downloadLongcre",
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


//导出数据
function downloadOutput(){
	var sheetId = $("#id").val();
	$.ajax({
		 type:"POST",
		 traditional: true,
		 url:"/testing/downloadOutput",
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




//提交实验结果校验
$(function()
{
    $('body').on('click', '#button_submit', function()
    {
    	
        
        if(true){
        	var layerObject = parent.parent.layer;
        	var buttons = [ '确定', '取消' ];
        	
        	layerObject.confirm('确定完成任务，提交任务单吗？', {
        		icon : 3,
        		title : '操作确认',
        		btn : buttons,
        		shade : 'transparent'
        	}, function(index)
        	{
    			$('#submit_form').submit();
    			layerObject.close(index);
        	}, function(index)
        	{
        		layerObject.close(index);
        	});
        }
    });
});
