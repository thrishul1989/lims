function clearClass() {
    $('.fileinput-upload-button').remove();
}

//导出数据
function downloadData(){
    var sheetId = $("#sheetId").val();
    console.log(sheetId+"~~~~~~~~~~~~~~");
    $.ajax({
        type:"POST",
        traditional: true,
        url:"/testSheet/downloadDTData",
        data:{sheetId : sheetId},
        async: false,
        success:function(data){
            $("#formValue").val(data);
            $("#hiddForm").submit();
        },
        error:function(){
            alert("failed");
        }
    });
}