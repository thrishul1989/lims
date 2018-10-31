$(function()
{
    $("#submit_form").validate({
        submitHandler : function(form)
        {
            var layerObject = parent.parent.layer;
            var buttons = [ '确定', '取消' ];

            var flag = true;
        	$('.unqualifiedTr').each(function()
        	{
        		var display = $(this).css('display');
        		if(display != 'none')
        		{
        			var value = $(this).find(".checknull").val();
        			if(null==value || ""==value ||"null" == value)
        			{
        				layerObject.alert("请填写备注原因!", {title : "提示"});
        				flag = false;
        			}
        		}
        	});
        	if(!flag)
        	{
        		return false;
        	}
        	
            layerObject.confirm('确定完成校验，提交数据校验结果吗？', {
                icon : 3,
                title : '操作确认',
                btn : buttons,
                shade : 'transparent'
            }, function(index)
            {

                form.submit();
                layerObject.close(index);
            }, function(index)
            {
                layerObject.close(index);
            });
        }
    	
    });

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
