function clearClass() {
    $('.fileinput-upload-button').remove();
    $('.file-preview').remove();
    $('.fileinput-remove').remove();
}

$(function()
{
	// 不合格详情联动显示
	$('.toggle-qualified').on('ifChecked', function()
	{
		var id = $(this).attr('data-id');
		$('#unqualified_' + id).hide();
	});

	$('.toggle-qualified').on('ifUnchecked', function()
	{
		var id = $(this).attr('data-id');
		$('#unqualified_' + id).show();
	});

	var asUnqualified = function($tr)
	{
		var $toggle = $tr.find(".toggle-qualified");
		var id = $toggle.attr('data-id');
		$toggle.prop("checked", false);
		$toggle.iCheck("update");
		$('#unqualified_' + id).show();
	}

	var asQualified = function($tr)
	{
		var $toggle = $tr.find(".toggle-qualified");
		var id = $toggle.attr('data-id');
		$toggle.prop("checked", true);
		$toggle.iCheck("update");
		$('#unqualified_' + id).hide();
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
		var sampleCodeArr=[];
		$('.sampleCodeClass').each(function(i, v) {
			var scode = $(this).text();
			sampleCodeArr.push(scode);
		});
		$("#sampleCodes").val(sampleCodeArr.join(","));
		$("#uploadForm").submit();
	});

});

//导出数据
function downloadData(){
	var sheetId = $("#id").val();
	$.ajax({
		type:"POST",
		traditional: true,
		url:"/testing/data/downloadMlpaData",
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