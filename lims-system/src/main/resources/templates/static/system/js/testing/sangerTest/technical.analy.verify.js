$(function()
{
    $("#submit_form").validate({
        submitHandler : function(form)
        {
            var layerObject = parent.parent.layer;
            var buttons = [ '确定', '取消' ];

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