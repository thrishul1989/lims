$(function()
{
    $('body').on('click', '.btn-assign', function(e)
    {
        e.preventDefault();
        var id = $(this).attr('data-id');
        $("#assign_ifrmae").attr("src", "qt_assign.do?id=" + id);
        $('#full').modal('show');
    })

    $('#released').on('click', function()
    {
        $('iframe#assign_ifrmae')[0].contentWindow.$('#assign_form').submit();
    })
});