$(function()
{
    $("#testing_task_form").validate();

    $('body').on('change', '#reagentKitId', function()
    {
        var selected = $(this).find('option:selected');
        var quantity = selected.attr('data-input-quantity');

        if (undefined == quantity)
        {
            quantity = '';
        }

        $('.input-sample-size').val(quantity);
    });
});