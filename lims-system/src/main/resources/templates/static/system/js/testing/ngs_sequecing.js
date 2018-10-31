$(function()
{
    $('body').on('click', '.btn-assign', function()
    {
        var id = $(this).attr('data-id');
        $("#assign_frame").attr("src", "/testing/ngsSequecing_assign.do?id=" + id);
        $('#full').modal('show');
    })
    $('#released').on('click', function()
    {
        $('iframe#assign_frame')[0].contentWindow.$('#assign_form').submit();
    })
});