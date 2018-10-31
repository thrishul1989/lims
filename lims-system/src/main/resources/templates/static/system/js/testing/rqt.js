$(function()
{
    var libLaps=[];
    $(".check-instance").each(function(){
        var sampleName=$(this).parents('tr').find('td').eq(2).text();
        if(sampleName=='捕获产物'){
            var libCatch=$(this).parents('tr');
            libLaps.push(libCatch);
        }
    })
    $('body').on('click', '.check-controller', function()
    {
        if ($(this).is(":checked"))
        {
            $(".check-instance").prop("checked", true);
        }
        else
        {
            $(".check-instance").prop("checked", false);
            $(".check-instance").each(function(){
                $(this).parents('tr').css('background-color','');
            })
        }
        dataSize();
        
    }).on('click', '.check-instance', function()
    {
        if (!$(this).is(":checked"))
        {
            var $this=$(this);
            $(".check-controller").prop("checked", false);
            $(this).parents('tr').css('background-color','');
            var code3=getCode($this);
            //1筛选捕获产物样本  获取ＤＯＭ对象集合。2截取当前编号，3，再次遍历相同编号的，同步操作。
            cancel(code3,libLaps);//一起取消
        }
        else
        {   var $this=$(this);
            var totalCount = $(".check-instance").size();// 选项总个数
            var checkedCount = $('input[type=checkbox]:checked').length;// 选中总数
            var code3=getCode($this);//获取样本编号下达编号
            choose(code3,libLaps);//相同下达编号全选
            if (totalCount == checkedCount)
            {
                $(".check-controller").prop("checked", true);
            }
            else
            {
                $(".check-controller").prop("checked", false);
            }
        }
        dataSize();
    }).on('click','#btn_assign',function(e)
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
                var keys = [];
                var cansubmit = true;
                
                instances.each(function()
                        {
                            keys.push($(this).val());
                            var flag = $(this).parents('tr').find('.storageStatus span').text();
                            if('' !=flag )
                            {
                            	if('未出库' != flag){
                            		alert("请选择状态是未出库的任务！");
                            		cansubmit =  false;
                            		return false;
                            	}
                            }
                        });
                if(!cansubmit){
                	return false;
                }
                $.ajax({
                    type: "GET",
                    url: "/testing/rqt/validateIndex.do",
                    data: {ids:keys},
                    dataType: "json",
                    traditional: true, 
                    success: function(data){ 
                        if(Object.keys(data).length >0){
                            parent.parent.layer.alert('有重复接头的文库', {title : "提示"});
                          /*  for(var i=0;i<data.length;i++){
                                $('#'+data[i]).parents('tr').css('background-color','red');
                            }*/
                            $.each(data,function(key,values){
                                var list=data[key];
                                for(var i=0;i<list.length;i++){
                                    $('#'+list[i]).parents('tr').css('background-color',key);
                                }
                            });
                            return false;
                        }else{
                        	$('#dataTable tbody tr').each(function(i,v){
                        		$(this).css('background-color', "");
                        	});
                        	 $('#keys').val(keys.join(','));
                             $('#testForm').submit();
                            $('#assign_dialog').modal('show');
                        }
                    },
                });
            });
    $('#released').on('click', function()
    {
        $('iframe#assign_frame')[0].contentWindow.$('#testing_task_form').submit();
    })
    
function dataSize()
{
    //e.preventDefault();
    var instances = $('.check-instance:checked');
    var toalDataSize = 0;
    instances.each(function(){
        var sampleTypeName = $(this).closest('tr').find('.sampleTypeName').html();
        if(sampleTypeName=='PCRNGS产物')
        {
            toalDataSize=Number(toalDataSize)+Number($(this).attr('data-size'));
        }else{
            toalDataSize = Number(toalDataSize) + (Number($(this).attr('data-size')));
        }
    });
    if(toalDataSize == 0){
    	toalDataSize = "";
    }else{
    	toalDataSize = toalDataSize.toFixed(2) + "G";
    }
    $('#totalDataSize').val(toalDataSize);
}
    
});

function cancel(code,libLaps){
    for(var i=0;i<libLaps.length;i++){
        var code2=libLaps[i].find('td').eq(3).text();
        code2=code2.substring(0,code2.lastIndexOf('-'));
        if(code2==code){
            libLaps[i].find('.check-instance').prop("checked", false);
        }
    }
}
function choose(code,libLaps){
    for(var i=0;i<libLaps.length;i++){
        var code2=libLaps[i].find('td').eq(3).text();
        code2=code2.substring(0,code2.lastIndexOf('-'));
        if(code2==code){
            libLaps[i].find('.check-instance').prop("checked", true);
        }
    }
}
function getCode($this){
    var code=$this.parents('tr').find('td').eq(3).text();
    code=code.substring(0,code.lastIndexOf('-'));
    return code;
}