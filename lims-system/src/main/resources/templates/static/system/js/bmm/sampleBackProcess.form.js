function selectType(obj)
{
    var d2 = $(obj).parents("tr").next().next();
    if ($(obj).val() != "0")
    {
        d2.show();
    }
    else
    {
        d2.hide();
    }
}

$(function()
{
	$('body').on('blur', '.volumn', function(){
		var volumn = $(this).val();
		var sampleSize = $(this).next('.sampleSize').val();
		if(Number(volumn) > Number(sampleSize)){
			parent.layer.alert("反样体积不能大于送样样本量,样本量为" + sampleSize, {"title" : "提示"});
			$(this).val(sampleSize);
		}
	});

    $("#print").click(function()
    {
        var i = 0;
        var j = 0;
        $('input.volumn').each(function()
        {
            $('td.volumn').eq(i).text($(this).val());
            i++;
        })
        $('input.remark').each(function()
        {
            $('td.remark').eq(j).text($(this).val());
            j++;
        })
        myPreview();
    })

    $("#check").click(function(e)
    {

        var i = 0;
        var a = 0;
        var b = 0;
        $('.sampleCode').each(function(e)
        {
            $(this).attr('name', "sampleBackProcessings[" + i + "].sampleCode");
            $(this).parents('tr').find('.volumn').attr('name', "sampleBackProcessings[" + i + "].volume");
            $(this).parents('tr').find('.remark').attr('name', "sampleBackProcessings[" + i + "].remark");
            $(this).parents('tr').prev().attr('name', "sampleBackProcessings[" + i + "].sampleId");
            i++;
        })

        var r = confirm("确认提交吗");
        if (r == true)
        {
            $("#process_form").validate();
            $('#process_form').submit();
        }
        else
        {
           return false;
        }

    })
})