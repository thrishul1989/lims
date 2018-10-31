$(function()
{
    $("#submit_form").validate({
        submitHandler : function(form)
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
                form.submit();
                layerObject.close(index);
            }, function(index)
            {
                layerObject.close(index);
            });
        }
    });
});