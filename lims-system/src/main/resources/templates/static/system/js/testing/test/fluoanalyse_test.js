$(function()
{
    // 文件上传样式重置
    $('.file-preview').hide();
    $('.fileinput-upload').hide();
    $('.fileinput-remove').hide();

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
    
    $('body').on('click', '#button_submit', function() {

        var flag=true;
        var importSample=[];
        var needImportSampleCode=[];
        var reason = "";
        var allSampleCode=[];
        var existSampleCode=[];
        $("#resultFrame").contents().find('#uploadDataTable tbody tr').each(function(i, v){
            var samleCode = $(this).find(".importSampleCode").val();
            if(importSample.indexOf(samleCode)<0)
            {
                importSample.push(samleCode);
            }
        });
        // 循环出所有的样本编号
        $('#dataTable tbody tr').each(function(i, v){
            if($(this).find('.checkSuc').is(':checked'))
            {
                var sampleCode = $(this).find(".sampleCodeClass").text();
                allSampleCode.push(sampleCode);
                needImportSampleCode.push(sampleCode);
            }

        });
        $("#resultFrame").contents().find('.importSampleCode').each(function(i, v) {
            var sampleCode = $(this).val();
            if(allSampleCode.indexOf(sampleCode)<0 && existSampleCode.indexOf(sampleCode)<0)
            {
            }else{
                existSampleCode.push(sampleCode);
                allSampleCode.remove(sampleCode);
            }
        });

        if(!flag){
            return;
        }

        if(allSampleCode.length>0)
        {
            parent.layer.alert('样本编号为：'+allSampleCode+"数据未上传",{title:"提示"});
            flag = false;
        }

        if(!flag){
            return;
        }

        if(needImportSampleCode.length > 0)
        {
            for(var i=0;i<needImportSampleCode.length;i++)
            {
                var scode = needImportSampleCode[i];
                if(importSample.indexOf(scode)<0)
                {
                    //上传的结果不全，scode结果没传
                    parent.layer.alert(scode+'样本未上传结果',{title:"提示"});
                    flag=false;
                    return false;
                }

            }

        }
        if(!flag){
            return;
        }

    	$('.son').each(function()
    	{
    		var display = $(this).css('display');
    		if(display != 'none')
    		{
    			var value = $(this).find(".checknull").val();
    			if(null==value || ""==value ||"null" == value)
    			{
    				parent.layer.alert("请填写备注原因!", {title : "提示"});
    				flag = false;
    			}
    		}
    	});
    	
        if(flag)
        {
            var layerObject = parent.parent.layer;
            var buttons = [ '确定', '取消' ];

            layerObject.confirm('确定完成任务，提交任务单吗？', {
                icon : 3,
                title : '操作确认',
                btn : buttons,
                shade : 'transparent'
            }, function(index)
            {
                var flag = true;
                var checknull = $(".checknull:visible");
                $.each(checknull, function(index, check)
                {//判断必填
                    if ($(check).val() == null || $(check).val() == "")
                    {
                        parent.layer.alert('请输入必填项', {
                            title : "提示"
                        });
                        $(check).addClass('addEroBorder');
                        flag = false;
                        return false;
                    }
                    else
                    {
                        if ($(check).hasClass('addEroBorder'))
                        {
                            $(check).removeClass('addEroBorder');
                        }
                    }
                })
                if (flag)
                {
                    $('#submit_form').submit();
                    layerObject.close(index);
                }
            }, function(index)
            {
                layerObject.close(index);
            });

        }
    });
});

function downloadData()
{
    var sheetId = $("#id").val();
    $.ajax({
        type : "POST",
        traditional : true,
        url : "/testing/fluo/downloadFluotestData",
        data : {
            sheetId : sheetId
        },
        async : false,
        success : function(data)
        {
            $("#formValue").val(data);
            $("#hiddForm").submit();
        },
        error : function()
        {
            alert("failed");
        }
    });
}