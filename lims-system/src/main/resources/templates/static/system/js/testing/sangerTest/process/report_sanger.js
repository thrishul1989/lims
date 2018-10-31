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

    }).on('click', '#btn_assign', function(e)
    {
        e.preventDefault();
        var instances = $('.check-instance:checked');
        var count = instances.length;

        if (count == 0)
        {
            parent.parent.layer.alert('请至少选择一条任务', {
                title : "提示"
            });
            return false;
        }
        
        if (count > 96)
        {
            parent.parent.layer.alert('下单任务数量不能超过96，请重新选择', {
                title : "提示"
            });
            return false;
        }
        
        var doctorAssist = "";
        var instanceDoctorAssist;
        var keys = [];
        var assignable = true;

        instances.each(function()
        {
            keys.push($(this).val());
            instanceDoctorAssist = $(this).attr('data-doctorAssist');

            if ("" == doctorAssist)
            {
            	doctorAssist = instanceDoctorAssist;
            }
            else
            {
                if (doctorAssist != instanceDoctorAssist)
                {
                    assignable = false;
                }
            }
        });

        if (!assignable)
        {
            parent.layer.alert('客户参与方式不一致，请重新选择',{title:"提示"});
            return false;
        }
        
        $('#assign_frame').attr('src', '/testing/report_sanger_assign.do?keys=' + keys.join(','));
        $('#assign_dialog').modal('show');
    });
    $('#released').on('click', function()
    {
        $('iframe#assign_frame')[0].contentWindow.$('#testing_task_form').submit();
    })
});