$(function () {

    //**************** 动态添加表单 BEGIN*********************//
    var addTemp = $("#addTemp").html();
    $("body").on("click",".add",function () {
        $("tbody#sampleTr").append(addTemp);
        setIndex();
    })

    $("tbody#sampleTr").on("click",".remove",function () {
        $(this).parent().parent().remove();
        setIndex();
    })

    function setIndex(){
        $('tbody#sampleTr tr').each(function(i,v){
            $(this).find(".sampleCode").attr('name','sheetSampleList['+i+'].sampleCode');
            $(this).find(".dataCode").attr('name','sheetSampleList['+i+'].dataCode');
            $(this).find(".indexCode").attr('name','sheetSampleList['+i+'].indexCode');
        });
    }
});