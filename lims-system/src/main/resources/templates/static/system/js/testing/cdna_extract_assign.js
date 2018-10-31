$(function()
{
    $("#testing_task_form").validate();

    $('body').on('change', '#extractReagentKit', function()
    {
        var selected = $(this).find('option:selected');
        var quantity = selected.attr('data-input-quantity');

        if (undefined == quantity)
        {
            quantity = '';
        }

        $('.input-sample-size').val(quantity);
    });
    
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
});