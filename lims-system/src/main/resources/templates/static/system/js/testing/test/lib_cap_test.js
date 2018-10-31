$(function()
{
    // 文件上传样式重置
    $('.file-preview').hide();
    $('.fileinput-upload').hide();
    $('.fileinput-remove').hide();

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
        },
        ignore : ':checkbox'
    });

    // 捕获是否成功联动显示
    $('.toggle-qualified').on('ifChecked', function()
    {
        var id = $(this).attr('data-id');
        var $group = $('.group-' + id);
        $group.removeClass('capture-failure');

        $group.each(function()
        {
            var failures = $(this).parents('td').find('.capture-failure').length;

            if (0 == failures)
            {
                var $result = $(this).parents('tr').find('td.capture-result');
                $result.removeClass('capture-failure');
                $result.empty().append('成功');
                
                var $strategy = $(this).parents('tr').find('td.failure-strategy');
                $strategy.find('.strategies').hide();
                $strategy.find('.none').show();
                
                var $qualified = $(this).parents('tr').find('td .input-task-qualified');
                $qualified.val('1');
            }
        });
    });

    $('.toggle-qualified').on('ifUnchecked', function()
    {
        var id = $(this).attr('data-id');
        var $group = $('.group-' + id);
        $group.addClass('capture-failure');

        $group.each(function()
        {
            var $result = $(this).parents('tr').find('td.capture-result');
            $result.addClass('capture-failure');
            $result.empty().append('失败');
            
            var $strategy = $(this).parents('tr').find('td.failure-strategy');
            $strategy.find('.strategies').show();
            $strategy.find('.none').hide();
            
            var $qualified = $(this).parents('tr').find('td .input-task-qualified');
            $qualified.val('0');
        });
    });

    // 导入解析excel
    $("#butt").click(function()
    {
        var excelFileName = $('#uploadData').val();
        var formatStr = '';
        var index = excelFileName.lastIndexOf('.');
        if (excelFileName.length == 0)
        {
            parent.layer.alert('请选择需要上传的文件', {
                title : "提示"
            });
            return;
        }
        else if (index > 0)
        {
            formatStr = excelFileName.substring(index);
            if (!(".xlsx" == formatStr || ".xls" == formatStr))
            {
                parent.layer.alert('请上传excel文件', {
                    title : "提示"
                });
                return;
            }
        }
        $.ajax({
            url : '/testSheet/uploadLibCapAmount',
            type : 'POST',
            cache : false,
            data : new FormData($('#uploadForm')[0]),
            processData : false,
            contentType : false
        }).done(function(list)
        {
            if (list[0] == '' && list[1] == '')
            {
                parent.layer.alert('Excel页面没有数据！', {
                    title : "提示"
                });
            }
            else
            {
                var code = list[0];
                var concentration = list[1];
                var flag = false;
                $('#groups tbody tr').each(function(i, v)
                {
                    var value = $(this).find("td").eq(0).text();
                    for (var m = 0; m < code.length; m++)
                    {
                        if (code[m] == value)
                        {
                            flag = true;
                            $(this).find(".concentration").val(parseFloat(concentration[m]).toFixed(2));
                        }
                    }
                });
                if (!flag)
                {
                    parent.layer.alert('没有匹配的实验编号！', {
                        title : "提示"
                    });
                }
            }
            $('#myModal').modal('hide');
        });
    });
});

// 导出数据
function downloadData()
{
    var sheetId = $("#id").val();
    var concentration = [];
    var volume = [];
    $(".concentration").each(function()
    {
        concentration.push($(this).val());
    });
    $.ajax({
        type : "POST",
        traditional : true,
        url : "/testSheet/downloadLibCapData",
        data : {
            sheetId : sheetId,
            concentration : concentration,
            volume : volume,
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