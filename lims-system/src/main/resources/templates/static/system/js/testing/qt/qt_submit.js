$(function()
{
    $("#submit_form").validate({
        submitHandler : function(form)
        {
            var layerObject = parent.parent.layer;
            var buttons =
            [ '确定', '取消' ];
            layerObject.confirm('确定完成任务，提交任务单吗？', {
                icon : 3,
                title : '操作确认',
                btn : buttons,
                shade : 'transparent'
            }, function(index)
            {
                var flag = true;
                if ($("[name='primarySample.concn']").val() < 4)
                {
                    parent.layer.alert('浓度值不能小于4', {
                        title : "提示"
                    });
                    flag = false;
                }
                if (flag)
                {
                    form.submit();
                    layerObject.close(index);
                }
            }, function(index)
            {
                layerObject.close(index);
            });
        }
    });

    var update = function($td, librarySizeOfQPCR, fragmentLength)
    {
        var libraryValueOfQPCR = (librarySizeOfQPCR * 452 / fragmentLength).toFixed(2);
        $td.empty().append(libraryValueOfQPCR);
    }

    $('body').on('blur', '.fragment-length', function()
    {
        var val = $(this).val();
        var number = parseFloat(val);

        if (isNaN(number))
        {
            var historyValue = $(this).attr('data-history-value');
            $(this).val(historyValue);
        }
        else
        {
            $(this).val(number);
            $(this).attr('data-history-value', number);

            var sampleCode = $(this).attr('data-sample-code');
            var librarySizeOfQPCR = $('#librarySizeOfQPCR_' + sampleCode).val();
            var librarySizeOfQPCRNumber = parseFloat(librarySizeOfQPCR);

            if (!isNaN(librarySizeOfQPCRNumber))
            {
                update($('#sample_' + sampleCode + "_qpcr .library-value-qpcr"), librarySizeOfQPCRNumber, number)
            }
        }
    }).on('blur', '.qpcr-library-size', function()
    {
        var val = $(this).val();
        var number = parseFloat(val);

        if (isNaN(number))
        {
            var historyValue = $(this).attr('data-history-value') || '';
            $(this).val(historyValue);
        }
        else
        {
            $(this).val(number);
            $(this).attr('data-history-value', number);

            var sampleCode = $(this).attr('data-sample-code');
            var fragmentLength = $('#sample_' + sampleCode).find('.fragment-length').val();
            var fragmentLengthNumber = parseFloat(fragmentLength);

            if (!isNaN(fragmentLengthNumber))
            {
                update($('#sample_' + sampleCode + "_qpcr .library-value-qpcr"), number, fragmentLengthNumber)
            }
        }
    });
});

// 导出数据
function downloadData()
{
    var sheetId = $("#id").val();
    var fragmentLength=$("#fragmentLength").val();
    var concn = $("#concn").val();
    $.ajax({
        type : "POST",
        traditional : true,
        url : "/testSheet/downloadQtData",
        data : {
            sheetId : sheetId,
            fragment_length : fragmentLength,
            concn : concn
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
