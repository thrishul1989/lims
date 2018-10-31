function clearClass() {
    $('.fileinput-upload-button').remove();
    $('.file-preview').remove();
    $('.fileinput-remove').remove();
}

//导出数据
function downloadData(){
	var sheetId = $("#id").val();
	$.ajax({
		 type:"POST",
		 traditional: true,
		 url:"/testSheet/downloadSecondPCRData",
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

//导入解析excel
$("#butt").click(function(){
    var excelFileName = $('#uploadData').val();
	var formatStr = '';
	var index = excelFileName.lastIndexOf('.');
	if(excelFileName.length == 0){
		parent.layer.alert('请选择需要上传的文件',{title:"提示"});
		return ;
	}else if(index > 0){
		formatStr = excelFileName.substring(index);
		if(!(".xlsx" == formatStr||".xls" == formatStr)){
			parent.layer.alert('请上传excel文件',{title:"提示"});
			return ;
		}
	}
	   
  $.ajax({
	    url: '/testSheet/uploadFirstPCR',
	    type: 'POST',
	    cache: false,
	    data: new FormData($('#uploadForm')[0]),
	    processData: false,
	    contentType: false
	}).done(function(list) {
		 if(list==''){
			  parent.layer.alert('Excel页面没有数据！',{title:"提示"});
		  }else{
          
	          $('#uploadDataTable tbody tr').each(function(i, v) {
	        	  $(this).show();
	        	  $(this).find(".resultInput").val("");
	        	  $(this).find(".disposeInput").val("");
	        	  $(this).find(".remarkInput").val("");
	        	  
	        	  var combineCode = $(this).find(".combineCode").val();
	        	  for(var m = 0; m < list.length; m++){
	        		  if(combineCode == list[m][0]){
	        			  
	        			  $(this).find(".result").text(list[m][1]);
		            	  $(this).find(".dispose").text(list[m][2]);
		            	  $(this).find(".remark").text(list[m][3]);
	        			  
	        			  if("成功" == list[m][1]){
		            		  $(this).find(".resultInput").val("0");
		            	  }else{
		            		  $(this).find(".resultInput").val("1");
		            	  }
		            	  $(this).find(".disposeInput").val(list[m][2]);
		            	  $(this).find(".remarkInput").val(list[m][3]);
	        		  }
	        	  }
	           });
	          
	          $('#uploadDataTable tbody tr').each(function(i, v) {
	        	  var result = $(this).find(".resultInput").val();
            	  var dispose = $(this).find(".disposeInput").val();
            	  var remark = $(this).find(".remarkInput").val();
            	  if(""==result && ""==dispose && ""==remark){
            		  $(this).css("display","none"); 
                  }
	          });
	          
	          $('#uploadTableName').show();
	          $('#uploadTable').show();
		  }
		 $('#myModal').modal('hide');
	}).fail(function(res) {});
});

//提交实验结果校验
$(function()
{
    $('body').on('click', '#button_submit', function()
    {
    	var flag=true;
        $('#uploadDataTable tbody tr').each(function(i, v){
        	var result = $(this).find(".resultInput").val();
      	    var dispose = $(this).find(".disposeInput").val();
      	    var remark = $(this).find(".remarkInput").val();
            if(""==result && ""==dispose && ""==remark){
            	parent.layer.alert('未上传结果数据或上传数据与任务数量不匹配',{title:"提示"});
                flag=false;
            }
        });
        
        if(flag){
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
