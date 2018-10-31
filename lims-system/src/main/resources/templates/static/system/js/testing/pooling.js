$(function()
{
    $('body').on('click', '.dealWith', function()
    {
        $('#full').modal('show');
    }).on('click', '.btn-assign', function()
    {
        var id = $(this).attr('data-id');
        $('#dialog_content').attr('src', '/testing/pooling_assign.do?id=' + id);
        $('#assign_dialog').modal('show');
    });
   
    $('#released').on('click', function()
    {   if("success"==$('#default').html()){
        $('iframe#dialog_content')[0].contentWindow.$('#testing_task_form').submit();
    }else{
         alert("CT值必须是数字");
    }
    })
});