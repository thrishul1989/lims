$(function()
{
    $("#testing_task_form").validate();

    $('body').on('change', '#reagentKit', function()
    {
        var selected = $(this).find('option:selected');
        var quantity = selected.attr('data-input-quantity');
        var volume = selected.attr('data-input-volume');

        if (undefined == quantity)
        {
            quantity = '';
        }

        if (undefined == volume)
        {
            volume = 0;
        }

        $('.inputSize').val(quantity);

        $('#testing_task_detail_table tbody tr').each(function(e){
            var $that = $(this);
            var v1 = $that.find('.concentration').html();
            console.log(v1+"~~~~~~~~~~~~~");
            var quantityResult= 0;
            if('' != quantity){
                quantityResult = quantity;
            }
            var result = 0;
            if(undefined == v1 || ''== v1){
                result = 0;
            }else{
                result = (quantityResult/v1).toFixed(2);
            }
            $that.find('.addSampleVolume').html(result);

            var waterVol = 0;
            if(0 != volume){
                waterVol = (volume-result).toFixed(2);
                if((waterVol-0)<=0)
                {
                    waterVol = 0;
                }
            }
            $that.find('.addWaterVolume').html(waterVol);
            $that.find('.addSampleVolumeInput').val(result);
            $that.find('.addWaterVolumeInput').val(waterVol);

        });

    }).on('change','#testing_task_detail_table input.inputSize',function(){
        var volume = $("#reagentKit").find('option:selected').attr('data-input-volume');
        console.log(volume);
        var quantity = $(this).val();
        var $tr = $(this).closest('tr');
        var v1 = $tr.find('.concentration').html();
        var quantityResult= 0;
        if('' != quantity){
            quantityResult = quantity;
        }
        var result = 0;
        if(undefined == v1 || ''== v1){
            result = 0;
        }else{
            result = (quantityResult/v1).toFixed(2);
        }
        var waterVol = 0;
        if(0 != volume){
            waterVol = (volume-result).toFixed(2);
            if((waterVol-0)<=0)
            {
                waterVol = 0;
            }
        }
        $tr.find('.addSampleVolume').html(result);
        $tr.find('.addWaterVolume').html(waterVol);
        $tr.find('.addSampleVolumeInput').val(result);
        $tr.find('.addWaterVolumeInput').val(waterVol);
    })
});