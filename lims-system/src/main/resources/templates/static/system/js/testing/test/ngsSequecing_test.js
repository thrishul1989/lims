$(function()
{
    $("#submit_form").validate({
        submitHandler : function(form)
        {
        	
        	if ($('input[name="lanCode"]:checked').length == 0){
        		alert("请勾选lan号!!");
        		return false;
        	}
        			
        			
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

    var update = function($td, librarySizeOfQPCR, fragmentLength)
    {
        var libraryValueOfQPCR = (librarySizeOfQPCR * 452 / fragmentLength).toFixed(2);
        console.log($td);
        $td.empty().append(libraryValueOfQPCR);
    }

    $('body').on('blur', '.cluster', function(){
    	var cluster = $(this).val();
    	var re = /^[1-9]\d*$/;
    	if (!(re.test(cluster))){
    		parent.layer.alert('cluster的值必须为正整数',{title:"提示"});
    		$(this).val('');
    	}
    }).on('blur', '.fragment-length', function()
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

//导出数据
function downloadData() {
    var sheetId = $("#id").val();
    $.ajax({
        type : "POST",
        traditional: true,
        url : "/testSheet/downloadOnTestingData",
        data : {
            sheetId : sheetId
        },
        async : false,
        success : function(data) {
            $("#formValue").val(data);
            $("#hiddForm").submit();
        },
        error : function() {
            alert("failed");
        }
    });
}

