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
        var a='';
        var flag=false;
        var storageStatus = "";
        var assignableStatus = true;
        var cansubmit = true;
        instances.each(function(index){
        	var flag = $(this).parents('tr').find('.storageStatus span').text();
        	if('未出库' != flag){
        		alert("请选择状态是未出库的任务！");
        		cansubmit =  false;
        		return false;
        	}
            if(index==0){
                a=$(this).parents('tr').find('td').eq(3).text();
            }
            else{
                if(a!=$(this).parents('tr').find('td').eq(3).text()){
                    alert("请选择产品相同的数据");
                    cansubmit =  false;
                    flag =true;
                    return false;//终端当前循环
                }
            }
            
            storageStatus = $(this).attr('data-storage-status');
            if(2 == storageStatus){
            	assignableStatus = false;
            }
        })
        if(!cansubmit){
        	return false;
        }
        if(flag){
            return false;
        }
        
//        if (!assignableStatus)
//        {
//            parent.layer.alert('出库状态样本，不可下达任务!',{title:"提示"});
//            return false;
//        }
        
        var keys = [];

        instances.each(function()
        {
            keys.push($(this).val());
        });

        $('#keys').val(keys.join(','));
        $('#testForm').submit();
        $('#assign_dialog').modal('show');
    });
    $('#released').on('click',function(){
        var hasCode = true;
        $('iframe#assign_frame')[0].contentWindow.$('.index1').each(function(e){
           var v1 = $(this).val();
            if(v1=''){
                hasCode=false;
            }
        });
        if(hasCode){
            $('iframe#assign_frame')[0].contentWindow.$('#testing_task_form').submit();
        }
   })
});