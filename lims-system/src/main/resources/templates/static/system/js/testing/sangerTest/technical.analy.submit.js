$(function()
{
    $('.file-preview').hide();
    $('.fileinput-upload').hide();
    $('.fileinput-remove').hide();

    var layerObject = parent.parent.layer;

    $('#button_submit').click(function()
    {
        var qc = $('.record-qualified').length;
        var mc = $('.record-matched').length;

        if (qc != mc)
        {
            var count = qc - mc;
            layerObject.alert(count + "条数据未提交分析结果，请提交结果后确认完成。", {
                title : "提示"
            });
            return false;
        }

        var buttons = [ '确定', '取消' ];

        layerObject.confirm('确定完成任务，提交任务单吗？', {
            icon : 3,
            title : '操作确认',
            btn : buttons,
            shade : 'transparent'
        }, function(index)
        {
            $('iframe#resultFrame')[0].contentWindow.$('#submit_form').submit();
            layerObject.close(index);
        }, function(index)
        {
            layerObject.close(index);
        });
    });
});

function downloadData()
{
    var sheetId = $("input[name='id']").val();
  
    $.ajax({
        type : "POST",
        traditional : true,
        url : "/testSheet/downloadTecAnalysisData",
        data : {
            sheetId : sheetId,
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

function subform()
{
	 dataTemplateId = $(this).attr('data-templateId');
     productName = $(this).attr('data-productName');
     if(dataTemplateId=="")
     {
         if(str.indexOf(productName) < 0)
         {
             str.push(productName);
         }
     }
     
     if ("" == dataTemplateIdType)
     {
         dataTemplateIdType = dataTemplateId;
     }
     else
     {
         if (dataTemplateIdType != dataTemplateId)
         {
             assignableDataTemplate = false;
         }
     }
     
     if(0 < str.length)
     {
         parent.layer.alert(str.join("、")+'产品的数据上传模板未配置，请配置好再下达',{title:"提示"});
         return false;
     }

     if (!assignableDataTemplate)
     {
         parent.layer.alert('数据上传模本不一致，请重新选择',{title:"提示"});
         return false;
     }
     alert(1223)
     //$('#upload_result_form').submit()
}