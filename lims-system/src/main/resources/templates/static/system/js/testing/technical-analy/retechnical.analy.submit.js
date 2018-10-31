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

        if (0 == mc)
        {
            var count = qc - mc;
            layerObject.alert("未提交分析结果，请提交结果后确认完成。", {
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
            var taskInstance = $('.record-submit');
            var taskIds=[];
            taskInstance.each(function()
            {
                var taskId = $(this).attr('data-id');
                taskIds.push(taskId);
            });
            console.log(taskIds);
            $('iframe#resultFrame')[0].contentWindow.$('#taskIds').val(taskIds.join(","));
            $('iframe#resultFrame')[0].contentWindow.$('#submitType').val(1);
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