$(function()
{
        var value=$('.sampleTypeId').eq(0).val();
        $.ajax({
            url : '/sample/getUnit.do',
            traditional : true,
            type : "POST",
            data : {
                id : value,
            },
            async : false,
            success : function(data)
            {
                $('.dataSize').text("样本投入量("+data+")");
            }, error : function()
            {
                parent.layer.alert('错误', {
                    title : "提示"
                });
            }
        })
})

//导出样本编号
function downloadCode() {
    var sheetId = $("#id").val();
    $.ajax({
        type : "POST",
        traditional: true,
        url : "/testing/downloadCode/cdna",
        data : {
            id : sheetId
        },
        async : false,
        success : function(data) {
            $("#formValue").val(data);
            $("#hiddForm").submit();
        },
        error : function() {
            alert("failed");
        }
    });
}

function downloadData()
{
    var sheetId = $("#id").val();
    $.ajax({
        type : "POST",
        traditional : true,
        url : "/testing/downloadCdnaExtractData",
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