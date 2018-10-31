
function clearClass() {
    $('.fileinput-upload-button').remove();
    $('.file-preview').remove();
    $('.fileinput-remove').remove();
}

//导出数据
function downloadData(){
	var sheetId = $("#sheetId").val();
	$.ajax({
		 type:"POST",
		 traditional: true,
		 url:"/testing/downloadLongpcr",
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
	    url: '/testing/uploadLongpcr',
	    type: 'POST',
	    cache: false,
	    data: new FormData($('#uploadForm')[0]),
	    processData: false,
	    contentType: false
	}).done(function(list) {
		 if(list==''){
			  parent.layer.alert('Excel页面没有数据！',{title:"提示"});
		  }else{

			  $('#uploadDataTable tbody').empty();
	        
	          $(list).each(function(i, data) {
	        	
	        	$($('td[name="pcrCode"]')).each(function(ii,v){
	        		if($(v).text() == data.pcrCode){
	        			$('#uploadDataTable tbody').append('<tr>'+
	        				'<input type="hidden" name="longpcrSubmitrequest['+i+'].testingTask.id" value="'+$(v).parent().find("input[name='taskId']").val()+'" />'+
	        				'<input type="hidden" name="longpcrSubmitrequest['+i+'].sheetId" value="'+$("#sheetId").val()+'" />'+
	        				'<input type="hidden" name="longpcrSubmitrequest['+i+'].A260230" value="'+data.a260230+'" />'+
	        				'<input type="hidden" name="longpcrSubmitrequest['+i+'].A260280" value="'+data.a260280+'" />'+
	        				'<input type="hidden" name="longpcrSubmitrequest['+i+'].concn" value="'+data.concn+'" />'+
	        				'<input type="hidden" name="longpcrSubmitrequest['+i+'].pcrCode" value="'+data.pcrCode+'" />'+
	        				'<input type="hidden" name="longpcrSubmitrequest['+i+'].volumn" value="'+data.volumn+'" />'+
	        				'<td>'+(i+1)+'</td>'+
	        				'<td>'+data.pcrCode+'</td>'+
	        				'<td>'+data.concn+'</td>'+
	        				'<td>'+data.volumn+'</td>'+
	        				'<td>'+data.a260280+'</td>'+
	        				'<td>'+data.a260230+'</td>'+
	        				'</tr>'
	        			)
	        			return;
	        		}
	        	})
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
