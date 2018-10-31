$(function()
{
    $("#testing_task_form").validate();

    var arr=[];

    var calc = function(concn, size)
    {
        var inputs = {};

        var requiredVolumn = parseFloat((size / concn).toFixed(2));

        if(requiredVolumn <= 0.5)
        {
            inputs.sample = 0.5;
        }else{
            inputs.sample = requiredVolumn;
        }
        return inputs;
    };

    $('body').on('change', '#reagentKitId', function()
    {
        var val = $(this).val();

        if (undefined == val || '' == val)
        {
            $('.input-sample-size').val('');
            $('.input-sample-volume').empty();
            return;
        }

        var selected = $(this).find('option:selected');
        var quantity = selected.attr('data-input-quantity');

        if (undefined == quantity || '' == quantity)
        {
            parent.parent.layer.alert('任务-试剂盒配置错误：未指定样本投入量', {title : "提示"});
            $(this).val('');
            return false;
        }

        // 计算加样体积 投入量/浓度  小于0.5按0.5算，大于3就取3
        $('.input-sample-size').each(function()
        {
            $(this).val(quantity);
            var tr = $(this).parents('tr');
            var concn = tr.find('.input-sample-concn').attr('data-concn');
            var inputs = calc(concn, quantity);
            tr.find('.input-sample-volume').empty().append(inputs.sample);
            tr.find('.marker-sample-volume').val(inputs.sample);
        });
    }).on('blur', '.input-sample-size', function()
    {
        var val = $(this).val();

        if ($('#reagentKitId').val() == '')
        {
            return;
        }

        var tr = $(this).parents('tr');
        var concn = tr.find('.input-sample-concn').attr('data-concn');
        var inputs = calc(concn, val);
        tr.find('.input-sample-volume').empty().append(inputs.sample);
        tr.find('.marker-sample-volume').val(inputs.sample);
    }).on('change', '#libIndex', function()
    {
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
                            //$.each($(".connect"),function(index,obj1){
                            //    $(obj1).val(data[index].connectNum);
                            //})

                            $('.indexShow').each(function(index){
                                var $obj = $(this);
                                $(this).val(data[index].connectNum);
                                var crtNum = $obj.parent().attr('rowSpan');
                                if(!crtNum){
                                   crtNum = 1;
                                }
                                var $tr = $obj.closest('tr');
                                var crtVal = $obj.val();
                                for(var i= 0; i < crtNum; i++){
                                    if(i==0){
                                        $tr.find('.indexHidden').val(crtVal);
                                    }else{
                                        var nextNum = i - 1;
                                        $tr.nextAll('tr:eq(' + nextNum + ')').find('.indexHidden').val(crtVal);
                                    }
                                }
                            });

                        },
                        error: function(data){
                            $(".connect").val("");
                        }
                    })
                }
            }
        })

    }).on('focus','.indexShow',function(){
        a=$(this).val();
        $(".indexShow").each(function(){
            arr.push($(this).val());
        });
    }).on('blur','.indexShow',function(){
        var exit=false;
        var indexVal=$(this).val();
        var $that = $(this);
        var crtNum = $that.parent().attr('rowSpan');
        if(!crtNum){
            crtNum = 1;
        }
        var $tr = $that.closest('tr');
        for(var i= 0; i < crtNum; i++){
            if(i==0){
                $tr.find('.indexHidden').val(indexVal);
            }else{
                var nextNum = i - 1;
                $tr.nextAll('tr:eq(' + nextNum + ')').find('.indexHidden').val(indexVal);
            }
        }

        if(indexVal==""){
            parent.parent.layer.alert("接头不能为空", {title : "提示"});
            return;
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
    });
});