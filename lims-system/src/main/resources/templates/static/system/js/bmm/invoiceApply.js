var companyMap ={
	"1":"shunfeng",
	"2":"tiantian",
	"3":"youzhengguonei"
    };

function showProcess(courierNumber,type){
	$('#express_table').html(""); 
	var param = {};
	param.com = companyMap[type];;
	param.num = courierNumber;
	$.get("/order/showTransport.do?param="+JSON.stringify(param) , function(data){
	     var tt = data.msg;
	     var dataObj=eval("("+tt+")");
	     if(dataObj.message=="ok"){
	    	 $('.express_query_code').find('font').text(dataObj.nu);
		     if(dataObj.state == "3"){
		    	 $('.express_query_status').find('font').text("已签收");
		     }
		    
		     $.each(dataObj.data,function(index,obj){
		    	  var context =obj.context;
		    	 $('#express_table').append("<tr><td>"+obj.time+"</td> <td>"+context+"</td> </tr>");
		     });
	     }else{
	    	 $('.express_query_code').find('font').text(courierNumber);
	    	 $('.express_query_status').find('font').text("查询无结果");
	    	 $('#express_table').append(""+dataObj.message+""); 
	     }
	     
	     $('#myModal').modal('show');
	});    
}
    	
$(function()
{
	var summation = 0;
	$('#cost tbody tr').each(function(){
		var incomingAmount = Number($(this).find('.incomingAmount').text());
		if(!isNaN(incomingAmount)){
			summation = Number(summation) + incomingAmount;
		}
	});
	$('.summation').text(summation);
	
    $('body').on('ifChecked', '.applyResult', function(){
    	$('#applyResultValue').val($(this).val());
    	var result = $('#applyResultValue').val();
    	if(result == "1"){
    		$('#receive_info').show();
    	}
    	if(result == "2"){
    		$('#receive_info').hide();
    	}
    }).on('click', '#button_submit_doApply', function()
		{
	    	var result = $('#applyResultValue').val();
	    	var receiverName = $('#receiverName').val();
	    	var drawerId = $('#drawerId').val();
	    	var invoiceTime = $('#invoiceTime').val();
	    	var invoicerNo = $('#invoicerNo').val();
	    	var authIdea = $('#authIdea').val();
	    	var flag = true;
	    	
	    	if(result == "1"){
	    		if(receiverName=="" || drawerId=="" || invoiceTime=="" || invoicerNo==""){
	    			parent.layer.alert('开票信息录入不全',{title:"提示"});
	    			flag = false;
	    		}
	    	}
	    	if(result == "2"){
	    		if(authIdea==""){
	    			parent.layer.alert('未填写审核意见',{title:"提示"});
	    			flag = false;
	    		}
	    	}
	    	
	        if(flag){
	        	var layerObject = parent.parent.layer;
	        	var buttons = [ '确定', '取消' ];
	        	
	        	layerObject.confirm('确定要提交此申请吗？', {
	        		icon : 3,
	        		title : '操作确认',
	        		btn : buttons,
	        		shade : 'transparent'
	        	}, function(index)
	        	{
	    			$('#doApply_form').submit();
	    			layerObject.close(index);
	        	}, function(index)
	        	{
	        		layerObject.close(index);
	        	});
	        }
	    }).on('change', '#transportType', function()
	       {
	    		var type = $('#transportType').val();
	    		if("0" == type){
	    			$('#artificial').show();
	    			$('#courier').hide();
	    		}else{
	    			$('#courier').show();
	    			$('#artificial').hide();
	    		}
	    	}).on('click', '#button_submit_send', function()
	    		    {
	    		var type = $('#transportType').val();
	        	var sendDate = $('#sendDate').val();
	        	var transportName = $('#transportName').val();
	        	var courierNumber = $('#courierNumber').val();
	        	var flag = true;
	        	
	        	if(type == "0"){
	        		if(sendDate=="" || transportName==""){
	        			parent.layer.alert('寄件信息录入不全',{title:"提示"});
	        			flag = false;
	        		}
	        	}
	        	if(type == "1"){
	        		if(sendDate=="" || courierNumber==""){
	        			parent.layer.alert('寄件信息录入不全',{title:"提示"});
	        			flag = false;
	        		}
	        	}
	        	
	            if(flag){
	            	var layerObject = parent.parent.layer;
	            	var buttons = [ '确定', '取消' ];
	            	
	            	layerObject.confirm('确定要提交此申请吗？', {
	            		icon : 3,
	            		title : '操作确认',
	            		btn : buttons,
	            		shade : 'transparent'
	            	}, function(index)
	            	{
	        			$('#send_form').submit();
	        			layerObject.close(index);
	            	}, function(index)
	            	{
	            		layerObject.close(index);
	            	});
	            }
	        });
});
