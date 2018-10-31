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
            var chknum = $(".check-instance").size();//选项总个数 
            var chk = $('input[type=checkbox]:checked').length;//选中总数
            if(chknum==chk){
                $(".check-controller").prop("checked", true);
            }else{
                $(".check-controller").prop("checked", false);
            } 
        }
        
    }).on('click', '#btn_assign', function(e)
    {
        e.preventDefault();
        var instances = $('.check-instance:checked');
        var count = instances.length;

        if (count == 0)
        {
            alert("请至少选择一条任务");
            return false;
        }

        if (count > 96)
        {
            alert("下单任务数量不能超过96，请重新选择");
            return false;
        }

        var sampleType = "";
        var instanceSampleType;
        var keys = [];
        var assignable = true;

        instances.each(function()
        {
            keys.push($(this).val());
            instanceSampleType = $(this).attr('data-sample-type');

            if ("" == sampleType)
            {
                sampleType = instanceSampleType;
            }
            else
            {
                if (sampleType != instanceSampleType)
                {
                    assignable = false;
                }
            }
        });

        if (!assignable)
        {
            alert("样本类型不一致，请重新选择");
            return false;
        }
        $('#keys').val(keys.join(','));
        $('#full').modal('show');
        $('#testForm').submit();
    });
    $('#released').on('click',function(){
        $('iframe#dialog_content')[0].contentWindow.$('#testing_task_form').submit();
   })
});