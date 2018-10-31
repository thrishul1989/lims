$(function()
{
	var id = $('#id').val();
	var companyRatio = $('#companyRatio').val();
    var inspectionRatio = $('#inspectionRatio').val();
    var cqCompanyRatio = $('#cqCompanyRatio').val();
	var institution = $('#institution').val();
    var delaySign = $('#delayStatus').val();
	
	//合计
	var summationReceivable = 0;
	var summationActualPayment = 0;
	var summationFillingAmount = 0;
	$('#cost tbody tr').each(function(){
		var receivable = Number($(this).find('.receivable').text());
		var actualPayment = Number($(this).find('.actualPayment').text());
		var fillingAmount = Number($(this).find('.fillingAmount').text());
		summationReceivable = summationReceivable + receivable;
		summationActualPayment = summationActualPayment + actualPayment;
		summationFillingAmount = summationFillingAmount + fillingAmount;
	});
	$('.summationReceivable').text(summationReceivable);
	$('.summationActualPayment').text(summationActualPayment);
	$('.summationFillingAmount').text(summationFillingAmount);
	
	var summationCompanyReceivable = 0;
	var summationCompanyActualPayment = 0;
	var summationInspectionReceivable = 0;
	var summationInspectionActualPayment = 0;

    var summationCqCompanyReceivable = 0;
    var summationCqCompanyActualPayment = 0;
    var summationCqInspectionReceivable = 0;
    var summationCqInspectionActualPayment = 0;
	$('#statistics tbody tr').each(function(){
		var companyReceivable = Number($(this).find('.companyReceivable').text());
		var companyActualPayment = Number($(this).find('.companyActualPayment').text());
		var inspectionReceivable = Number($(this).find('.inspectionReceivable').text());
		var inspectionActualPayment = Number($(this).find('.inspectionActualPayment').text());

        var cqCompanyReceivable = Number($(this).find('.cqCompanyReceivable').text());
        var cqCompanyActualPayment = Number($(this).find('.cqCompanyActualPayment').text());
        var cqInspectionReceivable = Number($(this).find('.cqInspectionReceivable').text());
        var cqInspectionActualPayment = Number($(this).find('.cqInspectionActualPayment').text());

		summationCompanyReceivable = summationCompanyReceivable + companyReceivable;
		summationCompanyActualPayment = summationCompanyActualPayment + companyActualPayment;
		summationInspectionReceivable = summationInspectionReceivable + inspectionReceivable;
		summationInspectionActualPayment = summationInspectionActualPayment + inspectionActualPayment;

        summationCqCompanyReceivable = summationCqCompanyReceivable + cqCompanyReceivable;
        summationCqCompanyActualPayment = summationCqCompanyActualPayment + cqCompanyActualPayment;
        summationCqInspectionReceivable = summationCqInspectionReceivable + cqInspectionReceivable;
        summationCqInspectionActualPayment = summationCqInspectionActualPayment + cqInspectionActualPayment;
	});
	$('.summationCompanyReceivable').text(summationCompanyReceivable);
	$('.summationCompanyActualPayment').text(summationCompanyActualPayment);
	$('.summationInspectionReceivable').text(summationInspectionReceivable);
	$('.summationInspectionActualPayment').text(summationInspectionActualPayment);

    $('.summationCqCompanyReceivable').text(summationCqCompanyReceivable);
    $('.summationCqCompanyActualPayment').text(summationCqCompanyActualPayment);
    $('.summationCqInspectionReceivable').text(summationCqInspectionReceivable);
    $('.summationCqInspectionActualPayment').text(summationCqInspectionActualPayment);
	
	$('.order').each(function(){
		var $tr = $(this).parents('tr');
		var orderCheck = $(this).magicSuggest({
		    width: 190,
		    highlight: true,
	        data:'/bmm/advanceInvoice/orderList.do?id='+id,
		    method:'get',
		    queryParam:"key",
		    allowFreeEntries:false,
		    renderer: function(v){
		    	return '<div>' + '<div >' + '<div>' + v.name + '</div>' + '<div style="clear:both;"></div>';
		    }
		});
		$(orderCheck).on('selectionchange',function(e){
			var $that = $(this)[0];
			var selections = $that.getSelection();
			var summation = 0;
			console.log($that.getValue());
			for(var i = 0; i < selections.length; i ++){
				var inputOrderId = selections[i].id;
				$('#cost tbody tr').each(function(){
		 			var orderId = $(this).find('.orderId').val();
		 			if(orderId == inputOrderId){
		 				var receivable = Number($(this).find('.receivable').text());
                        var actualPayment =  Number($(this).find('.actualPayment').text());
                        if (delaySign =="1")
                        {
                            actualPayment = receivable; //如果是延迟付款申请的，用应收款
                        }
                        if(institution == "1"){
                            summation = summation + (actualPayment*Number(companyRatio));
                        }else if (institution =="0")
                        {
                            summation = summation + (actualPayment*Number(inspectionRatio));
                        }else if (institution =="2")
                        {
                            summation = summation + (actualPayment*Number(cqCompanyRatio));
                        }else
                        {
                            summation = summation + (actualPayment*(1 - Number(companyRatio) -Number(inspectionRatio) - Number(cqCompanyRatio)));
                        }
		 			}
		 		});
			}
			$tr.find('td').eq(1).find('.invoiceAmount').val(summation.toFixed(2));
		});
	});
	
	
    $('body').on('click', '.addInvoice', function(){
       var invoiceTemp = $("#invoiceTemp").html();
       $(this).parents(".invoiceInfo").append(invoiceTemp);
       
       $('.order').each(function(){
	   		var $tr = $(this).parents('tr');
	   		var orderCheck1 = $(this).magicSuggest({
	   		    width: 190,
	   		    highlight: true,
	   	        data:'/bmm/advanceInvoice/orderList.do?id='+id,
	   		    method:'get',
	   		    queryParam:"key",
	   		    allowFreeEntries:false,
	   		    renderer: function(v){
	   		    	return '<div>' + '<div >' + '<div>' + v.name + '</div>' + '<div style="clear:both;"></div>';
	   		    }
	   		});
	   		$(orderCheck1).on('selectionchange',function(e){
	   			var $that = $(this)[0];
	   			var selections = $that.getSelection();
	   			var summation = 0;
	   			console.log($that.getValue());
	   			for(var i = 0; i < selections.length; i ++){
	   				var inputOrderId = selections[i].id;
	   				$('#cost tbody tr').each(function(){
	   		 			var orderId = $(this).find('.orderId').val();
	   		 			if(orderId == inputOrderId){
                            var actualPayment =  Number($(this).find('.actualPayment').text());
                            var receivable = Number($(this).find('.receivable').text());
                            if (delaySign =="1")
							{
								actualPayment = receivable; //如果是延迟付款申请的，用应收款
							}
		   		 			if(institution == "1"){
			 					summation = summation + (actualPayment*Number(companyRatio));
			 				}else if (institution =="0")
			 				{
                                summation = summation + (actualPayment*Number(inspectionRatio));
			 				}else if (institution =="2")
                            {
                                summation = summation + (actualPayment*Number(cqCompanyRatio));
                            }else
                            {
                                summation = summation + (actualPayment*(1 - Number(companyRatio) -Number(inspectionRatio) - Number(cqCompanyRatio)));
                            }

	   		 			}
	   		 		});
	   			}
	   			$tr.find('td').eq(1).find('.invoiceAmount').val(summation.toFixed(2));
	   		});
	   	});
    }).on('click', '.removeInvoice', function(){
    	$(this).parents('.invoiceTable').remove();
    }).on('blur', '.invoiceAmount', function(){
    	if(isNaN($(this).val())){
    		parent.parent.layer.alert('请输入数字', {title : "提示"});
    		$(this).val(0);
    	}
    });

    $("#update_submit").click( function(){
        var group=[];
        $("#amount_table tr ").each(function(i, v){
            var orderId = $(this).find("#orderId").val();
            if(orderId != undefined){
                var id = $(this).find('td').eq(2).text();
                var price = $(this).find('td').eq(4).find("input").val();
            }else{
                var id = $(this).find('td').eq(1).text();
                var price = $(this).find('td').eq(3).find("input").val();
            }

            var productAmount={"orderProductId":id,"orderProductPrice":price};
            group.push(productAmount);
        });
        $("#requestParams").val(JSON.stringify(group));
        console.info(JSON.stringify(group));
        $('#amountForm').submit();
    });
});
