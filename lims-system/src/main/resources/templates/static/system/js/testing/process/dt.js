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
            parent.layer.alert('请至少选择一条任务',{title:"提示"});
            return false;
        }

        if (count > 96)
        {
            parent.layer.alert('下单任务数量不能超过96，请重新选择',{title:"提示"});
            return false;
        }
        
        var primersLength = 0;
        var primerTest="";
        var flag = true;
        var cansubmit = true;
        instances.each(function(){
        	var flag = $(this).parents('tr').find('.storageStatus span').text();
        	if('未出库' != flag){
        		alert("请选择状态是未出库的任务！");
        		cansubmit =  false;
        		return false;
        	}
        	instancePrimers = $(this).attr('data-primers');
        	if(undefined == instancePrimers||null == instancePrimers||""==instancePrimers)
        	{
        		var index = $(this).parent().next().text()
        		parent.layer.alert('序号为'+index+'的数据没有关联引物！',{title:"提示"});
        		flag = false;
        		return false;
        	}
        	var pArray = instancePrimers.split(",");
        	primerTest = primerTest+instancePrimers+","
        	primersLength = primersLength + pArray.length;
        });
        if(!cansubmit){
        	return false;
        }
        if(!flag)
        {
        	return;
        }
        var primer=primerTest.split(',');
      //数组结尾会有空格，如果text没有值也会有空格，处理数组前，先去掉数组中的""值。
        for(var i=0;i<primer.length;i++){
            if(primer[i]==''){
                primer.splice(i, 1);
                i--;
            }
        }
        
        primer= primer.sort();
        var primers=[primer[0]];        
        //引物去重
        for(var i=1;i<primer.length;i++){
            if(primer[i]!=primer[i-1]){
                primers.push(primer[i]);
            }
        }
       
        var count=0;//实验所需的板子总数量；
        for(var i=0;i<primers.length;i++){
            var n=0;//相同的引物数量
            var m=0;//每组引物用到的板子数量
            for(var j=0;j<primer.length;j++){
                if(primers[i]==primer[j]){
                    n++;
                }
            }
            if(n<=8){
                m=1;
            }else if(n>8&&n<=16){
                m=2;
            }
            else if(n>16&&n<=24){
                m=3;
            }
            else if(n>24&&n<=32){
                m=4;
            }
            else if(n>32&&n<=40){
                m=5;
            }
            else if(n>40&&n<=48){
                m=6;
            }
            else if(n>48&&n<=56){
                m=7;
            }
            else if(n>56&&n<=64){
                m=8;
            }
            else if(n>64&&n<=72){
                m=9;
            }
            else if(n>72&&n<=80){
                m=10;
            }
            else if(n>80&&n<=88){
                m=11;
            }
            else if(n>88&&n<=96){
                m=12;
            }else{
                m=13;
            }
            count=count+m;
        }
        
        if (count > 12)
        {
        	parent.layer.alert('样本板占用个数超过96个，请重新选择',{title:"提示"});
            return false;
        }

        var keys = [];
        var storageStatus = "";
        var assignableStatus = true;

        instances.each(function()
        {
            keys.push($(this).val());
            storageStatus = $(this).attr('data-storage-status');
            if(2 == storageStatus){
            	assignableStatus = false;
            }
        });
//        if (!assignableStatus)
//        {
//            parent.layer.alert('出库状态样本，不可下达任务!',{title:"提示"});
//            return false;
//        }

        $('#keys').val(keys.join(','));
        $('#testForm').submit();
        $('#assign_dialog').modal('show');
    });
    $('#released').on('click',function(){
        $('iframe#assign_frame')[0].contentWindow.$('#testing_task_form').submit();
   })
});