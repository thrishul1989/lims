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
        	if(requiredVolumn < 0.5){
        		requiredVolumn = 0.5;
        	}
            inputs.sample = requiredVolumn;
            inputs.te = volumn - requiredVolumn;
        }
        return inputs;
    };

    var flag;
    var arr=[]
    var a="";
    $('body').on('change', '#reagentKit', function()
    {
        var val = $(this).val();

        if (undefined == val || '' == val)
        {
            $('.input-sample-size').val('');
            $('.input-sample-volume').empty();
            $('.input-te-volume').empty();
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
        });
    }).on('blur', '.input-sample-size', function()
    {
        var val = $(this).val();
        if ($('#reagentKit').val() == '')
        {
            return;
        }

        var tr = $(this).parents('tr');
        
        var concn = tr.find('.input-sample-concn').attr('data-concn');
        var volume = $('#reagentKit').find('option:selected').attr('data-input-volume');
        var inputs = calc(concn, val, volume);
        tr.find('.input-sample-volume').empty().append(inputs.sample);
        tr.find('.marker-sample-volume').val(inputs.sample);
        tr.find('.input-te-volume').empty().append((inputs.te).toFixed(2));
        tr.find('.marker-te-volume').val(inputs.te);
    });
});