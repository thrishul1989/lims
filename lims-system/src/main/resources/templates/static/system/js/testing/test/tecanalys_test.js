$(function()
{
    $('.file-preview').hide();
    $('.fileinput-upload').hide();
    $('.fileinput-remove').hide();

    $('body').on('click', '#button_submit', function()
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
            $('#uploadDataTable tbody tr').each(function(i, v)
            {
                var reason = $(this).find(".abnormalReason").text();
                if (reason != null && reason != "")
                {
                    parent.layer.alert('请先处理异常记录', {
                        title : "提示"
                    });
                    flag = false;
                    return false;
                }
            })
            var trText = $('#uploadDataTable tbody').html();
            if ("" == trText)
            {
                parent.layer.alert('请先上传结果数据!', {
                    title : "提示"
                });
                flag = false;
                return false;
            }
            if (flag)
            {
                $('#submit_form').submit();
                layerObject.close(index);
            }
        }, function(index)
        {
            layerObject.close(index);
        });
    });
});