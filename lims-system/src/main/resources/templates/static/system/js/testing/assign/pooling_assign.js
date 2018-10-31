$(function()
{
    $("#testing_task_form").validate();

    var update = function($tr, ratioG, ratioS)
    {
        var rv = $tr.find('.rv1').val();
        var gv = (rv * ratioG).toFixed(2);
        var mv = (gv * ratioS).toFixed(2);
        console.log(mv+"~~~~~");
        
        $tr.find('input.gv').val(gv);
        $tr.find('input.mv').val(mv);
        $tr.find('td.global-adjust-volume').empty().append(gv)
        $tr.find('td.mix-volume').empty().append(mv)
    }
    
    var getTotalVolume = function(){
    	var totalVolume = 0;
    	$('#assign_table tbody tr').each(function(){
    		var volume = $(this).find('.mix-volume').text();
    		totalVolume = totalVolume + Number(volume);
    	});
    	$('#totalVolume').empty().append(totalVolume.toFixed(2));
    }
    
    $().ready(function(){
    	getTotalVolume();
    });
    
    $('body').on('blur', '#global_ratio', function()
    {
        var val = $(this).val();
        var number = parseFloat(val);

        if (isNaN(number) || number < 1)
        {
            var historyValue = $(this).attr('data-history-value');
            $(this).val(historyValue);
        }
        else
        {
            $(this).val(number);
            $(this).attr('data-history-value', number);

            $('#assign_table tbody tr').each(function()
            {
                var $tr = $(this);
                var ratioS = $tr.find('.specified-ratio').val();
                update($tr, number, ratioS);
            })
        }
        getTotalVolume();

    }).on('blur', '.specified-ratio', function()
    {
        var val = $(this).val();
        var number = parseFloat(val);

        if (isNaN(number) || number < 1)
        {
            var historyValue = $(this).attr('data-history-value');
            $(this).val(historyValue);
        }
        else
        {
            $(this).val(number);
            $(this).attr('data-history-value', number);
            
            var $tr = $(this).parents('tr');
            var ratioG = $('#global_ratio').val();
            update($tr, ratioG, number);
        }
        getTotalVolume();
        
    });
    
    $('.ctv').change(function(){
        var a=$(this).val();
        calculate();
        if(isNaN(a)){
            alert("请输入数字");
            $('#default', window.parent.document).html("fail");
        }else{
            $('#default', window.parent.document).html("success");
        }
    });
});
function calculate(){ 
    var maxCt=0;
    $('.ctv').each(function(){
        var a=$(this).val();
        if(a>maxCt){
            maxCt=a;
        }
    })
    $(".rqtv").each(function(){
        var b=$(this).prev().find('input').val();
        var rqtv=Math.pow(2,(parseFloat(maxCt)-parseFloat(b))).toFixed(2);
        var num=$(this).next().text();
        var volume=(parseFloat(num)/rqtv).toFixed(2);
        $(this).parents('tr').find('.cvt').val(b);
        $(this).parents('tr').find('.relative-volume').text(volume);
        $(this).text(rqtv);
        $(this).closest('tr').find('td').eq(9).find('.rqtv1').val(rqtv);
        $(this).closest('tr').find('td').eq(9).find('.rv1').val(volume);
        $(this).closest('tr').find('td').eq(9).find('.ctv1').val(b);
        $tr=$(this).parents('tr');
        var global_ratio=$('#global_ratio').val();
        var volume=$tr.find('.relative-volume').text();
        var gvolume=global_ratio*parseFloat(volume);
        $tr.find(".global-adjust-volume").text(gvolume);
        $(this).closest('tr').find('td').eq(9).find('.gv').val(gvolume);
        var specified_ratio=$tr.find('.specified-ratio').val();
        var a=parseFloat(gvolume)*parseFloat(specified_ratio);
        $tr.find('.mix-volume').text(a.toFixed(2));
        $(this).closest('tr').find('td').eq(9).find('.mv').val(a.toFixed(2));
        var totalVolume=0;
        $('.mix-volume').each(function(){
            totalVolume=parseFloat(totalVolume)+parseFloat($(this).text());  
        })
        $("#totalVolume").text(totalVolume.toFixed(2));
    })
    
}
/*function changeCtv(){
	$('.ctv').val($("#ctv").val());
}*/