$(function()
{
    $('body').on('click', '.check-controller', function()
    {
        if ($(this).is(":checked"))
        {
            $(".check-instance").prop("checked", true);
        }
        else
        {
            $(".check-instance").prop("checked", false);
        }
    }).on('click', '.check-instance', function()
    {
        if (!$(this).is(":checked"))
        {
            $(".check-controller").prop("checked", false);
        }
        else
        {
            var totalCount = $(".check-instance").size();// 选项总个数
            var checkedCount = $('input[type=checkbox]:checked').length;// 选中总数
            if (totalCount == checkedCount)
            {
                $(".check-controller").prop("checked", true);
            }
            else
            {
                $(".check-controller").prop("checked", false);
            }
        }

    }).on('click', '#btn_download', function(e) {
        e.preventDefault();
        var instances = $('.check-instance:checked');
        var count = instances.length;
        
        if (count == 0)
        {
        	parent.layer.alert('请至少选择一条任务', {title : "提示"});
            return false;
        }

        var keys = [];
        instances.each(function()
        {
            keys.push($(this).val());
        });

        $('#keys').val(keys.join(','));
        $.ajax({
	        type : "GET",
	        url : "/bpm/report/firstReview/isCandownloadZip.do?keys="+keys.join(','),
	        success : function(data)
	        {
	        	if(null != data && 1 == data)
	        	{
	        		parent.parent.layer.alert('文件不存在或请求url失败！', {title : "提示"});
	        	}
	        	else
	        	{
	        		$('#downloadForm').submit();
	        	}
	        },
	        error : function()
	        {
	            alert("failed");
	        }
	    });
    }).on('click','#btn_review',function(e)
    {
    	var id = $(this).parent('.btn_td').find('.recordId').val();
    	$('#dialog_content').attr('src', '/bpm/report/firstReview/review.do?id=' + id);
	    $('#review_dialog').modal('show');
    });
    $('#released').on('click', function()
    {
    	$that = $('iframe#dialog_content')[0].contentWindow;
    	
    	var result = $that.$('#reviewResult').val();
    	var reviewOpinion = $that.$('#reviewOpinion').val();
    	var flag = true;
    	
    	if(result == "2" && reviewOpinion == ""){
			parent.layer.alert('未填写审核意见',{title:"提示"});
			flag = false;
    	}
    	
        if(flag){
        	var layerObject = parent.parent.layer;
        	var buttons = [ '确定', '取消' ];
        	
        	layerObject.confirm('确定要提交审核结果吗？', {
        		icon : 3,
        		title : '操作确认',
        		btn : buttons,
        		shade : 'transparent'
        	}, function(index)
        	{
        		$that.$('#review_form').submit();
    			layerObject.close(index);
        	}, function(index)
        	{
        		layerObject.close(index);
        	});
        }
    })
});