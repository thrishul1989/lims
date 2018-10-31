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
            var chknum = $(".check-instance").size();// 选项总个数
            var chk = $('input[type=checkbox]:checked').length;// 选中总数
            if (chknum == chk)
            {
                $(".check-controller").prop("checked", true);
            }
            else
            {
                $(".check-controller").prop("checked", false);
            }
        }

    }).on(
            'click',
            '#btn_assign',
            function(e)
            {
                e.preventDefault();
                var instances = $('.check-instance:checked');
                var count = instances.length;

                if (count == 0)
                {
                    parent.layer.alert("请至少选择一条任务",{title:"提示"});
                    return false;
                }

                if (count > 96)
                {
                    parent.layer.alert("下单任务数量不能超过96，请重新选择",{title:"提示"});
                    return false;
                }

                var product = "";
                var instanceProduct;
                var keys = [];
                var assignable = true;
                var storageStatus = "";
                var assignableStatus = true;
                var cansubmit = true;
                instances.each(function()
                {
                	var flag = $(this).parents('tr').find('.storageStatus span').text();
                	if('未出库' != flag){
                		alert("请选择状态是未出库的任务！");
                		cansubmit =  false;
                		return false;
                	}
                    keys.push($(this).val());
                    instanceProduct = $(this).attr('data-product');
                    storageStatus = $(this).attr('data-storage-status');

                    if ("" == product)
                    {
                    	product = instanceProduct;
                    }
                    else
                    {
                        if (product != instanceProduct)
                        {
                            assignable = false;
                        }
                    }
                    
                    if(2 == storageStatus){
                    	assignableStatus = false;
                    }
                });
                if(!cansubmit){
                	return false;
                }
                if (!assignable)
                {
                    parent.layer.alert("所下任务中有多种检测产品，请重新选择",{title:"提示"});
                    return false;
                }

//                if (!assignableStatus)
//                {
//                    parent.layer.alert('出库状态样本，不可下达任务!',{title:"提示"});
//                    return false;
//                }
                
                $('#keys').val(keys.join(','));
                $('#testForm').submit();
                $('#full').modal('show');
            });
    $('#released').on('click', function()
    {
        $('iframe#assign_frame')[0].contentWindow.$('#assign_form').submit();
    });
});