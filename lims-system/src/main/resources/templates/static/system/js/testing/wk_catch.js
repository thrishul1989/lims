$(function()
{

    
    var indexCode=[];
    $('body').on('click', '.check-controller', function()
    {
        if ($(this).is(":checked"))
        {
            $(".check-instance").prop("checked", true);
            var flag = false;
            $(".check-instance").each(function(){
                if($.inArray($(this).attr('data-library-index'),indexCode)==-1){
                    indexCode.push($(this).attr('data-library-index'));
                    this.checked=true;
                } else{
                    flag = true;
                    this.checked=false;
                }
            });
        }
        else
        {
            $(".check-instance").prop("checked", false);
            indexCode=[];
        }
        var instances = $('.check-instance:checked');
        var dataSize=0;
        instances.each(function(){
            dataSize=parseFloat(dataSize)+parseFloat($(this).attr('data-dataSize'));
        })
        $('#dataSize').val(dataSize.toFixed(1));
    }).on('click', '.check-instance', function()
    {
        if (!$(this).is(":checked"))
        {     
            $(".check-controller").prop("checked", false);
            indexCode.splice($.inArray($(this).attr('data-library-index'),indexCode),1);
        }
        else
        {
            var chknum = $(".check-instance").size();
            var chk = $('input[type=checkbox]:checked').length;
            if(chknum==chk){
                $(".check-controller").prop("checked", true);
            }else{
                $(".check-controller").prop("checked", false);
            }

            var indexCode2=$(this).attr('data-library-index');
            if($.inArray(indexCode2,indexCode)!=-1){
                parent.layer.alert("该接头已经存在");
                this.checked=false;
            }else{
                indexCode.push(indexCode2);
            }
        }
        
        var instances = $('.check-instance:checked');
        var count = instances.length;

        var dataSize=0;
        instances.each(function(){
            dataSize=parseFloat(dataSize)+parseFloat($(this).attr('data-dataSize'));
        })
        $('#dataSize').val(dataSize.toFixed(1));

    }).on('click', '#btn_assign', function(e)
    {
        e.preventDefault();
        var instances = $('.check-instance:checked');
        var count = instances.length;

        var dataSize=0;
        instances.each(function(){
            dataSize=parseFloat(dataSize)+parseFloat($(this).attr('data-dataSize'));
        })
        $('#dataSize').val(dataSize.toFixed(1));
        if (count == 0)
        {
            alert("请至少选择一条任务");
            return false;
        }

        var sampleType = "";
        var storageStatus = "";
        var instanceSampleType;
        var keys = [];
        var assignable = true;
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
            instanceSampleType = $(this).attr('data-sample-type');
            storageStatus = $(this).attr('data-storage-status');

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
            
            if(2 == storageStatus){
            	assignableStatus = false;
            }
        });
        if(!cansubmit){
        	return false;
        }
        if (!assignable)
        {
            alert("样本类型不一致，请重新选择");
            return false;
        }
//        if (!assignableStatus)
//        {
//            parent.layer.alert('出库状态样本，不可下达任务!',{title:"提示"});
//            return false;
//        }

        $('#keys').val(keys.join(','));
        $('#sampleType').val(sampleType);
        
        //判断是否有其他该订单下样本未被选中，给予提示
        $.ajax({
            url : '/testing/wk_catch_assign_check.do',
            type : "GET",
            data : {
                keys : $('#keys').val(),
            },
            success : function(data)
            {
              if(data.length != 0){
            	data.join(',');
            	alert("订单编号："+data+"尚有样本未选中！");
              }
            }
        })
        
        
        $('#full').modal('show');
        $('#testForm').submit();
    });
    $('#released').on('click',function(){
        $('iframe#create_task_form')[0].contentWindow.$('#lib_cap_form').submit();
   })
});