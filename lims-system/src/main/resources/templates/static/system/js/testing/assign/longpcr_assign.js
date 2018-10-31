var first;
var n;
$(function()
{
    $("#testing_task_form").validate();
    var a=0;
    //给name赋值
    $('.testingCode').each(function(){
       
        $(this).attr('name',"tasks["+a+"].testingCode");
        a++;
    })
    var b=0;
     $('.primer').each(function(){
        $(this).attr('name',"tasks["+b+"].primer.id");
        b++;
    })
    var c=0;
     $('.product').each(function(){
        $(this).attr('name',"tasks["+c+"].product.id");
        c++;
    })
    var d=0;
    $('.testingTask').each(function(){
        $(this).attr('name',"tasks["+d+"].testingTask.id");
        d++;
    })
    var e=0;
     $('.dnaVolume').each(function(){
        $(this).attr('name',"tasks["+e+"].dnaVolume");
        e++;
    })
    var f=0;
     $('.inputVolume').each(function(){
        $(this).attr('name',"tasks["+f+"].inputVolume");
        f++;
    })
    var g=0;
     $('.index1').each(function(){
        $(this).attr('name',"tasks["+g+"].connectNum");
        g++;
    })
    
    var calc = function(concn,volumn)//dna浓度 投入量
    {
        var inputs = {};

        var requiredVolumn = parseFloat((volumn / concn).toFixed(2));//dna体积 =  投入量/浓度
        if(requiredVolumn>0.5){
            inputs.dnaVolume=requiredVolumn;
        }else{
            inputs.dnaVolume=0.5;
        }
            inputs.inputVolume=volumn;
        return inputs;
    };

    
    $("body").on('change','#longPcrIndex',function(){

        var val = $(this).val();
        var $that = $(this);
        if(val==''){
            parent.parent.layer.alert('接头不能为空', {title : "提示"});
        }
        if(isNaN(val)){
            parent.parent.layer.alert('接头必须为数字', {title : "提示"});
            $that.val("");
        }
        $.ajax({
            type: "GET",
            url: "/connect/checkedconnectNum.do",
            data: {connectNum:val},
            dataType: "json",
            success: function(data){
                if(data){
                    parent.parent.layer.alert(val+'号接头不存在，请重新输入', {title : "提示"});
                    $that.val("");
                }
                else{
                    $.ajax({
                        type: "GET",
                        url: "/connect/ConnectListForWkcreate.do",
                        data: {connectNum:val,pageSize:$(".connect").size()},
                        dataType: "json",
                        success: function(data){
                            var i=0;
                            var n='';
                            $.each($('.connect'),function(index,obj1){
                                if($(obj1).attr("type")=='text'){
                                    $(obj1).val(data[i].connectNum);
                                    n=data[i].connectNum;
                                    i++
                                }else{
                                    $(obj1).val(n);
                                    
                                }
                                
                            })
                        },
                        error: function(data){
                            $(".connect").val("");
                        }
                    })
                }
            }
        })

    
    }).on('blur','.index1',function(){
        var exit=false;
        var indexVal=$(this).val();
        $(this).next().val(indexVal);
        var $that = $(this);
        if(indexVal==""){
            parent.parent.layer.alert("接头不能为空", {title : "提示"});
            return;
        }
        if(isNaN(indexVal)){
            parent.parent.layer.alert('接头必须为数字', {title : "提示"});
            $that.val("");
        }
        $.ajax({
            type: "GET",
            url: "/connect/checkedconnectNum.do",
            data: {connectNum:indexVal},
            dataType: "json",
            success: function(data){
                if(data){
                    parent.parent.layer.alert(indexVal+'号接头不存在，请重新输入', {title : "提示"});
                    $that.val("");
                    exit = true;
                }
            }
        })
        console.log(indexVal);
        console.log(arr);
        if(!exit){
            if(a!=indexVal){
                if(arr.indexOf(indexVal) > -1){
                    parent.parent.layer.alert("请不要输入重复的接头", {title : "提示"});
                    flag=false;
                    $that.val("");
                }
            }
            arr=[];
        }
    }).on("change","#pcrReagentKit",function(){
        var val = $(this).val();
        if (undefined == val || '' == val)
        {
            $('.inputVolumeText').empty();
            $('.dnavolumeText').empty();
            $('.inputVolume').val("");
            $('.dnavolume').val("");
            return;
        }

        var selected = $(this).find('option:selected');
        var volume = selected.attr('data-input-quantity');//投入量


        if (undefined == volume || '' == volume)
        {
            parent.parent.layer.alert('任务-试剂盒配置错误：未指定实验投入体积', {title : "提示"});
            $(this).val('');
            return false;
        }
        
        // 计算投入量、投入体积和补TE体积
        $('.input-sample-concn').each(function(index)
        {
            /*var a=index;
            if(typeof(first) == "undefined"){ 
                first=0;
                n=0;
            }else{
                first=n;//下标初始值
            }
             n=n+parseInt($(this).attr('rowspan'));//最后值；
*/             
            var concn =$(this).text();//浓度
            var inputs = calc(concn,  volume);
            var $tr = $(this).closest('tr');
            var trIndex = $tr.index();
            var rowspan = $(this).attr('rowspan');
            $tr.find('.inputVolumeText').text(inputs.inputVolume);
            $tr.find('.inputVolume').val(inputs.inputVolume);
            $tr.find('.dnaVolumeText').text(inputs.dnaVolume);
            $tr.find('.dnaVolume').val(inputs.dnaVolume);
            if(rowspan > 1)
            {
            	for(var i = 1;i<rowspan;i++)
            	{
            		var $trn = $("tbody tr:eq("+(trIndex+i)+")");
            		$trn.find('.inputVolumeText').text(inputs.inputVolume);
            		$trn.find('.inputVolume').val(inputs.inputVolume);
            		$trn.find('.dnaVolumeText').text(inputs.dnaVolume);
            		$trn.find('.dnaVolume').val(inputs.dnaVolume);
            	}
            }
         /*   $('.inputVolumeText').each(function(i){
                debugger;
                if(i>=first && i<=n-1){
                    $(this).text(inputs.inputVolume);
                }
            })
            $('.inputVolume').each(function(i){
            	debugger;
                if(i>=first && i<=n-1){
                    $(this).val(inputs.inputVolume);
                }
            })
            $('.dnaVolumeText').each(function(i){
                if(i>=first && i<=n-1){
                    $(this).text(inputs.dnaVolume);
                }
            })
            $('.dnaVolume').each(function(i){
                if(i>=first && i<=n-1){
                    $(this).val(inputs.dnaVolume);
                }
            })*/
        });
    
    }).on()
});