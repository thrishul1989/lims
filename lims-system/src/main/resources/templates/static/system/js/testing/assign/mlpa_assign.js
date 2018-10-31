$(function()
{
    $("#testing_task_form").validate();

    
    setTestingCode();

    
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
                if(result > Number(volume)){
                	result=volume;
                }
            }
            $that.find('.addSampleVolume').html(result);

            var waterVol = 0;
            if(0 != volume){
                waterVol = (volume-result).toFixed(2);
            }
            if(waterVol < 0)
            {
                waterVol=0;
            }
            $that.find('.addWaterVolume').html(waterVol);
            $that.find('.addSampleVolumeInput').val(result);
            $that.find('.addWaterVolumeInput').val(waterVol);

        });

    }).on('change','#testing_task_detail_table input.inputSize',function(){
        var volume = $("#reagentKit").find('option:selected').attr('data-input-volume');
        console.log(volume);
        var quantity = $(this).val();
        if(!isNaN(quantity)){
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
        		if(result > Number(volume)){
                	result=volume;
                }
        	}
        	var waterVol = 0;
        	if(0 != volume){
        		waterVol = (volume-result).toFixed(2);
        	}
        	if(waterVol < 0)
        	{
        		waterVol=0;
        	}
        	$tr.find('.addSampleVolume').html(result);
        	$tr.find('.addWaterVolume').html(waterVol);
        	$tr.find('.addSampleVolumeInput').val(result);
        	$tr.find('.addWaterVolumeInput').val(waterVol);
        }else{
        	alert('投入量必须为数字');
        	$(this).val('');
        }
    }).on('change','#testing_task_detail_table input.checkSample',function(){
        var num = parseInt($(this).val());
        if(num < 3)
        {
            num = 3;
            alert("请输入3-6的值");
            $(this).val(3);
        }
        if(num > 6)
        {
            num = 6;
            alert("请输入3-6的值");
            $(this).val(6);
        }
        $('#testing_task_detail_table tbody tr').each(function(){
            $(this).find('td:eq(0)').html('');
        });
        var $nextAll = $(this).closest('tr').nextAll('tr');
        var crtNum = null;
        //var $tmpTr = null;
        var lastIndex = $nextAll.length - 1;
        var strTd = '';
        //var isOne = false;
        $nextAll.each(function(index){
            var $thatTr = $(this);
            if(!strTd){
                if($thatTr.hasClass('record-last')){
                    strTd =$thatTr.next('tr').html();
                    crtNum = 0;
                }else{
                    if(index == 0){
                        if($thatTr.prev().hasClass('record-one')){
                            strTd = $thatTr.html();
                            crtNum = 1;
                        }
                    }
                }
            }else{
                crtNum ++;
                if(index != lastIndex){
                    if($thatTr.hasClass('record-start') || $thatTr.hasClass('record-one')){
                        if( crtNum == num ){
                            $thatTr.before('<tr>' + strTd + '</tr>');
                        }else if( crtNum < num){
                            var tmpStr = '';
                            for(var i=0; i <= num - crtNum; i++){
                                tmpStr += '<tr>' + strTd + '</tr>';
                            }
                            $thatTr.before(tmpStr);
                        }
                        return false;
                    }else{
                        if(crtNum > num){
                            $thatTr.remove();
                        }
                    }
                }else{
                    if(crtNum > num){
                        $thatTr.remove();
                    }else if (crtNum < num){
                        var tmpStr = '';
                        for(var i=0; i < num - crtNum; i++){
                            tmpStr += '<tr>' + strTd + '</tr>';
                        }
                        $thatTr.before(tmpStr);
                    }
                }
            }
        });
        setTestingCode();
        $('#testing_task_detail_table tbody tr').each(function(){
            if($(this).find('td:eq(0)').text()=='A13'){
                alert('样本板占用个数超过96个，请重新选择');
            }
        });
    }).on('click','#BHBtn',function(e){
        setTestingCode();
        
    });
});

function setTestingCode(){


    var totalNum = $("#testing_task_detail_table tbody tr").length;
    console.log(totalNum);
    if(96 < totalNum)
    {
        alert("总记录条数大于96，请修改！");
        return;
    }
    var n1 = 0;
    var n2 = 0;
    var n3 =''
    $('#testing_task_detail_table tbody tr').each(function(index){
        var $tr = $(this);
        if($(this).hasClass('record-start')||$(this).hasClass('record-one')){
            n1++;
            n2 = 0;
        }else{
            n2++;
        }
        switch(n2%8){
            case 0:
                n3= 'A';
                if(n2==8) {
                    n1++;
                }
                break;
            case 1:
                n3 = 'B';
                break;
            case 2:
                n3 = 'C';
                break;
            case 3:
                n3 = 'D';
                break;
            case 4:
                n3= 'E';
                break;
            case 5:
                n3= 'F';
                break;
            case 6:
                n3= 'G';
                break;
            case 7:
                n3= 'H';
                break;
        }
        $tr.find('td:eq(0)').html(n3 + n1);
        $tr.find("[name$='testCode']").val(n3 + n1);
        var crtIndex = index;
        $tr.find("[name$='orderFlag']").val(crtIndex+1);
        var reg = new RegExp('\\[(.+?)\\]',"g");
        $tr.find('input:hidden').each(function(){
            var strName = $(this).attr('name');
            if(strName){
                strName = strName.replace(reg,"[" + crtIndex + "]");
                $(this).attr('name',strName);
            }
        })
    });


}