$(function()
{
    $("#assign_form").validate();



    var calc = function(concn, size, volumn)
    {
        var inputs = {};

        var requiredVolumn = parseFloat((size / concn).toFixed(2));

        if (requiredVolumn >= volumn)
        {
            inputs.sample = volumn;
            inputs.te = 0;
        }
        else
        {
            inputs.sample = requiredVolumn;
            inputs.te = volumn - requiredVolumn;
        }
        return inputs;
    };


    var flag;
    var arr=[]
    var a="";
    $('body').on('change', '#createReagentKit', function()
    {
        var val = $(this).val();

        if (undefined == val || '' == val)
        {
            $('.input-sample-size').val('');
            $('.input-sample-volume').empty();
            $('.input-te-volume').empty();
            $('.input-testing-volume').empty();
            return;
        }

        var selected = $(this).find('option:selected');
        var quantity = selected.attr('data-input-quantity');
        var volume = selected.attr('data-input-volume');

        if (undefined == quantity || '' == quantity)
        {
            parent.parent.layer.alert('任务-试剂盒配置错误：未指定样本投入量', {title : "提示"});
            $(this).val('');
            return false;
        }

        if (undefined == volume || '' == volume)
        {
            parent.parent.layer.alert('任务-试剂盒配置错误：未指定实验投入体积', {title : "提示"});
            $(this).val('');
            return false;
        }

        // 计算投入量、投入体积和补TE体积
        $('.input-sample-size').each(function()
        {
            $(this).val(quantity);
            var tr = $(this).parents('tr');
            var concn = tr.find('.input-sample-concn').attr('data-concn');
            var inputs = calc(concn, quantity, volume);

            tr.find('.input-sample-volume').empty().append(inputs.sample);
            tr.find('.marker-sample-volume').val(inputs.sample);
            tr.find('.input-te-volume').empty().append((inputs.te).toFixed(2));
            tr.find('.marker-te-volume').val(inputs.te);
            tr.find('.input-testing-volume').empty().append(volume);
            tr.find('.marker-testing-volume').val(volume);
        });
    }).on('blur', '.input-sample-size', function()
    {
        var val = $(this).val();

        if ($('#createReagentKit').val() == '')
        {
            return;
        }

        var tr = $(this).parents('tr');
        var concn = tr.find('.input-sample-concn').attr('data-concn');
        var volume = tr.find('.input-testing-volume').text();
        var inputs = calc(concn, val, volume);
        tr.find('.input-sample-volume').empty().append(inputs.sample);
        tr.find('.marker-sample-volume').val(inputs.sample);
        tr.find('.input-te-volume').empty().append((inputs.te).toFixed(2));
        tr.find('.marker-te-volume').val(inputs.te);
        tr.find('.input-testing-volume').empty().append(volume);
        tr.find('.marker-testing-volume').val(volume);
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
                            $.each($(".connect"),function(index,obj1){
                                $(obj1).val(data[index].connectNum);
                            })
                        },
                        error: function(data){
                            $(".connect").val("");
                        }
                    })
                }
            }
        })

    }).on('focus','.index',function(){
        a=$(this).val();
        $(".index").each(function(){
            arr.push($(this).val());
        });
    }).on('blur','.index',function(){
        var exit=false;
        var indexVal=$(this).val();
        var $that = $(this);
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
    });
});