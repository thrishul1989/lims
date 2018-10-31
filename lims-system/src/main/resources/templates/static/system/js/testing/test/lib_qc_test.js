$(function()
{
    // 文件上传样式重置
    $('.file-preview').hide();
    $('.fileinput-upload').hide();
    $('.fileinput-remove').hide();

    // 不合格详情联动显示
    $('.toggle-qualified').on('ifChecked', function()
    {
        var id = $(this).attr('data-id');
        $('#unqualified_' + id).hide();
    });

    $('.toggle-qualified').on('ifUnchecked', function()
    {
        var id = $(this).attr('data-id');
        $('#unqualified_' + id).show();
    });

    var asUnqualified = function($tr)
    {
        var $toggle = $tr.find(".toggle-qualified");
        var id = $toggle.attr('data-id');
        $toggle.prop("checked", false);
        $toggle.iCheck("update");
        $('#unqualified_' + id).show();
    }

    var asQualified = function($tr)
    {
        var $toggle = $tr.find(".toggle-qualified");
        var id = $toggle.attr('data-id');
        $toggle.prop("checked", true);
        $toggle.iCheck("update");
        $('#unqualified_' + id).hide();
    }

    // 导入解析excel
    $("#butt").click(function()
    {
        var excelFileName = $('#uploadData').val();
        var formatStr = '';
        var index = excelFileName.lastIndexOf('.');
        if (excelFileName.length == 0)
        {
            parent.layer.alert('请选择需要上传的文件', {
                title : "提示"
            });
            return;
        }
        else if (index > 0)
        {
            formatStr = excelFileName.substring(index);
            if (!(".xlsx" == formatStr || ".xls" == formatStr))
            {
                parent.layer.alert('请上传excel文件', {
                    title : "提示"
                });
                return;
            }
        }
        $.ajax({
            url : '/testSheet/uploadLibQcAmount',
            type : 'POST',
            cache : false,
            data : new FormData($('#uploadForm')[0]),
            processData : false,
            contentType : false
        }).done(function(list)
        {
            if (list[0] == '' && list[1] == '' && list[2] == '' && list[3] == '')
            {
                parent.layer.alert('Excel页面没有数据！', {
                    title : "提示"
                });
            }
            else
            {
                var dnaCode = list[0];
                var concentration = list[1];
                var volume = list[2];
                var wantOne = list[3];
                var wantTwo = list[4];
                var fragmentLengthStart = list[5];
                var fragmentLengthEnd = list[6];
                var content = list[7];
                var flag = false;
                $('.getTr').each(function(i, v)
                {
                    var value = $(this).find("td").eq(5).text();
                    for (var m = 0; m < dnaCode.length; m++)
                    {
                        if (dnaCode[m] == value)
                        {
                            flag = true;
                            if(''!=concentration[m] && !isNaN(concentration[m])){
                            	$(this).find("td").eq(6).children('input').val(parseFloat(concentration[m]).toFixed(2));
                            }else{
                            	$(this).find("td").eq(6).children('input').val('');
                            }
                            if(''!=volume[m] && !isNaN(volume[m])){
                            	$(this).find("td").eq(7).children('input').val(parseFloat(volume[m]).toFixed(2));
                            }else{
                            	$(this).find("td").eq(7).children('input').val('');
                            }
                            if(''!=wantOne[m] && !isNaN(wantOne[m])){
                            	$(this).find("td").eq(8).children('input').val(parseFloat(wantOne[m]).toFixed(2));
                            }else{
                            	$(this).find("td").eq(8).children('input').val('');
                            }
                            if(''!=wantTwo[m] && !isNaN(wantTwo[m])){
                            	$(this).find("td").eq(9).children('input').val(parseFloat(wantTwo[m]).toFixed(2));
                            }else{
                            	$(this).find("td").eq(9).children('input').val('');
                            }
                            if(''!=fragmentLengthStart[m] && !isNaN(fragmentLengthStart[m])){
                                $(this).find("td").eq(10).children('input').val(parseFloat(fragmentLengthStart[m]).toFixed(2));
                            }else{
                                $(this).find("td").eq(10).children('input').val('');
                            }
                            if(''!=fragmentLengthEnd[m] && !isNaN(fragmentLengthEnd[m])){
                                $(this).find("td").eq(11).children('input').val(parseFloat(fragmentLengthEnd[m]).toFixed(2));
                            }else{
                                $(this).find("td").eq(11).children('input').val('');
                            }
                            $(this).find("td").eq(12).children('input').val(content[m]);
                            var id = $(this).find("td").eq(13).children('input').val();
                            if (content[m] == "D" || content[m] == "C")
                            {
                                asUnqualified($(this));
                            }
                            else
                            {
                                asQualified($(this));
                            }
                        }
                    }
                });
                $(".printTr").each(function()
                {
                    var value = $(this).find("td").eq(5).text();
                    for (var m = 0; m < dnaCode.length; m++)
                    {
                        if (dnaCode[m] == value)
                        {
                            $(this).find(".printConcentration").text(parseFloat(concentration[m]).toFixed(2));
                            $(this).find(".printVolume").text(parseFloat(volume[m]).toFixed(2));
                            $(this).find(".printA260280").text(parseFloat(wantOne[m]).toFixed(2));
                            $(this).find(".printA260230").text(parseFloat(wantTwo[m]).toFixed(2));
                            $(this).find(".fragmentLengthStart").text(parseFloat(fragmentLengthStart[m]).toFixed(2));
                            $(this).find(".fragmentLengthEnd").text(parseFloat(fragmentLengthEnd[m]).toFixed(2));
                            $(this).find(".printqcLevel").text(content[m]);
                        }
                    }
                });
                if (!flag)
                {
                    parent.layer.alert('没有匹配的样本编号！', {
                        title : "提示"
                    });
                }
            }
            $('#myModal').modal('hide');
        });
    });
});

// 导出数据
function downloadData()
{
    var sheetId = $("#id").val();
    $.ajax({
        type : "POST",
        traditional : true,
        url : "/testSheet/downloadLibQcData",
        data : {
            sheetId : sheetId
        },
        async : false,
        success : function(data)
        {
            $("#formValue").val(data);
            $("#hiddForm").submit();
        },
        error : function()
        {
            alert("failed");
        }
    });
}