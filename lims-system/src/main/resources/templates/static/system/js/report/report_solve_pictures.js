$(function()
{
	var pictureIds = $('#pictureIds').val();
	$('.check-instance').each(function()
    {
		var pId = $(this).val().trim();
		if(pictureIds.indexOf(pId) > -1){
			$(this).prop("checked", true);
		}
    });
	
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

    }).on('click', '#btn_pictures', function()
    {
    	var id = parent.document.getElementById("id").value;
    	var keys = [];
    	var instances = $('.check-instance:checked');
        instances.each(function()
        {
            keys.push($(this).val());
        });
        var pictures = keys.join(',');
        
        var layerObject = parent.parent.layer;
        var buttons = [ '确定', '取消' ];
        if(keys.length > 0)
        {
            layerObject.confirm('确定保存此报告所用图片吗？', {
                icon : 3,
                title : '操作确认',
                btn : buttons,
                shade : 'transparent'
            }, function(index)
            {
            	$.ajax({
                    type: "POST",
                    dataType: "html",
                    url: "/bpm/report/handle/save_pictures.do",
                    data: {
                    	id : id,
                    	pictures : pictures
                    	},
                    success: function (data) {
                    	parent.document.getElementById("iframe_sample").value = "true";
                    	parent.document.getElementById("iframe_result").value = "true";
                    },
                    error: function(data) {
                        alert("error");
                    }
            	});
                layerObject.close(index);
            }, function(index)
            {
                layerObject.close(index);
            });
        }
        else
        {
        	layerObject.alert("请至少选择一条数据!");
        }
    });
});