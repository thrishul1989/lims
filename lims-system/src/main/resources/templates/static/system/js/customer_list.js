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


        var sampleType = "";
        var keys = [];

        instances.each(function()
        {
            keys.push($(this).val());

        });

        $('#keys').val(keys.join(','));
        $('#assign_dialog').modal('show');
        $('#testForm').submit();
    }).on('click', '#btn_remove', function(r)
    {
        r.preventDefault();
        var instances = $('.check-instance:checked');
        var count = instances.length;
        if (count == 0)
        {
            alert("请至少选择一条任务");
            return false;
        }


        var sampleType = "";
        var ids = [];

        instances.each(function()
        {
            ids.push($(this).val());

        });

        $('#ids').val(ids.join(','));
        $('#assign_remove').modal('show');
        $('#removeForm').submit();
        console.info("aaaaa");
    });



    $('#released').on('click', function()
    {
        $('iframe#create_task_form')[0].contentWindow.$('#testing_task_form').submit();
    });

    $('#remove').on('click', function()
    {
        $('iframe#remove_task_form')[0].contentWindow.$('#remove_task,form').submit();
    })
});