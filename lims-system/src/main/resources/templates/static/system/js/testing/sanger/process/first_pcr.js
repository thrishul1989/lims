$(function()
{
    
   
 
    $('body').on('click', '.check-controller', function()
    {
        if ($(this).is(":checked"))
        {
            $(".check-instance").each(function(index){
                if(index<96){
                    $(this).prop("checked", true);
                }
            })
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
            if (96 <= checkedCount)//选项大于96 全选按钮不变
            {
                $(".check-controller").prop("checked", true);
            }
            else//小于96全选取消
            {
                $(".check-controller").prop("checked", false);
            }
        }

    }).on('click', '#btn_choose96', function()
    {
        $(".check-instance").each(function(i){
            var resubmitStatusflag = $(this).parents('tr').find('.resubmitStatus span').text();
            var flag = $(this).parents('tr').find('.storageStatus span').text();
            var i = $('.check-instance:checked').length;
        	if(i<96){
                if('正常' == resubmitStatusflag && '未出库' == flag) {
                    $(this).prop("checked", true);
                    i++;
                }
        	}
        });
    }).on('click','#btn_assign',function(e)
            {
                e.preventDefault();
                var instances = $('.check-instance:checked');
                var count = instances.length;
                console.log(count);

                if (count == 0)
                {
                    parent.layer.alert('请至少选择一条任务',{title:"提示"});
                    return false;
                }
                
                if (count > 96)
                {
                    parent.layer.alert('下单任务数量不能超过96，请重新选择',{title:"提示"});
                    return false;
                }
                
                var sampleList=[];
                var primerList=[];
                var storageStatus = "";
                var assignableStatus = true;
                var cansubmit = true;
                instances.each(function(){
                	var flag = $(this).parents('tr').find('.storageStatus span').text();
                	if('未出库' != flag){
                		alert("请选择状态是未出库的任务！");
                		cansubmit =  false;
                		return false;
                	}
                   var sampleCode= $(this).parent().next().next().next().next().text();
                   var primerCode= $(this).parent().next().next().next().next().next().text();
                   if($.inArray(sampleCode, sampleList)==-1){
                       sampleList.push(sampleCode);
                   }
                   if($.inArray(primerCode, primerList)==-1){
                       primerList.push(primerCode);
                   }
                   
                   storageStatus = $(this).attr('data-storage-status');
                   if(2 == storageStatus){
                   	assignableStatus = false;
                   }
                })
                if(!cansubmit){
                	return false;
                }
                if(sampleList.length>48){
                    parent.layer.alert('样本数不可以超过48',{title:"提示"});
                    return false;
                }
                if(primerList.length>48){
                    parent.layer.alert('引物数不可以超过48',{title:"提示"});
                    return false;
                }
                
//                if (!assignableStatus)
//                {
//                    parent.layer.alert('出库状态样本，不可下达任务!',{title:"提示"});
//                    return false;
//                }
                
                var keys = [];
                instances.each(function() {keys.push($(this).val());});
                $('#keys').val(keys.join(','));
                $('#testForm').submit();
                $('#assign_dialog').modal('show');
            });
    $('#released').on('click', function()
    {
        $('iframe#assign_frame')[0].contentWindow.$('#testing_task_form').submit();
    })
});