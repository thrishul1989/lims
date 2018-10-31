$(function(){
	$('.file-preview').hide();
    $('.fileinput-upload').hide();
    $('.fileinput-remove').hide();
    
    $("#report_handle_form").validate();
    
    $('body').on('click', '#report_edit', function(e)
    		{
    		    $('#instruction_dialog').modal('show');
    		    
    		}).on('click', '#butt', function(){
    			var orderCode = $('#orderCode').val();
    			var productCode = $('#productCode').val();
    			var realFileNames = [];
    			var params = [];
    			
    	   		var fileName = $('#uploadData').val();
    	   	    var formatStr = '';
    	   	    var index = fileName.lastIndexOf('.');
    	   	    if (fileName.length == 0) {
    	   	        parent.layer.alert('请选择需要上传的文件', {title : "提示"});
    	   	        return;
    	   	    } else if (index > 0) {
    	   	        formatStr = fileName.substring(index);
    	   	        if (!(".doc" == formatStr || ".docx" == formatStr || ".pdf" == formatStr)) {
    	   	            parent.layer.alert('请上传word文件或者PDF文件', {title : "提示"});
    	   	            return;
    	   	        }
    	   	        realFileNames = fileName.split("\\");
    	   	        params = realFileNames[realFileNames.length - 1].split("_");
    	   	        if(params.length == 4){
    	   	        	if(productCode != params[0] || orderCode != params[1]){
    	   	        		parent.layer.alert("订单编号或产品编号不匹配！", {title : "提示"});
        	   	        	return;
    	   	        	}
    	   	        }else{
    	   	        	parent.layer.alert("文件名称不符合‘产品编号_订单编号_样本编号_样本名称’规则！", {title : "提示"});
    	   	        	return;
    	   	        }
    	   	    }
    	   	    $.ajax({
    	   	        url : '/bpm/report/report_upload_single.do',
    	   	        type : 'POST',
    	   	        cache : false,
    	   	        data : new FormData($('#uploadForm')[0]),
    	   	        processData : false,
    	   	        contentType : false,
    	   	       }).done(function(data) {
    	   	    	   $('#report_download').show();
    	   	    	   $('#report_download').attr('href','/bpm/report/firstReview/download.do?fileName='+data);
    	   	    	   $('#myModal').modal('hide');
    	   	    	   
    	   	    	   $('#report_file_upload').val('true');
    	   		   }).fail(function(res){});
    	   	}).on('click', '#but_handle', function()
	   		    {
    	   			handsubmitForm();
	    	   	 
	    	   		var iframe_sample = $('#iframe_sample').val();
	    	   		var iframe_result = $('#iframe_result').val();
	    	   		var report_file_upload = $('#report_file_upload').val();
	    	   		if(report_file_upload == 'false'){
	    	   			if($("#combineUrl").val()==''){
	    	   				parent.layer.alert('请先上传报告文件(doc、docx、pdf文件)',{title:"提示"});	
	    	   			}
	    	   		}else if(iframe_sample == 'false'){
	    	   			parent.layer.alert('样本结果数据未保存，请保存后再提交报告数据',{title:"提示"});
	    	   		}else if(iframe_result == 'false'){
	    	   			parent.layer.alert('实验结果数据未保存，请保存后再提交报告数据',{title:"提示"});
	    	   		}else{
	    	   			var layerObject = parent.parent.layer;
		    	        var buttons = [ '确定', '取消' ];
		
		    	        layerObject.confirm('确定保存报告数据吗？', {
		    	            icon : 3,
		    	            title : '操作确认',
		    	            btn : buttons,
		    	            shade : 'transparent'
		    	        }, function(index)
		    	        {
		    	        	$('#report_handle_form').submit();
		    	            layerObject.close(index);
		    	        }, function(index)
		    	        {
		    	            layerObject.close(index);
		    	        });
	    	   		}
    	    });
    		
   	$('#instruction_but').on('click', function()
   	    {
   			var id = $('#id').val();
   	    	var instruction = $('#instruction').val();
   	    	if('' == instruction){
   	    		parent.layer.alert('请编辑结果说明',{title:"提示"});
   	    	}else{
   	    		$.ajax({
   	    			 type:"POST",
   	    			 traditional: true,
   	    			 url:"/bpm/report/report_edit.do",
   	    			 data:{
   	    				 id:id,
   	    				 instruction : instruction
   	    				 },
   	    			 async: false,
   	    			 success:function(data){
   	    				 $('#instruction_dialog').modal('hide');
   	    		    },
   	    			 error:function(){
   	    				 alert("failed");
   	    			}
   	    		});
   	    	}
   	    });
}); 