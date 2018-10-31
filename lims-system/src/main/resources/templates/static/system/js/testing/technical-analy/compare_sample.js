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
            var totalCount = $(".check-instance").length;// 选项总个数
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

    }).on('click', '#btnCompareSampleReAnalysis', function(e)
        {
            e.preventDefault();
            if($("#sampleProductCode").val()!=undefined&&$("#sampleProductCode").val()!=''){ 
                $('#compare_sample_dialog').modal('show');
                $('#compare_sample_form').submit();
            }else{
            	alert("没有对照样本数据");
            }
        });
/*    $('#released').on('click', function()
    {
        $('iframe#dialog_content')[0].contentWindow.$('#testing_task_form').submit();
    })*/
});