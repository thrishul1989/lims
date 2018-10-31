var tipMessage ="确定保存样本结果吗？";
$(function()
{
	$('.toggle-qualified').each(function(){
        if(!$(this).is(':checked')){
        	$('#btn_verify_sample').html('不合格上报');
        	tipMessage = "确定上报不合格样本吗？";
        }
    });
	
    // 不合格详情联动显示
    $('.toggle-qualified').on('ifChecked', function()
    {
        var id = $(this).attr('data-id');
        var number = $(this).attr('data-number');
        $('#unqualified_' + id+'_'+number).hide();
        var flag = true;
        $('.toggle-qualified').each(function(){
            if(!$(this).is(':checked')){
                flag = false;
            }
        });
        if(flag)
        {
        	$('#btn_verify_sample').html('保存样本结果');
        	tipMessage ="确定保存样本结果吗？";
        }
    });

    $('.toggle-qualified').on('ifUnchecked', function()
    {
        var id = $(this).attr('data-id');
        var number = $(this).attr('data-number');
        $('#unqualified_' + id+'_'+number).show();
        $('#btn_verify_sample').html('不合格上报');
        tipMessage = "确定上报不合格样本吗？";
    });

    var asUnqualified = function($tr)
    {
        var $toggle = $tr.find(".toggle-qualified");
        var id = $toggle.attr('data-id');
        var number = $toggle.attr('data-number');
        $toggle.prop("checked", false);
        $toggle.iCheck("update");
        $('#unqualified_' + id+'_'+number).show();
    }

    var asQualified = function($tr)
    {
        var $toggle = $tr.find(".toggle-qualified");
        var id = $toggle.attr('data-id');
        var number = $toggle.attr('data-number');
        $toggle.prop("checked", true);
        $toggle.iCheck("update");
        $('#unqualified_' + id+'_'+number).hide();
    }

    
    $('body').on('click', '#btn_verify_sample', function()
    {
        var layerObject = parent.parent.layer;
        var buttons = [ '确定', '取消' ];

        layerObject.confirm(tipMessage, {
            icon : 3,
            title : '操作确认',
            btn : buttons,
            shade : 'transparent'
        }, function(index)
        {
        	var flag = true;
        	$('.son').each(function()
        	{
        		var display = $(this).css('display');
        		if(display != 'none')
        		{
        			var value = $(this).find(".checknull").val();
        			if(null==value || ""==value ||"null" == value)
        			{
        				flag = false;
        			}
        		}
        	});
        	if(flag)
        	{
        	 	$.ajax({
                    type: "POST",
                    dataType: "html",
                    url: "/bpm/report/handle/save_verify_sample.do",
                    data: $('#verify_sample_form').serialize(),
                    success: function (result) {
                    	var result = eval(result);
                    	if(null != result && result.length > 0)
                    	{
                    		for(var i = 0;i<result.length;i++)
                    		{
                    			var rmap = result[i];
                    			for ( var key in rmap )
                    			{
                    				$('.toggle-qualified').each(function(){
                                		if(key == $(this).attr('data-id') && rmap[key] == $(this).attr('data-chrLocation'))
                                		{
                                			$(this).parents('.checkbox').hide();
                                		}
                        	        });
                    				$('#unqualified_'+key).find("input[class='form-control checknull']").attr('readonly',true);
                    			}
                    		}
                    	}
                    	
                    	parent.document.getElementById("iframe_sample").value = "true";
                    	parent.document.getElementById("iframe_result").value = "true";
                    },
                    error: function(data) {
                        alert("error");
                    }
            	});
        	}
        	else
        	{
        		layerObject.alert("请填写备注原因!", {title : "提示"});
        	}
            layerObject.close(index);
        }, function(index)
        {
            layerObject.close(index);
        });
    }).on('click', '.combineTypeText', function(){
    	var combineType = $(this).val();
    	var $tr = $(this).closest('tr');
    	$tr.find('.combineType').val(combineType);
    }).on('click', '.colcerTr', function(){
    	$('.colcerTr').each(function(){
    		$(this).css('background-color', '');
    	});
    	$(this).css('background-color', '#3EBBB3');
    });
    
});
