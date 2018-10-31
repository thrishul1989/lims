$(function(){
	var receivable_money = $("#receivable").text();
	var actualPayment_money = $("#actualPayment").text();
	var delaySign = $("#delaySign").val();
	if(receivable_money != actualPayment_money ){
		$("#actualPay").append("<a title='修改订单应收额' href='javascript:editActualMoney()'><b>修改</b></a>");
	}
	
	
    $('body').on('click', '.addInvoice', function(){
       var $invoiceTemp = $($("#invoiceTemp").html());
       var invoiceAmount = $("#invoiceAmount").val();
       var count = 0;
       $('#invoiceForm .invoiceTable').each(function(){
    	   var amount = $(this).find('.invoiceAmount').val();
    	   count = Number(count) + Number(amount);
       });
       var remainAmount = Number(invoiceAmount) - count;
       $invoiceTemp.find('.invoiceAmount').val(remainAmount);
       $(this).parents(".invoiceInfo").append($invoiceTemp);
   	}).on('click', '.removeInvoice', function(){
    	$(this).parents('.invoiceTable').remove();
    }).on('blur', '.invoiceAmount', function(){
    	if(isNaN($(this).val())){
    		parent.parent.layer.alert('请输入数字', {title : "提示"});
    		$(this).val(0);
    	}
    }).on('click', '#button_solve', function(){
    	var flag = true;
    	var invoiceAmount = $("#invoiceAmount").val();
    	var reduceStatus = $("#reduceStatus").val();
    	var receivable = $("#receivable").text();
    	var actualPayment = $("#actualPayment").text();
    	var count = 0;
    	invoiceInfoList=[];
        $(".invoiceTable").each(function(){
        	var invoiceTime=$(this).find(".invoiceTime").val();
        	var invoicerNo=$(this).find(".invoicerNo").val();
        	var drawerId=$(this).find(".drawerId").val();
        	if("" == invoiceTime || "" == invoicerNo || "" == drawerId){
        		parent.parent.layer.alert('开票信息必填项缺失', {title : "提示"});
        		flag = false;
        	}
        	
            invoiceInfo={};
            invoiceInfo.institution=$(this).find(".institution").val();
            invoiceInfo.invoiceAmount=$(this).find(".invoiceAmount").val();
            invoiceInfo.invoiceTime=invoiceTime;
            invoiceInfo.invoicerNo=invoicerNo;
            invoiceInfo.drawerId=drawerId;
            invoiceInfo.receiverName=$(this).find(".receiverName").val();
            invoiceInfoList.push(invoiceInfo);
            
            count = Number(count) + Number($(this).find(".invoiceAmount").val());
        });
        var content=JSON.stringify(invoiceInfoList);
        $("#content").val(content);

        if(count != Number(invoiceAmount)){
        	flag = false;
        	debugger;
        	if (delaySign == "1")
			{
                parent.parent.layer.alert('开票总金额与应收款不相等', {title : "提示"});

            }
			else
			{
                parent.parent.layer.alert('开票总金额与实收款不相等', {title : "提示"});

            }
        }
        
        if(flag){
        	if(reduceStatus == "0" || receivable != actualPayment){
        		
        		if($("#solveStatus").val()==2){   //可开票，应收款小于确认到账，必须增加订单应收款，使之对应
        			
        			var layerObject = parent.parent.layer;
                    var buttons = [ '修改金额', '取消' ];

                    layerObject.confirm('开票金额和实际收款金额不等,需修改订单应付款？', {
                        icon : 3,
                        title : '操作提示',
                        btn : buttons,
                        shade : 'transparent'
                    }, function(index)
                    {
                    	$("#updateReceiveModal").modal('show');
                        layerObject.close(index);
                    }, function(index)
                    {
                        layerObject.close(index);
                    });
        			
        			
        			
        		}else if($("#solveStatus").val()==1){ //待开票
        			var layerObject = parent.parent.layer;
                    var buttons = [ '继续开票', '修改金额' ];

                    layerObject.confirm('开票金额和实际收款金额不等,继续开票还是修改订单应付款？', {
                        icon : 3,
                        title : '操作提示',
                        btn : buttons,
                        shade : 'transparent'
                    }, function(index)
                    {
                    	$('#invoiceForm').submit();
                        layerObject.close(index);
                    }, function(index)
                    {
                    	$("#updateReceiveModal").modal('show');
                        layerObject.close(index);
                    });
        		}
                
            }else{
            	//相等直接提交
            	var layerObject = parent.parent.layer;
                var buttons = [ '确定', '取消' ];
                layerObject.confirm('订单应收款与确认到账相等,确定开票吗？', {
                    icon : 3,
                    title : '操作提示',
                    btn : buttons,
                    shade : 'transparent'
                }, function(index)
                {
                	$('#invoiceForm').submit();
                    layerObject.close(index);
                }, function(index)
                {
                    layerObject.close(index);
                });
            	
            	
            }
        }
    }).on('click', '#update_submit', function(){
    	var group=[];
    	$("#amount_table tr ").each(function(i, v){
    		if(i==0){
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
    	$('#amountForm').submit();
    });
    

	$("#amountForm").validate({
		
		submitHandler:function(form){
			form.submit();
		},
	
		rules: {
	        	amount: {
	        		required:true,
	        		number:true,
	        		min:0.01
	        	},
	        },
	        messages: {
	        	amount:  {
	                required: "请输入订单产品费用",
	            },
	        }
	 });
	
	
	
	
});

function editActualMoney(){
	$("#updateReceiveModal").modal('show');
}
