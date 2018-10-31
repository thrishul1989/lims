function suc(obj) {
    var dataid = $(obj).attr("data-id");
    console.log(dataid);
    if ($(obj).is(':checked')) {
        $("#info" + dataid).css("display", "none");
    } else {
        $("#info" + dataid).css("display", "block")
    }
}


//导出数据
function downloadData() {
    var sheetId = $("[name='id']").val();
    $(".ctv").each(function(){
        ctv.push($(this).val());
    });
    $.ajax({
        type : "POST",
        traditional: true,
        url : "/testSheet/downloadPoolingData",
        data : {
            sheetId : sheetId
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