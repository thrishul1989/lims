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
		 url:"/testing/data/downloadPcrNgsDataData",
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
$("#butt").click(function () {
	
	var count = $('.flag').size();
	var indexs = []
	var dataTemplateId;
	var dataTemplateIdType = "";
	var assignableDataTemplate = true;
	$('.flag').each(function(i,v){
		
		dataTemplateId = $(v).attr("data-templateId");
		if(null == dataTemplateId||"" == dataTemplateId)
		{
			indexs.push($(v).text());
		}
		if ("" == dataTemplateIdType) {
			dataTemplateIdType = dataTemplateId;
		} else {
			if (dataTemplateIdType != dataTemplateId) {
				assignableDataTemplate = false;
			}
		}
	});
	if(indexs.length > 0)
	{
		parent.layer.alert('序号为：'+indexs.join(',')+'  数据上传模板未配置',{title:"提示"});
		return false;
	}

	if (!assignableDataTemplate) {
		parent.layer.alert('数据上传模本不一致，请重新选择', {
			title : "提示"
		});
		return false;
	}
	
	var excelFileName = $('#uploadData').val();
	var formatStr = '';
	var index = excelFileName.lastIndexOf('.');
	if (excelFileName.length == 0) {
		parent.layer.alert('请选择需要上传的文件', {title: "提示"});
		return;
	} else if (index > 0) {
		formatStr = excelFileName.substring(index);
		if (!(".zip" == formatStr)) {
			parent.layer.alert('请上传zip文件', {title: "提示"});
			return;
		}
	}
	$("#uploadForm").submit();
});

//提交实验结果校验
$(function()
{
	var n = 0;
    $('body').on('click', '#button_submit', function()
    {
    	var flag=true;
		$("#resultFrame").contents().find('#uploadDataTable tbody tr').each(function(i, v){
			n++;
        	var reason = $(this).find(".abnormalReason").val();
            if(""!=reason){
            	parent.layer.alert('请先处理异常记录',{title:"提示"});
                flag=false;
            }
        });
		if(n==0)
		{
			parent.layer.alert('未上传结果数据或上传数据与任务数量不匹配',{title:"提示"});
			flag=false;
		}
        
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
