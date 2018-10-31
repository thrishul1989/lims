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
        var probe="";
        e.preventDefault();
        var instances = $('.check-instance:checked');
        var cansubmit = true;
        instances.each(function(){
        	var flag = $(this).parents('tr').find('.storageStatus span').text();
        	if('未出库' != flag){
        		alert("请选择状态是未出库的任务！");
        		cansubmit =  false;
        		return false;
        	}
            var probeText= $(this).parents('tr').find('.probe').text();
            probe=probe+probeText+','; 
        })
        if(!cansubmit){
        	return false;
        }
        var probes=probe.split(',');
        //数组结尾会有空格，如果text没有值也会有空格，处理数组前，先去掉数组中的""值。
        for(var i=0;i<probes.length;i++){
            if(probes[i]==''){
                probes.splice(i, 1);
                i--;
            }
        }
        var count1=probes.length;
        var probes=probes.sort();
        var probes2=[probes[0]];
        //探针去重
        for(var i=1;i<probes.length;i++){
            if(probes[i]!=probes[i-1]){
                probes2.push(probes[i]);
            }
        }
        var count2=probes2.length;
        var count=0;//计算用到的探针板数量
        for(var i=0;i<probes2.length;i++){
            var n=0;
            var m=0;
            for(var j=0;j<probes.length;j++){
                if(probes2[i]==probes[j]){
                    n++;
                }
            }
            if(n<=5){
                m=1;
            }else if(n>5&&n<=13){
                m=2;
            }
            else if(n>5&&n<=13){
                m=3;
            }
            else if(n>13&&n<=29){
                m=4;
            }
            else if(n>29&&n<=37){
                m=5;
            }
            else if(n>37&&n<=45){
                m=6;
            }
            else if(n>45&&n<=53){
                m=7;
            }
            else if(n>53&&n<=61){
                m=8;
            }
            else if(n>61&&n<=69){
                m=9;
            }
            else if(n>69&&n<=77){
                m=10;
            }
            else if(n>77&&n<=85){
                m=11;
            }
            else if(n>85&&n<=93){
                m=12;
            }else{
                m=13;
            }
            count=count+m;
        }
        
        if (count == 0)
        {
            alert("请至少选择一条任务");
            return false;
        }

        if (count > 12)
        {
            alert("样本板占用个数超过96个，请重新选择");//CS指定提示
            return false;
        }

        var keys = [];
        var flag=true;
        var storageStatus = "";
        var assignableStatus = true;
        instances.each(function(index) {
            var crt_v = $(this).closest('tr').find('.probe').html();
            if(!$.trim(crt_v)){
                flag = false;
                return false;
            }
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
        
        if(!flag){
            parent.layer.alert('存在探针为空的数据行，请检查后再下达！',{title:"提示"});
        }else{
        	 $('#keys').val(keys.join(','));
             $('#testForm').submit();
             $('#assign_dialog').modal('show');
        }
    });
    $('#released').on('click',function(){
        var hasCode = true;
        $('iframe#assign_frame')[0].contentWindow.$('#testing_task_detail_table tbody tr').each(function(e){
           var v1 = $(this).find('td:eq(0)').html();
            console.log(v1);
            if(!v1){
                alert('请点击下方生成编号按钮');
                hasCode = false;
                return false;
            }
            if($(this).find('td:eq(0)').text()=='A13'){
                hasCode = false;
                return false;
            }
        });
        if(hasCode){
            $('iframe#assign_frame')[0].contentWindow.$('#testing_task_form').submit();
        }
   })
});