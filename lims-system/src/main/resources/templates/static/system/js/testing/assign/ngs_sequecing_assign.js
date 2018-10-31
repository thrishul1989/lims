$(function()
{
    $("#assign_form").validate();

    $('body').on('blur', '#finalConcn', function()
    {
        var val = $(this).val();
        var number = parseFloat(val);
        var secondDiluteConcn = $('input[name="secondDiluteConcn"]').val();
        var secondDiluteConcnNumber = parseFloat(secondDiluteConcn);

        if (isNaN(number) || 0 == number || number >= secondDiluteConcn)
        {
            var historyValue = $(this).attr('data-history-value');
            $(this).val(historyValue);
        }
        else
        {
            $(this).val(number);
            $(this).attr('data-history-value', number);

            var finalInputVolume = $('input[name="finalInputVolume"]').val();
            var finalInputVolumeNumber = parseFloat(finalInputVolume);
            var finalSampleInputVolume = ((finalInputVolume * number) / secondDiluteConcn).toFixed(2);
            var finalHTInputVolume = (finalInputVolume - finalSampleInputVolume).toFixed(2);

            $('input[name="finalSampleInputVolume"]').val(finalSampleInputVolume);
            $('input[name="finalHTInputVolume"]').val(finalHTInputVolume);
            $('#final_concn_wrapper').empty().append(number);
            $('#final_sample_volume_wrapper').empty().append(finalSampleInputVolume);
            $('#final_ht_volume_wrapper').empty().append(finalHTInputVolume);
        }
    });
    
    
    $('input[name="sequecingType"]').click(function(){

    	if($(this).val() == '1'){
    		$("#selfForm").css("display","block");
    		$("#sendForm").css("display","none");
    		$("#flagForm1").attr("style","");
    		$("#flagForm2").attr("style","");
    	}
    	else if($(this).val() == '2'){
    		$("#selfForm").css("display","none");
    		$("#sendForm").css("display","block");
    		$("#flagForm1").css("display","none");
    		$("#flagForm2").css("display","none");
    	}
    })
});